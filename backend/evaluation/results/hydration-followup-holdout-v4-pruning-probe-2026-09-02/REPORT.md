# Ask Pokedexer hydration-pruning follow-up probe

## Recommendation

**GO for the full frozen-v3 evaluation.** All 28 scheduled executions completed successfully and received full passes under the unchanged `holdout-v4` / `phrase-alias-v1` scorer. The three Levitate validation failures from the preceding probe did not recur: all four Levitate records returned every required ID, and one execution recorded the conservative removal of Pokémon ID 10167 in `pruned_hydration_ids` rather than rejecting the answer.

The Zubat and blue-crocodile controls were all factually correct in this run, so the live probe did not exercise the conditional “a substantive failure remains a failure” case. This is not a blocking signal because pruning changes only hydration arrays, the regression test asserts unchanged answer text, and factual scoring remains independent from hydration scoring.

## Frozen inputs

- Base Git HEAD: `c74db168e7c005b9716d10cd574a9809826bfe76`
- Backend state: local uncommitted hydration resolution and pruning fixes
- Backend content SHA-256: `eb4ed119bdf8bfed13774aad9206b766b66d5a04415cdb1372f5261c89a1438b`
- Orchestrator SHA-256: `5c1baf549d380d0612b8210eb2ef8ddf48f9c845f5e1bdd22c47a5b4e2d58ab6`
- Suite/scorer: `holdout-v4` / `phrase-alias-v1`
- Suite SHA-256: `734c5c4aad1c1d4b043e0e7f80c4f7b0618c35f6042bab02cd5c019874292ae0`
- Scorer SHA-256: `bd22ebe83d02f25d46ca159a30caf5d213601f00afaecb26d746f983272895ab`
- Schema SHA-256: `93ea4e5e0131cd56202fc268723792e720f9279f2a8b5f5d70d5b864b4e97015`
- Seed: `ask-pokedexer-hydration-followup-v1-seed`
- Models/repetitions: Luna Low and Gemini 3.7 Flash, two repetitions each
- Scheduled/executed: 28/28 (hard cap 32)
- Schedule SHA-256: `d5fe5958e2aa895a2c4b1c13ce5bd733ea819243eede869884c27416761f8109`
- Preflight SHA-256: `ad076a4e169f8602f32fd36378e2e9d994335c37a6add528645bc8ab223ac9ac`
- Preflight: validation-only, zero paid executions, 28 schedule entries

The seven cases, order, candidates, repetitions, seed, scorer, and gate exactly match the preceding fixed probe.

## Scores

| Model | Availability | Full | Factual | Hydration | Evidence | Provider failures | Model failures |
|---|---:|---:|---:|---:|---:|---:|---:|
| Luna Low | 14/14 | 14/14 | 14/14 | 14/14 | 14/14 | 0 | 0 |
| Gemini 3.7 Flash | 14/14 | 14/14 | 14/14 | 14/14 | 14/14 | 0 | 0 |
| Combined | 28/28 | 28/28 | 28/28 | 28/28 | 28/28 | 0 | 0 |

All 12 hydration-sensitive executions were factually correct and contained every required evidence-verified ID. There were zero terminal provider, PokéAPI, model-validation, or evaluator failures. Fourteen intermediate GraphQL policy/schema rejections were returned to the models and recovered within the existing budget; no execution exceeded three GraphQL attempts.

## Per-record hydration

`P` means Pokémon and `A` means ability. Item and move arrays were empty throughout. Extras are relative to holdout-v4 requirements; all retained extras were evidence-verified and named in the answer.

| # | Model / rep | Case | Required → returned; extras or pruning | Score | ms |
|---:|---|---|---|---|---:|
| 1 | Luna / 1 | Zubat | P[41,42] → P[41,42,169]; extra P169 Crobat | full | 16290 |
| 2 | Gemini / 1 | Zubat | P[41,42] → P[41,42,169]; extra P169 Crobat | full | 7306 |
| 3 | Gemini / 2 | starters | P[3,6] → P[3,6,9]; extra P9 Blastoise | full | 7392 |
| 4 | Luna / 2 | starters | P[3,6] → P[3,6,9]; extra P9 Blastoise | full | 6993 |
| 5 | Luna / 1 | Intimidate list | P[23,24,58,59,128,130] A[22] → exact | full | 7880 |
| 6 | Gemini / 1 | Intimidate list | P[23,24,58,59,128,130] A[22] → exact | full | 8230 |
| 7 | Luna / 1 | Shedinja | P[292] A[25] → exact | full | 12379 |
| 8 | Gemini / 1 | Shedinja | P[292] A[25] → exact | full | 6810 |
| 9 | Luna / 1 | Levitate | P[92,93,109,110] A[26] → exact | full | 15828 |
| 10 | Gemini / 1 | Levitate | P[92,93,109,110] A[26] → plus P10167 Galarian Weezing | full | 9510 |
| 11 | Luna / 1 | starters | P[3,6] → P[3,6,9]; extra P9 Blastoise | full | 7417 |
| 12 | Gemini / 1 | starters | P[3,6] → P[3,6,9]; extra P9 Blastoise | full | 5056 |
| 13 | Luna / 1 | blue crocodile | P[160] → P[158,160]; extra P158 Totodile | full | 19302 |
| 14 | Gemini / 1 | blue crocodile | P[160] → P[158,159,160]; extras P158 Totodile, P159 Croconaw | full | 7810 |
| 15 | Luna / 2 | Shedinja | P[292] A[25] → exact | full | 9502 |
| 16 | Gemini / 2 | Shedinja | P[292] A[25] → exact | full | 9974 |
| 17 | Gemini / 2 | Levitate | P[92,93,109,110] A[26] → exact; diagnostic pruned P10167 | full | 9703 |
| 18 | Luna / 2 | Levitate | P[92,93,109,110] A[26] → exact | full | 11310 |
| 19 | Gemini / 2 | Intimidate fact | A[22] → exact; one call/attempt, no resolver | full | 5364 |
| 20 | Luna / 2 | Intimidate fact | A[22] → exact; one call/attempt, no resolver | full | 10111 |
| 21 | Luna / 2 | Intimidate list | P[23,24,58,59,128,130] A[22] → exact | full | 14408 |
| 22 | Gemini / 2 | Intimidate list | P[23,24,58,59,128,130] A[22] → exact | full | 8521 |
| 23 | Gemini / 2 | blue crocodile | P[160] → P[158,160,159]; extras P158 Totodile, P159 Croconaw | full | 13896 |
| 24 | Luna / 2 | blue crocodile | P[160] → P[158,160]; extra P158 Totodile | full | 24969 |
| 25 | Gemini / 2 | Zubat | P[41,42] → P[41,42,169]; extra P169 Crobat | full | 6929 |
| 26 | Luna / 2 | Zubat | P[41,42] → exact | full | 9612 |
| 27 | Gemini / 1 | Intimidate fact | A[22] → exact; one call/attempt, no resolver | full | 6059 |
| 28 | Luna / 1 | Intimidate fact | A[22] → exact; one call/attempt, no resolver | full | 10448 |

Record 17 is the only pruning diagnostic: P10167 was removed while all required IDs and the answer text were preserved. The answer used the display wording “Galarian Weezing,” while the conservative whole-name check can receive canonical `weezing-galar` metadata. This optional form ID does not affect the required Weezing result card (P110) or any score, but it documents the current form-name conservatism.

Exact answers, hydration arrays, traces, metrics, diagnostics, and scores are preserved in `records.jsonl`.

## Latency, cost, and consistency

| Model | Mean | p50 | p95 | Max | Estimated cost | Cost complete |
|---|---:|---:|---:|---:|---:|:---:|
| Luna Low | 12604 ms | 10448 ms | 24969 ms | 24969 ms | $0.03739251 | 14/14 |
| Gemini 3.7 Flash | 8040 ms | 7392 ms | 13896 ms | 13896 ms | $0.10797675 | 14/14 |
| Combined | 10322 ms | 9510 ms | 19302 ms | 24969 ms | $0.14536926 | 28/28 |

All 28 executions completed under 30 seconds. Before/after PokéAPI consistency probes matched (`consistent: true`, SHA-256 `0c7768c3e53d7a3f54a650cd28e3ba4e607b08723ccf8f0ee9961adf7b3acdb9`).

## Gate verdict

| Criterion | Verdict | Evidence |
|---|---|---|
| Correct hydration-sensitive answers contain required IDs | PASS | 12/12 factual responses |
| Complete Intimidate control avoids resolver and extra attempt | PASS | 4/4, each exactly one call/attempt |
| No unrelated/supporting IDs leak | PASS | 28/28; retained extras were verified and answer-referenced; one conservative prune was diagnosed |
| Substantive factual failure remains a failure | NOT EXERCISED | All eight Zubat/blue-crocodile controls were factually correct; source and unit tests establish that pruning does not alter answer text |
| No provider/model failures | PASS | zero terminal failures of any class |
| Every execution under 30 seconds | PASS | 28/28; maximum 24969 ms |

Overall gate: **PASS**, with the substantive-failure condition untriggered rather than contradicted.

## Artifact integrity

- All committed historical evaluation artifacts remain byte-for-byte unchanged.
- Both preceding hydration probe directories remain byte-for-byte unchanged.
- This paid repeat is isolated in the new uncommitted `hydration-followup-holdout-v4-pruning-probe-2026-09-02` directory.
- No source, suite, scorer, or schedule changed after preflight.
