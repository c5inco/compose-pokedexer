package des.c5inco.pokedexer

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.HiltAndroidApp
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.ui.home.HomeScreen
import des.c5inco.pokedexer.ui.home.MenuItem
import des.c5inco.pokedexer.ui.items.ItemsScreenRoute
import des.c5inco.pokedexer.ui.moves.MovesListScreenRoute
import des.c5inco.pokedexer.ui.pokedex.PokedexScreenRoute
import des.c5inco.pokedexer.ui.pokedex.PokemonDetailsScreenRoute
import des.c5inco.pokedexer.ui.pokedex.pokemonDetailsViewModel

@HiltAndroidApp
class PokedexerApplication : Application()

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PokedexerApp() {
    val navController = rememberNavController()
    var pokemon by remember { mutableStateOf(SamplePokemonData.first()) }

    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = Modifier.semantics {
            testTagsAsResourceId = true
        }
    ) {
        composable("home") {
            HomeScreen {
                if (it == MenuItem.Pokedex) {
                    navController.navigate("pokedex")
                }
                if (it == MenuItem.Moves) {
                    navController.navigate("moves")
                }
                if (it == MenuItem.Items) {
                    navController.navigate("items")
                }
            }
        }
        navigation(startDestination = "list", route = "pokedex") {
            composable("list") {
                PokedexScreenRoute(
                    viewModel = hiltViewModel(),
                    onPokemonSelected = {
                        pokemon = it
                        navController.navigate("details")
                    },
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable("details") {
                PokemonDetailsScreenRoute(
                    viewModel = hiltViewModel(),
                    detailsViewModel = pokemonDetailsViewModel(pokemon),
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
        composable(route = "moves") {
            MovesListScreenRoute(
                viewModel = hiltViewModel(),
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(route = "items") {
            ItemsScreenRoute(
                viewModel = hiltViewModel(),
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}