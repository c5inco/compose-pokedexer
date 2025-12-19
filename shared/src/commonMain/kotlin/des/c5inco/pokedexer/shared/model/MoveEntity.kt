package des.c5inco.pokedexer.shared.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MoveEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val category: String,
    val type: String,
    val pp: Int,
    val power: Int?,
    val accuracy: Int?
) {
    fun category(): MoveCategory {
        return MoveCategory.valueOf(category)
    }

    fun type(): Type {
        return Type.valueOf(type)
    }
}
