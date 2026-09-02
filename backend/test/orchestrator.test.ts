import assert from "node:assert/strict";
import { readFileSync } from "node:fs";
import { resolve } from "node:path";
import test from "node:test";

import { buildClientSchema, type IntrospectionQuery } from "graphql";

import {
  AskEvaluationError,
  AskOrchestrator,
  type GraphqlExecution,
  type ModelProvider,
  type PlannerTurn,
  type SynthesisResponse,
} from "../src/orchestrator.js";
import { createPaginationService } from "../src/pagination.js";
import {
  createReadonlyGraphqlExecutor,
  type GraphqlRequest,
} from "../src/readonly-graphql.js";

const trace = {
  purpose: "Resolve Bulbasaur",
  query: "query Pokemon($name: String!) { pokemon(name: $name, limit: 1) { id name } }",
  variables: { name: "bulbasaur" },
  document_sha256: "a".repeat(64),
  duration_ms: 12,
};

function providerWith(response: SynthesisResponse): ModelProvider {
  const turns: PlannerTurn[] = [
    {
      toolCalls: [
        {
          arguments: { purpose: trace.purpose, query: trace.query, variables: trace.variables },
          callId: "call-1",
          name: "execute_readonly_graphql",
        },
      ],
      usage: {
        cacheWriteTokens: 5,
        cachedInputTokens: 20,
        inputTokens: 100,
        outputTokens: 30,
        toolArgumentNormalizations: {
          calls: 1,
          kinds: { non_string_value_json: 0, variables_object_map: 1 },
        },
      },
    },
    {
      toolCalls: [],
      usage: { cacheWriteTokens: 0, cachedInputTokens: 30, inputTokens: 120, outputTokens: 20 },
    },
  ];

  return {
    async plan() {
      const turn = turns.shift();
      if (!turn) throw new Error("Fake provider ran out of planner turns");
      return turn;
    },
    async synthesize() {
      return {
        response,
        usage: { cacheWriteTokens: 0, cachedInputTokens: 40, inputTokens: 150, outputTokens: 40 },
      };
    },
  };
}

const execution: GraphqlExecution = {
  data: { pokemon: [{ id: 1, name: "bulbasaur" }] },
  entityIds: { ability: [], item: [], move: [], pokemon: [1] },
  entityReferences: {
    ability: [],
    item: [],
    move: [],
    pokemon: [{ id: 1, name: "bulbasaur" }],
  },
  trace,
};

test("builds authoritative query provenance from executed tools", async () => {
  const orchestrator = new AskOrchestrator({
    executeGraphql: async () => execution,
    model: providerWith({
      ability_ids: [],
      answer: "Bulbasaur is a Grass/Poison-type Pokémon.",
      item_ids: [],
      move_ids: [],
      pokemon_ids: [1],
      table: null,
    }),
    pricing: {
      cacheWritePerMillion: 0.25,
      cachedInputPerMillion: 0.02,
      inputPerMillion: 0.2,
      outputPerMillion: 1.2,
    },
    schemaLookup: async () => ({ matches: [] }),
  });

  const result = await orchestrator.ask("What type is Bulbasaur?");

  assert.deepEqual(result.response.queries, [trace]);
  assert.deepEqual(result.response.pokemon_ids, [1]);
  assert.equal(result.metrics.model_calls, 3);
  assert.equal(result.metrics.graphql_calls, 1);
  assert.deepEqual(result.metrics.tool_argument_normalizations, {
    calls: 1,
    kinds: { non_string_value_json: 0, variables_object_map: 1 },
  });
  assert.ok(result.metrics.estimated_cost_usd > 0);
});

test("rejects entity IDs that were not present in verified tool results", async () => {
  const orchestrator = new AskOrchestrator({
    executeGraphql: async () => execution,
    model: providerWith({
      ability_ids: [144],
      answer: "Regenerator restores HP.",
      item_ids: [],
      move_ids: [],
      pokemon_ids: [1],
      table: null,
    }),
    pricing: {
      cacheWritePerMillion: 0.25,
      cachedInputPerMillion: 0.02,
      inputPerMillion: 0.2,
      outputPerMillion: 1.2,
    },
    schemaLookup: async () => ({ matches: [] }),
  });

  let failure: unknown;
  try {
    await orchestrator.ask("What type is Bulbasaur?");
  } catch (error) {
    failure = error;
  }

  assert.ok(failure instanceof AskEvaluationError);
  assert.match(failure.message, /ability ID 144/);
  assert.equal(failure.evaluation.diagnostics.phase, "validation");
  assert.equal(failure.evaluation.diagnostics.failure_class, "model");
  assert.equal(failure.evaluation.metrics.model_attempts, 3);
  assert.equal(failure.evaluation.metrics.model_calls, 3);
  assert.equal(failure.evaluation.metrics.cost_complete, true);
  assert.ok(failure.evaluation.metrics.estimated_cost_usd > 0);
});

test("classifies an SDK failure separately from a model contract failure", async () => {
  const orchestrator = new AskOrchestrator({
    executeGraphql: async () => execution,
    model: {
      async plan() {
        throw new Error("Gemini returned HTTP 503");
      },
      async synthesize() {
        throw new Error("synthesis must not run");
      },
    },
    pricing: { cacheWritePerMillion: 0, cachedInputPerMillion: 0, inputPerMillion: 0, outputPerMillion: 0 },
    schemaLookup: async () => ({ matches: [] }),
  });

  await assert.rejects(
    orchestrator.ask("What type is Bulbasaur?"),
    (error: unknown) => {
      assert.ok(error instanceof AskEvaluationError);
      assert.equal(error.evaluation.diagnostics.failure_class, "provider");
      return true;
    },
  );
});

test("propagates one request cancellation signal through planning and synthesis", async () => {
  const signals: Array<AbortSignal | undefined> = [];
  const orchestrator = new AskOrchestrator({
    executeGraphql: async () => execution,
    model: {
      async plan(input) {
        signals.push(input.signal);
        return {
          toolCalls: [],
          usage: { cacheWriteTokens: 0, cachedInputTokens: 0, inputTokens: 1, outputTokens: 1 },
        };
      },
      async synthesize(input) {
        signals.push(input.signal);
        return {
          response: {
            ability_ids: [],
            answer: "That request is outside Pokémon data.",
            item_ids: [],
            move_ids: [],
            pokemon_ids: [],
            table: null,
          },
          usage: { cacheWriteTokens: 0, cachedInputTokens: 0, inputTokens: 1, outputTokens: 1 },
        };
      },
    },
    pricing: {
      cacheWritePerMillion: 0,
      cachedInputPerMillion: 0,
      inputPerMillion: 0,
      outputPerMillion: 0,
    },
    schemaLookup: async () => ({ matches: [] }),
  });
  const signal = new AbortController().signal;

  await orchestrator.ask("Tell me tomorrow's weather", signal);

  assert.deepEqual(signals, [signal, signal]);
});

test("returns a rejected GraphQL attempt to the planner for one bounded correction", async () => {
  const calls: string[] = [];
  const plannerTurns: PlannerTurn[] = [
    {
      toolCalls: [
        {
          arguments: { purpose: "Unbounded", query: "query Bad { pokemon { id } }", variables: {} },
          callId: "bad-call",
          name: "execute_readonly_graphql",
        },
      ],
      usage: { cacheWriteTokens: 0, cachedInputTokens: 0, inputTokens: 10, outputTokens: 5 },
    },
    {
      toolCalls: [
        {
          arguments: { purpose: trace.purpose, query: trace.query, variables: trace.variables },
          callId: "good-call",
          name: "execute_readonly_graphql",
        },
      ],
      usage: { cacheWriteTokens: 0, cachedInputTokens: 0, inputTokens: 10, outputTokens: 5 },
    },
    {
      toolCalls: [],
      usage: { cacheWriteTokens: 0, cachedInputTokens: 0, inputTokens: 10, outputTokens: 5 },
    },
  ];
  const model: ModelProvider = {
    async plan(input) {
      if (input.history.length === 1) {
        assert.deepEqual(input.history[0].result, {
          error: "List field pokemon requires a bounded limit",
        });
      }
      const turn = plannerTurns.shift();
      if (!turn) throw new Error("Fake provider ran out of turns");
      return turn;
    },
    async synthesize() {
      return {
        response: {
          ability_ids: [],
          answer: "Bulbasaur is a Pokémon.",
          item_ids: [],
          move_ids: [],
          pokemon_ids: [1],
          table: null,
        },
        usage: { cacheWriteTokens: 0, cachedInputTokens: 0, inputTokens: 10, outputTokens: 5 },
      };
    },
  };
  const orchestrator = new AskOrchestrator({
    async executeGraphql(request) {
      calls.push(request.query);
      if (calls.length === 1) throw new Error("List field pokemon requires a bounded limit");
      return execution;
    },
    model,
    pricing: {
      cacheWritePerMillion: 0.25,
      cachedInputPerMillion: 0.02,
      inputPerMillion: 0.2,
      outputPerMillion: 1.2,
    },
    schemaLookup: async () => ({ matches: [] }),
  });

  const result = await orchestrator.ask("What is Bulbasaur?");

  assert.equal(result.metrics.graphql_attempts, 2);
  assert.equal(result.metrics.graphql_calls, 1);
  assert.deepEqual(result.response.queries, [trace]);
});

test("synthesizes from evidence gathered in the final allowed tool round", async () => {
  const model = providerWith({
    ability_ids: [],
    answer: "Bulbasaur is a Pokémon.",
    item_ids: [],
    move_ids: [],
    pokemon_ids: [1],
    table: null,
  });
  const orchestrator = new AskOrchestrator({
    executeGraphql: async () => execution,
    maxToolRounds: 1,
    model,
    pricing: {
      cacheWritePerMillion: 0.25,
      cachedInputPerMillion: 0.02,
      inputPerMillion: 0.2,
      outputPerMillion: 1.2,
    },
    schemaLookup: async () => ({ matches: [] }),
  });

  const result = await orchestrator.ask("What is Bulbasaur?");

  assert.equal(result.metrics.model_calls, 2);
  assert.deepEqual(result.response.queries, [trace]);
});

test("omits schema discovery from synthesis after GraphQL evidence succeeds", async () => {
  const plannerTurns: PlannerTurn[] = [
    {
      toolCalls: [
        {
          arguments: { detail: "fields", field_limit: 4, limit: 2, terms: ["pokemon"] },
          callId: "schema-call",
          name: "schema_lookup",
        },
      ],
      usage: { cacheWriteTokens: 0, cachedInputTokens: 0, inputTokens: 10, outputTokens: 5 },
    },
    {
      toolCalls: [
        {
          arguments: { purpose: trace.purpose, query: trace.query, variables: trace.variables },
          callId: "graphql-call",
          name: "execute_readonly_graphql",
        },
      ],
      usage: { cacheWriteTokens: 0, cachedInputTokens: 0, inputTokens: 10, outputTokens: 5 },
    },
    {
      toolCalls: [],
      usage: { cacheWriteTokens: 0, cachedInputTokens: 0, inputTokens: 10, outputTokens: 5 },
    },
  ];
  const model: ModelProvider = {
    async plan(input) {
      if (input.history.length === 1) {
        assert.equal(input.history[0].call.name, "schema_lookup");
      }
      const turn = plannerTurns.shift();
      if (!turn) throw new Error("Fake provider ran out of turns");
      return turn;
    },
    async synthesize(input) {
      assert.deepEqual(
        input.evidence.map(({ call }) => call.name),
        ["execute_readonly_graphql"],
      );
      return {
        response: {
          ability_ids: [],
          answer: "Bulbasaur is a Pokémon.",
          item_ids: [],
          move_ids: [],
          pokemon_ids: [1],
          table: null,
        },
        usage: { cacheWriteTokens: 0, cachedInputTokens: 0, inputTokens: 10, outputTokens: 5 },
      };
    },
  };
  const orchestrator = new AskOrchestrator({
    executeGraphql: async () => execution,
    model,
    pricing: {
      cacheWritePerMillion: 0.25,
      cachedInputPerMillion: 0.02,
      inputPerMillion: 0.2,
      outputPerMillion: 1.2,
    },
    schemaLookup: async () => ({ matches: [{ fields: [{ name: "id" }], name: "pokemon" }] }),
  });

  await orchestrator.ask("What is Bulbasaur?");
});

test("does not create a cursor for an ordinary answer with extra observed evidence IDs", async () => {
  const factualExecution: GraphqlExecution = {
    ...execution,
    entityIds: { ability: [], item: [], move: [], pokemon: [1, 999] },
  };
  const response = {
    ability_ids: [],
    answer: "Bulbasaur is Grass/Poison.",
    continuation_candidates: null,
    item_ids: [],
    move_ids: [],
    pokemon_ids: [1],
    table: null,
  } as SynthesisResponse;
  const orchestrator = new AskOrchestrator({
    executeGraphql: async () => factualExecution,
    model: providerWith(response),
    pagination: createPaginationService({
      secret: "test-only-pagination-secret-at-least-32-bytes",
    }),
    pricing: { cacheWritePerMillion: 0, cachedInputPerMillion: 0, inputPerMillion: 0, outputPerMillion: 0 },
    schemaLookup: async () => ({ matches: [] }),
  });

  const result = await orchestrator.ask("What type is Bulbasaur?");

  assert.equal("pagination" in result.response, false);
});

test("ignores and records model-supplied continuation candidates for ordinary answers", async () => {
  const response = {
    ability_ids: [],
    answer: "Bulbasaur is Grass/Poison.",
    continuation_candidates: {
      ability_ids: [],
      item_ids: [],
      move_ids: [],
      pokemon_ids: [999],
    },
    item_ids: [],
    move_ids: [],
    pokemon_ids: [1],
    table: null,
  } as SynthesisResponse;
  const orchestrator = new AskOrchestrator({
    executeGraphql: async () => execution,
    model: providerWith(response),
    pagination: createPaginationService({
      secret: "test-only-pagination-secret-at-least-32-bytes",
    }),
    pricing: { cacheWritePerMillion: 0, cachedInputPerMillion: 0, inputPerMillion: 0, outputPerMillion: 0 },
    schemaLookup: async () => ({ matches: [] }),
  });

  const result = await orchestrator.ask("Tell me Bulbasaur's types and base stats.");

  assert.equal("pagination" in result.response, false);
  assert.deepEqual(result.diagnostics.contract_warnings, [
    "Ignored continuation candidates because the question was not a backend-recognized structured search",
  ]);
});

test("passes reviewed structured search constraints to synthesis", async () => {
  const turns: PlannerTurn[] = [
    {
      toolCalls: [{ arguments: { purpose: trace.purpose, query: trace.query, variables: trace.variables }, callId: "search", name: "execute_readonly_graphql" }],
      usage: { cacheWriteTokens: 0, cachedInputTokens: 0, inputTokens: 1, outputTokens: 1 },
    },
    { toolCalls: [], usage: { cacheWriteTokens: 0, cachedInputTokens: 0, inputTokens: 1, outputTokens: 1 } },
  ];
  const model: ModelProvider = {
    async plan() {
      throw new Error("Structured searches must not invoke model planning");
    },
    async synthesize(input) {
      assert.match(input.interpretation?.interpretations[0]?.disclosure ?? "", /round.*ball/);
      return {
        response: {
          ability_ids: [],
          answer: "Result",
          continuation_candidates: null,
          item_ids: [],
          move_ids: [],
          pokemon_ids: [1],
          table: null,
        },
        usage: { cacheWriteTokens: 0, cachedInputTokens: 0, inputTokens: 1, outputTokens: 1 },
      };
    },
  };
  const orchestrator = new AskOrchestrator({
    executeGraphql: async () => execution,
    model,
    pricing: { cacheWritePerMillion: 0, cachedInputPerMillion: 0, inputPerMillion: 0, outputPerMillion: 0 },
    schemaLookup: async () => ({ matches: [] }),
  });

  await orchestrator.ask("Find round Water Pokémon");
});

test("executes reviewed structured searches deterministically and uses the model only for synthesis", async () => {
  const selectedIds = Array.from({ length: 10 }, (_, index) => index + 1);
  let graphqlCalls = 0;
  let synthesisCalls = 0;
  const model: ModelProvider = {
    async plan() {
      throw new Error("Structured searches must not invoke model planning");
    },
    async synthesize(input) {
      synthesisCalls += 1;
      assert.equal(input.evidence.length, 1);
      assert.equal(input.evidence[0].call.name, "execute_readonly_graphql");
      assert.deepEqual(input.interpretation?.constraints, [
        { field: "shape", operator: "eq", value: "ball" },
        { field: "type", operator: "eq", value: "water" },
      ]);
      return {
        response: {
          ability_ids: [],
          answer: "Matching Pokémon.",
          continuation_candidates: {
            ability_ids: [],
            item_ids: [],
            move_ids: [],
            pokemon_ids: [999],
          },
          item_ids: [],
          move_ids: [],
          pokemon_ids: [999, 10],
          table: null,
        },
        usage: { cacheWriteTokens: 0, cachedInputTokens: 0, inputTokens: 20, outputTokens: 5 },
      };
    },
  };
  const orchestrator = new AskOrchestrator({
    async executeGraphql(request) {
      graphqlCalls += 1;
      assert.equal(request.variables.limit, 100);
      return {
        data: { pokemon: selectedIds.map((id) => ({ height: 5, id, name: `pokemon-${id}`, weight: 50 })) },
        entityIds: { ability: [], item: [], move: [], pokemon: selectedIds },
        trace: { ...trace, purpose: request.purpose, query: request.query, variables: request.variables },
      };
    },
    model,
    pagination: createPaginationService({ secret: "test-only-pagination-secret-at-least-32-bytes" }),
    pricing: { cacheWritePerMillion: 0, cachedInputPerMillion: 0, inputPerMillion: 0, outputPerMillion: 0 },
    schemaLookup: async () => {
      throw new Error("Structured searches must not invoke schema lookup");
    },
  });

  const first = await orchestrator.ask("Find round Water Pokémon");

  assert.equal(graphqlCalls, 1);
  assert.equal(synthesisCalls, 1);
  assert.equal(first.metrics.graphql_attempts, 1);
  assert.equal(first.metrics.graphql_calls, 1);
  assert.equal(first.metrics.model_attempts, 1);
  assert.equal(first.metrics.model_calls, 1);
  assert.equal(first.metrics.schema_lookups, 0);
  assert.deepEqual(first.response.pokemon_ids, [1, 2, 3, 4, 5, 6, 7, 8]);
  assert.equal(first.response.queries.length, 1);
  const cursor = first.response.pagination?.continuation_cursor;
  assert.ok(cursor);
  const second = orchestrator.continue("Find round Water Pokémon", cursor);
  assert.deepEqual(second.response.pokemon_ids, [9, 10]);
});

test("executes structured no-match searches once and returns no hydration IDs", async () => {
  let plannerCalled = false;
  const model: ModelProvider = {
    async plan() {
      plannerCalled = true;
      throw new Error("Structured searches must not invoke model planning");
    },
    async synthesize(input) {
      assert.deepEqual(input.evidence[0].result, { pokemon: [] });
      return {
        response: {
          ability_ids: [],
          answer: "No matching Pokémon were found in PokéAPI.",
          continuation_candidates: null,
          item_ids: [],
          move_ids: [],
          pokemon_ids: [],
          table: null,
        },
        usage: { cacheWriteTokens: 0, cachedInputTokens: 0, inputTokens: 10, outputTokens: 5 },
      };
    },
  };
  const orchestrator = new AskOrchestrator({
    executeGraphql: async (request) => ({
      data: { pokemon: [] },
      entityIds: { ability: [], item: [], move: [], pokemon: [] },
      entityReferences: { ability: [], item: [], move: [], pokemon: [] },
      trace: { ...trace, purpose: request.purpose, query: request.query, variables: request.variables },
    }),
    model,
    pagination: createPaginationService({ secret: "test-only-pagination-secret-at-least-32-bytes" }),
    pricing: { cacheWritePerMillion: 0, cachedInputPerMillion: 0, inputPerMillion: 0, outputPerMillion: 0 },
    schemaLookup: async () => ({ matches: [] }),
  });

  const result = await orchestrator.ask("Are there any round, purple Dragon-type Pokémon?");

  assert.equal(plannerCalled, false);
  assert.equal(result.metrics.graphql_calls, 1);
  assert.equal(result.metrics.model_calls, 1);
  assert.deepEqual(result.response.pokemon_ids, []);
  assert.equal("pagination" in result.response, false);
});

test("retries one evidence-free insufficient-evidence conclusion before returning it", async () => {
  const nidoranExecution: GraphqlExecution = {
    data: { pokemon: [{ id: 29, name: "nidoran-f" }] },
    entityIds: { ability: [], item: [], move: [], pokemon: [29] },
    entityReferences: {
      ability: [],
      item: [],
      move: [],
      pokemon: [{ id: 29, name: "nidoran-f" }],
    },
    trace: { ...trace, purpose: "Resolve female Nidoran", variables: { name: "nidoran-f" } },
  };
  const plannerInputs: Array<{ retryReason?: string }> = [];
  let planCount = 0;
  let synthesisCount = 0;
  const model: ModelProvider = {
    async plan(input) {
      plannerInputs.push(input);
      planCount += 1;
      if (planCount === 1) {
        return {
          toolCalls: [],
          usage: { cacheWriteTokens: 0, cachedInputTokens: 0, inputTokens: 10, outputTokens: 2 },
        };
      }
      return {
        toolCalls: [{
          arguments: { purpose: nidoranExecution.trace.purpose, query: trace.query, variables: nidoranExecution.trace.variables },
          callId: "evidence-retry",
          name: "execute_readonly_graphql",
        }],
        usage: { cacheWriteTokens: 0, cachedInputTokens: 0, inputTokens: 10, outputTokens: 2 },
      };
    },
    async synthesize(input) {
      synthesisCount += 1;
      return {
        response: {
          ability_ids: [],
          answer: input.evidence.length === 0
            ? "I can’t answer from verified evidence because no lookup succeeded."
            : "Female Nidoran is Poison type.",
          continuation_candidates: null,
          item_ids: [],
          move_ids: [],
          pokemon_ids: input.evidence.length === 0 ? [] : [29],
          table: null,
        },
        usage: { cacheWriteTokens: 0, cachedInputTokens: 0, inputTokens: 10, outputTokens: 2 },
      };
    },
  };
  const orchestrator = new AskOrchestrator({
    executeGraphql: async () => nidoranExecution,
    model,
    pricing: { cacheWritePerMillion: 0, cachedInputPerMillion: 0, inputPerMillion: 0, outputPerMillion: 0 },
    schemaLookup: async () => ({ matches: [] }),
  });

  const result = await orchestrator.ask("What type is the female Nidoran?");

  assert.equal(planCount, 2);
  assert.equal(synthesisCount, 2);
  assert.match(plannerInputs[1].retryReason ?? "", /no successful lookup/i);
  assert.equal(result.metrics.graphql_calls, 1);
  assert.equal(result.response.answer, "Female Nidoran is Poison type.");
  assert.deepEqual(result.response.pokemon_ids, [29]);
});

test("does not retry an out-of-scope refusal that called no tools", async () => {
  let planCount = 0;
  const model: ModelProvider = {
    async plan() {
      planCount += 1;
      return {
        toolCalls: [],
        usage: { cacheWriteTokens: 0, cachedInputTokens: 0, inputTokens: 10, outputTokens: 2 },
      };
    },
    async synthesize() {
      return {
        response: {
          ability_ids: [],
          answer: "I only answer Pokémon questions, so I can't write JavaScript.",
          continuation_candidates: null,
          item_ids: [],
          move_ids: [],
          pokemon_ids: [],
          table: null,
        },
        usage: { cacheWriteTokens: 0, cachedInputTokens: 0, inputTokens: 10, outputTokens: 2 },
      };
    },
  };
  const orchestrator = new AskOrchestrator({
    executeGraphql: async () => execution,
    model,
    pricing: { cacheWritePerMillion: 0, cachedInputPerMillion: 0, inputPerMillion: 0, outputPerMillion: 0 },
    schemaLookup: async () => ({ matches: [] }),
  });

  const result = await orchestrator.ask("Write a JavaScript quicksort implementation for me.");

  assert.equal(planCount, 1);
  assert.equal(result.metrics.graphql_calls, 0);
  assert.match(result.response.answer, /only answer Pokémon questions/i);
  assert.doesNotMatch(result.response.answer, /no lookup succeeded/i);
  assert.deepEqual(result.response.pokemon_ids, []);
});

test("retries one evidence-free not-found conclusion before returning it", async () => {
  const plannerInputs: Array<{ retryReason?: string }> = [];
  let planCount = 0;
  let synthesisCount = 0;
  const model: ModelProvider = {
    async plan(input) {
      plannerInputs.push(input);
      planCount += 1;
      if (planCount === 1) {
        return {
          toolCalls: [],
          usage: { cacheWriteTokens: 0, cachedInputTokens: 0, inputTokens: 10, outputTokens: 2 },
        };
      }
      return {
        toolCalls: [{
          arguments: { purpose: trace.purpose, query: trace.query, variables: trace.variables },
          callId: "evidence-retry",
          name: "execute_readonly_graphql",
        }],
        usage: { cacheWriteTokens: 0, cachedInputTokens: 0, inputTokens: 10, outputTokens: 2 },
      };
    },
    async synthesize(input) {
      synthesisCount += 1;
      return {
        response: {
          ability_ids: [],
          answer: input.evidence.length === 0
            ? "Bulbasaur was not found in the current verified PokéAPI data."
            : "Bulbasaur is a Pokémon.",
          continuation_candidates: null,
          item_ids: [],
          move_ids: [],
          pokemon_ids: input.evidence.length === 0 ? [] : [1],
          table: null,
        },
        usage: { cacheWriteTokens: 0, cachedInputTokens: 0, inputTokens: 10, outputTokens: 2 },
      };
    },
  };
  const orchestrator = new AskOrchestrator({
    executeGraphql: async () => execution,
    model,
    pricing: { cacheWritePerMillion: 0, cachedInputPerMillion: 0, inputPerMillion: 0, outputPerMillion: 0 },
    schemaLookup: async () => ({ matches: [] }),
  });

  const result = await orchestrator.ask("What type is Bulbasaur?");

  assert.equal(planCount, 2);
  assert.equal(synthesisCount, 2);
  assert.match(plannerInputs[1].retryReason ?? "", /not-found.*successful lookup/i);
  assert.equal(result.metrics.graphql_calls, 1);
  assert.equal(result.response.answer, "Bulbasaur is a Pokémon.");
  assert.deepEqual(result.response.pokemon_ids, [1]);
});

test("downgrades an unverified not-found conclusion when its bounded retry finds no evidence", async () => {
  let planCount = 0;
  const model: ModelProvider = {
    async plan() {
      planCount += 1;
      return {
        toolCalls: [],
        usage: { cacheWriteTokens: 0, cachedInputTokens: 0, inputTokens: 10, outputTokens: 2 },
      };
    },
    async synthesize() {
      return {
        response: {
          ability_ids: [],
          answer: "Silver Bloom was not found in the current verified PokéAPI data.",
          continuation_candidates: null,
          item_ids: [],
          move_ids: [],
          pokemon_ids: [],
          table: null,
        },
        usage: { cacheWriteTokens: 0, cachedInputTokens: 0, inputTokens: 10, outputTokens: 2 },
      };
    },
  };
  const orchestrator = new AskOrchestrator({
    executeGraphql: async () => execution,
    model,
    pricing: { cacheWritePerMillion: 0, cachedInputPerMillion: 0, inputPerMillion: 0, outputPerMillion: 0 },
    schemaLookup: async () => ({ matches: [] }),
  });

  const result = await orchestrator.ask("Do you have a creature called Silver Bloom?");

  assert.equal(planCount, 2);
  assert.match(result.response.answer, /couldn't verify.*no lookup succeeded/i);
  assert.doesNotMatch(result.response.answer, /not found/i);
  assert.deepEqual(result.response.pokemon_ids, []);
});

test("transparently discloses a reviewed unofficial nickname resolution", async () => {
  const marillExecution: GraphqlExecution = {
    data: { pokemon: [{ id: 183, name: "marill" }] },
    entityIds: { ability: [], item: [], move: [], pokemon: [183] },
    entityReferences: {
      ability: [],
      item: [],
      move: [],
      pokemon: [{ id: 183, name: "marill" }],
    },
    trace: { ...trace, variables: { name: "marill" } },
  };
  const model: ModelProvider = {
    async plan(input) {
      assert.deepEqual(input.entityResolutions, [{
        alias: "Pikablu",
        canonicalName: "marill",
        disclosure: "an unofficial historical fan name associated with Marill",
      }]);
      return {
        toolCalls: [{
          arguments: { purpose: trace.purpose, query: trace.query, variables: { name: "marill" } },
          callId: "resolve-marill",
          name: "execute_readonly_graphql",
        }],
        usage: { cacheWriteTokens: 0, cachedInputTokens: 0, inputTokens: 10, outputTokens: 2 },
      };
    },
    async synthesize() {
      return {
        response: {
          ability_ids: [],
          answer: "Marill is Water/Fairy.",
          continuation_candidates: null,
          item_ids: [],
          move_ids: [],
          pokemon_ids: [183],
          table: null,
        },
        usage: { cacheWriteTokens: 0, cachedInputTokens: 0, inputTokens: 10, outputTokens: 2 },
      };
    },
  };
  const orchestrator = new AskOrchestrator({
    executeGraphql: async () => marillExecution,
    model,
    pricing: { cacheWritePerMillion: 0, cachedInputPerMillion: 0, inputPerMillion: 0, outputPerMillion: 0 },
    schemaLookup: async () => ({ matches: [] }),
  });

  const result = await orchestrator.ask("What type is the Pokémon called Pikablu?");

  assert.match(result.response.answer, /Pikablu.*unofficial.*Marill/i);
  assert.match(result.response.answer, /Water\/Fairy/i);
  assert.deepEqual(result.response.pokemon_ids, [183]);
});

test("rejects observed hydration IDs that are unrelated to the question and answer", async () => {
  const broadExecution: GraphqlExecution = {
    data: { pokemon: [{ id: 1, name: "bulbasaur" }, { id: 999, name: "unrelated-mon" }] },
    entityIds: { ability: [], item: [], move: [], pokemon: [1, 999] },
    entityReferences: {
      ability: [],
      item: [],
      move: [],
      pokemon: [{ id: 1, name: "bulbasaur" }, { id: 999, name: "unrelated-mon" }],
    },
    trace,
  };
  const orchestrator = new AskOrchestrator({
    executeGraphql: async () => broadExecution,
    model: providerWith({
      ability_ids: [],
      answer: "Bulbasaur is Grass/Poison.",
      continuation_candidates: null,
      item_ids: [],
      move_ids: [],
      pokemon_ids: [1, 999],
      table: null,
    }),
    pricing: { cacheWritePerMillion: 0, cachedInputPerMillion: 0, inputPerMillion: 0, outputPerMillion: 0 },
    schemaLookup: async () => ({ matches: [] }),
  });

  await assert.rejects(
    orchestrator.ask("What type is Bulbasaur?"),
    /unrelated-mon.*not referenced/i,
  );
});

test("derives omitted result hydration from answer-referenced verified evidence", async () => {
  const resultExecution: GraphqlExecution = {
    data: {
      pokemon: [
        { id: 23, name: "ekans" },
        { id: 24, name: "arbok" },
        { id: 58, name: "growlithe" },
        { id: 59, name: "arcanine" },
        { id: 128, name: "tauros" },
        { id: 130, name: "gyarados" },
      ],
    },
    entityIds: { ability: [], item: [], move: [], pokemon: [23, 24, 58, 59, 128, 130] },
    entityReferences: {
      ability: [],
      item: [],
      move: [],
      pokemon: [
        { id: 23, name: "ekans" },
        { id: 24, name: "arbok" },
        { id: 58, name: "growlithe" },
        { id: 59, name: "arcanine" },
        { id: 128, name: "tauros" },
        { id: 130, name: "gyarados" },
      ],
    },
    trace,
  };
  const hydrationExecution: GraphqlExecution = {
    data: { ability: [{ id: 22, name: "intimidate" }] },
    entityIds: { ability: [22], item: [], move: [], pokemon: [] },
    entityReferences: {
      ability: [{ id: 22, name: "intimidate" }],
      item: [],
      move: [],
      pokemon: [],
    },
    trace: { ...trace, purpose: "Resolve answer-referenced entities" },
  };
  const requests: GraphqlRequest[] = [];
  const orchestrator = new AskOrchestrator({
    executeGraphql: async (request) => {
      requests.push(request);
      return requests.length === 1 ? resultExecution : hydrationExecution;
    },
    model: providerWith({
      ability_ids: [],
      answer: "Ekans, Arbok, Growlithe, Arcanine, Tauros, and Gyarados can have Intimidate.",
      continuation_candidates: null,
      item_ids: [],
      move_ids: [],
      pokemon_ids: [23, 24, 58, 59, 128, 130],
      table: null,
    }),
    pricing: { cacheWritePerMillion: 0, cachedInputPerMillion: 0, inputPerMillion: 0, outputPerMillion: 0 },
    schemaLookup: async () => ({ matches: [] }),
  });

  const result = await orchestrator.ask("Which original 151 Pokémon can have Intimidate?");

  assert.deepEqual(result.response.ability_ids, [22]);
  assert.deepEqual(result.response.pokemon_ids, [23, 24, 58, 59, 128, 130]);
  assert.equal(requests.length, 2);
  assert.equal(requests[1].purpose, "Resolve answer-referenced entities");
  assert.ok(
    (requests[1].variables as { names: string[] }).names.includes("intimidate"),
  );
  const schemaSource = readFileSync(
    resolve(
      import.meta.dirname,
      "../../shared/src/commonMain/graphql/des.c5inco.pokedexer.shared/schema.json",
    ),
    "utf8",
  );
  const schema = buildClientSchema(
    (JSON.parse(schemaSource) as { data: IntrospectionQuery }).data,
  );
  const executor = createReadonlyGraphqlExecutor({
    endpoint: "https://graphql.pokeapi.co/v1beta2",
    fetchImpl: async () =>
      new Response(
        JSON.stringify({
          data: { ability: [], item: [], move: [], pokemon: [] },
        }),
        { status: 200 },
      ),
    schema,
  });
  await assert.doesNotReject(
    executor.execute(requests[1]),
    "backend hydration lookup must remain valid under the pinned PokéAPI schema and policy",
  );
});

test("resolves a question target omitted from otherwise complete evidence", async () => {
  const abilityExecution: GraphqlExecution = {
    data: { ability: [{ id: 25, name: "wonder-guard" }] },
    entityIds: { ability: [25], item: [], move: [], pokemon: [] },
    entityReferences: {
      ability: [{ id: 25, name: "wonder-guard" }],
      item: [],
      move: [],
      pokemon: [],
    },
    trace,
  };
  const shedinjaExecution: GraphqlExecution = {
    data: { pokemon: [{ id: 292, name: "shedinja" }] },
    entityIds: { ability: [], item: [], move: [], pokemon: [292] },
    entityReferences: {
      ability: [],
      item: [],
      move: [],
      pokemon: [{ id: 292, name: "shedinja" }],
    },
    trace: { ...trace, purpose: "Resolve answer-referenced entities" },
  };
  let calls = 0;
  const orchestrator = new AskOrchestrator({
    executeGraphql: async () => {
      calls += 1;
      return calls === 1 ? abilityExecution : shedinjaExecution;
    },
    model: providerWith({
      ability_ids: [25],
      answer: "Wonder Guard protects Shedinja from damaging moves that are not super effective.",
      continuation_candidates: null,
      item_ids: [],
      move_ids: [],
      pokemon_ids: [],
      table: null,
    }),
    pricing: { cacheWritePerMillion: 0, cachedInputPerMillion: 0, inputPerMillion: 0, outputPerMillion: 0 },
    schemaLookup: async () => ({ matches: [] }),
  });

  const result = await orchestrator.ask("Which ability protects Shedinja, and how does it work?");

  assert.deepEqual(result.response.ability_ids, [25]);
  assert.deepEqual(result.response.pokemon_ids, [292]);
  assert.equal(calls, 2);
});

test("does not derive unrelated or unmentioned supporting hydration IDs", async () => {
  const broadExecution: GraphqlExecution = {
    data: {
      pokemon: [{ id: 1, name: "bulbasaur" }],
      supporting: {
        ability: [{ id: 65, name: "overgrow" }],
        pokemon: [{ id: 4, name: "charmander" }],
      },
    },
    entityIds: { ability: [65], item: [], move: [], pokemon: [1, 4] },
    entityReferences: {
      ability: [{ id: 65, name: "overgrow" }],
      item: [],
      move: [],
      pokemon: [{ id: 1, name: "bulbasaur" }, { id: 4, name: "charmander" }],
    },
    trace,
  };
  let calls = 0;
  const orchestrator = new AskOrchestrator({
    executeGraphql: async () => {
      calls += 1;
      return broadExecution;
    },
    model: providerWith({
      ability_ids: [],
      answer: "Bulbasaur is Grass/Poison type.",
      continuation_candidates: null,
      item_ids: [],
      move_ids: [],
      pokemon_ids: [],
      table: null,
    }),
    pricing: { cacheWritePerMillion: 0, cachedInputPerMillion: 0, inputPerMillion: 0, outputPerMillion: 0 },
    schemaLookup: async () => ({ matches: [] }),
  });

  const result = await orchestrator.ask("What type is Bulbasaur?");

  assert.deepEqual(result.response.pokemon_ids, [1]);
  assert.deepEqual(result.response.ability_ids, []);
  assert.equal(calls, 1);
});

test("accepts official display forms for canonical PokéAPI hydration names", async (context) => {
  const cases = [
    { answer: "Farfetch'd is Normal/Flying.", canonical: "farfetchd", id: 83, question: "What type is Farfetch'd?" },
    { answer: "Nidoran♀ is Poison type.", canonical: "nidoran-f", id: 29, question: "What type is the female Nidoran?" },
    { answer: "Nidoran♂ is Poison type.", canonical: "nidoran-m", id: 32, question: "What type is the male Nidoran?" },
    { answer: "Sirfetch'd is Fighting type.", canonical: "sirfetchd", id: 865, question: "What type is Sirfetch'd?" },
  ];

  for (const item of cases) {
    await context.test(item.canonical, async () => {
      const canonicalExecution: GraphqlExecution = {
        data: { pokemon: [{ id: item.id, name: item.canonical }] },
        entityIds: { ability: [], item: [], move: [], pokemon: [item.id] },
        entityReferences: {
          ability: [],
          item: [],
          move: [],
          pokemon: [{ id: item.id, name: item.canonical }],
        },
        trace,
      };
      const orchestrator = new AskOrchestrator({
        executeGraphql: async () => canonicalExecution,
        model: providerWith({
          ability_ids: [],
          answer: item.answer,
          continuation_candidates: null,
          item_ids: [],
          move_ids: [],
          pokemon_ids: [item.id],
          table: null,
        }),
        pricing: { cacheWritePerMillion: 0, cachedInputPerMillion: 0, inputPerMillion: 0, outputPerMillion: 0 },
        schemaLookup: async () => ({ matches: [] }),
      });

      const result = await orchestrator.ask(item.question);

      assert.deepEqual(result.response.pokemon_ids, [item.id]);
    });
  }
});

test("accepts apostrophized item display names for hydration", async () => {
  const kingsRockExecution: GraphqlExecution = {
    data: { item: [{ id: 221, name: "kings-rock" }] },
    entityIds: { ability: [], item: [221], move: [], pokemon: [] },
    entityReferences: {
      ability: [],
      item: [{ id: 221, name: "kings-rock" }],
      move: [],
      pokemon: [],
    },
    trace,
  };
  const orchestrator = new AskOrchestrator({
    executeGraphql: async () => kingsRockExecution,
    model: providerWith({
      ability_ids: [],
      answer: "King's Rock can make the holder's attacks cause flinching.",
      continuation_candidates: null,
      item_ids: [221],
      move_ids: [],
      pokemon_ids: [],
      table: null,
    }),
    pricing: { cacheWritePerMillion: 0, cachedInputPerMillion: 0, inputPerMillion: 0, outputPerMillion: 0 },
    schemaLookup: async () => ({ matches: [] }),
  });

  const result = await orchestrator.ask("What does King's Rock do?");

  assert.deepEqual(result.response.item_ids, [221]);
});
