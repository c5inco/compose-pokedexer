import assert from "node:assert/strict";
import test from "node:test";

import { createGeminiProvider } from "../src/gemini-provider.js";
import { createOpenAIProvider } from "../src/openai-provider.js";

function uppercaseSchemaTypes(value: unknown): void {
  if (Array.isArray(value)) {
    value.forEach(uppercaseSchemaTypes);
    return;
  }
  if (!value || typeof value !== "object") return;
  const object = value as Record<string, unknown>;
  if (typeof object.type === "string") object.type = object.type.toUpperCase();
  Object.values(object).forEach(uppercaseSchemaTypes);
}

test("keeps OpenAI tool declarations isolated from Gemini SDK normalization", async () => {
  const gemini = createGeminiProvider({
    client: {
      models: {
        async generateContent(request) {
          uppercaseSchemaTypes(request);
          return { functionCalls: [], usageMetadata: {} };
        },
      },
    },
    model: "gemini-3.7-flash",
  });
  await gemini.plan({ history: [], question: "What type is Bulbasaur?" });

  let openAiRequest: Record<string, unknown> | undefined;
  const openai = createOpenAIProvider({
    client: {
      responses: {
        async create(request) {
          openAiRequest = request;
          return { output: [], status: "completed", usage: null };
        },
      },
    },
    model: "gpt-5.6-luna",
  });
  await openai.plan({ history: [], question: "What type is Bulbasaur?" });

  const tools = openAiRequest?.tools as Array<{
    name: string;
    parameters: { properties: Record<string, { type?: string }> };
  }>;
  const lookup = tools.find((tool) => tool.name === "schema_lookup");
  assert.equal(lookup?.parameters.properties.detail.type, "string");
  assert.equal(lookup?.parameters.properties.field_limit.type, "integer");
});
