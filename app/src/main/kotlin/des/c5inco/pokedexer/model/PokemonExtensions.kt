package des.c5inco.pokedexer.model

import androidx.compose.ui.graphics.Color
import des.c5inco.pokedexer.shared.model.Pokemon

fun Pokemon.color(): Color {
    val type = typeOfPokemon.elementAtOrNull(0)
    return type?.let { mapTypeToColor(it) } ?: Color.Magenta
}
