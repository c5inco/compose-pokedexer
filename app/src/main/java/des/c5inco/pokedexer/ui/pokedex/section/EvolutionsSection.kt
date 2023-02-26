package des.c5inco.pokedexer.ui.pokedex.section

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.data.pokemon.mapSampleEvolutionsToList
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.ui.common.PokeBall
import des.c5inco.pokedexer.ui.common.PokemonImage
import des.c5inco.pokedexer.ui.pokedex.PokemonDetailsEvolutions
import des.c5inco.pokedexer.ui.theme.AppTheme

@Composable
fun EvolutionSection(
    modifier: Modifier = Modifier,
    evolutions: List<PokemonDetailsEvolutions> = listOf(),
) {
    Column(modifier) {
        Text(
            "Evolution chain",
            style = MaterialTheme.typography.titleLarge,
        )

        evolutions.forEachIndexed { idx, evo ->
            if (idx < evolutions.size - 1) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val e1 = evo
                    val e2 = evolutions[idx + 1]

                    EvolutionCard(e1.pokemon)
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.surfaceTint
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "Lvl ${e2.targetLevel}",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    EvolutionCard(e2.pokemon)
                }
            }
            if (idx < evolutions.size - 2)
                Divider()
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview
@Composable
fun EvolutionsSectionPreview() {
    AppTheme {
        Surface {
            Column {
                EvolutionSection(
                    modifier = Modifier.padding(vertical = 32.dp),
                    evolutions = mapSampleEvolutionsToList(
                        SamplePokemonData.first().evolutionChain
                    )
                )
                EvolutionSection(
                    modifier = Modifier.padding(vertical = 32.dp),
                    evolutions = mapSampleEvolutionsToList(
                        SamplePokemonData[3].evolutionChain
                    )
                )
            }
        }
    }
}

@Composable
private fun EvolutionCard(
    pokemon: Pokemon,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable {}
            .width(128.dp)
            .padding(horizontal = 8.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            PokeBall(
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
            )
            PokemonImage(
                modifier = Modifier.size(72.dp),
                image = pokemon.id
            )
        }
        Spacer(Modifier.height(4.dp))
        Text(
            pokemon.name
        )
        Spacer(Modifier.height(4.dp))
    }
}

@Preview
@Composable
fun EvolutionCardPreview() {
    AppTheme {
        Surface {
            Column {
                EvolutionCard(pokemon = SamplePokemonData[1])
                EvolutionCard(pokemon = SamplePokemonData[4])
            }
        }
    }
}
