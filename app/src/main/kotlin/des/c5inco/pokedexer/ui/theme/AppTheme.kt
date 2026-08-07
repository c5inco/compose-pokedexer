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
import des.c5inco.pokedexer.shared.theme.getSeedColorForType

private const val DARK_THEME_PRIMARY_DARKEN_AMOUNT = 0.4f
private const val LIGHT_THEME_COLOR_LIGHTEN_AMOUNT = 0.7f
private const val MINIMUM_SEED_COLOR_CONTRAST_RATIO = 2.2
private const val APP_THEME_SEED_COLOR = 0xff673AB7

@Composable
fun AppTheme(useDarkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors =
        rememberDynamicColorScheme(
            seedColor = Color(APP_THEME_SEED_COLOR),
            isDark = useDarkTheme,
            isAmoled = false,
        )

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        shapes = AppShapes,
        content = content,
    )
}

@Composable
fun PokemonTypesTheme(
    types: List<String>,
    paletteStyle: PaletteStyle = PaletteStyle.TonalSpot,
    content: @Composable () -> Unit,
) {
    // Use shared module's getSeedColorForType (returns Int) and convert to Color
    val seedColor = Color(getSeedColorForType(types))

    val kolorScheme = getDynamicColorScheme(seedColor = seedColor, paletteStyle = paletteStyle)

    val extendedTypesColors =
        mapDynamicPokemonColorScheme(seedColor = seedColor, colorScheme = kolorScheme)
            .copy(
                type =
                    types.firstOrNull()?.let { typeName ->
                        val normalizedTypeName = typeName.replaceFirstChar { it.uppercase() }
                        Type.entries.firstOrNull { it.name == normalizedTypeName }
                    }
            )

    CompositionLocalProvider(
        LocalPokemonTypeColorScheme provides extendedTypesColors,
        LocalContentColor provides extendedTypesColors.onSurface,
    ) {
        MaterialTheme(colorScheme = kolorScheme) { content() }
    }
}

object PokemonTypesTheme {
    val colorScheme: PokemonTypeColorScheme
        @Composable get() = LocalPokemonTypeColorScheme.current
}

@Composable
fun getDynamicColorScheme(
    seedColor: Color,
    paletteStyle: PaletteStyle,
    isDark: Boolean = isSystemInDarkTheme(),
) =
    rememberDynamicColorScheme(
        seedColor = seedColor,
        isDark = isDark,
        isAmoled = false,
        style = paletteStyle,
    )

@Composable
fun mapDynamicPokemonColorScheme(
    seedColor: Color,
    colorScheme: ColorScheme,
    useDarkTheme: Boolean = isSystemInDarkTheme(),
): PokemonTypeColorScheme {
    return if (useDarkTheme) {
        PokemonTypeColorScheme(
            primary = colorScheme.primaryContainer.darken(DARK_THEME_PRIMARY_DARKEN_AMOUNT),
            surface = colorScheme.primaryContainer,
            onSurface = colorScheme.onSurface,
            surfaceVariant = colorScheme.onPrimary,
            secondary = colorScheme.secondary,
            tertiary = colorScheme.secondary,
        )
    } else {
        PokemonTypeColorScheme(
            primary = seedColor.lighten(LIGHT_THEME_COLOR_LIGHTEN_AMOUNT),
            surface = seedColor,
            onSurface =
                if (
                    seedColor.contrastRatio(colorScheme.onSecondary) >
                        MINIMUM_SEED_COLOR_CONTRAST_RATIO
                ) {
                    colorScheme.onSecondary
                } else {
                    colorScheme.onSecondaryContainer
                },
            surfaceVariant = seedColor.lighten(LIGHT_THEME_COLOR_LIGHTEN_AMOUNT),
            secondary = colorScheme.primary,
            tertiary = colorScheme.secondary,
        )
    }
}

@Composable
fun MoveCategoryTheme(
    category: MoveCategory,
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit,
) {
    val extendedCategoryColors =
        mapCategoryToColorScheme(category = category, isDark = useDarkTheme)

    CompositionLocalProvider(
        LocalMoveCategoryColorScheme provides extendedCategoryColors,
        LocalContentColor provides
            if (extendedCategoryColors.onSurface != Color.Unspecified) {
                extendedCategoryColors.onSurface
            } else {
                contentColorFor(extendedCategoryColors.surface)
            },
    ) {
        content()
    }
}

object MoveCategoryTheme {
    val colorScheme: MoveCategoryColorScheme
        @Composable get() = LocalMoveCategoryColorScheme.current
}

@Composable
private fun mapCategoryToColorScheme(
    category: MoveCategory,
    isDark: Boolean,
): MoveCategoryColorScheme {
    if (!isDark) {
        return when (category) {
            MoveCategory.Physical ->
                MoveCategoryColorScheme(
                    primary = PhysicalColors.primaryLight,
                    surface = PhysicalColors.surfaceLight,
                    onSurface = PhysicalColors.onSurfaceLight,
                )
            MoveCategory.Special ->
                MoveCategoryColorScheme(
                    primary = SpecialColors.primaryLight,
                    surface = SpecialColors.surfaceLight,
                    onSurface = SpecialColors.onSurfaceLight,
                )
            MoveCategory.Status ->
                MoveCategoryColorScheme(
                    primary = StatusColors.primaryLight,
                    surface = StatusColors.surfaceLight,
                    onSurface = StatusColors.onSurfaceLight,
                )
        }
    } else {
        return when (category) {
            MoveCategory.Physical ->
                MoveCategoryColorScheme(
                    primary = PhysicalColors.primaryDark,
                    surface = PhysicalColors.surfaceDark,
                    onSurface = PhysicalColors.onSurfaceDark,
                )
            MoveCategory.Special ->
                MoveCategoryColorScheme(
                    primary = SpecialColors.primaryDark,
                    surface = SpecialColors.surfaceDark,
                    onSurface = SpecialColors.onSurfaceDark,
                )
            MoveCategory.Status ->
                MoveCategoryColorScheme(
                    primary = StatusColors.primaryDark,
                    surface = StatusColors.surfaceDark,
                    onSurface = StatusColors.onSurfaceDark,
                )
        }
    }
}
