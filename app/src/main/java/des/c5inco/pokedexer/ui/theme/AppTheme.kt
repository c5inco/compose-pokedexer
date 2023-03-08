package des.c5inco.pokedexer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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

    CompositionLocalProvider(
        LocalPokemonTypeColorScheme provides extendedTypesColors,
        LocalContentColor provides extendedTypesColors.onSurface
    ) {
        content()
    }
}

object PokemonTypesTheme {
    val colorScheme: PokemonTypeColorScheme
        @Composable
        get() = LocalPokemonTypeColorScheme.current
}

@Composable
private fun mapTypeToColorScheme(
    types: List<String>,
    isDark: Boolean
): PokemonTypeColorScheme {
    val firstType = types[0]

    if (!isDark) {
        return when (Type.valueOf(firstType)) {
            Type.Bug -> PokemonTypeColorScheme(
                primary = BugTypeColors.primaryLight,
                surface = BugTypeColors.surfaceLight,
                onSurface = BugTypeColors.onSurfaceLight,
                surfaceVariant = BugTypeColors.surfaceVariantLight
            )
            Type.Dark -> PokemonTypeColorScheme(
                primary = DarkTypeColors.primaryLight,
                surface = DarkTypeColors.primaryLight,
                onSurface = Color.White,
                surfaceVariant = DarkTypeColors.surfaceVariantLight
            )
            Type.Dragon -> PokemonTypeColorScheme(
                primary = DragonTypeColors.primaryLight,
                surface = DragonTypeColors.primaryLight,
                onSurface = Color.White,
                surfaceVariant = DragonTypeColors.surfaceVariantLight
            )
            Type.Electric -> PokemonTypeColorScheme(
                primary = ElectricTypeColors.primaryLight,
                surface = ElectricTypeColors.primaryLight,
                onSurface = ElectricTypeColors.onSurfaceLight,
                surfaceVariant = ElectricTypeColors.surfaceVariantLight
            )
            Type.Fairy -> PokemonTypeColorScheme(
                primary = FairyTypeColors.primaryLight,
                surface = FairyTypeColors.primaryLight,
                onSurface = FairyTypeColors.onSurfaceLight,
                surfaceVariant = FairyTypeColors.surfaceVariantLight
            )
            Type.Fighting -> PokemonTypeColorScheme(
                primary = FightingTypeColors.primaryLight,
                surface = FightingTypeColors.primaryLight,
                onSurface = Color.White,
                surfaceVariant = FightingTypeColors.surfaceVariantLight
            )
            Type.Fire -> PokemonTypeColorScheme(
                primary = FireTypeColors.primaryLight,
                surface = FireTypeColors.primaryLight,
                onSurface = Color.White,
                surfaceVariant = FireTypeColors.surfaceVariantLight
            )
            Type.Flying -> PokemonTypeColorScheme(
                primary = FlyingTypeColors.primaryLight,
                surface = FireTypeColors.primaryLight,
                onSurface = Color.White,
                surfaceVariant = FireTypeColors.surfaceVariantLight
            )
            Type.Ghost -> PokemonTypeColorScheme(
                primary = GhostTypeColors.primaryLight,
                surface = GhostTypeColors.primaryLight,
                onSurface = Color.White,
                surfaceVariant = GhostTypeColors.surfaceVariantLight
            )
            Type.Grass -> PokemonTypeColorScheme(
                primary = GrassTypeColors.primaryLight,
                surface = GrassTypeColors.primaryLight,
                onSurface = Color.White,
                surfaceVariant = GrassTypeColors.surfaceVariantLight
            )
            Type.Ground -> PokemonTypeColorScheme(
                primary = GroundTypeColors.primaryLight,
                surface = GroundTypeColors.primaryLight,
                onSurface = Color.White,
                surfaceVariant = GroundTypeColors.surfaceVariantLight
            )
            Type.Ice -> PokemonTypeColorScheme(
                primary = IceTypeColors.primaryLight,
                surface = IceTypeColors.primaryLight,
                onSurface = IceTypeColors.onSurfaceLight,
                surfaceVariant = IceTypeColors.surfaceVariantLight
            )
            Type.Normal -> PokemonTypeColorScheme(
                primary = NormalTypeColors.primaryLight,
                surface = NormalTypeColors.surfaceLight,
                onSurface = NormalTypeColors.onSurfaceLight,
                surfaceVariant = NormalTypeColors.surfaceVariantLight
            )
            Type.Poison -> PokemonTypeColorScheme(
                primary = PoisonTypeColors.primaryLight,
                surface = PoisonTypeColors.primaryLight,
                onSurface = Color.White,
                surfaceVariant = PoisonTypeColors.surfaceVariantLight
            )
            Type.Psychic -> PokemonTypeColorScheme(
                primary = PsychicTypeColors.primaryLight,
                surface = PsychicTypeColors.primaryLight,
                onSurface = Color.White,
                surfaceVariant = PsychicTypeColors.surfaceVariantLight
            )
            Type.Rock -> PokemonTypeColorScheme(
                primary = RockTypeColors.primaryLight,
                surface = RockTypeColors.primaryLight,
                onSurface = RockTypeColors.onSurfaceLight,
                surfaceVariant = RockTypeColors.surfaceVariantLight
            )
            Type.Water -> PokemonTypeColorScheme(
                primary = WaterTypeColors.primaryLight,
                surface = WaterTypeColors.primaryLight,
                onSurface = Color.White,
                surfaceVariant = WaterTypeColors.surfaceVariantLight
            )
            else -> LocalPokemonTypeColorScheme.current
        }
    } else {
        return when (Type.valueOf(firstType)) {
            Type.Bug -> PokemonTypeColorScheme(
                primary = BugTypeColors.primaryDark,
                surface = BugTypeColors.surfaceDark,
                onSurface = BugTypeColors.onSurfaceDark,
                surfaceVariant = BugTypeColors.surfaceVariantDark
            )
            Type.Dark -> PokemonTypeColorScheme(
                primary = DarkTypeColors.primaryDark,
                surface = DarkTypeColors.surfaceDark,
                onSurface = DarkTypeColors.onSurfaceDark,
                surfaceVariant = DarkTypeColors.surfaceVariantDark
            )
            Type.Dragon -> PokemonTypeColorScheme(
                primary = DragonTypeColors.primaryDark,
                surface = DragonTypeColors.surfaceDark,
                onSurface = DragonTypeColors.onSurfaceDark,
                surfaceVariant = DragonTypeColors.surfaceVariantDark
            )
            Type.Electric -> PokemonTypeColorScheme(
                primary = ElectricTypeColors.primaryDark,
                surface = ElectricTypeColors.surfaceDark,
                onSurface = ElectricTypeColors.onSurfaceDark,
                surfaceVariant = ElectricTypeColors.surfaceVariantDark
            )
            Type.Fairy -> PokemonTypeColorScheme(
                primary = FairyTypeColors.primaryDark,
                surface = FairyTypeColors.primaryDark,
                onSurface = FairyTypeColors.onSurfaceDark,
                surfaceVariant = FairyTypeColors.surfaceVariantDark
            )
            Type.Fighting -> PokemonTypeColorScheme(
                primary = FightingTypeColors.primaryDark,
                surface = FightingTypeColors.surfaceDark,
                onSurface = FightingTypeColors.onSurfaceDark,
                surfaceVariant = FightingTypeColors.surfaceVariantDark
            )
            Type.Fire -> PokemonTypeColorScheme(
                primary = FireTypeColors.primaryDark,
                surface = FireTypeColors.surfaceDark,
                onSurface = FireTypeColors.onSurfaceDark,
                surfaceVariant = FireTypeColors.surfaceVariantDark
            )
            Type.Flying -> PokemonTypeColorScheme(
                primary = FlyingTypeColors.primaryDark,
                surface = FlyingTypeColors.surfaceDark,
                onSurface = FlyingTypeColors.onSurfaceDark,
                surfaceVariant = FlyingTypeColors.surfaceVariantDark
            )
            Type.Ghost -> PokemonTypeColorScheme(
                primary = GhostTypeColors.primaryDark,
                surface = GhostTypeColors.surfaceDark,
                onSurface = GhostTypeColors.onSurfaceDark,
                surfaceVariant = GhostTypeColors.surfaceVariantDark
            )
            Type.Grass -> PokemonTypeColorScheme(
                primary = GrassTypeColors.primaryDark,
                surface = GrassTypeColors.surfaceDark,
                onSurface = GrassTypeColors.onSurfaceDark,
                surfaceVariant = GrassTypeColors.surfaceVariantDark
            )
            Type.Ground -> PokemonTypeColorScheme(
                primary = GroundTypeColors.primaryDark,
                surface = GroundTypeColors.surfaceDark,
                onSurface = GroundTypeColors.onSurfaceDark,
                surfaceVariant = GroundTypeColors.surfaceVariantDark
            )
            Type.Ice -> PokemonTypeColorScheme(
                primary = IceTypeColors.primaryDark,
                surface = IceTypeColors.surfaceDark,
                onSurface = IceTypeColors.onSurfaceDark,
                surfaceVariant = IceTypeColors.surfaceVariantDark
            )
            Type.Normal -> PokemonTypeColorScheme(
                primary = NormalTypeColors.primaryDark,
                surface = NormalTypeColors.surfaceDark,
                onSurface = NormalTypeColors.onSurfaceDark,
                surfaceVariant = NormalTypeColors.surfaceVariantDark
            )
            Type.Poison -> PokemonTypeColorScheme(
                primary = PoisonTypeColors.primaryDark,
                surface = PoisonTypeColors.surfaceDark,
                onSurface = PoisonTypeColors.onSurfaceDark,
                surfaceVariant = PoisonTypeColors.surfaceVariantDark
            )
            Type.Psychic -> PokemonTypeColorScheme(
                primary = PsychicTypeColors.primaryDark,
                surface = PsychicTypeColors.surfaceDark,
                onSurface = PsychicTypeColors.onSurfaceDark,
                surfaceVariant = PsychicTypeColors.surfaceVariantDark
            )
            Type.Rock -> PokemonTypeColorScheme(
                primary = RockTypeColors.primaryDark,
                surface = RockTypeColors.surfaceDark,
                onSurface = RockTypeColors.onSurfaceDark,
                surfaceVariant = RockTypeColors.surfaceVariantDark
            )
            Type.Water -> PokemonTypeColorScheme(
                primary = WaterTypeColors.primaryDark,
                surface = WaterTypeColors.surfaceDark,
                onSurface = WaterTypeColors.onSurfaceDark,
                surfaceVariant = WaterTypeColors.surfaceVariantDark
            )
            else -> LocalPokemonTypeColorScheme.current
        }
    }
}