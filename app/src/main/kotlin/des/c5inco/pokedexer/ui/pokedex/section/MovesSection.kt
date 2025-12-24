package des.c5inco.pokedexer.ui.pokedex.section

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.R
import des.c5inco.pokedexer.ui.common.CategoryIcon
import des.c5inco.pokedexer.ui.common.TypeLabel
import des.c5inco.pokedexer.ui.common.TypeLabelMetrics
import des.c5inco.pokedexer.ui.pokedex.PokemonDetailsMoves
import des.c5inco.pokedexer.ui.theme.PokemonTypesTheme

@Composable
fun MovesSection(
    modifier: Modifier = Modifier,
    moves: List<PokemonDetailsMoves>
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            top = 24.dp,
            start = 24.dp,
            end = 24.dp,
            bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 60.dp
        )
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
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.levelTableHeader),
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .requiredWidth(40.dp)
                            .padding(end = 12.dp)
                    )
                    Text(
                        text = stringResource(R.string.nameTableHeader),
                        modifier = Modifier.weight(1f),
                    )
                    Text(
                        text = stringResource(R.string.typeTableHeader),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.requiredWidth(75.dp)
                    )
                    Text(
                        text = stringResource(R.string.categoryTableHeader),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.requiredWidth(48.dp)
                    )
                    Text(
                        text = stringResource(R.string.powerTableHeader),
                        textAlign = TextAlign.End,
                        modifier = Modifier.requiredWidth(40.dp)
                    )
                    Text(
                        text = stringResource(R.string.accuracyTableHeader),
                        textAlign = TextAlign.End,
                        modifier = Modifier.requiredWidth(40.dp)
                    )
                }
            }
        }
        items(moves) { (move, targetLevel) ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$targetLevel",
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .requiredWidth(40.dp)
                        .padding(end = 12.dp)
                )
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
                        metrics = TypeLabelMetrics.MEDIUM
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