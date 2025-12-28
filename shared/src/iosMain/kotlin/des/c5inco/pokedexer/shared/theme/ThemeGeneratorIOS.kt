package des.c5inco.pokedexer.shared.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.materialkolor.PaletteStyle
import com.materialkolor.dynamicColorScheme
import com.materialkolor.ktx.contrastRatio
import com.materialkolor.ktx.darken
import com.materialkolor.ktx.lighten

/**
 * iOS-specific theme generation using Material Kolor with Compose Multiplatform.
 * This file is only compiled for iOS targets where Compose UI is available.
 */

/**
 * Generates a Pokemon theme using Material Kolor.
 * This function is only available on iOS via Compose Multiplatform.
 */
fun generatePokemonThemeForIOS(
    types: List<String>,
    isDark: Boolean = false
): PokemonThemeColors {
    val seedColorInt = getSeedColorForType(types)
    val seedColor = Color(seedColorInt)
    
    val colorScheme = dynamicColorScheme(
        seedColor = seedColor,
        isDark = isDark,
        isAmoled = false,
        style = PaletteStyle.TonalSpot
    )
    
    return if (isDark) {
        PokemonThemeColors(
            primary = colorScheme.primaryContainer.darken(0.4f).toArgb(),
            surface = colorScheme.primaryContainer.toArgb(),
            onSurface = colorScheme.onSurface.toArgb(),
            surfaceVariant = colorScheme.onPrimary.toArgb(),
            secondary = colorScheme.secondary.toArgb(),
            tertiary = colorScheme.secondary.toArgb()
        )
    } else {
        PokemonThemeColors(
            primary = seedColor.lighten(0.7f).toArgb(),
            surface = seedColor.toArgb(),
            onSurface = if (seedColor.contrastRatio(colorScheme.onSecondary) > 2.2) {
                colorScheme.onSecondary.toArgb()
            } else {
                colorScheme.onSecondaryContainer.toArgb()
            },
            surfaceVariant = seedColor.lighten(0.7f).toArgb(),
            secondary = colorScheme.primary.toArgb(),
            tertiary = colorScheme.secondary.toArgb()
        )
    }
}
