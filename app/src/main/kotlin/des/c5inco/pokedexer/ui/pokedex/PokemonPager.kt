package des.c5inco.pokedexer.ui.pokedex

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.core.graphics.ColorUtils
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.shared.model.Pokemon
import des.c5inco.pokedexer.ui.common.PokemonImage
import des.c5inco.pokedexer.ui.theme.AppTheme
import kotlin.math.absoluteValue
import org.intellij.lang.annotations.Language

@Language("AGSL")
private val PROGRESSIVE_TINT_SHADER =
    """
    layout(color) uniform vec4 tintColor;
    uniform float progress;
    uniform shader contents; 

    vec4 main(in vec2 fragCoord) {
        vec4 currentValue = contents.eval(fragCoord);
        
        if (currentValue.w > 0) {
            return mix(currentValue, tintColor, progress);
        }            
        return currentValue;
    }
    """
        .trimIndent()

data class PokemonPagerConfiguration(
    val backgroundColor: Color,
    val foregroundColor: Color = Color.Black,
    val enabled: Boolean = true,
)

internal fun pokemonDetailsInitialPage(pokemonSet: List<Pokemon>, displayedPokemonId: Int): Int =
    pokemonSet.indexOfFirst { it.id == displayedPokemonId }.coerceAtLeast(0)

internal fun pokemonDetailsPokemonForPage(pokemonSet: List<Pokemon>, page: Int): Pokemon? =
    if (pokemonSet.isEmpty()) null else pokemonSet[page]

@Composable
fun PagerPokemonImage(
    modifier: Modifier = Modifier.size(200.dp),
    image: Int,
    description: String?,
    tint: Color,
    progress: Float,
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val shader = remember { RuntimeShader(PROGRESSIVE_TINT_SHADER) }

        PokemonImage(
            image = image,
            description = description,
            modifier =
                modifier.graphicsLayer {
                    shader.setColorUniform("tintColor", tint.toArgb())
                    shader.setFloatUniform("progress", progress)
                    renderEffect =
                        RenderEffect.createRuntimeShaderEffect(shader, "contents")
                            .asComposeRenderEffect()
                },
        )
    } else {
        Box {
            PokemonImage(image = image, description = description, modifier = modifier)
            PokemonImage(
                image = image,
                description = null,
                tint = tint,
                modifier = modifier.graphicsLayer { alpha = progress },
            )
        }
    }
}

@Composable
fun PokemonPager(
    modifier: Modifier = Modifier,
    pokemonList: List<Pokemon>,
    configuration: PokemonPagerConfiguration,
    pagerState: PagerState,
    pagerContent: @Composable BoxScope.(Pokemon, Float, Color) -> Unit,
) {
    val foregroundTint =
        Color(
            ColorUtils.compositeColors(
                configuration.foregroundColor.copy(alpha = 0.25f).toArgb(),
                configuration.backgroundColor.toArgb(),
            )
        )

    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {
        HorizontalPager(
            state = pagerState,
            key = { pokemonList[it].id },
            contentPadding = PaddingValues(horizontal = 92.dp),
            userScrollEnabled = configuration.enabled,
            modifier = Modifier.testTag("PokemonPager"),
        ) { page ->
            val pokemon = pokemonList[page]
            val pageOffset =
                ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction)
                    .absoluteValue
            val progress = pageOffset.coerceIn(0f, 1f)
            val scale = lerp(start = 0.5f, stop = 1f, fraction = 1f - progress)
            val yPos = lerp(start = 48f, stop = 0f, fraction = 1f - progress)

            Box(
                modifier =
                    Modifier.padding(top = 24.dp).graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        translationY = yPos
                    }
            ) {
                pagerContent(pokemon, progress, foregroundTint)
            }
        }
    }
}

@Preview
@Composable
fun PokemonPagerPreview() {
    AppTheme {
        Surface {
            Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                PokemonPager(
                    pokemonList = SamplePokemonData,
                    configuration =
                        PokemonPagerConfiguration(
                            backgroundColor = MaterialTheme.colorScheme.surface
                        ),
                    pagerState = rememberPagerState { SamplePokemonData.size },
                ) { pokemon, progress, tint ->
                    PagerPokemonImage(
                        image = pokemon.image,
                        description = pokemon.name,
                        tint = tint,
                        progress = progress,
                    )
                }
            }
        }
    }
}
