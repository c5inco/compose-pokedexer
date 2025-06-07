package des.c5inco.pokedexer.ui.home

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import des.c5inco.pokedexer.ui.home.appbar.elements.MenuItem
import des.c5inco.pokedexer.ui.home.appbar.elements.mapMenuItemToIcon
import des.c5inco.pokedexer.ui.theme.AppTheme

@Composable
fun BottomNavigationBar(
    currentSelectedItem: MenuItem,
    onItemSelected: (MenuItem) -> Unit
) {
    val items = listOf(
        MenuItem.Pokedex,
        MenuItem.Moves,
        MenuItem.Items
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = mapMenuItemToIcon(item)),
                        contentDescription = stringResource(id = item.label)
                    )
                },
                label = { Text(stringResource(id = item.label)) },
                selected = currentSelectedItem == item,
                onClick = { onItemSelected(item) }
            )
        }
    }
}

@Preview
@Composable
fun PreviewBottomNavigationBar() {
    var selectedItem by remember { mutableStateOf<MenuItem>(MenuItem.Pokedex) }
    AppTheme {
        BottomNavigationBar(
            currentSelectedItem = selectedItem,
            onItemSelected = { selectedItem = it }
        )
    }
}
