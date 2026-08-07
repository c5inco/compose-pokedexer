package des.c5inco.pokedexer.ui.theme

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.materialkolor.PaletteStyle
import des.c5inco.pokedexer.shared.model.Type
import des.c5inco.pokedexer.shared.theme.PokemonTypeSeeds
import des.c5inco.pokedexer.shared.theme.getSeedColorForType
import kotlinx.coroutines.launch

private const val PALETTE_POPUP_OFFSET = 200f
private const val PALETTE_POPUP_FADE_DURATION_MILLIS = 300
private const val PALETTE_POPUP_OFFSET_DURATION_MILLIS = 500
private const val PALETTE_POPUP_DISPLAY_DURATION_MILLIS = 1000

private object MoveCategoryPalette {
    const val PHYSICAL_PRIMARY_DARK = 0xffE3300E
    const val PHYSICAL_SURFACE_DARK = 0xff561F14
    const val PHYSICAL_SURFACE_LIGHT = 0xffFFDAD3
    const val PHYSICAL_ON_SURFACE_DARK = 0xffFFDAD3
    const val PHYSICAL_ON_SURFACE_LIGHT = 0xff3A0A03

    const val SPECIAL_PRIMARY_DARK = 0xffC7BFFF
    const val SPECIAL_SURFACE_DARK = 0xff2F295F
    const val SPECIAL_SURFACE_LIGHT = 0xffE4DFFF
    const val SPECIAL_ON_SURFACE_DARK = 0xffE4DFFF
    const val SPECIAL_ON_SURFACE_LIGHT = 0xff1A1249

    const val STATUS_PRIMARY_DARK = 0xffFFB691
    const val STATUS_SURFACE_DARK = 0xff542102
    const val STATUS_SURFACE_LIGHT = 0xffFFDBCB
    const val STATUS_ON_SURFACE_DARK = 0xffFFDBCB
    const val STATUS_ON_SURFACE_LIGHT = 0xff341100
}

/**
 * PokemonColors object using shared module's seed colors. Provides Compose Color values for use in
 * Android UI.
 */
object PokemonColors {
    val Bug = Color(PokemonTypeSeeds.Bug)
    val Dark = Color(PokemonTypeSeeds.Dark)
    val Dragon = Color(PokemonTypeSeeds.Dragon)
    val Electric = Color(PokemonTypeSeeds.Electric)
    val Fairy = Color(PokemonTypeSeeds.Fairy)
    val Fighting = Color(PokemonTypeSeeds.Fighting)
    val Fire = Color(PokemonTypeSeeds.Fire)
    val Flying = Color(PokemonTypeSeeds.Flying)
    val Ghost = Color(PokemonTypeSeeds.Ghost)
    val Grass = Color(PokemonTypeSeeds.Grass)
    val Ground = Color(PokemonTypeSeeds.Ground)
    val Ice = Color(PokemonTypeSeeds.Ice)
    val Normal = Color(PokemonTypeSeeds.Normal)
    val Poison = Color(PokemonTypeSeeds.Poison)
    val Psychic = Color(PokemonTypeSeeds.Psychic)
    val Rock = Color(PokemonTypeSeeds.Rock)
    val Steel = Color(PokemonTypeSeeds.Steel)
    val Water = Color(PokemonTypeSeeds.Water)
}

/**
 * Helper function that wraps getSeedColorForType() and returns a Compose Color. For backwards
 * compatibility with existing code using mapTypeToSeedColor.
 */
fun mapTypeToSeedColor(types: List<String>): Color = Color(getSeedColorForType(types))

@Immutable
data class PokemonTypeColorScheme(
    val type: Type? = null,
    val primary: Color,
    val surface: Color,
    val onSurface: Color,
    val surfaceVariant: Color,
    val secondary: Color = primary,
    val tertiary: Color = primary,
)

val LocalPokemonTypeColorScheme = staticCompositionLocalOf {
    PokemonTypeColorScheme(
        primary = Color.Magenta,
        surface = Color.Magenta,
        onSurface = Color.Magenta,
        surfaceVariant = Color.Magenta,
    )
}

@Immutable
data class MoveCategoryColorScheme(val primary: Color, val surface: Color, val onSurface: Color)

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

val PhysicalColors =
    MoveCategoryColors(
        primaryDark = Color(MoveCategoryPalette.PHYSICAL_PRIMARY_DARK),
        primaryLight = PokemonColors.Fighting,
        surfaceDark = Color(MoveCategoryPalette.PHYSICAL_SURFACE_DARK),
        surfaceLight = Color(MoveCategoryPalette.PHYSICAL_SURFACE_LIGHT),
        onSurfaceDark = Color(MoveCategoryPalette.PHYSICAL_ON_SURFACE_DARK),
        onSurfaceLight = Color(MoveCategoryPalette.PHYSICAL_ON_SURFACE_LIGHT),
    )

val SpecialColors =
    MoveCategoryColors(
        primaryDark = Color(MoveCategoryPalette.SPECIAL_PRIMARY_DARK),
        primaryLight = PokemonColors.Flying,
        surfaceDark = Color(MoveCategoryPalette.SPECIAL_SURFACE_DARK),
        surfaceLight = Color(MoveCategoryPalette.SPECIAL_SURFACE_LIGHT),
        onSurfaceDark = Color(MoveCategoryPalette.SPECIAL_ON_SURFACE_DARK),
        onSurfaceLight = Color(MoveCategoryPalette.SPECIAL_ON_SURFACE_LIGHT),
    )

val StatusColors =
    MoveCategoryColors(
        primaryDark = Color(MoveCategoryPalette.STATUS_PRIMARY_DARK),
        primaryLight = PokemonColors.Fire,
        surfaceDark = Color(MoveCategoryPalette.STATUS_SURFACE_DARK),
        surfaceLight = Color(MoveCategoryPalette.STATUS_SURFACE_LIGHT),
        onSurfaceDark = Color(MoveCategoryPalette.STATUS_ON_SURFACE_DARK),
        onSurfaceLight = Color(MoveCategoryPalette.STATUS_ON_SURFACE_LIGHT),
    )

val AppPaletteStyles =
    listOf(
        PaletteStyle.TonalSpot,
        PaletteStyle.Rainbow,
        PaletteStyle.Vibrant,
        PaletteStyle.Expressive,
        PaletteStyle.Neutral,
    )

@Composable
fun PokemonTypeColorOverlay(
    types: List<String>,
    paletteStyle: PaletteStyle = PaletteStyle.TonalSpot,
    content: @Composable () -> Unit,
) {
    var activePaletteStyleIndex by remember {
        mutableIntStateOf(AppPaletteStyles.indexOf(paletteStyle))
    }
    val activePaletteStyle by
        remember(activePaletteStyleIndex) {
            mutableStateOf(AppPaletteStyles[activePaletteStyleIndex])
        }

    PokemonTypesTheme(types = types, paletteStyle = activePaletteStyle) {
        Box(modifier = Modifier.fillMaxSize()) {
            content()

            val pokemonTypeColorScheme = PokemonTypesTheme.colorScheme
            val materialColorScheme = MaterialTheme.colorScheme

            val popupAlpha = remember { Animatable(1f) }
            val popupYOffset = remember { Animatable(0f) }

            LaunchedEffect(activePaletteStyle) {
                popupAlpha.animateTo(1f)
                popupYOffset.animateTo(PALETTE_POPUP_OFFSET)
                launch {
                    popupAlpha.animateTo(
                        0f,
                        animationSpec =
                            tween(
                                PALETTE_POPUP_FADE_DURATION_MILLIS,
                                PALETTE_POPUP_DISPLAY_DURATION_MILLIS,
                            ),
                    )
                }
                launch {
                    popupYOffset.animateTo(
                        0f,
                        animationSpec =
                            tween(
                                PALETTE_POPUP_OFFSET_DURATION_MILLIS,
                                PALETTE_POPUP_DISPLAY_DURATION_MILLIS,
                            ),
                    )
                }
            }

            Text(
                text = activePaletteStyle.toString(),
                style = MaterialTheme.typography.titleLarge,
                modifier =
                    Modifier.padding(bottom = 32.dp)
                        .navigationBarsPadding()
                        .align(Alignment.BottomCenter)
                        .offset { IntOffset(x = 0, y = -popupYOffset.value.toInt()) }
                        .background(
                            MaterialTheme.colorScheme.surfaceContainerHigh,
                            RoundedCornerShape(4.dp),
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp),
            )

            FlowRow(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier =
                    Modifier.padding(16.dp)
                        .navigationBarsPadding()
                        .background(
                            color = MaterialTheme.colorScheme.surfaceContainerHigh,
                            shape = CircleShape,
                        )
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.outline,
                            shape = CircleShape,
                        )
                        .clip(CircleShape)
                        .clickable {
                            if (activePaletteStyleIndex < AppPaletteStyles.size - 1) {
                                activePaletteStyleIndex++
                            } else {
                                activePaletteStyleIndex = 0
                            }
                        }
                        .padding(16.dp)
                        .align(Alignment.BottomCenter),
            ) {
                QuadrantCircle(
                    Color(getSeedColorForType(types)),
                    pokemonTypeColorScheme.primary,
                    pokemonTypeColorScheme.surface,
                    pokemonTypeColorScheme.onSurface,
                )
                QuadrantCircle(
                    pokemonTypeColorScheme.surfaceVariant,
                    materialColorScheme.secondary,
                    materialColorScheme.primaryContainer,
                    materialColorScheme.secondaryContainer,
                )
            }
        }
    }
}

@Composable
fun QuadrantCircle(firstColor: Color, secondColor: Color, thirdColor: Color, fourthColor: Color) {
    Canvas(modifier = Modifier.size(40.dp)) {
        val canvasWidth = size.width
        val radius = canvasWidth / 2
        val size = Size(radius * 2, radius * 2)
        val offset = Offset(0f, 0f)

        drawArc(
            color = firstColor,
            startAngle = 180f,
            sweepAngle = 90f,
            useCenter = true,
            topLeft = offset,
            size = size,
        )
        drawArc(
            color = secondColor,
            startAngle = 90f,
            sweepAngle = 90f,
            useCenter = true,
            topLeft = offset,
            size = size,
        )
        drawArc(
            color = thirdColor,
            startAngle = 270f,
            sweepAngle = 90f,
            useCenter = true,
            topLeft = offset,
            size = size,
        )
        drawArc(
            color = fourthColor,
            startAngle = 0f,
            sweepAngle = 90f,
            useCenter = true,
            topLeft = offset,
            size = size,
        )
    }
}
