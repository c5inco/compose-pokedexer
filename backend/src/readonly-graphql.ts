import { createHash } from "node:crypto";

import {
  Kind,
  TypeInfo,
  getVariableValues,
  isListType,
  isNonNullType,
  parse,
  validate,
  visit,
  visitWithTypeInfo,
  type DocumentNode,
  type FieldNode,
  type GraphQLSchema,
  type GraphQLOutputType,
  type OperationDefinitionNode,
  type ValueNode,
} from "graphql";

export type JsonPrimitive = boolean | number | string | null;
export type JsonValue = JsonPrimitive | JsonValue[] | { [key: string]: JsonValue };
export type JsonObject = { [key: string]: JsonValue };

export interface EntityIds {
  ability: number[];
  item: number[];
  move: number[];
  pokemon: number[];
}

export interface EntityReference {
  id: number;
  name: string;
}

export type EntityReferences = Record<keyof EntityIds, EntityReference[]>;

export interface QueryTrace {
  cache_hit?: boolean;
  purpose: string;
  query: string;
  variables: JsonObject;
  document_sha256: string;
  duration_ms: number;
  infrastructure_attempts?: number;
  response_sha256?: string;
}

export interface GraphqlRequest {
  purpose: string;
  query: string;
  variables: JsonObject;
}

export interface GraphqlExecution {
  data: JsonObject;
  entityIds: EntityIds;
  entityReferences?: EntityReferences;
  trace: QueryTrace;
}

interface ExecutorOptions {
  endpoint: string;
  fetchImpl?: (request: Request) => Promise<Response>;
  maxComplexity?: number;
  maxDepth?: number;
  maxResponseBytes?: number;
  maxRows?: number;
  schema: GraphQLSchema;
  timeoutMs?: number;
}

export class GraphqlPolicyError extends Error {}
export class GraphqlInfrastructureError extends Error {}

const canonicalNameRoots = new Set([
  "ability",
  "item",
  "move",
  "pokemon",
  "pokemonspecies",
  "type",
  "versiongroup",
]);

export function canonicalizePokeApiName(value: string): string {
  return value
    .normalize("NFKD")
    .replaceAll(/[\u0300-\u036f]/g, "")
    .toLocaleLowerCase()
    .replaceAll("♀", "-f")
    .replaceAll("♂", "-m")
    .replaceAll(/[’']/g, "")
    .replaceAll(/[^a-z0-9]+/g, "-")
    .replaceAll(/^-+|-+$/g, "")
    .replaceAll(/-{2,}/g, "-");
}

function normalizeNameComparison(value: JsonValue): JsonValue {
  if (!value || typeof value !== "object" || Array.isArray(value)) return value;
  return Object.fromEntries(
    Object.entries(value).map(([operator, item]) => {
      if (operator === "_eq" && typeof item === "string") {
        return [operator, canonicalizePokeApiName(item)];
      }
      if (operator === "_in" && Array.isArray(item)) {
        return [
          operator,
          item.map((entry) =>
            typeof entry === "string" ? canonicalizePokeApiName(entry) : entry,
          ),
        ];
      }
      return [operator, item];
    }),
  ) as JsonObject;
}

function normalizeWhereNames(value: JsonValue): JsonValue {
  if (Array.isArray(value)) return value.map(normalizeWhereNames);
  if (!value || typeof value !== "object") return value;
  return Object.fromEntries(
    Object.entries(value).map(([key, item]) => {
      if (key === "name") return [key, normalizeNameComparison(item)];
      if (key.endsWith("names")) return [key, item];
      return [key, normalizeWhereNames(item)];
    }),
  ) as JsonObject;
}

function normalizeVariableValue(variables: JsonObject, name: string, mode: "name" | "where"): void {
  const value = variables[name];
  if (mode === "name" && typeof value === "string") {
    variables[name] = canonicalizePokeApiName(value);
  } else if (mode === "where" && value !== undefined) {
    variables[name] = normalizeWhereNames(value);
  }
}

function normalizeInlineNameVariables(node: ValueNode, variables: JsonObject): void {
  if (node.kind !== Kind.OBJECT) return;
  for (const field of node.fields) {
    if (field.name.value === "name" && field.value.kind === Kind.OBJECT) {
      for (const comparison of field.value.fields) {
        if (comparison.name.value === "_eq" && comparison.value.kind === Kind.VARIABLE) {
          normalizeVariableValue(variables, comparison.value.name.value, "name");
        }
        if (comparison.name.value === "_in" && comparison.value.kind === Kind.VARIABLE) {
          const value = variables[comparison.value.name.value];
          if (Array.isArray(value)) {
            variables[comparison.value.name.value] = value.map((entry) =>
              typeof entry === "string" ? canonicalizePokeApiName(entry) : entry,
            );
          }
        }
      }
      continue;
    }
    if (!field.name.value.endsWith("names")) {
      normalizeInlineNameVariables(field.value, variables);
    }
  }
}

function canonicalVariables(document: DocumentNode, source: JsonObject): JsonObject {
  const variables = structuredClone(source);
  const operation = operationFrom(document);
  for (const selection of operation.selectionSet.selections) {
    if (selection.kind !== Kind.FIELD || !canonicalNameRoots.has(selection.name.value)) continue;
    for (const argument of selection.arguments ?? []) {
      if (argument.name.value === "name" && argument.value.kind === Kind.VARIABLE) {
        normalizeVariableValue(variables, argument.value.name.value, "name");
      }
      if (argument.name.value === "where") {
        if (argument.value.kind === Kind.VARIABLE) {
          normalizeVariableValue(variables, argument.value.name.value, "where");
        } else {
          normalizeInlineNameVariables(argument.value, variables);
        }
      }
    }
  }
  return variables;
}

function outputContainsList(type: GraphQLOutputType): boolean {
  const nullable = isNonNullType(type) ? type.ofType : type;
  return isListType(nullable);
}

function readLimit(node: FieldNode, variables: JsonObject, maxRows: number): number {
  const argument = node.arguments?.find((item) => item.name.value === "limit");
  if (!argument) {
    throw new GraphqlPolicyError(`List field ${node.name.value} requires a bounded limit`);
  }

  let value: JsonValue | undefined;
  if (argument.value.kind === Kind.INT) {
    value = Number(argument.value.value);
  } else if (argument.value.kind === Kind.VARIABLE) {
    value = variables[argument.value.name.value];
  } else {
    throw new GraphqlPolicyError(`Limit on ${node.name.value} must be an integer or variable`);
  }

  if (!Number.isInteger(value) || typeof value !== "number" || value < 1 || value > maxRows) {
    throw new GraphqlPolicyError(`Limit on ${node.name.value} must be between 1 and ${maxRows}`);
  }
  return value;
}

function assertParameterized(value: ValueNode): void {
  if (
    value.kind === Kind.VARIABLE ||
    value.kind === Kind.BOOLEAN ||
    value.kind === Kind.ENUM ||
    value.kind === Kind.NULL
  ) {
    return;
  }
  if (value.kind === Kind.LIST) {
    value.values.forEach(assertParameterized);
    return;
  }
  if (value.kind === Kind.OBJECT) {
    value.fields.forEach((field) => assertParameterized(field.value));
    return;
  }
  throw new GraphqlPolicyError("Scalar filter values must use GraphQL variables");
}

function operationFrom(document: DocumentNode): OperationDefinitionNode {
  if (document.definitions.length !== 1) {
    throw new GraphqlPolicyError("Exactly one named query operation is required");
  }
  const definition = document.definitions[0];
  if (definition.kind !== Kind.OPERATION_DEFINITION || definition.operation !== "query") {
    throw new GraphqlPolicyError("Only query operations are allowed");
  }
  if (!definition.name) {
    throw new GraphqlPolicyError("The query operation must be named");
  }
  return definition;
}

function assertPolicy(
  schema: GraphQLSchema,
  document: DocumentNode,
  variables: JsonObject,
  options: Required<Pick<ExecutorOptions, "maxComplexity" | "maxDepth" | "maxRows">>,
): void {
  const operation = operationFrom(document);
  const validationErrors = validate(schema, document);
  if (validationErrors.length > 0) {
    throw new GraphqlPolicyError(validationErrors.map((error) => error.message).join("; "));
  }

  const variableResult = getVariableValues(schema, operation.variableDefinitions ?? [], variables);
  if (variableResult.errors) {
    throw new GraphqlPolicyError(variableResult.errors.map((error) => error.message).join("; "));
  }

  const typeInfo = new TypeInfo(schema);
  const multiplierStack: number[] = [];
  let complexity = 0;
  let depth = 0;
  let multiplier = 1;

  visit(
    document,
    visitWithTypeInfo(typeInfo, {
      Directive() {
        throw new GraphqlPolicyError("GraphQL directives are not allowed");
      },
      Field: {
        enter(node) {
          if (node.alias) {
            throw new GraphqlPolicyError("Aliases are not allowed");
          }
          if (node.name.value.startsWith("__")) {
            throw new GraphqlPolicyError("Introspection fields are not allowed");
          }
          for (const argument of node.arguments ?? []) {
            if (argument.name.value !== "limit") assertParameterized(argument.value);
          }

          depth += 1;
          if (depth > options.maxDepth) {
            throw new GraphqlPolicyError(`Query exceeds depth limit ${options.maxDepth}`);
          }

          multiplierStack.push(multiplier);
          const type = typeInfo.getType();
          if (type && outputContainsList(type)) {
            multiplier *= readLimit(node, variables, options.maxRows);
          }
          complexity += multiplier;
          if (complexity > options.maxComplexity) {
            throw new GraphqlPolicyError(`Query exceeds complexity limit ${options.maxComplexity}`);
          }
        },
        leave() {
          multiplier = multiplierStack.pop() ?? 1;
          depth -= 1;
        },
      },
    }),
  );
}

function sortedObject(value: JsonObject): JsonObject {
  return Object.fromEntries(
    Object.entries(value)
      .sort(([left], [right]) => left.localeCompare(right))
      .map(([key, item]) => [key, normalizeJson(item)]),
  );
}

function normalizeJson(value: JsonValue): JsonValue {
  if (Array.isArray(value)) return value.map(normalizeJson);
  if (value && typeof value === "object") return sortedObject(value);
  return value;
}

function collectEntityIds(data: JsonObject): EntityIds {
  const ids = {
    ability: new Set<number>(),
    item: new Set<number>(),
    move: new Set<number>(),
    pokemon: new Set<number>(),
  };
  const entityKeys: Record<string, keyof EntityIds> = {
    ability: "ability",
    item: "item",
    move: "move",
    pokemon: "pokemon",
    pokemonspecies: "pokemon",
  };
  const foreignKeys: Record<string, keyof EntityIds> = {
    ability_id: "ability",
    evolution_item_id: "item",
    item_id: "item",
    move_id: "move",
    pokemon_id: "pokemon",
    pokemon_species_id: "pokemon",
  };

  function walk(value: JsonValue, parentKey?: string): void {
    if (Array.isArray(value)) {
      value.forEach((item) => walk(item, parentKey));
      return;
    }
    if (!value || typeof value !== "object") return;

    const object = value as JsonObject;
    const entityType = parentKey ? entityKeys[parentKey] : undefined;
    if (entityType && Number.isInteger(object.id) && typeof object.id === "number") {
      ids[entityType].add(object.id);
    }
    for (const [key, item] of Object.entries(object)) {
      const foreignType = foreignKeys[key];
      if (foreignType && Number.isInteger(item) && typeof item === "number") {
        ids[foreignType].add(item);
      }
      walk(item, key);
    }
  }

  walk(data);
  return Object.fromEntries(
    Object.entries(ids).map(([key, values]) => [key, [...values].sort((left, right) => left - right)]),
  ) as unknown as EntityIds;
}

export function collectEntityReferences(data: JsonObject): EntityReferences {
  const references: Record<keyof EntityIds, Map<number, string>> = {
    ability: new Map(),
    item: new Map(),
    move: new Map(),
    pokemon: new Map(),
  };
  const entityKeys: Record<string, keyof EntityIds> = {
    ability: "ability",
    item: "item",
    move: "move",
    pokemon: "pokemon",
    pokemonspecies: "pokemon",
  };

  function walk(value: JsonValue, parentKey?: string): void {
    if (Array.isArray(value)) {
      value.forEach((item) => walk(item, parentKey));
      return;
    }
    if (!value || typeof value !== "object") return;
    const object = value as JsonObject;
    const entityType = parentKey ? entityKeys[parentKey] : undefined;
    if (
      entityType &&
      Number.isInteger(object.id) &&
      typeof object.id === "number" &&
      typeof object.name === "string"
    ) {
      references[entityType].set(object.id, object.name);
    }
    for (const [key, item] of Object.entries(object)) walk(item, key);
  }

  walk(data);
  return Object.fromEntries(
    Object.entries(references).map(([key, values]) => [
      key,
      [...values].map(([id, name]) => ({ id, name })).sort((left, right) => left.id - right.id),
    ]),
  ) as EntityReferences;
}

export function createReadonlyGraphqlExecutor(options: ExecutorOptions) {
  const fetchImpl = options.fetchImpl ?? fetch;
  const limits = {
    maxComplexity: options.maxComplexity ?? 10_000,
    maxDepth: options.maxDepth ?? 8,
    maxResponseBytes: options.maxResponseBytes ?? 1_000_000,
    maxRows: options.maxRows ?? 100,
    timeoutMs: options.timeoutMs ?? 5_000,
  };

  return {
    async execute(request: GraphqlRequest, signal?: AbortSignal): Promise<GraphqlExecution> {
      signal?.throwIfAborted();
      if (!request.purpose.trim() || request.purpose.length > 160) {
        throw new GraphqlPolicyError("Query purpose must be between 1 and 160 characters");
      }
      const document = parse(request.query);
      const variables = canonicalVariables(document, request.variables);
      assertPolicy(options.schema, document, variables, limits);

      const started = performance.now();
      let response: Response;
      const timeoutSignal = AbortSignal.timeout(limits.timeoutMs);
      const requestSignal = signal ? AbortSignal.any([signal, timeoutSignal]) : timeoutSignal;
      try {
        response = await fetchImpl(
          new Request(options.endpoint, {
            body: JSON.stringify({ query: request.query, variables }),
            headers: {
              "content-type": "application/json",
              "user-agent": "ask-pokedexer-backend/1",
            },
            method: "POST",
            signal: requestSignal,
          }),
        );
      } catch (error) {
        if (signal?.aborted) throw signal.reason;
        throw new GraphqlInfrastructureError(
          `PokéAPI request failed: ${error instanceof Error ? error.message : "unknown network error"}`,
        );
      }
      let text: string;
      try {
        text = await response.text();
      } catch (error) {
        if (signal?.aborted) throw signal.reason;
        throw new GraphqlInfrastructureError(
          `PokéAPI response failed: ${error instanceof Error ? error.message : "unknown network error"}`,
        );
      }
      if (Buffer.byteLength(text) > limits.maxResponseBytes) {
        throw new GraphqlPolicyError("PokéAPI response exceeded the size limit");
      }
      if (!response.ok) {
        const message = `PokéAPI returned HTTP ${response.status}`;
        if (response.status === 429 || response.status >= 500) {
          throw new GraphqlInfrastructureError(message);
        }
        throw new GraphqlPolicyError(message);
      }

      let payload: { data?: JsonObject; errors?: Array<{ message?: string }> };
      try {
        payload = JSON.parse(text) as typeof payload;
      } catch {
        throw new GraphqlInfrastructureError("PokéAPI returned invalid JSON");
      }
      if (payload.errors?.length) {
        throw new GraphqlPolicyError(
          payload.errors.map((error) => error.message ?? "Unknown GraphQL error").join("; "),
        );
      }
      if (!payload.data) {
        throw new GraphqlPolicyError("PokéAPI response did not contain data");
      }
      signal?.throwIfAborted();

      return {
        data: payload.data,
        entityIds: collectEntityIds(payload.data),
        entityReferences: collectEntityReferences(payload.data),
        trace: {
          document_sha256: createHash("sha256").update(request.query).digest("hex"),
          duration_ms: Math.round(performance.now() - started),
          purpose: request.purpose,
          query: request.query,
          response_sha256: createHash("sha256")
            .update(JSON.stringify(normalizeJson(payload.data)))
            .digest("hex"),
          variables: sortedObject(variables),
        },
      };
    },
  };
}
