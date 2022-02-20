package des.c5inco.pokedexer.ui.pokedex.section

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.ui.entity.Pokemon

data class Stat(
    val label: String,
    val value: Int?,
    val max: Int = 100
) {
    val progress: Float =
        1f * (value ?: 0) / max
}

@Composable
fun BaseStatsSection(
    pokemon: Pokemon
) {
    val stats = listOf(
        Stat("HP", pokemon.hp),
        Stat("Attack", pokemon.attack),
        Stat("Defense", pokemon.defense),
        Stat("Sp. Atk", pokemon.specialAttack),
        Stat("Sp. Def", pokemon.specialDefense),
        Stat("Speed", pokemon.speed),
        Stat("Total", pokemon.total, 600)
    )

    StatsTable(stats)
}

@Composable
private fun StatsTable(stats: List<Stat>) {
    Column(Modifier.fillMaxWidth()) {
        stats.forEach { stat ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    stat.label,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .weight(1f)
                )
                Text(
                    "${stat.value}",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .weight(0.7f)
                )
                LinearProgressIndicator(
                    progress = stat.progress,
                    color = Color.Red,
                    modifier = Modifier
                        .clip(RoundedCornerShape(100))
                        .weight(2.2f)
                )
            }
        }
    }
}