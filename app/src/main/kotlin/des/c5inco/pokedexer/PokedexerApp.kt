package des.c5inco.pokedexer

import android.app.Application
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.di.ApplicationGraph
import des.c5inco.pokedexer.di.metroViewModel
import des.c5inco.pokedexer.ui.common.Material3Transitions
import des.c5inco.pokedexer.ui.home.HomeScreenRoute
import des.c5inco.pokedexer.ui.home.appbar.SearchResult
import des.c5inco.pokedexer.ui.home.appbar.elements.MenuItem
import des.c5inco.pokedexer.ui.items.ItemsScreenRoute
import des.c5inco.pokedexer.ui.moves.MovesListScreenRoute
import des.c5inco.pokedexer.ui.pokedex.PokedexScreenRoute
import des.c5inco.pokedexer.ui.pokedex.PokemonDetailsScreenRoute
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PokedexerApp(
    viewModel: RootViewModel = metroViewModel()
) {
    val navController = rememberNavController()
    val density = LocalDensity.current
    val context = LocalContext.current
    var pokemon by remember { mutableStateOf(SamplePokemonData.first()) }

    CompositionLocalProvider(
        LocalGifImageLoader provides (context.applicationContext as Application).appGraph.gifImageLoader
    ) {
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.semantics {
                testTagsAsResourceId = true
            },
            enterTransition = { Material3Transitions.SharedXAxisEnterTransition(density) },
            popEnterTransition = { Material3Transitions.SharedXAxisPopEnterTransition(density) },
            exitTransition = { Material3Transitions.SharedXAxisExitTransition(density) },
            popExitTransition = { Material3Transitions.SharedXAxisPopExitTransition(density) }
        ) {
            composable(route = "home") {
                HomeScreenRoute(
                    viewModel = metroViewModel(),
                    onMenuItemSelected = {
                        if (it == MenuItem.Pokedex) {
                            navController.navigate("pokedex")
                        }
                        if (it == MenuItem.Moves) {
                            navController.navigate("moves")
                        }
                        if (it == MenuItem.Items) {
                            navController.navigate("items")
                        }
                    },
                    onSearchResultSelected = {
                        when (it) {
                            is SearchResult.PokemonEvent -> {
                                pokemon = it.pokemon
                                navController.navigate("details")
                            }

                            is SearchResult.ItemEvent -> TODO()
                            is SearchResult.MoveEvent -> TODO()
                        }
                    }
                )
            }
            navigation(
                startDestination = "list",
                route = "pokedex",
            ) {
                composable(
                    route = "list",
                    popEnterTransition = { fadeIn() },
                    exitTransition = { fadeOut() }
                ) {
                    val pastPokemonId = it.savedStateHandle.get<Int>("pokemonId")

                    PokedexScreenRoute(
                        viewModel = metroViewModel(),
                        onPokemonSelected = {
                            pokemon = it
                            navController.navigate("details")
                        },
                        pastPokemonSelected = pastPokemonId,
                        onBackClick = { navController.popBackStack() }
                    )
                }
                composable(
                    route = "details",
                    enterTransition = { Material3Transitions.SharedZAxisEnterTransition },
                    exitTransition = { Material3Transitions.SharedZAxisExitTransition },
                ) {
                    PokemonDetailsScreenRoute(
                        detailsViewModel = metroViewModel { pokemonDetailsViewModelFactory.create(pokemon) },
                        onBackClick = {
                            navController.previousBackStackEntry
                                ?.savedStateHandle
                                ?.set("pokemonId", it)
                            navController.popBackStack()
                        }
                    )
                }
            }
            composable(route = "moves") {
                MovesListScreenRoute(
                    viewModel = metroViewModel(),
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable(route = "items") {
                ItemsScreenRoute(
                    viewModel = metroViewModel(),
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}