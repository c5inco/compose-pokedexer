package des.c5inco.pokedexer.data.items

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import des.c5inco.pokedexer.ItemsQuery
import des.c5inco.pokedexer.data.Result
import des.c5inco.pokedexer.data.cleanupDescriptionText
import des.c5inco.pokedexer.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface ItemsRepository {
    fun items(): Flow<List<Item>>
    suspend fun updateItems()
    fun getItemById(id: Int): Flow<Item?>
    suspend fun getItemByIds(ids: List<Int>): Result<List<Item>>
    fun getItemsByName(name: String): Flow<List<Item>>
}

class ItemsRepositoryImpl @Inject constructor(
    private val itemsDao: ItemsDao,
    private val apolloClient: ApolloClient
): ItemsRepository {
    override fun items(): Flow<List<Item>> {
        return itemsDao.getAll()
    }

    override suspend fun updateItems() {
        val items = itemsDao.getAll().first()

        if (items.isEmpty()) {
            withContext(Dispatchers.IO) {
                println("Loading items from network...")
                val response = apolloClient.query(ItemsQuery()).execute()

                if (!response.hasErrors()) {
                    val itemsFromServer = response.data!!.info.items.map { model ->
                        Item(
                            id = model.id,
                            name = model.name.split("-").joinToString(" ") { part ->
                                part.replaceFirstChar { it.uppercase() }
                            },
                            description = cleanupDescriptionText(model.flavorText.first().text),
                            sprite = model.name,
                        )
                    }

                    itemsDao.deleteAll()
                    itemsDao.insertAll(*itemsFromServer.toTypedArray())
                    println("Populated items database: ${itemsFromServer.size}")
                } else {
                    throw ApolloException("The response has errors: ${response.errors}")
                }
            }
        } else {
            println("Items loaded from database: ${items.size}")
        }
    }

    override fun getItemById(id: Int): Flow<Item?> {
        return itemsDao.findById(id)
    }

    override suspend fun getItemByIds(ids: List<Int>): Result<List<Item>> {
        TODO("Not yet implemented")
    }

    override fun getItemsByName(name: String): Flow<List<Item>> {
        return itemsDao.findByName(name)
    }
}