package des.c5inco.pokedexer.shared.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Domain model and Room entity representing a Pokemon ability.
 */
@Entity
data class Ability(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String
)

data class PokemonAbility(
    val id: Int,
    val isHidden: Boolean
)

