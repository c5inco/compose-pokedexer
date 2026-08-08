# Conventions

## Code Style

- **Kotlin**: Follow standard Kotlin style (ktfmt `kotlinLangStyle`).
- **Compose**: Use standard Composable naming (`PascalCase`).
- **Formatting**: Run `./gradlew ktfmtFormat` before committing changes that include `.kt` or `.kts` files.

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
| Navigation keys | `app/ui/navigation/Screen.kt` |
| Room models | `shared/src/commonMain/kotlin/des/c5inco/pokedexer/shared/model/` |
| Repositories | `shared/src/commonMain/kotlin/des/c5inco/pokedexer/shared/data/.../` |

## Resource Management

- **Android**: Use `R.string.*` and `R.drawable.*`.
- **KMP Shared**: Use platform-agnostic models for cross-platform availability.
- **iOS**: Map KMP types to native SwiftUI components via SKIE.

## Git Policy

`AGENTS.md` is the source of truth for approval and worktree-isolation rules. Before any Git or GitHub operation, load
and follow `.agents/skills/git-github-ops/SKILL.md`; when creating or using a linked worktree, also load and follow
`.agents/skills/using-git-worktree/SKILL.md`.

- **Approval**: Read-only inspection is allowed without approval. Ask explicitly before any operation that persistently
  changes repository, worktree, branch, remote, issue, or pull-request state.
- **Branching**: Use descriptive, task-specific branch names such as `fix/search-race` or `feat/item-details`. Never
  push directly to the default branch unless the user explicitly requests it.
- **Commit Messages**: Explain what changed and why. Do not use typed Conventional Commit prefixes such as `feat:`,
  `fix:`, `docs:`, or `chore:`. Keep the subject imperative and at most 72 characters; wrap body lines at 72
  characters.
- **Pre-Commit**: If the change includes `.kt` or `.kts` files, run `./gradlew ktfmtFormat` and inspect the resulting
  diff. Run the targeted tests required by the TDD cycle for every behavioral change.
- **Pre-Push / PR**: Run `./gradlew check` and confirm it passes before pushing or opening a PR. The narrowly scoped
  pre-existing-failure exception in `AGENTS.md` applies only to non-code changes with targeted validation, a follow-up
  issue, and explicit user approval; the full check must still be run and reported.
- **Pull Requests**: Use a focused title and description, reference related issues (for example, `Fixes #27`), and add
  screenshots for UI changes.
- **Worktrees**: Offer a linked worktree before the first tracked edit when the session is still on the default branch
  in the main checkout. Use `.worktrees/`, which is gitignored, for project-local worktrees.
