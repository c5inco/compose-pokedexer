import assert from "node:assert/strict";
import test from "node:test";

import {
  RequestLimitError,
  createRequestLimiter,
  resolveRequestLimitConfig,
} from "../src/request-limiter.js";

test("bounds concurrent work and fixed-window request volume", () => {
  let now = 1_000;
  const limiter = createRequestLimiter({
    maxConcurrent: 1,
    maxRequests: 2,
    now: () => now,
    windowMs: 10_000,
  });

  const releaseFirst = limiter.acquire();
  assert.throws(
    () => limiter.acquire(),
    (error: unknown) => {
      assert.ok(error instanceof RequestLimitError);
      assert.equal(error.retryAfterSeconds, 1);
      return true;
    },
  );
  releaseFirst();

  const releaseSecond = limiter.acquire();
  releaseSecond();
  assert.throws(
    () => limiter.acquire(),
    (error: unknown) => {
      assert.ok(error instanceof RequestLimitError);
      assert.equal(error.retryAfterSeconds, 10);
      return true;
    },
  );

  now += 10_000;
  assert.doesNotThrow(() => limiter.acquire()());
});

test("releases concurrency exactly once", () => {
  const limiter = createRequestLimiter({
    maxConcurrent: 1,
    maxRequests: 10,
    windowMs: 60_000,
  });

  const release = limiter.acquire();
  release();
  release();

  assert.doesNotThrow(() => limiter.acquire()());
});

test("resolves bounded production request-limit configuration", () => {
  assert.deepEqual(resolveRequestLimitConfig({}), {
    maxConcurrent: 4,
    maxRequests: 30,
    windowMs: 60_000,
  });
  assert.deepEqual(
    resolveRequestLimitConfig({
      ASK_MAX_CONCURRENT_REQUESTS: "8",
      ASK_RATE_LIMIT_REQUESTS: "120",
      ASK_RATE_LIMIT_WINDOW_SECONDS: "300",
    }),
    { maxConcurrent: 8, maxRequests: 120, windowMs: 300_000 },
  );
  assert.throws(
    () => resolveRequestLimitConfig({ ASK_MAX_CONCURRENT_REQUESTS: "0" }),
    /ASK_MAX_CONCURRENT_REQUESTS/,
  );
  assert.throws(
    () => resolveRequestLimitConfig({ ASK_RATE_LIMIT_REQUESTS: "many" }),
    /ASK_RATE_LIMIT_REQUESTS/,
  );
  assert.throws(
    () => resolveRequestLimitConfig({ ASK_RATE_LIMIT_WINDOW_SECONDS: "3601" }),
    /ASK_RATE_LIMIT_WINDOW_SECONDS/,
  );
});
