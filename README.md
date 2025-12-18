# Compose Pokedexer
![License-MIT](https://img.shields.io/badge/License-MIT-red.svg)
![Kotlin](https://img.shields.io/badge/Kotlin-2.2.0-blue.svg)
![KMP](https://img.shields.io/badge/Kotlin%20Multiplatform-iOS%20%2B%20Android-orange.svg)

A **Kotlin Multiplatform** Pokédex app with shared business logic between Android and iOS.

Adapted fork of original [compose-pokedex](https://github.com/zsoltk/compose-pokedex) project

## Features

### Kotlin Multiplatform (KMP)
- **Shared module** (`shared/`) containing domain models, repositories, database, and networking
- **Room KMP** for cross-platform SQLite database
- **Apollo GraphQL** for fetching Pokemon data from [PokeAPI](https://pokeapi.co/docs/graphql)
- **KMP-NativeCoroutines** for Swift async/await interop
- Platform-native preferences: DataStore (Android) / UserDefaults (iOS)

### Android
- **Jetpack Compose** UI with Material 3
- **Hilt** for dependency injection
- **Coil** for image loading
- RuntimeShader effects for pager color transitions (API 33+)
- Dynamic theming powered by [Material Kolor](https://github.com/jordond/MaterialKolor)
- Many animations (loading, shared element, navigation transitions)

### iOS
- **SwiftUI** native UI
- Uses shared Kotlin module via framework
- Native UserDefaults for favorites storage

## Project Structure

```
Pokedexer/
├── app/                    # Android application
├── shared/                 # Kotlin Multiplatform shared module
│   ├── commonMain/         # Shared code (models, repositories, database)
│   ├── androidMain/        # Android-specific implementations
│   └── iosMain/            # iOS-specific implementations
├── iosApp/                 # iOS SwiftUI application
└── benchmark/              # Android benchmark tests
```

## Building

### Android

```bash
# Build debug APK
./gradlew :app:assembleDebug

# Build release APK
./gradlew :app:assembleRelease
```

### iOS

```bash
# Install XcodeGen
brew install xcodegen

# Generate Xcode project
cd iosApp
xcodegen generate

# Open in Xcode
open Pokedexer.xcodeproj
```

See [iosApp/README.md](iosApp/README.md) for detailed iOS setup instructions.

### Shared Module

```bash
# Build shared module for all platforms
./gradlew :shared:assemble

# Build iOS framework only
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
```

## Screenshots

![Screenshots of app](assets/jul2024-screenshots.png "Screenshots")

## Original design

Adapted from [Pokedex App design](https://dribbble.com/shots/6545819-Pokedex-App) by [Saepul Nahwan](https://dribbble.com/saepulnahwan23).

Notable additions:
- Dark theme
- Designs for all tabs on details screen
- Designs for Move, Abilities, and Items screens
- Designs for search

## License

All the code available under the MIT license. See [LICENSE](LICENSE).
