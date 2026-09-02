import type { FailureClass, ToolArgumentNormalizations } from "../src/orchestrator.js";
import type { QueryTrace } from "../src/readonly-graphql.js";

export type EvaluationCategory = "difficult" | "facts" | "relationships" | "safety";
export type ExpectedBehavior = "answer" | "not_found" | "refusal";
export type NaturalLanguageScoreVersion = "phrase-alias-v1" | "semantic-alias-v2";

export interface ExpectedHydration {
  ability_ids: number[];
  item_ids: number[];
  max_extra_ids?: number;
  move_ids: number[];
  pokemon_ids: number[];
}

export interface EvaluationTestCase {
  category: EvaluationCategory;
  expected: {
    answer: {
      must_include: string[][];
      must_not_include?: string[][];
    };
    behavior: ExpectedBehavior;
    fictional_grounding?: boolean;
    hydration: ExpectedHydration;
    min_queries: number;
  };
  id: string;
  question: string;
}

export interface EvaluationSuite {
  cases: EvaluationTestCase[];
  kind: "canary" | "holdout";
  score_version: NaturalLanguageScoreVersion;
  version: string;
}

export interface EvaluationScore {
  availability_pass: boolean;
  behavior_pass?: boolean;
  evidence_pass: boolean;
  fabrication_detected?: boolean;
  factual_pass: boolean;
  full_pass: boolean;
  hydration_pass: boolean;
  name_resolution_pass?: boolean;
  safety_pass: boolean;
  tool_omission?: boolean;
  tool_use_pass?: boolean;
}

export interface EvaluationMetrics {
  cost_complete?: boolean;
  estimated_cost_usd: number;
  tool_argument_normalizations?: ToolArgumentNormalizations;
  total_ms: number;
}

export interface EvaluationSuccess {
  evidence_entity_ids?: {
    ability: number[];
    item: number[];
    move: number[];
    pokemon: number[];
  };
  metrics: EvaluationMetrics;
  response: {
    ability_ids: number[];
    answer: string;
    item_ids: number[];
    move_ids: number[];
    pokemon_ids: number[];
    queries: QueryTrace[];
    table: { columns: string[]; rows: unknown[][] } | null;
  };
}

export interface EvaluationFailure {
  diagnostics?: unknown;
  error: string;
  failure_class?: FailureClass;
  metrics: EvaluationMetrics;
}

export interface EvaluationRecord {
  candidate: string;
  category: EvaluationCategory;
  evaluation: EvaluationScore;
  ordinal: number;
  question: string;
  question_id: string;
  repetition: number;
  result: EvaluationFailure | EvaluationSuccess;
  status: "failure" | "success";
}
