import type { GraphqlRequest, JsonObject } from "./readonly-graphql.js";
import type { SearchConstraint, SearchInterpretation } from "./search-descriptors.js";

const query = `query StructuredPokemonSearch($where: pokemon_bool_exp!, $limit: Int!) {
  pokemon(where: $where, order_by: { id: asc }, limit: $limit) {
    id
    name
    height
    weight
  }
}`;

function filterFor(constraint: SearchConstraint): JsonObject {
  if (constraint.field === "color") {
    return { pokemonspecy: { pokemoncolor: { name: { _eq: constraint.value } } } };
  }
  if (constraint.field === "shape") {
    return { pokemonspecy: { pokemonshape: { name: { _eq: constraint.value } } } };
  }
  if (constraint.field === "type") {
    return { pokemontypes: { type: { name: { _eq: constraint.value } } } };
  }
  return { [constraint.field]: { [`_${constraint.operator}`]: constraint.value } };
}

export function compileStructuredSearch(
  interpretation: SearchInterpretation,
): GraphqlRequest | null {
  if (interpretation.status !== "structured") return null;
  return {
    purpose: "Find Pokémon matching reviewed structured search constraints",
    query,
    variables: {
      limit: 100,
      where: {
        _and: [
          { is_default: { _eq: true } },
          ...interpretation.constraints.map(filterFor),
        ],
      },
    },
  };
}
