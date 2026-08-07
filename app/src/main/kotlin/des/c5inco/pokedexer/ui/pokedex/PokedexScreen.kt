package des.c5inco.pokedexer.ui.pokedex

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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.shared.model.Generation
import des.c5inco.pokedexer.shared.model.Pokemon
import des.c5inco.pokedexer.shared.model.Type
import des.c5inco.pokedexer.ui.common.LoadingIndicator
import des.c5inco.pokedexer.ui.common.Pokeball
import des.c5inco.pokedexer.ui.theme.AppTheme

private const val SELECTED_POKEMON_SCROLL_OFFSET = -100

@Composable
fun PokedexScreenRoute(
    viewModel: PokedexViewModel,
    onPokemonSelected: (Pokemon) -> Unit,
    pastPokemonSelected: Int? = null,
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val showFavorites by viewModel.showFavorites.collectAsStateWithLifecycle()
    val typeFilter by viewModel.typeFilters.collectAsStateWithLifecycle()
    val generationFilter by viewModel.generationFilters.collectAsStateWithLifecycle()

    PokedexScreen(
        screenState =
            PokedexScreenState(
                content = state,
                showFavorites = showFavorites,
                typeFilter = typeFilter,
                generationFilter = generationFilter,
                pastPokemonSelected = pastPokemonSelected,
            ),
        callbacks =
            PokedexScreenCallbacks(
                onPokemonSelected = onPokemonSelected,
                onMenuItemClick = { event -> viewModel.onFilterMenuEvent(event) },
                onBackClick = onBackClick,
            ),
    )
}

private fun PokedexViewModel.onFilterMenuEvent(event: FilterMenuEvent) {
    when (event) {
        is FilterMenuEvent.ToggleFavorites -> toggleFavorites()
        is FilterMenuEvent.FilterTypes -> filterByType(event.typeToFilter)
        is FilterMenuEvent.FilterGeneration -> filterByGeneration(event.generationToFilter)
        is FilterMenuEvent.ShowTypes -> Unit
        is FilterMenuEvent.ShowGenerations -> Unit
    }
}

data class PokedexScreenState(
    val content: PokedexUiState,
    val showFavorites: Boolean = false,
    val typeFilter: Type? = null,
    val generationFilter: Generation? = null,
    val pastPokemonSelected: Int? = null,
)

data class PokedexScreenCallbacks(
    val onPokemonSelected: (Pokemon) -> Unit = {},
    val onMenuItemClick: (FilterMenuEvent) -> Unit = {},
    val onBackClick: () -> Unit = {},
)

private data class PokedexLayoutState(
    val screen: PokedexScreenState,
    val listState: LazyGridState,
    val filterMenuState: FilterMenuState,
    val innerPadding: PaddingValues,
)

@Composable
fun PokedexScreen(
    screenState: PokedexScreenState,
    callbacks: PokedexScreenCallbacks = PokedexScreenCallbacks(),
) {
    val listState =
        rememberSaveable(
            screenState.typeFilter,
            screenState.generationFilter,
            screenState.showFavorites,
            saver = LazyGridState.Saver,
        ) {
            LazyGridState()
        }
    var filterMenuState by remember { mutableStateOf(FilterMenuState.Hidden) }
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
            state =
                rememberSaveable(
                    screenState.typeFilter,
                    screenState.generationFilter,
                    screenState.showFavorites,
                    saver = TopAppBarState.Saver,
                ) {
                    TopAppBarState(-Float.MAX_VALUE, 0f, 0f)
                }
        )

    RestoreSelectedPokemon(screenState, listState)

    Scaffold(
        topBar = { PokedexTopAppBar(scrollBehavior, callbacks.onBackClick) },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        PokedexBody(
            layoutState = PokedexLayoutState(screenState, listState, filterMenuState, innerPadding),
            callbacks = callbacks,
            onFilterMenuStateChange = { filterMenuState = it },
        )
    }
}

@Composable
private fun RestoreSelectedPokemon(screenState: PokedexScreenState, listState: LazyGridState) {
    LaunchedEffect(screenState.pastPokemonSelected, screenState.content is PokedexUiState.Ready) {
        val selectedId = screenState.pastPokemonSelected
        val readyState = screenState.content as? PokedexUiState.Ready
        if (selectedId != null && readyState != null) {
            val index = readyState.pokemon.indexOfFirst { it.id == selectedId }
            val isVisible = listState.layoutInfo.visibleItemsInfo.any { it.key == selectedId }
            if (index != -1 && !isVisible) {
                listState.scrollToItem(index, SELECTED_POKEMON_SCROLL_OFFSET)
            }
        }
    }
}

@Composable
private fun PokedexTopAppBar(scrollBehavior: TopAppBarScrollBehavior, onBackClick: () -> Unit) {
    MediumTopAppBar(
        title = { Text("Pokemon") },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0f)
            ),
        scrollBehavior = scrollBehavior,
    )
}

@Composable
private fun PokedexBody(
    layoutState: PokedexLayoutState,
    callbacks: PokedexScreenCallbacks,
    onFilterMenuStateChange: (FilterMenuState) -> Unit,
) {
    Box(Modifier.fillMaxSize()) {
        Pokeball(
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
            modifier = Modifier.size(256.dp).align(Alignment.TopEnd).offset(x = 90.dp, y = (-72).dp),
        )
        PokedexContent(layoutState, callbacks.onPokemonSelected)
        FilterScrim(layoutState.filterMenuState != FilterMenuState.Hidden) {
            onFilterMenuStateChange(FilterMenuState.Hidden)
        }
        FilterControls(
            state =
                FilterMenuModel(
                    showFavorites = layoutState.screen.showFavorites,
                    typeFilter = layoutState.screen.typeFilter,
                    generationFilter = layoutState.screen.generationFilter,
                    menuState = layoutState.filterMenuState,
                ),
            onMenuItemClick = { event ->
                onFilterMenuStateChange(layoutState.filterMenuState.after(event))
                if (!event.opensSubmenu) callbacks.onMenuItemClick(event)
            },
            onButtonClick = {
                onFilterMenuStateChange(layoutState.filterMenuState.afterButtonClick())
            },
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

@Composable
private fun PokedexContent(layoutState: PokedexLayoutState, onPokemonSelected: (Pokemon) -> Unit) {
    Column(
        modifier =
            Modifier.padding(top = layoutState.innerPadding.calculateTopPadding()).fillMaxWidth()
    ) {
        // Animated state transitions remain disabled due to a recomposition or performance issue
        // that still needs investigation.
        when (val content = layoutState.screen.content) {
            is PokedexUiState.Loading -> LoadingIndicator()
            is PokedexUiState.Ready ->
                PokemonList(
                    listState = layoutState.listState,
                    state =
                        PokemonListState(
                            listLoadedState = content.listLoadedState,
                            pokemon = content.pokemon,
                            favoriteIds = content.favoriteIds,
                            showFavorites = layoutState.screen.showFavorites,
                            typeFilter = layoutState.screen.typeFilter,
                            generationFilter = layoutState.screen.generationFilter,
                        ),
                    onPokemonSelected = onPokemonSelected,
                )
        }
    }
}

private data class PokemonListState(
    val listLoadedState: MutableTransitionState<Boolean>,
    val pokemon: List<Pokemon>,
    val favoriteIds: Set<Int>,
    val showFavorites: Boolean,
    val typeFilter: Type?,
    val generationFilter: Generation?,
)

@Composable
private fun PokemonList(
    modifier: Modifier = Modifier,
    listState: LazyGridState,
    state: PokemonListState,
    onPokemonSelected: (Pokemon) -> Unit = {},
) {
    val bottomContentPadding =
        96.dp + WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    LazyVerticalGrid(
        modifier = modifier.testTag("PokedexLazyGrid"),
        columns = GridCells.Fixed(2),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding =
            PaddingValues(top = 12.dp, start = 16.dp, end = 16.dp, bottom = bottomContentPadding),
        content = {
            if (state.pokemon.isEmpty()) {
                item(span = { GridItemSpan(2) }) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier.fillMaxWidth().height(200.dp),
                    ) {
                        Text(
                            text = "No Pokemon match the following:",
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        Spacer(Modifier.height(16.dp))
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            if (state.showFavorites) {
                                Text(
                                    text = "Favorites",
                                    style = MaterialTheme.typography.titleMedium,
                                )
                            }
                            if (state.generationFilter != null) {
                                Text(
                                    text = "Gen ${state.generationFilter.romanNumeral}",
                                    style = MaterialTheme.typography.titleMedium,
                                )
                            }
                            if (state.typeFilter != null) {
                                Text(
                                    text = "Type: ${state.typeFilter}",
                                    style = MaterialTheme.typography.titleMedium,
                                )
                            }
                        }
                    }
                }
            } else {
                itemsIndexed(items = state.pokemon, key = { _, p -> p.id }) { idx, p ->
                    AnimatedVisibility(
                        visibleState = state.listLoadedState,
                        enter =
                            slideInVertically(
                                animationSpec =
                                    tween(durationMillis = 500, delayMillis = idx / 2 * 120),
                                initialOffsetY = { it / 2 },
                            ) +
                                fadeIn(
                                    animationSpec =
                                        tween(durationMillis = 400, delayMillis = idx / 2 * 150)
                                ),
                        exit = ExitTransition.None,
                        label = "pokemonCardTransition",
                    ) {
                        PokedexCard(
                            pokemon = p,
                            isFavorite = state.favoriteIds.contains(p.id),
                            onPokemonSelected = onPokemonSelected,
                        )
                    }
                }
            }
        },
    )
}

@PreviewLightDark
@Composable
private fun PokedexScreenPreview() {
    var showFavorites by remember { mutableStateOf(false) }
    var typeFilter by remember { mutableStateOf<Type?>(null) }
    var generationFilter by remember { mutableStateOf<Generation?>(null) }

    var state by remember {
        mutableStateOf<PokedexUiState>(
            PokedexUiState.Ready(
                listLoadedState = MutableTransitionState(true),
                pokemon = SamplePokemonData.toList(),
                favoriteIds = setOf(1, 4, 7),
            )
        )
    }

    AppTheme {
        PokedexScreen(
            screenState =
                PokedexScreenState(
                    content = state,
                    showFavorites = showFavorites,
                    typeFilter = typeFilter,
                    generationFilter = generationFilter,
                ),
            callbacks =
                PokedexScreenCallbacks(
                    onMenuItemClick = { result ->
                        when (result) {
                            is FilterMenuEvent.ToggleFavorites -> {
                                showFavorites = !showFavorites
                                val readyState = state as PokedexUiState.Ready
                                state =
                                    readyState.copy(
                                        pokemon =
                                            if (showFavorites) {
                                                SamplePokemonData.take(5)
                                            } else {
                                                SamplePokemonData.toList()
                                            }
                                    )
                            }
                            is FilterMenuEvent.FilterTypes -> {
                                typeFilter =
                                    if (typeFilter != result.typeToFilter) result.typeToFilter
                                    else null
                            }
                            is FilterMenuEvent.FilterGeneration -> {
                                generationFilter =
                                    if (generationFilter != result.generationToFilter)
                                        result.generationToFilter
                                    else null
                            }
                            is FilterMenuEvent.ShowTypes -> Unit
                            is FilterMenuEvent.ShowGenerations -> Unit
                        }
                    }
                ),
        )
    }
}
