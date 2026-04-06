# Architecture

## Overview

This is a **Kotlin Multiplatform (KMP)** Pokedex app with native **Android (Jetpack Compose)** and **iOS (SwiftUI)** frontends sharing a common data layer.

## Module Map

| Module | Purpose | Dependencies |
|--------|---------|-------------|
| `:app` | Android Jetpack Compose application, entry point, navigation | `:shared` |
| `:shared` | KMP data layer (Room database, Apollo GraphQL, models, repositories) | None |
| `:benchmark` | Android macro-benchmark tests | `:app` |
| `iosApp/` | Native SwiftUI iOS application (Xcode project) | `:shared` (via framework) |

## Dependency Direction

Dependencies flow inward toward the `:shared` module:

- **Android (`:app`)** depends on `:shared` directly via Gradle.
- **iOS (`iosApp/`)** depends on `:shared` via a generated framework and the **SKIE** bridge for native Swift interop.

## Shared Layer (`:shared`)

The shared module is the single source of truth for business logic and data persistence.

- **Technology Stack**:
    - **Database**: Room KMP (version 7, with expect/actual platform builders).
    - **Network**: Apollo GraphQL client connecting to PokéAPI (`https://beta.pokeapi.co/graphql/v1beta`).
    - **Models**: Shared data classes with Room `@Entity` annotations.
    - **Theming**: Material Kolor for cross-platform seed color generation.
- **Project Structure**:
    ```
    shared/src/
    ├── commonMain/kotlin/des/c5inco/pokedexer/shared/
    │   ├── data/
    │   │   ├── PokemonDatabase.kt       # Room KMP database definition
    │   │   ├── Result.kt                # Success/Failure sealed class
    │   │   ├── pokemon/, moves/, items/, abilities/  # Daos and Repositories
    │   ├── model/
    │   │   ├── Pokemon.kt               # @Entity with stats, types, evolutions, moves, abilities
    │   │   ├── Move.kt, Item.kt, Ability.kt  # @Entity models
    │   │   ├── Type.kt, Generation.kt, Stats.kt, Evolution.kt
    │   └── theme/
    │       └── ThemeGenerator.kt         # Cross-platform Pokemon type color seeds
    ├── commonMain/graphql/               # Apollo .graphql query files
    ├── androidMain/                      # DatabaseBuilder.android.kt (expect/actual)
    └── iosMain/
        ├── PokedexerSDK.kt               # iOS SDK entry point
        └── data/DatabaseBuilder.ios.kt
    ```
- **GraphQL**:
    - **Endpoint**: `https://beta.pokeapi.co/graphql/v1beta`
    - **Queries**: `GetPokemon`, `GetMoves`, `GetItems`, `GetAbilities`
- **Database Schema**:
    - **Version**: 7
    - **Migration strategy**: Destructive fallback (`dropAllTables=true`)

## Android App (`:app`)

- **UI Framework**: Jetpack Compose with Material 3.
- **Architecture**: **MVVM** with **Metro DI**.
- **Dependency Injection**: `ApplicationGraph` defines the DI graph. ViewModels use `@AssistedInject` if they need runtime params.
- **Navigation**: **Compose Navigation 3** (`NavDisplay`). Screens are defined via `sealed interface Screen : NavKey`.
- **Project Structure**:
    ```
    app/src/main/kotlin/des/c5inco/pokedexer/
    ├── MainActivity.kt, PokedexerApp.kt, PokedexerApplication.kt
    ├── di/                   # Metro @DependencyGraph and ViewModel helpers
    ├── data/                 # Seed data, DataStore preferences, Type chart
    ├── model/                # Extension functions for shared models
    └── ui/
        ├── home/, pokedex/, moves/, items/, typechart/  # Feature screens
        ├── common/           # Shared UI components (Pokeball, StatsChart, MeshGradient, etc.)
        └── navigation/       # Navigation destinations and routes
    ```

## iOS App (`iosApp/`)

- **UI Framework**: SwiftUI (iOS 18.0+).
- **KMP Interop**: 
    - **PokedexerSDK**: Entry point in `shared/iosMain`.
    - **SKIE**: Bridges Kotlin `Flow` to Swift `AsyncSequence`.
- **Architecture**: **MVVM** using `ObservableObject` and native SwiftUI `NavigationStack`.
- **Project Structure**:
    ```
    iosApp/PokedexerApp/
    ├── App/                  # Entry point and root views
    ├── Core/                 # KMP bridge, Favorites, Navigation, Theme
    ├── Features/             # Home, Pokedex, Moves, Items, TypeChart views
    └── Shared/Components/    # Reusable SwiftUI components
    ```

## Shared UI Components

| Component | Purpose |
|-----------|---------|
| `NavigationTopAppBar` | Reusable top app bar with back navigation |
| `Pokeball` | Decorative rotating pokeball graphic |
| `PokemonImage` | Coil-based async image with loading states |
| `StatsChart` | Canvas-based radar chart for Pokemon stats |
| `MeshGradient` | 15-point animated mesh gradient background |
| `ShaderGradient` | `RuntimeShader`-based gradient effect (API 33+) |
| `Transitions` | Material motion transition specs (SharedXAxis, SharedZAxis) |

## Architectural Invariants

1. **No direct database access**: UI layers must always use Repository interfaces.
2. **KMP Models**: Platform-specific models should be avoided unless necessary.
3. **Flow-only Repositories**: All repository methods that fetch data should return a `Flow`.
4. **Shared Color Seeds**: All UI coloring must derive from the KMP `ThemeGenerator`.
