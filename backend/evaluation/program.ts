import { readFileSync } from "node:fs";
import { resolve } from "node:path";
import { pathToFileURL } from "node:url";

import { z } from "zod";

import { EVALUATION_CANDIDATES } from "./profile.js";
import {
  buildSchedule,
  loadSearchSuite,
  loadSuite,
  type SearchEvaluationSuite,
} from "./suite.js";
import type { EvaluationSuite } from "./types.js";

const suiteReferenceSchema = z.discriminatedUnion("role", [
  z.object({
    requirement: z.enum(["all_full_pass", "zero_fabrications"]),
    role: z.literal("gate"),
    version: z.string().regex(/^[a-z0-9-]+$/),
  }),
  z.object({
    category_weighting: z.literal("equal"),
    role: z.literal("scored"),
    version: z.string().regex(/^[a-z0-9-]+$/),
    weight: z.number().positive().max(1),
  }),
]);

const programSchema = z.object({
  candidates: z.array(z.string().min(1)).min(1).optional(),
  kind: z.literal("evaluation-program"),
  repetitions: z.number().int().min(1).max(10),
  seed: z.string().trim().min(1),
  suites: z.array(suiteReferenceSchema).min(2),
  version: z.string().regex(/^[a-z0-9-]+$/),
});

type SourceSuite = EvaluationSuite | SearchEvaluationSuite;
type ProgramCase = SourceSuite["cases"][number];

export interface LoadedProgramSuite {
  cases: ProgramCase[];
  category_weighting?: "equal";
  requirement?: "all_full_pass" | "zero_fabrications";
  role: "gate" | "scored";
  version: string;
  weight?: number;
}

export interface LoadedEvaluationProgram {
  candidates: string[];
  kind: "evaluation-program";
  repetitions: number;
  seed: string;
  suites: LoadedProgramSuite[];
  total_cases: number;
  version: string;
}

export interface ProgramEvaluationRecord {
  candidate: string;
  category: string;
  full_pass: boolean;
  fabrication_detected?: boolean;
  question_id: string;
  repetition: number;
  suite_version: string;
}

function assertUnique(values: string[], label: string): void {
  if (new Set(values).size !== values.length) throw new Error(`${label} must be unique`);
}

export function loadEvaluationProgram(
  source: string,
  suiteSources: SourceSuite[],
): LoadedEvaluationProgram {
  const config = programSchema.parse(JSON.parse(source));
  const candidates = config.candidates ?? Object.keys(EVALUATION_CANDIDATES);
  assertUnique(candidates, "Program candidate IDs");
  for (const candidate of candidates) {
    if (!(candidate in EVALUATION_CANDIDATES)) {
      throw new Error(`Program references unknown candidate ${candidate}`);
    }
  }
  assertUnique(config.suites.map((suite) => suite.version), "Program suite versions");
  assertUnique(suiteSources.map((suite) => suite.version), "Loaded suite versions");
  const sources = new Map(suiteSources.map((suite) => [suite.version, suite]));
  const suites = config.suites.map((reference): LoadedProgramSuite => {
    const suite = sources.get(reference.version);
    if (!suite) throw new Error(`Program suite ${reference.version} was not loaded`);
    if (reference.role === "gate" && suite.kind !== "canary") {
      throw new Error(`Gate suite ${reference.version} must be a canary`);
    }
    if (reference.role === "scored" && suite.kind === "canary") {
      throw new Error(`Canary suite ${reference.version} must be a gate`);
    }
    return { ...reference, cases: suite.cases };
  });
  if (sources.size !== suites.length) throw new Error("Loaded suites must exactly match the program");
  if (suites.filter((suite) => suite.role === "gate").length !== 1) {
    throw new Error("The program must contain exactly one gate suite");
  }
  const weight = suites.reduce((sum, suite) => sum + (suite.weight ?? 0), 0);
  if (Math.abs(weight - 1) > Number.EPSILON) {
    throw new Error("Scored suite weights must sum to 1");
  }
  const questions = suites.flatMap((suite) =>
    suite.cases.map((testCase) => testCase.question.toLocaleLowerCase()),
  );
  assertUnique(questions, "Program questions");
  const totalCases = questions.length;
  if (totalCases !== 66) throw new Error("The comprehensive program must contain 66 cases");
  return { ...config, candidates, suites, total_cases: totalCases };
}

export function buildProgramSchedule(
  program: LoadedEvaluationProgram,
  candidates: string[] = program.candidates,
) {
  let ordinal = 0;
  return program.suites.flatMap((suite) =>
    buildSchedule(
      suite,
      candidates,
      program.repetitions,
      program.seed,
    ).map((entry) => ({
      ...entry,
      ordinal: (ordinal += 1),
      role: suite.role,
      suite_version: suite.version,
    })),
  );
}

function passCount(records: ProgramEvaluationRecord[]) {
  const fullPasses = records.filter((record) => record.full_pass).length;
  return {
    full_pass_rate: records.length === 0 ? 0 : fullPasses / records.length,
    full_passes: fullPasses,
    total: records.length,
  };
}

export function summarizeProgramRecords(
  program: LoadedEvaluationProgram,
  records: ProgramEvaluationRecord[],
) {
  const suites = new Map(program.suites.map((suite) => [suite.version, suite]));
  const expected = new Map(
    program.suites.flatMap((suite) =>
      suite.cases.flatMap((testCase) =>
        Array.from({ length: program.repetitions }, (_, index) => [
          `${suite.version}:${testCase.id}:${index + 1}`,
          testCase.category,
        ] as const),
      ),
    ),
  );
  for (const record of records) {
    const category = expected.get(
      `${record.suite_version}:${record.question_id}:${record.repetition}`,
    );
    if (!category || category !== record.category) {
      throw new Error(`Record does not match program case ${record.suite_version}:${record.question_id}`);
    }
  }
  const candidateIds = [...new Set(records.map((record) => record.candidate))].sort();
  const candidates = Object.fromEntries(
    candidateIds.map((candidate) => {
      const selected = records.filter((record) => record.candidate === candidate);
      const keys = selected.map(
        (record) => `${record.suite_version}:${record.question_id}:${record.repetition}`,
      );
      assertUnique(keys, `Program records for ${candidate}`);
      if (keys.length !== expected.size) {
        throw new Error(`Program records for ${candidate} are incomplete`);
      }
      const gateRecords = selected.filter(
        (record) => suites.get(record.suite_version)?.role === "gate",
      );
      const gateCounts = passCount(gateRecords);
      const gate = {
        ...gateCounts,
        passed: program.suites.find((suite) => suite.role === "gate")?.requirement === "zero_fabrications"
          ? gateRecords.every((record) => record.fabrication_detected !== true)
          : gateCounts.full_passes === gateCounts.total,
      };
      const scoredSuites = Object.fromEntries(
        program.suites.filter((suite) => suite.role === "scored").map((suite) => {
          const suiteRecords = selected.filter(
            (record) => record.suite_version === suite.version,
          );
          const categories = [...new Set(suite.cases.map((testCase) => testCase.category))].sort();
          const categoryResults = Object.fromEntries(
            categories.map((category) => [
              category,
              passCount(suiteRecords.filter((record) => record.category === category)),
            ]),
          );
          const macroRate =
            categories.reduce(
              (sum, category) => sum + categoryResults[category].full_pass_rate,
              0,
            ) / categories.length;
          return [
            suite.version,
            {
              ...passCount(suiteRecords),
              categories: categoryResults,
              macro_category_full_pass_rate: macroRate,
              weight: suite.weight ?? 0,
            },
          ];
        }),
      );
      const weightedRate = Object.values(scoredSuites).reduce(
        (sum, suite) => sum + suite.macro_category_full_pass_rate * suite.weight,
        0,
      );
      return [
        candidate,
        {
          gate,
          scored_suites: scoredSuites,
          weighted_full_pass_rate: gate.passed ? weightedRate : null,
        },
      ];
    }),
  );
  return { candidates, records: records.length, version: program.version };
}

function main(): void {
  const evaluationRoot = resolve(import.meta.dirname);
  const program = loadEvaluationProgram(
    readFileSync(resolve(evaluationRoot, "programs/comprehensive-v3.json"), "utf8"),
    [
      loadSuite(readFileSync(resolve(evaluationRoot, "suites/holdout-v4.json"), "utf8")),
      loadSearchSuite(readFileSync(resolve(evaluationRoot, "suites/search-v3.json"), "utf8")),
      loadSuite(
        readFileSync(resolve(evaluationRoot, "suites/grounding-canary-v2.json"), "utf8"),
      ),
    ],
  );
  const schedule = buildProgramSchedule(program);
  console.log(
    JSON.stringify(
      {
        cases: program.total_cases,
        kind: program.kind,
        paid_requests_executed: 0,
        schedule_entries: schedule.length,
        stages: program.suites.map((suite) => ({
          cases: suite.cases.length,
          role: suite.role,
          schedule_entries: schedule.filter((entry) => entry.suite_version === suite.version).length,
          version: suite.version,
          ...(suite.weight === undefined ? {} : { weight: suite.weight }),
        })),
        version: program.version,
      },
      null,
      2,
    ),
  );
}

const invokedPath = process.argv[1] ? pathToFileURL(resolve(process.argv[1])).href : undefined;
if (invokedPath === import.meta.url) {
  try {
    main();
  } catch (error) {
    console.error(error instanceof Error ? error.message : "Program validation failed");
    process.exitCode = 1;
  }
}
