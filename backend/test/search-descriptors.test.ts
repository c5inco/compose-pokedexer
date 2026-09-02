import assert from "node:assert/strict";
import test from "node:test";

import { interpretSearchQuestion, interpretSearchDescriptors } from "../src/search-descriptors.js";

test("maps reviewed shape, color, type, height, and weight descriptors with units disclosed", () => {
  const result = interpretSearchDescriptors(["round", "red", "fire", "short", "heavy"]);

  assert.deepEqual(result.constraints, [
    { field: "shape", operator: "eq", value: "ball" },
    { field: "color", operator: "eq", value: "red" },
    { field: "type", operator: "eq", value: "fire" },
    { field: "height", operator: "lte", unit: "decimetres", value: 10 },
    { field: "weight", operator: "gte", unit: "hectograms", value: 1_000 },
  ]);
  assert.match(result.interpretations.find((item) => item.term === "round")?.disclosure ?? "", /ball/);
  assert.match(result.interpretations.find((item) => item.term === "short")?.disclosure ?? "", /10 decimetres \(1 metre\)/);
  assert.match(result.interpretations.find((item) => item.term === "heavy")?.disclosure ?? "", /1,000 hectograms \(100 kilograms\)/);
  assert.equal(result.status, "structured");
});

test("accepts all fourteen canonical PokéAPI shapes", () => {
  const shapes = [
    "ball",
    "squiggle",
    "fish",
    "arms",
    "blob",
    "upright",
    "legs",
    "quadruped",
    "wings",
    "tentacles",
    "heads",
    "humanoid",
    "bug-wings",
    "armor",
  ];
  const result = interpretSearchDescriptors(shapes);
  assert.deepEqual(
    result.constraints.map((constraint) => constraint.value),
    shapes,
  );
});

test("reports exclusive conflicts and ambiguous physical language instead of guessing", () => {
  const result = interpretSearchDescriptors(["round", "quadruped", "red", "blue", "small"]);

  assert.equal(result.status, "needs_clarification");
  assert.deepEqual(result.ambiguous_terms, [
    {
      reason: "Specify whether small refers to height, weight, or both, with a threshold.",
      term: "small",
    },
  ]);
  assert.deepEqual(result.conflicts.map((conflict) => conflict.field), ["shape", "color"]);
});

test("marks subjective or visual descriptors as requiring enrichment", () => {
  const result = interpretSearchDescriptors(["cute", "fluffy", "sleepy-looking"]);

  assert.equal(result.status, "requires_enrichment");
  assert.deepEqual(
    result.unsupported_terms.map((item) => item.term),
    ["cute", "fluffy", "sleepy-looking"],
  );
  assert.ok(result.unsupported_terms.every((item) => /semantic or visual enrichment/.test(item.reason)));
  assert.deepEqual(result.constraints, []);
});

test("extracts only reviewed whole-word descriptors from a natural-language search", () => {
  const result = interpretSearchQuestion("Find round, red Water Pokémon that are short, not sacred.");

  assert.deepEqual(result?.interpretations.map((item) => item.term), ["round", "red", "water", "short"]);
});

test("does not reinterpret ordinary entity questions as searches", () => {
  assert.equal(interpretSearchQuestion("What does Fire Punch do?"), null);
});

test("does not reinterpret a move name inside a Pokémon search as a type filter", () => {
  assert.equal(interpretSearchQuestion("Find Pokémon that learn Fire Punch."), null);
  assert.equal(interpretSearchQuestion("Which Pokémon can use Water Gun?"), null);
});

test("fails closed instead of turning a negated descriptor into a positive filter", () => {
  const result = interpretSearchQuestion("Find Pokémon that are not red.");

  assert.equal(result?.status, "needs_clarification");
  assert.deepEqual(result?.constraints, []);
  assert.match(result?.ambiguous_terms[0]?.reason ?? "", /negated descriptor/i);
  assert.equal(
    interpretSearchQuestion("Find Pokémon that aren't red.")?.status,
    "needs_clarification",
  );
});
