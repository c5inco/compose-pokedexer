import assert from "node:assert/strict";
import { mkdtempSync, readFileSync, rmSync, writeFileSync } from "node:fs";
import { tmpdir } from "node:os";
import { resolve } from "node:path";
import test from "node:test";

import { buildClientSchema, type IntrospectionQuery } from "graphql";

import {
  LIVE_CONSISTENCY_PROBES,
  loadCompletedRecords,
  parseRunOptions,
  summarizeRecords,
} from "../evaluation/run.js";
import type { EvaluationRecord } from "../evaluation/types.js";
import { createReadonlyGraphqlExecutor } from "../src/readonly-graphql.js";

function successfulRecord(candidate: string, fullPass: boolean, cost: number): EvaluationRecord {
  return {
    candidate,
    category: "facts",
    evaluation: {
      availability_pass: true,
      evidence_pass: fullPass,
      factual_pass: fullPass,
      full_pass: fullPass,
      hydration_pass: fullPass,
      safety_pass: true,
    },
    ordinal: 1,
    question: "What type is Bulbasaur?",
    question_id: "facts-bulbasaur",
    repetition: 1,
    result: {
      metrics: { cost_complete: true, estimated_cost_usd: cost, total_ms: 1_000 },
      response: {
        ability_ids: [],
        answer: "Bulbasaur is Grass and Poison type.",
        item_ids: [],
        move_ids: [],
        pokemon_ids: [1],
        queries: [],
        table: null,
      },
    },
    status: "success",
  };
}

test("defaults to validation and requires an unmistakable paid-run confirmation", () => {
  assert.deepEqual(parseRunOptions(["--suite", "holdout"]), {
    candidates: undefined,
    executePaid: false,
    outputDirectory: undefined,
    questionIds: undefined,
    repetitions: 3,
    seed: "ask-pokedexer-eval-v4-seed",
    suite: "holdout",
  });
  assert.throws(
    () => parseRunOptions(["--suite", "holdout", "--execute-paid"]),
    /--confirm-cost RUN_PAID_EVALUATION/,
  );
  assert.deepEqual(parseRunOptions(["--suite", "search"]), {
    candidates: undefined,
    executePaid: false,
    outputDirectory: undefined,
    questionIds: undefined,
    repetitions: 3,
    seed: "ask-pokedexer-eval-v4-seed",
    suite: "search",
  });
  assert.throws(
    () => parseRunOptions(["--suite", "search", "--execute-paid"]),
    /--confirm-cost RUN_PAID_EVALUATION/,
  );
  assert.deepEqual(
    parseRunOptions([
      "--suite",
      "search",
      "--execute-paid",
      "--confirm-cost",
      "RUN_PAID_EVALUATION",
      "--output",
      "/tmp/search-evaluation",
    ]),
    {
      candidates: undefined,
      executePaid: true,
      outputDirectory: "/tmp/search-evaluation",
      questionIds: undefined,
      repetitions: 3,
      seed: "ask-pokedexer-eval-v4-seed",
      suite: "search",
    },
  );
  assert.deepEqual(
    parseRunOptions([
      "--suite",
      "canary",
      "--candidate",
      "gemini-3.7-flash",
      "--question-id",
      "canary-cobalt-sprig",
      "--question-id",
      "canary-echo-mend",
    ]),
    {
      candidates: ["gemini-3.7-flash"],
      executePaid: false,
      outputDirectory: undefined,
      questionIds: ["canary-cobalt-sprig", "canary-echo-mend"],
      repetitions: 3,
      seed: "ask-pokedexer-eval-v4-seed",
      suite: "canary",
    },
  );
  assert.deepEqual(
    parseRunOptions(["--suite", "canary", "--candidate", "glm-5.3-flash"]).candidates,
    ["glm-5.3-flash"],
  );
  assert.throws(
    () => parseRunOptions(["--suite", "canary", "--candidate", "unknown-model"]),
    /Unknown evaluation candidate/,
  );
  assert.throws(
    () =>
      parseRunOptions([
        "--suite",
        "holdout",
        "--execute-paid",
        "--confirm-cost",
        "RUN_PAID_EVALUATION",
      ]),
    /--output/,
  );
});

test("loads resumable JSONL records and rejects duplicate schedule keys", () => {
  const directory = mkdtempSync(resolve(tmpdir(), "ask-eval-runner-"));
  try {
    const path = resolve(directory, "records.jsonl");
    const record = successfulRecord("luna-low", true, 0.001);
    writeFileSync(path, `${JSON.stringify(record)}\n`, "utf8");
    assert.equal(loadCompletedRecords(path).size, 1);

    writeFileSync(path, `${JSON.stringify(record)}\n${JSON.stringify(record)}\n`, "utf8");
    assert.throws(() => loadCompletedRecords(path), /Duplicate evaluation record/);
  } finally {
    rmSync(directory, { force: true, recursive: true });
  }
});

test("produces standalone summaries for every candidate", () => {
  const firstLuna = successfulRecord("luna-low", true, 0.001);
  firstLuna.result.metrics.tool_argument_normalizations = {
    calls: 1,
    kinds: { non_string_value_json: 0, variables_object_map: 1 },
  };
  const secondLuna = successfulRecord("luna-low", false, 0.002);
  secondLuna.result.metrics.tool_argument_normalizations = {
    calls: 2,
    kinds: { non_string_value_json: 2, variables_object_map: 0 },
  };
  const records = [
    firstLuna,
    { ...secondLuna, ordinal: 2, repetition: 2 },
    successfulRecord("gemini-3.7-flash", true, 0.003),
  ];

  const summary = summarizeRecords(records);

  assert.deepEqual(Object.keys(summary.candidates), ["gemini-3.7-flash", "luna-low"]);
  assert.equal(summary.candidates["luna-low"].full_passes, 1);
  assert.equal(summary.candidates["luna-low"].total, 2);
  assert.equal(summary.candidates["luna-low"].estimated_cost_usd, 0.003);
  assert.deepEqual(summary.candidates["luna-low"].tool_argument_normalizations, {
    calls: 3,
    kinds: { non_string_value_json: 2, variables_object_map: 1 },
  });
  assert.equal(summary.candidates["luna-low"].categories.facts.total, 2);
  assert.equal(summary.candidates["luna-low"].categories.facts.full_pass_rate, 0.5);
  assert.equal(summary.candidates["gemini-3.7-flash"].full_pass_rate, 1);
  assert.doesNotThrow(() => JSON.parse(JSON.stringify(summary)));
  assert.equal(readFileSync(resolve(import.meta.dirname, "../evaluation/suites/holdout-v1.json"), "utf8").length > 0, true);
});

test("reports provider, PokéAPI, model, and evaluator failures separately", () => {
  const base = successfulRecord("gemini-3.7-flash", false, 0);
  const records = (["provider", "pokeapi", "model", "evaluator"] as const).map(
    (failureClass, index): EvaluationRecord => ({
      ...base,
      evaluation: {
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
      },
      ordinal: index + 1,
      question_id: `failure-${failureClass}`,
      repetition: index + 1,
      result: {
        error: `${failureClass} failure`,
        failure_class: failureClass,
        metrics: { estimated_cost_usd: 0, total_ms: 10 },
      },
      status: "failure",
    }),
  );

  assert.deepEqual(
    summarizeRecords(records).candidates["gemini-3.7-flash"].failures_by_class,
    { evaluator: 1, model: 1, pokeapi: 1, provider: 1 },
  );
});

test("keeps every live consistency probe valid under the pinned PokéAPI schema", async () => {
  const schemaSource = readFileSync(
    resolve(
      import.meta.dirname,
      "../../shared/src/commonMain/graphql/des.c5inco.pokedexer.shared/schema.json",
    ),
    "utf8",
  );
  const schema = buildClientSchema(
    (JSON.parse(schemaSource) as { data: IntrospectionQuery }).data,
  );
  const executor = createReadonlyGraphqlExecutor({
    endpoint: "https://graphql.pokeapi.co/v1beta2",
    fetchImpl: async () => new Response(JSON.stringify({ data: {} }), { status: 200 }),
    schema,
  });

  for (const probe of LIVE_CONSISTENCY_PROBES) {
    await assert.doesNotReject(executor.execute(probe.request), probe.id);
  }
});
