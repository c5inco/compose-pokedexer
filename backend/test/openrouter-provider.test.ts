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
