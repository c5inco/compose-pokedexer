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
fun AppTheme(
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
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}

@Composable
fun PokemonTypesTheme(
    types: List<String>,
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val extendedTypesColors = mapTypeToColorScheme(types = types, isDark = useDarkTheme)

    MaterialTheme(
        colorScheme = extendedTypesColors,
        typography = AppTypography,
        shapes = AppShapes,
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
            Type.Bug -> M3LightColors.copy(
                primary = BugTypeColors.primaryLight,
                surface = BugTypeColors.surfaceLight,
                onSurface = BugTypeColors.onSurfaceLight,
                surfaceVariant = BugTypeColors.surfaceVariantLight
            )
            Type.Dark -> M3LightColors.copy(
                primary = DarkTypeColors.primaryLight,
                surface = DarkTypeColors.primaryLight,
                onSurface = Color.White,
                surfaceVariant = DarkTypeColors.surfaceVariantLight
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
                onSurface = ElectricTypeColors.onSurfaceLight,
                surfaceVariant = ElectricTypeColors.surfaceVariantLight
            )
            Type.Fighting -> M3LightColors.copy(
                primary = FightingTypeColors.primaryLight,
                surface = FightingTypeColors.primaryLight,
                onSurface = Color.White,
                surfaceVariant = FightingTypeColors.surfaceVariantLight
            )
            Type.Fire -> M3LightColors.copy(
                primary = FireTypeColors.primaryLight,
                surface = FireTypeColors.primaryLight,
                onSurface = Color.White,
                surfaceVariant = FireTypeColors.surfaceVariantLight
            )
            Type.Flying -> M3LightColors.copy(
                primary = FlyingTypeColors.primaryLight,
                surface = FireTypeColors.primaryLight,
                onSurface = Color.White,
                surfaceVariant = FireTypeColors.surfaceVariantLight
            )
            Type.Ghost -> M3LightColors.copy(
                primary = GhostTypeColors.primaryLight,
                surface = GhostTypeColors.primaryLight,
                onSurface = Color.White,
                surfaceVariant = GhostTypeColors.surfaceVariantLight
            )
            Type.Grass -> M3LightColors.copy(
                primary = GrassTypeColors.primaryLight,
                surface = GrassTypeColors.primaryLight,
                onSurface = Color.White,
                surfaceVariant = GrassTypeColors.surfaceVariantLight
            )
            Type.Ground -> M3LightColors.copy(
                primary = GroundTypeColors.primaryLight,
                surface = GroundTypeColors.primaryLight,
                onSurface = Color.White,
                surfaceVariant = GroundTypeColors.surfaceVariantLight
            )
            Type.Ice -> M3LightColors.copy(
                primary = IceTypeColors.primaryLight,
                surface = IceTypeColors.primaryLight,
                onSurface = IceTypeColors.onSurfaceLight,
                surfaceVariant = IceTypeColors.surfaceVariantLight
            )
            Type.Normal -> M3LightColors.copy(
                primary = NormalTypeColors.primaryLight,
                surface = NormalTypeColors.surfaceLight,
                onSurface = NormalTypeColors.onSurfaceLight,
                surfaceVariant = NormalTypeColors.surfaceVariantLight
            )
            Type.Poison -> M3LightColors.copy(
                primary = PoisonTypeColors.primaryLight,
                surface = PoisonTypeColors.primaryLight,
                onSurface = Color.White,
                surfaceVariant = PoisonTypeColors.surfaceVariantLight
            )
            Type.Psychic -> M3LightColors.copy(
                primary = PsychicTypeColors.primaryLight,
                surface = PsychicTypeColors.primaryLight,
                onSurface = Color.White,
                surfaceVariant = PsychicTypeColors.surfaceVariantLight
            )
            Type.Rock -> M3LightColors.copy(
                primary = RockTypeColors.primaryLight,
                surface = RockTypeColors.primaryLight,
                onSurface = RockTypeColors.onSurfaceLight,
                surfaceVariant = RockTypeColors.surfaceVariantLight
            )
            Type.Water -> M3LightColors.copy(
                primary = WaterTypeColors.primaryLight,
                surface = WaterTypeColors.primaryLight,
                onSurface = Color.White,
                surfaceVariant = WaterTypeColors.surfaceVariantLight
            )
            else -> M3LightColors
        }
    } else {
        return when (Type.valueOf(firstType)) {
            Type.Bug -> M3DarkColors.copy(
                primary = BugTypeColors.primaryDark,
                surface = BugTypeColors.surfaceDark,
                onSurface = BugTypeColors.onSurfaceDark,
                surfaceVariant = BugTypeColors.surfaceVariantDark
            )
            Type.Dark -> M3DarkColors.copy(
                primary = DarkTypeColors.primaryDark,
                surface = DarkTypeColors.surfaceDark,
                onSurface = DarkTypeColors.onSurfaceDark,
                surfaceVariant = DarkTypeColors.surfaceVariantDark
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
            Type.Fighting -> M3DarkColors.copy(
                primary = FightingTypeColors.primaryDark,
                surface = FightingTypeColors.surfaceDark,
                onSurface = FightingTypeColors.onSurfaceDark,
                surfaceVariant = FightingTypeColors.surfaceVariantDark
            )
            Type.Fire -> M3DarkColors.copy(
                primary = FireTypeColors.primaryDark,
                surface = FireTypeColors.surfaceDark,
                onSurface = FireTypeColors.onSurfaceDark,
                surfaceVariant = FireTypeColors.surfaceVariantDark
            )
            Type.Flying -> M3DarkColors.copy(
                primary = FlyingTypeColors.primaryDark,
                surface = FlyingTypeColors.surfaceDark,
                onSurface = FlyingTypeColors.onSurfaceDark,
                surfaceVariant = FlyingTypeColors.surfaceVariantDark
            )
            Type.Ghost -> M3DarkColors.copy(
                primary = GhostTypeColors.primaryDark,
                surface = GhostTypeColors.surfaceDark,
                onSurface = GhostTypeColors.onSurfaceDark,
                surfaceVariant = GhostTypeColors.surfaceVariantDark
            )
            Type.Grass -> M3DarkColors.copy(
                primary = GrassTypeColors.primaryDark,
                surface = GrassTypeColors.surfaceDark,
                onSurface = GrassTypeColors.onSurfaceDark,
                surfaceVariant = GrassTypeColors.surfaceVariantDark
            )
            Type.Ground -> M3DarkColors.copy(
                primary = GroundTypeColors.primaryDark,
                surface = GroundTypeColors.surfaceDark,
                onSurface = GroundTypeColors.onSurfaceDark,
                surfaceVariant = GroundTypeColors.surfaceVariantDark
            )
            Type.Ice -> M3DarkColors.copy(
                primary = IceTypeColors.primaryDark,
                surface = IceTypeColors.surfaceDark,
                onSurface = IceTypeColors.onSurfaceDark,
                surfaceVariant = IceTypeColors.surfaceVariantDark
            )
            Type.Normal -> M3DarkColors.copy(
                primary = NormalTypeColors.primaryDark,
                surface = NormalTypeColors.surfaceDark,
                onSurface = NormalTypeColors.onSurfaceDark,
                surfaceVariant = NormalTypeColors.surfaceVariantDark
            )
            Type.Poison -> M3DarkColors.copy(
                primary = PoisonTypeColors.primaryDark,
                surface = PoisonTypeColors.surfaceDark,
                onSurface = PoisonTypeColors.onSurfaceDark,
                surfaceVariant = PoisonTypeColors.surfaceVariantDark
            )
            Type.Psychic -> M3DarkColors.copy(
                primary = PsychicTypeColors.primaryDark,
                surface = PsychicTypeColors.surfaceDark,
                onSurface = PsychicTypeColors.onSurfaceDark,
                surfaceVariant = PsychicTypeColors.surfaceVariantDark
            )
            Type.Rock -> M3DarkColors.copy(
                primary = RockTypeColors.primaryDark,
                surface = RockTypeColors.surfaceDark,
                onSurface = RockTypeColors.onSurfaceDark,
                surfaceVariant = RockTypeColors.surfaceVariantDark
            )
            Type.Water -> M3DarkColors.copy(
                primary = WaterTypeColors.primaryDark,
                surface = WaterTypeColors.surfaceDark,
                onSurface = WaterTypeColors.onSurfaceDark,
                surfaceVariant = WaterTypeColors.surfaceVariantDark
            )
            else -> M3DarkColors
        }
    }
}