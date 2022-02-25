package des.c5inco.pokedexer.ui.pokedex

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.GridItemSpan
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import des.c5inco.pokedexer.data.pokemon.LocalPokemonRepository
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.model.color
import des.c5inco.pokedexer.ui.common.PokeBall
import des.c5inco.pokedexer.ui.common.PokeBallBackground
import des.c5inco.pokedexer.ui.common.PokemonTypeLabels
import des.c5inco.pokedexer.ui.common.TypeLabelMetrics.Companion.SMALL
import des.c5inco.pokedexer.ui.common.formatId
import des.c5inco.pokedexer.ui.theme.Theme.Companion.PokedexerTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PokemonList(
    loading: Boolean = false,
    pokemon: List<Pokemon>,
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
                        top = 64.dp, bottom = 24.dp
                    )
                )
            }
            if (loading) {
                item({ GridItemSpan(2) }) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            color = Color.Black,
                            modifier = Modifier
                                .size(48.dp)
                                .padding(vertical = 24.dp)
                        )
                    }
                }
            } else {
                items(pokemon) { pokemon ->
                    PokeDexCard(pokemon, onPokemonSelected)
                }
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
            text = formatId(pokemon.id),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 10.dp, end = 12.dp)
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
        // pokemon.image?.let {
        //     Image(
        //         painter = painterResource(id = it),
        //         contentDescription = pokemon.name,
        //         modifier = Modifier
        //             .align(Alignment.BottomEnd)
        //             .padding(bottom = 8.dp, end = 8.dp)
        //             .size(72.dp)
        //     )
        // }
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
    viewModel: PokedexViewModel,
    onPokemonSelected: (Pokemon) -> Unit = {}
) {
    Surface(Modifier.fillMaxSize()) {
        Box {
            PokeBallBackground(
                Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 90.dp, y = (-70).dp)
            )

            PokemonList(
                loading = viewModel.uiState.loading,
                pokemon = viewModel.uiState.pokemon
            ) {
                onPokemonSelected(it)
            }
        }
    }
}

@Preview
@Composable
private fun PokemonListPreview() {
    PokedexerTheme {
        PokemonListScreen(
            PokedexViewModel(LocalPokemonRepository())
        )
    }
}
