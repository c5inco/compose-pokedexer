package des.c5inco.pokedexer.ui.moves

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.data.moves.SampleMoves
import des.c5inco.pokedexer.model.Move
import des.c5inco.pokedexer.ui.common.CategoryIcon
import des.c5inco.pokedexer.ui.common.NavigationTopAppBar
import des.c5inco.pokedexer.ui.common.TypeLabel
import des.c5inco.pokedexer.ui.common.TypeLabelMetrics.Companion.MEDIUM
import des.c5inco.pokedexer.ui.theme.AppTheme
import des.c5inco.pokedexer.ui.theme.PokemonTypesTheme

@Composable
fun MovesListScreenRoute(
    viewModel: MovesListViewModel,
    onBackClick: () -> Unit = {}
) {
    MovesListScreen(
        loading = viewModel.uiState.loading,
        moves = viewModel.uiState.moves,
        onBackClick = onBackClick
    )
}

@Composable
fun MovesListScreen(
    loading: Boolean,
    moves: List<Move>,
    onBackClick: () -> Unit = {},
) {
    Surface {
        Column(
            Modifier.fillMaxSize()
        ) {
            NavigationTopAppBar(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(top = 16.dp),
                onBackClick = onBackClick
            )
            Column(
                Modifier
                    .weight(1f)
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    text = "Moves",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(
                        top = 16.dp, bottom = 24.dp
                    )
                )
                if (loading) {
                    CircularProgressIndicator()
                } else {
                    MovesList(moves = moves)
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MovesList(
    modifier: Modifier = Modifier,
    moves: List<Move> = SampleMoves
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = WindowInsets.navigationBars.asPaddingValues()
    ) {
        stickyHeader {
            val textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)

            CompositionLocalProvider(
                LocalTextStyle provides textStyle,
                LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Name",
                        modifier = Modifier.weight(1f),
                    )
                    Box(
                        Modifier.requiredWidth(75.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Type")
                    }
                    Box(
                        Modifier.requiredWidth(48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Cat")
                    }
                    Text(
                        text = "Pwr",
                        textAlign = TextAlign.End,
                        modifier = Modifier.requiredWidth(40.dp)
                    )
                    Text(
                        text = "Acc",
                        textAlign = TextAlign.End,
                        modifier = Modifier.requiredWidth(40.dp)
                    )
                }
            }
        }
        items(moves) { move ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                ,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    move.name.split("-").joinToString(" ") {
                        it[0].uppercase() + it.substring(1)
                    },
                    Modifier.weight(1f)
                )
                PokemonTypesTheme(types = listOf(move.type)) {
                    TypeLabel(
                        modifier = Modifier.requiredWidth(75.dp),
                        text = move.type,
                        colored = true,
                        metrics = MEDIUM
                    )
                }
                Box(
                    Modifier.requiredWidth(48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CategoryIcon(
                        modifier = Modifier.size(24.dp),
                        move = move
                    )
                }
                Text(
                    "${move.power ?: "—"}",
                    textAlign = TextAlign.End,
                    modifier = Modifier.requiredWidth(40.dp)
                )
                Text(
                    text = "${move.accuracy ?: "—"}",
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
    AppTheme {
        Surface {
            MovesListScreen(
                loading = false,
                moves = SampleMoves
            )
        }
    }
}

@Preview
@Composable
fun MovesListPreview() {
    AppTheme {
        Surface {
            MovesList()
        }
    }
}