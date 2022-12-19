package des.c5inco.pokedexer.ui.pokedex

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.data.pokemon.LocalPokemonRepository
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.ui.common.NavigationTopAppBar
import des.c5inco.pokedexer.ui.common.PokeBallBackground
import des.c5inco.pokedexer.ui.theme.PokedexerTheme

@Composable
fun PokemonList(
    modifier: Modifier = Modifier,
    listState: LazyGridState,
    loading: Boolean = false,
    title: @Composable () -> Unit,
    pokemon: List<Pokemon>,
    onPokemonSelected: (Pokemon) -> Unit = {},
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 64.dp),
        content = {
            item(span = { GridItemSpan(2) }) {
                title()
            }
            if (loading) {
                item(span = { GridItemSpan(2) }) {
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
                    PokeDexCard(
                        pokemon = pokemon,
                        onPokemonSelected = onPokemonSelected
                    )
                }
            }
        }
    )
}

@Composable
fun PokemonListScreen(
    viewModel: PokedexViewModel,
    onPokemonSelected: (Pokemon) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val listState = rememberLazyGridState()

    val topAppBarRevealThreshold = with(LocalDensity.current) { 64.dp.toPx() }
    val scrollOffset by remember {
        derivedStateOf {
            listState.firstVisibleItemScrollOffset
        }
    }

    val isGridAtTop by remember {
        derivedStateOf {
            scrollOffset < topAppBarRevealThreshold && listState.firstVisibleItemIndex == 0
        }
    }

    val topAppBarTitleRevealProgress by remember {
        derivedStateOf {
            if (isGridAtTop) {
                1f - (topAppBarRevealThreshold - scrollOffset) / topAppBarRevealThreshold
            } else {
                1f
            }
        }
    }

    val backgroundScrollThreshold = with(LocalDensity.current) { 40.dp.toPx() }
    val backgroundRevealProgress by remember {
        derivedStateOf {
            if (isGridAtTop) {
                (1f - (backgroundScrollThreshold - scrollOffset) / backgroundScrollThreshold).coerceIn(0f, 1f)
            } else {
                1f
            }
        }
    }

    Surface {
        Box(
            Modifier.fillMaxSize()
        ) {
            PokeBallBackground(
                Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 90.dp, y = (-70).dp)
            )
            PokemonList(
                modifier = Modifier.statusBarsPadding(),
                listState = listState,
                loading = viewModel.uiState.loading,
                title = {
                    Text(
                        text = "Pokedex",
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier
                            .padding(
                                top = 16.dp,
                                bottom = 24.dp
                            )
                            .graphicsLayer {
                                alpha = 1f - topAppBarTitleRevealProgress
                            }
                    )
                },
                pokemon = viewModel.uiState.pokemon,
                onPokemonSelected = onPokemonSelected
            )
            NavigationTopAppBar(
                modifier = Modifier
                    .drawBehind {
                        drawRect(color = Color.LightGray, alpha = backgroundRevealProgress)
                    }
                    .statusBarsPadding()
                    .padding(top = 16.dp)
                ,
                title = {
                    Text(
                        text = "Pokedex",
                        modifier = Modifier.graphicsLayer {
                            alpha = topAppBarTitleRevealProgress
                        }
                    )
                },
                onBackClick = onBackClick
            )
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
