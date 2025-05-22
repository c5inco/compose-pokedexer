package des.c5inco.pokedexer.ui.home.appbar.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.data.moves.SampleMoves
import des.c5inco.pokedexer.model.Move
import des.c5inco.pokedexer.model.category
import des.c5inco.pokedexer.model.type
import des.c5inco.pokedexer.ui.common.CategoryIcon
import des.c5inco.pokedexer.ui.common.TypeIconLabel
import des.c5inco.pokedexer.ui.theme.AppTheme
import des.c5inco.pokedexer.ui.theme.MoveCategoryTheme

@Composable
fun MoveResultCard(
    modifier: Modifier = Modifier,
    move: Move = SampleMoves.first(),
    onSelected: (Move) -> Unit = { }
) {
    MoveCategoryTheme(
        category = move.category()
    ) {
        Card(
            modifier = modifier
                .width(200.dp)
                .clickable { onSelected(move) },
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, top = 12.dp, bottom = 12.dp, end = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = move.name,
                    style = MaterialTheme.typography.bodyMedium,
                )
                CategoryIcon(
                    move = move,
                    modifier = Modifier
                        .size(36.dp)
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun MoveResultCardPreview() {
    AppTheme {
        Surface(
            tonalElevation = if (isSystemInDarkTheme()) 2.dp else 0.dp,
            modifier = Modifier.padding(vertical = 20.dp)
        ) {
            LazyHorizontalGrid(
                rows = GridCells.Fixed(4),
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .height(240.dp)
                    .fillMaxWidth()
            ) {
                items(5) {
                    MoveResultCard(
                        move = SampleMoves[it],
                    )
                }
            }
        }
    }
}

@Composable
fun MoveResultExpandedCard(
    modifier: Modifier = Modifier,
    move: Move = SampleMoves.first(),
) {
    MoveCategoryTheme(
        category = move.category()
    ) {
        Card(
            modifier = modifier
                .width(260.dp),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            )
        ) {
            Box {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                ) {
                    Text(
                        text = move.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = MoveCategoryTheme.colorScheme.primary,
                    )
                    Spacer(Modifier.height(6.dp))

                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TypeIconLabel(type = move.type())
                            Spacer(Modifier.width(6.dp))
                            ValuesText(
                                label = "PP",
                                value = move.pp,
                                modifier = Modifier.weight(0.75f)
                            )
                            ValuesText(
                                label = "PWR",
                                value = move.power ?: -1,
                            )
                            ValuesText(
                                label = "ACC",
                                value = move.accuracy ?: -1,
                            )
                        }
                        Spacer(Modifier.height(12.dp))
                        Text(
                            text = move.description,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
                CategoryIcon(
                    move = move,
                    modifier = Modifier
                        .size(64.dp)
                        .align(Alignment.TopEnd)
                        .offset(x = 16.dp, y = (-16).dp)
                        .graphicsLayer { alpha = 0.4f }
                )
            }
        }
    }
}

@Composable
private fun RowScope.ValuesText(
    modifier: Modifier = Modifier.weight(1f),
    label: String,
    value: Int
) {
    Row(
        modifier = modifier
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.graphicsLayer { alpha = 0.7f }
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = if (value == -1) "-" else value.toString(),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@PreviewLightDark
@Composable
private fun MoveResultExpandedCardPreview() {
    AppTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            MoveResultExpandedCard(
                move = SampleMoves[3],
            )
            MoveResultExpandedCard(
                move = SampleMoves[1],
            )
            MoveResultExpandedCard(
                move = SampleMoves[2],
            )
        }
    }
}