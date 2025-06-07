package des.c5inco.pokedexer.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.ui.home.appbar.elements.MenuItem
import des.c5inco.pokedexer.ui.items.ItemsScreenRoute
import des.c5inco.pokedexer.ui.moves.MovesListScreenRoute
import des.c5inco.pokedexer.ui.pokedex.PokedexScreenRoute
import des.c5inco.pokedexer.ui.theme.AppTheme

@Composable
fun HomeScreenRoute(
    // viewModel: HomeViewModel, // Remains commented out
    navigateToPokemonDetails: (Pokemon) -> Unit,
    pokemonIdFromDetailsScreen: Int?
) {
    HomeScreen(
        navigateToPokemonDetails = navigateToPokemonDetails,
        pokemonIdFromDetailsScreen = pokemonIdFromDetailsScreen
    )
}

@Composable
fun HomeScreen(
    navigateToPokemonDetails: (Pokemon) -> Unit = {},
    pokemonIdFromDetailsScreen: Int? = null
) {
    var currentSelectedItem by remember { mutableStateOf<MenuItem>(MenuItem.Pokedex) }

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp), // Allow inner scaffolds to handle insets
        bottomBar = {
            BottomNavigationBar(
                currentSelectedItem = currentSelectedItem,
                onItemSelected = { selectedItem ->
                    currentSelectedItem = selectedItem
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues) // Apply padding for bottom bar
                .fillMaxSize()
        ) {
            when (currentSelectedItem) {
                MenuItem.Pokedex -> PokedexScreenRoute(
                    viewModel = hiltViewModel(),
                    onPokemonSelected = navigateToPokemonDetails,
                    pastPokemonSelected = pokemonIdFromDetailsScreen,
                    onBackClick = { /* No-op, handled by NavHost or system back */ }
                )
                MenuItem.Moves -> MovesListScreenRoute(
                    viewModel = hiltViewModel(),
                    onBackClick = { /* No-op, handled by NavHost or system back */ }
                )
                MenuItem.Items -> ItemsScreenRoute(
                    viewModel = hiltViewModel(),
                    onBackClick = { /* No-op, handled by NavHost or system back */ }
                )
                // Default case should ideally not be reached if BottomNavigationBar only shows these 3
                else -> PokedexScreenRoute( // Default to Pokedex or a placeholder
                    viewModel = hiltViewModel(),
                    onPokemonSelected = navigateToPokemonDetails,
                    pastPokemonSelected = pokemonIdFromDetailsScreen,
                    onBackClick = { }
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun HomeScreenPreview() {
    AppTheme {
        HomeScreen()
    }
}
