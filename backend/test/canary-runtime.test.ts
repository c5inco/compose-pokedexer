import assert from "node:assert/strict";
import { readFileSync } from "node:fs";
import { resolve } from "node:path";
import test from "node:test";

import { createCanaryTarget } from "../evaluation/canary-target.js";
import { loadSuite } from "../evaluation/suite.js";

test("serves fictional grounding records through the same guarded GraphQL contract", async () => {
  const target = createCanaryTarget();
  const result = await target.execute({
    purpose: "Look up the fictional canary creature",
    query: `query CanaryCreature($name: String!, $limit: Int!, $typeLimit: Int!, $statLimit: Int!) {
      pokemon(where: {name: {_eq: $name}}, limit: $limit) {
        id
        name
        pokemontypes(limit: $typeLimit) { type { name } }
        pokemonstats(limit: $statLimit) { base_stat stat { name } }
      }
    }`,
    variables: { limit: 1, name: "cobalt-sprig", statLimit: 6, typeLimit: 2 },
  });

  assert.deepEqual(result.entityIds.pokemon, [90001]);
  assert.deepEqual(result.data, {
    pokemon: [
      {
        id: 90001,
        name: "cobalt-sprig",
        pokemontypes: [{ type: { name: "water" } }, { type: { name: "grass" } }],
        pokemonstats: [
          { base_stat: 71, stat: { name: "hp" } },
          { base_stat: 73, stat: { name: "speed" } },
        ],
      },
    ],
  });
});

test("applies nested relation filters, ordering, offset, and limits", async () => {
  const result = await createCanaryTarget().execute({
    purpose: "Select nested canary records",
    query: `query NestedSelection($name: String!, $speed: String!, $ability: String!, $typeId: Int!, $limit: Int!, $offset: Int!, $order: order_by!) {
      pokemon(where: {name: {_eq: $name}}, limit: $limit) {
        pokemonstats(where: {stat: {name: {_eq: $speed}}}, order_by: {base_stat: $order}, offset: $offset, limit: $limit) { base_stat stat_id stat { name } }
        pokemonabilities(where: {ability: {name: {_eq: $ability}}}, limit: $limit) { ability_id ability { name } }
        pokemontypes(where: {type_id: {_eq: $typeId}}, limit: $limit) { type_id type { name } }
      }
    }`,
    variables: { ability: "echo-mend", limit: 1, name: "cobalt-sprig", offset: 0, order: "desc", speed: "speed", typeId: 12 },
  });

  assert.deepEqual(result.data, {
    pokemon: [{
      pokemonabilities: [{ ability: { name: "echo-mend" }, ability_id: 92001 }],
      pokemonstats: [{ base_stat: 73, stat: { name: "speed" }, stat_id: 6 }],
      pokemontypes: [{ type: { name: "grass" }, type_id: 12 }],
    }],
  });
});

test("ships a separate locked grounding-canary suite", () => {
  const suite = loadSuite(
    readFileSync(
      resolve(import.meta.dirname, "../evaluation/suites/grounding-canary-v1.json"),
      "utf8",
    ),
  );

  assert.equal(suite.kind, "canary");
  assert.equal(suite.cases.length, 6);
  assert.ok(suite.cases.every((item) => item.question.length > 10));
});

test("ships grounding canary v2 with fictional grounding markers", () => {
  const suite = loadSuite(readFileSync(resolve(import.meta.dirname, "../evaluation/suites/grounding-canary-v2.json"), "utf8"));
  assert.equal(suite.version, "grounding-canary-v2");
  assert.equal(suite.cases.filter((item) => item.expected.fictional_grounding).length, 5);
  assert.equal(suite.cases.find((item) => item.id === "canary-not-found")?.expected.fictional_grounding, undefined);
});

test("mirrors common live-schema search and species relation shapes", async () => {
  const target = createCanaryTarget();
  const result = await target.execute({
    purpose: "Search fictional canary creatures using representative filters",
    query: `query CanarySearch($names: [String!]!, $pattern: String!, $limit: Int!, $pokemonLimit: Int!) {
      pokemon(where: {name: {_in: $names}}, order_by: {id: asc}, limit: $limit) {
        id
        name
      }
      pokemonspecies(where: {name: {_ilike: $pattern}}, order_by: {id: asc}, limit: $limit) {
        id
        name
        pokemons(limit: $pokemonLimit) { id name }
      }
    }`,
    variables: {
      limit: 2,
      names: ["cobalt-sprig", "ember-fin"],
      pattern: "%cobalt%",
      pokemonLimit: 1,
    },
  });

  assert.deepEqual(result.data, {
    pokemon: [
      { id: 90001, name: "cobalt-sprig" },
      { id: 90002, name: "ember-fin" },
    ],
    pokemonspecies: [
      {
        id: 90001,
        name: "cobalt-sprig",
        pokemons: [{ id: 90001, name: "cobalt-sprig" }],
      },
    ],
  });
});

test("serves ability effects through the common live-schema relations", async () => {
  const target = createCanaryTarget();
  const result = await target.execute({
    purpose: "Look up the fictional canary ability effect",
    query: `query CanaryAbility($name: String!, $limit: Int!, $textLimit: Int!) {
      ability(where: {name: {_eq: $name}}, limit: $limit) {
        id
        name
        is_main_series
        abilitynames(limit: $textLimit) { name language { iso639 } }
        abilityeffecttexts(limit: $textLimit) { effect short_effect language { iso639 } }
        abilityflavortexts(limit: $textLimit) { flavor_text language { iso639 } }
      }
    }`,
    variables: { limit: 1, name: "echo-mend", textLimit: 1 },
  });

  assert.deepEqual(result.entityIds.ability, [92001]);
  assert.deepEqual(result.data, {
    ability: [
      {
        abilityeffecttexts: [
          {
            effect: "Restores one quarter of maximum HP whenever the creature leaves battle.",
            language: { iso639: "en" },
            short_effect:
              "Restores one quarter of maximum HP whenever the creature leaves battle.",
          },
        ],
        abilityflavortexts: [
          {
            flavor_text:
              "Restores one quarter of maximum HP whenever the creature leaves battle.",
            language: { iso639: "en" },
          },
        ],
        abilitynames: [{ language: { iso639: "en" }, name: "Echo Mend" }],
        id: 92001,
        is_main_series: true,
        name: "echo-mend",
      },
    ],
  });
});
