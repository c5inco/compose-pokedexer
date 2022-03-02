package des.c5inco.pokedexer.ui.moves

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.data.moves.LocalMovesRepository
import des.c5inco.pokedexer.ui.theme.Theme.Companion.PokedexerTheme

@Composable
fun MovesList(
    viewModel: MovesListViewModel
) {
    Surface {
        if (viewModel.uiState.loading) {
            CircularProgressIndicator(color = Color.Red)
        } else {
            LazyColumn {
                items(viewModel.uiState.moves) { move ->
                    Row {
                        Text("${move.id}")
                        Spacer(Modifier.width(12.dp))
                        Text(move.name)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MovesListPreview() {
    PokedexerTheme {
        MovesList(MovesListViewModel(LocalMovesRepository()))
    }
}