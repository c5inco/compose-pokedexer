package des.c5inco.pokedexer.data.abilities

import des.c5inco.pokedexer.data.Result
import des.c5inco.pokedexer.model.Ability
import kotlinx.coroutines.flow.Flow

interface AbilitiesRepository {
    suspend fun updateAbilities()
    fun getAbilityById(id: Int): Flow<Ability?>
    suspend fun getAbilitiesByIds(ids: List<Int>): Result<List<Ability>>
    suspend fun getAbilitiesByName(name: String): Result<List<Ability>>
}
