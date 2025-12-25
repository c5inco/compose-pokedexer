package des.c5inco.pokedexer.model

import des.c5inco.pokedexer.R
import des.c5inco.pokedexer.shared.model.Move

fun Move.categoryIcon(): Int {
    return when(category) {
        "Physical" -> R.drawable.ic_move_physical
        "Special" -> R.drawable.ic_move_special
        else -> R.drawable.ic_move_status
    }
}
