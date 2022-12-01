package des.c5inco.pokedexer.ui.pokedex

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
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
import des.c5inco.pokedexer.ui.common.ImageState
import des.c5inco.pokedexer.ui.common.artworkUrl
import des.c5inco.pokedexer.ui.common.placeholderPokemonImage
import des.c5inco.pokedexer.ui.theme.Theme.Companion.PokedexerTheme
import kotlin.math.absoluteValue

@Composable
private fun PokemonImage(
    modifier: Modifier = Modifier,
    image: Int,
    description: String?
) {
    CoilImage(
        imageModel = artworkUrl(image),
        contentDescription = description,
        previewPlaceholder = placeholderPokemonImage(image),
        success = { imageState ->
            val currentState = remember { MutableTransitionState(ImageState.Loading) }
            currentState.targetState = ImageState.Loaded
            val transition = updateTransition(currentState, label = "imageLoad")
            val animateScale by transition.animateFloat(
                label = "scale",
                transitionSpec = { spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = 500f
                ) }
            ) { state ->
                if (state == ImageState.Loading) 0.8f else 1f
            }
            val animateOffsetY by transition.animateDp(
                label = "offsetY"
            ) { state ->
                if (state == ImageState.Loading) (48).dp else 0.dp
            }

            imageState.drawable?.let {
                Image(
                    bitmap = it.toBitmap().asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .matchParentSize()
                        // .scale(animateScale)
                        // .offset(y = animateOffsetY)
                )
            }
        },
        modifier = modifier.size(200.dp)
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PokemonPager(
    modifier: Modifier = Modifier,
    pokemonList: List<Pokemon>,
    pagerState: PagerState = rememberPagerState()
) {
    // Display 10 items
    HorizontalPager(
        count = pokemonList.size,
        state = pagerState,
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 88.dp)
    ) { page ->
        // Our page content
        val pokemon = pokemonList[page]
        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
        val scale = lerp(
            start = 0.7f,
            stop = 1f,
            fraction = 1f - pageOffset.coerceIn(0f, 1f)
        )
        val alpha = lerp(
            start = 1f,
            stop = .5f,
            fraction = pageOffset.coerceIn(0f, 1f)
        )

        Column(
            modifier = Modifier
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PokemonImage(
                image = pokemon.image,
                description = pokemon.name
            )
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
                PokemonPager(pokemonList = SamplePokemonData)
            }
        }
    }
}