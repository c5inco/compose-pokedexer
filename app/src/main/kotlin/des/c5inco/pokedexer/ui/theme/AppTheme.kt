package des.c5inco.pokedexer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.materialkolor.PaletteStyle
import com.materialkolor.ktx.contrastRatio
import com.materialkolor.ktx.darken
import com.materialkolor.ktx.lighten
import com.materialkolor.rememberDynamicColorScheme
import des.c5inco.pokedexer.model.MoveCategory
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
    content: @Composable () -> Unit
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
                surface = FlyingTypeColors.primaryLight,
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
            Type.Steel -> PokemonTypeColorScheme(
                primary = SteelTypeColors.primaryLight,
                surface = SteelTypeColors.primaryLight,
                onSurface = SteelTypeColors.onSurfaceLight,
                surfaceVariant = SteelTypeColors.surfaceVariantLight
            )
            Type.Water -> PokemonTypeColorScheme(
                primary = WaterTypeColors.primaryLight,
                surface = WaterTypeColors.primaryLight,
                onSurface = Color.White,
                surfaceVariant = WaterTypeColors.surfaceVariantLight
            )
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
            Type.Steel -> PokemonTypeColorScheme(
                primary = SteelTypeColors.primaryDark,
                surface = SteelTypeColors.surfaceDark,
                onSurface = SteelTypeColors.onSurfaceDark,
                surfaceVariant = SteelTypeColors.surfaceVariantDark
            )
            Type.Water -> PokemonTypeColorScheme(
                primary = WaterTypeColors.primaryDark,
                surface = WaterTypeColors.surfaceDark,
                onSurface = WaterTypeColors.onSurfaceDark,
                surfaceVariant = WaterTypeColors.surfaceVariantDark
            )
        }
    }
}

@Composable
fun PokemonTypesKolorTheme(
    types: List<String>,
    content: @Composable () -> Unit
) {
    val seedColor = mapTypeToSeedColor(types = types)

    val kolorScheme = getDynamicColorScheme(seedColor)

    val extendedTypesColors = mapDynamicPokemonColorScheme(
        seedColor = seedColor,
        colorScheme = kolorScheme,
    )

    CompositionLocalProvider(
        LocalPokemonTypeColorScheme provides extendedTypesColors,
        LocalContentColor provides extendedTypesColors.onSurface
    ) {
        MaterialTheme(
            colorScheme = kolorScheme
        ) {
            content()
        }
    }
}

@Composable
fun getDynamicColorScheme(
    seedColor: Color,
    isDark: Boolean = isSystemInDarkTheme()
) = rememberDynamicColorScheme(
    seedColor = seedColor,
    isDark = isDark,
    style = PaletteStyle.Rainbow
)

@Composable
fun mapDynamicPokemonColorScheme(
    seedColor: Color,
    colorScheme: ColorScheme,
    useDarkTheme: Boolean = isSystemInDarkTheme()
): PokemonTypeColorScheme {
    return if (useDarkTheme) {
        PokemonTypeColorScheme(
            primary = colorScheme.primaryContainer.darken(0.5f),
            surface = colorScheme.primaryContainer,
            onSurface = colorScheme.onSurface,
            surfaceVariant = colorScheme.onPrimary
        )
    } else {
        PokemonTypeColorScheme(
            primary = seedColor.lighten(0.7f),
            surface = seedColor,
            onSurface = if (seedColor.contrastRatio(colorScheme.onSecondary) > 2.2) {
                colorScheme.onSecondary
            } else {
                colorScheme.onSecondaryContainer
            },
            surfaceVariant = seedColor.lighten(0.7f)
        )
    }
}

fun mapTypeToSeedColor(
    types: List<String>,
): Color {
    val firstType = types[0]

    return when (Type.valueOf(firstType)) {
        Type.Bug -> PokemonColors.Bug
        Type.Dark -> PokemonColors.Dark
        Type.Dragon -> PokemonColors.Dragon
        Type.Electric -> PokemonColors.Electric
        Type.Fairy -> PokemonColors.Fairy
        Type.Fighting -> PokemonColors.Fighting
        Type.Fire -> PokemonColors.Fire
        Type.Flying -> PokemonColors.Flying
        Type.Ghost -> PokemonColors.Ghost
        Type.Grass -> PokemonColors.Grass
        Type.Ground -> PokemonColors.Ground
        Type.Ice -> PokemonColors.Ice
        Type.Normal -> PokemonColors.Normal
        Type.Poison -> PokemonColors.Poison
        Type.Psychic -> PokemonColors.Psychic
        Type.Rock -> PokemonColors.Rock
        Type.Steel -> PokemonColors.Steel
        Type.Water -> PokemonColors.Water
    }
}

@Composable
fun MoveCategoryTheme(
    category: MoveCategory,
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val extendedCategoryColors = mapCategoryToColorScheme(category = category, isDark = useDarkTheme)

    CompositionLocalProvider(
        LocalMoveCategoryColorScheme provides extendedCategoryColors,
        LocalContentColor provides if (extendedCategoryColors.onSurface != Color.Unspecified) {
            extendedCategoryColors.onSurface
        } else {
            contentColorFor(extendedCategoryColors.surface)
        }
    ) {
        content()
    }
}

object MoveCategoryTheme {
    val colorScheme: MoveCategoryColorScheme
        @Composable
        get() = LocalMoveCategoryColorScheme.current
}

@Composable
private fun mapCategoryToColorScheme(
    category: MoveCategory,
    isDark: Boolean
): MoveCategoryColorScheme {
    if (!isDark) {
        return when (category) {
            MoveCategory.Physical ->
                MoveCategoryColorScheme(
                    primary = PhysicalColors.primaryLight,
                    surface = PhysicalColors.surfaceLight,
                    onSurface = PhysicalColors.onSurfaceLight
                )
            MoveCategory.Special ->
                MoveCategoryColorScheme(
                    primary = SpecialColors.primaryLight,
                    surface = SpecialColors.surfaceLight,
                    onSurface = SpecialColors.onSurfaceLight
                )
            MoveCategory.Status ->
                MoveCategoryColorScheme(
                    primary = StatusColors.primaryLight,
                    surface = StatusColors.surfaceLight,
                    onSurface = StatusColors.onSurfaceLight
                )
        }
    } else {
        return when (category) {
            MoveCategory.Physical ->
                MoveCategoryColorScheme(
                    primary = PhysicalColors.primaryDark,
                    surface = PhysicalColors.surfaceDark,
                    onSurface = PhysicalColors.onSurfaceDark
                )
            MoveCategory.Special ->
                MoveCategoryColorScheme(
                    primary = SpecialColors.primaryDark,
                    surface = SpecialColors.surfaceDark,
                    onSurface = SpecialColors.onSurfaceDark
                )
            MoveCategory.Status ->
                MoveCategoryColorScheme(
                    primary = StatusColors.primaryDark,
                    surface = StatusColors.surfaceDark,
                    onSurface = StatusColors.onSurfaceDark
                )
        }
    }
}