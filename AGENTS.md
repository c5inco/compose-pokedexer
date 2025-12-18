# AGENTS.md

This file provides guidance to AI code assistants when working with code in this repository.

## Build Commands

### Android
- **Build the project**: `./gradlew build`
- **Run tests**: `./gradlew test`
- **Run instrumentation tests**: `./gradlew connectedAndroidTest`
- **Build debug APK**: `./gradlew :app:assembleDebug`
- **Build release APK**: `./gradlew :app:assembleRelease`
- **Run benchmarks**: `./gradlew :benchmark:pixel4Api31BenchmarkAndroidTest`
- **Clean build**: `./gradlew clean`

### Shared Module (KMP)
- **Build shared module**: `./gradlew :shared:assemble`
- **Build iOS debug framework**: `./gradlew :shared:linkDebugFrameworkIosSimulatorArm64`
- **Build iOS release framework**: `./gradlew :shared:linkReleaseFrameworkIosArm64`
- **Generate Apollo sources**: `./gradlew :shared:generatePokeapiApolloSources`

### iOS
- **Generate Xcode project**: `cd iosApp && xcodegen generate`
- **Open Xcode**: `open iosApp/Pokedexer.xcodeproj`

## Architecture Overview

This is a **Kotlin Multiplatform** Pokédex app with shared business logic between Android and iOS.

### Technology Stack

**Shared Module (`:shared`)**:
- **Database**: Room KMP (multiplatform SQLite)
- **Network**: Apollo GraphQL client connecting to PokéAPI
- **Coroutines**: kotlinx.coroutines for async operations
- **KMP-NativeCoroutines**: Swift async/await interop

**Android (`:app`)**:
- **UI Framework**: Jetpack Compose with Material 3
- **Architecture**: MVVM with Hilt dependency injection
- **Navigation**: Compose Navigation
- **Image Loading**: Coil
- **Theming**: Material Kolor for dynamic theming

**iOS (`iosApp/`)**:
- **UI Framework**: SwiftUI
- **Architecture**: MVVM
- **Image Loading**: AsyncImage (native)

### Project Structure

```
Pokedexer/
├── app/                          # Android application
│   └── src/main/kotlin/
│       ├── di/                   # Hilt dependency injection modules
│       ├── data/                 # Android repository implementations
│       ├── model/                # Android-specific model extensions
│       └── ui/                   # Jetpack Compose UI
│
├── shared/                       # Kotlin Multiplatform shared module
│   └── src/
│       ├── commonMain/           # Shared code
│       │   ├── model/            # Domain models with Room annotations
│       │   ├── data/db/          # Room database, DAOs, converters
│       │   ├── data/preferences/ # FavoritesStore interface
│       │   └── graphql/          # Apollo GraphQL queries
│       ├── androidMain/          # Android implementations (DataStore, DB builder)
│       └── iosMain/              # iOS implementations (UserDefaults, DB builder)
│
├── iosApp/                       # iOS SwiftUI application
│   └── iosApp/
│       ├── Views/                # SwiftUI views
│       └── ViewModels/           # ViewModels using shared SDK
│
└── benchmark/                    # Android benchmark tests
```

### Key Components

**Shared Module Entry Point**:
- `PokedexerSDK.kt` - Main SDK class exposing all shared functionality to iOS
- Uses `@NativeCoroutines` annotations for Swift async/await support

**Android Entry Points**:
- `MainActivity.kt` - Single activity with edge-to-edge display
- `PokedexerApp.kt` - Main Compose navigation graph and app structure
- `PokedexerApplication` - Hilt application class

**Data Layer (Shared)**:
- `PokedexerDatabase.kt` - Room KMP database with DAOs for Pokemon, Moves, Items, and Abilities
- Repository interfaces in shared, implementations bridge to shared DAOs
- GraphQL queries in `shared/src/commonMain/graphql/` directory
- Platform-specific database builders in `androidMain` and `iosMain`

**Preferences (Platform-Native)**:
- `FavoritesStore` interface in `commonMain`
- `DataStoreFavoritesStore` in `androidMain` (uses DataStore)
- `UserDefaultsFavoritesStore` in `iosMain` (uses NSUserDefaults)

**Android UI Structure**:
- `ui/home/` - Main home screen with search functionality
- `ui/pokedex/` - Pokemon list and detail screens
- `ui/moves/` - Moves listing
- `ui/items/` - Items listing
- `ui/common/` - Shared UI components and utilities
- `ui/theme/` - App theming with Material 3

**Android Dependency Injection**:
- `NetworkModule` - Provides shared Apollo client
- `SharedDatabaseModule` - Provides shared Room database and DAOs
- Repository modules bind shared implementations

### Database Schema
- Pokemon entities with stats, types, evolutions, moves, and abilities
- Type converters for complex data structures (lists, custom objects)
- Room KMP handles migrations across platforms

### Testing
- Unit tests in `app/src/test/`
- Instrumentation tests in `app/src/androidTest/`
- Benchmark tests in separate `benchmark` module
- Database tests for Pokemon and Moves functionality

## Pull Request Guidelines for the Agent
When the agent helps create a PR, please ensure it:

1. Includes a clear description of the changes as guided by AGENTS.md
2. References any related issues that the agent is addressing
3. Ensures all tests pass for code generated by the agent
4. Includes screenshots for UI changes implemented with the agent
5. Keeps PRs focused on a single concern as specified in AGENTS.md
6. For shared module changes, verify both Android and iOS builds succeed
