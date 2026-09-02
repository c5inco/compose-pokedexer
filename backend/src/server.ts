import { readFileSync } from "node:fs";
import { createServer } from "node:http";
import { resolve } from "node:path";

import { GoogleGenAI } from "@google/genai";
import { buildClientSchema, type IntrospectionQuery } from "graphql";
import OpenAI from "openai";

import { createApp } from "./app.js";
import { PRODUCT_EXECUTION_PROFILE } from "./execution-profile.js";
import { createGeminiProvider, type GeminiClient } from "./gemini-provider.js";
import { createOpenAIProvider, type ResponsesClient } from "./openai-provider.js";
import { createOpenRouterProvider } from "./openrouter-provider.js";
import type { ModelProvider } from "./orchestrator.js";
import { AskOrchestrator } from "./orchestrator.js";
import { createPaginationService, resolvePaginationConfig } from "./pagination.js";
import { createReadonlyGraphqlExecutor, type JsonValue } from "./readonly-graphql.js";
import { createRequestLimiter, resolveRequestLimitConfig } from "./request-limiter.js";
import { resolveRuntimeConfig } from "./runtime-config.js";
import { createSchemaLookup } from "./schema-lookup.js";

const runtime = resolveRuntimeConfig(process.env);
const port = Number(process.env.PORT ?? "8080");
const schemaPath =
  process.env.POKEAPI_SCHEMA_PATH ??
  resolve(
    import.meta.dirname,
    "../../shared/src/commonMain/graphql/des.c5inco.pokedexer.shared/schema.json",
  );
const introspection = JSON.parse(readFileSync(schemaPath, "utf8")) as { data: IntrospectionQuery };
const schema = buildClientSchema(introspection.data);

let provider: ModelProvider;
if (runtime.provider === "gemini") {
  const gemini = new GoogleGenAI({
    apiKey: runtime.apiKey,
    httpOptions: {
      retryOptions: { attempts: PRODUCT_EXECUTION_PROFILE.model.max_retries + 1 },
      timeout: PRODUCT_EXECUTION_PROFILE.model.timeout_ms,
    },
  });
  provider = createGeminiProvider({
    client: gemini as unknown as GeminiClient,
    model: runtime.model,
  });
} else if (runtime.provider === "openai") {
  const openai = new OpenAI({
    apiKey: runtime.apiKey,
    maxRetries: PRODUCT_EXECUTION_PROFILE.model.max_retries,
    timeout: PRODUCT_EXECUTION_PROFILE.model.timeout_ms,
  });
  provider = createOpenAIProvider({
    client: openai as unknown as ResponsesClient,
    model: runtime.model,
    reasoningEffort: runtime.openAiReasoningEffort,
  });
} else {
  const openrouter = new OpenAI({
    apiKey: runtime.apiKey,
    baseURL: "https://openrouter.ai/api/v1",
    maxRetries: PRODUCT_EXECUTION_PROFILE.model.max_retries,
    timeout: PRODUCT_EXECUTION_PROFILE.model.timeout_ms,
  });
  provider = createOpenRouterProvider({
    client: openrouter as unknown as ResponsesClient,
    model: runtime.model,
    reasoningEffort: runtime.openRouterReasoningEffort,
  });
}
const graphql = createReadonlyGraphqlExecutor({
  endpoint: "https://graphql.pokeapi.co/v1beta2",
  maxComplexity: PRODUCT_EXECUTION_PROFILE.graphql.max_complexity,
  maxDepth: PRODUCT_EXECUTION_PROFILE.graphql.max_depth,
  maxResponseBytes: PRODUCT_EXECUTION_PROFILE.graphql.max_response_bytes,
  maxRows: PRODUCT_EXECUTION_PROFILE.graphql.max_rows,
  schema,
  timeoutMs: PRODUCT_EXECUTION_PROFILE.graphql.timeout_ms,
});
const lookup = createSchemaLookup(schema);
const pagination = createPaginationService(resolvePaginationConfig(process.env));
const orchestrator = new AskOrchestrator({
  executeGraphql: graphql.execute,
  maxGraphqlAttempts: PRODUCT_EXECUTION_PROFILE.max_graphql_attempts,
  maxToolRounds: PRODUCT_EXECUTION_PROFILE.max_tool_rounds,
  model: provider,
  pagination,
  pricing: runtime.pricing,
  schemaLookup: async (request) => (await lookup(request)) as unknown as JsonValue,
});

const server = createServer(
  createApp({
    ask: (question, cursor, signal) =>
      cursor ? orchestrator.continue(question, cursor) : orchestrator.ask(question, signal),
    ...(process.env.ENABLE_EVALUATION_ROUTES === "true"
      ? { evaluation: { provider: runtime.providerLabel } }
      : {}),
    requestLimiter: createRequestLimiter(resolveRequestLimitConfig(process.env)),
    requestTimeoutMs: PRODUCT_EXECUTION_PROFILE.request_timeout_ms,
  }),
);
server.listen(port, "0.0.0.0", () => {
  console.log(`Ask Pokedexer API listening on port ${port}`);
});

process.on("SIGTERM", () => {
  server.close(() => process.exit(0));
});
