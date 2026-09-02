import type { IncomingMessage, RequestListener, ServerResponse } from "node:http";

import { AskEvaluationError } from "./orchestrator.js";
import { ContinuationCursorError } from "./pagination.js";
import {
  RequestLimitError,
  createRequestLimiter,
  type RequestLimiter,
} from "./request-limiter.js";

type ProviderLabel = "Gemini" | "OpenAI" | "OpenRouter";
type PublicErrorCode =
  | "DEADLINE_EXCEEDED"
  | "INVALID_CURSOR"
  | "INVALID_REQUEST"
  | "NOT_FOUND"
  | "RATE_LIMITED"
  | "UPSTREAM_FAILURE";

interface AppOptions {
  ask(question: string, cursor?: string, signal?: AbortSignal): Promise<unknown> | unknown;
  evaluation?: { provider: ProviderLabel };
  requestLimiter?: RequestLimiter;
  requestTimeoutMs?: number;
}

class HttpError extends Error {
  constructor(
    readonly status: number,
    readonly code: PublicErrorCode,
    message: string,
    readonly retryable = false,
  ) {
    super(message);
  }
}

class DeadlineError extends Error {}

function evaluationPage(provider: "Gemini" | "OpenAI" | "OpenRouter"): string {
  return `<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Ask Pokedexer evaluation</title>
  <style>
    :root { color-scheme: dark; font-family: ui-sans-serif, system-ui, sans-serif; }
    body { background: #111318; color: #f2f4f8; margin: 0; }
    main { margin: 0 auto; max-width: 920px; padding: 40px 20px 80px; }
    h1 { font-size: clamp(28px, 5vw, 48px); margin-bottom: 8px; }
    p { color: #aeb7c7; line-height: 1.55; }
    form { display: grid; gap: 12px; margin: 28px 0; }
    textarea, button { border: 1px solid #343b49; border-radius: 12px; font: inherit; }
    textarea { background: #191d25; color: inherit; min-height: 92px; padding: 14px; resize: vertical; }
    button { background: #7c5cff; color: white; cursor: pointer; font-weight: 700; padding: 12px 18px; }
    button:disabled { cursor: wait; opacity: .6; }
    .samples { display: flex; flex-wrap: wrap; gap: 8px; }
    .samples button { background: #202632; font-size: 13px; font-weight: 500; padding: 7px 10px; }
    .status { min-height: 24px; }
    pre { background: #0a0c10; border: 1px solid #2b313c; border-radius: 12px; overflow: auto; padding: 16px; white-space: pre-wrap; }
  </style>
</head>
<body>
<main>
  <h1>Ask Pokedexer</h1>
  <p>Bounded ${provider} tool loop with server-validated PokéAPI GraphQL. Results include authoritative query provenance, latency, tokens, and estimated cost.</p>
  <div class="samples" aria-label="Example questions">
    <button type="button">What are Bulbasaur's types and base stats?</button>
    <button type="button">Which Gen I Grass Pokémon learn Razor Leaf by level-up in FireRed/LeafGreen?</button>
    <button type="button">What do Leftovers and Regenerator do?</button>
    <button type="button">What is the latest version group with level-up data for Eevee?</button>
  </div>
  <form>
    <label for="question">Question</label>
    <textarea id="question" maxlength="500" required>What are Bulbasaur's types and base stats?</textarea>
    <button id="submit" type="submit">Run evaluation</button>
  </form>
  <p class="status" role="status"></p>
  <pre aria-live="polite">No evaluation run yet.</pre>
</main>
<script>
  const form = document.querySelector('form');
  const question = document.querySelector('#question');
  const submit = document.querySelector('#submit');
  const status = document.querySelector('.status');
  const output = document.querySelector('pre');
  document.querySelectorAll('.samples button').forEach((button) => {
    button.addEventListener('click', () => { question.value = button.textContent; question.focus(); });
  });
  form.addEventListener('submit', async (event) => {
    event.preventDefault();
    submit.disabled = true;
    status.textContent = 'Planning and querying…';
    output.textContent = '';
    const started = performance.now();
    try {
      const response = await fetch('/v1/evaluate', {
        method: 'POST',
        headers: {'content-type': 'application/json'},
        body: JSON.stringify({question: question.value})
      });
      const body = await response.json();
      if (!response.ok) throw new Error(body.error?.message || body.error || 'Evaluation failed');
      status.textContent = 'Completed in ' + ((performance.now() - started) / 1000).toFixed(1) + 's';
      output.textContent = JSON.stringify(body, null, 2);
    } catch (error) {
      status.textContent = 'Failed';
      output.textContent = error instanceof Error ? error.message : String(error);
    } finally {
      submit.disabled = false;
    }
  });
</script>
</body>
</html>`;
}

function writeJson(
  response: ServerResponse,
  status: number,
  payload: unknown,
  headers: Record<string, string> = {},
): void {
  response.writeHead(status, {
    "cache-control": "no-store",
    "content-type": "application/json; charset=utf-8",
    "x-content-type-options": "nosniff",
    ...headers,
  });
  response.end(JSON.stringify(payload));
}

async function readJson(request: IncomingMessage): Promise<unknown> {
  const chunks: Buffer[] = [];
  let size = 0;
  for await (const chunk of request) {
    const buffer = Buffer.from(chunk);
    size += buffer.length;
    if (size > 10_000) throw new HttpError(400, "INVALID_REQUEST", "Request body is too large");
    chunks.push(buffer);
  }
  try {
    return JSON.parse(Buffer.concat(chunks).toString("utf8"));
  } catch {
    throw new HttpError(400, "INVALID_REQUEST", "Request body must be valid JSON");
  }
}

function askRequestFrom(payload: unknown): { cursor?: string; question: string } {
  if (!payload || typeof payload !== "object" || !("question" in payload)) {
    throw new HttpError(400, "INVALID_REQUEST", "question is required");
  }
  const question = (payload as { question?: unknown }).question;
  if (typeof question !== "string" || !question.trim() || question.length > 500) {
    throw new HttpError(
      400,
      "INVALID_REQUEST",
      "question must be between 1 and 500 characters",
    );
  }
  const cursor = (payload as { cursor?: unknown }).cursor;
  if (
    cursor !== undefined &&
    cursor !== null &&
    (typeof cursor !== "string" || !cursor || cursor.length > 10_000)
  ) {
    throw new HttpError(
      400,
      "INVALID_REQUEST",
      "cursor must be a non-empty continuation cursor",
    );
  }
  return { cursor: cursor ?? undefined, question: question.trim() };
}

function publicResponseFrom(result: unknown, continuation: boolean) {
  if (!result || typeof result !== "object" || !("response" in result)) {
    throw new Error("Ask Pokedexer returned an invalid internal response");
  }
  const internal = (result as { response?: unknown }).response;
  if (!internal || typeof internal !== "object") {
    throw new Error("Ask Pokedexer returned an invalid internal response");
  }
  const response = internal as Record<string, unknown>;
  const ids = (name: string): number[] => {
    const value = response[name];
    if (!Array.isArray(value) || !value.every((id) => Number.isInteger(id) && id > 0)) {
      throw new Error("Ask Pokedexer returned invalid entity IDs");
    }
    return value as number[];
  };
  let pagination = null;
  if (response.pagination && typeof response.pagination === "object") {
    const internalPagination = response.pagination as Record<string, unknown>;
    if (
      typeof internalPagination.has_more !== "boolean" ||
      typeof internalPagination.page_size !== "number" ||
      (internalPagination.continuation_cursor !== null &&
        typeof internalPagination.continuation_cursor !== "string")
    ) {
      throw new Error("Ask Pokedexer returned invalid pagination");
    }
    pagination = {
      has_more: internalPagination.has_more,
      next_cursor: internalPagination.continuation_cursor,
      page_size: internalPagination.page_size,
    };
  }
  if (!continuation && typeof response.answer !== "string") {
    throw new Error("Ask Pokedexer returned an invalid answer");
  }
  return {
    answer: continuation ? null : response.answer,
    api_version: 1,
    entities: {
      ability: ids("ability_ids"),
      item: ids("item_ids"),
      move: ids("move_ids"),
      pokemon: ids("pokemon_ids"),
    },
    kind: continuation ? "continuation" : "answer",
    pagination,
    table: continuation ? null : (response.table ?? null),
  };
}

function writePublicError(
  response: ServerResponse,
  status: number,
  code: PublicErrorCode,
  message: string,
  retryable: boolean,
  headers?: Record<string, string>,
): void {
  writeJson(response, status, { api_version: 1, error: { code, message, retryable } }, headers);
}

export function createApp(options: AppOptions): RequestListener {
  const requestLimiter = options.requestLimiter ?? createRequestLimiter({
    maxConcurrent: 4,
    maxRequests: 30,
    windowMs: 60_000,
  });
  const requestTimeoutMs = options.requestTimeoutMs ?? 30_000;
  if (!Number.isInteger(requestTimeoutMs) || requestTimeoutMs < 1) {
    throw new Error("requestTimeoutMs must be a positive integer");
  }

  return async (request, response) => {
    let evaluationRequest = false;
    let deadlineSignal: AbortSignal | undefined;
    try {
      const url = new URL(request.url ?? "/", "http://localhost");
      if (request.method === "GET" && url.pathname === "/health") {
        writeJson(response, 200, { api_version: 1, status: "ok" });
        return;
      }
      if (request.method === "GET" && url.pathname === "/" && options.evaluation) {
        response.writeHead(200, {
          "cache-control": "no-store",
          "content-type": "text/html; charset=utf-8",
          "x-content-type-options": "nosniff",
        });
        response.end(evaluationPage(options.evaluation.provider));
        return;
      }
      if (
        request.method === "POST" &&
        (url.pathname === "/v1/ask" || (url.pathname === "/v1/evaluate" && options.evaluation))
      ) {
        evaluationRequest = url.pathname === "/v1/evaluate";
        if (!request.headers["content-type"]?.toLowerCase().startsWith("application/json")) {
          throw new HttpError(400, "INVALID_REQUEST", "content-type must be application/json");
        }
        const { cursor, question } = askRequestFrom(await readJson(request));
        const release = requestLimiter.acquire();
        const deadline = new DeadlineError("Ask Pokedexer exceeded its response deadline");
        const controller = new AbortController();
        deadlineSignal = controller.signal;
        let timeout: ReturnType<typeof setTimeout>;
        const deadlinePromise = new Promise<never>((_resolve, reject) => {
          timeout = setTimeout(() => {
            controller.abort(deadline);
            reject(deadline);
          }, requestTimeoutMs);
        });
        const work = Promise.resolve().then(() => options.ask(question, cursor, controller.signal));
        void work.then(release, release);
        try {
          const result = await Promise.race([work, deadlinePromise]);
          writeJson(
            response,
            200,
            evaluationRequest ? result : publicResponseFrom(result, cursor !== undefined),
          );
        } finally {
          clearTimeout(timeout!);
        }
        return;
      }
      writePublicError(response, 404, "NOT_FOUND", "Not found", false);
    } catch (error) {
      if (evaluationRequest && error instanceof AskEvaluationError) {
        writeJson(response, 502, { error: error.message, ...error.evaluation });
        return;
      }
      if (error instanceof HttpError) {
        writePublicError(response, error.status, error.code, error.message, error.retryable);
        return;
      }
      if (error instanceof RequestLimitError) {
        writePublicError(
          response,
          429,
          "RATE_LIMITED",
          "Ask Pokedexer is busy; try again shortly",
          true,
          { "retry-after": String(error.retryAfterSeconds) },
        );
        return;
      }
      if (error instanceof ContinuationCursorError) {
        if (evaluationRequest) {
          writeJson(response, 400, { error: error.message });
        } else {
          writePublicError(
            response,
            400,
            "INVALID_CURSOR",
            "The continuation cursor is invalid or expired",
            false,
          );
        }
        return;
      }
      if (error instanceof DeadlineError || deadlineSignal?.aborted) {
        writePublicError(
          response,
          504,
          "DEADLINE_EXCEEDED",
          "Ask Pokedexer exceeded its response deadline",
          true,
        );
        return;
      }
      console.error("Ask Pokedexer request failed", {
        error_type: error instanceof Error ? error.constructor.name : typeof error,
      });
      writePublicError(
        response,
        502,
        "UPSTREAM_FAILURE",
        "Ask Pokedexer is temporarily unavailable",
        true,
      );
    }
  };
}
