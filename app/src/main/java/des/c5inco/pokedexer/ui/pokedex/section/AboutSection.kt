package des.c5inco.pokedexer.ui.pokedex.section

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import des.c5inco.pokedexer.model.Pokemon

@Composable
fun AboutSection(
    pokemon: Pokemon
) {
    Text(
        text = pokemon.description,
        lineHeight = 24.sp
    )
}
