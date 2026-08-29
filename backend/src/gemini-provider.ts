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
import { ModelProviderError, type ModelProvider, type PlannerTurn, type TokenUsage } from "./orchestrator.js";
import { EXECUTION_PROFILE } from "./execution-profile.js";

interface GeminiResponse {
  functionCalls?: Array<{
    args?: unknown;
    name?: string;
  }>;
  text?: string;
  usageMetadata?: {
    cachedContentTokenCount?: number;
    candidatesTokenCount?: number;
    promptTokenCount?: number;
    thoughtsTokenCount?: number;
  };
}

export interface GeminiClient {
  models: {
    generateContent(request: Record<string, unknown>): Promise<GeminiResponse>;
  };
}

interface ProviderOptions {
  client: GeminiClient;
  model: string;
  thinkingLevel?: "LOW";
}

function usageFrom(response: GeminiResponse): TokenUsage {
  const usage = response.usageMetadata;
  return {
    cacheWriteTokens: 0,
    cachedInputTokens: usage?.cachedContentTokenCount ?? 0,
    inputTokens: usage?.promptTokenCount ?? 0,
    outputTokens: (usage?.candidatesTokenCount ?? 0) + (usage?.thoughtsTokenCount ?? 0),
  };
}

export function createGeminiProvider(options: ProviderOptions): ModelProvider {
  const thinkingConfig = { thinkingLevel: options.thinkingLevel ?? "LOW" };
  return {
    async plan(input): Promise<PlannerTurn> {
      const response = await options.client.models.generateContent({
        config: {
          maxOutputTokens: EXECUTION_PROFILE.model.planning_output_tokens,
          systemInstruction: plannerInstructions,
          thinkingConfig,
          toolConfig: { functionCallingConfig: { mode: "VALIDATED" } },
          tools: [{ functionDeclarations: structuredClone(functionDeclarations) }],
        },
        contents: `Question: ${input.question}\nBackend-reviewed entity resolutions: ${evidenceJson(input.entityResolutions ?? [])}\nBackend-required retry: ${evidenceJson(input.retryReason ?? null)}\nBackend-reviewed search interpretation: ${evidenceJson(input.interpretation ?? null)}\nExecuted tool history: ${evidenceJson(input.history)}`,
        model: options.model,
      });
      const usage = usageFrom(response);
      try {
        return {
          toolCalls: (response.functionCalls ?? []).map((call, index) => {
            if (!call.name || !call.args) throw new Error("Gemini returned an incomplete function call");
            return parseToolCall(call.name, call.args, `gemini-call-${index + 1}`);
          }),
          usage,
        };
      } catch (error) {
        throw new ModelProviderError(
          error instanceof Error ? error.message : "Gemini planning response was invalid",
          usage,
        );
      }
    },

    async synthesize(input) {
      const structured = input.interpretation?.status === "structured";
      const response = await options.client.models.generateContent({
        config: {
          maxOutputTokens: EXECUTION_PROFILE.model.synthesis_output_tokens,
          responseJsonSchema: structured ? structuredResponseJsonSchema : responseJsonSchema,
          responseMimeType: "application/json",
          systemInstruction: structured ? structuredSynthesisInstructions : synthesisInstructions,
          thinkingConfig,
        },
        contents: `Question: ${input.question}\nBackend-reviewed entity resolutions: ${evidenceJson(input.entityResolutions ?? [])}\nBackend-reviewed search interpretation: ${evidenceJson(input.interpretation ?? null)}\nVerified tool evidence: ${evidenceJson(input.evidence)}`,
        model: options.model,
      });
      const usage = usageFrom(response);
      try {
        if (!response.text) throw new Error("Gemini synthesis returned no output text");
        return {
          response: structured ? parseStructuredSynthesis(response.text) : parseSynthesis(response.text),
          usage,
        };
      } catch (error) {
        throw new ModelProviderError(
          error instanceof Error ? error.message : "Gemini synthesis response was invalid",
          usage,
        );
      }
    },
  };
}
