package des.c5inco.pokedexer

import android.app.Application
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.HiltAndroidApp
import des.c5inco.pokedexer.data.pokemon.LocalPokemonRepository
import des.c5inco.pokedexer.data.pokemon.PokemonRepository
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.ui.home.HomeScreen
import des.c5inco.pokedexer.ui.home.MenuItem
import des.c5inco.pokedexer.ui.pokedex.PokedexViewModel
import des.c5inco.pokedexer.ui.pokedex.PokemonDetails
import des.c5inco.pokedexer.ui.pokedex.PokemonListScreen

@HiltAndroidApp
class PokedexerApplication : Application()

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