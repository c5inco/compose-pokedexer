package des.c5inco.pokedexer.ui.pokedex.section

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import des.c5inco.pokedexer.model.Pokemon

@Composable
fun AboutSection(
    pokemon: Pokemon
) {
    Text(pokemon.description)
}
