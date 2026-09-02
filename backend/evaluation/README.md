# Evaluation versioning

Natural-language suites declare the scorer contract with `score_version`:

- `phrase-alias-v1` uses the original normalized phrase matching. Suites that omit
  `score_version` load as v1 so every historical holdout, canary, record, and score
  keeps its original interpretation.
- `semantic-alias-v2` additionally canonicalizes equivalent `cannot`/`can't`/`can’t`
  wording and recognizes a declared numeric metric and direction within one local
  phrase. For example, an expected accuracy decrease of 20 accepts “a loss of 20
  base accuracy,” but not “a gain of 20 base accuracy.” It is deliberately not an
  unordered bag-of-words scorer.

`holdout-v5.json` is the first suite that opts into `semantic-alias-v2`. Its cases
are unchanged from `holdout-v4.json`; only the suite and scorer versions advance.
The default holdout runner and `comprehensive-v3` continue to use `holdout-v4` and
the v1 scorer. A future evaluation program must name `holdout-v5` explicitly before
this behavior contributes to a new model comparison.

Do not use a newer scorer to overwrite committed evaluation records or summaries.
Any future rescore must write separate output and retain each record's
`original_evaluation` field.
