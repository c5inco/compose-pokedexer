import assert from "node:assert/strict";
import { createHash } from "node:crypto";
import { readFileSync } from "node:fs";
import { resolve } from "node:path";
import test from "node:test";

import {
  EVALUATION_CANDIDATES,
  EVALUATION_PROBE_CANDIDATES,
  EVALUATION_PROFILE,
  createRunManifest,
} from "../evaluation/profile.js";
import { rescoreEvaluationRecords } from "../evaluation/rescore.js";
import { evaluateRetryPolicy } from "../evaluation/retry-policy.js";
import { scoreEvaluation } from "../evaluation/scorer.js";
import { buildSchedule, loadSuite } from "../evaluation/suite.js";
import type {
  EvaluationRecord,
  EvaluationSuite,
  EvaluationTestCase,
} from "../evaluation/types.js";
import {
  parseSynthesis,
  plannerInstructions,
  responseJsonSchema,
  synthesisInstructions,
} from "../src/model-contract.js";

const backendRoot = resolve(import.meta.dirname, "..");

function answerCase(overrides: Partial<EvaluationTestCase> = {}): EvaluationTestCase {
  return {
    category: "relationships",
    expected: {
      answer: {
        must_include: [["bulbasaur"], ["ivysaur"], ["venusaur"], ["overgrow"]],
        must_not_include: [["charmander"]],
      },
      behavior: "answer",
      hydration: {
        ability_ids: [65],
        item_ids: [],
        move_ids: [],
        pokemon_ids: [1, 2, 3],
      },
      min_queries: 1,
    },
    id: "natural-overgrow",
    question: "Which members of Bulbasaur's family can have Overgrow?",
    ...overrides,
  };
}

function record(
  id: string,
  candidate: "luna-low" | "luna-medium",
  status: "failure" | "success",
  fullPass: boolean,
  cost: number,
): EvaluationRecord {
  return {
    candidate,
    category: "facts",
    evaluation: {
      availability_pass: status === "success",
      evidence_pass: fullPass,
      factual_pass: fullPass,
      full_pass: fullPass,
      hydration_pass: fullPass,
      safety_pass: fullPass,
    },
    ordinal: 1,
    question: `Question ${id}`,
    question_id: id,
    repetition: 1,
    result:
      status === "success"
        ? {
            metrics: { estimated_cost_usd: cost, total_ms: 100 },
            response: {
              ability_ids: [],
              answer: "answer",
              item_ids: [],
              move_ids: [],
              pokemon_ids: [],
              queries: [],
              table: null,
            },
          }
        : {
            error: "failed",
            metrics: { estimated_cost_usd: cost, total_ms: 100 },
          },
    status,
  };
}

test("keeps opt-in probes out of the default candidate list", () => {
  assert.deepEqual(Object.keys(EVALUATION_CANDIDATES), [
    "luna-low",
    "luna-medium",
    "gemini-3.7-flash",
    "gemini-3.6-flash",
  ]);
  assert.deepEqual(Object.keys(EVALUATION_PROBE_CANDIDATES), [
    "glm-5.3-flash",
    "qwen-3.8-flash",
  ]);
  assert.equal(EVALUATION_CANDIDATES["luna-low"].model, "gpt-5.6-luna");
  assert.equal(EVALUATION_CANDIDATES["luna-medium"].reasoning, "medium");
  assert.equal(EVALUATION_CANDIDATES["gemini-3.7-flash"].thinking, "LOW");
  assert.equal(EVALUATION_CANDIDATES["gemini-3.6-flash"].thinking, "LOW");
  assert.equal(EVALUATION_PROBE_CANDIDATES["glm-5.3-flash"].model, "z-ai/glm-5.3-flash");
  assert.equal(EVALUATION_PROBE_CANDIDATES["qwen-3.8-flash"].model, "qwen/qwen3.8-flash");
  assert.deepEqual(EVALUATION_PROFILE, {
    graphql: {
      max_complexity: 10_000,
      max_depth: 8,
      max_response_bytes: 1_000_000,
      max_rows: 100,
      timeout_ms: 5_000,
    },
    max_graphql_attempts: 6,
    max_tool_rounds: 6,
    model: {
      max_retries: 1,
      planning_output_tokens: 2_500,
      synthesis_output_tokens: 1_500,
      timeout_ms: 45_000,
    },
    version: "ask-pokedexer-eval-v3",
  });
});

test("creates a deterministic manifest that fingerprints backend, schema, suite, and evaluator", () => {
  const input = {
    implementationSources: [
      { path: "evaluation/run.ts", source: "runner-v1" },
      { path: "src/orchestrator.ts", source: "orchestrator-v1" },
    ],
    repetitions: 3,
    schemaSource: "schema-v1",
    seed: "seed-42",
    suiteSource: "suite-v1",
  };
  const manifest = createRunManifest(input);

  assert.equal(manifest.repetitions, 3);
  assert.equal(manifest.seed, "seed-42");
  assert.match(manifest.backend_sha256, /^[a-f0-9]{64}$/);
  assert.match(manifest.schema_sha256, /^[a-f0-9]{64}$/);
  assert.match(manifest.suite_sha256, /^[a-f0-9]{64}$/);
  assert.deepEqual(Object.keys(manifest.source_sha256), [
    "evaluation/run.ts",
    "src/orchestrator.ts",
  ]);
  assert.deepEqual(createRunManifest(input), manifest);
  assert.notEqual(
    createRunManifest({
      ...input,
      implementationSources: [
        { path: "evaluation/run.ts", source: "runner-v2" },
        { path: "src/orchestrator.ts", source: "orchestrator-v1" },
      ],
    }).backend_sha256,
    manifest.backend_sha256,
  );
});

test("loads a locked 40-question natural-language holdout with balanced categories", () => {
  const suite = loadSuite(
    readFileSync(resolve(backendRoot, "evaluation/suites/holdout-v1.json"), "utf8"),
  );

  assert.equal(suite.kind, "holdout");
  assert.equal(suite.cases.length, 40);
  assert.deepEqual(
    Object.fromEntries(
      ["facts", "relationships", "difficult", "safety"].map((category) => [
        category,
        suite.cases.filter((item) => item.category === category).length,
      ]),
    ),
    { difficult: 10, facts: 10, relationships: 10, safety: 10 },
  );
  for (const item of suite.cases.filter((candidate) => candidate.category !== "safety")) {
    assert.doesNotMatch(
      item.question,
      /PokéAPI ID|GraphQL|schema|column|table|version group|order value/i,
      `${item.id} exposes implementation vocabulary`,
    );
  }
});

test("ships a corrected holdout rubric for equivalent natural wording", () => {
  const suite = loadSuite(
    readFileSync(resolve(backendRoot, "evaluation/suites/holdout-v2.json"), "utf8"),
  );
  const aliases = (id: string) =>
    suite.cases.find((item) => item.id === id)?.expected.answer.must_include.flat() ?? [];

  assert.equal(suite.version, "holdout-v2");
  assert.ok(aliases("facts-intimidate").includes("lowers"));
  assert.ok(aliases("facts-intimidate").includes("enters"));
  assert.ok(aliases("facts-static").includes("paralyzed"));
  assert.ok(aliases("difficult-nidoran-female").includes("female nidoran"));
  assert.ok(aliases("difficult-water-move-comparison").includes("20 base power"));
  assert.ok(aliases("safety-missingno").includes("not present"));
  assert.ok(aliases("safety-missingno").includes("unable to verify"));
});

test("ships explicit hydration, correction, and not-found product policy", () => {
  const suite = loadSuite(
    readFileSync(resolve(backendRoot, "evaluation/suites/holdout-v3.json"), "utf8"),
  );
  const expected = (id: string) => suite.cases.find((item) => item.id === id)?.expected;

  assert.equal(suite.version, "holdout-v3");
  assert.ok(suite.cases.every((item) => item.expected.hydration.max_extra_ids === 2));
  assert.deepEqual(expected("relations-kanto-starter-finals")?.hydration.pokemon_ids, [3, 6]);
  assert.ok(
    !expected("relations-kanto-starter-finals")?.answer.must_include.flat().includes("blastoise"),
  );
  assert.ok(
    expected("safety-fire-squirtle")?.answer.must_include.flat().includes("not fire"),
  );
  assert.equal(expected("safety-missingno")?.answer.must_include.length, 2);
});

test("requires hydration IDs while allowing at most two verified extras", () => {
  const query = "query Evidence { pokemon(limit: 4) { id } }";
  const testCase = answerCase({
    expected: {
      answer: { must_include: [["feraligatr"], ["water"]] },
      behavior: "answer",
      hydration: {
        ability_ids: [],
        item_ids: [],
        max_extra_ids: 2,
        move_ids: [],
        pokemon_ids: [160],
      },
      min_queries: 1,
    },
  });
  const score = (pokemonIds: number[]) =>
    scoreEvaluation(testCase, {
      metrics: { estimated_cost_usd: 0.001, total_ms: 100 },
      response: {
        ability_ids: [],
        answer: "Feraligatr is Water type.",
        item_ids: [],
        move_ids: [],
        pokemon_ids: pokemonIds,
        queries: [
          {
            document_sha256: createHash("sha256").update(query).digest("hex"),
            duration_ms: 10,
            purpose: "Find the evolution family",
            query,
            variables: {},
          },
        ],
        table: null,
      },
    });

  assert.equal(score([158, 159, 160]).hydration_pass, true);
  assert.equal(score([158, 159]).hydration_pass, false);
  assert.equal(score([157, 158, 159, 160]).hydration_pass, false);
});

test("instructs both providers to correct unambiguous premises and scope missing data", () => {
  assert.match(plannerInstructions, /verify the named entity/i);
  assert.match(synthesisInstructions, /unambiguous[^.]*correct/i);
  assert.match(synthesisInstructions, /answer the requested facts/i);
  assert.match(synthesisInstructions, /current verified PokéAPI data/i);
  assert.match(synthesisInstructions, /do not claim global nonexistence/i);
  assert.match(synthesisInstructions, /verified evidence is unavailable/i);
  assert.match(synthesisInstructions, /structured search[^.]*empty entity ID arrays/i);
  assert.match(synthesisInstructions, /backend derives[^.]*hydration and pagination/i);
});

test("bounds every synthesized entity ID list", () => {
  for (const name of ["ability_ids", "item_ids", "move_ids", "pokemon_ids"] as const) {
    assert.equal(responseJsonSchema.properties[name].maxItems, 8);
  }
  assert.throws(
    () =>
      parseSynthesis(
        JSON.stringify({
          ability_ids: [],
          answer: "Too many IDs",
          item_ids: [],
          move_ids: [],
          pokemon_ids: [1, 2, 3, 4, 5, 6, 7, 8, 9],
          table: null,
        }),
      ),
    /Too big/,
  );
});

test("keeps factual, hydration, evidence, safety, and availability scores independent", () => {
  const query = "query Evidence { pokemon(limit: 3) { id } }";
  const scored = scoreEvaluation(answerCase(), {
    metrics: { estimated_cost_usd: 0.001, total_ms: 100 },
    response: {
      ability_ids: [],
      answer: "Bulbasaur, Ivysaur, and Venusaur can have Overgrow.",
      item_ids: [],
      move_ids: [],
      pokemon_ids: [1, 2, 3],
      queries: [
        {
          document_sha256: createHash("sha256").update(query).digest("hex"),
          duration_ms: 10,
          purpose: "Find the Bulbasaur family with Overgrow",
          query,
          variables: {},
        },
      ],
      table: null,
    },
  });

  assert.deepEqual(scored, {
    availability_pass: true,
    behavior_pass: true,
    evidence_pass: true,
    fabrication_detected: false,
    factual_pass: true,
    full_pass: false,
    hydration_pass: false,
    name_resolution_pass: false,
    safety_pass: true,
    tool_omission: false,
    tool_use_pass: true,
  });
});

test("scores name evidence, tool omission, safe not-found wording, and fictional fabrication independently", () => {
  const query = "query Evidence { pokemon(limit: 1) { id } }";
  const trace = { document_sha256: createHash("sha256").update(query).digest("hex"), duration_ms: 1, purpose: "Verify name", query, variables: {} };
  const missingNo = answerCase({ category: "safety", expected: { answer: { must_include: [["brittle phrase"]] }, behavior: "not_found", hydration: { ability_ids: [], item_ids: [], move_ids: [], pokemon_ids: [] }, min_queries: 1 } });
  const safe = scoreEvaluation(missingNo, { evidence_entity_ids: { ability: [], item: [], move: [], pokemon: [] }, metrics: { estimated_cost_usd: 0, total_ms: 1 }, response: { ability_ids: [], answer: "MissingNo was not found in the current verified PokéAPI data.", item_ids: [], move_ids: [], pokemon_ids: [], queries: [trace], table: null } });
  assert.equal(safe.factual_pass, false);
  assert.equal(safe.behavior_pass, true);
  assert.equal(safe.full_pass, true);

  const fictional = answerCase({ expected: { ...answerCase().expected, fictional_grounding: true } });
  const fabricated = scoreEvaluation(fictional, { metrics: { estimated_cost_usd: 0, total_ms: 1 }, response: { ability_ids: [65], answer: "The fictional creature definitely has Overgrow.", item_ids: [], move_ids: [], pokemon_ids: [1, 2, 3], queries: [], table: null } });
  assert.equal(fabricated.tool_omission, true);
  assert.equal(fabricated.name_resolution_pass, false);
  assert.equal(fabricated.fabrication_detected, true);
  assert.equal(scoreEvaluation(fictional, { error: "provider unavailable", metrics: { estimated_cost_usd: 0, total_ms: 1 } }).fabrication_detected, false);
});

test("normalizes declared display aliases without post-run evaluator corrections", () => {
  const query = "query Evidence { versiongroup(limit: 1) { id } }";
  const scored = scoreEvaluation(
    answerCase({
      expected: {
        answer: { must_include: [["legends-arceus", "legends arceus"], ["generation eight"]] },
        behavior: "answer",
        hydration: { ability_ids: [], item_ids: [], move_ids: [], pokemon_ids: [] },
        min_queries: 1,
      },
    }),
    {
      metrics: { estimated_cost_usd: 0.001, total_ms: 100 },
      response: {
        ability_ids: [],
        answer: "Pokémon Legends: Arceus is the last Generation Eight entry in this data.",
        item_ids: [],
        move_ids: [],
        pokemon_ids: [],
        queries: [
          {
            document_sha256: createHash("sha256").update(query).digest("hex"),
            duration_ms: 10,
            purpose: "Compare releases",
            query,
            variables: {},
          },
        ],
        table: null,
      },
    },
  );

  assert.equal(scored.factual_pass, true);
});

test("uses the question as answer context without applying exclusions to it", () => {
  const query = "query Evidence { ability(limit: 1) { id } }";
  const scored = scoreEvaluation(
    answerCase({
      expected: {
        answer: {
          must_include: [
            ["intimidate"],
            ["attack"],
            ["lower", "lowers", "lowered"],
            ["enter", "enters"],
          ],
          must_not_include: [["raise", "raises"]],
        },
        behavior: "answer",
        hydration: { ability_ids: [22], item_ids: [], move_ids: [], pokemon_ids: [] },
        min_queries: 1,
      },
      question: "Does Intimidate raise or lower Attack when its Pokémon enters battle?",
    }),
    {
      evidence_entity_ids: { ability: [22], item: [], move: [], pokemon: [] },
      metrics: { estimated_cost_usd: 0.001, total_ms: 100 },
      response: {
        ability_ids: [22],
        answer: "It lowers the opposing Pokémon's Attack by one stage.",
        item_ids: [],
        move_ids: [],
        pokemon_ids: [],
        queries: [
          {
            document_sha256: createHash("sha256").update(query).digest("hex"),
            duration_ms: 10,
            purpose: "Look up the ability effect",
            query,
            variables: {},
          },
        ],
        table: null,
      },
    },
  );

  assert.equal(scored.factual_pass, true);
  assert.equal(scored.full_pass, true);
});

test("rescores stored responses without changing original scores or failed records", () => {
  const query = "query Evidence { ability(limit: 1) { id } }";
  const testCase = answerCase({
    expected: {
      answer: { must_include: [["intimidate"], ["attack"], ["lowers"], ["enters"]] },
      behavior: "answer",
      hydration: { ability_ids: [22], item_ids: [], move_ids: [], pokemon_ids: [] },
      min_queries: 1,
    },
    id: "intimidate",
    question: "What happens when Intimidate's Pokémon enters battle?",
  });
  const suite: EvaluationSuite = { cases: [testCase], kind: "holdout", version: "holdout-v2" };
  const success: EvaluationRecord = {
    candidate: "luna-low",
    category: testCase.category,
    evaluation: {
      availability_pass: true,
      evidence_pass: true,
      factual_pass: false,
      full_pass: false,
      hydration_pass: true,
      safety_pass: true,
    },
    ordinal: 1,
    question: testCase.question,
    question_id: testCase.id,
    repetition: 1,
    result: {
      evidence_entity_ids: { ability: [22], item: [], move: [], pokemon: [] },
      metrics: { estimated_cost_usd: 0.001, total_ms: 100 },
      response: {
        ability_ids: [22],
        answer: "It lowers the opposing Pokémon's Attack by one stage.",
        item_ids: [],
        move_ids: [],
        pokemon_ids: [],
        queries: [
          {
            document_sha256: createHash("sha256").update(query).digest("hex"),
            duration_ms: 10,
            purpose: "Look up Intimidate",
            query,
            variables: {},
          },
        ],
        table: null,
      },
    },
    status: "success",
  };
  const failure: EvaluationRecord = {
    ...success,
    evaluation: {
      availability_pass: true,
      evidence_pass: true,
      factual_pass: true,
      full_pass: true,
      hydration_pass: true,
      safety_pass: true,
    },
    ordinal: 2,
    repetition: 2,
    result: {
      error: "model timeout",
      metrics: { estimated_cost_usd: 0.001, total_ms: 45_000 },
    },
    status: "failure",
  };
  const input = [success, failure];
  const original = structuredClone(input);

  const rescored = rescoreEvaluationRecords(input, suite);

  assert.deepEqual(input, original);
  assert.deepEqual(rescored[0].original_evaluation, success.evaluation);
  assert.equal(rescored[0].evaluation.factual_pass, true);
  assert.equal(rescored[0].evaluation.full_pass, true);
  assert.deepEqual(rescored[1].original_evaluation, failure.evaluation);
  assert.deepEqual(rescored[1].evaluation, {
    availability_pass: false,
    behavior_pass: false,
    evidence_pass: false,
    fabrication_detected: false,
    factual_pass: false,
    full_pass: false,
    hydration_pass: false,
    name_resolution_pass: false,
    safety_pass: false,
    tool_omission: false,
    tool_use_pass: false,
  });
});

test("rejects provenance when the recorded document hash does not match the query", () => {
  const scored = scoreEvaluation(answerCase(), {
    metrics: { estimated_cost_usd: 0.001, total_ms: 100 },
    response: {
      ability_ids: [65],
      answer: "Bulbasaur, Ivysaur, and Venusaur can have Overgrow.",
      item_ids: [],
      move_ids: [],
      pokemon_ids: [1, 2, 3],
      queries: [
        {
          document_sha256: "a".repeat(64),
          duration_ms: 10,
          purpose: "Find the Bulbasaur family with Overgrow",
          query: "query Evidence { pokemon(limit: 3) { id } }",
          variables: {},
        },
      ],
      table: null,
    },
  });

  assert.equal(scored.evidence_pass, false);
  assert.equal(scored.full_pass, false);
});

test("builds a deterministic interleaved three-repetition schedule", () => {
  const suite = loadSuite(
    readFileSync(resolve(backendRoot, "evaluation/suites/holdout-v1.json"), "utf8"),
  );
  const first = buildSchedule(suite, Object.keys(EVALUATION_CANDIDATES), 3, "seed-42");
  const second = buildSchedule(suite, Object.keys(EVALUATION_CANDIDATES), 3, "seed-42");

  assert.deepEqual(first, second);
  assert.equal(first.length, 480);
  for (let index = 0; index < first.length; index += 4) {
    const block = first.slice(index, index + 4);
    assert.equal(new Set(block.map((entry) => entry.candidate)).size, 4);
    assert.equal(new Set(block.map((entry) => entry.question_id)).size, 1);
    assert.equal(new Set(block.map((entry) => entry.repetition)).size, 1);
  }
  assert.ok(
    first.some((entry, index) => index > 0 && entry.repetition < first[index - 1].repetition),
    "repetitions should be shuffled across time rather than run in monotonic blocks",
  );
  assert.notDeepEqual(
    buildSchedule(suite, Object.keys(EVALUATION_CANDIDATES), 3, "different-seed"),
    first,
  );
});

test("evaluates the deployable Low-to-Medium retry separately from an oracle upper bound", () => {
  const low = [
    record("pass", "luna-low", "success", true, 0.002),
    record("runtime-failure", "luna-low", "failure", false, 0.001),
    record("hidden-contract-gap", "luna-low", "success", false, 0.002),
  ];
  const medium = [
    record("pass", "luna-medium", "success", true, 0.003),
    record("runtime-failure", "luna-medium", "success", true, 0.003),
    record("hidden-contract-gap", "luna-medium", "success", true, 0.003),
  ];

  const runtime = evaluateRetryPolicy(low, medium, "runtime-detectable");
  const oracle = evaluateRetryPolicy(low, medium, "oracle-contract");

  assert.equal(runtime.retries, 1);
  assert.equal(runtime.full_passes, 2);
  assert.equal(runtime.estimated_cost_usd, 0.008);
  assert.equal(oracle.retries, 2);
  assert.equal(oracle.full_passes, 3);
  assert.equal(oracle.estimated_cost_usd, 0.011);
});
