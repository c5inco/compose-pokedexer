package des.c5inco.pokedexer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.materialkolor.PaletteStyle
import com.materialkolor.ktx.contrastRatio
import com.materialkolor.ktx.darken
import com.materialkolor.ktx.lighten
import com.materialkolor.rememberDynamicColorScheme
import des.c5inco.pokedexer.shared.model.MoveCategory
import des.c5inco.pokedexer.shared.model.Type

@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = rememberDynamicColorScheme(
        seedColor = Color(0xff673AB7),
        isDark = useDarkTheme,
        isAmoled = false
    )

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
    paletteStyle: PaletteStyle = PaletteStyle.TonalSpot,
    content: @Composable () -> Unit
) {
    val seedColor = mapTypeToSeedColor(types = types)

    val kolorScheme = getDynamicColorScheme(
        seedColor = seedColor,
        paletteStyle = paletteStyle
    )

    val extendedTypesColors = mapDynamicPokemonColorScheme(
        seedColor = seedColor,
        colorScheme = kolorScheme,
    ).copy(
        type = types.getOrNull(0)?.let {
            try {
                Type.valueOf(it.replaceFirstChar { it.uppercase() })
            } catch (e: IllegalArgumentException) {
                null
            }
        }
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

object PokemonTypesTheme {
    val colorScheme: PokemonTypeColorScheme
        @Composable
        get() = LocalPokemonTypeColorScheme.current
}

@Composable
fun getDynamicColorScheme(
    seedColor: Color,
    paletteStyle: PaletteStyle,
    isDark: Boolean = isSystemInDarkTheme()
) = rememberDynamicColorScheme(
    seedColor = seedColor,
    isDark = isDark,
    isAmoled = false,
    style = paletteStyle
)

@Composable
fun mapDynamicPokemonColorScheme(
    seedColor: Color,
    colorScheme: ColorScheme,
    useDarkTheme: Boolean = isSystemInDarkTheme()
): PokemonTypeColorScheme {
    return if (useDarkTheme) {
        PokemonTypeColorScheme(
            primary = colorScheme.primaryContainer.darken(0.4f),
            surface = colorScheme.primaryContainer,
            onSurface = colorScheme.onSurface,
            surfaceVariant = colorScheme.onPrimary,
            secondary = colorScheme.secondary,
            tertiary = colorScheme.secondary,
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
            surfaceVariant = seedColor.lighten(0.7f),
            secondary = colorScheme.primary,
            tertiary = colorScheme.secondary,
        )
    }
}

fun mapTypeToSeedColor(
    types: List<String>,
): Color {
    val firstType = types.getOrNull(0) ?: return Color.Magenta

    return try {
        when (Type.valueOf(firstType.replaceFirstChar { it.uppercase() })) {
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
    } catch (e: IllegalArgumentException) {
        Color.Magenta
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
