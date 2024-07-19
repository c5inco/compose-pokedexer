package des.c5inco.pokedexer.ui.theme

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
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
import androidx.compose.runtime.derivedStateOf
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
import des.c5inco.pokedexer.model.Pokemon
import kotlinx.coroutines.launch

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

val AppPaletteStyles = listOf(
    PaletteStyle.Rainbow,
    PaletteStyle.TonalSpot,
    PaletteStyle.Vibrant,
    PaletteStyle.Expressive,
    PaletteStyle.Neutral
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PokemonTypeColorOverlay(
    modifier: Modifier = Modifier,
    paletteStyle: PaletteStyle = PaletteStyle.Rainbow,
    pokemon: Pokemon,
    content: @Composable () -> Unit
) {
    var activePaletteStyleIndex by remember { mutableIntStateOf(AppPaletteStyles.indexOf(paletteStyle)) }
    val activePaletteStyle by remember(activePaletteStyleIndex) { mutableStateOf(AppPaletteStyles[activePaletteStyleIndex]) }

    PokemonTypesTheme(
        types = pokemon.typeOfPokemon,
        paletteStyle = activePaletteStyle
    ) {
        Box(
            modifier = modifier.fillMaxSize()
        ) {
            content()

            val swatchColors = listOf(
                mapTypeToSeedColor(pokemon.typeOfPokemon),
                PokemonTypesTheme.colorScheme.primary,
                PokemonTypesTheme.colorScheme.surface,
                PokemonTypesTheme.colorScheme.onSurface,
                PokemonTypesTheme.colorScheme.surfaceVariant,
                MaterialTheme.colorScheme.secondary,
                MaterialTheme.colorScheme.primaryContainer,
                MaterialTheme.colorScheme.secondaryContainer
            )

            val palette by remember(pokemon, activePaletteStyle) {
                derivedStateOf {
                    swatchColors
                }
            }

            val popupAlpha = remember { Animatable(1f) }
            val popupYOffset = remember { Animatable(0f) }

            LaunchedEffect(activePaletteStyle) {
                popupAlpha.animateTo(1f)
                popupYOffset.animateTo(200f)
                launch {
                    popupAlpha.animateTo(0f, animationSpec = tween(300, 1000))
                }
                launch {
                    popupYOffset.animateTo(0f, animationSpec = tween(500, 1000))
                }
            }

            Text(
                text = activePaletteStyle.toString(),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .navigationBarsPadding()
                    .align(Alignment.BottomCenter)
                    .offset { IntOffset(x = 0, y = -popupYOffset.value.toInt()) }
                    .background(
                        MaterialTheme.colorScheme.surfaceContainerHigh,
                        RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )

            FlowRow(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
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
                    .clip(CircleShape)
                    .clickable {
                        if (activePaletteStyleIndex < AppPaletteStyles.size - 1) {
                            activePaletteStyleIndex++
                        } else {
                            activePaletteStyleIndex = 0
                        }
                    }
                    .padding(16.dp)
                    .align(Alignment.BottomCenter)
            ) {
                QuadrantCircle(swatchColors[0], swatchColors[1], swatchColors[2], swatchColors[3])
                QuadrantCircle(swatchColors[4], swatchColors[5], swatchColors[6], swatchColors[7])
                // palette.forEachIndexed { idx, color ->
                //     AnimatedContent(
                //         targetState = color,
                //         transitionSpec = {
                //             fadeIn(tween(300 + idx * 150)) togetherWith fadeOut()
                //         },
                //         label = "swatchColorTransition",
                //     ) { color ->
                //         ColorSwatch(
                //             color = color,
                //         )
                //     }
                // }
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

@Composable
fun QuadrantCircle(
    firstColor: Color,
    secondColor: Color,
    thirdColor: Color,
    fourthColor: Color
) {
    Canvas(modifier = Modifier.size(64.dp)) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val radius = canvasWidth / 2
        val size = Size(radius * 2, radius * 2)
        val offset = Offset(0f, 0f)

        drawArc(
            color = firstColor,
            startAngle = 180f,
            sweepAngle = 90f,
            useCenter = true,
            topLeft = offset,
            size = size
        )
        drawArc(
            color = secondColor,
            startAngle = 90f,
            sweepAngle = 90f,
            useCenter = true,
            topLeft = offset,
            size = size
        )
        drawArc(
            color = thirdColor,
            startAngle = 270f,
            sweepAngle = 90f,
            useCenter = true,
            topLeft = offset,
            size = size
        )
        drawArc(
            color = fourthColor,
            startAngle = 0f,
            sweepAngle = 90f,
            useCenter = true,
            topLeft = offset,
            size = size
        )
    }
}