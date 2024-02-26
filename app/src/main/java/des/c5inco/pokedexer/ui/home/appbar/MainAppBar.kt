package des.c5inco.pokedexer.ui.home.appbar

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.ui.common.PokeBallBackground
import des.c5inco.pokedexer.ui.home.MenuItem
import des.c5inco.pokedexer.ui.home.appbar.elements.Menu
import des.c5inco.pokedexer.ui.home.appbar.elements.RoundedSearchBar
import des.c5inco.pokedexer.ui.theme.AppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainAppBar(
    searchText: TextFieldState = rememberTextFieldState(),
    onMenuItemSelected: (MenuItem) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(
            bottomStart = 32.dp,
            bottomEnd = 32.dp
        ),
        tonalElevation = if (isSystemInDarkTheme()) 2.dp else 0.dp
    ) {
        Box {
            PokeBallBackground(
                Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 90.dp, y = (-70).dp),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
            )
            Column(
                modifier = Modifier.padding(32.dp)
            ) {
                Text(
                    text = "What Pokémon\nare you looking for?",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(
                        top = 64.dp, bottom = 32.dp
                    )
                )
                RoundedSearchBar(
                    searchText = searchText
                )
                Spacer(modifier = Modifier.height(32.dp))
                Menu(
                    modifier = Modifier.padding(bottom = 16.dp),
                    onMenuItemSelected = onMenuItemSelected
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
@Composable
fun MainAppBarPreview() {
    AppTheme {
        Surface(
            Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                MainAppBar(onMenuItemSelected = {})
            }
        }
    }
}