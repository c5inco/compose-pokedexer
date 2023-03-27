package des.c5inco.pokedexer.ui.pokedex.section

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.ui.pokedex.PokemonDetailsMoves

@Composable
fun MovesSection(
    pokemon: Pokemon,
    moves: List<PokemonDetailsMoves>
) {
    Column {
        Text("\uD83D\uDEA7 - Moves for ${pokemon.name}: ${moves.size}")

        moves.forEach {
            Text(it.move.name)
        }
    }
}