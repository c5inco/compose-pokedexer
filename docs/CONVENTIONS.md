# Conventions

## Code Style

- **Kotlin**: Follow standard Kotlin style (ktfmt `kotlinLangStyle`).
- **Compose**: Use standard Composable naming (`PascalCase`).
- **Formatting**: Always run `./gradlew ktfmtFormat` before committing.

## Key Patterns

### Dependency Injection (Metro)
- `ApplicationGraph` defines the DI graph with `@DependencyGraph(AppScope::class)`.
- ViewModels needing runtime params use `@AssistedInject` + `@AssistedFactory`.
- `metroViewModel()` composable helper for Compose integration.

### Navigation (Android)
- Type-safe routes via `sealed interface Screen : NavKey` with `@Serializable`.
- `NavDisplay` with `rememberNavBackStack` for stack management.
- Per-route `transitionSpec` (SharedXAxis for lateral, SharedZAxis for depth).

### Repository Pattern
- Repository interfaces defined in `shared/` (`PokemonRepository`, `MovesRepository`, etc.).
- Remote implementations combine Apollo GraphQL with Room local caching.
- Flow-based APIs for reactive UI updates.

### Search
- `HomeViewModel` uses `TextFieldState` with `snapshotFlow` and 200ms debounce.
- Combines results from Pokemon, Moves, and Items repositories.

### Theming
- **Android**: Material 3 color scheme generated from seed colors via Material Kolor.
- **iOS**: `PokemonTypeTheme` maps types to colors and SF Symbols.
- **Shared**: `ThemeGenerator` provides cross-platform seed colors (`PokemonTypeSeeds`).

### KMP Interop (iOS)
- `PokedexerSDK` in `iosMain` is the entry point for iOS.
- `PokedexerSDKWrapper` singleton access with initialization state.
- SKIE automatically converts Kotlin `Flow` to Swift `AsyncSequence`.
- `FlowPublisher` bridges remaining Kotlin Flow → Swift async patterns.

## File Placement

| Resource | Directory |
|----------|-----------|
| Composable screens | `app/ui/` |
| Composable components | `app/ui/common/` |
| ViewModels | `app/ui/.../` (alongside their screens) |
| Navigation keys | `app/ui/navigation/PokedexerDestinations.kt` |
| Room models | `shared/src/commonMain/kotlin/des/c5inco/pokedexer/shared/model/` |
| Repositories | `shared/src/commonMain/kotlin/des/c5inco/pokedexer/shared/data/.../` |

## Resource Management

- **Android**: Use `R.string.*` and `R.drawable.*`.
- **KMP Shared**: Use platform-agnostic models for cross-platform availability.
- **iOS**: Map KMP types to native SwiftUI components via SKIE.

## Git Policy

**CRITICAL**: Always ask for explicit permission before running git commit, git push, git rebase, or any other command that makes permanent changes to the repository.

- **Branching**: Use feature-specific branches.
- **Commit Messages**: Clear and concise, describing the *what* and *why*.
- **Pre-Push**: Run `./gradlew check` to ensure all quality checks pass.
- **Worktrees**: Use git worktrees for task isolation (optional but recommended).
