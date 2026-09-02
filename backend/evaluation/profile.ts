import { createHash } from "node:crypto";

import { EXECUTION_PROFILE } from "../src/execution-profile.js";

export const EVALUATION_PROFILE = EXECUTION_PROFILE;

export const EVALUATION_CANDIDATES = {
  "luna-low": {
    model: "gpt-5.6-luna",
    provider: "openai",
    reasoning: "low",
  },
  "luna-medium": {
    model: "gpt-5.6-luna",
    provider: "openai",
    reasoning: "medium",
  },
  "gemini-3.7-flash": {
    model: "gemini-3.7-flash",
    provider: "gemini",
    thinking: "LOW",
  },
  "gemini-3.6-flash": {
    model: "gemini-3.6-flash",
    provider: "gemini",
    thinking: "LOW",
  },
} as const;

export const EVALUATION_PROBE_CANDIDATES = {
  "glm-5.3-flash": {
    model: "z-ai/glm-5.3-flash",
    provider: "openrouter",
    reasoning: "low",
  },
  "qwen-3.8-flash": {
    model: "qwen/qwen3.8-flash",
    provider: "openrouter",
    reasoning: "low",
  },
} as const;

export const EVALUATION_MODEL_CONFIGS = {
  ...EVALUATION_CANDIDATES,
  ...EVALUATION_PROBE_CANDIDATES,
} as const;

export type EvaluationCandidateId = keyof typeof EVALUATION_MODEL_CONFIGS;

function sha256(value: string): string {
  return createHash("sha256").update(value).digest("hex");
}

export function createRunManifest(input: {
  candidateIds?: string[];
  implementationSources: Array<{ path: string; source: string }>;
  liveDataSha256?: string;
  questionIds?: string[];
  repetitions: number;
  schemaSource: string;
  seed: string;
  suiteSource: string;
}) {
  const sources = [...input.implementationSources].sort((left, right) =>
    left.path.localeCompare(right.path),
  );
  const sourceSha256 = Object.fromEntries(
    sources.map(({ path, source }) => [path, sha256(source)]),
  );
  const backendSource = sources
    .map(({ path, source }) => `${path}\0${Buffer.byteLength(source)}\0${source}`)
    .join("\0");
  return {
    backend_sha256: sha256(backendSource),
    candidates: input.candidateIds ?? Object.keys(EVALUATION_CANDIDATES),
    live_data_sha256: input.liveDataSha256 ?? null,
    profile: EVALUATION_PROFILE,
    question_ids: input.questionIds ?? null,
    repetitions: input.repetitions,
    schema_sha256: sha256(input.schemaSource),
    seed: input.seed,
    source_sha256: sourceSha256,
    suite_sha256: sha256(input.suiteSource),
  };
}
