package des.c5inco.pokedexer.ui.pokedex

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.core.graphics.drawable.toBitmap
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.skydoves.landscapist.coil.CoilImage
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.ui.common.artworkUrl
import des.c5inco.pokedexer.ui.common.placeholderPokemonImage
import des.c5inco.pokedexer.ui.theme.PokedexerTheme
import org.intellij.lang.annotations.Language
import kotlin.math.absoluteValue

@Language("AGSL")
val PROGRESSIVE_TINT_SHADER = """
    layout(color) uniform vec4 backgroundColor;
    uniform float progress;
    uniform shader contents; 
    
    vec4 main(in vec2 fragCoord) {
        const vec4 overColor = vec4(0,0,0,0.4);
        vec4 currentValue = contents.eval(fragCoord);
        
        if (currentValue.w > 0) {
            return mix(
                currentValue,
                vec4(mix(overColor.rgb, backgroundColor.rgb, overColor.a), 1),
                progress
            );
        }            
        return currentValue;
    }
""".trimIndent()

@Composable
fun PokemonImage(
    modifier: Modifier = Modifier,
    image: Int,
    description: String?
) {
    CoilImage(
        imageModel = { artworkUrl(image) },
        previewPlaceholder = placeholderPokemonImage(image),
        loading = {
            CircularProgressIndicator()
        },
        success = { imageState ->
            imageState.drawable?.let {
                Image(
                    bitmap = it.toBitmap().asImageBitmap(),
                    contentDescription = description,
                    modifier = Modifier.matchParentSize()
                )
            }
        },
        modifier = modifier.size(200.dp)
    )
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun PokemonPager(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    pokemonList: List<Pokemon>,
    backgroundColor: Color,
    pagerState: PagerState = rememberPagerState(),
    pagerContent: @Composable (Pokemon) -> Unit
) {
    val shader = remember { RuntimeShader(PROGRESSIVE_TINT_SHADER) }

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        if (!loading) {
            HorizontalPager(
                count = pokemonList.size,
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 92.dp)
            ) { page ->
                val pokemon = pokemonList[page]
                val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                val progress = pageOffset.coerceIn(0f, 1f)
                val scale = lerp(
                    start = 0.5f, stop = 1f, fraction = 1f - progress
                )
                val yPos = lerp(
                    start = -100f, stop = 0f, fraction = 1f - progress
                )

                Column(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            translationY = yPos
                            shader.setColorUniform("backgroundColor", backgroundColor.toArgb())
                            shader.setFloatUniform("progress", progress)
                            renderEffect = RenderEffect.createRuntimeShaderEffect(
                                shader,
                                "contents"
                            ).asComposeRenderEffect()
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    pagerContent(pokemon)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
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
                ) {
                    PokemonImage(
                        image = it.image,
                        description = it.name,
                    )
                }
            }
        }
    }
}