import type {
  EntityIds,
  EntityReferences,
  GraphqlExecution,
  GraphqlRequest,
  JsonObject,
  JsonValue,
  QueryTrace,
} from "./readonly-graphql.js";
import {
  collectEntityReferences,
  GraphqlInfrastructureError,
} from "./readonly-graphql.js";
import {
  discloseEntityResolutions,
  resolveEntityAliases,
  type EntityResolution,
} from "./entity-resolution.js";
import type { PaginationService } from "./pagination.js";
import { interpretSearchQuestion } from "./search-descriptors.js";
import type { SearchInterpretation } from "./search-descriptors.js";
import { compileStructuredSearch } from "./structured-search.js";

export type { GraphqlExecution } from "./readonly-graphql.js";

export interface ToolCall {
  arguments: JsonObject;
  callId: string;
  name: "execute_readonly_graphql" | "schema_lookup";
}

export type ToolArgumentNormalizationKind =
  | "non_string_value_json"
  | "variables_object_map";

export interface ToolArgumentNormalizations {
  calls: number;
  kinds: Record<ToolArgumentNormalizationKind, number>;
}

export interface TokenUsage {
  cacheWriteTokens: number;
  cachedInputTokens: number;
  inputTokens: number;
  outputTokens: number;
  toolArgumentNormalizations?: ToolArgumentNormalizations;
}

export class ModelProviderError extends Error {
  constructor(
    message: string,
    readonly usage?: TokenUsage,
  ) {
    super(message);
  }
}

export interface PlannerTurn {
  toolCalls: ToolCall[];
  usage: TokenUsage;
}

export interface SynthesisResponse {
  ability_ids: number[];
  answer: string;
  continuation_candidates?: {
    ability_ids: number[];
    item_ids: number[];
    move_ids: number[];
    pokemon_ids: number[];
  } | null;
  item_ids: number[];
  move_ids: number[];
  pokemon_ids: number[];
  table: { columns: string[]; rows: JsonValue[][] } | null;
}

export interface ModelProvider {
  plan(input: {
    entityResolutions?: EntityResolution[];
    history: Array<{ call: ToolCall; result: JsonValue }>;
    interpretation?: SearchInterpretation;
    question: string;
    retryReason?: string;
  }): Promise<PlannerTurn>;
  synthesize(input: {
    entityResolutions?: EntityResolution[];
    evidence: Array<{ call: ToolCall; result: JsonValue }>;
    interpretation?: SearchInterpretation;
    question: string;
  }): Promise<{ response: SynthesisResponse; usage: TokenUsage }>;
}

export interface Pricing {
  cacheWritePerMillion: number;
  cachedInputPerMillion: number;
  inputPerMillion: number;
  outputPerMillion: number;
}

interface OrchestratorOptions {
  executeGraphql(request: GraphqlRequest): Promise<GraphqlExecution>;
  maxGraphqlAttempts?: number;
  maxToolRounds?: number;
  model: ModelProvider;
  pagination?: PaginationService;
  pricing: Pricing;
  schemaLookup(request: JsonObject): Promise<JsonValue>;
}

interface UsageTotals extends Omit<TokenUsage, "toolArgumentNormalizations"> {
  modelCalls: number;
  toolArgumentNormalizations: ToolArgumentNormalizations;
}

export type EvaluationPhase = "graphql" | "planning" | "schema_lookup" | "synthesis" | "validation";
export type FailureClass = "evaluator" | "model" | "pokeapi" | "provider";

export interface AskMetrics {
  cache_write_tokens: number;
  cached_input_tokens: number;
  cost_complete: boolean;
  estimated_cost_usd: number;
  graphql_attempts: number;
  graphql_calls: number;
  graphql_ms: number;
  input_tokens: number;
  model_attempts: number;
  model_calls: number;
  model_ms: number;
  output_tokens: number;
  schema_lookup_ms: number;
  schema_lookups: number;
  tool_argument_normalizations: ToolArgumentNormalizations;
  total_ms: number;
}

export interface AskDiagnostics {
  contract_warnings?: string[];
  failure_class?: FailureClass;
  phase: EvaluationPhase;
  tool_errors: Array<{
    duration_ms: number;
    message: string;
    name: "execute_readonly_graphql" | "schema_lookup";
    round: number;
  }>;
}

export interface FailedEvaluation {
  diagnostics: AskDiagnostics;
  metrics: AskMetrics;
}

export class AskEvaluationError extends Error {
  constructor(
    message: string,
    readonly evaluation: FailedEvaluation,
  ) {
    super(message);
  }
}

function emptyUsage(): UsageTotals {
  return {
    cacheWriteTokens: 0,
    cachedInputTokens: 0,
    inputTokens: 0,
    modelCalls: 0,
    outputTokens: 0,
    toolArgumentNormalizations: {
      calls: 0,
      kinds: { non_string_value_json: 0, variables_object_map: 0 },
    },
  };
}

function addUsage(total: UsageTotals, usage: TokenUsage): void {
  total.cacheWriteTokens += usage.cacheWriteTokens;
  total.cachedInputTokens += usage.cachedInputTokens;
  total.inputTokens += usage.inputTokens;
  total.outputTokens += usage.outputTokens;
  total.modelCalls += 1;
  if (usage.toolArgumentNormalizations) {
    total.toolArgumentNormalizations.calls += usage.toolArgumentNormalizations.calls;
    total.toolArgumentNormalizations.kinds.non_string_value_json +=
      usage.toolArgumentNormalizations.kinds.non_string_value_json;
    total.toolArgumentNormalizations.kinds.variables_object_map +=
      usage.toolArgumentNormalizations.kinds.variables_object_map;
  }
}

function normalizedText(value: string): string {
  return value
    .normalize("NFKD")
    .replaceAll(/[\u0300-\u036f]/g, "")
    .toLocaleLowerCase()
    .replaceAll("♀", " female ")
    .replaceAll("♂", " male ")
    .replaceAll(/[’']/g, "")
    .replaceAll(/[^a-z0-9]+/g, " ")
    .trim()
    .replaceAll(/\s+/g, " ");
}

const hydrationNameAliases: Readonly<Record<string, string[]>> = {
  "nidoran-f": ["female nidoran", "nidoran female"],
  "nidoran-m": ["male nidoran", "nidoran male"],
};

function referencesEntityName(semanticText: string, canonicalName: string): boolean {
  return [canonicalName, ...(hydrationNameAliases[canonicalName] ?? [])]
    .map(normalizedText)
    .some((name) => ` ${semanticText} `.includes(` ${name} `));
}

function validateIds(
  response: SynthesisResponse,
  observed: EntityIds,
  references: EntityReferences,
  question: string,
): void {
  const checks: Array<[string, number[], number[]]> = [
    ["Pokémon", response.pokemon_ids, observed.pokemon],
    ["move", response.move_ids, observed.move],
    ["item", response.item_ids, observed.item],
    ["ability", response.ability_ids, observed.ability],
  ];
  const semanticText = normalizedText([
    question,
    response.answer,
    ...(response.table?.columns ?? []),
    ...(response.table?.rows.flatMap((row) => row.map((value) => String(value))) ?? []),
  ].join(" "));
  for (const [label, returned, available] of checks) {
    const allowed = new Set(available);
    for (const id of returned) {
      if (!Number.isInteger(id) || id < 1 || !allowed.has(id)) {
        throw new Error(`Final response referenced unverified ${label} ID ${id}`);
      }
      const kind = label === "Pokémon" ? "pokemon" : label as keyof EntityReferences;
      const reference = references[kind].find((candidate) => candidate.id === id);
      if (!reference) {
        throw new Error(`Final response ${label} ID ${id} had no verified name for hydration`);
      }
      if (!referencesEntityName(semanticText, reference.name)) {
        throw new Error(
          `Hydration entity ${reference.name} (${label} ID ${id}) was not referenced by the question or answer`,
        );
      }
    }
  }
}

function structuredResultIds(observed: EntityIds): EntityIds {
  return {
    ability: [],
    item: [],
    move: [],
    pokemon: [...new Set(observed.pokemon)].sort((left, right) => left - right).slice(0, 100),
  };
}

function hasEntityIds(ids: EntityIds): boolean {
  return Object.values(ids).some((values) => values.length > 0);
}

function mergeIds(target: EntityIds, source: EntityIds): void {
  for (const key of Object.keys(target) as Array<keyof EntityIds>) {
    target[key] = [...new Set([...target[key], ...source[key]])].sort((left, right) => left - right);
  }
}

function emptyReferences(): EntityReferences {
  return { ability: [], item: [], move: [], pokemon: [] };
}

function mergeReferences(target: EntityReferences, source: EntityReferences): void {
  for (const key of Object.keys(target) as Array<keyof EntityReferences>) {
    const references = new Map(target[key].map((item) => [item.id, item.name]));
    source[key].forEach((item) => references.set(item.id, item.name));
    target[key] = [...references]
      .map(([id, name]) => ({ id, name }))
      .sort((left, right) => left.id - right.id);
  }
}

function isDefinitiveNotFound(answer: string): boolean {
  return /\b(?:not found|does not exist|doesn't exist|no match|not present)\b/i.test(answer);
}

function isInsufficientEvidenceAnswer(answer: string): boolean {
  return /(?:cannot|can't|can’t) answer from verified|no lookup succeeded|verified evidence is unavailable|insufficient evidence/i.test(
    answer,
  );
}

function isSafeNoToolRefusal(answer: string): boolean {
  if (isDefinitiveNotFound(answer) || isInsufficientEvidenceAnswer(answer)) return false;
  return /only answer pok[eé]mon|outside pok[eé]mon|read-?only|private instructions|too broad|not allowed|cannot (?:write|delete|reveal|dump)|can't (?:write|delete|reveal|dump)|can’t (?:write|delete|reveal|dump)/i.test(
    answer,
  );
}

export class AskOrchestrator {
  constructor(private readonly options: OrchestratorOptions) {}

  continue(question: string, cursor: string) {
    if (!this.options.pagination) throw new Error("Continuation is not configured");
    if (!question.trim() || question.length > 500) {
      throw new Error("Question must be between 1 and 500 characters");
    }
    const response = this.options.pagination.nextPage(question, cursor);
    return {
      diagnostics: { tool_errors: [] },
      metrics: {
        cache_write_tokens: 0,
        cached_input_tokens: 0,
        cost_complete: true,
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
        total_ms: 0,
      },
      response,
    };
  }

  async ask(question: string) {
    if (!question.trim() || question.length > 500) {
      throw new Error("Question must be between 1 and 500 characters");
    }

    const started = performance.now();
    const history: Array<{ call: ToolCall; result: JsonValue }> = [];
    const entityResolutions = resolveEntityAliases(question);
    const interpretation = interpretSearchQuestion(question) ?? undefined;
    const structuredRequest = interpretation
      ? compileStructuredSearch(interpretation)
      : null;
    const contractWarnings: string[] = [];
    const traces: QueryTrace[] = [];
    const observed: EntityIds = { ability: [], item: [], move: [], pokemon: [] };
    const references = emptyReferences();
    const usage = emptyUsage();
    const toolErrors: AskDiagnostics["tool_errors"] = [];
    let costComplete = true;
    let failureClass: FailureClass | undefined;
    let graphqlAttempts = 0;
    let graphqlCalls = 0;
    let graphqlMs = 0;
    let modelAttempts = 0;
    let modelMs = 0;
    let phase: EvaluationPhase = "planning";
    let schemaLookupMs = 0;
    let schemaLookups = 0;

    const metrics = (): AskMetrics => {
      const uncachedInput = Math.max(
        0,
        usage.inputTokens - usage.cachedInputTokens - usage.cacheWriteTokens,
      );
      const estimatedCost =
        (uncachedInput * this.options.pricing.inputPerMillion +
          usage.cacheWriteTokens * this.options.pricing.cacheWritePerMillion +
          usage.cachedInputTokens * this.options.pricing.cachedInputPerMillion +
          usage.outputTokens * this.options.pricing.outputPerMillion) /
        1_000_000;
      return {
        cache_write_tokens: usage.cacheWriteTokens,
        cached_input_tokens: usage.cachedInputTokens,
        cost_complete: costComplete,
        estimated_cost_usd: estimatedCost,
        graphql_attempts: graphqlAttempts,
        graphql_calls: graphqlCalls,
        graphql_ms: Math.round(graphqlMs),
        input_tokens: usage.inputTokens,
        model_attempts: modelAttempts,
        model_calls: usage.modelCalls,
        model_ms: Math.round(modelMs),
        output_tokens: usage.outputTokens,
        schema_lookup_ms: Math.round(schemaLookupMs),
        schema_lookups: schemaLookups,
        tool_argument_normalizations: {
          calls: usage.toolArgumentNormalizations.calls,
          kinds: { ...usage.toolArgumentNormalizations.kinds },
        },
        total_ms: Math.round(performance.now() - started),
      };
    };

    const callModel = async <T>(action: () => Promise<T>): Promise<T> => {
      modelAttempts += 1;
      const callStarted = performance.now();
      try {
        return await action();
      } catch (error) {
        if (error instanceof ModelProviderError && error.usage) {
          addUsage(usage, error.usage);
          failureClass = "model";
        } else {
          costComplete = false;
          failureClass = "provider";
        }
        throw error;
      } finally {
        modelMs += performance.now() - callStarted;
      }
    };

    const executeToolCalls = async (calls: ToolCall[], round: number): Promise<void> => {
      for (const call of calls) {
        if (call.name === "schema_lookup") {
          phase = "schema_lookup";
          const toolStarted = performance.now();
          try {
            const result = await this.options.schemaLookup(call.arguments);
            history.push({ call, result });
            schemaLookups += 1;
          } catch (error) {
            failureClass = "model";
            const message = error instanceof Error ? error.message : "Unknown schema lookup failure";
            toolErrors.push({
              duration_ms: Math.round(performance.now() - toolStarted),
              message: message.slice(0, 500),
              name: call.name,
              round,
            });
            throw error;
          } finally {
            schemaLookupMs += performance.now() - toolStarted;
          }
          continue;
        }
        if (call.name === "execute_readonly_graphql") {
          phase = "graphql";
          if (graphqlAttempts >= (this.options.maxGraphqlAttempts ?? Number.POSITIVE_INFINITY)) {
            const message = "GraphQL attempt budget exhausted";
            history.push({ call, result: { error: message } });
            toolErrors.push({ duration_ms: 0, message, name: call.name, round });
            continue;
          }
          graphqlAttempts += 1;
          const toolStarted = performance.now();
          try {
            const execution = await this.options.executeGraphql(
              call.arguments as unknown as GraphqlRequest,
            );
            history.push({ call, result: execution.data });
            traces.push(execution.trace);
            mergeIds(observed, execution.entityIds);
            mergeReferences(
              references,
              execution.entityReferences ?? collectEntityReferences(execution.data),
            );
            graphqlCalls += 1;
          } catch (error) {
            if (error instanceof GraphqlInfrastructureError) failureClass = "pokeapi";
            const message = error instanceof Error ? error.message : "Unknown GraphQL failure";
            history.push({ call, result: { error: message.slice(0, 500) } });
            toolErrors.push({
              duration_ms: Math.round(performance.now() - toolStarted),
              message: message.slice(0, 500),
              name: call.name,
              round,
            });
          } finally {
            graphqlMs += performance.now() - toolStarted;
          }
          continue;
        }
        const unreachable: never = call.name;
        throw new Error(`Unsupported tool ${unreachable}`);
      }
    };

    try {
      if (structuredRequest) {
        phase = "graphql";
        graphqlAttempts += 1;
        const call: ToolCall = {
          arguments: structuredRequest as unknown as JsonObject,
          callId: "backend-structured-search",
          name: "execute_readonly_graphql",
        };
        const toolStarted = performance.now();
        try {
          const execution = await this.options.executeGraphql(structuredRequest);
          history.push({ call, result: execution.data });
          traces.push(execution.trace);
          mergeIds(observed, execution.entityIds);
          mergeReferences(
            references,
            execution.entityReferences ?? collectEntityReferences(execution.data),
          );
          graphqlCalls += 1;
        } catch (error) {
          if (error instanceof GraphqlInfrastructureError) failureClass = "pokeapi";
          const message = error instanceof Error ? error.message : "Unknown GraphQL failure";
          toolErrors.push({
            duration_ms: Math.round(performance.now() - toolStarted),
            message: message.slice(0, 500),
            name: call.name,
            round: 1,
          });
          throw error;
        } finally {
          graphqlMs += performance.now() - toolStarted;
        }
      } else {
        for (let round = 0; round < (this.options.maxToolRounds ?? 4); round += 1) {
          phase = "planning";
          const turn = await callModel(() =>
            this.options.model.plan({ entityResolutions, history, interpretation, question }),
          );
          addUsage(usage, turn.usage);
          if (turn.toolCalls.length === 0) {
            break;
          }

          await executeToolCalls(turn.toolCalls, round + 1);
        }
      }

      const synthesize = async () => {
        const synthesisEvidence =
          graphqlCalls > 0
            ? history.filter(({ call }) => call.name === "execute_readonly_graphql")
            : history;
        phase = "synthesis";
        const result = await callModel(() =>
          this.options.model.synthesize({
            entityResolutions,
            evidence: synthesisEvidence,
            interpretation,
            question,
          }),
        );
        addUsage(usage, result.usage);
        return result;
      };

      let synthesis = await synthesize();
      if (!structuredRequest && graphqlCalls === 0 && !isSafeNoToolRefusal(synthesis.response.answer)) {
        phase = "planning";
        const retryReason = isDefinitiveNotFound(synthesis.response.answer)
          ? "A definitive not-found conclusion requires a successful lookup. Perform one bounded lookup now; do not rely on memory."
          : "This question still has no successful lookup. Perform one bounded lookup now; do not rely on memory.";
        const retry = await callModel(() =>
          this.options.model.plan({
            entityResolutions,
            history,
            interpretation,
            question,
            retryReason,
          }),
        );
        addUsage(usage, retry.usage);
        await executeToolCalls(retry.toolCalls, (this.options.maxToolRounds ?? 4) + 1);
        if (graphqlCalls > 0) {
          synthesis = await synthesize();
        } else {
          synthesis.response = {
            ability_ids: [],
            answer: "I couldn't verify this because no lookup succeeded.",
            continuation_candidates: null,
            item_ids: [],
            move_ids: [],
            pokemon_ids: [],
            table: null,
          };
        }
      }
      synthesis.response.answer = discloseEntityResolutions(
        synthesis.response.answer,
        entityResolutions,
      );
      phase = "validation";
      if (!structuredRequest) validateIds(synthesis.response, observed, references, question);
      const continuationAllowed = interpretation?.status === "structured";
      if (synthesis.response.continuation_candidates && !continuationAllowed) {
        contractWarnings.push(
          "Ignored continuation candidates because the question was not a backend-recognized structured search",
        );
      }
      const resultIds = structuredRequest
        ? structuredResultIds(observed)
        : undefined;
      const firstPage = resultIds && hasEntityIds(resultIds)
        ? this.options.pagination?.firstPage(question, resultIds)
        : undefined;
      const pagination =
        firstPage?.pagination;
      const { continuation_candidates: _privateCandidates, ...publicResponse } = synthesis.response;

      return {
        diagnostics: {
          ...(contractWarnings.length > 0 ? { contract_warnings: contractWarnings } : {}),
          ...(failureClass ? { failure_class: failureClass } : {}),
          tool_errors: toolErrors,
        },
        evidence_entity_ids: observed,
        metrics: metrics(),
        response: {
          ...publicResponse,
          ...(resultIds
            ? {
                ability_ids: resultIds.ability.slice(0, 8),
                item_ids: resultIds.item.slice(0, 8),
                move_ids: resultIds.move.slice(0, 8),
                pokemon_ids: resultIds.pokemon.slice(0, 8),
              }
            : {}),
          ...(pagination ? { pagination } : {}),
          ...(interpretation ? { interpretation } : {}),
          queries: traces,
        },
      };
    } catch (error) {
      if (error instanceof AskEvaluationError) throw error;
      const message = error instanceof Error ? error.message : "Evaluation failed";
      const classifiedFailure = failureClass ?? (phase === "validation" ? "model" : "evaluator");
      throw new AskEvaluationError(message, {
        diagnostics: {
          ...(contractWarnings.length > 0 ? { contract_warnings: contractWarnings } : {}),
          failure_class: classifiedFailure,
          phase,
          tool_errors: toolErrors,
        },
        metrics: metrics(),
      });
    }
  }
}
