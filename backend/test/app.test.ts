import assert from "node:assert/strict";
import { createServer } from "node:http";
import test from "node:test";

import { createApp } from "../src/app.js";
import { AskEvaluationError } from "../src/orchestrator.js";
import { ContinuationCursorError } from "../src/pagination.js";

test("serves health and evaluation endpoints without exposing model credentials", async (t) => {
  const questions: string[] = [];
  const app = createApp({
    async ask(question) {
      questions.push(question);
      return {
        metrics: { estimated_cost_usd: 0.001, total_ms: 1200 },
        response: { answer: "Bulbasaur is Grass/Poison.", queries: [] },
      };
    },
    model: "gpt-5.6-luna",
    provider: "OpenAI",
  });
  const server = createServer(app);
  server.listen(0, "127.0.0.1");
  await new Promise<void>((resolve) => server.once("listening", resolve));
  t.after(() => server.close());
  const address = server.address();
  if (!address || typeof address === "string") throw new Error("Missing server address");
  const origin = `http://127.0.0.1:${address.port}`;

  const health = await fetch(`${origin}/health`).then((response) => response.json());
  assert.deepEqual(health, { model: "gpt-5.6-luna", provider: "OpenAI", status: "ok" });

  const evaluationPage = await fetch(origin).then((response) => response.text());
  assert.match(evaluationPage, /Bounded OpenAI tool loop/);

  const evaluationResponse = await fetch(`${origin}/v1/evaluate`, {
    body: JSON.stringify({ question: "What type is Bulbasaur?" }),
    headers: { "content-type": "application/json" },
    method: "POST",
  });
  assert.equal(evaluationResponse.status, 200);
  assert.deepEqual(await evaluationResponse.json(), {
    metrics: { estimated_cost_usd: 0.001, total_ms: 1200 },
    response: { answer: "Bulbasaur is Grass/Poison.", queries: [] },
  });
  assert.deepEqual(questions, ["What type is Bulbasaur?"]);

  const missingQuestion = await fetch(`${origin}/v1/evaluate`, {
    body: "{}",
    headers: { "content-type": "application/json" },
    method: "POST",
  });
  assert.equal(missingQuestion.status, 400);
});

test("preserves accumulated metrics and safe diagnostics on evaluation failure", async (t) => {
  const app = createApp({
    async ask() {
      throw new AskEvaluationError("Final response referenced an unverified ability ID", {
        diagnostics: { phase: "validation", tool_errors: [] },
        metrics: {
          cache_write_tokens: 0,
          cached_input_tokens: 0,
          cost_complete: true,
          estimated_cost_usd: 0.002,
          graphql_attempts: 1,
          graphql_calls: 1,
          graphql_ms: 120,
          input_tokens: 1_000,
          model_attempts: 3,
          model_calls: 3,
          model_ms: 900,
          output_tokens: 200,
          schema_lookup_ms: 0,
          schema_lookups: 0,
          total_ms: 1_050,
        },
      });
    },
    model: "gpt-5.6-luna",
    provider: "OpenAI",
  });
  const server = createServer(app);
  server.listen(0, "127.0.0.1");
  await new Promise<void>((resolve) => server.once("listening", resolve));
  t.after(() => server.close());
  const address = server.address();
  if (!address || typeof address === "string") throw new Error("Missing server address");

  const response = await fetch(`http://127.0.0.1:${address.port}/v1/evaluate`, {
    body: JSON.stringify({ question: "Which Pokémon have Overgrow?" }),
    headers: { "content-type": "application/json" },
    method: "POST",
  });

  assert.equal(response.status, 502);
  const body = (await response.json()) as Record<string, unknown>;
  assert.equal(body.error, "Final response referenced an unverified ability ID");
  assert.deepEqual(body.diagnostics, { phase: "validation", tool_errors: [] });
  assert.deepEqual(body.metrics, {
    cache_write_tokens: 0,
    cached_input_tokens: 0,
    cost_complete: true,
    estimated_cost_usd: 0.002,
    graphql_attempts: 1,
    graphql_calls: 1,
    graphql_ms: 120,
    input_tokens: 1_000,
    model_attempts: 3,
    model_calls: 3,
    model_ms: 900,
    output_tokens: 200,
    schema_lookup_ms: 0,
    schema_lookups: 0,
    total_ms: 1_050,
  });
});

test("maps rejected continuation cursors to a safe client error", async (t) => {
  const app = createApp({
    ask(_question, cursor) {
      assert.equal(cursor, "bad-cursor");
      throw new ContinuationCursorError("Invalid continuation cursor");
    },
    model: "gpt-5.6-luna",
    provider: "OpenAI",
  });
  const server = createServer(app);
  server.listen(0, "127.0.0.1");
  await new Promise<void>((resolve) => server.once("listening", resolve));
  t.after(() => server.close());
  const address = server.address();
  if (!address || typeof address === "string") throw new Error("Missing server address");

  const response = await fetch(`http://127.0.0.1:${address.port}/v1/ask`, {
    body: JSON.stringify({ cursor: "bad-cursor", question: "Find Water Pokémon" }),
    headers: { "content-type": "application/json" },
    method: "POST",
  });

  assert.equal(response.status, 400);
  assert.deepEqual(await response.json(), { error: "Invalid continuation cursor" });
});
