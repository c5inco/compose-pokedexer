# Xcode Project Setup Guide

The Xcode project files have been created! Follow these steps to complete the setup.

## ✅ What's Already Done

- ✅ Xcode project file (`.xcodeproj`)
- ✅ All Swift source files (35+ files)
- ✅ Info.plist with network permissions
- ✅ Assets catalog structure
- ✅ KMP framework built and ready
- ✅ Build script for automatic KMP framework builds

## 🚀 Quick Start (2 Methods)

### Method 1: Open and Add Files in Xcode (Recommended - 5 minutes)

1. **Open the project:**
   ```bash
   cd iosApp
   open PokedexerApp.xcodeproj
   ```

2. **Add all Swift files:**
   - In Xcode, right-click on the `PokedexerApp` folder in the Project Navigator
   - Select **"Add Files to PokedexerApp..."**
   - Navigate to and select the following folders:
     - `Core/` (with subfolders)
     - `Features/` (with subfolders)
     - `Shared/` (with subfolders)
   - ✅ Check **"Create groups"**
   - ✅ Check **"Copy items if needed"** = NO (keep unchecked)
   - ✅ Target: PokedexerApp
   - Click **"Add"**

3. **Verify the framework:**
   - Select the project in the navigator
   - Select the "PokedexerApp" target
   - Go to "General" tab
   - Under "Frameworks, Libraries, and Embedded Content"
   - The `shared.framework` should already be there
   - If not, click + and add: `../shared/build/bin/iosSimulatorArm64/releaseFramework/shared.framework`
   - Set to **"Embed & Sign"**

4. **Build and Run:**
   - Select "iPhone 15 Pro" (or any iOS 18+ simulator)
   - Press ⌘R or click the Play button
   - First build will take 2-3 minutes (builds KMP framework)

### Method 2: Use Setup Script (Alternative)

```bash
cd iosApp
./setup_project.sh
```

Then open in Xcode and add files as described in Method 1, step 2.

## 📁 Files to Add in Xcode

When adding files, make sure you include:

```
Core/
├── Navigation/
│   ├── NavigationCoordinator.swift
│   └── Screen.swift
├── KMP/
│   ├── FlowPublisher.swift
│   ├── PokedexerSDKWrapper.swift
│   └── ModelExtensions.swift
└── Theme/
    ├── PokemonTypeTheme.swift
    └── MeshGradientHelper.swift

Features/
├── Home/
│   ├── HomeView.swift
│   └── HomeViewModel.swift
├── Pokedex/
│   ├── List/
│   │   ├── PokedexListView.swift
│   │   ├── PokedexListViewModel.swift
│   │   └── PokedexCardView.swift
│   └── Detail/
│       ├── PokemonDetailView.swift
│       └── PokemonDetailViewModel.swift
├── Moves/
│   ├── MovesListView.swift
│   └── MovesListViewModel.swift
├── Items/
│   ├── ItemsListView.swift
│   └── ItemsListViewModel.swift
└── TypeChart/
    └── TypeChartView.swift

Shared/
└── Components/
    ├── PokeballBackground.swift
    ├── LoadingView.swift
    ├── TypeLabel.swift
    └── PokemonImage.swift
```

## 🔧 Build Settings Verification

The project is pre-configured with:

- **iOS Deployment Target:** 18.0
- **Swift Version:** 5.0
- **Framework Search Paths:** `$(SRCROOT)/../shared/build/bin/iosSimulatorArm64/releaseFramework`
- **Bundle Identifier:** `des.c5inco.pokedexer`
- **Build Script:** Automatically builds KMP framework before compilation

## ⚠️ Important Notes

### iOS Version Requirement
- **iOS 18.0+** required for native MeshGradient
- The app will NOT run on iOS 17 or below
- Use iPhone 15 or newer simulators

### Network Permissions
The Info.plist is already configured with exceptions for:
- `pokeapi.co` (Pokemon data API)
- `raw.githubusercontent.com` (Pokemon images)

### Framework Architecture
The build script automatically builds for:
- **Simulator (Arm64):** Apple Silicon Macs
- If you need Intel simulator: Change script to `linkReleaseFrameworkIosX64`
- If you need device: Change script to `linkReleaseFrameworkIosArm64`

## 🐛 Troubleshooting

### "Module 'shared' not found"

1. **Build the framework manually:**
   ```bash
   cd ../shared
   ./gradlew linkReleaseFrameworkIosSimulatorArm64
   ```

2. **Verify framework location:**
   ```bash
   ls -la ../shared/build/bin/iosSimulatorArm64/releaseFramework/shared.framework
   ```

3. **In Xcode:**
   - Go to Build Settings → Framework Search Paths
   - Should contain: `$(SRCROOT)/../shared/build/bin/iosSimulatorArm64/releaseFramework`

### "Cannot find type 'Pokemon' in scope"

This means Swift files haven't been added to the project target:
1. Select each Swift file in Project Navigator
2. In File Inspector (right panel), verify "Target Membership"
3. Check the box next to "PokedexerApp"

### Build Errors with Swift Files

If you get syntax errors:
1. Make sure ALL Swift files in `Core/`, `Features/`, and `Shared/` are added
2. Check that ContentView.swift doesn't have the space in "Pokedex ListView" (should be "PokedexListView")
3. Clean build folder (⌘⇧K) and rebuild

### App Crashes on Launch

1. Check the iOS simulator is iOS 18.0+
2. Verify the KMP framework is properly embedded
3. Check console for errors about missing framework

## 📱 First Run

When you first run the app:
1. **Loading takes 10-30 seconds** - The KMP SDK is:
   - Initializing Room database
   - Fetching Pokemon data from PokeAPI GraphQL
   - Processing 1000+ Pokemon records
2. **Subsequent launches** - Instant (data is cached in database)
3. **Pull to refresh** - Not yet implemented (data loads once)

## 🎨 Features to Test

1. **Home Screen:**
   - Type in search bar → See results appear
   - Tap navigation cards → Navigate to sections

2. **Pokédex List:**
   - See mesh gradients on Pokemon cards
   - Tap filter button → Filter by type/generation
   - Tap Pokemon → See details

3. **Pokemon Details:**
   - Swipe through tabs (About, Stats, Evolution, Moves)
   - Drag card down → Dismiss back to list

4. **Other Screens:**
   - Moves list with type colors
   - Items with async-loaded images
   - Type chart grid

## 🎯 Success Criteria

You'll know it's working when:
- ✅ App launches on iOS 18 simulator
- ✅ Home screen shows 4 navigation cards
- ✅ Search bar is functional
- ✅ Pokemon cards show mesh gradients (not just solid colors)
- ✅ All screens are accessible
- ✅ Pokemon images load from network

## 🚀 Performance Tips

- **First build:** 2-3 minutes (compiles KMP + Swift)
- **Incremental builds:** 10-20 seconds
- **Clean builds:** 1-2 minutes

## 📚 Additional Resources

- Full implementation details: `IMPLEMENTATION_SUMMARY.md`
- General instructions: `README.md`
- Detailed plan: `/Users/c5inco/.claude/plans/fuzzy-splashing-zephyr.md`

## ✅ Checklist

Before running:
- [ ] Xcode 16+ installed
- [ ] iOS 18+ simulator available
- [ ] Project opened in Xcode
- [ ] Swift files added to target
- [ ] shared.framework linked and embedded
- [ ] Build script verified (Build Phases)
- [ ] Framework Search Paths set correctly

After these steps, you should be able to build and run the complete iOS Pokedexer app! 🎉
