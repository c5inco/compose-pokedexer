package des.c5inco.pokedexer

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.HiltAndroidApp
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.ui.home.HomeScreen
import des.c5inco.pokedexer.ui.home.MenuItem
import des.c5inco.pokedexer.ui.moves.MovesListScreenRoute
import des.c5inco.pokedexer.ui.pokedex.PokedexScreenRoute
import des.c5inco.pokedexer.ui.pokedex.PokemonDetailsScreenRoute
import des.c5inco.pokedexer.ui.pokedex.pokemonDetailsViewModel

@HiltAndroidApp
class PokedexerApplication : Application()

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun PokedexerApp() {
    val navController = rememberNavController()
    var pokemon by remember { mutableStateOf(SamplePokemonData.first()) }

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen {
                if (it == MenuItem.Pokedex) {
                    navController.navigate("pokedex")
                }
                if (it == MenuItem.Moves) {
                    navController.navigate("moves")
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
    }
}