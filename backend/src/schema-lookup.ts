import {
  isEnumType,
  isInputObjectType,
  isInterfaceType,
  isObjectType,
  type GraphQLArgument,
  type GraphQLField,
  type GraphQLInputField,
  type GraphQLNamedType,
  type GraphQLSchema,
} from "graphql";
import { z } from "zod";

import type { JsonObject } from "./readonly-graphql.js";

interface FieldSummary {
  args: Array<{ name: string; type: string }>;
  name: string;
  type: string;
}

interface TypeSummary {
  fields?: FieldSummary[];
  kind: string;
  name: string;
}

export interface SchemaLookupResult {
  matches: TypeSummary[];
}

const requestSchema = z.object({
  detail: z.enum(["types", "fields"]),
  field_limit: z.number().int().min(1).max(12),
  limit: z.number().int().min(1).max(8),
  terms: z.array(z.string().min(1).max(80)).min(1).max(6),
});

function normalize(value: string): string {
  return value.toLowerCase().replaceAll(/[^a-z0-9]/g, "");
}

function argumentsFor(
  field: GraphQLField<unknown, unknown> | GraphQLInputField,
): readonly GraphQLArgument[] {
  return "args" in field ? field.args : [];
}

function fieldsFor(type: GraphQLNamedType): FieldSummary[] {
  if (isObjectType(type) || isInterfaceType(type) || isInputObjectType(type)) {
    return Object.values(type.getFields())
      .map((field) => ({
        args: argumentsFor(field).map((argument) => ({
          name: argument.name,
          type: String(argument.type),
        })),
        name: field.name,
        type: String(field.type),
      }));
  }
  if (isEnumType(type)) {
    return type
      .getValues()
      .map((value) => ({ args: [], name: value.name, type: "enum value" }));
  }
  return [];
}

function fieldScore(field: FieldSummary, terms: string[]): number {
  const name = normalize(field.name);
  const type = normalize(field.type);
  const argumentNames = field.args.map((argument) => normalize(argument.name));
  const relevance = terms.reduce((total, rawTerm) => {
    const term = normalize(rawTerm);
    if (!term) return total;
    if (name === term) return total + 100;
    if (name.includes(term) || term.includes(name)) return total + 40;
    if (type.includes(term) || term.includes(type)) return total + 10;
    if (argumentNames.some((argument) => argument === term)) return total + 5;
    return total;
  }, 0);
  return relevance || (name === "id" || name === "name" ? 1 : 0);
}

function rankedFields(type: GraphQLNamedType, terms: string[], limit: number): FieldSummary[] {
  return fieldsFor(type)
    .map((field, index) => ({ field, index, score: fieldScore(field, terms) }))
    .sort((left, right) => right.score - left.score || left.index - right.index)
    .slice(0, limit)
    .map(({ field }) => field);
}

function score(type: GraphQLNamedType, terms: string[]): number {
  const name = normalize(type.name);
  const fields = fieldsFor(type).map((field) => normalize(field.name));
  return terms.reduce((total, rawTerm) => {
    const term = normalize(rawTerm);
    if (!term) return total;
    if (name === term) return total + 100;
    if (name.includes(term) || term.includes(name)) return total + 40;
    if (fields.some((field) => field === term)) return total + 20;
    if (fields.some((field) => field.includes(term) || term.includes(field))) return total + 5;
    return total;
  }, 0);
}

function kindFor(type: GraphQLNamedType): string {
  if (isObjectType(type)) return "object";
  if (isInterfaceType(type)) return "interface";
  if (isInputObjectType(type)) return "input_object";
  if (isEnumType(type)) return "enum";
  return "other";
}

export function createSchemaLookup(schema: GraphQLSchema) {
  const types = Object.values(schema.getTypeMap()).filter(
    (type) => !type.name.startsWith("__") && fieldsFor(type).length > 0,
  );

  return async (request: JsonObject): Promise<SchemaLookupResult> => {
    const parsed = requestSchema.parse(request);
    const matches = types
      .map((type) => ({ score: score(type, parsed.terms), type }))
      .filter((candidate) => candidate.score > 0)
      .sort(
        (left, right) =>
          right.score - left.score || left.type.name.localeCompare(right.type.name),
      )
      .slice(0, parsed.limit)
      .map(({ type }) => {
        const summary: TypeSummary = {
          kind: kindFor(type),
          name: type.name,
        };
        if (parsed.detail === "fields") {
          summary.fields = rankedFields(type, parsed.terms, parsed.field_limit);
        }
        return summary;
      });
    return { matches };
  };
}
