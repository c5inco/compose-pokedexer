package des.c5inco.pokedexer.data.items

import des.c5inco.pokedexer.data.Result
import des.c5inco.pokedexer.model.Item
import kotlinx.coroutines.flow.Flow

interface ItemsRepository {
    fun items(): Flow<List<Item>>
    suspend fun updateItems()
    fun getItemById(id: Int): Flow<Item?>
    suspend fun getItemByIds(ids: List<Int>): Result<List<Item>>
    fun getItemsByName(name: String): Flow<List<Item>>
}
