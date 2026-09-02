import assert from "node:assert/strict";
import { createServer, type RequestListener } from "node:http";
import test, { type TestContext } from "node:test";

import { createApp } from "../src/app.js";
import { AskEvaluationError } from "../src/orchestrator.js";
import { ContinuationCursorError } from "../src/pagination.js";
import { createRequestLimiter } from "../src/request-limiter.js";

async function serve(t: TestContext, app: RequestListener): Promise<string> {
  const server = createServer(app);
  server.listen(0, "127.0.0.1");
  await new Promise<void>((resolve) => server.once("listening", resolve));
  t.after(() => server.close());
  const address = server.address();
  if (!address || typeof address === "string") throw new Error("Missing server address");
  return `http://127.0.0.1:${address.port}`;
}

function post(origin: string, path: string, body: unknown) {
  return fetch(`${origin}${path}`, {
    body: JSON.stringify(body),
    headers: { "content-type": "application/json" },
    method: "POST",
  });
}

const internalAnswer = {
  diagnostics: { phase: "validation", tool_errors: [] },
  metrics: { estimated_cost_usd: 0.001, total_ms: 1_200 },
  response: {
    ability_ids: [65],
    answer: "Bulbasaur can have Overgrow.",
    item_ids: [],
    move_ids: [],
    pokemon_ids: [1],
    queries: [{ query: "internal query must not cross the public boundary" }],
    table: { columns: ["Pokémon"], rows: [["Bulbasaur"]] },
  },
};

test("serves a versioned mobile response without evaluation internals", async (t) => {
  const questions: string[] = [];
  const origin = await serve(
    t,
    createApp({
      async ask(question) {
        questions.push(question);
        return internalAnswer;
      },
    }),
  );

  const health = await fetch(`${origin}/health`);
  assert.deepEqual(await health.json(), { api_version: 1, status: "ok" });

  const response = await post(origin, "/v1/ask", { cursor: null, question: "  Bulbasaur?  " });
  assert.equal(response.status, 200);
  assert.deepEqual(await response.json(), {
    answer: "Bulbasaur can have Overgrow.",
    api_version: 1,
    entities: { ability: [65], item: [], move: [], pokemon: [1] },
    kind: "answer",
    pagination: null,
    table: { columns: ["Pokémon"], rows: [["Bulbasaur"]] },
  });
  assert.deepEqual(questions, ["Bulbasaur?"]);
});

test("uses the same public envelope for continuation pages", async (t) => {
  const origin = await serve(
    t,
    createApp({
      ask(_question, cursor) {
        assert.equal(cursor, "signed-cursor");
        return {
          metrics: { estimated_cost_usd: 0, total_ms: 0 },
          response: {
            ability_ids: [],
            item_ids: [],
            move_ids: [],
            pagination: {
              continuation_cursor: "next-cursor",
              has_more: true,
              page_size: 8,
              scope: "verified_entity_ids",
            },
            pokemon_ids: [9, 10],
          },
        };
      },
    }),
  );

  const response = await post(origin, "/v1/ask", {
    cursor: "signed-cursor",
    question: "Find Water Pokémon",
  });

  assert.equal(response.status, 200);
  assert.deepEqual(await response.json(), {
    answer: null,
    api_version: 1,
    entities: { ability: [], item: [], move: [], pokemon: [9, 10] },
    kind: "continuation",
    pagination: { has_more: true, next_cursor: "next-cursor", page_size: 8 },
    table: null,
  });
});

test("keeps evaluation routes disabled unless explicitly enabled", async (t) => {
  const origin = await serve(
    t,
    createApp({
      ask: async () => internalAnswer,
    }),
  );

  const root = await fetch(origin);
  assert.equal(root.status, 404);
  assert.deepEqual(await root.json(), {
    api_version: 1,
    error: { code: "NOT_FOUND", message: "Not found", retryable: false },
  });

  const evaluation = await post(origin, "/v1/evaluate", { question: "Bulbasaur?" });
  assert.equal(evaluation.status, 404);
});

test("returns stable safe public errors", async (t) => {
  let failure: "cursor" | "model" = "cursor";
  const origin = await serve(
    t,
    createApp({
      ask() {
        if (failure === "cursor") throw new ContinuationCursorError("cursor leaked details");
        throw new AskEvaluationError("model leaked details", {
          diagnostics: { failure_class: "model", phase: "validation", tool_errors: [] },
          metrics: internalAnswer.metrics as never,
        });
      },
    }),
  );

  const invalid = await post(origin, "/v1/ask", { question: "" });
  assert.equal(invalid.status, 400);
  assert.deepEqual(await invalid.json(), {
    api_version: 1,
    error: {
      code: "INVALID_REQUEST",
      message: "question must be between 1 and 500 characters",
      retryable: false,
    },
  });

  const cursor = await post(origin, "/v1/ask", {
    cursor: "bad-cursor",
    question: "Find Water Pokémon",
  });
  assert.equal(cursor.status, 400);
  assert.deepEqual(await cursor.json(), {
    api_version: 1,
    error: {
      code: "INVALID_CURSOR",
      message: "The continuation cursor is invalid or expired",
      retryable: false,
    },
  });

  failure = "model";
  const model = await post(origin, "/v1/ask", { question: "Bulbasaur?" });
  assert.equal(model.status, 502);
  assert.deepEqual(await model.json(), {
    api_version: 1,
    error: {
      code: "UPSTREAM_FAILURE",
      message: "Ask Pokedexer is temporarily unavailable",
      retryable: true,
    },
  });
});

test("enforces the public request deadline", async (t) => {
  let requestSignal: AbortSignal | undefined;
  const origin = await serve(
    t,
    createApp({
      async ask(_question, _cursor, signal) {
        requestSignal = signal;
        await new Promise<void>((_resolve, reject) => {
          signal?.addEventListener("abort", () => reject(signal.reason), { once: true });
        });
        throw new Error("unreachable");
      },
      requestTimeoutMs: 5,
    }),
  );

  const response = await post(origin, "/v1/ask", { question: "Bulbasaur?" });
  assert.equal(response.status, 504);
  assert.deepEqual(await response.json(), {
    api_version: 1,
    error: {
      code: "DEADLINE_EXCEEDED",
      message: "Ask Pokedexer exceeded its response deadline",
      retryable: true,
    },
  });
  assert.equal(requestSignal?.aborted, true);
});

test("returns Retry-After when per-instance concurrency is exhausted", async (t) => {
  let finishFirst: (() => void) | undefined;
  let firstStarted: (() => void) | undefined;
  const started = new Promise<void>((resolve) => {
    firstStarted = resolve;
  });
  const origin = await serve(
    t,
    createApp({
      async ask() {
        firstStarted?.();
        await new Promise<void>((resolve) => {
          finishFirst = resolve;
        });
        return internalAnswer;
      },
      requestLimiter: createRequestLimiter({
        maxConcurrent: 1,
        maxRequests: 10,
        windowMs: 60_000,
      }),
    }),
  );

  const first = post(origin, "/v1/ask", { question: "Bulbasaur?" });
  await started;
  const limited = await post(origin, "/v1/ask", { question: "Ivysaur?" });

  assert.equal(limited.status, 429);
  assert.equal(limited.headers.get("retry-after"), "1");
  assert.deepEqual(await limited.json(), {
    api_version: 1,
    error: {
      code: "RATE_LIMITED",
      message: "Ask Pokedexer is busy; try again shortly",
      retryable: true,
    },
  });

  finishFirst?.();
  assert.equal((await first).status, 200);
});

test("requires JSON requests without invoking the backend", async (t) => {
  let called = false;
  const origin = await serve(
    t,
    createApp({
      ask() {
        called = true;
        return internalAnswer;
      },
    }),
  );

  const response = await fetch(`${origin}/v1/ask`, {
    body: JSON.stringify({ question: "Bulbasaur?" }),
    headers: { "content-type": "text/plain" },
    method: "POST",
  });

  assert.equal(response.status, 400);
  assert.equal(called, false);
  assert.deepEqual(await response.json(), {
    api_version: 1,
    error: {
      code: "INVALID_REQUEST",
      message: "content-type must be application/json",
      retryable: false,
    },
  });
});

test("retains the explicit evaluation surface for local model analysis", async (t) => {
  const origin = await serve(
    t,
    createApp({
      ask: async () => internalAnswer,
      evaluation: { provider: "OpenAI" },
    }),
  );

  const page = await fetch(origin);
  assert.equal(page.status, 200);
  assert.match(await page.text(), /Bounded OpenAI tool loop/);

  const response = await post(origin, "/v1/evaluate", { question: "Bulbasaur?" });
  assert.equal(response.status, 200);
  assert.deepEqual(await response.json(), internalAnswer);
});
