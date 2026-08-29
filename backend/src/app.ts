import type { IncomingMessage, RequestListener, ServerResponse } from "node:http";

import { AskEvaluationError } from "./orchestrator.js";
import { ContinuationCursorError } from "./pagination.js";

interface AppOptions {
  ask(question: string, cursor?: string): Promise<unknown> | unknown;
  model: string;
  provider: "Gemini" | "OpenAI" | "OpenRouter";
}

class HttpError extends Error {
  constructor(
    readonly status: number,
    message: string,
  ) {
    super(message);
  }
}

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
      if (!response.ok) throw new Error(body.error || 'Evaluation failed');
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

function writeJson(response: ServerResponse, status: number, payload: unknown): void {
  response.writeHead(status, {
    "cache-control": "no-store",
    "content-type": "application/json; charset=utf-8",
    "x-content-type-options": "nosniff",
  });
  response.end(JSON.stringify(payload));
}

async function readJson(request: IncomingMessage): Promise<unknown> {
  const chunks: Buffer[] = [];
  let size = 0;
  for await (const chunk of request) {
    const buffer = Buffer.from(chunk);
    size += buffer.length;
    if (size > 10_000) throw new HttpError(413, "Request body is too large");
    chunks.push(buffer);
  }
  try {
    return JSON.parse(Buffer.concat(chunks).toString("utf8"));
  } catch {
    throw new HttpError(400, "Request body must be valid JSON");
  }
}

function askRequestFrom(payload: unknown): { cursor?: string; question: string } {
  if (!payload || typeof payload !== "object" || !("question" in payload)) {
    throw new HttpError(400, "question is required");
  }
  const question = (payload as { question?: unknown }).question;
  if (typeof question !== "string" || !question.trim() || question.length > 500) {
    throw new HttpError(400, "question must be between 1 and 500 characters");
  }
  const cursor = (payload as { cursor?: unknown }).cursor;
  if (cursor !== undefined && (typeof cursor !== "string" || !cursor || cursor.length > 100_000)) {
    throw new HttpError(400, "cursor must be a non-empty continuation cursor");
  }
  return { cursor, question };
}

export function createApp(options: AppOptions): RequestListener {
  let inFlight = 0;

  return async (request, response) => {
    try {
      const url = new URL(request.url ?? "/", "http://localhost");
      if (request.method === "GET" && url.pathname === "/health") {
        writeJson(response, 200, { model: options.model, provider: options.provider, status: "ok" });
        return;
      }
      if (request.method === "GET" && url.pathname === "/") {
        response.writeHead(200, {
          "cache-control": "no-store",
          "content-type": "text/html; charset=utf-8",
          "x-content-type-options": "nosniff",
        });
        response.end(evaluationPage(options.provider));
        return;
      }
      if (
        request.method === "POST" &&
        (url.pathname === "/v1/evaluate" || url.pathname === "/v1/ask")
      ) {
        if (inFlight >= 2) throw new HttpError(429, "Too many evaluations are running");
        const { cursor, question } = askRequestFrom(await readJson(request));
        inFlight += 1;
        try {
          const result = (await options.ask(question, cursor)) as { response?: unknown };
          writeJson(response, 200, url.pathname === "/v1/ask" ? result.response : result);
        } finally {
          inFlight -= 1;
        }
        return;
      }
      writeJson(response, 404, { error: "Not found" });
    } catch (error) {
      const status =
        error instanceof HttpError ? error.status : error instanceof ContinuationCursorError ? 400 : 502;
      const message = error instanceof Error ? error.message : "Evaluation failed";
      if (
        !(error instanceof HttpError) &&
        !(error instanceof AskEvaluationError) &&
        !(error instanceof ContinuationCursorError)
      ) {
        console.error(error);
      }
      writeJson(
        response,
        status,
        error instanceof AskEvaluationError
          ? { error: message, ...error.evaluation }
          : { error: message },
      );
    }
  };
}
