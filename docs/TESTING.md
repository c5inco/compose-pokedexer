# Testing

## TDD Workflow (Red -> Green -> Refactor)

1. **Write the test**: Define the expected behavior.
2. **Run the test**: Verify it fails (Red).
3. **Implement**: Write the minimum code to make it pass.
4. **Re-run**: Verify it passes (Green).
5. **Refactor**: Clean up the code if necessary, keeping the test passing.

## Test Types

### Unit Tests (`app/src/test/` and `shared/src/commonTest/`)
- Test logic in repositories, data converters, and ViewModels.
- Use `kotlin.test` for platform-agnostic testing in `:shared`.
- Use JUnit 4 for Android-specific logic in `:app`.

### Instrumentation Tests (`app/src/androidTest/`)
- **Compose UI Tests**: Use `createAndroidComposeRule<MainActivity>()`.
- **Database Tests**: `PokemonDatabaseTest`, `MovesDatabaseTest` (Room DAO tests).
- **User Journeys**: `PokedexNavigationTest`, `MenuScreensJourneyTest`, `ScreenVerificationTest`, `GenerationFilterJourneyTest`.
- **Framework**: Use UI Automator for system-level interactions.

### Maestro Regression Tests (`.maestro/`)
- Exercise the installed Android app as a black-box through an Emulator.wtf remote device.
- Keep `EW_API_TOKEN` in an environment variable or Orb secret; never put it in the repository.
- Run `scripts/android-regression doctor` to check prerequisites.
- Run `scripts/android-regression test` to build, deploy, execute the suite, and stop the remote session.
- Run `scripts/android-regression start` for an interactive session, then use `deploy`, `shell`, `status`, and `stop` as needed.
- The harness pins Maestro 2.8.0 and ew-cli 1.4.1 so selector and screenshot behavior is reproducible.
- `.agents/setup` installs the pinned tools; `.agents/resume` restores their environment and verifies them without starting a billed remote session.
- The generation-loading journey verifies generations I through V using representative Pokédex entries and semantic readiness assertions.
- Visual comparisons use Pixel 7 and API 36 by default. The source revision for the checked-in screenshots is recorded in `.maestro/baselines/source-revision.txt`.
- Replace baselines only after reviewing the visual change and confirming that it is intentional.

### Benchmark Tests (`benchmark/`)
- `StartupBenchmark`: Cold/warm startup timing.
- `PokedexListScrollBenchmark`: List scroll performance.
- `DetailsBenchmark`: Details screen rendering.
- `BaselineProfileGenerator`: ART compilation baseline profiles.
- **Config**: Managed device (Pixel 4, API 31), 5 iterations, `CompilationMode.None`/`Partial`.

## Test Command Reference

| Command | Purpose |
|---------|---------|
| `./gradlew test` | Run all unit tests |
| `./gradlew connectedAndroidTest` | Run Android instrumented tests |
| `./gradlew :benchmark:pixel4Api31BenchmarkAndroidTest` | Run macro-benchmarks |
| `./gradlew check` | Run unit tests + linting (Detekt/ktfmt) |
| `scripts/android-regression test` | Run Android Maestro regression tests on Emulator.wtf |

## Quality Invariants

- **Failing tests block PRs**: Every PR must pass all tests.
- **TDD Requirement**: New features must have corresponding tests written before implementation.
- **Coverage**: Focus on testing logic-heavy repositories and critical UI interactions.
