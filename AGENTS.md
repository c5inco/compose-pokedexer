# AGENT.md

This file provides guidance to an AI agent when working with code in this repository.

## Build Commands

### Building the App
```bash
./gradlew build
```

### Running Tests
```bash
# Run unit tests
./gradlew test

# Run instrumented/UI tests  
./gradlew connectedAndroidTest

# Run specific database tests
./gradlew connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=des.c5inco.pokedexer.PokemonDatabaseTest
./gradlew connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=des.c5inco.pokedexer.MovesDatabaseTest
```

### Development Tasks
```bash
# Install debug APK
./gradlew installDebug

# Clean build
./gradlew clean

# Generate baseline profile (performance)
./gradlew :benchmark:connectedBenchmarkAndroidTest
```

## Architecture Overview

### Project Structure
This is an Android app built with **Jetpack Compose** and **Material 3**, showcasing a Pokédex interface with data sourced from the PokeAPI via GraphQL.

### Key Technologies
- **UI Framework**: Jetpack Compose with Material 3 design
- **Architecture**: MVVM with Hilt dependency injection  
- **Database**: Room with auto-migrations (currently version 6)
- **Navigation**: Compose Navigation with shared element transitions
- **Data Source**: GraphQL via Apollo Client querying PokeAPI
- **Image Loading**: Coil
- **Theming**: Dynamic theming with Material Kolor

### Core Components

#### Data Layer (`app/src/main/kotlin/des/c5inco/pokedexer/data/`)
- **PokemonDatabase**: Room database with entities for Pokemon, Move, Item, Ability
- **Repositories**: Separate repository classes for each domain (Pokemon, Moves, Items, Abilities)
- **Remote vs Local**: RemotePokemonRepository handles GraphQL, PokemonRepository handles Room operations

#### Dependency Injection (`app/src/main/kotlin/des/c5inco/pokedexer/di/`)
- Hilt modules for database, repository, and network dependencies
- Separate modules for each domain area (Pokemon, Moves, Items, Abilities)

#### UI Layer (`app/src/main/kotlin/des/c5inco/pokedexer/ui/`)
- **home/**: Main screen with search functionality and navigation menu
- **pokedex/**: Pokemon list and detailed view with pager support
- **moves/**: Move list screen  
- **items/**: Items list screen
- **common/**: Shared UI components (PokemonImage, StatsChart, transitions, etc.)

#### Models (`app/src/main/kotlin/des/c5inco/pokedexer/model/`)
- Data classes for Pokemon, Move, Item, Ability, Type, etc.
- Room entities and GraphQL response models

### Navigation Structure
- **Home** (`"home"`) - Main screen with search and menu
- **Pokedex** (`"pokedex"`) - Nested navigation:
  - `"list"` - Pokemon grid/list view
  - `"details"` - Individual Pokemon details with pager
- **Moves** (`"moves"`) - Move list screen
- **Items** (`"items"`) - Items list screen

### GraphQL Integration
- Schema and queries located in `app/src/main/graphql/des.c5inco.pokedexer/`
- Apollo client configured in NetworkModule
- Queries: GetPokemon, GetMoves, GetItems, GetAbilities

### Database Schema
- Room database with auto-migrations enabled
- Schema files stored in `app/schemas/`
- Current version: 6 (includes Pokemon, Move, Item, Ability entities)

### Testing Strategy
- Unit tests in `app/src/test/`
- Instrumented tests in `app/src/androidTest/` including database tests
- Benchmark tests in `benchmark/` module for performance profiling

### Build Variants
- **debug**: Development build with debugging enabled
- **release**: Production build with ProGuard disabled
- **benchmark**: Special variant for performance testing