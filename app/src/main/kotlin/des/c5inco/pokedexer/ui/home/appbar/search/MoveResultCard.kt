package des.c5inco.pokedexer.ui.home.appbar.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.data.moves.SampleMoves
import des.c5inco.pokedexer.model.Move
import des.c5inco.pokedexer.model.Type
import des.c5inco.pokedexer.model.categoryIcon
import des.c5inco.pokedexer.ui.theme.AppTheme
import des.c5inco.pokedexer.ui.theme.PokemonTypesTheme

@Composable
fun MoveResultCard(
    modifier: Modifier = Modifier,
    move: Move = SampleMoves.first()
) {
    PokemonTypesTheme(
        types = listOf(getMoveType(move).toString())
    ) {
        Card(
            modifier = modifier
                .width(200.dp),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = PokemonTypesTheme.colorScheme.surfaceVariant,
                contentColor = PokemonTypesTheme.colorScheme.primary
            )
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
                Icon(
                    painter = painterResource(id = move.categoryIcon()),
                    contentDescription = move.category,
                    tint = PokemonTypesTheme.colorScheme.primary.copy(alpha = 0.4f),
                    modifier = Modifier
                        .size(36.dp)
                )
            }
        }
    }
}

private fun getMoveType(move: Move): Type {
    return when (move.category.lowercase()) {
        "physical" -> Type.Fire
        "special" -> Type.Dragon
        else -> Type.Dark
    }
}

@Preview()
@Composable
fun MoveResultCardPreview() {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp)
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
                item {
                    MoveResultCard()
                }
                item {
                    MoveResultCard()
                }
                item {
                    MoveResultCard()
                }
                item {
                    MoveResultCard()
                }
                item {
                    MoveResultCard()
                }
            }
        }
    }
}

