package des.c5inco.pokedexer.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ability(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String
)

data class PokemonAbility(
    val id: Int,
    val isHidden: Boolean
)