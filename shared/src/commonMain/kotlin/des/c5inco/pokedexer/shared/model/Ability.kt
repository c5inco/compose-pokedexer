package des.c5inco.pokedexer.shared.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.native.ObjCName

@Entity
data class Ability(
    @PrimaryKey
    val id: Int,
    val name: String,
    @property:ObjCName("desc") val description: String
)

data class PokemonAbility(
    val id: Int,
    val isHidden: Boolean
)
