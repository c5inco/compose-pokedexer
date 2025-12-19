package des.c5inco.pokedexer.shared.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AbilityEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String
)
