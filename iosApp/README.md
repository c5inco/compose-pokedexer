# Pokedexer iOS App

Native iOS app built with SwiftUI that integrates with the Kotlin Multiplatform shared module.

## ✅ Implementation Status

All major components have been implemented:

- ✅ **KMP Framework** - Built and ready to link
- ✅ **Core Infrastructure** - Navigation, KMP bridge, model extensions
- ✅ **Theme System** - All 18 Pokemon type colors ported
- ✅ **MeshGradient** - Ported to native iOS 18 MeshGradient API
- ✅ **UI Components** - Pokeball, TypeLabel, LoadingView, etc.
- ✅ **Home Screen** - Search with debounce, navigation grid
- ✅ **Pokedex List** - 2-column grid with mesh gradients and filters
- ✅ **Pokemon Details** - Tabs (About, Stats, Evolution, Moves)
- ✅ **Moves Screen** - Table-style list
- ✅ **Items Screen** - Card-based list
- ✅ **Type Chart** - Basic grid layout

## 🚀 Setup Instructions

### Prerequisites

- macOS with Xcode 16+ installed
- iOS 18.0+ deployment target (for MeshGradient)
- Kotlin Multiplatform shared framework built

### Step 1: Build the KMP Framework

```bash
cd ..
./gradlew linkReleaseFrameworkIosArm64 linkReleaseFrameworkIosSimulatorArm64 linkReleaseFrameworkIosX64
```

The frameworks will be built at:
- `../shared/build/bin/iosArm64/releaseFramework/shared.framework`
- `../shared/build/bin/iosSimulatorArm64/releaseFramework/shared.framework`
- `../shared/build/bin/iosX64/releaseFramework/shared.framework`

### Step 2: Create Xcode Project

1. Open Xcode
2. **File → New → Project**
3. Choose **iOS → App**
4. Settings:
   - Product Name: `PokedexerApp`
   - Interface: **SwiftUI**
   - Language: **Swift**
   - Location: This `iosApp/` directory
   - Bundle ID: `des.c5inco.pokedexer`
   - Deployment Target: **iOS 18.0**

### Step 3: Add Swift Files to Project

1. In Xcode, right-click on `PokedexerApp` folder
2. **Add Files to "PokedexerApp"...**
3. Select the `PokedexerApp/` directory (the one with App/, Core/, Features/, etc.)
4. Check **Create folder references**
5. Click **Add**

### Step 4: Link KMP Framework

1. In Xcode, select the project in the navigator
2. Select the `PokedexerApp` target
3. Go to **General** tab
4. Under **Frameworks, Libraries, and Embedded Content**, click **+**
5. Click **Add Other... → Add Files...**
6. Navigate to `../shared/build/bin/iosSimulatorArm64/releaseFramework/`
7. Select `shared.framework`
8. Set to **Embed & Sign**

### Step 5: Add Build Script for Automatic Framework Builds

1. Select the `PokedexerApp` target
2. Go to **Build Phases** tab
3. Click **+** → **New Run Script Phase**
4. Name it "Build KMP Framework"
5. Add this script:

```bash
cd "$SRCROOT/../shared"
./gradlew linkReleaseFrameworkIosSimulatorArm64
```

6. Drag this phase to run **before** "Compile Sources"

### Step 6: Configure Framework Search Paths

1. Select the `PokedexerApp` target
2. Go to **Build Settings** tab
3. Search for "Framework Search Paths"
4. Add: `$(SRCROOT)/../shared/build/bin/iosSimulatorArm64/releaseFramework`

### Step 7: Add Kingfisher (Optional - for enhanced image loading)

1. In Xcode, select the project
2. Go to **Package Dependencies** tab
3. Click **+**
4. Search for: `https://github.com/onevcat/Kingfisher.git`
5. Version: `7.0.0` or later
6. Click **Add Package**

> **Note:** The app already uses AsyncImage which works well. Kingfisher is optional but provides better caching.

### Step 8: Update Info.plist for Network Access

1. Open `Info.plist`
2. Add key: **App Transport Security Settings** (Dictionary)
3. Add subkey: **Allow Arbitrary Loads** = `YES`

Or better, add specific exception for PokeAPI:
```xml
<key>NSAppTransportSecurity</key>
<dict>
    <key>NSExceptionDomains</key>
    <dict>
        <key>pokeapi.co</key>
        <dict>
            <key>NSExceptionAllowsInsecureHTTPLoads</key>
            <true/>
        </dict>
        <key>raw.githubusercontent.com</key>
        <dict>
            <key>NSExceptionAllowsInsecureHTTPLoads</key>
            <true/>
        </dict>
    </dict>
</dict>
```

## 🎨 Features

### MeshGradient Support
- **iOS 18+**: Uses native `MeshGradient` API
- **iOS 17 and below**: Automatic fallback to `LinearGradient`

### Screens Implemented

1. **Home Screen**
   - Search bar with 200ms debounce
   - 2x2 navigation grid (Pokédex, Moves, Type Charts, Items)
   - Search results for Pokemon, Moves, Items

2. **Pokédex List**
   - 2-column grid with mesh gradient backgrounds
   - Filters: Favorites, Type (18 types), Generation (I-IX)
   - Staggered animation on appear
   - Tap to navigate to details

3. **Pokemon Details**
   - Mesh gradient background
   - Draggable card (drag down to dismiss)
   - 4 tabs: About, Stats, Evolution, Moves
   - Favorite toggle

4. **Moves List**
   - Table-style layout
   - Columns: Name, Type, Category, Power, Accuracy
   - Type-colored labels

5. **Items List**
   - Card-based scrollable list
   - Async image loading for item sprites
   - Alternating background colors

6. **Type Chart**
   - Horizontal & vertical scrolling grid
   - Type effectiveness visualization

## 🏗️ Architecture

### MVVM + AsyncSequence Pattern

```
View → ViewModel → PokedexerSDKWrapper → KMP SDK → Room DB/Apollo GraphQL
```

### Key Components

- **FlowPublisher**: Bridges Kotlin Flow to Swift AsyncSequence
- **PokedexerSDKWrapper**: Swift-friendly wrapper for KMP SDK
- **NavigationCoordinator**: Centralized navigation management
- **PokemonTypeTheme**: All Pokemon type colors and analogous color calculation
- **MeshGradientHelper**: Native iOS 18 mesh gradient implementation

## 📱 Running the App

1. Select **iPhone 15 Pro** (or any iOS 18+ simulator)
2. Click **Run** (⌘R)

The app will:
1. Build the KMP framework
2. Compile Swift code
3. Launch in the simulator
4. Initialize the Room database
5. Fetch Pokemon data from PokéAPI

## 🐛 Troubleshooting

### "Module 'shared' not found"
- Ensure the framework is built: `./gradlew linkReleaseFrameworkIosSimulatorArm64`
- Check Framework Search Paths in Build Settings
- Clean build folder (⌘⇧K) and rebuild

### "No such module 'Kingfisher'"
- Kingfisher is optional - you can remove import statements if not using
- Or add it via Swift Package Manager (see Step 7)

### Mesh gradients not showing
- Requires iOS 18+ - check deployment target
- On iOS 17, linear gradients will be used as fallback

### Database errors
- The KMP SDK initializes the database automatically
- Check network connectivity for PokeAPI access
- Database is stored in app's Documents directory

## 🎯 Next Steps (Optional Enhancements)

- [ ] Add horizontal pager to Pokemon Details for swipe navigation
- [ ] Implement complete type effectiveness chart data
- [ ] Add user preferences persistence
- [ ] Implement pull-to-refresh
- [ ] Add haptic feedback
- [ ] Create app icon and launch screen
- [ ] Add accessibility labels
- [ ] Implement deep linking
- [ ] Add widgets (Today widget with random Pokemon)
- [ ] Support Dynamic Type for text scaling

## 📄 License

This project uses the same license as the main Pokedexer repository.

## 🙏 Acknowledgments

- Pokemon data from [PokéAPI](https://pokeapi.co/)
- MeshGradient port based on Android Compose implementation
- Room KMP for cross-platform database
- Apollo GraphQL for API communication
