import { createHash } from "node:crypto";

import {
  GraphqlInfrastructureError,
  type GraphqlExecution,
  type GraphqlRequest,
} from "../src/readonly-graphql.js";

export interface ConsistencyProbe {
  id: string;
  request: GraphqlRequest;
}

export interface ConsistencyProbeResult {
  id: string;
  response_sha256: string;
}

interface ControlOptions {
  execute(request: GraphqlRequest): Promise<GraphqlExecution>;
}

function normalized(value: unknown): unknown {
  if (Array.isArray(value)) return value.map(normalized);
  if (!value || typeof value !== "object") return value;
  return Object.fromEntries(
    Object.entries(value)
      .sort(([left], [right]) => left.localeCompare(right))
      .map(([key, item]) => [key, normalized(item)]),
  );
}

function requestKey(request: GraphqlRequest): string {
  return createHash("sha256")
    .update(JSON.stringify(normalized({ query: request.query, variables: request.variables })))
    .digest("hex");
}

export function createEvaluationGraphqlControls(options: ControlOptions) {
  const cache = new Map<string, GraphqlExecution>();

  const executeWithRetry = async (request: GraphqlRequest): Promise<GraphqlExecution> => {
    for (let attempt = 1; attempt <= 2; attempt += 1) {
      try {
        const result = await options.execute(request);
        return {
          ...result,
          trace: {
            ...result.trace,
            cache_hit: false,
            infrastructure_attempts: attempt,
          },
        };
      } catch (error) {
        if (!(error instanceof GraphqlInfrastructureError) || attempt === 2) throw error;
      }
    }
    throw new Error("Unreachable GraphQL retry state");
  };

  return {
    consistencySha256(results: ConsistencyProbeResult[]): string {
      return createHash("sha256").update(JSON.stringify(normalized(results))).digest("hex");
    },

    async execute(request: GraphqlRequest): Promise<GraphqlExecution> {
      const key = requestKey(request);
      const cached = cache.get(key);
      if (cached) {
        return {
          ...structuredClone(cached),
          trace: {
            ...cached.trace,
            cache_hit: true,
            duration_ms: 0,
            infrastructure_attempts: 0,
            purpose: request.purpose,
          },
        };
      }
      const result = await executeWithRetry(request);
      cache.set(key, structuredClone(result));
      return result;
    },

    async runConsistencyProbes(probes: ConsistencyProbe[]): Promise<ConsistencyProbeResult[]> {
      const results: ConsistencyProbeResult[] = [];
      for (const probe of probes) {
        const result = await executeWithRetry(probe.request);
        const responseSha256 = result.trace.response_sha256;
        if (!responseSha256) {
          throw new Error(`Consistency probe ${probe.id} did not produce a response hash`);
        }
        results.push({ id: probe.id, response_sha256: responseSha256 });
      }
      return results;
    },
  };
}
