package des.c5inco.pokedexer.ui.pokedex.section

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.ui.common.CategoryIcon
import des.c5inco.pokedexer.ui.common.TypeLabel
import des.c5inco.pokedexer.ui.common.TypeLabelMetrics
import des.c5inco.pokedexer.ui.pokedex.PokemonDetailsMoves
import des.c5inco.pokedexer.ui.theme.PokemonTypesTheme

fun LazyListScope.MovesSection(
    pokemon: Pokemon,
    moves: List<PokemonDetailsMoves>
) {
    item {
        val textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)

        CompositionLocalProvider(
            LocalTextStyle provides textStyle,
            LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(vertical = 12.dp, horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Lvl",
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .requiredWidth(40.dp)
                        .padding(end = 12.dp)
                )
                Text(
                    text = "Move",
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = "Type",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.requiredWidth(75.dp)
                )
                Text("Cat",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.requiredWidth(48.dp))
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
    items(moves) { (move, targetLevel) ->
        Row(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp, start = 24.dp, end = 24.dp)
            ,
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
            CategoryIcon(
                modifier = Modifier.requiredWidth(48.dp),
                move = move
            )
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

    item {
        // In lieu of contentPadding for all sections
        Spacer(Modifier
            .padding(WindowInsets.navigationBars.asPaddingValues())
            .height(100.dp)
        )
    }
}