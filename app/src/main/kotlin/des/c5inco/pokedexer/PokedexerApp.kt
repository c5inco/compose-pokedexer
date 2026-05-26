package des.c5inco.pokedexer

import android.app.Application
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.defaultPopTransitionSpec
import androidx.navigation3.ui.defaultTransitionSpec
import coil.ImageLoader
import des.c5inco.pokedexer.di.ApplicationGraph
import des.c5inco.pokedexer.di.metroViewModel
import des.c5inco.pokedexer.ui.common.Material3Transitions
import des.c5inco.pokedexer.ui.home.HomeScreenRoute
import des.c5inco.pokedexer.ui.home.appbar.SearchResult
import des.c5inco.pokedexer.ui.items.ItemsScreenRoute
import des.c5inco.pokedexer.ui.moves.MovesListScreenRoute
import des.c5inco.pokedexer.ui.navigation.Screen
import des.c5inco.pokedexer.ui.navigation.pokedexerBottomBar
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

@Composable
fun PokedexerApp(viewModel: RootViewModel = metroViewModel()) {
    val backStack = rememberNavBackStack(Screen.Pokedex)
    val density = LocalDensity.current
    val context = LocalContext.current

    CompositionLocalProvider(
        LocalGifImageLoader provides
            (context.applicationContext as Application).appGraph.gifImageLoader
    ) {
        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = {
                pokedexerBottomBar(
                    currentScreen = currentTopLevelDestination(backStack),
                    onDestinationSelected = { destination: Screen ->
                        navigateToTopLevelDestination(backStack, destination)
                    },
                )
            },
        ) { innerPadding ->
            PokedexerNavDisplay(
                backStack = backStack,
                density = density,
                innerPadding = innerPadding,
            )
        }
    }
}

private fun currentTopLevelDestination(backStack: NavBackStack<NavKey>): Screen {
    return when (backStack.lastOrNull()) {
        Screen.Moves -> Screen.Moves
        Screen.Items -> Screen.Items
        Screen.TypeCharts -> Screen.TypeCharts
        else -> Screen.Pokedex
    }
}

private fun navigateToTopLevelDestination(backStack: NavBackStack<NavKey>, destination: Screen) {
    val currentDestination = currentTopLevelDestination(backStack)
    if (destination == currentDestination) return

    while (backStack.size > 1) {
        backStack.removeAt(backStack.lastIndex)
    }

    backStack[0] = destination
}

@Composable
private fun PokedexerNavDisplay(
    backStack: NavBackStack<NavKey>,
    density: Density,
    innerPadding: PaddingValues,
) {
    val transitionMetadataForScreen =
        remember(density) { pokemonDetailsTransitionMetadataProvider(density) }

    NavDisplay(
        backStack = backStack,
        modifier = Modifier.padding(innerPadding),
        onBack = {
            if (backStack.size > 1) {
                backStack.removeAt(backStack.lastIndex)
            }
        },
        transitionSpec = defaultTransitionSpec(),
        popTransitionSpec = defaultPopTransitionSpec(),
    ) { screen ->
        NavEntry(key = screen, metadata = transitionMetadataForScreen(screen)) {
            PokedexerDestinationContent(screen = screen as Screen, backStack = backStack)
        }
    }
}

internal fun pokemonDetailsTransitionMetadataProvider(
    density: Density
): (NavKey) -> Map<String, Any> {
    val cache = mutableMapOf<NavKey, Map<String, Any>>()
    return { screen ->
        cache.getOrPut(screen) {
            pokemonDetailsTransitionMetadata(screen = screen, density = density)
        }
    }
}

private fun pokemonDetailsTransitionMetadata(screen: NavKey, density: Density): Map<String, Any> {
    return if (screen is Screen.PokemonDetails) {
        NavDisplay.transitionSpec {
            Material3Transitions.SharedXAxisEnterTransition(density) togetherWith
                Material3Transitions.SharedXAxisExitTransition(density)
        } +
            NavDisplay.popTransitionSpec {
                Material3Transitions.SharedXAxisPopEnterTransition(density) togetherWith
                    Material3Transitions.SharedXAxisPopExitTransition(density)
            }
    } else {
        emptyMap()
    }
}

@Composable
private fun PokedexerDestinationContent(screen: Screen, backStack: NavBackStack<NavKey>) {
    when (screen) {
        Screen.Home -> {
            HomeScreenRoute(
                viewModel = metroViewModel(),
                onSearchResultSelected = {
                    when (it) {
                        is SearchResult.PokemonEvent -> {
                            backStack.add(Screen.PokemonDetails(it.pokemon.id))
                        }

                        is SearchResult.ItemEvent -> TODO()
                        is SearchResult.MoveEvent -> TODO()
                    }
                },
            )
        }

        Screen.Pokedex -> {
            PokedexDestination(backStack = backStack)
        }

        is Screen.PokemonDetails -> {
            PokemonDetailsDestination(screen = screen, backStack = backStack)
        }

        Screen.Moves -> {
            MovesListScreenRoute(viewModel = metroViewModel())
        }

        Screen.Items -> {
            ItemsScreenRoute(viewModel = metroViewModel())
        }

        Screen.TypeCharts -> {
            TypeChartScreenRoute()
        }
    }
}

@Composable
private fun PokedexDestination(backStack: NavBackStack<NavKey>) {
    val pastPokemonId = (backStack.getOrNull(backStack.lastIndex - 1) as? Screen.PokemonDetails)?.id

    PokedexScreenRoute(
        viewModel = metroViewModel(),
        onPokemonSelected = { backStack.add(Screen.PokemonDetails(it.id)) },
        pastPokemonSelected = pastPokemonId,
    )
}

@Composable
private fun PokemonDetailsDestination(
    screen: Screen.PokemonDetails,
    backStack: NavBackStack<NavKey>,
) {
    PokemonDetailsScreenRoute(
        detailsViewModel =
            metroViewModel(key = "pokemon_${screen.id}") {
                pokemonDetailsViewModelFactory.create(screen.id)
            },
        onBackClick = { backStack.removeAt(backStack.lastIndex) },
    )
}
