@file:OptIn(ExperimentalFoundationApi::class)

package des.c5inco.pokedexer.ui.pokedex

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.GridItemSpan
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import des.c5inco.pokedexer.ui.common.PokeBall
import des.c5inco.pokedexer.ui.common.PokeBallBackground
import des.c5inco.pokedexer.ui.common.PokemonTypeLabels
import des.c5inco.pokedexer.ui.common.TypeLabelMetrics.Companion.SMALL
import des.c5inco.pokedexer.ui.entity.Data.Companion.SamplePokemons
import des.c5inco.pokedexer.ui.entity.Data.Companion.color
import des.c5inco.pokedexer.ui.entity.Pokemon
import des.c5inco.pokedexer.ui.theme.Theme.Companion.PokedexerTheme

@Composable
fun PokemonList(
    onPokemonSelected: (Pokemon) -> Unit = {}
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(32.dp),
        content = {
            item({ GridItemSpan(2) }) {
                Text(
                    text = "Pokedex",
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(
                        top = 64.dp,
                        bottom = 24.dp
                    )
                )
            }
            items(SamplePokemons.size) { index ->
                PokeDexCard(SamplePokemons[index], onPokemonSelected)
            }
        }
    )
}

@Composable
fun PokeDexCard(pokemon: Pokemon, onPokemonSelected: (Pokemon) -> Unit) {
    Surface(
        color = pokemon.color(),
        shape = RoundedCornerShape(16.dp)
    ) {
        PokeDexCardContent(
            modifier = Modifier.clickable {
                onPokemonSelected(pokemon)
            },
            pokemon = pokemon
        )
    }
}

@Composable
fun PokeDexCardContent(
    modifier: Modifier = Modifier,
    pokemon: Pokemon
) {
    Box(modifier.height(120.dp)) {
        Column(
            Modifier.padding(top = 8.dp, start = 12.dp)
        ) {
            PokemonName(pokemon.name)
            PokemonTypeLabels(pokemon.typeOfPokemon, SMALL)
        }
        Text(
            text = pokemon.id ?: "",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 8.dp, end = 12.dp)
                .graphicsLayer {
                    alpha = 0.1f
                }
        )
        PokeBall(
            Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 5.dp, y = 10.dp)
                .requiredSize(96.dp),
            Color.White,
            0.25f
        )
        pokemon.image?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = pokemon.name,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 8.dp, end = 8.dp)
                    .size(72.dp)
            )
        }
    }
}

@Composable
fun PokemonName(name: String?) {
    Text(
        text = name ?: "",
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = Color.White,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun PokemonListScreen(
    onPokemonSelected: (Pokemon) -> Unit = {}
) {
    Surface(Modifier.fillMaxSize()) {
        Box {
            PokeBallBackground(
                Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 90.dp, y = (-70).dp)
            )
            PokemonList {
                onPokemonSelected(it)
            }
        }
    }
}

@Preview
@Composable
private fun PokemonListPreview() {
    PokedexerTheme {
        PokemonListScreen()
    }
}
