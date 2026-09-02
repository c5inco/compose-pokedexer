import { z } from "zod";

import type { EvaluationSuite } from "./types.js";
import type { SearchEvaluationTestCase } from "./search-scorer.js";

const hydrationSchema = z.object({
  ability_ids: z.array(z.number().int().positive()),
  item_ids: z.array(z.number().int().positive()),
  max_extra_ids: z.number().int().min(0).max(6).optional(),
  move_ids: z.array(z.number().int().positive()),
  pokemon_ids: z.array(z.number().int().positive()),
});

const aliasesSchema = z.array(z.array(z.string().min(1)).min(1));

const testCaseSchema = z.object({
  category: z.enum(["facts", "relationships", "difficult", "safety"]),
  expected: z.object({
    answer: z.object({
      must_include: aliasesSchema,
      must_not_include: aliasesSchema.optional(),
    }),
    behavior: z.enum(["answer", "not_found", "refusal"]),
    fictional_grounding: z.boolean().optional(),
    hydration: hydrationSchema,
    min_queries: z.number().int().min(0).max(10),
  }),
  id: z.string().regex(/^[a-z0-9-]+$/),
  question: z.string().trim().min(4).max(500),
});

const suiteSchema = z.object({
  cases: z.array(testCaseSchema),
  kind: z.enum(["holdout", "canary"]),
  score_version: z
    .enum(["phrase-alias-v1", "semantic-alias-v2"])
    .default("phrase-alias-v1"),
  version: z.string().regex(/^[a-z0-9-]+$/),
});

const legacySearchCaseSchema = z.object({
  category: z.enum(["ambiguity", "combined", "no-match", "pagination", "physical", "subjective"]),
  expected: z.object({
    behavior: z.enum(["answer", "insufficient_evidence", "needs_clarification", "not_found", "requires_enrichment", "scoped_no_match"]),
    evidence_coverage: z.boolean(),
    forbidden_clear_nonmatches: z.array(z.number().int().positive()),
    interpretation_disclosure: z.array(z.string().min(1)),
    min_strong_matches_on_top_page: z.number().int().min(0).max(8),
    minimum_successful_queries: z.number().int().min(0).max(10).optional(),
    pagination: z.object({
      min_unique_results: z.number().int().min(9).max(1_000),
      required: z.boolean(),
    }).optional(),
    strong_match_ids: z.array(z.number().int().positive()),
    top_page_size: z.literal(8),
  }),
  id: z.string().regex(/^[a-z0-9-]+$/),
  question: z.string().trim().min(4).max(500),
});

const legacySearchSuiteSchema = z.object({
  cases: z.array(legacySearchCaseSchema).min(20),
  kind: z.literal("search"),
  version: z.literal("search-v1"),
});

const constraintSchema = z.union([
  z.object({
    field: z.enum(["color", "shape", "type"]),
    operator: z.literal("eq"),
    value: z.string().min(1),
  }),
  z.object({
    field: z.enum(["height", "weight"]),
    operator: z.enum(["gte", "lte"]),
    unit: z.enum(["decimetres", "hectograms"]),
    value: z.number().int().positive(),
  }),
]);

const searchCaseSchema = z.object({
  category: z.enum(["ambiguity", "combined", "no-match", "pagination", "physical", "subjective"]),
  expected: z.object({
    behavior: z.enum(["answer", "insufficient_evidence", "needs_clarification", "not_found", "requires_enrichment", "scoped_no_match"]),
    canonical_pokemon_ids: z.array(z.number().int().positive()).max(100),
    evidence_coverage: z.boolean(),
    interpretation: z.object({
      ambiguous_terms: z.array(z.string().min(1)),
      conflicts: z.array(z.object({
        field: z.enum(["color", "shape", "type", "height", "weight"]),
        terms: z.array(z.string().min(1)).min(2),
      })),
      constraints: z.array(constraintSchema),
      status: z.enum(["needs_clarification", "requires_enrichment", "structured"]),
      unsupported_terms: z.array(z.string().min(1)),
    }),
    minimum_successful_queries: z.number().int().min(0).max(10).optional(),
    pagination: z.object({
      min_unique_results: z.number().int().min(9).max(100),
      required: z.boolean(),
    }).optional(),
    top_page_size: z.literal(8),
  }),
  id: z.string().regex(/^[a-z0-9-]+$/),
  question: z.string().trim().min(4).max(500),
});

const searchSuiteSchema = z.object({
  canonical_snapshot: z.object({
    endpoint: z.literal("https://graphql.pokeapi.co/v1beta2"),
    max_results_per_case: z.literal(100),
    schema_sha256: z.string().regex(/^[a-f0-9]{64}$/),
  }),
  cases: z.array(searchCaseSchema).length(20),
  kind: z.literal("search"),
  score_version: z.literal("canonical-predicate-v2"),
  version: z.enum(["search-v2", "search-v3"]),
});

export interface SearchEvaluationSuite {
  cases: SearchEvaluationTestCase[];
  canonical_snapshot: {
    endpoint: "https://graphql.pokeapi.co/v1beta2";
    max_results_per_case: 100;
    schema_sha256: string;
  };
  kind: "search";
  score_version: "canonical-predicate-v2";
  version: "search-v2" | "search-v3";
}

function assertUnique(values: string[], label: string): void {
  if (new Set(values).size !== values.length) throw new Error(`${label} must be unique`);
}

export function loadSuite(source: string): EvaluationSuite {
  const suite = suiteSchema.parse(JSON.parse(source)) as EvaluationSuite;
  assertUnique(
    suite.cases.map((item) => item.id),
    "Suite case IDs",
  );
  assertUnique(
    suite.cases.map((item) => item.question.toLocaleLowerCase()),
    "Suite questions",
  );
  if (suite.kind === "holdout") {
    if (suite.cases.length !== 40) throw new Error("The locked holdout must contain 40 cases");
    for (const category of ["facts", "relationships", "difficult", "safety"] as const) {
      if (suite.cases.filter((item) => item.category === category).length !== 10) {
        throw new Error(`The locked holdout must contain 10 ${category} cases`);
      }
    }
  }
  if (suite.kind === "canary" && suite.cases.length !== 6) {
    throw new Error("The grounding canary suite must contain 6 cases");
  }
  return suite;
}

export function loadSearchSuite(source: string): SearchEvaluationSuite {
  const suite = searchSuiteSchema.parse(JSON.parse(source)) as SearchEvaluationSuite;
  assertUnique(suite.cases.map((item) => item.id), "Search suite case IDs");
  assertUnique(
    suite.cases.map((item) => item.question.toLocaleLowerCase()),
    "Search suite questions",
  );
  for (const item of suite.cases) {
    const ids = item.expected.canonical_pokemon_ids;
    if (ids.some((id, index) => index > 0 && ids[index - 1] >= id)) {
      throw new Error(`Canonical Pokémon IDs for ${item.id} must be unique and strictly ascending`);
    }
    if (item.expected.behavior === "answer" && ids.length === 0) {
      throw new Error(`Answer case ${item.id} requires canonical Pokémon IDs`);
    }
    if (item.expected.behavior !== "answer" && ids.length > 0) {
      throw new Error(`Non-answer case ${item.id} cannot declare canonical Pokémon IDs`);
    }
  }
  return suite;
}

export function loadLegacySearchSuite(source: string) {
  const suite = legacySearchSuiteSchema.parse(JSON.parse(source));
  assertUnique(suite.cases.map((item) => item.id), "Legacy search suite case IDs");
  assertUnique(
    suite.cases.map((item) => item.question.toLocaleLowerCase()),
    "Legacy search suite questions",
  );
  return suite;
}

function seededRandom(seed: string): () => number {
  let value = 2166136261;
  for (const character of seed) {
    value ^= character.codePointAt(0) ?? 0;
    value = Math.imul(value, 16777619);
  }
  return () => {
    value += 0x6d2b79f5;
    let result = value;
    result = Math.imul(result ^ (result >>> 15), result | 1);
    result ^= result + Math.imul(result ^ (result >>> 7), result | 61);
    return ((result ^ (result >>> 14)) >>> 0) / 4_294_967_296;
  };
}

function shuffled<T>(values: T[], random: () => number): T[] {
  const result = [...values];
  for (let index = result.length - 1; index > 0; index -= 1) {
    const replacement = Math.floor(random() * (index + 1));
    [result[index], result[replacement]] = [result[replacement], result[index]];
  }
  return result;
}

export function buildSchedule(
  suite: { cases: Array<{ id: string }> },
  candidates: string[],
  repetitions: number,
  seed: string,
) {
  if (!Number.isInteger(repetitions) || repetitions < 1 || repetitions > 10) {
    throw new Error("Repetitions must be between 1 and 10");
  }
  assertUnique(candidates, "Candidate IDs");
  const random = seededRandom(seed);
  const schedule: Array<{ candidate: string; question_id: string; repetition: number }> = [];
  const slots = shuffled(
    Array.from({ length: repetitions }, (_, index) =>
      suite.cases.map((item) => ({ question_id: item.id, repetition: index + 1 })),
    ).flat(),
    random,
  );
  for (const slot of slots) {
    for (const candidate of shuffled(candidates, random)) {
      schedule.push({ candidate, ...slot });
    }
  }
  return schedule;
}
