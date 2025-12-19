package des.c5inco.pokedexer.shared.data.items

import des.c5inco.pokedexer.shared.data.Result
import des.c5inco.pokedexer.shared.model.ItemEntity
import kotlinx.coroutines.flow.Flow

interface ItemsRepository {
    fun items(): Flow<List<ItemEntity>>
    suspend fun updateItems()
    fun getItemById(id: Int): Flow<ItemEntity?>
    suspend fun getItemByIds(ids: List<Int>): Result<List<ItemEntity>>
    fun getItemsByName(name: String): Flow<List<ItemEntity>>
}
