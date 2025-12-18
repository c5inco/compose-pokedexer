package des.c5inco.pokedexer.shared.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Domain model and Room entity representing a Pokemon move.
 */
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

enum class MoveCategory {
    Physical,
    Special,
    Status
}

data class PokemonMove(
    val id: Int,
    val targetLevel: Int
)

