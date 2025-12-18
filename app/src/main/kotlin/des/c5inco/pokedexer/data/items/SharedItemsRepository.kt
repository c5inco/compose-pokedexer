package des.c5inco.pokedexer.data.items

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import des.c5inco.pokedexer.data.Result
import des.c5inco.pokedexer.data.toAndroid
import des.c5inco.pokedexer.data.toAndroidItemList
import des.c5inco.pokedexer.model.Item
import des.c5inco.pokedexer.shared.ItemsQuery
import des.c5inco.pokedexer.shared.data.toDomainModel
import des.c5inco.pokedexer.shared.data.db.ItemsDao as SharedItemsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Items repository implementation that uses the shared KMP database.
 * Returns Android models for compatibility with existing UI code.
 */
class SharedItemsRepository @Inject constructor(
    private val sharedItemsDao: SharedItemsDao,
    private val apolloClient: ApolloClient
) : ItemsRepository {

    override fun items(): Flow<List<Item>> {
        return sharedItemsDao.getAll().map { it.toAndroidItemList() }
    }

    override suspend fun updateItems() {
        val items = sharedItemsDao.getAll().first()

        if (items.isEmpty()) {
            withContext(Dispatchers.IO) {
                println("Loading items from network using shared module...")
                val response = apolloClient.query(ItemsQuery()).execute()

                if (!response.hasErrors()) {
                    val itemsFromServer = response.data!!.info.items.map { model ->
                        model.toDomainModel()
                    }

                    sharedItemsDao.deleteAll()
                    sharedItemsDao.insertAll(*itemsFromServer.toTypedArray())
                    println("Populated shared items database: ${itemsFromServer.size}")
                } else {
                    throw ApolloException("The response has errors: ${response.errors}")
                }
            }
        } else {
            println("Items loaded from shared database: ${items.size}")
        }
    }

    override fun getItemById(id: Int): Flow<Item?> {
        return sharedItemsDao.findById(id).map { it?.toAndroid() }
    }

    override suspend fun getItemByIds(ids: List<Int>): Result<List<Item>> {
        return Result.Success(sharedItemsDao.findByIds(ids).toAndroidItemList())
    }

    override fun getItemsByName(name: String): Flow<List<Item>> {
        return sharedItemsDao.findByName(name).map { it.toAndroidItemList() }
    }
}

