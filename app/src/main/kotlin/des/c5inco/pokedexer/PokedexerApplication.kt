@file:JvmName("PokedexerAppKt")

package des.c5inco.pokedexer

import android.app.Application
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
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

/** Extension property to access the app graph from any context. */
val Application.appGraph: ApplicationGraph
    get() = (this as PokedexerApplication).appGraph

val LocalGifImageLoader = compositionLocalOf<ImageLoader> { error("No GIF ImageLoader provided") }
private val LocalRootViewModel =
    compositionLocalOf<RootViewModel> { error("No RootViewModel provided") }

@Composable
fun PokedexerApp(viewModel: RootViewModel = metroViewModel()) {
    CompositionLocalProvider(LocalRootViewModel provides viewModel) {
        val backStack = rememberNavBackStack(Screen.Home)
        val density = LocalDensity.current
        val context = LocalContext.current

        CompositionLocalProvider(
            LocalGifImageLoader provides
                (context.applicationContext as Application).appGraph.gifImageLoader
        ) {
            NavDisplay(
                backStack = backStack,
                onBack = { backStack.popNestedDestination() },
                transitionSpec = {
                    if (targetState.key is Screen.PokemonDetails) {
                        Material3Transitions.SharedZAxisEnterTransition togetherWith fadeOut()
                    } else {
                        Material3Transitions.SharedXAxisEnterTransition(density) togetherWith
                            Material3Transitions.SharedXAxisExitTransition(density)
                    }
                },
                popTransitionSpec = {
                    if (initialState.key is Screen.PokemonDetails) {
                        fadeIn() togetherWith Material3Transitions.SharedZAxisExitTransition
                    } else {
                        Material3Transitions.SharedXAxisPopEnterTransition(density) togetherWith
                            Material3Transitions.SharedXAxisPopExitTransition(density)
                    }
                },
            ) { screen ->
                pokedexerDestination(screen, backStack)
            }
        }
    }
}

private fun pokedexerDestination(screen: NavKey, backStack: MutableList<NavKey>): NavEntry<NavKey> =
    NavEntry(screen) {
        when (screen) {
            Screen.Home -> HomeDestination(backStack)
            Screen.Pokedex -> PokedexDestination(backStack)
            is Screen.PokemonDetails -> PokemonDetailsDestination(screen, backStack)
            Screen.Moves ->
                MovesListScreenRoute(
                    viewModel = metroViewModel(),
                    onBackClick = { backStack.popNestedDestination() },
                )
            Screen.Items ->
                ItemsScreenRoute(
                    viewModel = metroViewModel(),
                    onBackClick = { backStack.popNestedDestination() },
                )
            Screen.TypeCharts ->
                TypeChartScreenRoute(onBackClick = { backStack.popNestedDestination() })
        }
    }

@Composable
private fun HomeDestination(backStack: MutableList<NavKey>) {
    HomeScreenRoute(
        viewModel = metroViewModel(),
        onMenuItemSelected = { item -> destinationFor(item)?.let(backStack::add) },
        onSearchResultSelected = { result ->
            when (result) {
                is SearchResult.PokemonEvent ->
                    backStack.add(Screen.PokemonDetails(result.pokemon.id))
                is SearchResult.ItemEvent -> TODO()
                is SearchResult.MoveEvent -> TODO()
            }
        },
    )
}

@Composable
private fun PokedexDestination(backStack: MutableList<NavKey>) {
    PokedexScreenRoute(
        viewModel = metroViewModel(),
        onPokemonSelected = { backStack.add(Screen.PokemonDetails(it.id)) },
        pastPokemonSelected = previousPokemonId(backStack),
        onBackClick = { backStack.popNestedDestination() },
    )
}

@Composable
private fun PokemonDetailsDestination(
    screen: Screen.PokemonDetails,
    backStack: MutableList<NavKey>,
) {
    PokemonDetailsScreenRoute(
        detailsViewModel =
            metroViewModel(key = "pokemon_${screen.id}") {
                pokemonDetailsViewModelFactory.create(screen.id)
            },
        onBackClick = { backStack.popNestedDestination() },
    )
}

internal fun destinationFor(item: MenuItem): Screen? =
    when (item) {
        MenuItem.Pokedex -> Screen.Pokedex
        MenuItem.Moves -> Screen.Moves
        MenuItem.Items -> Screen.Items
        MenuItem.TypeCharts -> Screen.TypeCharts
        MenuItem.Abilities,
        MenuItem.Locations -> null
    }

internal fun MutableList<NavKey>.popNestedDestination(): Boolean {
    if (size <= 1) return false

    removeAt(lastIndex)
    return true
}

internal fun previousPokemonId(backStack: List<NavKey>): Int? =
    (backStack.getOrNull(backStack.lastIndex - 1) as? Screen.PokemonDetails)?.id
