import assert from "node:assert/strict";
import test from "node:test";

import { buildSchema } from "graphql";

import { createSchemaLookup } from "../src/schema-lookup.js";

test("returns bounded compact summaries ranked by type and field names", async () => {
  const schema = buildSchema(`
    type Move { id: Int!, name: String! }
    type PokemonMove { move_id: Int!, move: Move! }
    type Pokemon { id: Int!, pokemonmoves(limit: Int): [PokemonMove!]! }
    type Query { pokemon(limit: Int): [Pokemon!]!, move(limit: Int): [Move!]! }
  `);
  const lookup = createSchemaLookup(schema);

  const result = await lookup({ detail: "fields", field_limit: 2, limit: 2, terms: ["pokemonmove"] });

  assert.equal(result.matches.length, 2);
  assert.equal(result.matches[0].name, "PokemonMove");
  assert.ok(result.matches[0].fields?.some((field) => field.name === "move_id"));
  assert.ok(result.matches.every((match) => match.fields && match.fields.length <= 2));
});

test("finds and prioritizes matching fields beyond the legacy 30-field slice", async () => {
  const fields = Array.from({ length: 35 }, (_, index) => `field${index + 1}: String`).join("\n");
  const schema = buildSchema(`
    type WideType {
      ${fields}
      long_tail_relation(limit: Int): [String!]!
    }
    type Query { wide(limit: Int): [WideType!]! }
  `);
  const lookup = createSchemaLookup(schema);

  const result = await lookup({
    detail: "fields",
    field_limit: 3,
    limit: 1,
    terms: ["long tail relation"],
  });

  assert.equal(result.matches[0].name, "WideType");
  assert.equal(result.matches[0].fields?.[0].name, "long_tail_relation");
  assert.equal(result.matches[0].fields?.length, 3);
});

test("supports cheap type discovery without returning field payloads", async () => {
  const schema = buildSchema(`
    type Move { id: Int!, name: String! }
    type Query { move(limit: Int): [Move!]! }
  `);
  const lookup = createSchemaLookup(schema);

  const result = await lookup({ detail: "types", field_limit: 1, limit: 2, terms: ["move"] });

  assert.ok(result.matches.length > 0);
  assert.ok(result.matches.every((match) => !("fields" in match)));
});
