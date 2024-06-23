package des.c5inco.pokedexer.ui.pokedex.section

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import des.c5inco.pokedexer.R
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.ui.common.Label
import des.c5inco.pokedexer.ui.theme.AppTheme

@Composable
fun AboutSection(
    pokemon: Pokemon
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        Text(
            text = pokemon.description,
            lineHeight = 24.sp
        )
        Surface(
            shape = RoundedCornerShape(12.dp),
            tonalElevation = 3.dp
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 20.dp)
            ) {
                Column(Modifier.weight(1f)) {
                    Label(text = "Height")
                    Spacer(Modifier.height(12.dp))
                    Text("${pokemon.height}m")
                }
                Column(Modifier.weight(1f)) {
                    Label(text = "Weight")
                    Spacer(Modifier.height(12.dp))
                    Text("${pokemon.weight}kg")
                }
            }
        }
        BreedingDetails(pokemon = pokemon)
    }
}

@Composable
private fun BreedingDetails(
    modifier: Modifier = Modifier,
    pokemon: Pokemon
) {
    Column(modifier) {
        Text(
            "Breeding",
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(Modifier.height(24.dp))
        Row(Modifier.fillMaxWidth()) {
            Label(
                text = "Gender",
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp)
            )
            if (pokemon.genderRate != -1) {
                Row(Modifier.weight(2f)) {
                    val femaleGenderRate = pokemon.genderRate * 12.5
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_outline_male_20),
                            contentDescription = null,
                            tint = Color(0xff6C79DB)
                        )
                        Spacer(Modifier.width(2.dp))
                        Text("${100 - femaleGenderRate}%")
                    }
                    Spacer(Modifier.width(12.dp))
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_outline_female_20),
                            contentDescription = null,
                            tint = Color(0xffF0729F)
                        )
                        Spacer(Modifier.width(2.dp))
                        Text("$femaleGenderRate%")
                    }
                }
            } else {
                Text(
                    "Genderless",
                    modifier = Modifier.weight(2.2f)
                )
            }
        }
        Spacer(Modifier.height(18.dp))
        Row(Modifier.fillMaxWidth()) {
            Label(
                text = "Egg Groups",
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp)
            )
            Text(
                "-",
                modifier = Modifier.weight(2f)
            )
        }
        Spacer(Modifier.height(18.dp))
        Row(Modifier.fillMaxWidth()) {
            Label(
                text = "Egg Cycles",
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp)
            )
            Text(
                "-",
                modifier = Modifier.weight(2f)
            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview
@Composable
fun AboutSectionPreview() {
    AppTheme {
        Surface(Modifier.fillMaxWidth()) {
            AboutSection(pokemon = SamplePokemonData[0])
        }
    }
}
