import assert from "node:assert/strict";
import test from "node:test";

import { createGeminiProvider } from "../src/gemini-provider.js";
import { ModelProviderError } from "../src/orchestrator.js";

test("converts Gemini function calls into provider-neutral tool calls", async () => {
  const requests: Array<Record<string, unknown>> = [];
  const client = {
    models: {
      async generateContent(request: Record<string, unknown>) {
        requests.push(request);
        return {
          functionCalls: [
            {
              args: {
                purpose: "Resolve Razor Leaf",
                query:
                  "query Move($name: String!) { move(where: {name: {_eq: $name}}, limit: 1) { id name } }",
                variables: [
                  { name: "name", value_json: '"razor-leaf"' },
                  { name: "typeName", value_json: "grass" },
                ],
              },
              name: "execute_readonly_graphql",
            },
          ],
          usageMetadata: {
            cachedContentTokenCount: 25,
            candidatesTokenCount: 20,
            promptTokenCount: 100,
            thoughtsTokenCount: 5,
          },
        };
      },
    },
  };
  const provider = createGeminiProvider({ client, model: "gemini-3.7-flash" });

  const turn = await provider.plan({ history: [], question: "Who learns Razor Leaf?" });

  assert.deepEqual(turn.toolCalls[0].arguments, {
    purpose: "Resolve Razor Leaf",
    query:
      "query Move($name: String!) { move(where: {name: {_eq: $name}}, limit: 1) { id name } }",
    variables: { name: "razor-leaf", typeName: "grass" },
  });
  assert.deepEqual(turn.usage, {
    cacheWriteTokens: 0,
    cachedInputTokens: 25,
    inputTokens: 100,
    outputTokens: 25,
  });
  const config = requests[0].config as Record<string, unknown>;
  assert.equal(requests[0].model, "gemini-3.7-flash");
  assert.match(String(config.systemInstruction), /SpeciesWithMove/);
  assert.deepEqual(config.thinkingConfig, { thinkingLevel: "LOW" });
  assert.equal(config.responseMimeType, undefined);
  const tools = config.tools as Array<{ functionDeclarations: Array<{ name: string }> }>;
  assert.deepEqual(
    tools[0].functionDeclarations.map((tool) => tool.name),
    ["schema_lookup", "execute_readonly_graphql"],
  );
});

test("uses a separate tools-disabled Gemini structured-output call", async () => {
  const requests: Array<Record<string, unknown>> = [];
  const response = {
    ability_ids: [144],
    answer: "Regenerator restores HP when the Pokémon is withdrawn.",
    continuation_candidates: null,
    item_ids: [],
    move_ids: [],
    pokemon_ids: [],
    table: null,
  };
  const client = {
    models: {
      async generateContent(request: Record<string, unknown>) {
        requests.push(request);
        return {
          text: JSON.stringify(response),
          usageMetadata: {
            candidatesTokenCount: 30,
            promptTokenCount: 80,
            thoughtsTokenCount: 10,
          },
        };
      },
    },
  };
  const provider = createGeminiProvider({ client, model: "gemini-3.7-flash" });

  const result = await provider.synthesize({
    evidence: [],
    question: "What does Regenerator do?",
  });

  assert.deepEqual(result.response, response);
  assert.deepEqual(result.usage, {
    cacheWriteTokens: 0,
    cachedInputTokens: 0,
    inputTokens: 80,
    outputTokens: 40,
  });
  const config = requests[0].config as Record<string, unknown>;
  assert.equal(config.tools, undefined);
  assert.equal(config.maxOutputTokens, 1_500);
  assert.equal(config.responseMimeType, "application/json");
  assert.deepEqual(config.thinkingConfig, { thinkingLevel: "LOW" });
  const schema = config.responseJsonSchema as Record<string, unknown>;
  assert.equal(schema.additionalProperties, false);
});

test("uses only answer and table in the Gemini structured-search synthesis contract", async () => {
  const requests: Array<Record<string, unknown>> = [];
  const client = {
    models: {
      async generateContent(request: Record<string, unknown>) {
        requests.push(request);
        return {
          text: JSON.stringify({ answer: "Matching Pokémon.", table: null }),
          usageMetadata: { candidatesTokenCount: 5, promptTokenCount: 20 },
        };
      },
    },
  };
  const provider = createGeminiProvider({ client, model: "gemini-3.7-flash" });

  const result = await provider.synthesize({
    evidence: [],
    interpretation: {
      ambiguous_terms: [],
      conflicts: [],
      constraints: [{ field: "color", operator: "eq", value: "blue" }],
      interpretations: [],
      status: "structured",
      unsupported_terms: [],
    },
    question: "Find blue Pokémon.",
  });

  assert.deepEqual(result.response, {
    ability_ids: [],
    answer: "Matching Pokémon.",
    continuation_candidates: null,
    item_ids: [],
    move_ids: [],
    pokemon_ids: [],
    table: null,
  });
  const config = requests[0].config as Record<string, unknown>;
  const schema = config.responseJsonSchema as Record<string, unknown>;
  assert.deepEqual(Object.keys(schema.properties as Record<string, unknown>).sort(), ["answer", "table"]);
  assert.deepEqual(schema.required, ["answer", "table"]);
});

test("preserves usage when a Gemini response cannot satisfy the contract", async () => {
  const provider = createGeminiProvider({
    client: {
      models: {
        async generateContent() {
          return {
            text: "not-json",
            usageMetadata: {
              cachedContentTokenCount: 20,
              candidatesTokenCount: 30,
              promptTokenCount: 120,
              thoughtsTokenCount: 10,
            },
          };
        },
      },
    },
    model: "gemini-3.7-flash",
  });

  await assert.rejects(
    () => provider.synthesize({ evidence: [], question: "What type is Bulbasaur?" }),
    (error: unknown) => {
      assert.ok(error instanceof ModelProviderError);
      assert.deepEqual(error.usage, {
        cacheWriteTokens: 0,
        cachedInputTokens: 20,
        inputTokens: 120,
        outputTokens: 40,
      });
      return true;
    },
  );
});
