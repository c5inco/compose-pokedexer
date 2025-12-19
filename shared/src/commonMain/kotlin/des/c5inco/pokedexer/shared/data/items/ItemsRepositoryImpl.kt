package des.c5inco.pokedexer.shared.data.items

import com.apollographql.apollo3.ApolloClient
import des.c5inco.pokedexer.shared.ItemsQuery
import des.c5inco.pokedexer.shared.data.Result
import des.c5inco.pokedexer.shared.data.cleanupDescriptionText
import des.c5inco.pokedexer.shared.model.ItemEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class ItemsRepositoryImpl(
    private val itemsDao: ItemsDao,
    private val apolloClient: ApolloClient
) : ItemsRepository {
    override fun items(): Flow<List<ItemEntity>> {
        return itemsDao.getAll()
    }

    override suspend fun updateItems() {
        val items = itemsDao.getAll().first()

        if (items.isEmpty()) {
            println("Loading items from network...")
            val response = apolloClient.query(ItemsQuery()).execute()

            if (!response.hasErrors()) {
                val itemsFromServer = response.data!!.info.items.map { model ->
                    ItemEntity(
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
                throw Exception("The response has errors: ${response.errors}")
            }
        } else {
            println("Items loaded from database: ${items.size}")
        }
    }

    override fun getItemById(id: Int): Flow<ItemEntity?> {
        return itemsDao.findById(id)
    }

    override suspend fun getItemByIds(ids: List<Int>): Result<List<ItemEntity>> {
        return Result.Success(itemsDao.findByIds(ids))
    }

    override fun getItemsByName(name: String): Flow<List<ItemEntity>> {
        return itemsDao.findByName(name)
    }
}
