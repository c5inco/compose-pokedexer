@file:OptIn(com.apollographql.apollo3.annotations.ApolloExperimental::class)

package des.c5inco.pokedexer.shared.data.items

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.testing.QueueTestNetworkTransport
import com.apollographql.apollo3.testing.enqueueTestResponse
import des.c5inco.pokedexer.shared.ItemsQuery
import des.c5inco.pokedexer.shared.model.Item
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking

class ItemsRepositoryTest {
    @Test
    fun countMismatchReplacesLocalItems() = runBlocking {
        val itemsDao = FakeItemsDao(listOf(databaseItem(1)))
        val client = testClient()
        client.enqueueTestResponse(ItemsQuery(), itemsData(item(1), item(2)))

        try {
            ItemsRepositoryImpl(itemsDao, client).updateItems()

            assertEquals(1, itemsDao.deleteAllCalls)
            assertEquals(listOf(1, 2), itemsDao.insertedItems.map(Item::id))
        } finally {
            client.close()
        }
    }

    @Test
    fun equalCountKeepsLocalItems() = runBlocking {
        val itemsDao = FakeItemsDao(listOf(databaseItem(1), databaseItem(2)))
        val client = testClient()
        client.enqueueTestResponse(ItemsQuery(), itemsData(item(1), item(2)))

        try {
            ItemsRepositoryImpl(itemsDao, client).updateItems()

            assertEquals(0, itemsDao.deleteAllCalls)
            assertEquals(emptyList(), itemsDao.insertedItems)
        } finally {
            client.close()
        }
    }

    @Test
    fun missingFlavorTextUsesEmptyDescription() = runBlocking {
        val itemsDao = FakeItemsDao()
        val client = testClient()
        client.enqueueTestResponse(ItemsQuery(), itemsData(item(1, flavorText = emptyList())))

        try {
            ItemsRepositoryImpl(itemsDao, client).updateItems()

            assertEquals("", itemsDao.insertedItems.single().description)
        } finally {
            client.close()
        }
    }
}

private fun testClient(): ApolloClient =
    ApolloClient.Builder().networkTransport(QueueTestNetworkTransport()).build()

private fun itemsData(vararg items: ItemsQuery.Item): ItemsQuery.Data =
    ItemsQuery.Data(
        info = ItemsQuery.Info(total = ItemsQuery.Total(items.size), items = items.toList())
    )

private fun item(
    id: Int,
    flavorText: List<ItemsQuery.FlavorText> = listOf(ItemsQuery.FlavorText("An item description.")),
): ItemsQuery.Item = ItemsQuery.Item(id = id, name = "item-$id", flavorText = flavorText)

private fun databaseItem(id: Int) =
    Item(id = id, name = "Item $id", description = "An item description.", sprite = "item-$id")

private class FakeItemsDao(initialItems: List<Item> = emptyList()) : ItemsDao {
    private val items = MutableStateFlow(initialItems)

    var deleteAllCalls = 0
        private set

    var insertedItems = emptyList<Item>()
        private set

    override fun getAll(): Flow<List<Item>> = items

    override suspend fun count(): Int = items.value.size

    override fun findById(id: Int): Flow<Item?> =
        MutableStateFlow(items.value.firstOrNull { it.id == id })

    override suspend fun findByIds(ids: List<Int>): List<Item> = items.value.filter { it.id in ids }

    override fun findByName(name: String): Flow<List<Item>> =
        MutableStateFlow(items.value.filter { name in it.name })

    override suspend fun insert(item: Item) {
        items.value += item
    }

    override suspend fun insertAll(vararg item: Item) {
        insertedItems = item.toList()
        items.value = item.toList()
    }

    override suspend fun deleteAll() {
        deleteAllCalls++
        items.value = emptyList()
    }
}
