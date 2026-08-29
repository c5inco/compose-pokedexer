import assert from "node:assert/strict";
import test from "node:test";

import { buildSchema } from "graphql";

import { createReadonlyGraphqlExecutor } from "../src/readonly-graphql.js";

const schema = buildSchema(`
  type PokemonType { id: Int!, name: String! }
  type Pokemon { id: Int!, name: String!, types(limit: Int): [PokemonType!]! }
  type Query { pokemon(name: String, limit: Int): [Pokemon!]! }
  type Mutation { deletePokemon(id: Int!): Boolean! }
`);

function jsonResponse(payload: unknown): Response {
  return new Response(JSON.stringify(payload), {
    headers: { "content-type": "application/json" },
    status: 200,
  });
}

test("executes a bounded parameterized query against only the configured endpoint", async () => {
  const requests: Request[] = [];
  const executor = createReadonlyGraphqlExecutor({
    endpoint: "https://graphql.pokeapi.co/v1beta2",
    fetchImpl: async (request) => {
      requests.push(request);
      return jsonResponse({ data: { pokemon: [{ id: 1, name: "bulbasaur" }] } });
    },
    schema,
  });

  const result = await executor.execute({
    purpose: "Resolve Bulbasaur",
    query:
      "query Pokemon($name: String!, $limit: Int!) { pokemon(name: $name, limit: $limit) { id name types(limit: 10) { id name } } }",
    variables: { limit: 1, name: "bulbasaur" },
  });

  assert.equal(requests.length, 1);
  assert.equal(requests[0].url, "https://graphql.pokeapi.co/v1beta2");
  assert.deepEqual(JSON.parse(await requests[0].clone().text()), {
    query:
      "query Pokemon($name: String!, $limit: Int!) { pokemon(name: $name, limit: $limit) { id name types(limit: 10) { id name } } }",
    variables: { limit: 1, name: "bulbasaur" },
  });
  assert.deepEqual(result.entityIds.pokemon, [1]);
  assert.deepEqual(result.entityReferences?.pokemon, [{ id: 1, name: "bulbasaur" }]);
  assert.equal(result.trace.purpose, "Resolve Bulbasaur");
  assert.match(result.trace.document_sha256, /^[a-f0-9]{64}$/);
  assert.match(result.trace.response_sha256 ?? "", /^[a-f0-9]{64}$/);
});

test("normalizes exact canonical-name filters before validation, execution, and tracing", async () => {
  const requests: Request[] = [];
  const executor = createReadonlyGraphqlExecutor({
    endpoint: "https://graphql.pokeapi.co/v1beta2",
    fetchImpl: async (request) => {
      requests.push(request);
      return jsonResponse({ data: { pokemon: [{ id: 122, name: "mr-mime" }] } });
    },
    schema,
  });

  const result = await executor.execute({
    purpose: "Resolve a display name",
    query: "query Pokemon($name: String!, $limit: Int!) { pokemon(name: $name, limit: $limit) { id name } }",
    variables: { limit: 1, name: "Mr Mime" },
  });

  assert.deepEqual(JSON.parse(await requests[0].clone().text()), {
    query: "query Pokemon($name: String!, $limit: Int!) { pokemon(name: $name, limit: $limit) { id name } }",
    variables: { limit: 1, name: "mr-mime" },
  });
  assert.deepEqual(result.trace.variables, { limit: 1, name: "mr-mime" });
});

test("hashes normalized response data independently of object key order", async () => {
  const responses = [
    { data: { pokemon: [{ id: 1, name: "bulbasaur" }] } },
    { data: { pokemon: [{ name: "bulbasaur", id: 1 }] } },
  ];
  const executor = createReadonlyGraphqlExecutor({
    endpoint: "https://graphql.pokeapi.co/v1beta2",
    fetchImpl: async () => jsonResponse(responses.shift()),
    schema,
  });
  const request = {
    purpose: "Stable response hash",
    query: "query Pokemon($limit: Int!) { pokemon(limit: $limit) { id name } }",
    variables: { limit: 1 },
  };

  const first = await executor.execute(request);
  const second = await executor.execute(request);

  assert.equal(first.trace.response_sha256, second.trace.response_sha256);
});

test("rejects mutations, introspection, aliases, and unbounded lists before fetch", async (t) => {
  const executor = createReadonlyGraphqlExecutor({
    endpoint: "https://graphql.pokeapi.co/v1beta2",
    fetchImpl: async () => {
      throw new Error("fetch must not run");
    },
    schema,
  });

  const cases = [
    ["mutation", "mutation Delete { deletePokemon(id: 1) }", /Only query operations/],
    ["introspection", "query Schema { __schema { queryType { name } } }", /Introspection/],
    ["alias", "query Alias { result: pokemon(limit: 1) { id } }", /Aliases/],
    [
      "literal filter",
      'query Literal { pokemon(name: "bulbasaur", limit: 1) { id } }',
      /Scalar filter values must use GraphQL variables/,
    ],
    ["unbounded root", "query Unbounded { pokemon { id } }", /bounded limit/],
    [
      "unbounded relation",
      "query Relation { pokemon(limit: 1) { id types { id } } }",
      /bounded limit/,
    ],
  ] as const;

  for (const [name, query, expected] of cases) {
    await t.test(name, async () => {
      await assert.rejects(
        executor.execute({ purpose: name, query, variables: {} }),
        expected,
      );
    });
  }
});

test("rejects excessive list limits and query complexity", async () => {
  const executor = createReadonlyGraphqlExecutor({
    endpoint: "https://graphql.pokeapi.co/v1beta2",
    fetchImpl: async () => {
      throw new Error("fetch must not run");
    },
    schema,
  });

  await assert.rejects(
    executor.execute({
      purpose: "Too many rows",
      query: "query TooMany($limit: Int!) { pokemon(limit: $limit) { id } }",
      variables: { limit: 101 },
    }),
    /between 1 and 100/,
  );

  await assert.rejects(
    executor.execute({
      purpose: "Too expensive",
      query: "query Expensive { pokemon(limit: 100) { types(limit: 100) { id name } } }",
      variables: {},
    }),
    /complexity limit/,
  );
});
