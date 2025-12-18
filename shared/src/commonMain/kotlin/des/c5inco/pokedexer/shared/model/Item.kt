package des.c5inco.pokedexer.shared.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Domain model and Room entity representing an item in the Pokemon world.
 */
@Entity
data class Item(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val sprite: String
)

