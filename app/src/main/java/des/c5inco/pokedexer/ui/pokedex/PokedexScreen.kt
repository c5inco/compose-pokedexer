package des.c5inco.pokedexer.ui.pokedex

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.R
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.ui.common.NavigationTopAppBar
import des.c5inco.pokedexer.ui.common.PokeBallBackground
import des.c5inco.pokedexer.ui.theme.AppTheme

@Composable
fun PokedexScreenRoute(
    viewModel: PokedexViewModel,
    onPokemonSelected: (Pokemon) -> Unit,
    onBackClick: () -> Unit
) {
    PokedexScreen(
        loading = viewModel.uiState.loading,
        pokemon = viewModel.uiState.pokemon,
        favorites = viewModel.favorites,
        onPokemonSelected = onPokemonSelected,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalAnimationApi::class)
@Composable
fun PokedexScreen(
    loading: Boolean,
    pokemon: List<Pokemon>,
    favorites: List<Pokemon>,
    onPokemonSelected: (Pokemon) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val listState = rememberLazyGridState()
    var showFilterMenu by remember { mutableStateOf(false) }

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
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 90.dp, y = (-70).dp),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
            )
            PokemonList(
                modifier = Modifier.statusBarsPadding(),
                listState = listState,
                loading = loading,
                title = {
                    Text(
                        text = "Pokedex",
                        style = MaterialTheme.typography.headlineMedium,
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
                favorites = favorites,
                onPokemonSelected = onPokemonSelected
            )

            val navBarCollapsedColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)

            NavigationTopAppBar(
                modifier = Modifier
                    .drawBehind {
                        drawRect(color = navBarCollapsedColor, alpha = backgroundRevealProgress)
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

            AnimatedVisibility(
                visible = showFilterMenu,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.matchParentSize()
            ) {
                Box(
                    Modifier.background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.5f))
                )
            }

            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(WindowInsets.navigationBars.asPaddingValues())
                    .padding(bottom = 24.dp, end = 24.dp)
            ) {
                FilterMenu(visible = showFilterMenu)
                Spacer(Modifier.height(16.dp))
                FloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.primary,
                    onClick = { showFilterMenu = !showFilterMenu },
                ) {
                    AnimatedContent(
                        targetState = showFilterMenu
                    ) { targetState ->
                        if (targetState) {
                            Icon(
                                painterResource(id = R.drawable.ic_close),
                                contentDescription = "Hide filters",
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_filter),
                                contentDescription = "Show filters",
                            )
                        }
                    }
                }
            }
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
    favorites: List<Pokemon>,
    onPokemonSelected: (Pokemon) -> Unit = {},
) {
    val loaded = remember { MutableTransitionState(!loading) }

    LazyVerticalGrid(
        modifier = modifier.testTag("PokedexLazyGrid"),
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
                            color = MaterialTheme.colorScheme.primary,
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
                            isFavorite = favorites.contains(p),
                            onPokemonSelected = onPokemonSelected
                        )
                    }
                }
            }
        }
    )
}

@Composable
private fun FilterMenu(
    modifier: Modifier = Modifier,
    visible: Boolean,
    onMenuItemClick: () -> Unit = {},
) {
    AnimatedVisibility(
        visible = visible,
        enter = EnterTransition.None,
        exit = ExitTransition.None,
        label = "Filter menu",
        modifier = modifier
    ) {
        ProvideTextStyle(
            value = LocalTextStyle.current.merge(
                TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
            )
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.End,
            ) {
                FilterMenuItem(
                    index = 0,
                    onClick = onMenuItemClick
                ) {
                    Text("Favorite Pokemon")
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }
                FilterMenuItem(
                    index = 1,
                    onClick = onMenuItemClick
                ) {
                    Text("All types")
                    Icon(
                        painter = painterResource(id = R.drawable.ic_genetics),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun AnimatedVisibilityScope.FilterMenuItem(
    modifier: Modifier = Modifier,
    index: Int,
    onClick: () -> Unit = {},
    content: @Composable RowScope.() -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .animateEnterExit(
                enter = fadeIn(
                    animationSpec = tween(
                        durationMillis = 200,
                        delayMillis = index * 15 + 60
                    )
                ) +
                        slideInVertically(
                            initialOffsetY = { it / 2 },
                            animationSpec = tween(
                                durationMillis = 240,
                                delayMillis = index * 50 + 60
                            )
                        ),
                exit = fadeOut(animationSpec = spring(stiffness = Spring.StiffnessMedium)) +
                        slideOutVertically(targetOffsetY = { it / 2 }),
                label = "Filter menu item"
            )
            .clip(RoundedCornerShape(100))
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.surface)
            .height(36.dp)
            .padding(horizontal = 18.dp)
    ) {
        content()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
@Composable
private fun PokedexScreenPreview() {
    AppTheme {
        PokedexScreen(
            loading = false,
            pokemon = SamplePokemonData,
            favorites = emptyList()
        )
    }
}