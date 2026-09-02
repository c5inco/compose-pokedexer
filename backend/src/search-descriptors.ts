export const canonicalPokemonShapes = [
  "ball",
  "squiggle",
  "fish",
  "arms",
  "blob",
  "upright",
  "legs",
  "quadruped",
  "wings",
  "tentacles",
  "heads",
  "humanoid",
  "bug-wings",
  "armor",
] as const;

const colors = ["black", "blue", "brown", "gray", "green", "pink", "purple", "red", "white", "yellow"] as const;
const types = [
  "normal",
  "fire",
  "water",
  "electric",
  "grass",
  "ice",
  "fighting",
  "poison",
  "ground",
  "flying",
  "psychic",
  "bug",
  "rock",
  "ghost",
  "dragon",
  "dark",
  "steel",
  "fairy",
] as const;

export type SearchConstraint =
  | { field: "color" | "shape" | "type"; operator: "eq"; value: string }
  | {
      field: "height" | "weight";
      operator: "gte" | "lte";
      unit: "decimetres" | "hectograms";
      value: number;
    };

interface Interpretation {
  disclosure: string;
  field: SearchConstraint["field"];
  term: string;
}

export interface SearchInterpretation {
  ambiguous_terms: Array<{ reason: string; term: string }>;
  conflicts: Array<{ field: SearchConstraint["field"]; terms: string[] }>;
  constraints: SearchConstraint[];
  interpretations: Interpretation[];
  status: "needs_clarification" | "requires_enrichment" | "structured";
  unsupported_terms: Array<{ reason: string; term: string }>;
}

const shapeAliases: Record<string, (typeof canonicalPokemonShapes)[number]> = { round: "ball" };
const physical: Record<string, { constraint: SearchConstraint; disclosure: string }> = {
  heavy: {
    constraint: { field: "weight", operator: "gte", unit: "hectograms", value: 1_000 },
    disclosure: "heavy means weight at least 1,000 hectograms (100 kilograms)",
  },
  light: {
    constraint: { field: "weight", operator: "lte", unit: "hectograms", value: 100 },
    disclosure: "light means weight at most 100 hectograms (10 kilograms)",
  },
  short: {
    constraint: { field: "height", operator: "lte", unit: "decimetres", value: 10 },
    disclosure: "short means height at most 10 decimetres (1 metre)",
  },
  tall: {
    constraint: { field: "height", operator: "gte", unit: "decimetres", value: 20 },
    disclosure: "tall means height at least 20 decimetres (2 metres)",
  },
};
const ambiguous: Record<string, string> = {
  big: "Specify whether big refers to height, weight, or both, with a threshold.",
  large: "Specify whether large refers to height, weight, or both, with a threshold.",
  little: "Specify whether little refers to height, weight, or both, with a threshold.",
  small: "Specify whether small refers to height, weight, or both, with a threshold.",
};
const subjective = new Set(["cute", "fluffy", "sleepy-looking"]);
const detectableTerms = [
  ...canonicalPokemonShapes,
  ...colors,
  ...types,
  ...Object.keys(shapeAliases),
  ...Object.keys(physical),
  ...Object.keys(ambiguous),
  ...subjective,
];

export function interpretSearchDescriptors(rawTerms: string[]): SearchInterpretation {
  const terms = rawTerms.map((term) => term.trim().toLocaleLowerCase()).filter(Boolean);
  const constraints: SearchConstraint[] = [];
  const interpretations: Interpretation[] = [];
  const ambiguousTerms: SearchInterpretation["ambiguous_terms"] = [];
  const unsupportedTerms: SearchInterpretation["unsupported_terms"] = [];

  for (const term of terms) {
    const shape = shapeAliases[term] ??
      (canonicalPokemonShapes.includes(term as (typeof canonicalPokemonShapes)[number]) ? term : undefined);
    let constraint: SearchConstraint | undefined;
    let disclosure: string | undefined;
    if (shape) {
      constraint = { field: "shape", operator: "eq", value: shape };
      disclosure = term === shape ? `${term} uses PokéAPI shape ${shape}` : `${term} maps to PokéAPI shape ${shape}`;
    } else if (colors.includes(term as (typeof colors)[number])) {
      constraint = { field: "color", operator: "eq", value: term };
      disclosure = `${term} uses PokéAPI species color ${term}`;
    } else if (types.includes(term as (typeof types)[number])) {
      constraint = { field: "type", operator: "eq", value: term };
      disclosure = `${term} uses PokéAPI type ${term}`;
    } else if (physical[term]) {
      ({ constraint, disclosure } = physical[term]);
    } else if (ambiguous[term]) {
      ambiguousTerms.push({ reason: ambiguous[term], term });
    } else if (subjective.has(term)) {
      unsupportedTerms.push({
        reason: `${term} is subjective and requires semantic or visual enrichment`,
        term,
      });
    } else {
      unsupportedTerms.push({
        reason: `${term} has no reviewed structured mapping and requires semantic or visual enrichment`,
        term,
      });
    }
    if (constraint && disclosure) {
      constraints.push(constraint);
      interpretations.push({ disclosure, field: constraint.field, term });
    }
  }

  const conflicts = (["shape", "color", "height", "weight"] as const).flatMap((field) => {
    const matching = interpretations.filter((item) => item.field === field).map((item) => item.term);
    return matching.length > 1 ? [{ field, terms: matching }] : [];
  });
  const status =
    ambiguousTerms.length > 0 || conflicts.length > 0
      ? "needs_clarification"
      : unsupportedTerms.length > 0
        ? "requires_enrichment"
        : "structured";
  return {
    ambiguous_terms: ambiguousTerms,
    conflicts,
    constraints,
    interpretations,
    status,
    unsupported_terms: unsupportedTerms,
  };
}

export function interpretSearchQuestion(question: string): SearchInterpretation | null {
  const normalized = question
    .normalize("NFKD")
    .replaceAll(/[\u0300-\u036f]/g, "")
    .toLocaleLowerCase()
    .replaceAll(/[^a-z0-9-]+/g, " ");
  const clearSearchIntent =
    /\bpokemon\b/.test(normalized) &&
    (/^(?:find|list|search|show)\b/.test(normalized.trim()) ||
      /^are\s+there\s+any\b/.test(normalized.trim()) ||
      /^(?:what|which)\b.*\bpokemon\b.*\b(?:are|have|that|type|with|whose)\b/.test(
        normalized.trim(),
      ));
  if (!clearSearchIntent) return null;

  const matches = detectableTerms
    .map((term) => ({
      index: normalized.search(new RegExp(`\\b${term.replace("-", "[-\\s]")}\\b`)),
      term,
    }))
    .filter((match) => match.index >= 0)
    .filter(({ term }) => {
      if (!types.includes(term as (typeof types)[number])) return true;
      const escaped = term.replace("-", "[-\\s]");
      return new RegExp(`\\b${escaped}(?:[-\\s]+type|\\s+pokemon)\\b`).test(normalized);
    })
    .sort((left, right) => left.index - right.index);
  if (matches.length === 0) return null;

  const negated = matches.filter(({ term }) =>
    new RegExp(
      `(?:\\b(?:not|without|excluding|except|no|(?:isn|aren|wasn|weren)\\s+t)\\s+(?:[a-z-]+\\s+){0,2}${term.replace("-", "[-\\s]")}\\b|\\bnon[-\\s]${term.replace("-", "[-\\s]")}\\b)`,
    ).test(normalized),
  );
  const result = interpretSearchDescriptors(
    matches.filter((match) => !negated.includes(match)).map((match) => match.term),
  );
  if (negated.length > 0) {
    result.ambiguous_terms.push(
      ...negated.map(({ term }) => ({
        reason: `The negated descriptor '${term}' has no reviewed exclusion mapping. Rephrase with positive constraints.`,
        term,
      })),
    );
    result.status = "needs_clarification";
  }
  return result;
}
