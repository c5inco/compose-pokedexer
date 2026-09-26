@file:OptIn(com.apollographql.apollo.annotations.ApolloExperimental::class)

package des.c5inco.pokedexer.shared.data.abilities

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.exception.ApolloNetworkException
import com.apollographql.apollo.testing.QueueTestNetworkTransport
import com.apollographql.apollo.testing.enqueueTestNetworkError
import des.c5inco.pokedexer.shared.model.Ability
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

class AbilitiesRepositoryTest {
    @Test
    fun networkFailureThrowsNetworkException() = runBlocking {
        val abilitiesDao = FakeAbilitiesDao()
        val client = ApolloClient.Builder().networkTransport(QueueTestNetworkTransport()).build()
        client.enqueueTestNetworkError()

        try {
            assertFailsWith<ApolloNetworkException> {
                AbilitiesRepositoryImpl(abilitiesDao, client).updateAbilities()
            }

            assertEquals(emptyList(), abilitiesDao.currentAbilities)
        } finally {
            client.close()
        }
    }
}

private class FakeAbilitiesDao : AbilitiesDao {
    private val abilities = MutableStateFlow(emptyList<Ability>())

    val currentAbilities: List<Ability>
        get() = abilities.value

    override fun getAll(): Flow<List<Ability>> = abilities

    override fun findById(id: Int): Flow<Ability?> = flowOf(abilities.value.find { it.id == id })

    override suspend fun findByIds(ids: List<Int>): List<Ability> =
        abilities.value.filter { it.id in ids }

    override suspend fun findByName(name: String): List<Ability> =
        abilities.value.filter { it.name.contains(name, ignoreCase = true) }

    override suspend fun insert(item: Ability) {
        insertAll(item)
    }

    override suspend fun insertAll(vararg item: Ability) {
        abilities.value = abilities.value + item
    }

    override suspend fun deleteAll() {
        abilities.value = emptyList()
    }
}
