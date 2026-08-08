# Android visual baselines

These images are captured from the last known-good Android revision with:

```bash
scripts/android-regression capture-baseline <git-revision>
```

The command records the full commit hash in `source-revision.txt` and the tool/device configuration
in `environment.txt`. Baselines must be captured on the device configured by `EW_DEVICE`; the
default is Pixel 7 with Android 16 (API 36). Use the same device configuration for comparisons.

Review image changes before replacing a baseline. Never regenerate baselines from the revision
under test merely to make a failing regression pass.

The journeys intentionally exercise the app's live network-backed data loading. Upstream service
availability or content changes can therefore affect a run even when the app revision is unchanged.
