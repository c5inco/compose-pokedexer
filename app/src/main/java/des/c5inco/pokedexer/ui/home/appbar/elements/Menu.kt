package des.c5inco.pokedexer.ui.home.appbar.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.ui.common.PokeBall
import des.c5inco.pokedexer.ui.home.MenuItem
import des.c5inco.pokedexer.ui.home.MenuItem.Abilities
import des.c5inco.pokedexer.ui.home.MenuItem.Items
import des.c5inco.pokedexer.ui.home.MenuItem.Locations
import des.c5inco.pokedexer.ui.home.MenuItem.Moves
import des.c5inco.pokedexer.ui.home.MenuItem.Pokedex
import des.c5inco.pokedexer.ui.home.MenuItem.TypeCharts
import des.c5inco.pokedexer.ui.theme.AppTheme
import des.c5inco.pokedexer.ui.theme.PokemonTypesTheme

@Composable
fun Menu(
    onMenuItemSelected: (MenuItem) -> Unit = {}
) {
    val menuItems = listOf(
        Pokedex, Moves,
        Abilities, Items,
        Locations, TypeCharts
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        content = {
            items(menuItems.size) { index ->
                val item = menuItems[index]
                PokemonTypesTheme(types = listOf(item.typeColor.name)) {
                    MenuItemButton(
                        text = item.label
                    ) {
                        onMenuItemSelected(item)
                    }
                }
            }
        }
    )
}

@Composable
fun MenuItemButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .height(64.dp)
                .clickable { onClick() },
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = text,
                color = Color.White
            )
            PokeBall(
                Modifier
                    .align(Alignment.TopStart)
                    .offset(x = (-30).dp, y = (-40).dp)
                    .requiredSize(60.dp),
                Color.White,
                0.15f
            )
            PokeBall(
                Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 20.dp)
                    .requiredSize(96.dp),
                Color.White,
                0.15f
            )
        }
    }
}

@Preview
@Composable
fun MenuPreview() {
    AppTheme {
        Surface {
            Menu()
        }
    }
}