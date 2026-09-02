import {
  evidenceJson,
  functionDeclarations,
  parseSynthesis,
  parseStructuredSynthesis,
  parseToolCall,
  plannerInstructions,
  responseJsonSchema,
  structuredResponseJsonSchema,
  structuredSynthesisInstructions,
  synthesisInstructions,
} from "./model-contract.js";
import {
  ModelProviderError,
  type ModelProvider,
  type PlannerTurn,
  type TokenUsage,
  type ToolArgumentNormalizationKind,
} from "./orchestrator.js";
import { EXECUTION_PROFILE } from "./execution-profile.js";

interface OpenAIResponse {
  output: Array<{
    arguments?: string;
    call_id?: string;
    name?: string;
    type: string;
  }>;
  output_text?: string;
  status?: string;
  usage?: {
    input_tokens: number;
    input_tokens_details?: { cache_write_tokens?: number; cached_tokens?: number };
    output_tokens: number;
  } | null;
}

export interface ResponsesClient {
  responses: {
    create(
      request: Record<string, unknown>,
      options?: { signal?: AbortSignal | null },
    ): Promise<OpenAIResponse>;
  };
}

export interface NormalizedToolArguments {
  arguments: unknown;
  kinds: ToolArgumentNormalizationKind[];
}

export type ResponsesReasoningEffort = "none" | "minimal" | "low" | "medium" | "high" | "xhigh" | "max";

export interface OpenAIProviderOptions {
  client: ResponsesClient;
  includeParallelToolCalls?: boolean;
  model: string;
  normalizeToolArguments?: (name: string, rawArguments: unknown) => NormalizedToolArguments;
  providerLabel?: "OpenAI" | "OpenRouter";
  reasoningEffort?: ResponsesReasoningEffort;
  requireParameters?: boolean;
}

function usageFrom(response: OpenAIResponse): TokenUsage {
  return {
    cacheWriteTokens: response.usage?.input_tokens_details?.cache_write_tokens ?? 0,
    cachedInputTokens: response.usage?.input_tokens_details?.cached_tokens ?? 0,
    inputTokens: response.usage?.input_tokens ?? 0,
    outputTokens: response.usage?.output_tokens ?? 0,
  };
}

function assertCompleted(response: OpenAIResponse, providerLabel: string): void {
  if (response.status && response.status !== "completed") {
    throw new Error(`${providerLabel} response ended with status ${response.status}`);
  }
}

function parseOpenAIToolCall(
  item: OpenAIResponse["output"][number],
  providerLabel: string,
  usage: TokenUsage,
  normalizeToolArguments?: (name: string, rawArguments: unknown) => NormalizedToolArguments,
) {
  if (!item.arguments || !item.call_id || !item.name) {
    throw new Error(`${providerLabel} returned an incomplete function call`);
  }
  const rawArguments = JSON.parse(item.arguments) as unknown;
  const normalized = normalizeToolArguments?.(item.name, rawArguments);
  if (normalized && normalized.kinds.length > 0) {
    const kinds = new Set(normalized.kinds);
    usage.toolArgumentNormalizations ??= {
      calls: 0,
      kinds: { non_string_value_json: 0, variables_object_map: 0 },
    };
    usage.toolArgumentNormalizations.calls += 1;
    for (const kind of kinds) usage.toolArgumentNormalizations.kinds[kind] += 1;
  }
  return parseToolCall(item.name, normalized?.arguments ?? rawArguments, item.call_id);
}

export function createOpenAIProvider(options: OpenAIProviderOptions): ModelProvider {
  const providerLabel = options.providerLabel ?? "OpenAI";
  const parallelToolCalls = options.includeParallelToolCalls === false
    ? {}
    : { parallel_tool_calls: false };
  const providerRouting = options.requireParameters
    ? { provider: { require_parameters: true } }
    : {};
  const reasoning = { effort: options.reasoningEffort ?? "low" };
  return {
    async plan(input): Promise<PlannerTurn> {
      const response = await options.client.responses.create(
        {
          input: `Question: ${input.question}\nBackend-reviewed entity resolutions: ${evidenceJson(input.entityResolutions ?? [])}\nBackend-required retry: ${evidenceJson(input.retryReason ?? null)}\nBackend-reviewed search interpretation: ${evidenceJson(input.interpretation ?? null)}\nExecuted tool history: ${evidenceJson(input.history)}`,
          instructions: plannerInstructions,
          max_output_tokens: EXECUTION_PROFILE.model.planning_output_tokens,
          model: options.model,
          ...parallelToolCalls,
          ...providerRouting,
          reasoning,
          store: false,
          tool_choice: "auto",
          tools: structuredClone(functionDeclarations).map((declaration) => ({
            ...declaration,
            strict: true,
            type: "function",
          })),
        },
        { signal: input.signal },
      );
      const usage = usageFrom(response);
      if (options.normalizeToolArguments) {
        usage.toolArgumentNormalizations = {
          calls: 0,
          kinds: { non_string_value_json: 0, variables_object_map: 0 },
        };
      }
      try {
        assertCompleted(response, providerLabel);
        return {
          toolCalls: response.output
            .filter((item) => item.type === "function_call")
            .map((item) =>
              parseOpenAIToolCall(item, providerLabel, usage, options.normalizeToolArguments)
            ),
          usage,
        };
      } catch (error) {
        throw new ModelProviderError(
          error instanceof Error ? error.message : `${providerLabel} planning response was invalid`,
          usage,
        );
      }
    },

    async synthesize(input) {
      const structured = input.interpretation?.status === "structured";
      const response = await options.client.responses.create(
        {
          input: `Question: ${input.question}\nBackend-reviewed entity resolutions: ${evidenceJson(input.entityResolutions ?? [])}\nBackend-reviewed search interpretation: ${evidenceJson(input.interpretation ?? null)}\nVerified tool evidence: ${evidenceJson(input.evidence)}`,
          instructions: structured ? structuredSynthesisInstructions : synthesisInstructions,
          max_output_tokens: EXECUTION_PROFILE.model.synthesis_output_tokens,
          model: options.model,
          ...providerRouting,
          reasoning,
          store: false,
          text: {
            format: {
              name: "ask_pokedexer_response",
              schema: structured ? structuredResponseJsonSchema : responseJsonSchema,
              strict: true,
              type: "json_schema",
            },
          },
        },
        { signal: input.signal },
      );
      const usage = usageFrom(response);
      try {
        assertCompleted(response, providerLabel);
        if (!response.output_text) throw new Error(`${providerLabel} synthesis returned no output text`);
        return {
          response: structured
            ? parseStructuredSynthesis(response.output_text)
            : parseSynthesis(response.output_text),
          usage,
        };
      } catch (error) {
        throw new ModelProviderError(
          error instanceof Error ? error.message : `${providerLabel} synthesis response was invalid`,
          usage,
        );
      }
    },
  };
}
