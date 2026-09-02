import assert from "node:assert/strict";
import test from "node:test";

import {
  EXECUTION_PROFILE,
  PRODUCT_EXECUTION_PROFILE,
} from "../src/execution-profile.js";

test("keeps the frozen evaluation profile separate from the product deadline", () => {
  assert.equal(EXECUTION_PROFILE.model.timeout_ms, 45_000);
  assert.equal(EXECUTION_PROFILE.model.max_retries, 1);
  assert.equal(EXECUTION_PROFILE.version, "ask-pokedexer-eval-v3");

  assert.equal(PRODUCT_EXECUTION_PROFILE.request_timeout_ms, 30_000);
  assert.equal(PRODUCT_EXECUTION_PROFILE.model.timeout_ms, 30_000);
  assert.equal(PRODUCT_EXECUTION_PROFILE.model.max_retries, 0);
  assert.equal(PRODUCT_EXECUTION_PROFILE.version, "ask-pokedexer-api-v1");
});
