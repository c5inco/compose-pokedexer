import {
  createOpenAIProvider,
  type OpenAIProviderOptions,
} from "./openai-provider.js";
import type { ModelProvider } from "./orchestrator.js";

type OpenRouterProviderOptions = Omit<OpenAIProviderOptions, "providerLabel" | "requireParameters">;

export function createOpenRouterProvider(options: OpenRouterProviderOptions): ModelProvider {
  return createOpenAIProvider({
    ...options,
    includeParallelToolCalls: false,
    providerLabel: "OpenRouter",
    requireParameters: true,
  });
}
