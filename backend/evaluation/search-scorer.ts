import { isDeepStrictEqual } from "node:util";

import type { EntityIds } from "../src/readonly-graphql.js";
import type { SearchConstraint, SearchInterpretation } from "../src/search-descriptors.js";

export type SearchBehavior = "answer" | "insufficient_evidence" | "needs_clarification" | "not_found" | "requires_enrichment" | "scoped_no_match";
export type SearchCategory = "ambiguity" | "combined" | "no-match" | "pagination" | "physical" | "subjective";

export interface ExpectedSearchInterpretation {
  ambiguous_terms: string[];
  conflicts: Array<{ field: SearchConstraint["field"]; terms: string[] }>;
  constraints: SearchConstraint[];
  status: SearchInterpretation["status"];
  unsupported_terms: string[];
}

export interface SearchEvaluationTestCase {
  category: SearchCategory;
  expected: {
    behavior: SearchBehavior;
    canonical_pokemon_ids: number[];
    evidence_coverage: boolean;
    interpretation: ExpectedSearchInterpretation;
    minimum_successful_queries?: number;
    pagination?: { min_unique_results: number; required: boolean };
    top_page_size: number;
  };
  id: string;
  question: string;
}

export interface SearchFixtureResult {
  behavior?: SearchBehavior;
  evidence_entity_ids: EntityIds;
  interpretation: SearchInterpretation | null;
  pages?: Array<{
    pagination: {
      continuation_cursor: string | null;
      has_more: boolean;
      page_size: number;
      scope: string;
    };
    pokemon_ids: number[];
  }>;
  response: {
    ability_ids: number[];
    answer: string;
    item_ids: number[];
    move_ids: number[];
    pokemon_ids: number[];
  };
  successful_evidence_queries?: number;
}

export interface SearchEvaluationScore {
  behavior_pass: boolean;
  evidence_coverage_pass: boolean;
  full_pass: boolean;
  interpretation_pass: boolean;
  pagination_pass: boolean;
  relevance_pass: boolean;
}

export interface SearchEvaluationRecord {
  candidate: string;
  category: SearchCategory;
  score: SearchEvaluationScore;
}

function sameIds(left: number[], right: number[]): boolean {
  return left.length === right.length && left.every((id, index) => id === right[index]);
}

function machineInterpretation(
  interpretation: SearchInterpretation | null,
): ExpectedSearchInterpretation | null {
  if (!interpretation) return null;
  return {
    ambiguous_terms: interpretation.ambiguous_terms.map((item) => item.term),
    conflicts: interpretation.conflicts.map((item) => ({
      field: item.field,
      terms: [...item.terms],
    })),
    constraints: interpretation.constraints,
    status: interpretation.status,
    unsupported_terms: interpretation.unsupported_terms.map((item) => item.term),
  };
}

export function scoreSearchEvaluation(
  testCase: SearchEvaluationTestCase,
  result: SearchFixtureResult,
): SearchEvaluationScore {
  const returned = result.response.pokemon_ids.slice(0, testCase.expected.top_page_size);
  const pages = result.pages ?? [];
  const pagedIds = pages.flatMap((page) => page.pokemon_ids);
  const allReturnedIds = pages.length > 0 ? pagedIds : returned;
  const canonicalIds = testCase.expected.canonical_pokemon_ids;
  const expectedFirstPage = canonicalIds.slice(0, testCase.expected.top_page_size);
  const relevancePass =
    sameIds(returned, expectedFirstPage) &&
    allReturnedIds.length <= canonicalIds.length &&
    sameIds(allReturnedIds, canonicalIds.slice(0, allReturnedIds.length));
  const interpretationPass = isDeepStrictEqual(
    machineInterpretation(result.interpretation),
    testCase.expected.interpretation,
  );
  const idsToCheck = {
    ability: result.response.ability_ids,
    item: result.response.item_ids,
    move: result.response.move_ids,
    pokemon: pages.length > 0 ? pagedIds : returned,
  };
  const successfulQueries = result.successful_evidence_queries ?? 0;
  const actualBehavior =
    result.behavior ??
    (result.interpretation?.status === "needs_clarification" ||
    result.interpretation?.status === "requires_enrichment"
      ? result.interpretation.status
      : Object.values(idsToCheck).some((ids) => ids.length > 0)
        ? "answer"
        : "insufficient_evidence");
  const scopedInsufficientEvidence =
    testCase.expected.behavior === "scoped_no_match" &&
    actualBehavior === "insufficient_evidence" &&
    successfulQueries === 0;
  const evidenceCoveragePass =
    !testCase.expected.evidence_coverage ||
    scopedInsufficientEvidence ||
    (successfulQueries >= (testCase.expected.minimum_successful_queries ?? 0) &&
      (Object.keys(idsToCheck) as Array<keyof EntityIds>).every((key) => {
        const evidence = new Set(result.evidence_entity_ids[key]);
        return idsToCheck[key].every((id) => evidence.has(id));
      }));
  const behaviorPass =
    (testCase.expected.behavior === "scoped_no_match"
      ? (actualBehavior === "not_found" && successfulQueries > 0) || scopedInsufficientEvidence
      : actualBehavior === testCase.expected.behavior);
  const paginationPass = !testCase.expected.pagination?.required ||
    (pages.length > 1 &&
      pagedIds.length >= testCase.expected.pagination.min_unique_results &&
      new Set(pagedIds).size === pagedIds.length &&
      pagedIds.every((id, index) => index === 0 || pagedIds[index - 1] < id) &&
      pages.every((page, index) =>
        page.pokemon_ids.length <= 8 &&
        page.pokemon_ids.every((id, idIndex, ids) => idIndex === 0 || ids[idIndex - 1] < id) &&
        page.pagination.page_size === 8 &&
        page.pagination.scope === "verified_entity_ids" &&
        page.pagination.has_more === (index < pages.length - 1) &&
        (index < pages.length - 1
          ? Boolean(page.pagination.continuation_cursor)
          : page.pagination.continuation_cursor === null),
      ));
  const score = {
    behavior_pass: behaviorPass,
    evidence_coverage_pass: evidenceCoveragePass,
    full_pass: false,
    interpretation_pass: interpretationPass,
    pagination_pass: paginationPass,
    relevance_pass: relevancePass,
  };
  score.full_pass = Object.entries(score)
    .filter(([name]) => name !== "full_pass")
    .every(([, passed]) => passed);
  return score;
}

export function summarizeSearchRecords(records: SearchEvaluationRecord[]) {
  return {
    candidates: Object.fromEntries(
      [...new Set(records.map((record) => record.candidate))].sort().map((candidate) => {
        const selected = records.filter((record) => record.candidate === candidate);
        const categories = [...new Set(selected.map((record) => record.category))].sort();
        return [
          candidate,
          {
            categories: Object.fromEntries(
              categories.map((category) => {
                const categoryRecords = selected.filter((record) => record.category === category);
                return [
                  category,
                  {
                    full_passes: categoryRecords.filter((record) => record.score.full_pass).length,
                    total: categoryRecords.length,
                  },
                ];
              }),
            ),
            full_passes: selected.filter((record) => record.score.full_pass).length,
            total: selected.length,
          },
        ];
      }),
    ),
    kind: "search" as const,
    records: records.length,
  };
}
