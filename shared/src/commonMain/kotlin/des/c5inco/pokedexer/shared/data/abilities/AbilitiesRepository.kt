package des.c5inco.pokedexer.shared.data.abilities

import des.c5inco.pokedexer.shared.data.Result
import des.c5inco.pokedexer.shared.model.AbilityEntity
import kotlinx.coroutines.flow.Flow

interface AbilitiesRepository {
    suspend fun updateAbilities()
    fun getAbilityById(id: Int): Flow<AbilityEntity?>
    suspend fun getAbilitiesByIds(ids: List<Int>): Result<List<AbilityEntity>>
    suspend fun getAbilitiesByName(name: String): Result<List<AbilityEntity>>
}
