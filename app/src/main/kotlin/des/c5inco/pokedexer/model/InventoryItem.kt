package des.c5inco.pokedexer.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class InventoryItem(
    @PrimaryKey val id: Int,
    val name: String
)