package des.c5inco.pokedexer.data.items

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.exception.DefaultApolloException
import des.c5inco.pokedexer.ItemsQuery
import des.c5inco.pokedexer.data.Result
import des.c5inco.pokedexer.data.cleanupDescriptionText
import des.c5inco.pokedexer.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface ItemsRepository {
    suspend fun getAllItems(): Result<List<Item>>
    suspend fun getItemById(id: Int): Result<Item>
    suspend fun getItemByIds(ids: List<Int>): Result<List<Item>>
    suspend fun getItemsByName(name: String): Result<List<Item>>
}

class ItemsRepositoryImpl @Inject constructor(
    private val itemsDao: ItemsDao,
    private val apolloClient: ApolloClient
): ItemsRepository {
    override suspend fun getAllItems(): Result<List<Item>> {
        val localItems = itemsDao.getAll()

        if (localItems.isNotEmpty()) {
            delay(300)
            println("items from cache")
            return Result.Success(localItems)
        } else {
            return withContext(Dispatchers.IO) {
                println("items from network")
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
                    Result.Success(itemsFromServer)
                } else {
                    Result.Error(
                        DefaultApolloException("The response has errors: ${response.errors}")
                    )
                }
            }
        }
    }

    override suspend fun getItemById(id: Int): Result<Item> {
        itemsDao.findById(id)?.let {
            return Result.Success(it)
        }
        return Result.Error(
            Exception("Item with ID: $id not found in local DB!")
        )
    }

    override suspend fun getItemByIds(ids: List<Int>): Result<List<Item>> {
        TODO("Not yet implemented")
    }

    override suspend fun getItemsByName(name: String): Result<List<Item>> {
        return Result.Success(itemsDao.findByName(name))
    }
}