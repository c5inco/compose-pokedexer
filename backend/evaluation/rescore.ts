import { failedEvaluation, scoreEvaluation } from "./scorer.js";
import type {
  EvaluationRecord,
  EvaluationScore,
  EvaluationSuite,
} from "./types.js";

export interface RescoredEvaluationRecord extends EvaluationRecord {
  original_evaluation: EvaluationScore;
}

export function rescoreEvaluationRecords(
  records: EvaluationRecord[],
  suite: EvaluationSuite,
): RescoredEvaluationRecord[] {
  const testCases = new Map(suite.cases.map((testCase) => [testCase.id, testCase]));

  return records.map((record) => {
    const testCase = testCases.get(record.question_id);
    if (!testCase) throw new Error(`Evaluation suite does not contain ${record.question_id}`);
    return {
      ...record,
      evaluation:
        record.status === "success"
          ? scoreEvaluation(testCase, record.result, suite.score_version)
          : failedEvaluation(),
      original_evaluation: { ...record.evaluation },
    };
  });
}
