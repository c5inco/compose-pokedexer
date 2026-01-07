package des.c5inco.pokedexer.shared.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.native.ObjCName

@Entity
data class Move(
    @PrimaryKey val id: Int,
    val name: String,
    @property:ObjCName("desc") val description: String,
    val category: String,
    val type: String,
    val pp: Int,
    val power: Int?,
    val accuracy: Int?
)

@ObjCName("moveCategory")
fun Move.category(): MoveCategory {
    return MoveCategory.valueOf(category)
}

@ObjCName("moveType")
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
