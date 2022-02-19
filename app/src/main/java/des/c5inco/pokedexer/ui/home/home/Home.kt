package des.c5inco.pokedexer.ui.home.home

import androidx.compose.runtime.Composable
import des.c5inco.pokedexer.R

interface Home {
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

    companion object {
        @Composable
        fun Content(onMenuItemSelected: (MenuItem) -> Unit) {
            // VerticalScroller {
            //     Column {
            //         MainAppBar(onMenuItemSelected)
            //         Container(modifier = LayoutPadding(32.dp)) {
            //             NewsSection()
            //         }
            //     }
            // }
        }
    }
}