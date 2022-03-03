@file:OptIn(ExperimentalFoundationApi::class)

package des.c5inco.pokedexer.ui.moves

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.data.moves.LocalMovesRepository
import des.c5inco.pokedexer.data.moves.SampleMoves
import des.c5inco.pokedexer.model.Move
import des.c5inco.pokedexer.ui.common.PokemonTypeLabel
import des.c5inco.pokedexer.ui.common.TypeLabelMetrics.Companion.MEDIUM
import des.c5inco.pokedexer.ui.theme.Theme.Companion.PokedexerTheme

@Composable
fun MovesListScreen(
    viewModel: MovesListViewModel
) {
    Surface {
        Column(
            Modifier
                .fillMaxSize()
                .padding(32.dp)
        ) {
            Text(
                text = "Moves",
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(
                    top = 64.dp, bottom = 24.dp
                )
            )
            if (viewModel.uiState.loading) {
                CircularProgressIndicator(color = Color.Black)
            } else {
                MovesList(viewModel.uiState.moves)
            }
        }
    }
}

@Composable
private fun MovesList(
    moves: List<Move> = SampleMoves
) {
    LazyColumn {
        stickyHeader {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface)
                ,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Move",
                    Modifier.weight(1f)
                )
                Text("Type", Modifier.requiredWidth(80.dp))
                Text("Cat.", Modifier.requiredWidth(40.dp))
                Text("Power", Modifier.requiredWidth(40.dp))
                Text("Acc.", Modifier.requiredWidth(40.dp))
            }
        }
        items(moves) { move ->
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    move.name.split("-").joinToString(" ") {
                        it[0].uppercase() + it.substring(1)
                    },
                    Modifier.weight(1f)
                )
                PokemonTypeLabel(
                    modifier = Modifier.requiredWidth(80.dp),
                    text = move.type,
                    colored = true,
                    metrics = MEDIUM
                )
                Text(
                    move.category,
                    Modifier.requiredWidth(40.dp)
                )
                Text(
                    "${move.power}",
                    textAlign = TextAlign.End,
                    modifier = Modifier.requiredWidth(40.dp)
                )
                Text(
                    "${move.accuracy}",
                    textAlign = TextAlign.End,
                    modifier = Modifier.requiredWidth(40.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun MovesListScreenPreview() {
    PokedexerTheme {
        Surface {
            MovesListScreen(MovesListViewModel(LocalMovesRepository()))
        }
    }
}

@Preview
@Composable
fun MovesListPreview() {
    PokedexerTheme {
        Surface {
            MovesList()
        }
    }
}