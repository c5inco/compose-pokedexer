@file:OptIn(ExperimentalFoundationApi::class)

package des.c5inco.pokedexer.ui.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import des.c5inco.pokedexer.model.Type
import des.c5inco.pokedexer.ui.home.appbar.MainAppBar
import des.c5inco.pokedexer.ui.theme.AppTheme

sealed class MenuItem(
    val label: String,
    val typeColor: Type
) {
    object Pokedex : MenuItem("Pokedex", Type.Grass)
    object Moves : MenuItem("Moves", Type.Fire)
    object Abilities : MenuItem("Abilities", Type.Water)
    object Items : MenuItem("Items", Type.Electric)
    object Locations : MenuItem("Locations", Type.Dragon)
    object TypeCharts : MenuItem("Type charts", Type.Psychic)
}

@Composable
fun HomeScreen(
    onMenuItemSelected: (MenuItem) -> Unit = {}
) {
    var openAlertDialog by remember { mutableStateOf(false) }

    Surface(
        Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            MainAppBar(
                onMenuItemSelected = {
                    when (it) {
                        MenuItem.Moves,
                        MenuItem.Pokedex,
                        MenuItem.Items ->
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

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview
@Composable
fun HomeScreenPreview() {
    AppTheme {
        HomeScreen()
    }
}