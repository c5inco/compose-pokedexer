package des.c5inco.pokedexer.ui.pokedex.section

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
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.data.pokemon.mapSampleEvolutionsToList
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.ui.common.PokeBall
import des.c5inco.pokedexer.ui.common.PokemonImage
import des.c5inco.pokedexer.ui.pokedex.PokemonDetailsEvolutions
import des.c5inco.pokedexer.ui.theme.Grey100
import des.c5inco.pokedexer.ui.theme.PokedexerTheme

@Composable
fun EvolutionSection(
    modifier: Modifier = Modifier,
    evolutions: List<PokemonDetailsEvolutions> = listOf(),
) {
    Column(modifier) {
        Text(
            "Evolution chain",
            style = MaterialTheme.typography.h6,
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
                            tint = Color.Black.copy(alpha = 0.4f)
                        )
                        Text(
                            "Lvl ${e2.targetLevel}",
                            style = MaterialTheme.typography.caption,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    EvolutionCard(e2.pokemon)
                }
            }
            if (idx < evolutions.size - 2)
                Divider(color = Grey100)
        }
    }
}

@Preview
@Composable
fun EvolutionsSectionPreview() {
    PokedexerTheme {
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
            .clickable{}
            .width(128.dp)
            .padding(horizontal = 8.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            PokeBall(
                modifier = Modifier.size(80.dp),
                tint = Grey100,
            )
            PokemonImage(
                modifier = Modifier.size(72.dp), image = pokemon.id
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
    PokedexerTheme {
        Surface {
            Column {
                EvolutionCard(pokemon = SamplePokemonData[1])
                EvolutionCard(pokemon = SamplePokemonData[4])
            }
        }
    }
}
