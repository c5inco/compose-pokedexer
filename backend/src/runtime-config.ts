interface Environment {
  GEMINI_API_KEY?: string;
  GEMINI_MODEL?: string;
  MODEL_PROVIDER?: string;
  OPENAI_API_KEY?: string;
  OPENAI_MODEL?: string;
  OPENAI_REASONING_EFFORT?: string;
  OPENROUTER_API_KEY?: string;
  OPENROUTER_MODEL?: string;
  OPENROUTER_REASONING_EFFORT?: string;
}

type OpenAIReasoningEffort = "none" | "low" | "medium" | "high" | "xhigh" | "max";
type OpenRouterReasoningEffort = "none" | "minimal" | "low" | "medium" | "high" | "xhigh";

interface Pricing {
  cacheWritePerMillion: number;
  cachedInputPerMillion: number;
  inputPerMillion: number;
  outputPerMillion: number;
}

interface RuntimeConfig {
  apiKey: string;
  model: string;
  openAiReasoningEffort?: OpenAIReasoningEffort;
  openRouterReasoningEffort?: OpenRouterReasoningEffort;
  pricing: Pricing;
  provider: "gemini" | "openai" | "openrouter";
  providerLabel: "Gemini" | "OpenAI" | "OpenRouter";
}

function openAiPricing(model: string): Pricing {
  if (model === "gpt-5.6-luna") {
    return {
      cacheWritePerMillion: 0.25,
      cachedInputPerMillion: 0.02,
      inputPerMillion: 0.2,
      outputPerMillion: 1.2,
    };
  }
  if (model === "gpt-5.6-terra") {
    return {
      cacheWritePerMillion: 2.5,
      cachedInputPerMillion: 0.2,
      inputPerMillion: 2,
      outputPerMillion: 12,
    };
  }
  if (model === "gpt-5.6-sol") {
    return {
      cacheWritePerMillion: 5,
      cachedInputPerMillion: 0.4,
      inputPerMillion: 4,
      outputPerMillion: 20,
    };
  }
  throw new Error(`OPENAI_MODEL ${model} has no configured pricing`);
}

function openAiReasoningEffort(value: string | undefined): OpenAIReasoningEffort {
  const effort = value ?? "low";
  if (["none", "low", "medium", "high", "xhigh", "max"].includes(effort)) {
    return effort as OpenAIReasoningEffort;
  }
  throw new Error("OPENAI_REASONING_EFFORT must be none, low, medium, high, xhigh, or max");
}

function openRouterPricing(model: string): Pricing {
  if (model === "z-ai/glm-5.3-flash") {
    return {
      cacheWritePerMillion: 0,
      cachedInputPerMillion: 0.015,
      inputPerMillion: 0.075,
      outputPerMillion: 0.25,
    };
  }
  if (model === "qwen/qwen3.8-flash") {
    return {
      cacheWritePerMillion: 0.2,
      cachedInputPerMillion: 0.016,
      inputPerMillion: 0.15,
      outputPerMillion: 0.47,
    };
  }
  throw new Error(`OPENROUTER_MODEL ${model} has no configured pricing`);
}

function openRouterReasoningEffort(value: string | undefined): OpenRouterReasoningEffort {
  const effort = value ?? "low";
  if (["none", "minimal", "low", "medium", "high", "xhigh"].includes(effort)) {
    return effort as OpenRouterReasoningEffort;
  }
  throw new Error(
    "OPENROUTER_REASONING_EFFORT must be none, minimal, low, medium, high, or xhigh",
  );
}

function geminiPricing(model: string): Pricing {
  if (model === "gemini-3.5-flash-lite") {
    return {
      cacheWritePerMillion: 0,
      cachedInputPerMillion: 0.03,
      inputPerMillion: 0.3,
      outputPerMillion: 2.5,
    };
  }
  if (model === "gemini-3.5-flash") {
    return {
      cacheWritePerMillion: 0,
      cachedInputPerMillion: 0.15,
      inputPerMillion: 1.5,
      outputPerMillion: 9,
    };
  }
  return {
    cacheWritePerMillion: 0,
    cachedInputPerMillion: 0.075,
    inputPerMillion: 0.75,
    outputPerMillion: 3.75,
  };
}

export function resolveRuntimeConfig(environment: Environment): RuntimeConfig {
  const provider = environment.MODEL_PROVIDER ?? "openai";
  if (provider !== "openai" && provider !== "gemini" && provider !== "openrouter") {
    throw new Error("MODEL_PROVIDER must be openai, gemini, or openrouter");
  }

  if (provider === "gemini") {
    if (!environment.GEMINI_API_KEY) throw new Error("GEMINI_API_KEY is required");
    const model = environment.GEMINI_MODEL ?? "gemini-3.7-flash";
    return {
      apiKey: environment.GEMINI_API_KEY,
      model,
      pricing: geminiPricing(model),
      provider,
      providerLabel: "Gemini",
    };
  }

  if (provider === "openrouter") {
    if (!environment.OPENROUTER_API_KEY) throw new Error("OPENROUTER_API_KEY is required");
    const model = environment.OPENROUTER_MODEL ?? "z-ai/glm-5.3-flash";
    return {
      apiKey: environment.OPENROUTER_API_KEY,
      model,
      openRouterReasoningEffort: openRouterReasoningEffort(
        environment.OPENROUTER_REASONING_EFFORT,
      ),
      pricing: openRouterPricing(model),
      provider,
      providerLabel: "OpenRouter",
    };
  }

  if (!environment.OPENAI_API_KEY) throw new Error("OPENAI_API_KEY is required");
  const model = environment.OPENAI_MODEL ?? "gpt-5.6-luna";
  return {
    apiKey: environment.OPENAI_API_KEY,
    model,
    openAiReasoningEffort: openAiReasoningEffort(environment.OPENAI_REASONING_EFFORT),
    pricing: openAiPricing(model),
    provider,
    providerLabel: "OpenAI",
  };
}
