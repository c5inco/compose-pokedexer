import { createHash } from "node:crypto";
import { readFileSync } from "node:fs";
import { resolve } from "node:path";

import { buildClientSchema, type IntrospectionQuery } from "graphql";

import { createSchemaLookup } from "../src/schema-lookup.js";

const schemaSha256 = "93ea4e5e0131cd56202fc268723792e720f9279f2a8b5f5d70d5b864b4e97015";
const cases = [
  {
    legacyCharacters: 11_444,
    name: "pokemon move relations",
    terms: ["pokemonmove", "versiongroup", "learnmethod"],
  },
  {
    legacyCharacters: 19_874,
    name: "species evolution",
    terms: ["pokemonspecies", "evolutionchain", "generation"],
  },
  {
    legacyCharacters: 12_991,
    name: "localized flavor text",
    terms: ["flavortext", "language", "effect"],
  },
  {
    legacyCharacters: 10_159,
    name: "items and held items",
    terms: ["item", "helditem", "pokemonitem"],
  },
  {
    legacyCharacters: 17_396,
    name: "ability effects",
    terms: ["ability", "effecttext", "language"],
  },
] as const;

const schemaPath = resolve(
  import.meta.dirname,
  "../../shared/src/commonMain/graphql/des.c5inco.pokedexer.shared/schema.json",
);
const schemaJson = readFileSync(schemaPath, "utf8");
const actualSha256 = createHash("sha256").update(schemaJson).digest("hex");
if (actualSha256 !== schemaSha256) {
  throw new Error("Schema changed; recapture the legacy character baselines before comparing");
}

const introspection = JSON.parse(schemaJson) as { data: IntrospectionQuery };
const lookup = createSchemaLookup(buildClientSchema(introspection.data));
let legacyTotal = 0;
let optimizedTotal = 0;
let typeDiscoveryTotal = 0;

for (const benchmark of cases) {
  const fieldResult = await lookup({
    detail: "fields",
    field_limit: 8,
    limit: 4,
    terms: [...benchmark.terms],
  });
  const typeResult = await lookup({
    detail: "types",
    field_limit: 1,
    limit: 8,
    terms: [...benchmark.terms],
  });
  const optimizedCharacters = JSON.stringify(fieldResult).length;
  const typeDiscoveryCharacters = JSON.stringify(typeResult).length;
  const reduction = 1 - optimizedCharacters / benchmark.legacyCharacters;
  legacyTotal += benchmark.legacyCharacters;
  optimizedTotal += optimizedCharacters;
  typeDiscoveryTotal += typeDiscoveryCharacters;
  console.log(
    `${benchmark.name}: ${benchmark.legacyCharacters} -> ${optimizedCharacters} chars (${(
      reduction * 100
    ).toFixed(1)}% reduction); types-only ${typeDiscoveryCharacters} chars`,
  );
}

const totalReduction = 1 - optimizedTotal / legacyTotal;
console.log(`total: ${legacyTotal} -> ${optimizedTotal} chars (${(totalReduction * 100).toFixed(1)}%)`);
console.log(
  `rough payload tokens at 4 chars/token: ${Math.ceil(legacyTotal / 4)} -> ${Math.ceil(
    optimizedTotal / 4,
  )}`,
);
console.log(
  `types-only total: ${typeDiscoveryTotal} chars (~${Math.ceil(typeDiscoveryTotal / 4)} tokens)`,
);
