package des.c5inco.pokedexer

import android.app.Application
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import dagger.hilt.android.HiltAndroidApp
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.ui.common.Material3Transitions
import des.c5inco.pokedexer.ui.home.HomeScreenRoute
import des.c5inco.pokedexer.ui.home.appbar.SearchResult
import des.c5inco.pokedexer.ui.home.appbar.elements.MenuItem
import des.c5inco.pokedexer.ui.items.ItemsScreenRoute
import des.c5inco.pokedexer.ui.moves.MovesListScreenRoute
import des.c5inco.pokedexer.ui.pokedex.PokedexScreenRoute
import des.c5inco.pokedexer.ui.pokedex.PokemonDetailsScreenRoute
import des.c5inco.pokedexer.ui.pokedex.pokemonDetailsViewModel

@HiltAndroidApp
class PokedexerApplication : Application()

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PokedexerApp(
    viewModel: RootViewModel = viewModel()
) {
    // Create the back stack with Home as the starting destination
    val backStack = remember { mutableStateListOf<AppDestination>(AppDestination.Home) }

    // Temporary state to hold the selected Pokemon
    // TODO: In the future, consider fetching Pokemon by ID in the details screen
    var selectedPokemon by remember { mutableStateOf(SamplePokemonData.first()) }

    val density = LocalDensity.current

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        modifier = Modifier.semantics {
            testTagsAsResourceId = true
        },
        // Default SharedXAxis transitions for forward navigation
        transitionSpec = {
            Material3Transitions.SharedXAxisEnterTransition(density) togetherWith
                Material3Transitions.SharedXAxisExitTransition(density)
        },
        // Default SharedXAxis transitions for back navigation
        popTransitionSpec = {
            Material3Transitions.SharedXAxisPopEnterTransition(density) togetherWith
                Material3Transitions.SharedXAxisPopExitTransition(density)
        },
        entryProvider = entryProvider {
            // Home screen
            entry<AppDestination.Home> {
                HomeScreenRoute(
                    viewModel = viewModel(),
                    onMenuItemSelected = { menuItem ->
                        when (menuItem) {
                            MenuItem.Pokedex -> backStack.add(AppDestination.PokedexList)
                            MenuItem.Moves -> backStack.add(AppDestination.Moves)
                            MenuItem.Items -> backStack.add(AppDestination.Items)
                            MenuItem.Abilities -> TODO("Abilities not yet implemented")
                            MenuItem.Locations -> TODO("Locations not yet implemented")
                            MenuItem.TypeCharts -> TODO("TypeCharts not yet implemented")
                        }
                    },
                    onSearchResultSelected = { searchResult ->
                        when (searchResult) {
                            is SearchResult.PokemonEvent -> {
                                selectedPokemon = searchResult.pokemon
                                backStack.add(AppDestination.PokemonDetails(searchResult.pokemon.id))
                            }
                            is SearchResult.ItemEvent -> TODO()
                            is SearchResult.MoveEvent -> TODO()
                        }
                    }
                )
            }

            // Pokedex list screen - with fade transitions when returning from details
            entry<AppDestination.PokedexList>(
                metadata = NavDisplay.popTransitionSpec {
                    fadeIn() togetherWith fadeOut()
                }
            ) {
                // Find if we're returning from a Pokemon details page
                val previousPokemonId = backStack
                    .filterIsInstance<AppDestination.PokemonDetails>()
                    .lastOrNull()?.pokemonId

                PokedexScreenRoute(
                    viewModel = viewModel(),
                    onPokemonSelected = { pokemon ->
                        selectedPokemon = pokemon
                        backStack.add(AppDestination.PokemonDetails(pokemon.id))
                    },
                    pastPokemonSelected = previousPokemonId,
                    onBackClick = { backStack.removeLastOrNull() }
                )
            }

            // Pokemon details screen - with SharedZAxis transitions
            entry<AppDestination.PokemonDetails>(
                metadata = NavDisplay.transitionSpec {
                    Material3Transitions.SharedZAxisEnterTransition togetherWith
                        Material3Transitions.SharedZAxisExitTransition
                }
            ) { destination ->
                PokemonDetailsScreenRoute(
                    detailsViewModel = pokemonDetailsViewModel(selectedPokemon),
                    onBackClick = { pokemonId ->
                        backStack.removeLastOrNull()
                    }
                )
            }

            // Moves list screen
            entry<AppDestination.Moves> {
                MovesListScreenRoute(
                    viewModel = viewModel(),
                    onBackClick = { backStack.removeLastOrNull() }
                )
            }

            // Items screen
            entry<AppDestination.Items> {
                ItemsScreenRoute(
                    viewModel = viewModel(),
                    onBackClick = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}