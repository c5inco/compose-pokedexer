package des.c5inco.pokedexer.ui.pokedex.section

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import des.c5inco.pokedexer.model.Pokemon

@Composable
fun MovesSection(
    pokemon: Pokemon
) {
    Text("\uD83D\uDEA7 - Moves for ${pokemon.name}")
}