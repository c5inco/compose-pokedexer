package des.c5inco.pokedexer.model

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
    return when(category) {
        "physical" -> MoveCategory.Physical
        "special" -> MoveCategory.Special
        else -> MoveCategory.Status
    }
}

fun Move.type(): Type {
    return when(type) {
        else -> Type.Normal
    }
}

enum class MoveCategory {
    Physical,
    Special,
    Status
}
