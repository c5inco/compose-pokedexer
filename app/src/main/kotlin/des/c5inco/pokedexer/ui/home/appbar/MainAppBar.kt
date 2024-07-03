package des.c5inco.pokedexer.ui.home.appbar

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import des.c5inco.pokedexer.data.items.SampleItems
import des.c5inco.pokedexer.data.moves.SampleMoves
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.model.Item
import des.c5inco.pokedexer.model.Move
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.ui.common.PokeBallBackground
import des.c5inco.pokedexer.ui.home.HomeViewModel
import des.c5inco.pokedexer.ui.home.MenuItem
import des.c5inco.pokedexer.ui.home.appbar.elements.Menu
import des.c5inco.pokedexer.ui.home.appbar.elements.RoundedSearchBar
import des.c5inco.pokedexer.ui.home.appbar.search.ItemResultCard
import des.c5inco.pokedexer.ui.home.appbar.search.MoveResultCard
import des.c5inco.pokedexer.ui.home.appbar.search.PokemonResultCard
import des.c5inco.pokedexer.ui.theme.AppTheme

sealed class SearchResult {
    data class PokemonEvent(val pokemon: Pokemon) : SearchResult()
    data class MoveEvent(val move: Move) : SearchResult()
    data class ItemEvent(val item: Item) : SearchResult()
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainAppBar(
    viewModel: HomeViewModel = hiltViewModel(),
    selectedSearchResult: SearchResult? = null,
    sharedTransitionScope: SharedTransitionScope,
    onMenuItemSelected: (MenuItem) -> Unit = { _ -> },
    onSearchResultSelected: (SearchResult) -> Unit = { _ -> }
) {
    val searchResponse = viewModel.searchResponses.collectAsState()
    val density = LocalDensity.current

    Surface(
        shape = RoundedCornerShape(
            bottomStart = 32.dp,
            bottomEnd = 32.dp
        ),
        tonalElevation = if (isSystemInDarkTheme()) 2.dp else 0.dp,
    ) {
        Box {
            PokeBallBackground(
                Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 90.dp, y = (-70).dp),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
            )
            Column(
                modifier = Modifier.padding(top = 32.dp)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 32.dp)
                ) {
                    Text(
                        text = "What Pokémon\nare you looking for?",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(
                            top = 64.dp, bottom = 32.dp
                        )
                    )
                    RoundedSearchBar(
                        searchText = viewModel.searchText,
                        onTextClear = {
                            viewModel.searchText.clearText()
                        }
                    )
                }
                AnimatedContent(
                    targetState = searchResponse.value,
                    transitionSpec = { fadeIn().togetherWith(fadeOut()).using(SizeTransform(clip = false)) },
                ) { response  ->
                    if (response.foundPokemon.isNotEmpty() || response.foundMoves.isNotEmpty() || response.foundItems.isNotEmpty()) {
                        SearchResults(
                            pokemonResults = response.foundPokemon,
                            movesResults = response.foundMoves,
                            itemsResults = response.foundItems,
                            selectedSearchResult = selectedSearchResult,
                            sharedTransitionScope = sharedTransitionScope,
                            onSelected = onSearchResultSelected,
                            modifier = Modifier.padding(top = 32.dp)
                        )
                    } else if (response.currentText.isNotEmpty()) {
                        val annotatedString = buildAnnotatedString {
                            append("No results found for ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(response.currentText)
                            }
                        }

                        Text(
                            text = annotatedString,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp)
                        )
                    } else {
                        Menu(
                            modifier = Modifier
                                .padding(32.dp),
                            onMenuItemSelected = onMenuItemSelected
                        )
                    }
                }
            }
        }
    }
}

private fun slideAndFadeEnterTransition(index: Int): EnterTransition {
    return fadeIn(
            tween(durationMillis = 300, delayMillis = index / 2 * 100)
        ) +
        slideInHorizontally(
            tween(durationMillis = 300, delayMillis = index / 2 * 100)
        ) { it / 2 }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun AnimatedContentScope.SearchResults(
    modifier: Modifier = Modifier,
    pokemonResults: List<Pokemon> = SamplePokemonData.take(10),
    movesResults: List<Move> = SampleMoves.take(10),
    itemsResults: List<Item> = SampleItems.take(10),
    selectedSearchResult: SearchResult? = null,
    sharedTransitionScope: SharedTransitionScope,
    onSelected: (SearchResult) -> Unit = { _ ->}
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        if (pokemonResults.isNotEmpty()) {
            Column {
                Text(
                    text = "Pokemon (${pokemonResults.size})",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
                Spacer(Modifier.height(16.dp))
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(2),
                    contentPadding = PaddingValues(horizontal = 32.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.height(200.dp)
                ) {
                    itemsIndexed(items = pokemonResults, key = { _, it -> it.id }) { idx, it ->
                        PokemonResultCard(
                            pokemon = it,
                            onPokemonSelected = { onSelected(SearchResult.PokemonEvent(it)) },
                            modifier = Modifier
                                .animateEnterExit(
                                    enter = slideAndFadeEnterTransition(idx),
                                    exit = fadeOut()
                                )
                        )
                    }
                }
            }
        }

        if (movesResults.isNotEmpty()) {
            Column {
                Text(
                    text = "Moves (${movesResults.size})",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
                Spacer(Modifier.height(16.dp))
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(4),
                    contentPadding = PaddingValues(horizontal = 32.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.height(240.dp)
                ) {
                    itemsIndexed(items = movesResults, key = { _, it -> it.id }) { idx, it ->
                        with (sharedTransitionScope) {
                            AnimatedVisibility(
                                visible = it != (selectedSearchResult as? SearchResult.MoveEvent)?.move,
                                modifier = Modifier.animateEnterExit(
                                    enter = slideAndFadeEnterTransition(idx),
                                    exit = fadeOut()
                                )
                            ) {
                                MoveResultCard(
                                    move = it,
                                    animatedVisibilityScope = this,
                                    modifier = Modifier.width(200.dp),
                                    onSelected = { onSelected(SearchResult.MoveEvent(it)) }
                                )
                            }
                        }
                    }
                }
            }
        }

        if (itemsResults.isNotEmpty()) {
            Column {
                Text(
                    text = "Items (${itemsResults.size})",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
                Spacer(Modifier.height(16.dp))
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(3),
                    contentPadding = PaddingValues(horizontal = 32.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.height(240.dp)
                ) {
                    itemsIndexed(items = itemsResults, key = { _, it -> it.id }) { idx, it ->
                        with (sharedTransitionScope) {
                            AnimatedVisibility(
                                visible = it != (selectedSearchResult as? SearchResult.ItemEvent)?.item,
                                modifier = Modifier.animateEnterExit(
                                    enter = slideAndFadeEnterTransition(idx),
                                    exit = fadeOut()
                                )
                            ) {
                                ItemResultCard(
                                    item = it,
                                    modifier = Modifier.width(200.dp),
                                    animatedVisibilityScope = this,
                                    onSelected = { onSelected(SearchResult.ItemEvent(it)) }
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.systemBars))
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
private fun SearchResultsPreview() {
    AppTheme {
        Surface {
            SharedTransitionLayout {
                AnimatedContent(
                    targetState = true,
                ) { _ ->
                    SearchResults(
                        sharedTransitionScope = this@SharedTransitionLayout,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
@Composable
fun PreviewMainAppBar() {
    AppTheme {
        Surface(
            Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SharedTransitionLayout {
                AnimatedContent(
                    targetState = true,
                ) { _ ->
                    Column {
                        MainAppBar(
                            sharedTransitionScope = this@SharedTransitionLayout,
                        )
                    }
                }
            }
        }
    }
}