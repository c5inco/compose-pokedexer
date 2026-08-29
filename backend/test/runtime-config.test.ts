import assert from "node:assert/strict";
import test from "node:test";

import { resolveRuntimeConfig } from "../src/runtime-config.js";

const credential = ["fixture", "credential"].join("-");

function environmentWith(nameParts: string[]): Record<string, string> {
  return { [nameParts.join("_")]: credential };
}

test("uses OpenAI defaults without requiring a Gemini key", () => {
  assert.deepEqual(resolveRuntimeConfig(environmentWith(["OPENAI", "API", "KEY"])), {
    apiKey: credential,
    model: "gpt-5.6-luna",
    openAiReasoningEffort: "low",
    pricing: {
      cacheWritePerMillion: 0.25,
      cachedInputPerMillion: 0.02,
      inputPerMillion: 0.2,
      outputPerMillion: 1.2,
    },
    provider: "openai",
    providerLabel: "OpenAI",
  });
});

test("selects Terra and a configured OpenAI reasoning effort with model-specific pricing", () => {
  assert.deepEqual(
    resolveRuntimeConfig({
      ...environmentWith(["OPENAI", "API", "KEY"]),
      OPENAI_MODEL: "gpt-5.6-terra",
      OPENAI_REASONING_EFFORT: "medium",
    }),
    {
      apiKey: credential,
      model: "gpt-5.6-terra",
      openAiReasoningEffort: "medium",
      pricing: {
        cacheWritePerMillion: 2.5,
        cachedInputPerMillion: 0.2,
        inputPerMillion: 2,
        outputPerMillion: 12,
      },
      provider: "openai",
      providerLabel: "OpenAI",
    },
  );
});

test("rejects an unsupported OpenAI reasoning effort", () => {
  assert.throws(
    () =>
      resolveRuntimeConfig({
        ...environmentWith(["OPENAI", "API", "KEY"]),
        OPENAI_REASONING_EFFORT: "extreme",
      }),
    /OPENAI_REASONING_EFFORT/,
  );
});

test("selects Gemini 3.5 Flash-Lite with its cheaper model-specific pricing", () => {
  assert.deepEqual(
    resolveRuntimeConfig({
      ...environmentWith(["GEMINI", "API", "KEY"]),
      GEMINI_MODEL: "gemini-3.5-flash-lite",
      MODEL_PROVIDER: "gemini",
    }),
    {
      apiKey: credential,
      model: "gemini-3.5-flash-lite",
      pricing: {
        cacheWritePerMillion: 0,
        cachedInputPerMillion: 0.03,
        inputPerMillion: 0.3,
        outputPerMillion: 2.5,
      },
      provider: "gemini",
      providerLabel: "Gemini",
    },
  );
});

test("uses the current promotional pricing for Gemini 3.6 Flash", () => {
  const config = resolveRuntimeConfig({
    ...environmentWith(["GEMINI", "API", "KEY"]),
    GEMINI_MODEL: "gemini-3.6-flash",
    MODEL_PROVIDER: "gemini",
  });

  assert.deepEqual(config.pricing, {
    cacheWritePerMillion: 0,
    cachedInputPerMillion: 0.075,
    inputPerMillion: 0.75,
    outputPerMillion: 3.75,
  });
});

test("selects OpenRouter GLM with its exact model slug and pricing", () => {
  assert.deepEqual(
    resolveRuntimeConfig({
      MODEL_PROVIDER: "openrouter",
      OPENROUTER_API_KEY: credential,
      OPENROUTER_MODEL: "z-ai/glm-5.3-flash",
    }),
    {
      apiKey: credential,
      model: "z-ai/glm-5.3-flash",
      openRouterReasoningEffort: "low",
      pricing: {
        cacheWritePerMillion: 0,
        cachedInputPerMillion: 0.015,
        inputPerMillion: 0.075,
        outputPerMillion: 0.25,
      },
      provider: "openrouter",
      providerLabel: "OpenRouter",
    },
  );
});

test("selects OpenRouter Qwen with model-specific cache-write pricing", () => {
  const config = resolveRuntimeConfig({
    MODEL_PROVIDER: "openrouter",
    OPENROUTER_API_KEY: credential,
    OPENROUTER_MODEL: "qwen/qwen3.8-flash",
  });

  assert.deepEqual(config.pricing, {
    cacheWritePerMillion: 0.2,
    cachedInputPerMillion: 0.016,
    inputPerMillion: 0.15,
    outputPerMillion: 0.47,
  });
  assert.equal(config.openRouterReasoningEffort, "low");
});

test("rejects unsupported OpenRouter models and reasoning efforts", () => {
  assert.throws(
    () =>
      resolveRuntimeConfig({
        MODEL_PROVIDER: "openrouter",
        OPENROUTER_API_KEY: credential,
        OPENROUTER_MODEL: "openai/gpt-5.6-luna",
      }),
    /OPENROUTER_MODEL openai\/gpt-5.6-luna has no configured pricing/,
  );
  assert.throws(
    () =>
      resolveRuntimeConfig({
        MODEL_PROVIDER: "openrouter",
        OPENROUTER_API_KEY: credential,
        OPENROUTER_REASONING_EFFORT: "extreme",
      }),
    /OPENROUTER_REASONING_EFFORT/,
  );
});

test("rejects an unsupported provider before server startup", () => {
  assert.throws(
    () =>
      resolveRuntimeConfig({
        ...environmentWith(["OPENAI", "API", "KEY"]),
        MODEL_PROVIDER: "other",
      }),
    /MODEL_PROVIDER must be openai, gemini, or openrouter/,
  );
});
