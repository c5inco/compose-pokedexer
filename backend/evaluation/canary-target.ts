import { readFileSync } from "node:fs";
import { resolve } from "node:path";

import {
  buildClientSchema,
  graphql,
  type GraphQLSchema,
  type IntrospectionQuery,
} from "graphql";

import { EVALUATION_PROFILE } from "./profile.js";
import { createReadonlyGraphqlExecutor } from "../src/readonly-graphql.js";
import { createSchemaLookup } from "../src/schema-lookup.js";

const schemaSource = readFileSync(
  resolve(
    import.meta.dirname,
    "../../shared/src/commonMain/graphql/des.c5inco.pokedexer.shared/schema.json",
  ),
  "utf8",
);
const schema = buildClientSchema(
  (JSON.parse(schemaSource) as { data: IntrospectionQuery }).data,
);

const language = { id: 9, iso639: "en", name: "en" };
const abilities = [
  {
    effect: "Restores one quarter of maximum HP whenever the creature leaves battle.",
    id: 92001,
    name: "echo-mend",
  },
  {
    effect: "Cuts damage from the first super-effective hit in half.",
    id: 92002,
    name: "tide-shield",
  },
];

const pokemon = [
  {
    abilities: [abilities[0]],
    id: 90001,
    name: "cobalt-sprig",
    stats: [
      { base_stat: 71, stat: { id: 1, name: "hp" } },
      { base_stat: 73, stat: { id: 6, name: "speed" } },
    ],
    types: [
      { id: 11, name: "water" },
      { id: 12, name: "grass" },
    ],
  },
  {
    abilities: [abilities[1]],
    id: 90002,
    name: "ember-fin",
    stats: [
      { base_stat: 62, stat: { id: 1, name: "hp" } },
      { base_stat: 91, stat: { id: 6, name: "speed" } },
    ],
    types: [
      { id: 10, name: "fire" },
      { id: 11, name: "water" },
    ],
  },
  {
    abilities: [abilities[0]],
    id: 90003,
    name: "quartz-wing",
    stats: [
      { base_stat: 67, stat: { id: 1, name: "hp" } },
      { base_stat: 105, stat: { id: 6, name: "speed" } },
    ],
    types: [
      { id: 6, name: "rock" },
      { id: 3, name: "flying" },
    ],
  },
];

const moves = [
  {
    accuracy: 91,
    id: 91001,
    name: "aurora-pulse",
    power: 73,
    type: { id: 15, name: "ice" },
  },
  {
    accuracy: 88,
    id: 91002,
    name: "moss-comet",
    power: 82,
    type: { id: 12, name: "grass" },
  },
];

interface Comparison {
  _eq?: unknown;
  _ilike?: string;
  _in?: unknown[];
  _regex?: string;
}

interface Where {
  _and?: Where[];
  _not?: Where;
  _or?: Where[];
  id?: Comparison;
  name?: Comparison;
  [field: string]: Comparison | Where | Where[] | undefined;
}

interface SelectionArguments {
  limit: number;
  offset?: number;
  order_by?: Array<Record<string, string>> | Record<string, string>;
  where?: Where;
}

function likePattern(pattern: string): RegExp {
  const source = pattern
    .replaceAll(/[.*+?^${}()|[\]\\]/g, "\\$&")
    .replaceAll("%", ".*")
    .replaceAll("_", ".");
  return new RegExp(`^${source}$`, "i");
}

function comparisonMatches(value: number | string, comparison: Comparison | undefined): boolean {
  if (!comparison) return true;
  if (comparison._eq !== undefined && value !== comparison._eq) return false;
  if (comparison._in && !comparison._in.includes(value)) return false;
  if (comparison._ilike && !likePattern(comparison._ilike).test(String(value))) return false;
  if (comparison._regex && !new RegExp(comparison._regex).test(String(value))) return false;
  return true;
}

function whereMatches(value: { id: number; name: string }, where: Where | undefined): boolean {
  if (!where) return true;
  if (where._and && !where._and.every((candidate) => whereMatches(value, candidate))) return false;
  if (where._or && !where._or.some((candidate) => whereMatches(value, candidate))) return false;
  if (where._not && whereMatches(value, where._not)) return false;
  return comparisonMatches(value.id, where.id) && comparisonMatches(value.name, where.name);
}

function select<T extends { id: number; name: string }>(
  values: T[],
  arguments_: SelectionArguments,
): T[] {
  const order = Array.isArray(arguments_.order_by)
    ? arguments_.order_by[0]
    : arguments_.order_by;
  const ordered = [...values].filter((value) => whereMatches(value, arguments_.where));
  const orderEntry = order ? Object.entries(order)[0] : undefined;
  if (orderEntry) {
    const [field, direction] = orderEntry;
    if (field === "id" || field === "name") {
      ordered.sort((left, right) => {
        const comparison = left[field] < right[field] ? -1 : left[field] > right[field] ? 1 : 0;
        return direction.startsWith("desc") ? -comparison : comparison;
      });
    }
  }
  const offset = arguments_.offset ?? 0;
  return ordered.slice(offset, offset + arguments_.limit);
}

function relationWhereMatches(value: Record<string, unknown>, where: Where | undefined): boolean {
  if (!where) return true;
  if (where._and && !where._and.every((candidate) => relationWhereMatches(value, candidate))) return false;
  if (where._or && !where._or.some((candidate) => relationWhereMatches(value, candidate))) return false;
  if (where._not && relationWhereMatches(value, where._not)) return false;
  return Object.entries(where).every(([field, condition]) => {
    if (field.startsWith("_")) return true;
    const actual = value[field];
    if (actual && typeof actual === "object" && condition && typeof condition === "object") {
      return relationWhereMatches(actual as Record<string, unknown>, condition as Where);
    }
    return comparisonMatches(actual as number | string, condition as Comparison | undefined);
  });
}

function selectRelations<T extends Record<string, unknown>>(values: T[], arguments_: SelectionArguments): T[] {
  const order = Array.isArray(arguments_.order_by) ? arguments_.order_by[0] : arguments_.order_by;
  const selected = values.filter((value) => relationWhereMatches(value, arguments_.where));
  const orderEntry = order ? Object.entries(order)[0] : undefined;
  if (orderEntry) {
    const [field, direction] = orderEntry;
    selected.sort((left, right) => {
      const a = left[field] as number | string;
      const b = right[field] as number | string;
      const comparison = a < b ? -1 : a > b ? 1 : 0;
      return direction.startsWith("desc") ? -comparison : comparison;
    });
  }
  const offset = arguments_.offset ?? 0;
  return selected.slice(offset, offset + arguments_.limit);
}

function pokemonValue(value: (typeof pokemon)[number]) {
  return {
    id: value.id,
    is_default: true,
    name: value.name,
    pokemonabilities: (arguments_: SelectionArguments) =>
      selectRelations(value.abilities.map((ability, index) => ({
        ability,
        ability_id: ability.id,
        id: value.id * 10 + index,
        pokemon_id: value.id,
      })), arguments_),
    pokemonstats: (arguments_: SelectionArguments) =>
      selectRelations(value.stats.map((stat, index) => ({
        ...stat,
        id: value.id * 10 + index,
        pokemon_id: value.id,
        stat_id: stat.stat.id,
      })), arguments_),
    pokemontypes: (arguments_: SelectionArguments) =>
      selectRelations(value.types.map((type, index) => ({
        id: value.id * 10 + index,
        pokemon_id: value.id,
        slot: index + 1,
        type,
        type_id: type.id,
      })), arguments_),
  };
}

function speciesValue(value: (typeof pokemon)[number]) {
  return {
    id: value.id,
    name: value.name,
    pokemons: (arguments_: SelectionArguments) =>
      select([value], arguments_).map(pokemonValue),
  };
}

function abilityValue(value: (typeof abilities)[number]) {
  const displayName = value.name
    .split("-")
    .map((part) => `${part[0].toUpperCase()}${part.slice(1)}`)
    .join(" ");
  const effectText = {
    ability_id: value.id,
    effect: value.effect,
    id: value.id,
    language,
    language_id: language.id,
    name: value.name,
    short_effect: value.effect,
  };
  const flavorText = {
    ability_id: value.id,
    flavor_text: value.effect,
    id: value.id,
    language,
    language_id: language.id,
    name: value.name,
    version_group_id: 99_001,
    versiongroup: { id: 99_001, name: "canary-version", order: 1 },
  };
  const nameText = {
    ability_id: value.id,
    id: value.id,
    language,
    language_id: language.id,
    name: displayName,
  };
  return {
    abilityeffecttexts: ({ limit }: { limit: number }) => [effectText].slice(0, limit),
    abilityflavortexts: ({ limit }: { limit: number }) => [flavorText].slice(0, limit),
    abilitynames: ({ limit }: { limit: number }) => [nameText].slice(0, limit),
    effectText,
    flavorText,
    id: value.id,
    is_main_series: true,
    name: value.name,
    nameText,
  };
}

function rootValue() {
  const types = [...new Map(pokemon.flatMap((value) => value.types).map((type) => [type.id, type])).values()];
  const stats = [...new Map(pokemon.flatMap((value) => value.stats).map(({ stat }) => [stat.id, stat])).values()];
  const abilityValues = abilities.map(abilityValue);
  const speciesNames = pokemon.map((value) => ({
    genus: "Canary creature",
    id: value.id,
    language,
    language_id: language.id,
    name: value.name
      .split("-")
      .map((part) => `${part[0].toUpperCase()}${part.slice(1)}`)
      .join(" "),
    pokemon_species_id: value.id,
    pokemonspecy: speciesValue(value),
  }));
  return {
    ability: (arguments_: SelectionArguments) =>
      select(abilities, arguments_).map(abilityValue),
    abilityeffecttext: (arguments_: SelectionArguments) =>
      select(abilityValues.map((value) => value.effectText), arguments_),
    abilityflavortext: (arguments_: SelectionArguments) =>
      select(abilityValues.map((value) => value.flavorText), arguments_),
    abilityname: (arguments_: SelectionArguments) =>
      select(abilityValues.map((value) => value.nameText), arguments_),
    item: () => [],
    language: (arguments_: SelectionArguments) => select([language], arguments_),
    move: (arguments_: SelectionArguments) => select(moves, arguments_),
    pokemon: (arguments_: SelectionArguments) => select(pokemon, arguments_).map(pokemonValue),
    pokemonspecies: (arguments_: SelectionArguments) =>
      select(pokemon, arguments_).map(speciesValue),
    pokemonspeciesname: (arguments_: SelectionArguments) =>
      select(speciesNames, arguments_),
    stat: (arguments_: SelectionArguments) => select(stats, arguments_),
    type: (arguments_: SelectionArguments) => select(types, arguments_),
    versiongroup: (arguments_: SelectionArguments) =>
      select([{ id: 99_001, name: "canary-version", order: 1 }], arguments_),
  };
}

function localFetch(targetSchema: GraphQLSchema) {
  return async (request: Request): Promise<Response> => {
    const body = (await request.json()) as { query: string; variables?: Record<string, unknown> };
    const result = await graphql({
      rootValue: rootValue(),
      schema: targetSchema,
      source: body.query,
      variableValues: body.variables,
    });
    return new Response(JSON.stringify(result), {
      headers: { "content-type": "application/json" },
      status: 200,
    });
  };
}

export function createCanaryTarget() {
  const executor = createReadonlyGraphqlExecutor({
    endpoint: "http://canary.invalid/graphql",
    fetchImpl: localFetch(schema),
    maxComplexity: EVALUATION_PROFILE.graphql.max_complexity,
    maxDepth: EVALUATION_PROFILE.graphql.max_depth,
    maxResponseBytes: EVALUATION_PROFILE.graphql.max_response_bytes,
    maxRows: EVALUATION_PROFILE.graphql.max_rows,
    schema,
    timeoutMs: EVALUATION_PROFILE.graphql.timeout_ms,
  });
  return {
    execute: executor.execute,
    lookup: createSchemaLookup(schema),
    schema,
    schemaSource,
  };
}
