import { isDeepStrictEqual } from "node:util";
import { createHash } from "node:crypto";
import {
  chmodSync,
  closeSync,
  existsSync,
  fsyncSync,
  mkdirSync,
  openSync,
  readFileSync,
  renameSync,
  writeFileSync,
  writeSync,
} from "node:fs";
import { resolve } from "node:path";
import { pathToFileURL } from "node:url";

import { GoogleGenAI } from "@google/genai";
import { buildClientSchema, printSchema, type IntrospectionQuery } from "graphql";
import OpenAI from "openai";

import { createCanaryTarget } from "./canary-target.js";
import {
  createEvaluationGraphqlControls,
  type ConsistencyProbe,
  type ConsistencyProbeResult,
} from "./graphql-controls.js";
import {
  EVALUATION_CANDIDATES,
  EVALUATION_MODEL_CONFIGS,
  EVALUATION_PROFILE,
  createRunManifest,
  type EvaluationCandidateId,
} from "./profile.js";
import { evaluateRetryPolicy } from "./retry-policy.js";
import { EVALUATION_CURSOR_SECRET } from "./search-rescore.js";
import { failedEvaluation, scoreEvaluation } from "./scorer.js";
import {
  scoreSearchEvaluation,
  summarizeSearchRecords,
  type SearchEvaluationScore,
  type SearchEvaluationTestCase,
  type SearchFixtureResult,
} from "./search-scorer.js";
import { buildSchedule, loadSearchSuite, loadSuite, type SearchEvaluationSuite } from "./suite.js";
import type {
  EvaluationCategory,
  EvaluationFailure,
  EvaluationRecord,
  EvaluationScore,
  EvaluationSuccess,
  EvaluationSuite,
} from "./types.js";
import { createGeminiProvider, type GeminiClient } from "../src/gemini-provider.js";
import { createOpenAIProvider, type ResponsesClient } from "../src/openai-provider.js";
import { createOpenRouterProvider } from "../src/openrouter-provider.js";
import {
  AskEvaluationError,
  AskOrchestrator,
  type AskMetrics,
  type FailureClass,
  type ModelProvider,
} from "../src/orchestrator.js";
import {
  createReadonlyGraphqlExecutor,
  type GraphqlExecution,
  type GraphqlRequest,
  type JsonObject,
  type JsonValue,
} from "../src/readonly-graphql.js";
import { resolveRuntimeConfig } from "../src/runtime-config.js";
import { createSchemaLookup } from "../src/schema-lookup.js";
import { createPaginationService } from "../src/pagination.js";
import type { SearchInterpretation } from "../src/search-descriptors.js";

const backendRoot = resolve(import.meta.dirname, "..");
const defaultSeed = "ask-pokedexer-eval-v4-seed";
const implementationPaths = [
  "evaluation/canary-target.ts",
  "evaluation/graphql-controls.ts",
  "evaluation/program.ts",
  "evaluation/programs/comprehensive-v1.json",
  "evaluation/programs/comprehensive-v2.json",
  "evaluation/programs/comprehensive-v3.json",
  "evaluation/profile.ts",
  "evaluation/retry-policy.ts",
  "evaluation/run.ts",
  "evaluation/scorer.ts",
  "evaluation/search-scorer.ts",
  "evaluation/suite.ts",
  "evaluation/types.ts",
  "package-lock.json",
  "package.json",
  "src/execution-profile.ts",
  "src/entity-resolution.ts",
  "src/gemini-provider.ts",
  "src/model-contract.ts",
  "src/openai-provider.ts",
  "src/openrouter-provider.ts",
  "src/orchestrator.ts",
  "src/pagination.ts",
  "src/readonly-graphql.ts",
  "src/runtime-config.ts",
  "src/schema-lookup.ts",
  "src/search-descriptors.ts",
  "src/structured-search.ts",
  "evaluation/suites/search-v3.json",
  "evaluation/suites/grounding-canary-v2.json",
  "evaluation/suites/holdout-v4.json",
] as const;

export interface RunOptions {
  candidates?: EvaluationCandidateId[];
  executePaid: boolean;
  outputDirectory?: string;
  questionIds?: string[];
  repetitions: number;
  seed: string;
  suite: "canary" | "holdout" | "search";
}

interface EvaluationTarget {
  execute(request: GraphqlRequest): Promise<GraphqlExecution>;
  lookup(request: JsonObject): Promise<unknown>;
  runConsistencyProbes?(): Promise<ConsistencyProbeResult[]>;
  schemaSource: string;
}

function nextValue(arguments_: string[], index: number, name: string): string {
  const value = arguments_[index + 1];
  if (!value || value.startsWith("--")) throw new Error(`${name} requires a value`);
  return value;
}

export function parseRunOptions(arguments_: string[]): RunOptions {
  let confirmation: string | undefined;
  const candidates: EvaluationCandidateId[] = [];
  let executePaid = false;
  let outputDirectory: string | undefined;
  const questionIds: string[] = [];
  let repetitions = 3;
  let seed = defaultSeed;
  let suite: RunOptions["suite"] | undefined;

  for (let index = 0; index < arguments_.length; index += 1) {
    const argument = arguments_[index];
    if (argument === "--execute-paid") {
      executePaid = true;
      continue;
    }
    if (argument === "--confirm-cost") {
      confirmation = nextValue(arguments_, index, argument);
      index += 1;
      continue;
    }
    if (argument === "--candidate") {
      const value = nextValue(arguments_, index, argument);
      if (!(value in EVALUATION_MODEL_CONFIGS)) {
        throw new Error(`Unknown evaluation candidate ${value}`);
      }
      candidates.push(value as EvaluationCandidateId);
      index += 1;
      continue;
    }
    if (argument === "--output") {
      outputDirectory = nextValue(arguments_, index, argument);
      index += 1;
      continue;
    }
    if (argument === "--repetitions") {
      repetitions = Number(nextValue(arguments_, index, argument));
      index += 1;
      continue;
    }
    if (argument === "--question-id") {
      questionIds.push(nextValue(arguments_, index, argument));
      index += 1;
      continue;
    }
    if (argument === "--seed") {
      seed = nextValue(arguments_, index, argument);
      index += 1;
      continue;
    }
    if (argument === "--suite") {
      const value = nextValue(arguments_, index, argument);
      if (value !== "canary" && value !== "holdout" && value !== "search") {
        throw new Error("--suite must be holdout, canary, or search");
      }
      suite = value;
      index += 1;
      continue;
    }
    throw new Error(`Unknown argument ${argument}`);
  }

  if (!suite) throw new Error("--suite holdout or --suite canary is required");
  if (!Number.isInteger(repetitions) || repetitions < 1 || repetitions > 10) {
    throw new Error("--repetitions must be an integer between 1 and 10");
  }
  if (!seed.trim()) throw new Error("--seed cannot be empty");
  if (new Set(candidates).size !== candidates.length) {
    throw new Error("--candidate values must be unique");
  }
  if (new Set(questionIds).size !== questionIds.length) {
    throw new Error("--question-id values must be unique");
  }
  if (executePaid && confirmation !== "RUN_PAID_EVALUATION") {
    throw new Error("Paid execution requires --confirm-cost RUN_PAID_EVALUATION");
  }
  if (executePaid && !outputDirectory) throw new Error("Paid execution requires --output");
  if (!executePaid && confirmation) throw new Error("--confirm-cost requires --execute-paid");
  if (!executePaid && outputDirectory) throw new Error("--output requires --execute-paid");
  return {
    candidates: candidates.length > 0 ? candidates : undefined,
    executePaid,
    outputDirectory,
    questionIds: questionIds.length > 0 ? questionIds : undefined,
    repetitions,
    seed,
    suite,
  };
}

function recordKey(record: Pick<EvaluationRecord, "candidate" | "question_id" | "repetition">) {
  return `${record.candidate}:${record.question_id}:${record.repetition}`;
}

export function loadCompletedRecords(path: string): Map<string, EvaluationRecord> {
  if (!existsSync(path)) return new Map();
  const records = new Map<string, EvaluationRecord>();
  const lines = readFileSync(path, "utf8").split("\n");
  for (let index = 0; index < lines.length; index += 1) {
    const line = lines[index].trim();
    if (!line) continue;
    let record: EvaluationRecord;
    try {
      record = JSON.parse(line) as EvaluationRecord;
    } catch {
      throw new Error(`Invalid JSONL evaluation record on line ${index + 1}`);
    }
    if (!record.candidate || !record.question_id || !Number.isInteger(record.repetition)) {
      throw new Error(`Invalid evaluation record on line ${index + 1}`);
    }
    const key = recordKey(record);
    if (records.has(key)) throw new Error(`Duplicate evaluation record ${key}`);
    records.set(key, record);
  }
  return records;
}

function rounded(value: number): number {
  return Math.round(value * 1_000_000_000) / 1_000_000_000;
}

function percentile(values: number[], fraction: number): number {
  if (values.length === 0) return 0;
  const sorted = [...values].sort((left, right) => left - right);
  return sorted[Math.max(0, Math.ceil(sorted.length * fraction) - 1)];
}

function metric(record: EvaluationRecord, name: string): number {
  const value = (record.result.metrics as unknown as Record<string, unknown>)[name];
  return typeof value === "number" && Number.isFinite(value) ? value : 0;
}

function summarizeSelection(selected: EvaluationRecord[]) {
  const count = (dimension: keyof EvaluationScore) =>
    selected.filter((record) => record.evaluation[dimension]).length;
  const total = selected.length;
  const costs = selected.map((record) => record.result.metrics.estimated_cost_usd);
  const latencies = selected.map((record) => record.result.metrics.total_ms);
  const fullPasses = count("full_pass");
  const toolArgumentNormalizations = selected.reduce(
    (total, record) => {
      const item = record.result.metrics.tool_argument_normalizations;
      if (!item) return total;
      total.calls += item.calls;
      total.kinds.non_string_value_json += item.kinds.non_string_value_json;
      total.kinds.variables_object_map += item.kinds.variables_object_map;
      return total;
    },
    {
      calls: 0,
      kinds: { non_string_value_json: 0, variables_object_map: 0 },
    },
  );
  const averageMetric = (name: string) =>
    total === 0
      ? 0
      : Math.round(selected.reduce((sum, record) => sum + metric(record, name), 0) / total);
  const estimatedCost = costs.reduce((totalCost, cost) => totalCost + cost, 0);
  const failureClasses = (["provider", "pokeapi", "model", "evaluator"] as FailureClass[])
    .map((failureClass) => [
      failureClass,
      selected.filter(
        (record) =>
          !("response" in record.result) && record.result.failure_class === failureClass,
      ).length,
    ] as const);
  return {
    availability_passes: count("availability_pass"),
    behavior_passes: count("behavior_pass"),
    average_estimated_cost_usd: total === 0 ? 0 : rounded(estimatedCost / total),
    average_metrics: {
      graphql_attempts: averageMetric("graphql_attempts"),
      graphql_calls: averageMetric("graphql_calls"),
      input_tokens: averageMetric("input_tokens"),
      model_attempts: averageMetric("model_attempts"),
      model_calls: averageMetric("model_calls"),
      output_tokens: averageMetric("output_tokens"),
      schema_lookups: averageMetric("schema_lookups"),
    },
    cost_complete_records: selected.filter(
      (record) => record.result.metrics.cost_complete !== false,
    ).length,
    estimated_cost_usd: rounded(estimatedCost),
    evidence_passes: count("evidence_pass"),
    fabrications_detected: selected.filter(
      (record) => record.evaluation.fabrication_detected === true,
    ).length,
    factual_passes: count("factual_pass"),
    failures_by_class: Object.fromEntries(failureClasses),
    failures: selected.filter((record) => record.status === "failure").length,
    full_pass_rate: total === 0 ? 0 : fullPasses / total,
    full_passes: fullPasses,
    hydration_passes: count("hydration_pass"),
    latency_ms: {
      mean: total === 0 ? 0 : Math.round(latencies.reduce((sum, item) => sum + item, 0) / total),
      p50: percentile(latencies, 0.5),
      p95: percentile(latencies, 0.95),
    },
    name_resolution_passes: count("name_resolution_pass"),
    safety_passes: count("safety_pass"),
    tool_omissions: selected.filter((record) => record.evaluation.tool_omission === true).length,
    tool_argument_normalizations: toolArgumentNormalizations,
    tool_use_passes: count("tool_use_pass"),
    total,
  };
}

export function summarizeRecords(records: EvaluationRecord[]) {
  const candidateIds = [...new Set(records.map((record) => record.candidate))].sort();
  const candidates = Object.fromEntries(
    candidateIds.map((candidate) => {
      const selected = records.filter((record) => record.candidate === candidate);
      return [
        candidate,
        {
          ...summarizeSelection(selected),
          categories: Object.fromEntries(
            (["facts", "relationships", "difficult", "safety"] as EvaluationCategory[])
              .map((category) => [
                category,
                summarizeSelection(selected.filter((record) => record.category === category)),
              ]),
          ),
        },
      ];
    }),
  );
  return { candidates, records: records.length };
}

function suitePath(kind: RunOptions["suite"]): string {
  const file =
    kind === "holdout"
      ? "holdout-v4.json"
      : kind === "canary"
        ? "grounding-canary-v2.json"
        : "search-v3.json";
  return resolve(backendRoot, "evaluation/suites", file);
}

function selectedCandidates(options: RunOptions): EvaluationCandidateId[] {
  return options.candidates ?? (Object.keys(EVALUATION_CANDIDATES) as EvaluationCandidateId[]);
}

function selectedSuite<T extends { cases: Array<{ id: string }> }>(suite: T, options: RunOptions): T {
  if (!options.questionIds) return suite;
  const available = new Set(suite.cases.map((testCase) => testCase.id));
  for (const questionId of options.questionIds) {
    if (!available.has(questionId)) throw new Error(`Suite does not contain question ${questionId}`);
  }
  const requested = new Set(options.questionIds);
  return { ...suite, cases: suite.cases.filter((testCase) => requested.has(testCase.id)) };
}

function implementationSources() {
  return implementationPaths.map((path) => ({
    path,
    source: readFileSync(resolve(backendRoot, path), "utf8"),
  }));
}

export const LIVE_CONSISTENCY_PROBES: ConsistencyProbe[] = [
  {
    id: "core-entities",
    request: {
      purpose: "Verify stable core entity data for the evaluation",
      query: `query EvaluationCoreProbe($pokemonIds: [Int!]!, $moveIds: [Int!]!, $abilityIds: [Int!]!, $limit: Int!) {
        pokemon(where: {id: {_in: $pokemonIds}}, order_by: {id: asc}, limit: $limit) { id name height weight }
        move(where: {id: {_in: $moveIds}}, order_by: {id: asc}, limit: $limit) { id name power accuracy }
        ability(where: {id: {_in: $abilityIds}}, order_by: {id: asc}, limit: $limit) { id name is_main_series }
      }`,
      variables: { abilityIds: [9, 22], limit: 3, moveIds: [33, 85], pokemonIds: [1, 25, 150] },
    },
  },
  {
    id: "species-relations",
    request: {
      purpose: "Verify stable species relationships for the evaluation",
      query: `query EvaluationRelationProbe($ids: [Int!]!, $limit: Int!, $pokemonLimit: Int!) {
        pokemonspecies(where: {id: {_in: $ids}}, order_by: {id: asc}, limit: $limit) {
          id
          name
          pokemons(where: {is_default: {_eq: true}}, order_by: {id: asc}, limit: $pokemonLimit) { id name }
        }
      }`,
      variables: { ids: [1, 25, 150], limit: 3, pokemonLimit: 2 },
    },
  },
];

function consistencySha256(results: ConsistencyProbeResult[]): string {
  return createHash("sha256").update(JSON.stringify(results)).digest("hex");
}

function loadLiveTarget(): EvaluationTarget {
  const schemaPath = resolve(
    backendRoot,
    "../shared/src/commonMain/graphql/des.c5inco.pokedexer.shared/schema.json",
  );
  const schemaSource = readFileSync(schemaPath, "utf8");
  const introspection = JSON.parse(schemaSource) as { data: IntrospectionQuery };
  const schema = buildClientSchema(introspection.data);
  const graphql = createReadonlyGraphqlExecutor({
    endpoint: "https://graphql.pokeapi.co/v1beta2",
    maxComplexity: EVALUATION_PROFILE.graphql.max_complexity,
    maxDepth: EVALUATION_PROFILE.graphql.max_depth,
    maxResponseBytes: EVALUATION_PROFILE.graphql.max_response_bytes,
    maxRows: EVALUATION_PROFILE.graphql.max_rows,
    schema,
    timeoutMs: EVALUATION_PROFILE.graphql.timeout_ms,
  });
  const controls = createEvaluationGraphqlControls({ execute: graphql.execute });
  return {
    execute: controls.execute,
    lookup: createSchemaLookup(schema),
    runConsistencyProbes: () => controls.runConsistencyProbes(LIVE_CONSISTENCY_PROBES),
    schemaSource,
  };
}

function loadTarget(kind: RunOptions["suite"]): EvaluationTarget {
  if (kind === "holdout" || kind === "search") return loadLiveTarget();
  const target = createCanaryTarget();
  return { execute: target.execute, lookup: target.lookup, schemaSource: printSchema(target.schema) };
}

function candidateProvider(
  candidateId: EvaluationCandidateId,
  environment: NodeJS.ProcessEnv,
): { model: ModelProvider; pricing: ReturnType<typeof resolveRuntimeConfig>["pricing"] } {
  const candidate = EVALUATION_MODEL_CONFIGS[candidateId];
  if (candidate.provider === "openai") {
    const runtime = resolveRuntimeConfig({
      ...environment,
      MODEL_PROVIDER: "openai",
      OPENAI_MODEL: candidate.model,
      OPENAI_REASONING_EFFORT: candidate.reasoning,
    });
    const client = new OpenAI({
      apiKey: runtime.apiKey,
      maxRetries: EVALUATION_PROFILE.model.max_retries,
      timeout: EVALUATION_PROFILE.model.timeout_ms,
    });
    return {
      model: createOpenAIProvider({
        client: client as unknown as ResponsesClient,
        model: candidate.model,
        reasoningEffort: candidate.reasoning,
      }),
      pricing: runtime.pricing,
    };
  }

  if (candidate.provider === "openrouter") {
    const runtime = resolveRuntimeConfig({
      ...environment,
      MODEL_PROVIDER: "openrouter",
      OPENROUTER_MODEL: candidate.model,
      OPENROUTER_REASONING_EFFORT: candidate.reasoning,
    });
    const client = new OpenAI({
      apiKey: runtime.apiKey,
      baseURL: "https://openrouter.ai/api/v1",
      maxRetries: EVALUATION_PROFILE.model.max_retries,
      timeout: EVALUATION_PROFILE.model.timeout_ms,
    });
    return {
      model: createOpenRouterProvider({
        client: client as unknown as ResponsesClient,
        model: candidate.model,
        reasoningEffort: candidate.reasoning,
      }),
      pricing: runtime.pricing,
    };
  }

  const runtime = resolveRuntimeConfig({
    ...environment,
    GEMINI_MODEL: candidate.model,
    MODEL_PROVIDER: "gemini",
  });
  const client = new GoogleGenAI({
    apiKey: runtime.apiKey,
    httpOptions: {
      retryOptions: { attempts: EVALUATION_PROFILE.model.max_retries + 1 },
      timeout: EVALUATION_PROFILE.model.timeout_ms,
    },
  });
  return {
    model: createGeminiProvider({
      client: client as unknown as GeminiClient,
      model: candidate.model,
      thinkingLevel: candidate.thinking,
    }),
    pricing: runtime.pricing,
  };
}

function createCandidateOrchestrators(
  target: EvaluationTarget,
  candidateIds: EvaluationCandidateId[],
) {
  return new Map(
    candidateIds.map((candidateId) => {
      const provider = candidateProvider(candidateId, process.env);
      return [
        candidateId,
        new AskOrchestrator({
          executeGraphql: target.execute,
          maxGraphqlAttempts: EVALUATION_PROFILE.max_graphql_attempts,
          maxToolRounds: EVALUATION_PROFILE.max_tool_rounds,
          model: provider.model,
          pagination: createPaginationService({
            secret: EVALUATION_CURSOR_SECRET,
          }),
          pricing: provider.pricing,
          schemaLookup: async (request) => (await target.lookup(request)) as JsonValue,
        }),
      ];
    }),
  );
}

function unknownFailureMetrics(started: number): AskMetrics {
  return {
    cache_write_tokens: 0,
    cached_input_tokens: 0,
    cost_complete: false,
    estimated_cost_usd: 0,
    graphql_attempts: 0,
    graphql_calls: 0,
    graphql_ms: 0,
    input_tokens: 0,
    model_attempts: 0,
    model_calls: 0,
    model_ms: 0,
    output_tokens: 0,
    schema_lookup_ms: 0,
    schema_lookups: 0,
    tool_argument_normalizations: {
      calls: 0,
      kinds: { non_string_value_json: 0, variables_object_map: 0 },
    },
    total_ms: Math.round(performance.now() - started),
  };
}

function failureFrom(error: unknown, started: number): EvaluationFailure {
  if (error instanceof AskEvaluationError) {
    return {
      diagnostics: error.evaluation.diagnostics,
      error: error.message.slice(0, 500),
      failure_class: error.evaluation.diagnostics.failure_class ?? "evaluator",
      metrics: error.evaluation.metrics,
    };
  }
  return {
    diagnostics: { phase: "runner" },
    error: (error instanceof Error ? error.message : "Unknown evaluation failure").slice(0, 500),
    failure_class: "evaluator",
    metrics: unknownFailureMetrics(started),
  };
}

function appendRecord(path: string, record: unknown): void {
  const descriptor = openSync(path, "a", 0o600);
  try {
    writeSync(descriptor, `${JSON.stringify(record)}\n`);
    fsyncSync(descriptor);
  } finally {
    closeSync(descriptor);
  }
}

interface SearchRunRecord {
  candidate: string;
  category: SearchEvaluationTestCase["category"];
  ordinal: number;
  question: string;
  question_id: string;
  repetition: number;
  result: unknown;
  score: SearchEvaluationScore;
  status: "failure" | "success";
}

function loadSearchRecords(path: string): Map<string, SearchRunRecord> {
  if (!existsSync(path)) return new Map();
  const records = new Map<string, SearchRunRecord>();
  for (const [index, line] of readFileSync(path, "utf8").split("\n").entries()) {
    if (!line.trim()) continue;
    let record: SearchRunRecord;
    try {
      record = JSON.parse(line) as SearchRunRecord;
    } catch {
      throw new Error(`Invalid JSONL search record on line ${index + 1}`);
    }
    const key = recordKey(record);
    if (records.has(key)) throw new Error(`Duplicate search evaluation record ${key}`);
    records.set(key, record);
  }
  return records;
}

function failedSearchScore(): SearchEvaluationScore {
  return {
    behavior_pass: false,
    evidence_coverage_pass: false,
    full_pass: false,
    interpretation_pass: false,
    pagination_pass: false,
    relevance_pass: false,
  };
}

function searchFixture(
  orchestrator: AskOrchestrator,
  question: string,
  result: Awaited<ReturnType<AskOrchestrator["ask"]>>,
): SearchFixtureResult {
  const response = result.response;
  const pages: NonNullable<SearchFixtureResult["pages"]> = [];
  let pagination = response.pagination;
  let pokemonIds = response.pokemon_ids;
  while (pagination) {
    pages.push({ pagination, pokemon_ids: pokemonIds });
    const cursor = pagination.continuation_cursor;
    if (!cursor) break;
    const continuation = orchestrator.continue(question, cursor).response;
    pagination = continuation.pagination;
    pokemonIds = continuation.pokemon_ids;
  }
  const interpretation = (response.interpretation ?? null) as SearchInterpretation | null;
  const hasEntities =
    response.ability_ids.length +
      response.item_ids.length +
      response.move_ids.length +
      response.pokemon_ids.length >
    0;
  const behavior =
    interpretation?.status === "needs_clarification" ||
    interpretation?.status === "requires_enrichment"
      ? interpretation.status
      : hasEntities
        ? "answer"
        : result.metrics.graphql_calls > 0
          ? "not_found"
          : "insufficient_evidence";
  return {
    behavior,
    evidence_entity_ids: result.evidence_entity_ids,
    interpretation,
    pages,
    response,
    successful_evidence_queries: result.metrics.graphql_calls,
  };
}

async function executePaidSearchRun(
  options: RunOptions,
  suite: SearchEvaluationSuite,
  target: EvaluationTarget,
  manifest: unknown,
): Promise<void> {
  if (!options.outputDirectory) throw new Error("Missing paid evaluation output directory");
  const output = resolve(options.outputDirectory);
  mkdirSync(output, { recursive: true });
  writeManifest(resolve(output, "manifest.json"), manifest);
  const recordsPath = resolve(output, "search-records.jsonl");
  const summaryPath = resolve(output, "search-summary.json");
  const candidateIds = selectedCandidates(options);
  const schedule = buildSchedule(
    suite,
    candidateIds,
    options.repetitions,
    options.seed,
  );
  const testCases = new Map(suite.cases.map((testCase) => [testCase.id, testCase]));
  const orchestrators = createCandidateOrchestrators(target, candidateIds);
  const expected = new Map(
    schedule.map((entry, index) => [recordKey(entry), { ...entry, ordinal: index + 1 }]),
  );
  const completed = loadSearchRecords(recordsPath);
  for (const [key, record] of completed) {
    const scheduled = expected.get(key);
    if (!scheduled || record.ordinal !== scheduled.ordinal) {
      throw new Error(`Search record ${key} is not part of the frozen schedule`);
    }
  }
  for (let index = 0; index < schedule.length; index += 1) {
    const entry = schedule[index];
    const key = recordKey(entry);
    if (completed.has(key)) continue;
    const testCase = testCases.get(entry.question_id);
    const orchestrator = orchestrators.get(entry.candidate as EvaluationCandidateId);
    if (!testCase || !orchestrator) throw new Error(`Invalid search schedule entry ${entry.question_id}`);
    const started = performance.now();
    let record: SearchRunRecord;
    try {
      const result = await orchestrator.ask(testCase.question);
      const fixture = searchFixture(orchestrator, testCase.question, result);
      record = {
        ...entry,
        category: testCase.category,
        ordinal: index + 1,
        question: testCase.question,
        result,
        score: scoreSearchEvaluation(testCase, fixture),
        status: "success",
      };
    } catch (error) {
      record = {
        ...entry,
        category: testCase.category,
        ordinal: index + 1,
        question: testCase.question,
        result: failureFrom(error, started),
        score: failedSearchScore(),
        status: "failure",
      };
    }
    appendRecord(recordsPath, record);
    completed.set(key, record);
  }
  const records = [...completed.values()].sort((left, right) => left.ordinal - right.ordinal);
  writeSummary(
    summaryPath,
    summarizeSearchRecords(
      records.map((record) => ({
        candidate: record.candidate,
        category: record.category,
        score: record.score,
      })),
    ),
  );
}

function writeManifest(path: string, manifest: unknown): void {
  if (existsSync(path)) {
    const existing = JSON.parse(readFileSync(path, "utf8")) as unknown;
    if (!isDeepStrictEqual(existing, manifest)) {
      throw new Error("Existing manifest does not match this evaluation run");
    }
    return;
  }
  writeFileSync(path, `${JSON.stringify(manifest, null, 2)}\n`, { encoding: "utf8", flag: "wx" });
  chmodSync(path, 0o444);
}

function writeSummary(path: string, summary: unknown): void {
  const temporary = `${path}.tmp`;
  writeFileSync(temporary, `${JSON.stringify(summary, null, 2)}\n`, "utf8");
  renameSync(temporary, path);
}

async function executePaidRun(
  options: RunOptions,
  suite: EvaluationSuite,
  target: EvaluationTarget,
  manifest: unknown,
): Promise<void> {
  if (!options.outputDirectory) throw new Error("Missing paid evaluation output directory");
  const output = resolve(options.outputDirectory);
  mkdirSync(output, { recursive: true });
  const manifestPath = resolve(output, "manifest.json");
  const recordsPath = resolve(output, "records.jsonl");
  const summaryPath = resolve(output, "summary.json");
  writeManifest(manifestPath, manifest);

  const candidateIds = selectedCandidates(options);
  const schedule = buildSchedule(
    suite,
    candidateIds,
    options.repetitions,
    options.seed,
  );
  const expected = new Map(
    schedule.map((entry, index) => [recordKey(entry), { ...entry, ordinal: index + 1 }]),
  );
  const completed = loadCompletedRecords(recordsPath);
  for (const [key, record] of completed) {
    const scheduled = expected.get(key);
    if (!scheduled || record.ordinal !== scheduled.ordinal) {
      throw new Error(`Record ${key} is not part of the frozen schedule`);
    }
  }

  const testCases = new Map(suite.cases.map((testCase) => [testCase.id, testCase]));
  const orchestrators = createCandidateOrchestrators(target, candidateIds);
  for (let index = 0; index < schedule.length; index += 1) {
    const entry = schedule[index];
    const key = recordKey(entry);
    if (completed.has(key)) continue;
    const testCase = testCases.get(entry.question_id);
    const orchestrator = orchestrators.get(entry.candidate as EvaluationCandidateId);
    if (!testCase || !orchestrator) throw new Error(`Invalid frozen schedule entry ${key}`);
    const started = performance.now();
    let record: EvaluationRecord;
    try {
      const result = (await orchestrator.ask(testCase.question)) as EvaluationSuccess;
      record = {
        ...entry,
        category: testCase.category,
        evaluation: scoreEvaluation(testCase, result, suite.score_version),
        ordinal: index + 1,
        question: testCase.question,
        result,
        status: "success",
      };
    } catch (error) {
      const result = failureFrom(error, started);
      record = {
        ...entry,
        category: testCase.category,
        evaluation: failedEvaluation(),
        ordinal: index + 1,
        question: testCase.question,
        result,
        status: "failure",
      };
    }
    appendRecord(recordsPath, record);
    completed.set(key, record);
    console.log(
      `[${index + 1}/${schedule.length}] ${entry.candidate} ${entry.question_id} ${record.status}`,
    );
  }

  const records = [...completed.values()].sort((left, right) => left.ordinal - right.ordinal);
  const lowRecords = records.filter((record) => record.candidate === "luna-low");
  const mediumRecords = records.filter((record) => record.candidate === "luna-medium");
  const retryPolicies =
    lowRecords.length === 0 || mediumRecords.length === 0
      ? null
      : {
          oracle_contract: evaluateRetryPolicy(lowRecords, mediumRecords, "oracle-contract"),
          runtime_detectable: evaluateRetryPolicy(lowRecords, mediumRecords, "runtime-detectable"),
        };
  writeSummary(summaryPath, { ...summarizeRecords(records), retry_policies: retryPolicies });
}

async function initialConsistency(
  options: RunOptions,
  target: EvaluationTarget,
): Promise<ConsistencyProbeResult[] | undefined> {
  if (!options.executePaid || !target.runConsistencyProbes) return undefined;
  return target.runConsistencyProbes();
}

async function finalizeConsistency(
  options: RunOptions,
  target: EvaluationTarget,
  before: ConsistencyProbeResult[] | undefined,
): Promise<void> {
  if (!before || !target.runConsistencyProbes || !options.outputDirectory) return;
  const after = await target.runConsistencyProbes();
  const consistent = isDeepStrictEqual(before, after);
  const output = resolve(options.outputDirectory);
  writeSummary(resolve(output, "data-consistency.json"), {
    after,
    before,
    consistent,
    sha256: consistencySha256(before),
  });
  if (!consistent) {
    writeFileSync(
      resolve(output, "INVALID_DATA_CONSISTENCY"),
      "PokéAPI consistency probes changed during this run. Do not use these records for model comparison.\n",
      "utf8",
    );
    throw new Error("PokéAPI consistency probes changed during the evaluation");
  }
}

export async function main(arguments_: string[]): Promise<void> {
  const options = parseRunOptions(arguments_);
  const suiteSource = readFileSync(suitePath(options.suite), "utf8");
  const candidateIds = selectedCandidates(options);
  if (options.suite === "search") {
    const suite = selectedSuite(loadSearchSuite(suiteSource), options);
    const target = loadTarget("search");
    const consistency = await initialConsistency(options, target);
    const manifest = createRunManifest({
      candidateIds,
      implementationSources: implementationSources(),
      liveDataSha256: consistency ? consistencySha256(consistency) : undefined,
      questionIds: options.questionIds,
      repetitions: options.repetitions,
      schemaSource: target.schemaSource,
      seed: options.seed,
      suiteSource,
    });
    const schedule = buildSchedule(
      suite,
      candidateIds,
      options.repetitions,
      options.seed,
    );
    if (!options.executePaid) {
      console.log(
        JSON.stringify(
          {
            historical_cases_included: 0,
            manifest,
            mode: "validation-only",
            paid_requests_executed: 0,
            schedule_entries: schedule.length,
            search_cases: suite.cases.length,
            suite: suite.version,
          },
          null,
          2,
        ),
      );
      return;
    }
    await executePaidSearchRun(options, suite, target, manifest);
    await finalizeConsistency(options, target, consistency);
    return;
  }
  const suite = selectedSuite(loadSuite(suiteSource), options);
  if (suite.kind !== options.suite) throw new Error("Suite kind does not match --suite");
  const target = loadTarget(options.suite);
  const consistency = await initialConsistency(options, target);
  const manifest = createRunManifest({
    candidateIds,
    implementationSources: implementationSources(),
    liveDataSha256: consistency ? consistencySha256(consistency) : undefined,
    questionIds: options.questionIds,
    repetitions: options.repetitions,
    schemaSource: target.schemaSource,
    seed: options.seed,
    suiteSource,
  });
  const schedule = buildSchedule(
    suite,
    candidateIds,
    options.repetitions,
    options.seed,
  );
  if (!options.executePaid) {
    console.log(
      JSON.stringify(
        {
          manifest,
          mode: "validation-only",
          paid_requests_executed: 0,
          schedule_entries: schedule.length,
          suite: suite.version,
        },
        null,
        2,
      ),
    );
    return;
  }
  await executePaidRun(options, suite, target, manifest);
  await finalizeConsistency(options, target, consistency);
}

const invokedPath = process.argv[1] ? pathToFileURL(resolve(process.argv[1])).href : undefined;
if (invokedPath === import.meta.url) {
  main(process.argv.slice(2)).catch((error) => {
    console.error(error instanceof Error ? error.message : "Evaluation runner failed");
    process.exitCode = 1;
  });
}
