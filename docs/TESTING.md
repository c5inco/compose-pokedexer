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

## Quality Invariants

- **Failing tests block PRs**: Every PR must pass all tests.
- **TDD Requirement**: New features must have corresponding tests written before implementation.
- **Coverage**: Focus on testing logic-heavy repositories and critical UI interactions.
