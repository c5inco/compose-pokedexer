# Ask Pokedexer fixed hydration follow-up probe

## Recommendation

**NO-GO for the full frozen-v3 paid evaluation.** The two targeted fixes worked in live execution:

- all four already-complete Intimidate controls returned ability ID 22 with one GraphQL request/attempt and no `Resolve answer-referenced entities` trace; and
- all four Shedinja controls returned Pokémon ID 292 and ability ID 25. Luna repetition 1 exercised the repaired path: its initial evidence had Wonder Guard but lacked Shedinja name metadata, then exact backend resolution of `shedinja` supplied `{id:292,name:"shedinja"}` before final validation.

However, three Levitate executions failed closed because the model returned verified Galarian Weezing ID 10167 without mentioning Galarian Weezing in the answer. The strict answer-reference rule prevented the supporting ID from leaking, but the predeclared no-model-failure gate failed. The substantive-failure control remains inconclusive because all eight Zubat/blue-crocodile answers were factually correct.

## Frozen inputs

- Backend content SHA-256: `c5c118b467c108dc3ed6ff20bfaa29ef62f4da8bbe2de73b2f3fcaecc17e4de9`
- Suite/scorer: `holdout-v4` / `phrase-alias-v1`
- Suite SHA-256: `734c5c4aad1c1d4b043e0e7f80c4f7b0618c35f6042bab02cd5c019874292ae0`
- Schema SHA-256: `93ea4e5e0131cd56202fc268723792e720f9279f2a8b5f5d70d5b864b4e97015`
- Seed: `ask-pokedexer-hydration-followup-v1-seed`
- Models/repetitions: Luna Low and Gemini 3.7 Flash, two repetitions each
- Scheduled/executed: 28/28 (hard cap 32)
- Schedule SHA-256: `756ecab227452f93da34ffedc80848db8f703c792d56acca8746a90a0340edf6`
- Preflight SHA-256: `a845844d9657a822df0c6d2f14c99df83f37164398803d39dc3dc5ecb54877b4`
- Preflight: validation-only, zero paid executions, 28 schedule entries

The schedule order, cases, candidates, repetitions, seed, scorer, and gate exactly match the original probe.

## Scores

| Model | Availability | Full | Factual | Hydration | Evidence | Provider failures | Model failures |
|---|---:|---:|---:|---:|---:|---:|---:|
| Luna Low | 13/14 (92.86%) | 13/14 | 13/14 | 13/14 | 13/14 | 0 | 1 |
| Gemini 3.7 Flash | 12/14 (85.71%) | 12/14 | 12/14 | 12/14 | 12/14 | 0 | 2 |
| Combined | 25/28 (89.29%) | 25/28 | 25/28 | 25/28 | 25/28 | 0 | 3 |

Among 12 hydration-sensitive executions, nine were factually correct and all nine contained every required verified ID. The other three failed before returning a response.

## Per-record hydration

`P` means Pokémon and `A` means ability. Move and item arrays were empty throughout. Extras are relative to holdout-v4 requirements; every successful extra was verified and explicitly answer-referenced.

| # | Model / rep | Case | Required → returned; extras | Result | ms |
|---:|---|---|---|---|---:|
| 1 | Luna / 1 | Zubat | P[41,42] → P[41,42,169]; extra Crobat 169 | full pass | 14401 |
| 2 | Gemini / 1 | Zubat | P[41,42] → P[41,42,169]; extra Crobat 169 | full pass | 7702 |
| 3 | Gemini / 2 | starters | P[3,6] → P[3,6,9]; extra Blastoise 9 | full pass | 8044 |
| 4 | Luna / 2 | starters | P[3,6] → P[3,6,9]; extra Blastoise 9 | full pass | 12726 |
| 5 | Luna / 1 | Intimidate list | P[23,24,58,59,128,130] A[22] → exact | full pass | 13219 |
| 6 | Gemini / 1 | Intimidate list | same → exact | full pass | 12173 |
| 7 | Luna / 1 | Shedinja | P[292] A[25] → exact | full pass; exact resolver exercised | 15395 |
| 8 | Gemini / 1 | Shedinja | P[292] A[25] → exact | full pass | 11765 |
| 9 | Luna / 1 | Levitate | P[92,93,109,110] A[26] → no response | fail closed on unmentioned P[10167] | 16519 |
| 10 | Gemini / 1 | Levitate | same → no response | fail closed on unmentioned P[10167] | 17132 |
| 11 | Luna / 1 | starters | P[3,6] → P[3,6,9]; extra Blastoise 9 | full pass | 10827 |
| 12 | Gemini / 1 | starters | same | full pass | 9958 |
| 13 | Luna / 1 | blue crocodile | P[160] → P[158,159,160]; extras Totodile/Croconaw | full pass | 7873 |
| 14 | Gemini / 1 | blue crocodile | same | full pass | 6528 |
| 15 | Luna / 2 | Shedinja | P[292] A[25] → exact | full pass | 16068 |
| 16 | Gemini / 2 | Shedinja | P[292] A[25] → exact | full pass | 14701 |
| 17 | Gemini / 2 | Levitate | P[92,93,109,110] A[26] → no response | fail closed on unmentioned P[10167] | 13845 |
| 18 | Luna / 2 | Levitate | P[92,93,109,110] A[26] → exact | full pass | 22461 |
| 19 | Gemini / 2 | Intimidate fact | A[22] → exact | full pass; 1 call/attempt, no resolver | 6294 |
| 20 | Luna / 2 | Intimidate fact | A[22] → exact | full pass; 1 call/attempt, no resolver | 8521 |
| 21 | Luna / 2 | Intimidate list | P[23,24,58,59,128,130] A[22] → exact | full pass | 15034 |
| 22 | Gemini / 2 | Intimidate list | same → exact | full pass | 8450 |
| 23 | Gemini / 2 | blue crocodile | P[160] → P[158,159,160]; extras Totodile/Croconaw | full pass | 6585 |
| 24 | Luna / 2 | blue crocodile | same | full pass | 17722 |
| 25 | Gemini / 2 | Zubat | P[41,42] → P[41,42,169]; extra Crobat 169 | full pass | 12824 |
| 26 | Luna / 2 | Zubat | P[41,42] → exact | full pass | 20332 |
| 27 | Gemini / 1 | Intimidate fact | A[22] → exact | full pass; 1 call/attempt, no resolver | 6609 |
| 28 | Luna / 1 | Intimidate fact | A[22] → exact | full pass; 1 call/attempt, no resolver | 9479 |

Exact answer strings, traces, metrics, and diagnostics are preserved in `records.jsonl`.

## Latency, cost, and consistency

| Model | Mean | p50 | p95 | Max | Estimated cost | Cost complete |
|---|---:|---:|---:|---:|---:|:---:|
| Luna Low | 14327 ms | 14401 ms | 22461 ms | 22461 ms | $0.04142613 | 14/14 |
| Gemini 3.7 Flash | 10186 ms | 8450 ms | 17132 ms | 17132 ms | $0.11712900 | 14/14 |
| Combined | — | — | — | 22461 ms | $0.15855513 | 28/28 |

All 28 executions completed under 30 seconds. There were zero provider or PokéAPI failures and three model-validation failures. Before/after PokéAPI consistency probes matched (`consistent: true`, SHA-256 `0c7768c3e53d7a3f54a650cd28e3ba4e607b08723ccf8f0ee9961adf7b3acdb9`).

## Gate verdict

| Criterion | Verdict | Evidence |
|---|---|---|
| Correct hydration-sensitive answers contain required IDs | PASS | 9/9 factual responses |
| Complete Intimidate control avoids resolver and extra attempt | PASS | 4/4, each exactly one call/attempt |
| No unrelated/supporting IDs leak | PASS, fail-closed | P[10167] was rejected in three records; no response leaked it |
| Substantive factual failure remains a failure | INCONCLUSIVE | 0/8 substantive controls failed factually |
| No provider/model failures | **FAIL** | zero provider failures, three model-validation failures |
| Every execution under 30 seconds | PASS | 28/28; maximum 22461 ms |

The next product/implementation decision is whether verified-but-unmentioned model IDs should continue to fail the whole response or be pruned before final validation. No further code change or paid execution is included in this probe.

## Artifact integrity

- All committed historical evaluation artifacts remain byte-for-byte unchanged.
- Every artifact from the first paid hydration probe remains byte-for-byte unchanged.
- This repeat is isolated in the new uncommitted `hydration-followup-holdout-v4-fixed-probe-2026-09-02` directory.
