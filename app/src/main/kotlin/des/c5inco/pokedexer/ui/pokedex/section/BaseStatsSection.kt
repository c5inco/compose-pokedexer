package des.c5inco.pokedexer.ui.pokedex.section

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.ui.common.Label
import des.c5inco.pokedexer.ui.theme.AppTheme
import des.c5inco.pokedexer.ui.theme.PokemonTypesKolorTheme
import des.c5inco.pokedexer.ui.theme.PokemonTypesTheme
import kotlinx.coroutines.delay

data class Stat(
    val label: String,
    val value: Int?,
    val max: Int = 200
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
    )

    Column(Modifier.fillMaxWidth()) {
        stats.forEachIndexed { idx, stat ->
            val statValue = remember { Animatable(0f) }

            LaunchedEffect(stat) {
                delay(70L * idx)
                statValue.animateTo(
                    targetValue = stat.progress,
                    animationSpec = spring(
                        0.6f,
                        1000f
                    )
                )
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Label(
                    text = stat.label,
                    modifier = Modifier
                        .weight(1f)
                        .graphicsLayer { alpha = 0.7f }
                )
                Text(
                    "${stat.value}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .weight(0.6f)
                )

                val indicatorColor by animateColorAsState(
                    targetValue = PokemonTypesTheme.colorScheme.primary,
                    tween(durationMillis = 500),
                    label = "statsProgressIndicatorColor"
                )

                LinearProgressIndicator(
                    progress = { statValue.value },
                    color = indicatorColor,
                    modifier = Modifier
                        .clip(RoundedCornerShape(100))
                        .weight(2.5f)
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun BaseStatsSectionPreview() {
    val pokemon = SamplePokemonData.first()

    AppTheme {
        Surface(Modifier.fillMaxWidth()) {
            PokemonTypesKolorTheme(
                types = pokemon.typeOfPokemon
            ) {
                BaseStatsSection(pokemon = pokemon)
            }
        }
    }
}