package des.c5inco.pokedexer.shared.model

import androidx.room.Entity
import androidx.room.PrimaryKey

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

enum class MoveCategory {
    Physical,
    Special,
    Status
}

data class PokemonMove(
    val id: Int,
    val targetLevel: Int
)
