package des.c5inco.pokedexer

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.ui.home.HomeScreen
import des.c5inco.pokedexer.ui.home.MenuItem
import des.c5inco.pokedexer.ui.pokedex.PokemonDetails
import des.c5inco.pokedexer.ui.pokedex.PokemonListScreen

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
            }
        }
        navigation(startDestination = "list", route = "pokedex") {
            composable("list") {
                PokemonListScreen {
                    pokemon = it
                    navController.navigate("details")
                }
            }
            composable("details") { PokemonDetails(pokemon = pokemon) }
        }
    }
}