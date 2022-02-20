package des.c5inco.pokedexer.ui.home.appbar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.ui.common.PokeBallBackground
import des.c5inco.pokedexer.ui.home.MenuItem
import des.c5inco.pokedexer.ui.home.appbar.elements.Menu
import des.c5inco.pokedexer.ui.home.appbar.elements.RoundedSearchBar
import des.c5inco.pokedexer.ui.theme.Theme.Companion.PokedexerTheme

@Composable
fun MainAppBar(onMenuItemSelected: (MenuItem) -> Unit) {
    Surface(
        shape = RoundedCornerShape(
            bottomStart = 32.dp,
            bottomEnd = 32.dp
        )
    ) {
        Box {
            PokeBallBackground(
                Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 90.dp, y = (-70).dp)
            )
            Column(
                modifier = Modifier.padding(top = 32.dp, bottom = 16.dp, start = 32.dp, end = 32.dp)
            ) {
                Text(
                    text = "What Pokémon\nare you looking for?",
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(
                        top = 64.dp, bottom = 24.dp
                    )
                )
                RoundedSearchBar()
                Spacer(modifier = Modifier.height(32.dp))
                Menu(onMenuItemSelected)
            }
        }
    }
}

@Preview
@Composable
fun PreviewMainAppBar() {
    PokedexerTheme {
        Surface(
            Modifier.fillMaxSize(),
            color = Color.DarkGray
        ) {
            Column {
                MainAppBar(onMenuItemSelected = {})
            }
        }
    }
}