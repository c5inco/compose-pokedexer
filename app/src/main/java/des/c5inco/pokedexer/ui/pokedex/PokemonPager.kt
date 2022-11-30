package des.c5inco.pokedexer.ui.pokedex

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import des.c5inco.pokedexer.R
import des.c5inco.pokedexer.ui.theme.Theme.Companion.PokedexerTheme
import kotlin.math.absoluteValue

data class PokemonPagerUiState(
    val name: String,
    val resId: Int
)

val samplePagerList = listOf(
    PokemonPagerUiState(
        "Bulbasaur",
        R.drawable.poke001
    ),
    PokemonPagerUiState(
        "IvySaur",
        R.drawable.poke002
    ),
    PokemonPagerUiState(
        "Venusaur",
        R.drawable.poke003
    ),
    PokemonPagerUiState(
        "Charmander",
        R.drawable.poke004
    ),
    PokemonPagerUiState(
        "Charmeleon",
        R.drawable.poke005
    ),
    PokemonPagerUiState(
        "Charizard",
        R.drawable.poke006
    ),
)

@Preview
@Composable
fun PokemonPagerPreview() {
    PokedexerTheme {
        Surface {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                PokemonPager(samplePagerList)
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PokemonPager(
    pokemonList: List<PokemonPagerUiState>
) {
    // Display 10 items
    HorizontalPager(
        count = pokemonList.size,
        state = rememberPagerState(initialPage = pokemonList.size / 2),
        modifier = Modifier.fillMaxHeight(0.5f),
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) { page ->
        // Our page content
        val pokemon = pokemonList[page]
        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
        val scale = lerp(
            start = 0.8f,
            stop = 1f,
            fraction = 1f - pageOffset.coerceIn(0f, 1f)
        )
        val alpha = lerp(
            start = 0f,
            stop = 1f,
            fraction = 1f - pageOffset.coerceIn(0f, 1f)
        )

        Column(
            modifier = Modifier
                .padding(8.dp)
                .graphicsLayer {
                    scaleY = scale
                }
                .background(Color.Magenta)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = pokemon.resId),
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    color = Color.Black.copy(alpha),
                )
            )
            Text(
                text = pokemon.name,
                fontSize = 36.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}