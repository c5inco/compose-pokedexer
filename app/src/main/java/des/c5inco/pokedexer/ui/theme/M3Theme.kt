package des.c5inco.pokedexer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import des.c5inco.pokedexer.model.Type

private val M3LightColors = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = SurfaceTones.light2,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surfaceTint,
    outlineVariant = md_theme_light_outlineVariant,
    scrim = md_theme_light_scrim,
)

private val M3DarkColors = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = SurfaceTones.dark,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint,
    outlineVariant = md_theme_dark_outlineVariant,
    scrim = md_theme_dark_scrim,
)

@Composable
fun M3Theme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (!useDarkTheme) {
        M3LightColors
    } else {
        M3DarkColors
    }

    MaterialTheme(
        colorScheme = colors,
        typography = M3Typography,
        shapes = M3Shapes,
        content = content
    )
}

@Composable
fun TypesMaterialTheme(
    types: List<String>,
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val extendedTypesColors = mapTypeToColorScheme(types = types, isDark = useDarkTheme)

    MaterialTheme(
        colorScheme = extendedTypesColors,
        typography = M3Typography,
        shapes = M3Shapes,
        content = content
    )
}

@Composable
private fun mapTypeToColorScheme(
    types: List<String>,
    isDark: Boolean
): ColorScheme {
    val firstType = types[0]

    if (!isDark) {
        return when (Type.valueOf(firstType)) {
            Type.Grass -> M3LightColors.copy(
                primary = GrassTypeColors.primaryLight,
                surface = GrassTypeColors.primaryLight,
                onSurface = Color.White,
                surfaceVariant = GrassTypeColors.surfaceVariantLight
            )
            Type.Fire -> M3LightColors.copy(
                primary = FireTypeColors.primaryLight,
                surface = FireTypeColors.primaryLight,
                onSurface = Color.White,
                surfaceVariant = FireTypeColors.surfaceVariantLight
            )
            Type.Water -> M3LightColors.copy(
                primary = WaterTypeColors.primaryLight,
                surface = WaterTypeColors.primaryLight,
                onSurface = Color.White,
                surfaceVariant = WaterTypeColors.surfaceVariantLight
            )
            Type.Dragon -> M3LightColors.copy(
                primary = DragonTypeColors.primaryLight,
                surface = DragonTypeColors.primaryLight,
                onSurface = Color.White,
                surfaceVariant = DragonTypeColors.surfaceVariantLight
            )
            Type.Electric -> M3LightColors.copy(
                primary = ElectricTypeColors.primaryLight,
                surface = ElectricTypeColors.primaryLight,
                onSurface = Color.White,
                surfaceVariant = ElectricTypeColors.surfaceVariantLight
            )
            Type.Psychic -> M3LightColors.copy(
                primary = PsychicTypeColors.primaryLight,
                surface = PsychicTypeColors.primaryLight,
                onSurface = Color.White,
                surfaceVariant = PsychicTypeColors.surfaceVariantLight
            )
            else -> M3LightColors
        }
    } else {
        return when (Type.valueOf(firstType)) {
            Type.Grass -> M3DarkColors.copy(
                primary = GrassTypeColors.primaryDark,
                surface = GrassTypeColors.surfaceDark,
                onSurface = GrassTypeColors.onSurfaceDark,
                surfaceVariant = GrassTypeColors.surfaceVariantDark
            )
            Type.Fire -> M3DarkColors.copy(
                primary = FireTypeColors.primaryDark,
                surface = FireTypeColors.surfaceDark,
                onSurface = FireTypeColors.onSurfaceDark,
                surfaceVariant = FireTypeColors.surfaceVariantDark
            )
            Type.Water -> M3DarkColors.copy(
                primary = WaterTypeColors.primaryDark,
                surface = WaterTypeColors.surfaceDark,
                onSurface = WaterTypeColors.onSurfaceDark,
                surfaceVariant = WaterTypeColors.surfaceVariantDark
            )
            Type.Dragon -> M3DarkColors.copy(
                primary = DragonTypeColors.primaryDark,
                surface = DragonTypeColors.surfaceDark,
                onSurface = DragonTypeColors.onSurfaceDark,
                surfaceVariant = DragonTypeColors.surfaceVariantDark
            )
            Type.Electric -> M3DarkColors.copy(
                primary = ElectricTypeColors.primaryDark,
                surface = ElectricTypeColors.surfaceDark,
                onSurface = ElectricTypeColors.onSurfaceDark,
                surfaceVariant = ElectricTypeColors.surfaceVariantDark
            )
            Type.Psychic -> M3DarkColors.copy(
                primary = PsychicTypeColors.primaryDark,
                surface = PsychicTypeColors.surfaceDark,
                onSurface = PsychicTypeColors.onSurfaceDark,
                surfaceVariant = PsychicTypeColors.surfaceVariantDark
            )
            else -> M3DarkColors
        }
    }
}