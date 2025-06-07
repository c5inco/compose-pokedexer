package des.c5inco.pokedexer

// import des.c5inco.pokedexer.ui.items.ItemsScreenRoute // No longer directly used by NavHost
// import des.c5inco.pokedexer.ui.moves.MovesListScreenRoute // No longer directly used by NavHost
// import des.c5inco.pokedexer.ui.pokedex.PokedexScreenRoute // No longer directly used by NavHost
import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.HiltAndroidApp
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.ui.common.Material3Transitions
import des.c5inco.pokedexer.ui.home.HomeScreenRoute
import des.c5inco.pokedexer.ui.pokedex.PokemonDetailsScreenRoute
import des.c5inco.pokedexer.ui.pokedex.pokemonDetailsViewModel

@HiltAndroidApp
class PokedexerApplication : Application()

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PokedexerApp(
    // viewModel: RootViewModel = hiltViewModel() // RootViewModel seems unused
) {
    val navController = rememberNavController()
    val density = LocalDensity.current
    var pokemon by remember { mutableStateOf(SamplePokemonData.first()) }

    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = Modifier.semantics {
            testTagsAsResourceId = true
        },
        enterTransition = { Material3Transitions.SharedXAxisEnterTransition(density) },
        popEnterTransition =  { Material3Transitions.SharedXAxisPopEnterTransition(density) },
        exitTransition = { Material3Transitions.SharedXAxisExitTransition(density) },
        popExitTransition = { Material3Transitions.SharedXAxisPopExitTransition(density) }
    ) {
        composable(route = "home") { navBackStackEntry ->
            val pokemonIdFromDetails = navBackStackEntry.savedStateHandle.get<Int>("pokemonId")
            // Consume it only once
            navBackStackEntry.savedStateHandle.remove<Int>("pokemonId")

            HomeScreenRoute(
                // viewModel = hiltViewModel(), // This was previously commented out
                navigateToPokemonDetails = { selectedPokemon ->
                    pokemon = selectedPokemon
                    navController.navigate("details")
                },
                pokemonIdFromDetailsScreen = pokemonIdFromDetails
            )
        }
        composable(
            route = "details",
            enterTransition = { Material3Transitions.SharedZAxisEnterTransition },
            exitTransition = { Material3Transitions.SharedZAxisExitTransition },
        ) {
            PokemonDetailsScreenRoute(
                detailsViewModel = pokemonDetailsViewModel(pokemon),
                onBackClick = { returnedPokemonId ->
                    // Set the pokemonId on the "home" route's SavedStateHandle
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("pokemonId", returnedPokemonId)
                    navController.popBackStack()
                }
            )
        }
    }
}
