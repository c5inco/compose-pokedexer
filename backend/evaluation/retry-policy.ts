import type { EvaluationRecord } from "./types.js";

export type RetryPolicyMode = "oracle-contract" | "runtime-detectable";

function key(record: EvaluationRecord): string {
  return `${record.question_id}:${record.repetition}`;
}

function cost(record: EvaluationRecord): number {
  return record.result.metrics.estimated_cost_usd;
}

export function evaluateRetryPolicy(
  lowRecords: EvaluationRecord[],
  mediumRecords: EvaluationRecord[],
  mode: RetryPolicyMode,
) {
  const mediumByKey = new Map(mediumRecords.map((record) => [key(record), record]));
  let estimatedCost = 0;
  let fullPasses = 0;
  let retries = 0;
  const selections: Array<{
    question_id: string;
    repetition: number;
    retried: boolean;
    selected_candidate: string;
  }> = [];

  for (const low of lowRecords) {
    estimatedCost += cost(low);
    const shouldRetry =
      low.status === "failure" || (mode === "oracle-contract" && !low.evaluation.full_pass);
    let selected = low;
    if (shouldRetry) {
      const medium = mediumByKey.get(key(low));
      if (!medium) throw new Error(`Missing Luna Medium result for ${key(low)}`);
      estimatedCost += cost(medium);
      selected = medium;
      retries += 1;
    }
    if (selected.evaluation.full_pass) fullPasses += 1;
    selections.push({
      question_id: low.question_id,
      repetition: low.repetition,
      retried: shouldRetry,
      selected_candidate: selected.candidate,
    });
  }

  return {
    estimated_cost_usd: Math.round(estimatedCost * 1_000_000_000) / 1_000_000_000,
    full_passes: fullPasses,
    mode,
    retries,
    selections,
    total: lowRecords.length,
  };
}
