package des.c5inco.pokedexer

import android.app.Application
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.HiltAndroidApp
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.ui.common.Material3Transitions
import des.c5inco.pokedexer.ui.items.ItemsScreenRoute
import des.c5inco.pokedexer.ui.moves.MovesListScreenRoute
import des.c5inco.pokedexer.ui.pokedex.PokedexScreenRoute
import des.c5inco.pokedexer.ui.pokedex.PokemonDetailsScreenRoute
import des.c5inco.pokedexer.ui.pokedex.pokemonDetailsViewModel

@HiltAndroidApp
class PokedexerApplication : Application()

sealed class BottomNavItem(
    val route: String,
    @StringRes val labelRes: Int,
    @DrawableRes val iconRes: Int
) {
    data object Pokedex : BottomNavItem(
        route = "pokedex",
        labelRes = R.string.pokedexLabel,
        iconRes = R.drawable.ic_catching_pokemon
    )
    data object Moves : BottomNavItem(
        route = "moves",
        labelRes = R.string.movesLabel,
        iconRes = R.drawable.ic_fitness_center
    )
    data object Items : BottomNavItem(
        route = "items",
        labelRes = R.string.itemsLabel,
        iconRes = R.drawable.ic_store_mall_directory
    )
}

private val bottomNavItems = listOf(
    BottomNavItem.Pokedex,
    BottomNavItem.Moves,
    BottomNavItem.Items
)

private val topLevelRoutes = setOf("pokedex/list", "moves", "items")

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PokedexerApp(
    viewModel: RootViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val density = LocalDensity.current
    var pokemon by remember { mutableStateOf(SamplePokemonData.first()) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route

    val showBottomBar = currentRoute in topLevelRoutes

    Scaffold(
        modifier = Modifier.semantics {
            testTagsAsResourceId = true
        },
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        val selected = currentDestination?.hierarchy?.any {
                            it.route == item.route || it.route == "${item.route}/list"
                        } == true

                        NavigationBarItem(
                            icon = {
                                Icon(
                                    painter = painterResource(id = item.iconRes),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            label = { Text(stringResource(item.labelRes)) },
                            selected = selected,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "pokedex",
            modifier = Modifier.padding(innerPadding),
            enterTransition = { Material3Transitions.SharedXAxisEnterTransition(density) },
            popEnterTransition = { Material3Transitions.SharedXAxisPopEnterTransition(density) },
            exitTransition = { Material3Transitions.SharedXAxisExitTransition(density) },
            popExitTransition = { Material3Transitions.SharedXAxisPopExitTransition(density) }
        ) {
            navigation(
                startDestination = "pokedex/list",
                route = "pokedex",
            ) {
                composable(
                    route = "pokedex/list",
                    popEnterTransition = { fadeIn() },
                    exitTransition = { fadeOut() }
                ) {
                    val pastPokemonId = it.savedStateHandle.get<Int>("pokemonId")

                    PokedexScreenRoute(
                        viewModel = hiltViewModel(),
                        onPokemonSelected = { selectedPokemon ->
                            pokemon = selectedPokemon
                            navController.navigate("pokedex/details")
                        },
                        pastPokemonSelected = pastPokemonId,
                        showNavigationIcon = false
                    )
                }
                composable(
                    route = "pokedex/details",
                    enterTransition = { Material3Transitions.SharedZAxisEnterTransition },
                    exitTransition = { Material3Transitions.SharedZAxisExitTransition },
                ) {
                    PokemonDetailsScreenRoute(
                        detailsViewModel = pokemonDetailsViewModel(pokemon),
                        onBackClick = { pokemonId ->
                            navController.previousBackStackEntry
                                ?.savedStateHandle
                                ?.set("pokemonId", pokemonId)
                            navController.popBackStack()
                        }
                    )
                }
            }
            composable(route = "moves") {
                MovesListScreenRoute(
                    viewModel = hiltViewModel(),
                    showNavigationIcon = false
                )
            }
            composable(route = "items") {
                ItemsScreenRoute(
                    viewModel = hiltViewModel(),
                    showNavigationIcon = false
                )
            }
        }
    }
}