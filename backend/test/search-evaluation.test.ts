import assert from "node:assert/strict";
import { readFileSync } from "node:fs";
import { resolve } from "node:path";
import test from "node:test";

import {
  EVALUATION_CURSOR_SECRET,
  parseSearchRescoreOptions,
  rescoreSearchRecords,
  type StoredSearchRecord,
} from "../evaluation/search-rescore.js";
import { scoreSearchEvaluation, summarizeSearchRecords } from "../evaluation/search-scorer.js";
import {
  loadLegacySearchSuite,
  loadSearchSuite,
  type SearchEvaluationSuite,
} from "../evaluation/suite.js";
import { createPaginationService } from "../src/pagination.js";

const backendRoot = resolve(import.meta.dirname, "..");

test("keeps search rescoring offline with explicit input and output paths", () => {
  assert.deepEqual(
    parseSearchRescoreOptions(["--input", "/tmp/raw.jsonl", "--output", "/tmp/rescored"]),
    { input: "/tmp/raw.jsonl", output: "/tmp/rescored" },
  );
  assert.throws(() => parseSearchRescoreOptions([]), /--input and --output are required/);
  assert.throws(
    () => parseSearchRescoreOptions(["--input", "/tmp/raw.jsonl", "--output", "/tmp/out", "--execute-paid"]),
    /Unknown argument/,
  );
});

test("preserves historical search suites and loads the unseen canonical search-v3 rubric", () => {
  const legacy = loadLegacySearchSuite(
    readFileSync(resolve(backendRoot, "evaluation/suites/search-v1.json"), "utf8"),
  );
  const suite = loadSearchSuite(
    readFileSync(resolve(backendRoot, "evaluation/suites/search-v2.json"), "utf8"),
  );
  const unseen = loadSearchSuite(
    readFileSync(resolve(backendRoot, "evaluation/suites/search-v3.json"), "utf8"),
  );

  assert.equal(legacy.version, "search-v1");
  assert.equal(suite.kind, "search");
  assert.equal(suite.version, "search-v2");
  assert.equal(suite.score_version, "canonical-predicate-v2");
  assert.equal(suite.cases.length, 20);
  assert.equal(unseen.version, "search-v3");
  assert.equal(unseen.score_version, "canonical-predicate-v2");
  assert.equal(unseen.cases.length, 20);
  assert.deepEqual(
    Object.fromEntries(
      ["ambiguity", "combined", "no-match", "pagination", "physical", "subjective"].map(
        (category) => [category, unseen.cases.filter((item) => item.category === category).length],
      ),
    ),
    { ambiguity: 3, combined: 5, "no-match": 2, pagination: 3, physical: 4, subjective: 3 },
  );
  assert.deepEqual(
    [...new Set(suite.cases.map((item) => item.category))].sort(),
    ["ambiguity", "combined", "no-match", "pagination", "physical", "subjective"],
  );
  for (const item of suite.cases) {
    assert.doesNotMatch(item.question, /reviewed structured threshold|reviewed weight threshold|current PokéAPI data/i);
  }

  const cases = new Map(suite.cases.map((item) => [item.id, item]));
  assert.deepEqual(cases.get("combined-round-water")?.expected.canonical_pokemon_ids, [90, 91, 366]);
  assert.deepEqual(
    cases.get("combined-heavy-rock")?.expected.canonical_pokemon_ids.slice(0, 8),
    [75, 76, 95, 111, 112, 247, 248, 305],
  );
  assert.deepEqual(
    cases.get("physical-short")?.expected.canonical_pokemon_ids.slice(0, 8),
    [1, 2, 4, 7, 8, 10, 11, 13],
  );
  assert.deepEqual(cases.get("physical-heavy")?.expected.interpretation.constraints, [
    { field: "weight", operator: "gte", unit: "hectograms", value: 1_000 },
  ]);
  assert.equal(
    cases.get("no-match-insufficient-evidence")?.question,
    "Are there any round, purple Dragon-type Pokémon?",
  );
});

test("scores canonical predicates and machine-readable interpretations instead of answer phrasing", () => {
  const score = scoreSearchEvaluation(
    {
      category: "combined",
      expected: {
        behavior: "answer",
        canonical_pokemon_ids: [75, 76, 95, 111, 112, 247, 248, 305, 306],
        evidence_coverage: true,
        interpretation: {
          ambiguous_terms: [],
          conflicts: [],
          constraints: [
            { field: "weight", operator: "gte", unit: "hectograms", value: 1_000 },
            { field: "type", operator: "eq", value: "rock" },
          ],
          status: "structured",
          unsupported_terms: [],
        },
        top_page_size: 8,
      },
      id: "combined-heavy-rock",
      question: "Find heavy Rock-type Pokémon.",
    },
    {
      evidence_entity_ids: {
        ability: [],
        item: [],
        move: [],
        pokemon: [75, 76, 95, 111, 112, 247, 248, 305, 306],
      },
      interpretation: {
        ambiguous_terms: [],
        conflicts: [],
        constraints: [
          { field: "weight", operator: "gte", unit: "hectograms", value: 1_000 },
          { field: "type", operator: "eq", value: "rock" },
        ],
        interpretations: [],
        status: "structured",
        unsupported_terms: [],
      },
      response: {
        ability_ids: [],
        answer: "These weigh ≥1,000 hg (100 kg).",
        item_ids: [],
        move_ids: [],
        pokemon_ids: [75, 76, 95, 111, 112, 247, 248, 305],
      },
      successful_evidence_queries: 1,
    },
  );

  assert.equal(score.interpretation_pass, true);
  assert.equal(score.relevance_pass, true);
  assert.equal(score.full_pass, true);
});

test("requires the canonical first page rather than hand-picked example matches", () => {
  const testCase = {
    category: "physical" as const,
    expected: {
      behavior: "answer" as const,
      canonical_pokemon_ids: [1, 2, 4, 7, 8, 10, 11, 13, 14, 15, 16],
      evidence_coverage: true,
      interpretation: {
        ambiguous_terms: [],
        conflicts: [],
        constraints: [
          { field: "height" as const, operator: "lte" as const, unit: "decimetres" as const, value: 10 },
        ],
        status: "structured" as const,
        unsupported_terms: [],
      },
      top_page_size: 8 as const,
    },
    id: "physical-short",
    question: "Which Pokémon are short?",
  };
  const fixture = (pokemonIds: number[]) => ({
    evidence_entity_ids: { ability: [], item: [], move: [], pokemon: pokemonIds },
    interpretation: {
      ambiguous_terms: [],
      conflicts: [],
      constraints: [
        { field: "height" as const, operator: "lte" as const, unit: "decimetres" as const, value: 10 },
      ],
      interpretations: [],
      status: "structured" as const,
      unsupported_terms: [],
    },
    response: {
      ability_ids: [],
      answer: "Short Pokémon.",
      item_ids: [],
      move_ids: [],
      pokemon_ids: pokemonIds,
    },
    successful_evidence_queries: 1,
  });

  assert.equal(
    scoreSearchEvaluation(testCase, fixture([1, 2, 4, 7, 8, 10, 11, 13])).relevance_pass,
    true,
  );
  assert.equal(
    scoreSearchEvaluation(testCase, fixture([10, 13, 16, 19, 21, 50])).relevance_pass,
    false,
  );
});

test("scores enrichment behavior without inventing result entities", () => {
  const score = scoreSearchEvaluation(
    {
      category: "subjective",
      expected: {
        behavior: "requires_enrichment",
        canonical_pokemon_ids: [],
        evidence_coverage: true,
        interpretation: {
          ambiguous_terms: [],
          conflicts: [],
          constraints: [],
          status: "requires_enrichment",
          unsupported_terms: ["cute"],
        },
        top_page_size: 8,
      },
      id: "subjective-cute",
      question: "Show me cute Pokémon",
    },
    {
      evidence_entity_ids: { ability: [], item: [], move: [], pokemon: [] },
      interpretation: {
        ambiguous_terms: [],
        conflicts: [],
        constraints: [],
        interpretations: [],
        status: "requires_enrichment",
        unsupported_terms: [{ reason: "cute requires visual enrichment", term: "cute" }],
      },
      response: {
        ability_ids: [],
        answer: "Cute is subjective, so visual enrichment is required.",
        item_ids: [],
        move_ids: [],
        pokemon_ids: [],
      },
    },
  );

  assert.deepEqual(score, {
    behavior_pass: true,
    evidence_coverage_pass: true,
    full_pass: true,
    interpretation_pass: true,
    pagination_pass: true,
    relevance_pass: true,
  });
});

test("rejects noncanonical results and returned entities without evidence", () => {
  const score = scoreSearchEvaluation(
    {
      category: "combined",
      expected: {
        behavior: "answer",
        canonical_pokemon_ids: [100, 101],
        evidence_coverage: true,
        interpretation: {
          ambiguous_terms: [],
          conflicts: [],
          constraints: [{ field: "shape", operator: "eq", value: "ball" }],
          status: "structured",
          unsupported_terms: [],
        },
        top_page_size: 8,
      },
      id: "combined-round-water",
      question: "Round Water Pokémon",
    },
    {
      evidence_entity_ids: { ability: [], item: [], move: [], pokemon: [100] },
      interpretation: {
        ambiguous_terms: [],
        conflicts: [],
        constraints: [{ field: "shape", operator: "eq", value: "ball" }],
        interpretations: [],
        status: "structured",
        unsupported_terms: [],
      },
      response: {
        ability_ids: [],
        answer: "Results",
        item_ids: [],
        move_ids: [],
        pokemon_ids: [100, 99],
      },
      successful_evidence_queries: 1,
    },
  );

  assert.equal(score.relevance_pass, false);
  assert.equal(score.evidence_coverage_pass, false);
  assert.equal(score.full_pass, false);
});

test("scores stable broad continuation pages independently from relevance", () => {
  const score = scoreSearchEvaluation(
    {
      category: "pagination",
      expected: {
        behavior: "answer",
        canonical_pokemon_ids: [7, 8, 9, 54, 55, 60, 61, 62, 72, 73],
        evidence_coverage: true,
        interpretation: {
          ambiguous_terms: [],
          conflicts: [],
          constraints: [{ field: "type", operator: "eq", value: "water" }],
          status: "structured",
          unsupported_terms: [],
        },
        pagination: { min_unique_results: 10, required: true },
        top_page_size: 8,
      },
      id: "pagination-water",
      question: "Show Water Pokémon",
    },
    {
      evidence_entity_ids: {
        ability: [],
        item: [],
        move: [],
        pokemon: [7, 8, 9, 54, 55, 60, 61, 62, 72, 73],
      },
      interpretation: {
        ambiguous_terms: [],
        conflicts: [],
        constraints: [{ field: "type", operator: "eq", value: "water" }],
        interpretations: [],
        status: "structured",
        unsupported_terms: [],
      },
      pages: [
        {
          pagination: { continuation_cursor: "signed-1", has_more: true, page_size: 8, scope: "verified_entity_ids" },
          pokemon_ids: [7, 8, 9, 54, 55, 60, 61, 62],
        },
        {
          pagination: { continuation_cursor: null, has_more: false, page_size: 8, scope: "verified_entity_ids" },
          pokemon_ids: [72, 73],
        },
      ],
      response: {
        ability_ids: [],
        answer: "Water type results",
        item_ids: [],
        move_ids: [],
        pokemon_ids: [7, 8, 9, 54, 55, 60, 61, 62],
      },
      successful_evidence_queries: 1,
    },
  );

  assert.equal(score.pagination_pass, true);
  assert.equal(score.full_pass, true);
});

test("reports search scores separately by candidate and search category", () => {
  const passing = {
    behavior_pass: true,
    evidence_coverage_pass: true,
    full_pass: true,
    interpretation_pass: true,
    pagination_pass: true,
    relevance_pass: true,
  };
  const summary = summarizeSearchRecords([
    { candidate: "candidate-a", category: "pagination", score: passing },
    {
      candidate: "candidate-a",
      category: "subjective",
      score: { ...passing, full_pass: false, relevance_pass: false },
    },
    { candidate: "candidate-b", category: "subjective", score: passing },
  ]);

  assert.equal(summary.kind, "search");
  assert.deepEqual(summary.candidates["candidate-a"], {
    categories: { pagination: { full_passes: 1, total: 1 }, subjective: { full_passes: 0, total: 1 } },
    full_passes: 1,
    total: 2,
  });
  assert.equal(summary.candidates["candidate-b"].full_passes, 1);
});

test("does not pass a definitive scoped no-match without successful evidence", () => {
  const score = scoreSearchEvaluation(
    {
      category: "no-match",
      expected: {
        behavior: "not_found",
        canonical_pokemon_ids: [],
        evidence_coverage: true,
        interpretation: {
          ambiguous_terms: [],
          conflicts: [],
          constraints: [{ field: "shape", operator: "eq", value: "ball" }],
          status: "structured",
          unsupported_terms: [],
        },
        minimum_successful_queries: 1,
        top_page_size: 8,
      },
      id: "scoped-no-match",
      question: "Find an impossible combination",
    },
    {
      behavior: "not_found",
      evidence_entity_ids: { ability: [], item: [], move: [], pokemon: [] },
      interpretation: {
        ambiguous_terms: [],
        conflicts: [],
        constraints: [{ field: "shape", operator: "eq", value: "ball" }],
        interpretations: [],
        status: "structured",
        unsupported_terms: [],
      },
      response: {
        ability_ids: [],
        answer: "No match in current verified PokéAPI data.",
        item_ids: [],
        move_ids: [],
        pokemon_ids: [],
      },
      successful_evidence_queries: 0,
    },
  );

  assert.equal(score.behavior_pass, true);
  assert.equal(score.evidence_coverage_pass, false);
  assert.equal(score.full_pass, false);
});

test("accepts insufficient-evidence behavior for a scoped no-match only when no lookup succeeded", () => {
  const score = scoreSearchEvaluation(
    {
      category: "no-match",
      expected: {
        behavior: "scoped_no_match",
        canonical_pokemon_ids: [],
        evidence_coverage: true,
        interpretation: {
          ambiguous_terms: [],
          conflicts: [],
          constraints: [{ field: "shape", operator: "eq", value: "ball" }],
          status: "structured",
          unsupported_terms: [],
        },
        minimum_successful_queries: 1,
        top_page_size: 8,
      },
      id: "conditional-no-match",
      question: "Are there any matching Pokémon?",
    },
    {
      behavior: "insufficient_evidence",
      evidence_entity_ids: { ability: [], item: [], move: [], pokemon: [] },
      interpretation: {
        ambiguous_terms: [],
        conflicts: [],
        constraints: [{ field: "shape", operator: "eq", value: "ball" }],
        interpretations: [],
        status: "structured",
        unsupported_terms: [],
      },
      response: {
        ability_ids: [],
        answer: "I cannot claim a definitive no-match without verified evidence.",
        item_ids: [],
        move_ids: [],
        pokemon_ids: [],
      },
      successful_evidence_queries: 0,
    },
  );

  assert.equal(score.behavior_pass, true);
  assert.equal(score.evidence_coverage_pass, true);
  assert.equal(score.full_pass, true);
});

test("does not treat a structured interpretation as proof that an expected answer was returned", () => {
  const score = scoreSearchEvaluation(
    {
      category: "combined",
      expected: {
        behavior: "answer",
        canonical_pokemon_ids: [100],
        evidence_coverage: true,
        interpretation: {
          ambiguous_terms: [],
          conflicts: [],
          constraints: [{ field: "shape", operator: "eq", value: "ball" }],
          status: "structured",
          unsupported_terms: [],
        },
        top_page_size: 8,
      },
      id: "expected-answer",
      question: "Find round Pokémon",
    },
    {
      behavior: "not_found",
      evidence_entity_ids: { ability: [], item: [], move: [], pokemon: [] },
      interpretation: {
        ambiguous_terms: [],
        conflicts: [],
        constraints: [{ field: "shape", operator: "eq", value: "ball" }],
        interpretations: [],
        status: "structured",
        unsupported_terms: [],
      },
      response: {
        ability_ids: [],
        answer: "No matches.",
        item_ids: [],
        move_ids: [],
        pokemon_ids: [],
      },
      successful_evidence_queries: 1,
    },
  );

  assert.equal(score.behavior_pass, false);
  assert.equal(score.full_pass, false);
});

test("rescores preserved search responses and signed continuation pages without provider calls", () => {
  const interpretation = {
    ambiguous_terms: [],
    conflicts: [],
    constraints: [{ field: "type" as const, operator: "eq" as const, value: "water" }],
    interpretations: [],
    status: "structured" as const,
    unsupported_terms: [],
  };
  const suite: SearchEvaluationSuite = {
    canonical_snapshot: {
      endpoint: "https://graphql.pokeapi.co/v1beta2",
      max_results_per_case: 100,
      schema_sha256: "a".repeat(64),
    },
    cases: [
      {
        category: "pagination",
        expected: {
          behavior: "answer",
          canonical_pokemon_ids: [7, 8, 9, 54, 55, 60, 61, 62, 72, 73],
          evidence_coverage: true,
          interpretation: {
            ambiguous_terms: [],
            conflicts: [],
            constraints: [{ field: "type", operator: "eq", value: "water" }],
            status: "structured",
            unsupported_terms: [],
          },
          pagination: { min_unique_results: 10, required: true },
          top_page_size: 8,
        },
        id: "water",
        question: "Show me Water-type Pokémon.",
      },
    ],
    kind: "search",
    score_version: "canonical-predicate-v2",
    version: "search-v2",
  };
  const pagination = createPaginationService({
    now: () => 1_000,
    secret: EVALUATION_CURSOR_SECRET,
    ttlMs: 60_000,
  });
  const first = pagination.firstPage("Show me Water-type Pokémon.", {
    ability: [],
    item: [],
    move: [],
    pokemon: [7, 8, 9, 54, 55, 60, 61, 62, 72, 73],
  });
  const originalScore = {
    behavior_pass: false,
    evidence_coverage_pass: false,
    full_pass: false,
    interpretation_pass: false,
    pagination_pass: false,
    relevance_pass: false,
  };
  const records: StoredSearchRecord[] = [
    {
      candidate: "candidate-a",
      category: "pagination",
      ordinal: 1,
      question: "Show me Water-type Pokémon.",
      question_id: "water",
      repetition: 1,
      result: {
        diagnostics: { tool_errors: [] },
        evidence_entity_ids: {
          ability: [],
          item: [],
          move: [],
          pokemon: [7, 8, 9, 54, 55, 60, 61, 62, 72, 73],
        },
        metrics: { graphql_calls: 1 },
        response: {
          ability_ids: [],
          answer: "Water Pokémon.",
          interpretation,
          item_ids: [],
          move_ids: [],
          pagination: first.pagination,
          pokemon_ids: first.pokemon_ids,
        },
      },
      score: originalScore,
      status: "success",
    },
    {
      candidate: "candidate-a",
      category: "pagination",
      ordinal: 2,
      question: "Show me Water-type Pokémon.",
      question_id: "water",
      repetition: 2,
      result: { error: "provider unavailable", metrics: { graphql_calls: 0 } },
      score: originalScore,
      status: "failure",
    },
  ];

  const rescored = rescoreSearchRecords(records, suite);

  assert.equal(rescored[0].score.full_pass, true);
  assert.equal(rescored[0].score.pagination_pass, true);
  assert.deepEqual(rescored[0].original_score, originalScore);
  assert.equal(rescored[0].score_version, "canonical-predicate-v2");
  assert.equal(rescored[1].score.full_pass, false);
  assert.deepEqual(records[0].score, originalScore);
});
