import assert from "node:assert/strict";
import test from "node:test";

import { createPaginationService } from "../src/pagination.js";
import { AskOrchestrator, type ModelProvider } from "../src/orchestrator.js";
import { parseSynthesis, synthesisInstructions } from "../src/model-contract.js";
import { resolvePaginationConfig } from "../src/pagination.js";

const secret = "test-only-pagination-secret-at-least-32-bytes";

test("returns stable eight-ID continuation pages without duplicates", () => {
  const pagination = createPaginationService({ now: () => 1_000, secret, ttlMs: 60_000 });
  const first = pagination.firstPage(
    "Show me all matching Pokémon",
    { ability: [], item: [], move: [], pokemon: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17] },
  );

  assert.deepEqual(first.pokemon_ids, [1, 2, 3, 4, 5, 6, 7, 8]);
  assert.equal(first.pagination.page_size, 8);
  assert.equal(first.pagination.has_more, true);
  assert.equal(first.pagination.scope, "verified_entity_ids");
  assert.ok(first.pagination.continuation_cursor);

  const second = pagination.nextPage(
    "Show me all matching Pokémon",
    first.pagination.continuation_cursor,
  );
  assert.deepEqual(second.pokemon_ids, [9, 10, 11, 12, 13, 14, 15, 16]);
  assert.equal(second.pagination.has_more, true);

  const third = pagination.nextPage(
    "Show me all matching Pokémon",
    second.pagination.continuation_cursor!,
  );
  assert.deepEqual(third.pokemon_ids, [17]);
  assert.equal(third.pagination.has_more, false);
  assert.equal(third.pagination.continuation_cursor, null);
  assert.deepEqual(new Set([...first.pokemon_ids, ...second.pokemon_ids, ...third.pokemon_ids]).size, 17);
});

test("fails closed for tampered, malformed, query-mismatched, and expired cursors", () => {
  let now = 1_000;
  const pagination = createPaginationService({ now: () => now, secret, ttlMs: 100 });
  const first = pagination.firstPage(
    "original query",
    { ability: [], item: [], move: [], pokemon: [1, 2, 3, 4, 5, 6, 7, 8, 9] },
  );
  const cursor = first.pagination.continuation_cursor;
  assert.ok(cursor);

  const tampered = `${cursor.slice(0, -1)}${cursor.endsWith("a") ? "b" : "a"}`;
  assert.throws(() => pagination.nextPage("original query", tampered), /Invalid continuation cursor/);
  assert.throws(() => pagination.nextPage("original query", "not-a-cursor"), /Invalid continuation cursor/);
  assert.throws(() => pagination.nextPage("different query", cursor), /does not match/);
  now = 1_101;
  assert.throws(() => pagination.nextPage("original query", cursor), /expired/);
});

test("continuation performs no provider or GraphQL invocation", async () => {
  let providerCalls = 0;
  let graphqlCalls = 0;
  const model: ModelProvider = {
    async plan() {
      providerCalls += 1;
      throw new Error("provider must not run");
    },
    async synthesize() {
      providerCalls += 1;
      throw new Error("provider must not run");
    },
  };
  const pagination = createPaginationService({ now: () => 1_000, secret, ttlMs: 60_000 });
  const first = pagination.firstPage(
    "broad query",
    { ability: [], item: [], move: [], pokemon: [1, 2, 3, 4, 5, 6, 7, 8, 9] },
  );
  const orchestrator = new AskOrchestrator({
    executeGraphql: async () => {
      graphqlCalls += 1;
      throw new Error("GraphQL must not run");
    },
    model,
    pagination,
    pricing: {
      cacheWritePerMillion: 0,
      cachedInputPerMillion: 0,
      inputPerMillion: 0,
      outputPerMillion: 0,
    },
    schemaLookup: async () => {
      throw new Error("schema lookup must not run");
    },
  });

  const result = orchestrator.continue("broad query", first.pagination.continuation_cursor!);

  assert.deepEqual(result.response.pokemon_ids, [9]);
  assert.equal(result.metrics.model_calls, 0);
  assert.equal(result.metrics.graphql_calls, 0);
  assert.equal(providerCalls, 0);
  assert.equal(graphqlCalls, 0);
});

test("instructs synthesis to choose the ascending first hydration page", () => {
  assert.match(synthesisInstructions, /continuation candidates[^.]*ascending/i);
});

test("bounds private continuation candidates independently from public hydration IDs", () => {
  assert.throws(
    () =>
      parseSynthesis(
        JSON.stringify({
          ability_ids: [],
          answer: "Too many candidates",
          continuation_candidates: {
            ability_ids: [],
            item_ids: [],
            move_ids: [],
            pokemon_ids: Array.from({ length: 101 }, (_, index) => index + 1),
          },
          item_ids: [],
          move_ids: [],
          pokemon_ids: [1, 2, 3, 4, 5, 6, 7, 8],
          table: null,
        }),
      ),
    /Too big/,
  );
});

test("validates isolated runtime cursor signing configuration", () => {
  assert.deepEqual(
    resolvePaginationConfig({
      CURSOR_SIGNING_SECRET: secret,
      CURSOR_TTL_SECONDS: "60",
    }),
    { secret, ttlMs: 60_000 },
  );
  assert.throws(() => resolvePaginationConfig({}), /CURSOR_SIGNING_SECRET/);
  assert.throws(
    () => resolvePaginationConfig({ CURSOR_SIGNING_SECRET: secret, CURSOR_TTL_SECONDS: "0" }),
    /CURSOR_TTL_SECONDS/,
  );
});
