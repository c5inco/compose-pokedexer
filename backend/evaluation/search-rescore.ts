import { createHash } from "node:crypto";
import { mkdirSync, readFileSync, writeFileSync } from "node:fs";
import { resolve } from "node:path";
import { pathToFileURL } from "node:url";

import type { EntityIds } from "../src/readonly-graphql.js";
import type { SearchInterpretation } from "../src/search-descriptors.js";
import { createPaginationService } from "../src/pagination.js";
import {
  scoreSearchEvaluation,
  summarizeSearchRecords,
  type SearchCategory,
  type SearchEvaluationScore,
  type SearchFixtureResult,
} from "./search-scorer.js";
import { loadSearchSuite, type SearchEvaluationSuite } from "./suite.js";

export const EVALUATION_CURSOR_SECRET = "deterministic-evaluation-only-cursor-secret";

interface StoredSearchResponse {
  ability_ids: number[];
  answer: string;
  interpretation?: SearchInterpretation;
  item_ids: number[];
  move_ids: number[];
  pagination?: {
    continuation_cursor: string | null;
    has_more: boolean;
    page_size: 8;
    scope: "verified_entity_ids";
  };
  pokemon_ids: number[];
}

interface StoredSearchSuccess {
  diagnostics?: unknown;
  evidence_entity_ids: EntityIds;
  metrics: { graphql_calls: number; [key: string]: unknown };
  response: StoredSearchResponse;
}

interface StoredSearchFailure {
  error: string;
  metrics: { graphql_calls?: number; [key: string]: unknown };
}

export interface StoredSearchRecord {
  candidate: string;
  category: SearchCategory;
  ordinal: number;
  question: string;
  question_id: string;
  repetition: number;
  result: StoredSearchFailure | StoredSearchSuccess;
  score: SearchEvaluationScore;
  status: "failure" | "success";
}

export interface RescoredSearchRecord extends StoredSearchRecord {
  original_score: SearchEvaluationScore;
  score_version: "canonical-predicate-v2";
}

export interface SearchRescoreOptions {
  input: string;
  output: string;
}

function nextValue(arguments_: string[], index: number, name: string): string {
  const value = arguments_[index + 1];
  if (!value || value.startsWith("--")) throw new Error(`${name} requires a value`);
  return value;
}

export function parseSearchRescoreOptions(arguments_: string[]): SearchRescoreOptions {
  let input: string | undefined;
  let output: string | undefined;
  for (let index = 0; index < arguments_.length; index += 1) {
    const argument = arguments_[index];
    if (argument === "--input" || argument === "--output") {
      const value = nextValue(arguments_, index, argument);
      if (argument === "--input") input = value;
      else output = value;
      index += 1;
      continue;
    }
    throw new Error(`Unknown argument ${argument}`);
  }
  if (!input || !output) throw new Error("--input and --output are required");
  return { input, output };
}

function failedSearchScore(): SearchEvaluationScore {
  return {
    behavior_pass: false,
    evidence_coverage_pass: false,
    full_pass: false,
    interpretation_pass: false,
    pagination_pass: false,
    relevance_pass: false,
  };
}

function isSuccess(record: StoredSearchRecord): record is StoredSearchRecord & {
  result: StoredSearchSuccess;
  status: "success";
} {
  return record.status === "success" && "response" in record.result;
}

function fixtureFromStored(record: StoredSearchRecord & { result: StoredSearchSuccess }): SearchFixtureResult {
  const response = record.result.response;
  const pages: NonNullable<SearchFixtureResult["pages"]> = [];
  const paginationService = createPaginationService({
    now: () => 0,
    secret: EVALUATION_CURSOR_SECRET,
  });
  let pagination = response.pagination;
  let pokemonIds = response.pokemon_ids;
  for (let pageNumber = 0; pagination && pageNumber < 13; pageNumber += 1) {
    pages.push({ pagination, pokemon_ids: pokemonIds });
    if (!pagination.continuation_cursor) break;
    try {
      const continuation = paginationService.nextPage(
        record.question,
        pagination.continuation_cursor,
      );
      pagination = continuation.pagination;
      pokemonIds = continuation.pokemon_ids;
    } catch {
      break;
    }
  }
  const interpretation = response.interpretation ?? null;
  const hasEntities =
    response.ability_ids.length +
      response.item_ids.length +
      response.move_ids.length +
      response.pokemon_ids.length >
    0;
  const behavior =
    interpretation?.status === "needs_clarification" ||
    interpretation?.status === "requires_enrichment"
      ? interpretation.status
      : hasEntities
        ? "answer"
        : record.result.metrics.graphql_calls > 0
          ? "not_found"
          : "insufficient_evidence";
  return {
    behavior,
    evidence_entity_ids: record.result.evidence_entity_ids,
    interpretation,
    pages,
    response,
    successful_evidence_queries: record.result.metrics.graphql_calls,
  };
}

export function rescoreSearchRecords(
  records: StoredSearchRecord[],
  suite: SearchEvaluationSuite,
): RescoredSearchRecord[] {
  const testCases = new Map(suite.cases.map((testCase) => [testCase.id, testCase]));
  return records.map((record) => {
    const testCase = testCases.get(record.question_id);
    if (!testCase) throw new Error(`Search suite does not contain ${record.question_id}`);
    if (record.question !== testCase.question || record.category !== testCase.category) {
      throw new Error(`Stored search record ${record.question_id} does not match the scoring suite`);
    }
    return {
      ...record,
      original_score: { ...record.score },
      score: isSuccess(record)
        ? scoreSearchEvaluation(testCase, fixtureFromStored(record))
        : failedSearchScore(),
      score_version: suite.score_version,
    };
  });
}

function sha256(source: string): string {
  return createHash("sha256").update(source).digest("hex");
}

function readRecords(path: string): StoredSearchRecord[] {
  const records: StoredSearchRecord[] = [];
  const keys = new Set<string>();
  for (const [index, line] of readFileSync(path, "utf8").split("\n").entries()) {
    if (!line.trim()) continue;
    let record: StoredSearchRecord;
    try {
      record = JSON.parse(line) as StoredSearchRecord;
    } catch {
      throw new Error(`Invalid JSONL search record on line ${index + 1}`);
    }
    const key = `${record.candidate}:${record.question_id}:${record.repetition}`;
    if (keys.has(key)) throw new Error(`Duplicate search record ${key}`);
    keys.add(key);
    records.push(record);
  }
  return records;
}

function searchSummary(records: Array<StoredSearchRecord | RescoredSearchRecord>) {
  return summarizeSearchRecords(
    records.map((record) => ({
      candidate: record.candidate,
      category: record.category,
      score: record.score,
    })),
  );
}

export function main(arguments_: string[]): void {
  const options = parseSearchRescoreOptions(arguments_);
  const inputPath = resolve(options.input);
  const outputDirectory = resolve(options.output);
  const suitePath = resolve(import.meta.dirname, "suites/search-v2.json");
  const recordsSource = readFileSync(inputPath, "utf8");
  const suiteSource = readFileSync(suitePath, "utf8");
  const suite = loadSearchSuite(suiteSource);
  const original = readRecords(inputPath);
  const rescored = rescoreSearchRecords(original, suite);
  mkdirSync(outputDirectory, { recursive: true });

  const changed = rescored.filter(
    (record) => record.original_score.full_pass !== record.score.full_pass,
  );
  const summary = {
    kind: "search-rescore",
    original: searchSummary(original),
    paid_requests_executed: 0,
    rescored: searchSummary(rescored),
    score_changes: {
      full_pass_changed: changed.length,
      gained: changed.filter((record) => record.score.full_pass).length,
      lost: changed.filter((record) => !record.score.full_pass).length,
    },
    score_version: suite.score_version,
  };
  const recordsOutput = rescored.map((record) => JSON.stringify(record)).join("\n") + "\n";
  const manifest = {
    kind: "search-rescore",
    paid_requests_executed: 0,
    record_count: rescored.length,
    score_version: suite.score_version,
    source_records_sha256: sha256(recordsSource),
    suite_sha256: sha256(suiteSource),
    suite_version: suite.version,
  };
  writeFileSync(resolve(outputDirectory, "search-records-v2.jsonl"), recordsOutput, "utf8");
  writeFileSync(
    resolve(outputDirectory, "search-summary-v2.json"),
    `${JSON.stringify(summary, null, 2)}\n`,
    "utf8",
  );
  writeFileSync(
    resolve(outputDirectory, "rescore-manifest.json"),
    `${JSON.stringify(manifest, null, 2)}\n`,
    "utf8",
  );
  console.log(JSON.stringify(manifest, null, 2));
}

const invokedPath = process.argv[1] ? pathToFileURL(resolve(process.argv[1])).href : undefined;
if (invokedPath === import.meta.url) {
  try {
    main(process.argv.slice(2));
  } catch (error) {
    console.error(error instanceof Error ? error.message : "Search rescoring failed");
    process.exitCode = 1;
  }
}
