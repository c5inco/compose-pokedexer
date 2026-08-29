import assert from "node:assert/strict";
import test from "node:test";

import { createOpenAIProvider } from "../src/openai-provider.js";
import { ModelProviderError } from "../src/orchestrator.js";

test("converts strict OpenAI tool arguments into provider-neutral calls", async () => {
  const requests: Array<Record<string, unknown>> = [];
  const client = {
    responses: {
      async create(request: Record<string, unknown>) {
        requests.push(request);
        return {
          output: [
            {
              arguments: JSON.stringify({
                purpose: "Resolve Razor Leaf",
                query: "query Move($name: String!) { move(where: {name: {_eq: $name}}, limit: 1) { id name } }",
                variables: [
                  { name: "name", value_json: '"razor-leaf"' },
                  { name: "typeName", value_json: "grass" },
                ],
              }),
              call_id: "call-1",
              name: "execute_readonly_graphql",
              type: "function_call",
            },
          ],
          status: "completed",
          usage: {
            input_tokens: 100,
            input_tokens_details: { cache_write_tokens: 10, cached_tokens: 25 },
            output_tokens: 20,
          },
        };
      },
    },
  };
  const provider = createOpenAIProvider({ client, model: "gpt-5.6-luna" });

  const turn = await provider.plan({ history: [], question: "Who learns Razor Leaf?" });

  assert.deepEqual(turn.toolCalls[0].arguments, {
    purpose: "Resolve Razor Leaf",
    query: "query Move($name: String!) { move(where: {name: {_eq: $name}}, limit: 1) { id name } }",
    variables: { name: "razor-leaf", typeName: "grass" },
  });
  assert.deepEqual(turn.usage, {
    cacheWriteTokens: 10,
    cachedInputTokens: 25,
    inputTokens: 100,
    outputTokens: 20,
  });
  assert.equal(requests[0].store, false);
  assert.match(String(requests[0].instructions), /SpeciesWithMove/);
  const requestTools = requests[0].tools as Array<{
    name: string;
    parameters: { properties: Record<string, unknown> };
  }>;
  assert.equal(requestTools.length, 2);
  const lookup = requestTools.find((tool) => tool.name === "schema_lookup");
  assert.deepEqual(lookup?.parameters.properties.terms, {
    items: { maxLength: 80, type: "string" },
    maxItems: 6,
    minItems: 1,
    type: "array",
  });
  assert.deepEqual(lookup?.parameters.properties.detail, {
    enum: ["types", "fields"],
    type: "string",
  });
  assert.deepEqual(lookup?.parameters.properties.field_limit, {
    maximum: 12,
    minimum: 1,
    type: "integer",
  });
});

test("uses a separate tools-disabled strict synthesis response", async () => {
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
    responses: {
      async create(request: Record<string, unknown>) {
        requests.push(request);
        return {
          output: [],
          output_text: JSON.stringify(response),
          status: "completed",
          usage: {
            input_tokens: 80,
            input_tokens_details: { cached_tokens: 10 },
            output_tokens: 30,
          },
        };
      },
    },
  };
  const provider = createOpenAIProvider({ client, model: "gpt-5.6-luna" });

  const result = await provider.synthesize({
    evidence: [],
    question: "What does Regenerator do?",
  });

  assert.deepEqual(result.response, response);
  assert.equal(requests[0].tools, undefined);
  assert.deepEqual(requests[0].text, {
    format: {
      name: "ask_pokedexer_response",
      schema: assertSchemaObject(requests[0]),
      strict: true,
      type: "json_schema",
    },
  });
});

test("uses only answer and table in the structured-search synthesis contract", async () => {
  const requests: Array<Record<string, unknown>> = [];
  const client = {
    responses: {
      async create(request: Record<string, unknown>) {
        requests.push(request);
        return {
          output: [],
          output_text: JSON.stringify({ answer: "Matching Pokémon.", table: null }),
          status: "completed",
          usage: { input_tokens: 20, output_tokens: 5 },
        };
      },
    },
  };
  const provider = createOpenAIProvider({ client, model: "gpt-5.6-luna" });

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
  const schema = assertSchemaObject(requests[0]);
  assert.deepEqual(Object.keys(schema.properties as Record<string, unknown>).sort(), ["answer", "table"]);
  assert.deepEqual(schema.required, ["answer", "table"]);
});

test("uses the configured reasoning effort for planning and synthesis", async () => {
  const requests: Array<Record<string, unknown>> = [];
  const client = {
    responses: {
      async create(request: Record<string, unknown>) {
        requests.push(request);
        return {
          output: [],
          output_text: JSON.stringify({
            ability_ids: [],
            answer: "No evidence was needed.",
            continuation_candidates: null,
            item_ids: [],
            move_ids: [],
            pokemon_ids: [],
            table: null,
          }),
          status: "completed",
          usage: { input_tokens: 10, output_tokens: 5 },
        };
      },
    },
  };
  const provider = createOpenAIProvider({
    client,
    model: "gpt-5.6-luna",
    reasoningEffort: "medium",
  });

  await provider.plan({ history: [], question: "What is Bulbasaur?" });
  await provider.synthesize({ evidence: [], question: "What is Bulbasaur?" });

  assert.deepEqual(requests.map((request) => request.reasoning), [
    { effort: "medium" },
    { effort: "medium" },
  ]);
  assert.deepEqual(requests.map((request) => request.max_output_tokens), [2_500, 1_500]);
});

test("preserves usage when an OpenAI response cannot satisfy the contract", async () => {
  const provider = createOpenAIProvider({
    client: {
      responses: {
        async create() {
          return {
            output: [],
            status: "incomplete",
            usage: {
              input_tokens: 120,
              input_tokens_details: { cached_tokens: 20 },
              output_tokens: 40,
            },
          };
        },
      },
    },
    model: "gpt-5.6-luna",
  });

  await assert.rejects(
    () => provider.plan({ history: [], question: "What type is Bulbasaur?" }),
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

function assertSchemaObject(request: Record<string, unknown>): Record<string, unknown> {
  const text = request.text as { format: { schema: Record<string, unknown> } };
  assert.equal(text.format.schema.additionalProperties, false);
  return text.format.schema;
}
