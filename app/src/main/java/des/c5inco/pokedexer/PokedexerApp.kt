package des.c5inco.pokedexer

import android.app.Application
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import des.c5inco.pokedexer.data.pokemon.*
import des.c5inco.pokedexer.ui.home.HomeScreen
import des.c5inco.pokedexer.ui.home.MenuItem
import des.c5inco.pokedexer.ui.pokedex.PokedexViewModel
import des.c5inco.pokedexer.ui.pokedex.PokemonDetails
import des.c5inco.pokedexer.ui.pokedex.PokemonListScreen

class PokedexerApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { PokemonDatabase.getInstance(this) }
    val repository by lazy { RemotePokemonRepository(database.pokemonDao()) }
}

@Composable
fun PokedexerApp(
    pokemonRepository: PokemonRepository = LocalPokemonRepository()
) {
    val navController = rememberNavController()
    var pokemon by remember { mutableStateOf(SamplePokemonData.first()) }

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen {
                if (it == MenuItem.Pokedex) {
                    navController.navigate("pokedex")
                }
            }
        }
        navigation(startDestination = "list", route = "pokedex") {
            composable("list") {
                PokemonListScreen(
                    viewModel = PokedexViewModel(pokemonRepository)
                ) {
                    pokemon = it
                    navController.navigate("details")
                }
            }
            composable("details") { PokemonDetails(pokemon = pokemon) }
        }
    }
}