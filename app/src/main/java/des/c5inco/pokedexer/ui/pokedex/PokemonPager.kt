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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.core.graphics.ColorUtils
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.ui.common.PokemonImage
import des.c5inco.pokedexer.ui.theme.PokedexerTheme
import org.intellij.lang.annotations.Language
import kotlin.math.absoluteValue

@Language("AGSL")
private val PROGRESSIVE_TINT_SHADER = """
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
""".trimIndent()

@Composable
fun PagerPokemonImage(
    modifier: Modifier = Modifier.size(200.dp),
    image: Int,
    description: String?,
    tint: Color,
    progress: Float
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val shader = remember { RuntimeShader(PROGRESSIVE_TINT_SHADER) }

        PokemonImage(
            image = image,
            description = description,
            modifier = modifier.graphicsLayer {
                shader.setColorUniform("tintColor", tint.toArgb())
                shader.setFloatUniform("progress", progress)
                renderEffect = RenderEffect.createRuntimeShaderEffect(
                    shader,
                    "contents"
                ).asComposeRenderEffect()
            }
        )
    } else {
        Box {
            PokemonImage(
                image = image,
                description = description,
                modifier = modifier
            )
            PokemonImage(
                image = image,
                description = null,
                tint = tint,
                modifier = modifier.graphicsLayer {
                    alpha = progress
                }
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PokemonPager(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    pokemonList: List<Pokemon>,
    backgroundColor: Color,
    enabled: Boolean = true,
    pagerState: PagerState = rememberPagerState(),
    pagerContent: @Composable BoxScope.(Pokemon, Float, Color) -> Unit
) {
    val foregroundTint = Color(
        ColorUtils.compositeColors(
            Color.Black.copy(alpha = 0.4f).toArgb(),
            backgroundColor.toArgb()
        )
    )

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        if (!loading) {
            HorizontalPager(
                count = pokemonList.size,
                state = pagerState,
                key = { it },
                contentPadding = PaddingValues(horizontal = 92.dp),
                userScrollEnabled = enabled,
            ) { page ->
                val pokemon = pokemonList[page]
                val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                val progress = pageOffset.coerceIn(0f, 1f)
                val scale = lerp(
                    start = 0.5f, stop = 1f, fraction = 1f - progress
                )
                val yPos = lerp(
                    start = 48f, stop = 0f, fraction = 1f - progress
                )

                Box(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            translationY = yPos
                        },
                ) {
                    pagerContent(pokemon, progress, foregroundTint)
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
fun PokemonPagerPreview() {
    PokedexerTheme {
        Surface {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                PokemonPager(
                    pokemonList = SamplePokemonData,
                    backgroundColor = MaterialTheme.colors.surface
                ) { it, progress, tint ->
                    PagerPokemonImage(
                        image = it.image,
                        description = it.name,
                        tint = tint,
                        progress = progress
                    )
                }
            }
        }
    }
}