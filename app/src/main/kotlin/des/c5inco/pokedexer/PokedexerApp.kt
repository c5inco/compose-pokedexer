package des.c5inco.pokedexer

import android.app.Application
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import coil.ImageLoader
import des.c5inco.pokedexer.di.ApplicationGraph
import des.c5inco.pokedexer.di.metroViewModel
import des.c5inco.pokedexer.ui.common.Material3Transitions
import des.c5inco.pokedexer.ui.home.HomeScreenRoute
import des.c5inco.pokedexer.ui.home.appbar.SearchResult
import des.c5inco.pokedexer.ui.home.appbar.elements.MenuItem
import des.c5inco.pokedexer.ui.items.ItemsScreenRoute
import des.c5inco.pokedexer.ui.moves.MovesListScreenRoute
import des.c5inco.pokedexer.ui.navigation.Screen
import des.c5inco.pokedexer.ui.pokedex.PokedexScreenRoute
import des.c5inco.pokedexer.ui.pokedex.PokemonDetailsScreenRoute
import des.c5inco.pokedexer.ui.typechart.TypeChartScreenRoute
import dev.zacsweers.metro.createGraphFactory

class PokedexerApplication : Application() {
    lateinit var appGraph: ApplicationGraph
        private set

    override fun onCreate() {
        super.onCreate()
        appGraph = createGraphFactory<ApplicationGraph.Factory>().create(this)
    }
}

/**
 * Extension property to access the app graph from any context.
 */
val Application.appGraph: ApplicationGraph
    get() = (this as PokedexerApplication).appGraph

val LocalGifImageLoader = compositionLocalOf<ImageLoader> {
    error("No GIF ImageLoader provided")
}

@Composable
fun PokedexerApp(
    viewModel: RootViewModel = metroViewModel()
) {
    val backStack = rememberNavBackStack(Screen.Home)
    val density = LocalDensity.current
    val context = LocalContext.current

    CompositionLocalProvider(
        LocalGifImageLoader provides (context.applicationContext as Application).appGraph.gifImageLoader
    ) {
        NavDisplay(
            backStack = backStack,
            onBack = {
                if (backStack.size > 1) {
                    backStack.removeAt(backStack.lastIndex)
                } else {
                    // TODO: Resolve what this does
                    // Quit app
                    // (context as? Activity)?.finish()
                    // or just let system handle back?
                    // For now, if size is 1, onBack typically doesn't trigger if handled by system back handler?
                    // Actually NavDisplay might request back handling.
                    // If we want to exit, we usually don't verify strict size > 1 here if back handler is intercepted.
                    // But simplest is removeLast.
                }
            },
            transitionSpec = {
                if (targetState.key is Screen.PokemonDetails) {
                    Material3Transitions.SharedZAxisEnterTransition togetherWith fadeOut()
                } else {
                    Material3Transitions.SharedXAxisEnterTransition(density) togetherWith Material3Transitions.SharedXAxisExitTransition(density)
                }
            },
            popTransitionSpec = {
                if (initialState.key is Screen.PokemonDetails) {
                    fadeIn() togetherWith Material3Transitions.SharedZAxisExitTransition
                } else {
                    Material3Transitions.SharedXAxisPopEnterTransition(density) togetherWith Material3Transitions.SharedXAxisPopExitTransition(density)
                }
            }
        ) { screen ->
            NavEntry(screen) {
                when (screen) {
                    Screen.Home -> {
                        HomeScreenRoute(
                            viewModel = metroViewModel(),
                            onMenuItemSelected = {
                                if (it == MenuItem.Pokedex) {
                                    backStack.add(Screen.Pokedex)
                                }
                                if (it == MenuItem.Moves) {
                                    backStack.add(Screen.Moves)
                                }
                                if (it == MenuItem.Items) {
                                    backStack.add(Screen.Items)
                                }
                                if (it == MenuItem.TypeCharts) {
                                    backStack.add(Screen.TypeCharts)
                                }
                            },
                            onSearchResultSelected = {
                                when (it) {
                                    is SearchResult.PokemonEvent -> {
                                        backStack.add(Screen.PokemonDetails(it.pokemon.id))
                                    }

                                    is SearchResult.ItemEvent -> TODO()
                                    is SearchResult.MoveEvent -> TODO()
                                }
                            }
                        )
                    }
                    Screen.Pokedex -> {
                        // Extract the pokemon ID from the previous screen (if it was PokemonDetails)
                        // so we can scroll to it when returning from the details screen
                        val pastPokemonId = (backStack.getOrNull(backStack.lastIndex - 1) as? Screen.PokemonDetails)?.id
                        
                        PokedexScreenRoute(
                            viewModel = metroViewModel(),
                            onPokemonSelected = {
                                backStack.add(Screen.PokemonDetails(it.id))
                            },
                            pastPokemonSelected = pastPokemonId,
                            onBackClick = { backStack.removeAt(backStack.lastIndex) }
                        )
                    }
                    is Screen.PokemonDetails -> {
                        PokemonDetailsScreenRoute(
                            detailsViewModel = metroViewModel { pokemonDetailsViewModelFactory.create(screen.id) },
                            onBackClick = {
                                backStack.removeAt(backStack.lastIndex)
                            }
                        )
                    }
                    Screen.Moves -> {
                        MovesListScreenRoute(
                            viewModel = metroViewModel(),
                            onBackClick = { backStack.removeAt(backStack.lastIndex) }
                        )
                    }
                    Screen.Items -> {
                        ItemsScreenRoute(
                            viewModel = metroViewModel(),
                            onBackClick = { backStack.removeAt(backStack.lastIndex) }
                        )
                    }
                    Screen.TypeCharts -> {
                        TypeChartScreenRoute(
                            onBackClick = { backStack.removeAt(backStack.lastIndex) }
                        )
                    }
                }
            }
        }
    }
}
