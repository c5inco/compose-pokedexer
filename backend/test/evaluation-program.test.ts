import assert from "node:assert/strict";
import { readFileSync } from "node:fs";
import { resolve } from "node:path";
import test from "node:test";

import {
  buildProgramSchedule,
  loadEvaluationProgram,
  summarizeProgramRecords,
} from "../evaluation/program.js";
import { EVALUATION_CANDIDATES } from "../evaluation/profile.js";
import { loadSearchSuite, loadSuite } from "../evaluation/suite.js";

const backendRoot = resolve(import.meta.dirname, "..");

function source(path: string): string {
  return readFileSync(resolve(backendRoot, path), "utf8");
}

function loadProgram() {
  const holdout = loadSuite(source("evaluation/suites/holdout-v3.json"));
  const search = loadSearchSuite(source("evaluation/suites/search-v2.json"));
  const canary = loadSuite(source("evaluation/suites/grounding-canary-v1.json"));
  return loadEvaluationProgram(source("evaluation/programs/comprehensive-v1.json"), [
    holdout,
    search,
    canary,
  ]);
}

function loadProgramV2() {
  const holdout = loadSuite(source("evaluation/suites/holdout-v3.json"));
  const search = loadSearchSuite(source("evaluation/suites/search-v3.json"));
  const canary = loadSuite(source("evaluation/suites/grounding-canary-v1.json"));
  return loadEvaluationProgram(source("evaluation/programs/comprehensive-v2.json"), [
    holdout,
    search,
    canary,
  ]);
}

function loadProgramV3() {
  return loadEvaluationProgram(source("evaluation/programs/comprehensive-v3.json"), [
    loadSuite(source("evaluation/suites/holdout-v4.json")),
    loadSearchSuite(source("evaluation/suites/search-v3.json")),
    loadSuite(source("evaluation/suites/grounding-canary-v2.json")),
  ]);
}

test("locks a globally unique 66-question program with predeclared weighting and gate policy", () => {
  const program = loadProgram();

  assert.equal(program.version, "comprehensive-v1");
  assert.equal(program.repetitions, 3);
  assert.equal(program.total_cases, 66);
  assert.deepEqual(
    program.suites.map(({ role, version, weight }) => ({ role, version, weight })),
    [
      { role: "gate", version: "grounding-canary-v1", weight: undefined },
      { role: "scored", version: "holdout-v3", weight: 0.5 },
      { role: "scored", version: "search-v2", weight: 0.5 },
    ],
  );
  assert.deepEqual(
    program.suites.filter((suite) => suite.role === "scored").map((suite) => suite.category_weighting),
    ["equal", "equal"],
  );
});

test("builds the staged 72-canary, 480-holdout, and 240-search schedule", () => {
  const schedule = buildProgramSchedule(loadProgram(), Object.keys(EVALUATION_CANDIDATES));

  assert.equal(schedule.length, 792);
  assert.deepEqual(
    Object.fromEntries(
      ["grounding-canary-v1", "holdout-v3", "search-v2"].map((version) => [
        version,
        schedule.filter((entry) => entry.suite_version === version).length,
      ]),
    ),
    {
      "grounding-canary-v1": 72,
      "holdout-v3": 480,
      "search-v2": 240,
    },
  );
  assert.equal(schedule[0]?.role, "gate");
  assert.equal(schedule.at(-1)?.suite_version, "search-v2");
});

test("locks the unseen search holdout into a five-repetition 66-question rerun", () => {
  const program = loadProgramV2();
  const schedule = buildProgramSchedule(program, Object.keys(EVALUATION_CANDIDATES));

  assert.equal(program.version, "comprehensive-v2");
  assert.equal(program.repetitions, 5);
  assert.equal(program.total_cases, 66);
  assert.deepEqual(
    program.suites.map(({ role, version, weight }) => ({ role, version, weight })),
    [
      { role: "gate", version: "grounding-canary-v1", weight: undefined },
      { role: "scored", version: "holdout-v3", weight: 0.5 },
      { role: "scored", version: "search-v3", weight: 0.5 },
    ],
  );
  assert.equal(schedule.length, 1_320);
  assert.deepEqual(
    Object.fromEntries(
      ["grounding-canary-v1", "holdout-v3", "search-v3"].map((version) => [
        version,
        schedule.filter((entry) => entry.suite_version === version).length,
      ]),
    ),
    {
      "grounding-canary-v1": 120,
      "holdout-v3": 800,
      "search-v3": 400,
    },
  );
});

test("v3 gates only on zero canary fabrications", () => {
  const program = loadProgramV3();
  const schedule = buildProgramSchedule(program);
  const records = program.suites.flatMap((suite) => suite.cases.flatMap((item) => Array.from({ length: program.repetitions }, (_, index) => ({ candidate: "candidate-a", category: item.category, fabrication_detected: false, full_pass: false, question_id: item.id, repetition: index + 1, suite_version: suite.version }))));
  const passing = summarizeProgramRecords(program, records).candidates["candidate-a"];
  assert.deepEqual(program.candidates, ["luna-low", "luna-medium", "gemini-3.6-flash"]);
  assert.equal(schedule.length, 990);
  assert.equal(program.suites[0].requirement, "zero_fabrications");
  assert.equal(passing.gate.passed, true);
  records[0].fabrication_detected = true;
  assert.equal(summarizeProgramRecords(program, records).candidates["candidate-a"].gate.passed, false);
});

test("keeps category results, macro-weights scored suites equally, and fails closed on a canary miss", () => {
  const program = loadProgram();
  const records = program.suites.flatMap((suite) =>
    suite.cases.flatMap((testCase) =>
      Array.from({ length: program.repetitions }, (_, index) => ({
        candidate: "candidate-a",
        category: testCase.category,
        full_pass: true,
        question_id: testCase.id,
        repetition: index + 1,
        suite_version: suite.version,
      })),
    ),
  );
  const passing = summarizeProgramRecords(program, records).candidates["candidate-a"];

  assert.equal(passing.gate.full_passes, 18);
  assert.equal(passing.gate.total, 18);
  assert.equal(passing.gate.passed, true);
  assert.equal(passing.weighted_full_pass_rate, 1);
  assert.deepEqual(Object.keys(passing.scored_suites["holdout-v3"].categories).sort(), [
    "difficult",
    "facts",
    "relationships",
    "safety",
  ]);
  assert.deepEqual(Object.keys(passing.scored_suites["search-v2"].categories).sort(), [
    "ambiguity",
    "combined",
    "no-match",
    "pagination",
    "physical",
    "subjective",
  ]);

  records[0].full_pass = false;
  const failing = summarizeProgramRecords(program, records).candidates["candidate-a"];
  assert.equal(failing.gate.full_passes, 17);
  assert.equal(failing.gate.passed, false);
  assert.equal(failing.weighted_full_pass_rate, null);
});

test("rejects duplicate questions across constituent suites", () => {
  const holdout = loadSuite(source("evaluation/suites/holdout-v3.json"));
  const search = loadSearchSuite(source("evaluation/suites/search-v2.json"));
  const canary = loadSuite(source("evaluation/suites/grounding-canary-v1.json"));
  search.cases[0].question = holdout.cases[0].question;

  assert.throws(
    () =>
      loadEvaluationProgram(source("evaluation/programs/comprehensive-v1.json"), [
        holdout,
        search,
        canary,
      ]),
    /Program questions must be unique/,
  );
});
