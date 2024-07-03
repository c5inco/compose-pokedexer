package des.c5inco.pokedexer.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import des.c5inco.pokedexer.R

@Entity
data class Move(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val category: String,
    val type: String,
    val pp: Int,
    val power: Int?,
    val accuracy: Int?
)

fun Move.category(): MoveCategory {
    return MoveCategory.valueOf(category)
}

fun Move.type(): Type {
    return Type.valueOf(type)
}

fun Move.categoryIcon(): Int {
    return when(category) {
        "Physical" -> R.drawable.ic_move_physical
        "Special" -> R.drawable.ic_move_special
        else -> R.drawable.ic_move_status
    }
}

enum class MoveCategory {
    Physical,
    Special,
    Status
}

data class PokemonMove(
    val id: Int,
    val targetLevel: Int
)
