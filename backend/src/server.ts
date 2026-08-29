import { readFileSync } from "node:fs";
import { createServer } from "node:http";
import { resolve } from "node:path";

import { GoogleGenAI } from "@google/genai";
import { buildClientSchema, type IntrospectionQuery } from "graphql";
import OpenAI from "openai";

import { createApp } from "./app.js";
import { EXECUTION_PROFILE } from "./execution-profile.js";
import { createGeminiProvider, type GeminiClient } from "./gemini-provider.js";
import { createOpenAIProvider, type ResponsesClient } from "./openai-provider.js";
import { createOpenRouterProvider } from "./openrouter-provider.js";
import type { ModelProvider } from "./orchestrator.js";
import { AskOrchestrator } from "./orchestrator.js";
import { createPaginationService, resolvePaginationConfig } from "./pagination.js";
import { createReadonlyGraphqlExecutor, type JsonValue } from "./readonly-graphql.js";
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
      retryOptions: { attempts: EXECUTION_PROFILE.model.max_retries + 1 },
      timeout: EXECUTION_PROFILE.model.timeout_ms,
    },
  });
  provider = createGeminiProvider({
    client: gemini as unknown as GeminiClient,
    model: runtime.model,
  });
} else if (runtime.provider === "openai") {
  const openai = new OpenAI({
    apiKey: runtime.apiKey,
    maxRetries: EXECUTION_PROFILE.model.max_retries,
    timeout: EXECUTION_PROFILE.model.timeout_ms,
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
    maxRetries: EXECUTION_PROFILE.model.max_retries,
    timeout: EXECUTION_PROFILE.model.timeout_ms,
  });
  provider = createOpenRouterProvider({
    client: openrouter as unknown as ResponsesClient,
    model: runtime.model,
    reasoningEffort: runtime.openRouterReasoningEffort,
  });
}
const graphql = createReadonlyGraphqlExecutor({
  endpoint: "https://graphql.pokeapi.co/v1beta2",
  maxComplexity: EXECUTION_PROFILE.graphql.max_complexity,
  maxDepth: EXECUTION_PROFILE.graphql.max_depth,
  maxResponseBytes: EXECUTION_PROFILE.graphql.max_response_bytes,
  maxRows: EXECUTION_PROFILE.graphql.max_rows,
  schema,
  timeoutMs: EXECUTION_PROFILE.graphql.timeout_ms,
});
const lookup = createSchemaLookup(schema);
const pagination = createPaginationService(resolvePaginationConfig(process.env));
const orchestrator = new AskOrchestrator({
  executeGraphql: graphql.execute,
  maxGraphqlAttempts: EXECUTION_PROFILE.max_graphql_attempts,
  maxToolRounds: EXECUTION_PROFILE.max_tool_rounds,
  model: provider,
  pagination,
  pricing: runtime.pricing,
  schemaLookup: async (request) => (await lookup(request)) as unknown as JsonValue,
});

const server = createServer(
  createApp({
    ask: (question, cursor) =>
      cursor ? orchestrator.continue(question, cursor) : orchestrator.ask(question),
    model: runtime.model,
    provider: runtime.providerLabel,
  }),
);
server.listen(port, "0.0.0.0", () => {
  console.log(
    `Ask Pokedexer evaluation server listening on port ${port} with ${runtime.providerLabel} ${runtime.model}`,
  );
});

process.on("SIGTERM", () => {
  server.close(() => process.exit(0));
});
