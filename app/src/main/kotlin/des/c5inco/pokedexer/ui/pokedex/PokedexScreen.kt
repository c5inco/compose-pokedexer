package des.c5inco.pokedexer.ui.pokedex

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.materialkolor.PaletteStyle
import des.c5inco.pokedexer.R
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.model.Type
import des.c5inco.pokedexer.ui.common.Pokeball
import des.c5inco.pokedexer.ui.common.mapTypeToIcon
import des.c5inco.pokedexer.ui.theme.AppTheme
import des.c5inco.pokedexer.ui.theme.getDynamicColorScheme
import des.c5inco.pokedexer.ui.theme.mapDynamicPokemonColorScheme
import des.c5inco.pokedexer.ui.theme.mapTypeToSeedColor

@Composable
fun PokedexScreenRoute(
    viewModel: PokedexViewModel,
    onPokemonSelected: (Pokemon) -> Unit,
    pastPokemonSelected: Int?,
    onBackClick: () -> Unit
) {
    PokedexScreen(
        loading = viewModel.uiState.loading,
        pokemon = viewModel.uiState.pokemon,
        favorites = viewModel.favorites,
        showFavorites = viewModel.showFavorites,
        typeFilter = viewModel.typeFilter,
        pastPokemonSelected = pastPokemonSelected,
        onPokemonSelected = onPokemonSelected,
        onMenuItemClick = {
            when (it) {
                is FilterMenuEvent.ToggleFavorites -> {
                    viewModel.toggleFavorites()
                }
                is FilterMenuEvent.FilterTypes -> {
                    viewModel.filterByType(it.typeToFilter)
                }
                is FilterMenuEvent.ShowTypes -> {}
            }
        },
        onBackClick = onBackClick
    )
}

enum class FilterMenuState {
    Hidden,
    Visible,
    Types
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokedexScreen(
    loading: Boolean,
    pokemon: List<Pokemon>,
    favorites: List<Pokemon>,
    showFavorites: Boolean = false,
    typeFilter: Type? = null,
    pastPokemonSelected: Int? = null,
    onPokemonSelected: (Pokemon) -> Unit = {},
    onMenuItemClick: (FilterMenuEvent) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val listState = rememberLazyGridState()
    var filterMenuState by remember { mutableStateOf(FilterMenuState.Hidden) }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    LaunchedEffect(pastPokemonSelected) {
        pastPokemonSelected?.let {pastId ->
            val visibleItems = listState.layoutInfo.visibleItemsInfo
            val visible = visibleItems.filter { it.key == pastId }

            if (visible.isEmpty()) {
                listState.scrollToItem(pastId, -100)
            }
        }
    }

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text("Pokemon",) },
                navigationIcon =  {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.Transparent),
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        Box(
            Modifier.fillMaxSize()
        ) {
            Pokeball(
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
                modifier = Modifier
                    .size(256.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = 90.dp, y = (-72).dp)
            )
            PokemonList(
                modifier = Modifier
                    .padding(top = innerPadding.calculateTopPadding())
                    .fillMaxWidth(),
                listState = listState,
                loading = loading,
                pokemon = pokemon,
                favorites = favorites,
                showFavorites = showFavorites,
                typeFilter = typeFilter,
                onPokemonSelected = onPokemonSelected
            )
            AnimatedVisibility(
                visible = filterMenuState != FilterMenuState.Hidden,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.matchParentSize()
            ) {
                Box(
                    Modifier
                        .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.5f))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { filterMenuState = FilterMenuState.Hidden }
                        ),
                )
            }

            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(WindowInsets.navigationBars.asPaddingValues())
                    .padding(bottom = 24.dp)
            ) {
                if (filterMenuState != FilterMenuState.Hidden) {
                    FilterMenu(
                        showFavorites = showFavorites,
                        typeFilter = typeFilter,
                        menuState = filterMenuState,
                        onMenuItemClick = {
                            if (it is FilterMenuEvent.ShowTypes) {
                                filterMenuState = FilterMenuState.Types
                            } else {
                                filterMenuState = FilterMenuState.Hidden
                                onMenuItemClick(it)
                            }
                        },
                    )
                }
                Spacer(Modifier.height(16.dp))
                FloatingActionButton(
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.primary,
                    onClick = {
                        filterMenuState = when (filterMenuState) {
                            FilterMenuState.Hidden -> FilterMenuState.Visible
                            FilterMenuState.Visible -> FilterMenuState.Hidden
                            FilterMenuState.Types -> FilterMenuState.Visible
                        }
                    },
                ) {
                    AnimatedContent(
                        targetState = filterMenuState,
                        label = "filterMenuButtonTransition"
                    ) { targetState ->
                        when (targetState) {
                            FilterMenuState.Hidden -> {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_filter),
                                    contentDescription = "Show filters",
                                )
                            }
                            FilterMenuState.Visible -> {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_close),
                                    contentDescription = "Hide filters",
                                )
                            }
                            FilterMenuState.Types -> {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back to filter menu",
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PokemonList(
    modifier: Modifier = Modifier,
    listState: LazyGridState,
    loading: Boolean = false,
    pokemon: List<Pokemon>,
    favorites: List<Pokemon>,
    showFavorites: Boolean = false,
    typeFilter: Type? = null,
    onPokemonSelected: (Pokemon) -> Unit = {},
) {
    val loaded = remember { MutableTransitionState(!loading) }
    val pokemonToShow = if (showFavorites) favorites else pokemon
    val bottomContentPadding = 96.dp + WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    LazyVerticalGrid(
        modifier = modifier.testTag("PokedexLazyGrid"),
        columns = GridCells.Fixed(2),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(top = 12.dp, start = 16.dp, end = 16.dp, bottom = bottomContentPadding),
        content = {
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

                if (pokemonToShow.isEmpty()) {
                    item(span = { GridItemSpan(2) }) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Bottom,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        ) {
                            Text(
                                text = "No Pokemon match the following:",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(Modifier.height(16.dp))
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                if (showFavorites) {
                                    Text(
                                        text = "Favorites",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                                if (typeFilter != null) {
                                    Text(
                                        text = "Type: $typeFilter",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            }
                        }
                    }
                } else {
                    itemsIndexed(items = pokemonToShow, key = { _, p -> p.id }) { idx, p ->
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
                            exit = ExitTransition.None,
                            label = "pokemonCardTransition"
                        ) {
                            PokedexCard(
                                pokemon = p,
                                isFavorite = favorites.contains(p),
                                onPokemonSelected = onPokemonSelected
                            )
                        }
                    }
                }
            }
        }
    )
}

sealed class FilterMenuEvent {
    data class ToggleFavorites(val filterFavorites: Boolean) : FilterMenuEvent()
    data class ShowTypes(val showTypes: Boolean) : FilterMenuEvent()
    data class FilterTypes(val typeToFilter: Type) : FilterMenuEvent()
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FilterMenu(
    modifier: Modifier = Modifier,
    showFavorites: Boolean,
    typeFilter: Type? = null,
    menuState: FilterMenuState,
    onMenuItemClick: (FilterMenuEvent) -> Unit = {},
) {
    AnimatedContent(
        targetState = menuState,
        transitionSpec = {
            EnterTransition.None togetherWith ExitTransition.None using(SizeTransform(false))
        },
        label = "filterMenuTransition",
        modifier = modifier.fillMaxWidth()
    ) { targetState ->
        if (targetState == FilterMenuState.Types) {
            FlowRow(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                Type.entries.forEachIndexed { idx, type ->
                    val selected = type == typeFilter

                    val seedColor = mapTypeToSeedColor(types = listOf(type.toString()))
                    val kolorScheme = getDynamicColorScheme(seedColor, PaletteStyle.Rainbow)
                    val pokemonColorScheme = mapDynamicPokemonColorScheme(
                        seedColor = seedColor,
                        colorScheme = kolorScheme
                    )

                    FilterTypeItem(
                        type = type,
                        colors = if (selected) {
                            ButtonDefaults.filledTonalButtonColors(
                                containerColor = pokemonColorScheme.surface,
                                contentColor = pokemonColorScheme.onSurface
                            )
                        } else {
                            ButtonDefaults.filledTonalButtonColors()
                        },
                        selected = selected,
                        index = idx,
                        onClick = { onMenuItemClick(FilterMenuEvent.FilterTypes(type)) },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                FilterMenuItem(
                    index = 0,
                    onClick = { onMenuItemClick(FilterMenuEvent.ToggleFavorites(!showFavorites)) }
                ) {
                    Icon(
                        imageVector = if (showFavorites) Icons.Default.FavoriteBorder else Icons.Default.Favorite,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(if (showFavorites) "Show all" else "Show favorites")
                }
                FilterMenuItem(
                    index = 1,
                    onClick = { onMenuItemClick(FilterMenuEvent.ShowTypes(true)) }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_genetics),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(if (typeFilter != null) "Filtered by $typeFilter" else "All types")
                }
            }
        }
    }
}

@Composable
private fun AnimatedVisibilityScope.FilterMenuItem(
    modifier: Modifier = Modifier,
    index: Int,
    onClick: () -> Unit = {},
    content: @Composable RowScope.() -> Unit = {}
) {
    FilledTonalButton(
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 8.dp),
        onClick = onClick,
        modifier = modifier
            .animateEnterExit(
                enter = fadeIn(animationSpec = tween(durationMillis = 200, delayMillis = index * 15 + 60)) +
                    slideInVertically(initialOffsetY = { it / 2 }, animationSpec = tween(durationMillis = 240, delayMillis = index * 50 + 60)),
                exit = fadeOut(animationSpec = spring(stiffness = Spring.StiffnessMedium)) +
                    slideOutVertically(targetOffsetY = { it / 2 }),
                label = "filterMenuItemTransition"
            )
    ) {
        content()
    }
}

@Composable
private fun AnimatedVisibilityScope.FilterTypeItem(
    modifier: Modifier = Modifier,
    type: Type,
    colors: ButtonColors = ButtonDefaults.filledTonalButtonColors(),
    selected: Boolean = false,
    index: Int,
    onClick: () -> Unit = {},
) {
    FilledTonalButton(
        contentPadding = PaddingValues(start = 12.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
        onClick = onClick,
        colors = colors,
        modifier = modifier
            .animateEnterExit(
                enter = fadeIn(animationSpec = tween(durationMillis = 240, delayMillis = index * 15 + 60)) +
                    slideInVertically(initialOffsetY = { it / 2 }, animationSpec = tween(durationMillis = 150, delayMillis = index * 15 + 60)),
                exit = fadeOut(animationSpec = spring(stiffness = Spring.StiffnessMedium)),
                label = "{$type}TypeTransition"
            )
    ) {
        Icon(
            painter = painterResource(id = mapTypeToIcon(type)),
            contentDescription = null,
            modifier = Modifier
                .size(18.dp)
                .graphicsLayer { alpha = if (selected) 1f else 0.4f }
        )
        Spacer(Modifier.width(4.dp))
        Text("$type")
    }
}

@PreviewLightDark
@Composable
private fun PokedexScreenPreview() {
    var pokemon by remember { mutableStateOf(SamplePokemonData) }
    val favorites by remember { mutableStateOf(SamplePokemonData.take(5)) }
    var showFavorites by remember { mutableStateOf(false) }
    var typeFilter by remember { mutableStateOf<Type?>(null) }

    AppTheme {
        PokedexScreen(
            loading = false,
            pokemon = pokemon,
            favorites = favorites,
            showFavorites = showFavorites,
            onMenuItemClick = { result ->
                when (result) {
                    is FilterMenuEvent.ToggleFavorites -> {
                        showFavorites = !showFavorites
                        pokemon = if (showFavorites) {
                            favorites.toList()
                        } else {
                            SamplePokemonData.toList()
                        }
                    }
                    is FilterMenuEvent.FilterTypes -> {
                        typeFilter = if (typeFilter != result.typeToFilter) result.typeToFilter else null

                        pokemon = if (typeFilter != null) {
                            SamplePokemonData.filter { it.typeOfPokemon.contains(typeFilter.toString()) }
                        } else {
                            SamplePokemonData.toList()
                        }
                    }
                    is FilterMenuEvent.ShowTypes -> {}
                }
            }
        )
    }
}