interface RequestLimitOptions {
  maxConcurrent: number;
  maxRequests: number;
  now?: () => number;
  windowMs: number;
}

export interface RequestLimiter {
  acquire(): () => void;
}

export class RequestLimitError extends Error {
  constructor(readonly retryAfterSeconds: number) {
    super("Request limit exceeded");
  }
}

function positiveInteger(environment: Record<string, string | undefined>, name: string, fallback: number) {
  const value = Number(environment[name] ?? fallback);
  if (!Number.isInteger(value) || value < 1) throw new Error(`${name} must be a positive integer`);
  return value;
}

export function resolveRequestLimitConfig(environment: Record<string, string | undefined>) {
  const maxConcurrent = positiveInteger(environment, "ASK_MAX_CONCURRENT_REQUESTS", 4);
  const maxRequests = positiveInteger(environment, "ASK_RATE_LIMIT_REQUESTS", 30);
  const windowSeconds = positiveInteger(environment, "ASK_RATE_LIMIT_WINDOW_SECONDS", 60);
  if (maxConcurrent > 100) throw new Error("ASK_MAX_CONCURRENT_REQUESTS must not exceed 100");
  if (maxRequests > 10_000) throw new Error("ASK_RATE_LIMIT_REQUESTS must not exceed 10000");
  if (windowSeconds > 3_600) throw new Error("ASK_RATE_LIMIT_WINDOW_SECONDS must not exceed 3600");
  return { maxConcurrent, maxRequests, windowMs: windowSeconds * 1_000 };
}

export function createRequestLimiter(options: RequestLimitOptions): RequestLimiter {
  if (!Number.isInteger(options.maxConcurrent) || options.maxConcurrent < 1) {
    throw new Error("maxConcurrent must be a positive integer");
  }
  if (!Number.isInteger(options.maxRequests) || options.maxRequests < 1) {
    throw new Error("maxRequests must be a positive integer");
  }
  if (!Number.isInteger(options.windowMs) || options.windowMs < 1) {
    throw new Error("windowMs must be a positive integer");
  }
  const now = options.now ?? Date.now;
  let inFlight = 0;
  let requestCount = 0;
  let windowStarted = now();

  return {
    acquire() {
      const current = now();
      if (current >= windowStarted + options.windowMs) {
        requestCount = 0;
        windowStarted = current;
      }
      if (inFlight >= options.maxConcurrent) throw new RequestLimitError(1);
      if (requestCount >= options.maxRequests) {
        const remainingMs = Math.max(1, windowStarted + options.windowMs - current);
        throw new RequestLimitError(Math.ceil(remainingMs / 1_000));
      }
      inFlight += 1;
      requestCount += 1;
      let released = false;
      return () => {
        if (released) return;
        released = true;
        inFlight -= 1;
      };
    },
  };
}
