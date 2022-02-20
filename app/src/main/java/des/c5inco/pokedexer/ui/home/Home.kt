package des.c5inco.pokedexer.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import des.c5inco.pokedexer.ui.home.appbar.MainAppBar
import des.c5inco.pokedexer.ui.theme.PokemonColors

sealed class MenuItem(
    val label: String,
    val color: Color
) {
    object Pokedex : MenuItem("Pokedex", PokemonColors.Teal)
    object Moves : MenuItem("Moves", PokemonColors.Red)
    object Abilities : MenuItem("Abilities", PokemonColors.LightBlue)
    object Items : MenuItem("Items", PokemonColors.Yellow)
    object Locations : MenuItem("Locations", PokemonColors.Purple)
    object TypeCharts : MenuItem("Type charts", PokemonColors.Brown)
}

@Composable
fun HomeScreen(
    onMenuItemSelected: (MenuItem) -> Unit = {}
) {
    Surface(
        Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column {
            MainAppBar(onMenuItemSelected = onMenuItemSelected)
        }
    }
}