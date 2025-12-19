# Pokedexer iOS App

SwiftUI iOS application consuming the Kotlin Multiplatform shared module.

## Prerequisites

1. **Xcode 15+** - Required to build the iOS app
2. **Shared Framework** - Built from the KMP shared module

## Building the Shared Framework

Before building the iOS app, you need to build the shared KMP framework:

```bash
# From the project root directory
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64

# For Release build
./gradlew :shared:linkReleaseFrameworkIosSimulatorArm64

# For physical device (requires Xcode)
./gradlew :shared:linkDebugFrameworkIosArm64
```

## Opening the Project

```bash
open iosApp/iosApp.xcodeproj
```

## Project Structure

```
iosApp/
├── iosApp.xcodeproj/          # Xcode project
└── iosApp/
    ├── PokedexerApp.swift      # App entry point
    ├── ContentView.swift       # Main view with navigation
    ├── PokemonListView.swift   # Grid view of Pokemon
    ├── PokemonDetailView.swift # Detail view with tabs
    ├── PokemonListViewModel.swift # ViewModel with repository
    └── Assets.xcassets/        # App assets
```

## Features

- **Pokemon Grid** - Displays Pokemon in a 2-column grid with cards
- **Type Badges** - Colored badges showing Pokemon types
- **Detail View** - Tabbed interface with About, Stats, and Moves sections
- **Async Images** - Pokemon artwork loaded from PokeAPI

## Framework Integration

The shared framework is linked from:
- Debug: `../shared/build/bin/iosSimulatorArm64/debugFramework/shared.framework`
- Release: `../shared/build/bin/iosSimulatorArm64/releaseFramework/shared.framework`

## Notes

The ViewModel currently includes mock implementations that will be replaced with actual shared module repository calls once the framework is built.
