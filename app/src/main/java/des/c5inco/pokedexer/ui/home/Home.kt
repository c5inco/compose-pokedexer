package des.c5inco.pokedexer.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import des.c5inco.pokedexer.R
import des.c5inco.pokedexer.ui.home.appbar.MainAppBar

sealed class MenuItem(
    val label: String,
    val colorResId: Int
) {
    object Pokedex : MenuItem("Pokedex", R.color.poke_teal)
    object Moves : MenuItem("Moves", R.color.poke_red)
    object Abilities : MenuItem("Abilities", R.color.poke_light_blue)
    object Items : MenuItem("Items", R.color.poke_yellow)
    object Locations : MenuItem("Locations", R.color.poke_purple)
    object TypeCharts : MenuItem("Type charts", R.color.poke_brown)
}

@Composable
fun HomeScreen(
    onMenuItemSelected: (MenuItem) -> Unit = {}
) {
    Surface(
        Modifier.fillMaxSize(),
        color = Color.DarkGray
    ) {
        Column {
            MainAppBar(onMenuItemSelected = onMenuItemSelected)
        }
    }
}