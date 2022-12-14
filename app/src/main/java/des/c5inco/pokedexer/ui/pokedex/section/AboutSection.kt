package des.c5inco.pokedexer.ui.pokedex.section

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.ui.theme.Theme

@Composable
fun AboutSection(
    pokemon: Pokemon
) {
    Column {
        Text(
            text = pokemon.description,
            lineHeight = 24.sp
        )
        Spacer(Modifier.height(28.dp))
        Surface(
            shape = RoundedCornerShape(12.dp),
            elevation = 12.dp
        ) {
            Row(Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 20.dp)
            ) {
                Column(Modifier.weight(1f)) {
                    Text("Height", modifier = Modifier.alpha(0.4f))
                    Spacer(Modifier.height(12.dp))
                    Text("${pokemon.height}cm")
                }
                Column(Modifier.weight(1f)) {
                    Text("Weight", modifier = Modifier.alpha(0.4f))
                    Spacer(Modifier.height(12.dp))
                    Text("${pokemon.weight}kg")
                }
            }
        }
    }
}

@Preview
@Composable
fun AboutSectionPreview() {
    Theme.PokedexerTheme {
        Surface(Modifier.fillMaxWidth()) {
            AboutSection(pokemon = SamplePokemonData[0])
        }
    }
}
