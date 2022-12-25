package des.c5inco.pokedexer.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    var openAlertDialog by remember { mutableStateOf(false) }

    Surface(
        Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column {
            MainAppBar(
                onMenuItemSelected = {
                    when (it) {
                        MenuItem.Moves,
                        MenuItem.Pokedex ->
                            onMenuItemSelected(it)
                        else ->
                            openAlertDialog = true
                    }
                }
            )
        }
    }

    if (openAlertDialog) {
        AlertDialog(
            onDismissRequest = { openAlertDialog = false },
            title = {
                Text(text = "\uD83D\uDEA7 Feature WIP")
            },
            text = {
                Text(
                    "This area is under construction!"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openAlertDialog = false
                    }
                ) {
                    Text("Dismiss")
                }
            },
        )
    }
}