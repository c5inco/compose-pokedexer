# Ask Pokedexer comprehensive-v3 hydration follow-up evaluation

## Recommendation

**Keep Luna Low as the launch-default recommendation.** Both candidates passed the frozen `comprehensive-v3` canary gate and scored above 97.6%. Gemini 3.7 Flash led the weighted score by only 0.44 percentage points (98.08% versus 97.64%), but cost 3.35× as much and had three terminal model-validation failures versus none for Luna. For a public product where provider spend needs a durable ceiling, Luna's materially lower cost and clean completion record outweigh Gemini's small score and latency advantages.

Gemini remains the better option if product priorities shift toward response speed: its overall mean was 5.75 seconds versus Luna's 9.24 seconds, and every Gemini execution completed under 30 seconds. Luna exceeded 30 seconds four times. This is a product tradeoff, not a new blocker in the frozen program.

## Frozen inputs and execution

- Program: `comprehensive-v3`, five repetitions, seed `ask-pokedexer-eval-v4-seed`
- Candidate override: Luna Low and Gemini 3.7 Flash, matching the preceding final comparison
- Scheduled/executed: 660/660 paid executions
  - `grounding-canary-v2`: 60, gate on zero fabrications, `phrase-alias-v1`
  - `holdout-v4`: 400, 50% program weight with equal category weighting, `phrase-alias-v1`
  - `search-v3`: 200, 50% program weight with equal category weighting, `canonical-predicate-v2`
- Base Git HEAD: `9defbd9c99ad3c23697d75bdea8509a6abdf8725`
- Backend SHA-256: `eb4ed119bdf8bfed13774aad9206b766b66d5a04415cdb1372f5261c89a1438b`
- Orchestrator SHA-256: `5c1baf549d380d0612b8210eb2ef8ddf48f9c845f5e1bdd22c47a5b4e2d58ab6`
- Program SHA-256: `b4630d5bf8b132784c5fd52f32695ca2ae8b2911a5d6aafc7730ca04a154fe16`
- Frozen parent schedule SHA-256: `581a7ab37070cbd7bc0221db9a8e6074dd6796c6c84ba9a92fcc16b256662d53`
- Validation-only preflight SHA-256: `3f07a1179c194b992c2ed7ebd37adf45cd44d53a505a29c5ea252569d83c3759`
- Preflight completed with zero paid executions before the fixed schedule ran

This run intentionally preserves the committed scorer behavior. It does not use the prospective `phrase-alias-v2` scorer or `holdout-v5`, and it does not retroactively rescore any prior artifact.

## Program result

Both candidates passed the only program gate: zero fabricated canary facts. Scored-suite categories are macro-averaged, then the two suites are weighted equally.

| Model | Canary gate | Holdout-v4 macro | Search-v3 macro | Weighted score |
|---|---:|---:|---:|---:|
| Luna Low | PASS, 0 fabrications | 97.50% | 97.78% | **97.64%** |
| Gemini 3.7 Flash | PASS, 0 fabrications | 99.50% | 96.67% | **98.08%** |

Gemini's weighted lead is 0.44 percentage points. Raw full passes across the 300 scored executions were 293/300 for Luna and 296/300 for Gemini.

### Holdout-v4

| Model | Full | Availability | Factual | Hydration | Evidence | Terminal failures |
|---|---:|---:|---:|---:|---:|---:|
| Luna Low | 195/200 | 200/200 | 187/200 | 199/200 | 200/200 | 0 |
| Gemini 3.7 Flash | 199/200 | 199/200 | 174/200 | 199/200 | 199/200 | 1 model |

| Model | Facts | Relationships | Difficult | Safety |
|---|---:|---:|---:|---:|
| Luna Low | 50/50 | 50/50 | 49/50 | 46/50 |
| Gemini 3.7 Flash | 50/50 | 49/50 | 50/50 | 50/50 |

Luna's misses were one incorrect blue-crocodile answer and four safety-behavior misses. The incorrect answer remained a factual and hydration failure rather than being repaired by hydration completion. Gemini's single terminal failure returned unverified Pokémon ID 10167 for the Levitate case; strict validation correctly rejected it.

### Search-v3

| Model | Full | Ambiguity | Combined | No match | Pagination | Physical | Subjective | Terminal failures |
|---|---:|---:|---:|---:|---:|---:|---:|---:|
| Luna Low | 98/100 | 15/15 | 25/25 | 10/10 | 15/15 | 20/20 | 13/15 | 0 |
| Gemini 3.7 Flash | 97/100 | 15/15 | 25/25 | 10/10 | 15/15 | 20/20 | 12/15 | 2 model |

Luna's two misses were subjective-cute relevance misses. Gemini had one subjective-fluffy relevance miss and two terminal sleepy-opinion validations where Pokémon ID 143 lacked verified Pokémon-name metadata. These failures are preserved in the raw records and should be investigated before treating Gemini as a drop-in default.

### Canary

Gemini received 30/30 full passes. Luna received 29/30; its one non-full response recovered from two schema errors but could not establish the requested Speed comparison. It did not fabricate the answer, so Luna correctly passed the frozen zero-fabrication gate.

## Hydration behavior

- Holdout hydration passed 199/200 for both candidates.
- Five executions recorded conservative `pruned_hydration_ids` diagnostics before final output:
  - four Gemini Levitate executions pruned Pokémon ID 10167;
  - one Luna negated-color search pruned Pokémon IDs 10–15, 46, and 47.
- No diagnosed unmentioned supporting entity remained in a successful final hydration array.
- The remaining Gemini Levitate attempt supplied ID 10167 without verified evidence and was rejected, preserving fail-closed handling of genuinely unverified IDs.
- Luna's incorrect blue-crocodile answer remained incorrect and incomplete, confirming that hydration completion did not mask the substantive miss.

## Latency and cost

| Model | Executions | Mean | p50 | p95 | Max | Under 30 s | Estimated cost | Cost complete |
|---|---:|---:|---:|---:|---:|---:|---:|:---:|
| Luna Low | 330 | 9244 ms | 8434 ms | 20586 ms | 36740 ms | 326/330 | $0.49454509 | 330/330 |
| Gemini 3.7 Flash | 330 | 5746 ms | 5686 ms | 10219 ms | 20517 ms | 330/330 | $1.65903750 | 330/330 |

Combined estimated cost was **$2.15358259**, with complete cost data for all 660 executions. There were zero provider, PokéAPI, or evaluator terminal failures. Gemini had three model-validation failures; Luna had none.

| Stage | Luna cost | Gemini cost | Luna mean / p95 | Gemini mean / p95 |
|---|---:|---:|---:|---:|
| Canary | $0.05881515 | $0.19247700 | 12412 / 33389 ms | 7190 / 11681 ms |
| Holdout | $0.38134049 | $1.12408050 | 10833 / 21594 ms | 6577 / 10179 ms |
| Search | $0.05438945 | $0.34248000 | 5116 / 11901 ms | 3651 / 9909 ms |

## Integrity and reproducibility

- Canary, holdout, and search contain exactly 60, 400, and 200 records.
- Every stage manifest records backend SHA-256 `eb4ed119bdf8bfed13774aad9206b766b66d5a04415cdb1372f5261c89a1438b`.
- Holdout and search before/after PokéAPI snapshots match, both with SHA-256 `0c7768c3e53d7a3f54a650cd28e3ba4e607b08723ccf8f0ee9961adf7b3acdb9`.
- `program-summary.json` was produced with the repository's `summarizeProgramRecords` implementation over all 660 raw records.
- All frozen-input and prior-artifact integrity manifests verify byte-for-byte.
- This full evaluation is isolated in a new uncommitted results directory. No historical artifact was changed.
- No source, suite, scorer, or schedule changed after preflight. No push, PR, deployment, historical rescore, or artifact commit was performed.
