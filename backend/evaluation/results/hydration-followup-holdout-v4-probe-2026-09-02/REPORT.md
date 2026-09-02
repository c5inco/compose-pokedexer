# Ask Pokedexer hydration follow-up probe

## Recommendation

**NO-GO for the full frozen-v3 paid evaluation.** The probe passed hydration completeness for every factually correct hydration-sensitive response and leaked no unrelated IDs, but it failed two predeclared gates:

1. all four already-complete `facts-intimidate` controls invoked `Resolve answer-referenced entities`; and
2. Luna Low repetition 2 for Shedinja failed model validation with `Final response Pokémon ID 292 had no verified name for hydration`.

The substantive-failure guard is inconclusive: all eight Zubat/blue-crocodile controls were factually correct, so this sample contained no substantive miss with which to prove that hydration cannot mask a bad answer.

## Frozen inputs

- Local Git HEAD: `c74db168e7c005b9716d10cd574a9809826bfe76`
- Immutable backend content SHA-256: `1bfc8a587e467150a1b86f6ae4f99154520523c4aef421088cf120cf407a3999`
- Suite/scorer: `holdout-v4` / `phrase-alias-v1`
- Suite SHA-256: `734c5c4aad1c1d4b043e0e7f80c4f7b0618c35f6042bab02cd5c019874292ae0`
- Schema SHA-256: `93ea4e5e0131cd56202fc268723792e720f9279f2a8b5f5d70d5b864b4e97015`
- Seed: `ask-pokedexer-hydration-followup-v1-seed`
- Models: `luna-low`, `gemini-3.7-flash`
- Repetitions: 2
- Scheduled/executed: 28/28 (hard cap 32)
- Frozen schedule SHA-256: `26ab23159c0f54828349bf2894520d6f9db50e60ab426b070481d44d28a8f4ba`
- Zero-cost preflight SHA-256: `db0becc6eac331bf1acbf9e1523c00809e5170a5fb3278fa77f23de67c970acf`

The CLI rejected one syntactically invalid command before any provider call, manifest, or record was created. Before the first paid call, only the saved CLI spelling was corrected from unsupported aggregate flags to the repository's repeated singular flags. The models, cases, repetitions, seed, scorer, gate, and exact 28-entry shuffled schedule never changed.

## Cases

All move and item requirements are empty. `P` means Pokémon IDs and `A` means ability IDs.

| Case | Role | Exact question | Required |
|---|---|---|---|
| `facts-intimidate` | already-complete/no-extra-lookup control | What happens when a Pokémon with Intimidate enters battle? | A[22] |
| `relations-kanto-poison-flying` | substantive Zubat failure control | What does Zubat evolve into, and do they share the same types? | P[41,42] |
| `relations-kanto-intimidate` | hydration-sensitive | Which Pokémon from the original 151 can have Intimidate? | P[23,24,58,59,128,130] A[22] |
| `relations-kanto-starter-finals` | supporting/unrelated-evidence leakage control | Which final evolutions of the three Kanto starters have two types, and what are those types? | P[3,6] |
| `relations-kanto-levitate` | hydration-sensitive | Which first-generation Pokémon can currently have Levitate? | P[92,93,109,110] A[26] |
| `difficult-blue-crocodile` | substantive answer-failure control | What does the little blue crocodile starter eventually evolve into, and what type is the final form? | P[160] |
| `difficult-shedinja-protection` | hydration-sensitive | What is the ability that stops most damaging moves from hurting Shedinja, and how does it work? | P[292] A[25] |

## Scores

| Model | Availability | Full | Factual | Hydration | Evidence | Provider failures | Model failures |
|---|---:|---:|---:|---:|---:|---:|---:|
| Luna Low | 13/14 (92.86%) | 13/14 (92.86%) | 13/14 (92.86%) | 13/14 (92.86%) | 13/14 (92.86%) | 0 | 1 |
| Gemini 3.7 Flash | 14/14 (100%) | 14/14 (100%) | 14/14 (100%) | 14/14 (100%) | 14/14 (100%) | 0 | 0 |
| Combined | 27/28 (96.43%) | 27/28 (96.43%) | 27/28 (96.43%) | 27/28 (96.43%) | 27/28 (96.43%) | 0 | 1 |

Among the 12 hydration-sensitive executions, 11 were factually correct and all 11 contained every required verified ID. The remaining execution failed before returning a response.

## Per-record results in frozen schedule order

`Score` is availability/factual/hydration/evidence/full. `Resolver` reports a `Resolve answer-referenced entities` query trace. Extras are relative to frozen holdout-v4 requirements; every successful extra was evidence-verified and explicitly named in the answer.

| # | Model / rep | Case | Required → returned; extras | Score | Resolver | ms | Estimated USD | Answer or error |
|---:|---|---|---|---|:---:|---:|---:|---|
| 1 | Luna / 1 | `relations-kanto-poison-flying` | P[41,42] → P[41,42]; none | 1/1/1/1/1 | no | 16382 | 0.00280026 | Zubat evolves into Golbat. They share the same types: Poison and Flying. |
| 2 | Gemini / 1 | `relations-kanto-poison-flying` | P[41,42] → P[41,42,169]; extra P[169] Crobat | 1/1/1/1/1 | no | 12494 | 0.01236000 | Zubat evolves into Golbat, which evolves into Crobat. Yes, they all share the exact same dual typing: Poison and Flying. |
| 3 | Gemini / 2 | `relations-kanto-starter-finals` | P[3,6] → P[3,6,9]; extra P[9] Blastoise | 1/1/1/1/1 | yes | 7414 | 0.00579675 | Among the final evolutions of the three Kanto starters, Venusaur and Charizard have two types: Venusaur is Grass/Poison and Charizard is Fire/Flying. Blastoise is pure Water. |
| 4 | Luna / 2 | `relations-kanto-starter-finals` | P[3,6] → P[3,6,9]; extra P[9] Blastoise | 1/1/1/1/1 | no | 10419 | 0.00183481 | Venusaur (ID 3) has Grass/Poison types, and Charizard (ID 6) has Fire/Flying types. Blastoise (ID 9) has only Water typing. |
| 5 | Luna / 1 | `relations-kanto-intimidate` | P[23,24,58,59,128,130] A[22] → exact; none | 1/1/1/1/1 | yes | 20704 | 0.00525167 | The original 151 Pokémon that can have Intimidate are Ekans, Arbok, Growlithe, Arcanine, Tauros, and Gyarados. |
| 6 | Gemini / 1 | `relations-kanto-intimidate` | P[23,24,58,59,128,130] A[22] → exact; none | 1/1/1/1/1 | yes | 7834 | 0.00613125 | The Pokémon from the original 151 (Generation I) that can have the ability Intimidate are Ekans, Arbok, Growlithe, Arcanine, Tauros, and Gyarados. |
| 7 | Luna / 1 | `difficult-shedinja-protection` | P[292] A[25] → exact; none | 1/1/1/1/1 | no | 9716 | 0.00153166 | Shedinja’s ability is Wonder Guard. It only allows super-effective moves to hit Shedinja, so most damaging moves do not hurt it. |
| 8 | Gemini / 1 | `difficult-shedinja-protection` | P[292] A[25] → exact; none | 1/1/1/1/1 | no | 9070 | 0.00790050 | The Ability is Wonder Guard. It makes the Pokémon immune to all damaging moves that are not super effective against it. It does not protect against non-damaging moves, status conditions, weather damage, entry hazards, or other indirect damage. |
| 9 | Luna / 1 | `relations-kanto-levitate` | P[92,93,109,110] A[26] → exact; none | 1/1/1/1/1 | yes | 10334 | 0.00229591 | The first-generation Pokémon that can currently have Levitate are Gastly, Haunter, Koffing, and Weezing. In each case, Levitate is the default ability. |
| 10 | Gemini / 1 | `relations-kanto-levitate` | P[92,93,109,110] A[26] → P[92,93,109,110,10167] A[26]; extra P[10167] Galarian Weezing | 1/1/1/1/1 | yes | 15601 | 0.01407525 | The first-generation Pokémon that can currently have the Levitate ability are Gastly, Haunter, Koffing, and Weezing (including Galarian Weezing). |
| 11 | Luna / 1 | `relations-kanto-starter-finals` | P[3,6] → P[3,6,9]; extra P[9] Blastoise | 1/1/1/1/1 | no | 10096 | 0.00185386 | Venusaur (ID 3) has Grass/Poison typing, and Charizard (ID 6) has Fire/Flying typing. Blastoise (ID 9) has only Water typing, so it does not have two types. |
| 12 | Gemini / 1 | `relations-kanto-starter-finals` | P[3,6] → P[3,6,9]; extra P[9] Blastoise | 1/1/1/1/1 | yes | 9490 | 0.01030050 | Venusaur and Charizard have two types, while Blastoise has only one: Venusaur is Grass/Poison and Charizard is Fire/Flying. |
| 13 | Luna / 1 | `difficult-blue-crocodile` | P[160] → P[158,159,160]; extra P[158,159] Totodile/Croconaw | 1/1/1/1/1 | no | 18627 | 0.00375620 | The little blue crocodile starter is Totodile. It evolves into Croconaw and then Feraligatr, which is a Water-type Pokémon. |
| 14 | Gemini / 1 | `difficult-blue-crocodile` | P[160] → P[158,159,160]; extra P[158,159] Totodile/Croconaw | 1/1/1/1/1 | no | 7177 | 0.00569700 | Totodile evolves into Croconaw and eventually Feraligatr. Feraligatr is a pure Water-type Pokémon. |
| 15 | Luna / 2 | `difficult-shedinja-protection` | P[292] A[25] → no response; missing P[292] A[25] | 0/0/0/0/0 | no | 13233 | 0.00288648 | **Model validation failure:** Final response Pokémon ID 292 had no verified name for hydration. |
| 16 | Gemini / 2 | `difficult-shedinja-protection` | P[292] A[25] → exact; none | 1/1/1/1/1 | no | 15463 | 0.00560700 | Shedinja's ability is Wonder Guard. It makes the Pokémon immune to damaging moves that are not super effective against it, but not non-direct damage such as status, weather, or entry hazards. |
| 17 | Gemini / 2 | `relations-kanto-levitate` | P[92,93,109,110] A[26] → P[92,93,109,110,10167] A[26]; extra P[10167] Galarian Weezing | 1/1/1/1/1 | yes | 8383 | 0.00908025 | The Generation I Pokémon that can currently have Levitate are Gastly, Haunter, Koffing, Weezing, and Galarian Weezing. |
| 18 | Luna / 2 | `relations-kanto-levitate` | P[92,93,109,110] A[26] → exact; none | 1/1/1/1/1 | yes | 11039 | 0.00230951 | The first-generation Pokémon that can currently have Levitate are Gastly, Haunter, Koffing, and Weezing. Levitate is their non-hidden slot 1 ability. |
| 19 | Gemini / 2 | `facts-intimidate` | A[22] → exact; none | 1/1/1/1/1 | yes | 6844 | 0.00572475 | Intimidate lowers the opposing Pokémon's Attack by one stage when its user enters battle. |
| 20 | Luna / 2 | `facts-intimidate` | A[22] → exact; none | 1/1/1/1/1 | yes | 9622 | 0.00175606 | Intimidate lowers the opposing Pokémon’s Attack by one stage when its user enters battle. |
| 21 | Luna / 2 | `relations-kanto-intimidate` | P[23,24,58,59,128,130] A[22] → exact; none | 1/1/1/1/1 | yes | 16804 | 0.00414021 | The original 151 Pokémon whose default form can have Intimidate are Ekans, Arbok, Growlithe, Arcanine, Tauros, and Gyarados. |
| 22 | Gemini / 2 | `relations-kanto-intimidate` | P[23,24,58,59,128,130] A[22] → exact; none | 1/1/1/1/1 | yes | 8391 | 0.00595800 | The Pokémon from the original 151 that can have Intimidate are Ekans, Arbok, Growlithe, Arcanine, Tauros, and Gyarados. |
| 23 | Gemini / 2 | `difficult-blue-crocodile` | P[160] → P[158,159,160]; extra P[158,159] Totodile/Croconaw | 1/1/1/1/1 | no | 10579 | 0.00917550 | Totodile eventually evolves into Feraligatr via Croconaw. Feraligatr is a pure Water-type Pokémon. |
| 24 | Luna / 2 | `difficult-blue-crocodile` | P[160] → P[158,160]; extra P[158] Totodile | 1/1/1/1/1 | no | 12738 | 0.00177521 | The little blue crocodile starter is Totodile. It eventually evolves into Feraligatr, which is a Water-type Pokémon. |
| 25 | Gemini / 2 | `relations-kanto-poison-flying` | P[41,42] → P[41,42,169]; extra P[169] Crobat | 1/1/1/1/1 | no | 14283 | 0.01256775 | Zubat evolves into Golbat, which further evolves into Crobat. They all share Poison/Flying typing. |
| 26 | Luna / 2 | `relations-kanto-poison-flying` | P[41,42] → P[41,42,169]; extra P[169] Crobat | 1/1/1/1/1 | no | 11298 | 0.00264613 | Zubat evolves into Golbat, which evolves into Crobat. Zubat, Golbat, and Crobat all share Poison/Flying typing. |
| 27 | Gemini / 1 | `facts-intimidate` | A[22] → exact; none | 1/1/1/1/1 | yes | 6687 | 0.00531900 | Intimidate lowers the Attack of opposing Pokémon by one stage when its user enters battle. |
| 28 | Luna / 1 | `facts-intimidate` | A[22] → exact; none | 1/1/1/1/1 | yes | 11200 | 0.00162305 | Intimidate lowers opposing Pokémon’s Attack by one stage when its user enters battle; it has no effect on an opponent behind a Substitute. |

No move or item IDs were returned. No required IDs were missing from any successful response.

## Extra-ID and lookup findings

- No unrelated or unmentioned hydration IDs leaked. In particular, starter-family supporting evidence IDs 1, 2, 4, and 5 never appeared.
- Every extra was within the allowed two-ID budget, evidence-verified, and explicitly answer-referenced: Crobat 169, Blastoise 9, Galarian Weezing 10167, Totodile 158, and Croconaw 159.
- Blastoise 9 appeared in all four starter controls because every answer explicitly mentioned Blastoise as the single-typed exclusion. This is an allowed answer-referenced extra, not a copy of unrelated evidence.
- All four `facts-intimidate` controls included the correct ability ID 22, but all four also had a resolver trace. Two performed infrastructure lookups; the later two were cache hits with zero infrastructure attempts. This still fails the predeclared “does not issue a resolver lookup” criterion.

## Latency, cost, failures, and consistency

| Model | Mean | p50 | p95 | Max | All under 30s | Estimated cost | Cost complete |
|---|---:|---:|---:|---:|:---:|---:|:---:|
| Luna Low | 13015 ms | 11200 ms | 20704 ms | 20704 ms | yes | $0.03646102 | 14/14 |
| Gemini 3.7 Flash | 9979 ms | 8391 ms | 15601 ms | 15601 ms | yes | $0.11569350 | 14/14 |
| Combined | — | — | — | 20704 ms | yes (28/28) | $0.15215452 | 28/28 |

There were zero provider and PokéAPI failures. There was one model-validation failure (record 15). Costs are runner estimates, not invoice totals. Before/after PokéAPI consistency probes matched (`consistent: true`, SHA-256 `0c7768c3e53d7a3f54a650cd28e3ba4e607b08723ccf8f0ee9961adf7b3acdb9`).

All four Zubat and all four blue-crocodile records were factually correct under frozen `phrase-alias-v1`. Hydration did not mask a substantive failure in observed records, but the intended masking guard remains inconclusive because neither control reproduced its historical miss.

## Gate verdict

| Predeclared criterion | Verdict | Evidence |
|---|---|---|
| All factually correct hydration-sensitive answers contain required verified IDs | PASS | 11/11; the twelfth scheduled response failed validation |
| Complete control does not issue answer-entity resolution lookup | **FAIL** | 0/4 avoided the resolver trace |
| No unrelated/supporting evidence IDs leak | PASS | No unmentioned IDs; no IDs 1,2,4,5; all extras answer-referenced and verified |
| Substantive factual failure stays a failure | INCONCLUSIVE | 0/8 substantive controls failed factually |
| No provider/model execution failure | **FAIL** | 0 provider failures, 1 Luna model-validation failure |
| Every execution under 30 seconds | PASS | 28/28; maximum 20704 ms |

The smallest next step is to fix the false-positive resolver invocation and the verified-ID-without-name validation path, add targeted regressions, and repeat this same bounded probe before authorizing a full paid frozen-v3 evaluation.

## Artifact integrity

- The 15 previously committed evaluation files all passed the pre-probe SHA-256 manifest after this run and remain byte-for-byte unchanged.
- This probe lives only in the new uncommitted `hydration-followup-holdout-v4-probe-2026-09-02` results directory.
- Raw `manifest.json`, `records.jsonl`, `summary.json`, and `data-consistency.json` are preserved unchanged from the runner.
