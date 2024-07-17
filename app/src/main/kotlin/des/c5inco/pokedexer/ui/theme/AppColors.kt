package des.c5inco.pokedexer.ui.theme

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.materialkolor.PaletteStyle
import des.c5inco.pokedexer.model.Pokemon

val md_theme_light_primary = Color(0xFF4855B5)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFDFE0FF)
val md_theme_light_onPrimaryContainer = Color(0xFF000C62)
val md_theme_light_secondary = Color(0xFF5B5D72)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFFE0E0F9)
val md_theme_light_onSecondaryContainer = Color(0xFF181A2C)
val md_theme_light_tertiary = Color(0xFF77536C)
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFFFD7F0)
val md_theme_light_onTertiaryContainer = Color(0xFF2D1127)
val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_background = Color(0xFFFFFBFF)
val md_theme_light_onBackground = Color(0xFF1B1B1F)
val md_theme_light_surface = Color(0xFFFFFBFF)
val md_theme_light_onSurface = Color(0xFF1B1B1F)
val md_theme_light_surfaceVariant = Color(0xFFE3E1EC)
val md_theme_light_onSurfaceVariant = Color(0xFF46464F)
val md_theme_light_outline = Color(0xFF777680)
val md_theme_light_inverseOnSurface = Color(0xFFF3F0F4)
val md_theme_light_inverseSurface = Color(0xFF303034)
val md_theme_light_inversePrimary = Color(0xFFBCC2FF)
val md_theme_light_shadow = Color(0xFF000000)
val md_theme_light_surfaceTint = Color(0xFF4855B5)
val md_theme_light_outlineVariant = Color(0xFFC7C5D0)
val md_theme_light_scrim = Color(0xFF000000)

val md_theme_dark_primary = Color(0xFFBCC2FF)
val md_theme_dark_onPrimary = Color(0xFF142285)
val md_theme_dark_primaryContainer = Color(0xFF2F3C9C)
val md_theme_dark_onPrimaryContainer = Color(0xFFDFE0FF)
val md_theme_dark_secondary = Color(0xFFC4C5DD)
val md_theme_dark_onSecondary = Color(0xFF2D2F42)
val md_theme_dark_secondaryContainer = Color(0xFF434559)
val md_theme_dark_onSecondaryContainer = Color(0xFFE0E0F9)
val md_theme_dark_tertiary = Color(0xFFE6BAD6)
val md_theme_dark_onTertiary = Color(0xFF45263D)
val md_theme_dark_tertiaryContainer = Color(0xFF5D3C54)
val md_theme_dark_onTertiaryContainer = Color(0xFFFFD7F0)
val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val md_theme_dark_background = Color(0xFF1B1B1F)
val md_theme_dark_onBackground = Color(0xFFE4E1E6)
val md_theme_dark_surface = Color(0xFF1B1B1F)
val md_theme_dark_onSurface = Color(0xFFE4E1E6)
val md_theme_dark_surfaceVariant = Color(0xFF46464F)
val md_theme_dark_onSurfaceVariant = Color(0xFFC7C5D0)
val md_theme_dark_outline = Color(0xFF90909A)
val md_theme_dark_inverseOnSurface = Color(0xFF1B1B1F)
val md_theme_dark_inverseSurface = Color(0xFFE4E1E6)
val md_theme_dark_inversePrimary = Color(0xFF4855B5)
val md_theme_dark_shadow = Color(0xFF000000)
val md_theme_dark_surfaceTint = Color(0xFFBCC2FF)
val md_theme_dark_outlineVariant = Color(0xFF46464F)
val md_theme_dark_scrim = Color(0xFF000000)

class SurfaceTones {
    companion object {
        val light2 = Color(0xffF0EEF9)
        val dark = md_theme_dark_surface
    }
}

class PokemonColors {
    companion object {
        val Bug = Color(0xffaabb22)
        val Dark = Color(0xff775544)
        val Dragon = Color(0xff7766EE)
        val Electric = Color(0xffF0C03E)
        val Fairy = Color(0xffee99ee)
        val Fighting = Color(0xffbb5544)
        val Fire = Color(0xffff4422)
        val Flying = Color(0xff8899ff)
        val Ghost = Color(0xff9F5BBA)
        val Grass = Color(0xff4FC1A6)
        val Ground = Color(0xff775544)
        val Ice = Color(0xff66ccff)
        val Normal = Color(0xffaaaa99)
        val Poison = Color(0xffaa5599)
        val Psychic = Color(0xffff5599)
        val Rock = Color(0xffBBAA66)
        val Water = Color(0xff429BED)
        val Steel = Color(0xffaaaabb)
    }
}

@Immutable
data class PokemonTypeColorScheme(
    val primary: Color,
    val surface: Color,
    val onSurface: Color,
    val surfaceVariant: Color
)

val LocalPokemonTypeColorScheme = staticCompositionLocalOf {
    PokemonTypeColorScheme(
        primary = Color.Magenta,
        surface = Color.Magenta,
        onSurface = Color.Magenta,
        surfaceVariant = Color.Magenta
    )
}

@Immutable
data class MoveCategoryColorScheme(
    val primary: Color,
    val surface: Color,
    val onSurface: Color,
)

val LocalMoveCategoryColorScheme = staticCompositionLocalOf {
    MoveCategoryColorScheme(
        primary = Color.Magenta,
        surface = Color.Magenta,
        onSurface = Color.Magenta,
    )
}

@Immutable
data class MoveCategoryColors(
    val primaryDark: Color,
    val primaryLight: Color,
    val surfaceDark: Color,
    val surfaceLight: Color,
    val onSurfaceDark: Color = Color.Unspecified,
    val onSurfaceLight: Color = Color.Unspecified,
)

val PhysicalColors = MoveCategoryColors(
    primaryDark = Color(0xffE3300E),
    primaryLight = PokemonColors.Fire,
    surfaceDark = Color(0xff561F14),
    surfaceLight = Color(0xffFFDAD3),
    onSurfaceDark = Color(0xffFFDAD3),
    onSurfaceLight = Color(0xff3A0A03)
)

val SpecialColors = MoveCategoryColors(
    primaryDark = Color(0xffC7BFFF),
    primaryLight = PokemonColors.Dragon,
    surfaceDark = Color(0xff2F295F),
    surfaceLight = Color(0xffE4DFFF),
    onSurfaceDark = Color(0xffE4DFFF),
    onSurfaceLight = Color(0xff1A1249)
)

val StatusColors = MoveCategoryColors(
    primaryDark = Color(0xffFFB691),
    primaryLight = PokemonColors.Dark,
    surfaceDark = Color(0xff542102),
    surfaceLight = Color(0xffFFDBCB),
    onSurfaceDark = Color(0xffFFDBCB),
    onSurfaceLight = Color(0xff341100)
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PokemonTypeColorOverlay(
    modifier: Modifier = Modifier,
    paletteStyle: PaletteStyle = PaletteStyle.Rainbow,
    pokemon: Pokemon,
    content: @Composable () -> Unit
) {

    PokemonTypesTheme(
        types = pokemon.typeOfPokemon,
        paletteStyle = paletteStyle
    ) {
        Box(
            modifier = modifier.fillMaxSize()
        ) {
            content()

            val swatchColors = listOf(
                PokemonTypesTheme.colorScheme.primary,
                PokemonTypesTheme.colorScheme.surface,
                PokemonTypesTheme.colorScheme.onSurface,
                PokemonTypesTheme.colorScheme.surfaceVariant,
                MaterialTheme.colorScheme.secondary,
                MaterialTheme.colorScheme.primaryContainer,
                MaterialTheme.colorScheme.secondaryContainer
            )

            val palette by remember(pokemon) {
                derivedStateOf {
                    swatchColors
                }
            }

            FlowRow(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(16.dp)
                    .navigationBarsPadding()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainerHigh,
                        shape = CircleShape
                    )
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = CircleShape
                    )
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomCenter)
            ) {
                palette.forEachIndexed { idx, color ->
                    AnimatedContent(
                        targetState = color,
                        transitionSpec = {
                            fadeIn(tween(300 + idx * 150)) togetherWith fadeOut()
                        },
                        label = "swatchColorTransition",
                    ) { color ->
                        ColorSwatch(
                            color = color,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ColorSwatch(
    modifier: Modifier = Modifier,
    color: Color,
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .background(color, CircleShape)
    )
}