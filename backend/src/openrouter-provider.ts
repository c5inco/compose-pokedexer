import {
  createOpenAIProvider,
  type NormalizedToolArguments,
  type OpenAIProviderOptions,
} from "./openai-provider.js";
import type { ModelProvider } from "./orchestrator.js";

type OpenRouterProviderOptions = Omit<
  OpenAIProviderOptions,
  "normalizeToolArguments" | "providerLabel" | "requireParameters"
>;

function encodeValueJson(value: unknown): string {
  const encoded = JSON.stringify(value);
  if (typeof encoded !== "string") {
    throw new Error("Unsupported GraphQL variable value");
  }
  return encoded;
}

function normalizeVariableEntry(entry: unknown): unknown {
  if (entry === null || typeof entry !== "object" || Array.isArray(entry)) return entry;
  if (!Object.hasOwn(entry, "value_json")) return entry;
  const record = entry as Record<string, unknown>;
  if (typeof record.value_json === "string") return entry;
  return { ...record, value_json: encodeValueJson(record.value_json) };
}

export function normalizeOpenRouterToolArguments(
  name: string,
  rawArguments: unknown,
): NormalizedToolArguments {
  if (name !== "execute_readonly_graphql") return { arguments: rawArguments, kinds: [] };
  if (rawArguments === null || typeof rawArguments !== "object" || Array.isArray(rawArguments)) {
    return { arguments: rawArguments, kinds: [] };
  }
  if (!Object.hasOwn(rawArguments, "variables")) return { arguments: rawArguments, kinds: [] };
  const args = rawArguments as Record<string, unknown>;
  const variables = args.variables;
  if (Array.isArray(variables)) {
    const requiresNormalization = variables.some(
      (entry) =>
        entry !== null &&
        typeof entry === "object" &&
        !Array.isArray(entry) &&
        Object.hasOwn(entry, "value_json") &&
        typeof (entry as Record<string, unknown>).value_json !== "string",
    );
    return {
      arguments: requiresNormalization
        ? { ...args, variables: variables.map(normalizeVariableEntry) }
        : rawArguments,
      kinds: requiresNormalization ? ["non_string_value_json"] : [],
    };
  }
  if (variables !== null && typeof variables === "object") {
    return {
      arguments: {
        ...args,
        variables: Object.entries(variables).map(([variableName, value]) => ({
          name: variableName,
          value_json: encodeValueJson(value),
        })),
      },
      kinds: ["variables_object_map"],
    };
  }
  return { arguments: rawArguments, kinds: [] };
}

export function createOpenRouterProvider(options: OpenRouterProviderOptions): ModelProvider {
  return createOpenAIProvider({
    ...options,
    includeParallelToolCalls: false,
    normalizeToolArguments: normalizeOpenRouterToolArguments,
    providerLabel: "OpenRouter",
    requireParameters: true,
  });
}
