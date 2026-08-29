import assert from "node:assert/strict";
import test from "node:test";

import {
  createEvaluationGraphqlControls,
  type ConsistencyProbe,
} from "../evaluation/graphql-controls.js";
import {
  GraphqlInfrastructureError,
  type GraphqlExecution,
  type GraphqlRequest,
} from "../src/readonly-graphql.js";

const request: GraphqlRequest = {
  purpose: "Resolve Bulbasaur",
  query: "query Pokemon($limit: Int!) { pokemon(limit: $limit) { id name } }",
  variables: { limit: 1 },
};

function execution(purpose = request.purpose): GraphqlExecution {
  return {
    data: { pokemon: [{ id: 1, name: "bulbasaur" }] },
    entityIds: { ability: [], item: [], move: [], pokemon: [1] },
    entityReferences: {
      ability: [],
      item: [],
      move: [],
      pokemon: [{ id: 1, name: "bulbasaur" }],
    },
    trace: {
      document_sha256: "a".repeat(64),
      duration_ms: 4,
      purpose,
      query: request.query,
      response_sha256: "b".repeat(64),
      variables: request.variables,
    },
  };
}

test("retries one transient PokéAPI failure and caches successful identical requests", async () => {
  let calls = 0;
  const controls = createEvaluationGraphqlControls({
    async execute() {
      calls += 1;
      if (calls === 1) throw new GraphqlInfrastructureError("PokéAPI returned HTTP 503");
      return execution();
    },
  });

  const first = await controls.execute(request);
  const second = await controls.execute({ ...request, purpose: "Same data, another model" });

  assert.equal(calls, 2);
  assert.equal(first.trace.infrastructure_attempts, 2);
  assert.equal(first.trace.cache_hit, false);
  assert.equal(second.trace.cache_hit, true);
  assert.equal(second.trace.purpose, "Same data, another model");
  assert.equal(second.trace.response_sha256, first.trace.response_sha256);
});

test("compares fixed consistency probes by response hash", async () => {
  const probes: ConsistencyProbe[] = [
    { id: "core-entities", request },
    { id: "relations", request: { ...request, purpose: "Probe relations" } },
  ];
  const controls = createEvaluationGraphqlControls({ execute: async (input) => execution(input.purpose) });

  const before = await controls.runConsistencyProbes(probes);
  const after = await controls.runConsistencyProbes(probes);

  assert.deepEqual(after, before);
  assert.match(controls.consistencySha256(before), /^[a-f0-9]{64}$/);
});
