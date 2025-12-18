# Pokedexer iOS App

This is the iOS SwiftUI application that consumes the shared Kotlin Multiplatform module.

## Prerequisites

- Xcode 15.0 or later
- macOS Sonoma or later (recommended)
- [XcodeGen](https://github.com/yonaskolb/XcodeGen) (recommended for generating Xcode project)

## Quick Start (Recommended)

### Using XcodeGen

The easiest way to set up the iOS project is using XcodeGen:

```bash
# Install XcodeGen if you haven't already
brew install xcodegen

# Navigate to the iosApp directory
cd /path/to/Pokedexer/iosApp

# Generate the Xcode project
xcodegen generate

# Open the generated project
open Pokedexer.xcodeproj
```

The `project.yml` file is already configured with:
- Correct framework search paths for the shared module
- A pre-build script to automatically build the shared framework
- Debug and Release configurations

### 1. Build the Shared Framework

The Xcode project includes a build phase that automatically builds the shared framework.
If you want to build it manually:

```bash
cd /path/to/Pokedexer

# For iOS Simulator (Debug)
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64

# For iOS Device (Release)
./gradlew :shared:linkReleaseFrameworkIosArm64
```

## Manual Setup (Alternative)

If you prefer not to use XcodeGen:

### 1. Create Xcode Project

1. Open Xcode and create a new iOS App project:
   - File → New → Project
   - Choose "App" under iOS
   - Product Name: `Pokedexer`
   - Interface: SwiftUI
   - Language: Swift
   - Save in the `iosApp` folder of this project

2. Add the Swift source files:
   - Drag the existing Swift files from `iosApp/iosApp/` into your Xcode project
   - Make sure "Copy items if needed" is unchecked
   - Select your target

### 3. Link the Shared Framework

#### Option A: Direct Framework Linking

1. Find the built framework at:
   - Debug: `shared/build/bin/iosSimulatorArm64/debugFramework/Shared.framework`
   - Release: `shared/build/bin/iosArm64/releaseFramework/Shared.framework`

2. In Xcode:
   - Select your project in the navigator
   - Select your target
   - Go to "General" tab
   - Under "Frameworks, Libraries, and Embedded Content", click "+"
   - Click "Add Other" → "Add Files"
   - Navigate to and select the framework
   - Set "Embed" to "Embed & Sign"

3. Add a build phase script to build the framework automatically:
   - Select your target
   - Go to "Build Phases"
   - Click "+" → "New Run Script Phase"
   - Add this script:

```bash
cd "$SRCROOT/.."
./gradlew :shared:assembleSharedDebugXCFramework -Pkotlin.native.cocoapods.platform=$PLATFORM_NAME -Pkotlin.native.cocoapods.archs="$ARCHS" -Pkotlin.native.cocoapods.configuration=$CONFIGURATION
```

#### Option B: Using CocoaPods (Alternative)

Add this to the shared module's `build.gradle.kts`:

```kotlin
kotlin {
    cocoapods {
        summary = "Pokedexer shared module"
        homepage = "https://github.com/your/repo"
        ios.deploymentTarget = "15.0"
        framework {
            baseName = "Shared"
        }
    }
}
```

Then run `pod install` in the iosApp directory after creating a Podfile.

### 4. Configure Framework Search Paths

1. In Xcode, select your target
2. Go to "Build Settings"
3. Search for "Framework Search Paths"
4. Add the path to your framework:
   - `$(SRCROOT)/../shared/build/bin/iosSimulatorArm64/debugFramework` (Debug)
   - `$(SRCROOT)/../shared/build/bin/iosArm64/releaseFramework` (Release)

### 5. Import Header Search Path (if needed)

If you encounter header issues:
1. Go to "Build Settings"
2. Search for "Header Search Paths"
3. Add: `$(SRCROOT)/../shared/build/bin/iosSimulatorArm64/debugFramework/Shared.framework/Headers`

## Project Structure

```
iosApp/
├── iosApp/
│   ├── PokedexerApp.swift       # App entry point
│   ├── ContentView.swift        # Root content view
│   ├── Info.plist               # App configuration
│   ├── Views/
│   │   ├── HomeView.swift       # Home screen
│   │   ├── PokemonListView.swift # Pokemon list
│   │   └── PokemonDetailView.swift # Pokemon details
│   └── ViewModels/
│       ├── HomeViewModel.swift  # Home screen logic
│       └── PokemonListViewModel.swift # Pokemon list logic
└── README.md
```

## Using the Shared Module

### Importing

```swift
import Shared
```

### Using the PokedexerSDK (Recommended)

The `PokedexerSDK` provides a clean, high-level API for accessing all shared functionality:

```swift
import Shared

// Initialize the SDK with the iOS-native favorites store
let favoritesStore = UserDefaultsFavoritesStore()
let sdk = PokedexerSDK(favoritesStore: favoritesStore)

// Load Pokemon (handles caching automatically)
sdk.loadPokemonForGeneration(generationId: 1) { pokemon, error in
    if let pokemon = pokemon {
        // Use the pokemon list
        print("Loaded \(pokemon.count) Pokemon")
    }
}

// Get available generations
let generations = sdk.getGenerations()

// Toggle favorites
sdk.toggleFavorite(pokemonId: 25) { _, error in
    // Pikachu is now a favorite!
}
```

### Using Low-Level APIs

If you need more control, you can access the database and Apollo client directly:

```swift
// Use shared database
let database = DatabaseBuilderKt.getDatabaseBuilder()
    .fallbackToDestructiveMigration(dropAllTables: true)
    .build()

// Access DAOs
let pokemonDao = database.pokemonDao()

// Use FavoritesStore
let favoritesStore = UserDefaultsFavoritesStore()
```

### Handling Kotlin Coroutines in Swift

The shared module uses [KMP-NativeCoroutines](https://github.com/rickclephas/KMP-NativeCoroutines) for Swift interop.

#### Installing KMPNativeCoroutinesAsync

Add the Swift Package to your Xcode project:
1. File → Add Package Dependencies
2. Enter: `https://github.com/nicklockwood/KMP-NativeCoroutines`
3. Select `KMPNativeCoroutinesAsync` product

#### Using async/await

```swift
import KMPNativeCoroutinesAsync

// For suspend functions
let pokemon = try await asyncFunction(for: sdk.loadPokemonForGeneration(generationId: 1))

// For Flow (observing changes)
for try await pokemonList in asyncSequence(for: sdk.getAllPokemon()) {
    // Handle each update
    updateUI(with: pokemonList)
}

// For StateFlow
let isLoading = try await asyncFunction(for: sdk.isLoadingPokemon)
```

#### Using Combine (Alternative)

```swift
import KMPNativeCoroutinesCombine

// Convert Flow to Combine Publisher
let publisher = createPublisher(for: sdk.getAllPokemon())
    .sink { completion in
        // Handle completion
    } receiveValue: { pokemon in
        // Handle pokemon updates
    }
```

## Troubleshooting

### Framework Not Found

Make sure you've built the shared module first:
```bash
./gradlew :shared:assemble
```

### Module 'Shared' Not Found

1. Check that the framework is properly linked
2. Verify Framework Search Paths are correct
3. Clean build folder (Cmd+Shift+K) and rebuild

### Undefined Symbols

This usually means the framework architecture doesn't match:
- Use `iosSimulatorArm64` for M1/M2 Macs running simulator
- Use `iosArm64` for physical devices

