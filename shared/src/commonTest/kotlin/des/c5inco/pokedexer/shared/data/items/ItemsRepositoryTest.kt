@file:OptIn(com.apollographql.apollo3.annotations.ApolloExperimental::class)

package des.c5inco.pokedexer.shared.data.items

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.testing.QueueTestNetworkTransport
import com.apollographql.apollo3.testing.enqueueTestNetworkError
import com.apollographql.apollo3.testing.enqueueTestResponse
import com.benasher44.uuid.uuid4
import des.c5inco.pokedexer.shared.ItemsQuery
import des.c5inco.pokedexer.shared.model.Item
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking

class ItemsRepositoryTest {
    @Test
    fun countMismatchReplacesLocalItemsAtomically() = runBlocking {
        val itemsDao = FakeItemsDao(listOf(databaseItem(1)))
        val client = testClient()
        client.enqueueTestResponse(ItemsQuery(), itemsData(item(1), item(2)))

        try {
            ItemsRepositoryImpl(itemsDao, client).updateItems()

            assertEquals(1, itemsDao.replaceAllCalls)
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

            assertEquals(0, itemsDao.replaceAllCalls)
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

    @Test
    fun reportedCountMismatchKeepsCachedItems() = runBlocking {
        val itemsDao = FakeItemsDao(listOf(databaseItem(99)))
        val client = testClient()
        client.enqueueTestResponse(ItemsQuery(), itemsData(item(1), total = 2))

        try {
            assertFailsWith<ApolloException> { ItemsRepositoryImpl(itemsDao, client).updateItems() }

            assertEquals(listOf(99), itemsDao.currentItems.map(Item::id))
            assertEquals(0, itemsDao.replaceAllCalls)
        } finally {
            client.close()
        }
    }

    @Test
    fun networkFailureKeepsCachedItems() = runBlocking {
        val itemsDao = FakeItemsDao(listOf(databaseItem(99)))
        val client = testClient()
        client.enqueueTestNetworkError()

        try {
            assertFailsWith<ApolloException> { ItemsRepositoryImpl(itemsDao, client).updateItems() }

            assertEquals(listOf(99), itemsDao.currentItems.map(Item::id))
            assertEquals(0, itemsDao.replaceAllCalls)
        } finally {
            client.close()
        }
    }

    @Test
    fun nullDataThrowsApolloException() = runBlocking {
        val itemsDao = FakeItemsDao(listOf(databaseItem(99)))
        val client = testClient()
        client.enqueueTestResponse(ApolloResponse.Builder(ItemsQuery(), uuid4(), null).build())

        try {
            assertFailsWith<ApolloException> { ItemsRepositoryImpl(itemsDao, client).updateItems() }

            assertEquals(listOf(99), itemsDao.currentItems.map(Item::id))
            assertEquals(0, itemsDao.replaceAllCalls)
        } finally {
            client.close()
        }
    }
}

private fun testClient(): ApolloClient =
    ApolloClient.Builder().networkTransport(QueueTestNetworkTransport()).build()

private fun itemsData(vararg items: ItemsQuery.Item, total: Int = items.size): ItemsQuery.Data =
    ItemsQuery.Data(info = ItemsQuery.Info(total = ItemsQuery.Total(total), items = items.toList()))

private fun item(
    id: Int,
    flavorText: List<ItemsQuery.FlavorText> = listOf(ItemsQuery.FlavorText("An item description.")),
): ItemsQuery.Item = ItemsQuery.Item(id = id, name = "item-$id", flavorText = flavorText)

private fun databaseItem(id: Int) =
    Item(id = id, name = "Item $id", description = "An item description.", sprite = "item-$id")

private class FakeItemsDao(initialItems: List<Item> = emptyList()) : ItemsDao {
    private val items = MutableStateFlow(initialItems)

    val currentItems: List<Item>
        get() = items.value

    var replaceAllCalls = 0
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

    override suspend fun replaceAll(items: List<Item>) {
        replaceAllCalls++
        insertedItems = items
        this.items.value = items
    }

    override suspend fun deleteAll() {
        items.value = emptyList()
    }
}
