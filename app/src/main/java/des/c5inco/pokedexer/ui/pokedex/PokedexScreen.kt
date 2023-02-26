package des.c5inco.pokedexer.ui.pokedex

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
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
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
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
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.ui.common.NavigationTopAppBar
import des.c5inco.pokedexer.ui.common.PokeBallBackground
import des.c5inco.pokedexer.ui.theme.AppTheme
import androidx.compose.material3.CircularProgressIndicator as M3CircularProgressIndicator
import androidx.compose.material3.MaterialTheme as M3Theme
import androidx.compose.material3.Surface as M3Surface
import androidx.compose.material3.Text as M3Text

@Composable
fun PokedexScreenRoute(
    viewModel: PokedexViewModel,
    onPokemonSelected: (Pokemon) -> Unit,
    onBackClick: () -> Unit
) {
    PokedexScreen(
        loading = viewModel.uiState.loading,
        pokemon = viewModel.uiState.pokemon,
        onPokemonSelected = onPokemonSelected,
        onBackClick = onBackClick
    )
}

@Composable
fun PokedexScreen(
    loading: Boolean,
    pokemon: List<Pokemon>,
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

    M3Surface {
        Box(
            Modifier.fillMaxSize()
        ) {
            PokeBallBackground(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 90.dp, y = (-70).dp),
                tint = M3Theme.colorScheme.primary.copy(alpha = 0.05f)
            )
            PokemonList(
                modifier = Modifier.statusBarsPadding(),
                listState = listState,
                loading = loading,
                title = {
                    M3Text(
                        text = "Pokedex",
                        style = M3Theme.typography.headlineMedium,
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
                pokemon = pokemon,
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
                    M3Text(
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

@Composable
fun PokemonList(
    modifier: Modifier = Modifier,
    listState: LazyGridState,
    loading: Boolean = false,
    title: @Composable () -> Unit,
    pokemon: List<Pokemon>,
    onPokemonSelected: (Pokemon) -> Unit = {},
) {
    val loaded = remember { MutableTransitionState(!loading) }

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
                        M3CircularProgressIndicator(
                            color = M3Theme.colorScheme.primary,
                            modifier = Modifier
                                .size(48.dp)
                                .padding(vertical = 24.dp)
                        )
                    }
                }
            } else {
                loaded.targetState = true

                itemsIndexed(pokemon) { idx, p ->
                    AnimatedVisibility(
                        visibleState = loaded,
                        enter = slideInVertically(
                            animationSpec = tween(
                                durationMillis = 500,
                                delayMillis = idx / 2 * 120
                            ),
                            initialOffsetY = { it / 2 }
                        ) + fadeIn(
                            animationSpec = tween(
                                durationMillis = 400,
                                delayMillis = idx / 2 * 150
                            ),
                        ),
                        exit = ExitTransition.None
                    ) {
                        PokeDexCard(
                            pokemon = p,
                            onPokemonSelected = onPokemonSelected
                        )
                    }
                }
            }
        }
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
@Composable
private fun PokedexScreenPreview() {
    AppTheme {
        PokedexScreen(
            loading = false,
            pokemon = SamplePokemonData
        )
    }
}