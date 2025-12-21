# ImageLoader Refactoring Summary

## Changes Made

### 1. Added GIF ImageLoader to Metro DI (`ApplicationGraph.kt`)

**What Changed:**
- Added `provideGifImageLoader()` provider method to `ApplicationModule`
- Exposed `gifImageLoader` in `ApplicationGraph` interface
- Added necessary imports for Coil ImageLoader and decoders

**Why:**
The GIF-enabled ImageLoader is used across multiple screens (Pokedex, Moves, Items) for the animated Pikachu loading indicator. Using DI provides:
- **Single instance** - One ImageLoader shared across the app
- **Memory efficiency** - Avoids creating multiple ImageLoader instances with separate caches
- **Consistency** - All loading indicators use the same configuration

**Code:**
```kotlin
@Provides
@SingleIn(AppScope::class)
fun provideGifImageLoader(context: Context): ImageLoader {
    return ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
}
```

### 2. Created CompositionLocal for GIF ImageLoader (`PokedexerApp.kt`)

**What Changed:**
- Added `LocalGifImageLoader` CompositionLocal
- Provided the GIF ImageLoader at the app root using `CompositionLocalProvider`

**Why:**
CompositionLocal allows composables deep in the tree to access the ImageLoader without explicit parameter passing, following Compose best practices.

**Code:**
```kotlin
val LocalGifImageLoader = compositionLocalOf<ImageLoader> {
    error("No GIF ImageLoader provided")
}
```

### 3. Updated LoadingIndicator (`LoadingIndicator.kt`)

**What Changed:**
- Removed local ImageLoader creation
- Now uses `LocalGifImageLoader.current` to get the injected ImageLoader
- Simplified code by removing builder logic

**Before:**
```kotlin
val imageLoader = ImageLoader.Builder(context)
    .components { add(ImageDecoderDecoder.Factory()) }
    .build()
```

**After:**
```kotlin
val imageLoader = LocalGifImageLoader.current
```

### 4. Updated LoadingSpinner (`LoadingSpinner.kt`)

**What Changed:**
- Removed local ImageLoader creation with SDK version checks
- Now uses `LocalGifImageLoader.current` to get the injected ImageLoader
- Cleaner, more maintainable code

### 5. Updated ItemImage (`ItemImage.kt`)

**What Changed:**
- Removed explicit `imageLoader` parameter from `AsyncImage`
- Now uses Coil's default singleton ImageLoader (implicit)

**Why:**
ItemImage doesn't need GIF support, so it should use Coil's optimized singleton instance rather than explicitly requesting it.

**Before:**
```kotlin
AsyncImage(
    model = itemAssetsUri(item.sprite),
    imageLoader = LocalContext.current.imageLoader,
    ...
)
```

**After:**
```kotlin
AsyncImage(
    model = itemAssetsUri(item.sprite),
    // Uses Coil's default singleton implicitly
    ...
)
```

## Architecture Pattern

### Image Loading Strategy:
1. **Regular images** (Pokemon, Items) → Use Coil's default singleton (no explicit ImageLoader)
2. **Animated images** (GIF loading indicators) → Use Metro-injected GIF ImageLoader via CompositionLocal

### Benefits:
- ✅ Single GIF ImageLoader instance shared app-wide
- ✅ Reduced memory overhead
- ✅ Better cache efficiency
- ✅ Cleaner, more maintainable code
- ✅ Follows modern Android DI patterns with Metro
- ✅ Consistent with Compose best practices

## Files Modified

1. `/app/src/main/kotlin/des/c5inco/pokedexer/di/ApplicationGraph.kt`
2. `/app/src/main/kotlin/des/c5inco/pokedexer/PokedexerApp.kt`
3. `/app/src/main/kotlin/des/c5inco/pokedexer/ui/common/LoadingIndicator.kt`
4. `/app/src/main/kotlin/des/c5inco/pokedexer/ui/common/LoadingSpinner.kt`
5. `/app/src/main/kotlin/des/c5inco/pokedexer/ui/common/ItemImage.kt`

## Testing

- ✅ Gradle sync successful
- ✅ Build successful (`app:assembleDebug`)
- ✅ No compilation errors

## Next Steps

Consider testing the app to ensure:
1. Loading indicators (animated Pikachu) display correctly on all screens
2. Pokemon and item images load properly
3. No performance regressions
