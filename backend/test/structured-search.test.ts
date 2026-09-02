import assert from "node:assert/strict";
import test from "node:test";

import type { SearchInterpretation } from "../src/search-descriptors.js";
import { compileStructuredSearch } from "../src/structured-search.js";

const structuredInterpretation: SearchInterpretation = {
  ambiguous_terms: [],
  conflicts: [],
  constraints: [
    { field: "color", operator: "eq", value: "blue" },
    { field: "shape", operator: "eq", value: "ball" },
    { field: "type", operator: "eq", value: "water" },
    { field: "height", operator: "lte", unit: "decimetres", value: 10 },
    { field: "height", operator: "gte", unit: "decimetres", value: 2 },
    { field: "weight", operator: "lte", unit: "hectograms", value: 100 },
    { field: "weight", operator: "gte", unit: "hectograms", value: 5 },
  ],
  interpretations: [],
  status: "structured",
  unsupported_terms: [],
};

test("compiles every reviewed search constraint into one bounded canonical query", () => {
  const request = compileStructuredSearch(structuredInterpretation);

  assert.ok(request);
  assert.equal(request.purpose, "Find Pokémon matching reviewed structured search constraints");
  assert.match(request.query, /query StructuredPokemonSearch\(/);
  assert.match(request.query, /pokemon\(where: \$where, order_by: \{ id: asc \}, limit: \$limit\)/);
  assert.match(request.query, /id\s+name\s+height\s+weight/);
  assert.deepEqual(request.variables, {
    limit: 100,
    where: {
      _and: [
        { is_default: { _eq: true } },
        { pokemonspecy: { pokemoncolor: { name: { _eq: "blue" } } } },
        { pokemonspecy: { pokemonshape: { name: { _eq: "ball" } } } },
        { pokemontypes: { type: { name: { _eq: "water" } } } },
        { height: { _lte: 10 } },
        { height: { _gte: 2 } },
        { weight: { _lte: 100 } },
        { weight: { _gte: 5 } },
      ],
    },
  });
});

test("does not compile ambiguous or enrichment-dependent searches", () => {
  for (const status of ["needs_clarification", "requires_enrichment"] as const) {
    assert.equal(
      compileStructuredSearch({ ...structuredInterpretation, constraints: [], status }),
      null,
    );
  }
});
