package des.c5inco.pokedexer.ui.home.appbar.elements

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.ui.common.PokeBall
import des.c5inco.pokedexer.ui.home.home.Home
import des.c5inco.pokedexer.ui.home.home.Home.MenuItem.*
import des.c5inco.pokedexer.ui.theme.Theme.Companion.PokedexerTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Menu(
    onMenuItemSelected: (Home.MenuItem) -> Unit = {}
) {
    val menuItems = listOf(
        Pokedex, Moves,
        Abilities, Items,
        Locations, TypeCharts
    )

    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        content = {
            items(menuItems.size) { index ->
                val item = menuItems[index]
                MenuItemButton(
                    text = item.label,
                    color = colorResource(id = item.colorResId)
                ) {
                    onMenuItemSelected(item)
                }
            }
        }
    )
}

@Composable
fun MenuItemButton(
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier,
        color = color,
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
    PokedexerTheme {
        Surface {
            Menu()
        }
    }
}