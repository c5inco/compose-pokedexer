# AGENTS.md

This file provides guidance to AI code assistants when working with code in this repository.

## Build Commands

### Android
- **Build the project**: `./gradlew build`
- **Run tests**: `./gradlew test`
- **Run instrumentation tests**: `./gradlew connectedAndroidTest`
- **Build debug APK**: `./gradlew assembleDebug`
- **Build release APK**: `./gradlew assembleRelease`
- **Run benchmarks**: `./gradlew :benchmark:pixel4Api31BenchmarkAndroidTest`
- **Clean build**: `./gradlew clean`

### iOS
- **Open Xcode project**: `open iosApp/PokedexerApp.xcodeproj`
- **Build iOS framework**: `./gradlew :shared:linkReleaseFrameworkIosArm64` (or `IosSimulatorArm64`, `IosX64`)

## Architecture Overview

This is a **Kotlin Multiplatform (KMP)** Pokedex app with native **Android (Jetpack Compose)** and **iOS (SwiftUI)** frontends sharing a common data layer.

### Project Modules

```
settings.gradle modules: app, shared, benchmark
```

- **`:app`** - Android Jetpack Compose application
- **`:shared`** - KMP data layer (Room database, Apollo GraphQL, models, repositories)
- **`:benchmark`** - Android macro-benchmark tests
- **`iosApp/`** - Native SwiftUI iOS application (Xcode project, not a Gradle module)

### Technology Stack

**Shared (KMP)**
- **Database**: Room KMP (version 7, with expect/actual platform builders)
- **Network**: Apollo GraphQL client connecting to PokéAPI (`https://beta.pokeapi.co/graphql/v1beta`)
- **Models**: Shared data classes with Room `@Entity` annotations
- **Theming**: Material Kolor for cross-platform seed color generation

**Android**
- **UI Framework**: Jetpack Compose with Material 3
- **Architecture**: MVVM with Metro dependency injection
- **Navigation**: Compose Navigation 3 (`NavDisplay`, `rememberNavBackStack`)
- **Image Loading**: Coil (with GIF support)
- **Preferences**: Jetpack DataStore for favorites
- **Graphics**: `RuntimeShader` for gradient effects (API 33+), `androidx.graphics.shapes`

**iOS**
- **UI Framework**: SwiftUI (iOS 18.0+ deployment target)
- **Architecture**: MVVM with `ObservableObject` ViewModels
- **KMP Interop**: SKIE for Kotlin Flow → Swift `AsyncSequence` conversion
- **Theming**: Native iOS 18 `MeshGradient` API with `LinearGradient` fallback

### Build Configuration

| Setting | Value |
|---------|-------|
| `compileSdk` | 36 |
| `minSdk` | 28 |
| `jvmTarget` | 17 |
| `applicationId` | `des.c5inco.pokedexer` |
| iOS deployment target | 18.0 |
| Room database version | 7 |
| Room migration strategy | Destructive fallback (`dropAllTables=true`) |

### Key Gradle Plugins
- Kotlin Multiplatform, KSP, Apollo GraphQL, Room KMP, SKIE, Metro, Compose Compiler, Kotlin Serialization

## Project Structure

### Shared KMP Module (`shared/`)

```
shared/src/
├── commonMain/kotlin/des/c5inco/pokedexer/shared/
│   ├── data/
│   │   ├── PokemonDatabase.kt       # Room KMP database definition
│   │   ├── Result.kt                # Success/Failure sealed class
│   │   ├── Utils.kt
│   │   ├── pokemon/                  # PokemonDao, PokemonRepository, RemotePokemonRepository
│   │   ├── moves/                    # MovesDao, MovesRepository, RemoteMovesRepository
│   │   ├── items/                    # ItemsDao, ItemsRepository, ItemsRepositoryImpl
│   │   └── abilities/                # AbilitiesDao, AbilitiesRepository, AbilitiesRepositoryImpl
│   ├── model/
│   │   ├── Pokemon.kt               # @Entity with stats, types, evolutions, moves, abilities
│   │   ├── Move.kt, Item.kt, Ability.kt  # @Entity models
│   │   ├── Type.kt                   # 18 Pokemon types enum
│   │   ├── Generation.kt            # Generations I–IX enum
│   │   ├── Stats.kt, Evolution.kt   # Related data classes
│   │   └── PokemonMove.kt, PokemonAbility.kt
│   └── theme/
│       └── ThemeGenerator.kt         # Cross-platform Pokemon type color seeds
├── commonMain/graphql/               # Apollo .graphql query files
├── androidMain/                      # DatabaseBuilder.android.kt (expect/actual)
└── iosMain/
    ├── PokedexerSDK.kt               # iOS SDK entry point
    ├── data/DatabaseBuilder.ios.kt
    └── theme/ThemeGeneratorIOS.kt
```

### Android App (`app/`)

```
app/src/main/kotlin/des/c5inco/pokedexer/
├── MainActivity.kt                   # Single activity, edge-to-edge
├── PokedexerApp.kt                   # Root composable with NavDisplay
├── PokedexerApplication.kt           # Application class, Metro DI graph init
├── RootViewModel.kt
├── di/
│   ├── ApplicationGraph.kt           # Metro @DependencyGraph (database, network, repos, VMs)
│   └── MetroViewModel.kt             # metroViewModel() composable helper
├── data/
│   ├── pokemon/PokemonData.kt        # Sample/seed data
│   ├── moves/MovesData.kt
│   ├── items/ItemsData.kt
│   ├── abilities/AbilitiesData.kt
│   ├── preferences/UserPreferencesRepository.kt  # DataStore favorites
│   └── typechart/TypeEffectiveness.kt
├── model/
│   ├── PokemonExtensions.kt          # Pokemon.color(), formatting helpers
│   ├── MoveExtensions.kt             # Move.category(), Move.type()
│   └── TypeExtensions.kt
├── ui/
│   ├── home/                         # HomeScreen, HomeViewModel (search with 200ms debounce)
│   │   └── appbar/                   # MainAppBar, search components, menu
│   ├── pokedex/
│   │   ├── PokedexScreen.kt          # 2-column grid with mesh gradient cards
│   │   ├── PokedexViewModel.kt       # Filtering by generation, type, favorites
│   │   ├── PokemonDetailsScreen.kt   # Detail view with tabbed sections
│   │   ├── PokemonDetailsViewModel.kt  # Assisted injection (requires pokemonId)
│   │   ├── PokemonPager.kt           # Horizontal pager between Pokemon
│   │   └── section/                  # AboutSection, BaseStatsSection, EvolutionsSection, MovesSection
│   ├── moves/                        # MovesListScreen, MovesListViewModel
│   ├── items/                        # ItemsScreen, ItemsViewModel
│   ├── typechart/                    # TypeChartScreen
│   ├── common/                       # Shared UI components (see below)
│   ├── navigation/
│   │   └── PokedexerDestinations.kt  # Sealed interface Screen : NavKey
│   └── theme/
│       ├── AppTheme.kt               # Material 3 with Material Kolor
│       ├── AppColors.kt, AppTypography.kt, AppShapes.kt
│       └── PokemonTypesTheme.kt      # Dynamic type-based theming
```

### iOS App (`iosApp/`)

```
iosApp/PokedexerApp/
├── App/
│   ├── PokedexerApp.swift            # @main entry point
│   ├── AppViewModel.swift            # Data sync state management
│   └── ContentView.swift             # Root navigation view
├── Core/
│   ├── KMP/
│   │   ├── FlowPublisher.swift       # Kotlin Flow → AsyncSequence bridge
│   │   ├── PokedexerSDKWrapper.swift  # Singleton SDK access
│   │   └── ModelExtensions.swift     # Swift extensions on KMP models
│   ├── Favorites/
│   │   └── FavoriteManager.swift
│   ├── Navigation/
│   │   ├── NavigationCoordinator.swift
│   │   └── Screen.swift              # Screen enum for routing
│   └── Theme/
│       ├── PokemonTypeTheme.swift    # 18 type colors + SF Symbols mapping
│       └── MeshGradientHelper.swift  # iOS 18 MeshGradient wrapper
├── Features/
│   ├── Home/                         # HomeView, HomeViewModel
│   ├── Pokedex/
│   │   ├── List/                     # PokedexListView, PokedexCardView, FilterSheet
│   │   └── Detail/                   # PokemonDetailView, PokemonDetailViewModel
│   ├── Moves/                        # MovesListView, MovesListViewModel
│   ├── Items/                        # ItemsListView, ItemsListViewModel
│   └── TypeChart/                    # TypeChartView
└── Shared/Components/
    ├── FlowLayout.swift, LoadingView.swift, PokeballBackground.swift
    ├── PokemonImage.swift, TypeLabel.swift
```

### Shared UI Components (Android `ui/common/`)

| Component | Purpose |
|-----------|---------|
| `NavigationTopAppBar` | Reusable top app bar with back navigation |
| `Pokeball` | Decorative rotating pokeball graphic |
| `PokemonImage` | Coil-based async image with loading states |
| `PokemonTypeLabels` | Type badge chips with colors |
| `StatsChart` | Canvas-based radar chart for Pokemon stats |
| `LoadingIndicator` | Animated loading spinner |
| `MeshGradient` | 15-point animated mesh gradient background |
| `ShaderGradient` | `RuntimeShader`-based gradient effect (API 33+) |
| `CategoryIcon` | Move category icons (Physical, Special, Status) |
| `ItemImage` | Item sprite image loader |
| `Label` | Generic styled label |
| `ConsumeSwipeNestedScrollConnection` | Nested scroll interop helper |
| `Transitions` | Material motion transition specs (SharedXAxis, SharedZAxis) |

## Key Patterns

### Dependency Injection (Metro)
- `ApplicationGraph` defines the DI graph with `@DependencyGraph(AppScope::class)`
- ViewModels without runtime params are exposed as properties on the graph
- ViewModels needing runtime params use `@AssistedInject` + `@AssistedFactory`
- `metroViewModel()` composable replaces Hilt's `hiltViewModel()` for Compose integration

### Navigation (Android)
- Type-safe routes via `sealed interface Screen : NavKey` with `@Serializable`
- `NavDisplay` with `rememberNavBackStack` for manual stack management
- Per-route `transitionSpec` / `popTransitionSpec` (SharedXAxis for lateral, SharedZAxis for depth)
- Back navigation: `backStack.removeAt(backStack.lastIndex)`

### Repository Pattern
- Repository interfaces defined in `shared/` (`PokemonRepository`, `MovesRepository`, etc.)
- Remote implementations combine Apollo GraphQL fetching with Room local caching
- Flow-based APIs for reactive UI updates
- Generation-based filtering at the repository level

### Search
- `HomeViewModel` uses `TextFieldState` with `snapshotFlow` and 200ms debounce
- Combines results from Pokemon, Moves, and Items repositories

### Theming
- **Android**: Material 3 color scheme generated from seed colors via Material Kolor; `PokemonTypesTheme` composable provides per-type `LocalPokemonTypeColorScheme`
- **iOS**: `PokemonTypeTheme` maps all 18 types to colors and SF Symbols
- **Shared**: `ThemeGenerator` provides cross-platform seed colors (`PokemonTypeSeeds`)

### KMP Interop (iOS)
- `PokedexerSDK` in `iosMain` is the entry point for iOS
- `PokedexerSDKWrapper` provides singleton access with initialization state
- SKIE automatically converts Kotlin `Flow` to Swift `AsyncSequence`
- `FlowPublisher` bridges remaining Kotlin Flow → Swift async patterns

## Database Schema

- **Entities**: `Pokemon`, `Move`, `Item`, `Ability`
- **Version**: 7
- **TypeConverters**: `Converters` class handles `List<String>`, `List<Evolution>`, `List<PokemonMove>`, `List<PokemonAbility>`
- **Migration strategy**: Destructive fallback (schema exports enabled)
- **Platform builders**: `expect/actual` pattern for Android (`Room.databaseBuilder`) and iOS (bundled SQLite)

## GraphQL

- **Endpoint**: `https://beta.pokeapi.co/graphql/v1beta`
- **Queries**: `GetPokemon`, `GetMoves`, `GetItems`, `GetAbilities` (in `shared/src/commonMain/graphql/`)
- **Package**: `des.c5inco.pokedexer.shared`

## Testing

### Unit Tests (`app/src/test/`)
- Basic unit test template

### Instrumentation Tests (`app/src/androidTest/`)
- **Database**: `PokemonDatabaseTest`, `MovesDatabaseTest` (Room DAO tests)
- **Journeys**: `PokedexNavigationTest`, `MenuScreensJourneyTest`, `ScreenVerificationTest`, `GenerationFilterJourneyTest`
- **Framework**: Compose Test Rule with `createAndroidComposeRule<MainActivity>()`, UI Automator

### Benchmark Tests (`benchmark/`)
- `StartupBenchmark` - Cold/warm startup timing
- `PokedexListScrollBenchmark` - List scroll performance
- `DetailsBenchmark` - Details screen rendering
- `BaselineProfileGenerator` - ART compilation baseline profiles
- **Config**: Managed device (Pixel 4, API 31), 5 iterations, `CompilationMode.None`/`Partial`

## Git Policy

**CRITICAL**: Always ask for explicit permission before running git commit, git push, git rebase, or any other command that makes permanent changes to the repository. Do not assume permission from unrelated requests.

## Pull Request Guidelines for the Agent
When the agent helps create a PR, please ensure it:

1. Includes a clear description of the changes as guided by AGENTS.md
2. References any related issues that the agent is addressing
3. Ensures all tests pass for code generated by the agent
4. Includes screenshots for UI changes implemented with the agent
5. Keeps PRs focused on a single concern as specified in AGENTS.md
