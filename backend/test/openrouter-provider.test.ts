import assert from "node:assert/strict";
import test from "node:test";

import { createOpenRouterProvider } from "../src/openrouter-provider.js";
import { ModelProviderError } from "../src/orchestrator.js";

const synthesisResponse = {
  ability_ids: [],
  answer: "Bulbasaur is a Grass- and Poison-type Pokémon.",
  continuation_candidates: null,
  item_ids: [],
  move_ids: [],
  pokemon_ids: [1],
  table: null,
};

test("uses stateless OpenRouter Responses with strict capability routing", async () => {
  const requests: Array<Record<string, unknown>> = [];
  const provider = createOpenRouterProvider({
    client: {
      responses: {
        async create(request: Record<string, unknown>) {
          requests.push(request);
          return requests.length === 1
            ? {
                output: [],
                status: "completed",
                usage: { input_tokens: 20, output_tokens: 5 },
              }
            : {
                output: [],
                output_text: JSON.stringify(synthesisResponse),
                status: "completed",
                usage: { input_tokens: 30, output_tokens: 10 },
              };
        },
      },
    },
    model: "z-ai/glm-5.3-flash",
    reasoningEffort: "low",
  });

  await provider.plan({ history: [], question: "What type is Bulbasaur?" });
  const synthesis = await provider.synthesize({
    evidence: [],
    question: "What type is Bulbasaur?",
  });

  assert.deepEqual(synthesis.response, synthesisResponse);
  assert.deepEqual(
    requests.map((request) => request.model),
    ["z-ai/glm-5.3-flash", "z-ai/glm-5.3-flash"],
  );
  assert.deepEqual(
    requests.map((request) => request.store),
    [false, false],
  );
  assert.deepEqual(
    requests.map((request) => request.reasoning),
    [{ effort: "low" }, { effort: "low" }],
  );
  assert.deepEqual(
    requests.map((request) => request.provider),
    [{ require_parameters: true }, { require_parameters: true }],
  );
  assert.equal(requests[0].parallel_tool_calls, undefined);
  const tools = requests[0].tools as Array<{ strict: boolean; type: string }>;
  assert.equal(tools.length, 2);
  assert.ok(tools.every((tool) => tool.strict && tool.type === "function"));
  assert.equal(requests[1].tools, undefined);
  assert.equal(
    (requests[1].text as { format: { strict: boolean; type: string } }).format.strict,
    true,
  );
  assert.equal(
    (requests[1].text as { format: { strict: boolean; type: string } }).format.type,
    "json_schema",
  );
});

function graphqlPlannerClient(argumentsJson: unknown) {
  return {
    responses: {
      async create() {
        return {
          output: [
            {
              arguments: JSON.stringify(argumentsJson),
              call_id: "call-openrouter-1",
              name: "execute_readonly_graphql",
              type: "function_call",
            },
          ],
          status: "completed",
          usage: { input_tokens: 40, output_tokens: 12 },
        };
      },
    },
  };
}

const canonicalQuery =
  "query Pokemon($where: pokemon_bool_exp!, $limit: Int!) { pokemon(where: $where, limit: $limit) { id name } }";

test("keeps already-canonical OpenRouter GraphQL variables unchanged", async () => {
  const provider = createOpenRouterProvider({
    client: graphqlPlannerClient({
      purpose: "Resolve Cobalt Sprig",
      query: canonicalQuery,
      variables: [
        { name: "where", value_json: '{"name":{"_eq":"cobalt-sprig"}}' },
        { name: "limit", value_json: "1" },
      ],
    }),
    model: "z-ai/glm-5.3-flash",
    reasoningEffort: "low",
  });

  const turn = await provider.plan({ history: [], question: "What types is Cobalt Sprig?" });

  assert.deepEqual(turn.toolCalls[0].arguments, {
    purpose: "Resolve Cobalt Sprig",
    query: canonicalQuery,
    variables: { where: { name: { _eq: "cobalt-sprig" } }, limit: 1 },
  });
  assert.deepEqual(turn.usage.toolArgumentNormalizations, {
    calls: 0,
    kinds: { non_string_value_json: 0, variables_object_map: 0 },
  });
});

test("does not rewrite OpenRouter schema_lookup arguments", async () => {
  const provider = createOpenRouterProvider({
    client: {
      responses: {
        async create() {
          return {
            output: [
              {
                arguments: JSON.stringify({
                  detail: "fields",
                  field_limit: 8,
                  limit: 4,
                  terms: ["pokemon", "type"],
                }),
                call_id: "call-openrouter-lookup",
                name: "schema_lookup",
                type: "function_call",
              },
            ],
            status: "completed",
            usage: { input_tokens: 20, output_tokens: 6 },
          };
        },
      },
    },
    model: "z-ai/glm-5.3-flash",
    reasoningEffort: "low",
  });

  const turn = await provider.plan({ history: [], question: "What types is Cobalt Sprig?" });

  assert.deepEqual(turn.toolCalls[0].arguments, {
    detail: "fields",
    field_limit: 8,
    limit: 4,
    terms: ["pokemon", "type"],
  });
});

test("normalizes OpenRouter GraphQL object-map variables into the canonical array contract", async () => {
  const provider = createOpenRouterProvider({
    client: graphqlPlannerClient({
      purpose: "Resolve Cobalt Sprig",
      query: canonicalQuery,
      variables: {
        where: { name: { _eq: "cobalt-sprig" } },
        limit: 1,
      },
    }),
    model: "z-ai/glm-5.3-flash",
    reasoningEffort: "low",
  });

  const turn = await provider.plan({ history: [], question: "What types is Cobalt Sprig?" });

  assert.deepEqual(turn.toolCalls[0].arguments, {
    purpose: "Resolve Cobalt Sprig",
    query: canonicalQuery,
    variables: { where: { name: { _eq: "cobalt-sprig" } }, limit: 1 },
  });
  assert.deepEqual(turn.usage.toolArgumentNormalizations, {
    calls: 1,
    kinds: { non_string_value_json: 0, variables_object_map: 1 },
  });
});

test("JSON-stringifies non-string OpenRouter value_json entries before strict parseToolCall", async () => {
  const provider = createOpenRouterProvider({
    client: graphqlPlannerClient({
      purpose: "Compare Ember Fin and Cobalt Sprig speed",
      query: canonicalQuery,
      variables: [
        { name: "where", value_json: { name: { _in: ["ember-fin", "cobalt-sprig"] } } },
        { name: "limit", value_json: 2 },
      ],
    }),
    model: "z-ai/glm-5.3-flash",
    reasoningEffort: "low",
  });

  const turn = await provider.plan({
    history: [],
    question: "Which is faster, Ember Fin or Cobalt Sprig?",
  });

  assert.deepEqual(turn.toolCalls[0].arguments, {
    purpose: "Compare Ember Fin and Cobalt Sprig speed",
    query: canonicalQuery,
    variables: { where: { name: { _in: ["ember-fin", "cobalt-sprig"] } }, limit: 2 },
  });
  assert.deepEqual(turn.usage.toolArgumentNormalizations, {
    calls: 1,
    kinds: { non_string_value_json: 1, variables_object_map: 0 },
  });
});

test("rejects duplicate OpenRouter GraphQL variable names instead of merging them", async () => {
  const provider = createOpenRouterProvider({
    client: graphqlPlannerClient({
      purpose: "Resolve a Pokémon",
      query: canonicalQuery,
      variables: [
        { name: "limit", value_json: 1 },
        { name: "limit", value_json: 2 },
      ],
    }),
    model: "z-ai/glm-5.3-flash",
    reasoningEffort: "low",
  });

  await assert.rejects(
    () => provider.plan({ history: [], question: "What type is Bulbasaur?" }),
    (error: unknown) => {
      assert.ok(error instanceof ModelProviderError);
      assert.match(error.message, /Duplicate GraphQL variable limit/);
      assert.deepEqual(error.usage?.toolArgumentNormalizations, {
        calls: 1,
        kinds: { non_string_value_json: 1, variables_object_map: 0 },
      });
      return true;
    },
  );
});

test("rejects unsupported OpenRouter GraphQL variable shapes without repairing them", async () => {
  const provider = createOpenRouterProvider({
    client: graphqlPlannerClient({
      purpose: "Look up Eevee evolutions",
      query: canonicalQuery,
      variables: ["where", "limit"],
    }),
    model: "z-ai/glm-5.3-flash",
    reasoningEffort: "low",
  });

  await assert.rejects(
    () =>
      provider.plan({
        history: [],
        question: "What are the Water, Electric, and Fire evolutions of Eevee?",
      }),
    (error: unknown) => {
      assert.ok(error instanceof ModelProviderError);
      assert.match(error.message, /expected object, received string/);
      return true;
    },
  );
});

test("labels invalid OpenRouter responses without reporting them as OpenAI failures", async () => {
  const provider = createOpenRouterProvider({
    client: {
      responses: {
        async create() {
          return {
            output: [],
            status: "incomplete",
            usage: { input_tokens: 12, output_tokens: 4 },
          };
        },
      },
    },
    model: "qwen/qwen3.8-flash",
    reasoningEffort: "low",
  });

  await assert.rejects(
    () => provider.plan({ history: [], question: "What type is Bulbasaur?" }),
    (error: unknown) => {
      assert.ok(error instanceof ModelProviderError);
      assert.match(error.message, /^OpenRouter response ended with status incomplete$/);
      return true;
    },
  );
});
