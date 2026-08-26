@file:OptIn(com.apollographql.apollo3.annotations.ApolloExperimental::class)

package des.c5inco.pokedexer.shared.data.moves

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.testing.QueueTestNetworkTransport
import com.apollographql.apollo3.testing.enqueueTestNetworkError
import com.apollographql.apollo3.testing.enqueueTestResponse
import des.c5inco.pokedexer.shared.PokemonOriginalMovesQuery
import des.c5inco.pokedexer.shared.model.Move
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking

class RemoteMovesRepositoryTest {
    @Test
    fun countMismatchReplacesLocalMovesAtomically() = runBlocking {
        val movesDao = FakeMovesDao(listOf(databaseMove(1)))
        val client = testClient()
        client.enqueueTestResponse(
            PokemonOriginalMovesQuery(),
            movesData(move(id = 1), move(id = 2)),
        )

        try {
            RemoteMovesRepository(movesDao, client).updateMoves()

            assertEquals(1, movesDao.replaceAllCalls)
            assertEquals(listOf(1, 2), movesDao.insertedMoves.map(Move::id))
        } finally {
            client.close()
        }
    }

    @Test
    fun equalCountKeepsLocalMoves() = runBlocking {
        val movesDao = FakeMovesDao(listOf(databaseMove(1), databaseMove(2)))
        val client = testClient()
        client.enqueueTestResponse(
            PokemonOriginalMovesQuery(),
            movesData(move(id = 1), move(id = 2)),
        )

        try {
            RemoteMovesRepository(movesDao, client).updateMoves()

            assertEquals(0, movesDao.replaceAllCalls)
            assertEquals(emptyList(), movesDao.insertedMoves)
        } finally {
            client.close()
        }
    }

    @Test
    fun incompleteMoveDoesNotPreventValidMovesFromLoading() = runBlocking {
        val movesDao = FakeMovesDao()
        val client = testClient()
        client.enqueueTestResponse(
            PokemonOriginalMovesQuery(),
            movesData(
                move(id = 1, descriptions = emptyList()),
                move(id = 2, category = null),
                move(id = 3, pp = null),
                move(id = 4, type = null),
                total = 1,
            ),
        )

        try {
            RemoteMovesRepository(movesDao, client).updateMoves()

            assertEquals(1, movesDao.insertedMoves.size)
            assertEquals("", movesDao.insertedMoves.single().description)
        } finally {
            client.close()
        }
    }

    @Test
    fun mappedCountMismatchKeepsCachedMoves() = runBlocking {
        val movesDao = FakeMovesDao(listOf(databaseMove(99)))
        val client = testClient()
        client.enqueueTestResponse(
            PokemonOriginalMovesQuery(),
            movesData(move(id = 1), move(id = 2, pp = null), total = 2),
        )

        try {
            assertFailsWith<ApolloException> {
                RemoteMovesRepository(movesDao, client).updateMoves()
            }

            assertEquals(listOf(99), movesDao.currentMoves.map(Move::id))
            assertEquals(0, movesDao.replaceAllCalls)
        } finally {
            client.close()
        }
    }

    @Test
    fun networkFailureKeepsCachedMoves() = runBlocking {
        val movesDao = FakeMovesDao(listOf(databaseMove(99)))
        val client = testClient()
        client.enqueueTestNetworkError()

        try {
            assertFailsWith<ApolloException> {
                RemoteMovesRepository(movesDao, client).updateMoves()
            }

            assertEquals(listOf(99), movesDao.currentMoves.map(Move::id))
            assertEquals(0, movesDao.replaceAllCalls)
        } finally {
            client.close()
        }
    }
}

private fun testClient(): ApolloClient =
    ApolloClient.Builder().networkTransport(QueueTestNetworkTransport()).build()

private fun movesData(
    vararg moves: PokemonOriginalMovesQuery.Move,
    total: Int = moves.size,
): PokemonOriginalMovesQuery.Data =
    PokemonOriginalMovesQuery.Data(
        info = PokemonOriginalMovesQuery.Info(total = PokemonOriginalMovesQuery.Total(total)),
        moves = moves.toList(),
    )

private fun move(
    id: Int,
    descriptions: List<PokemonOriginalMovesQuery.Description> =
        listOf(PokemonOriginalMovesQuery.Description("A move description.")),
    category: PokemonOriginalMovesQuery.Category? = PokemonOriginalMovesQuery.Category("physical"),
    pp: Int? = 10,
    type: PokemonOriginalMovesQuery.Type? = PokemonOriginalMovesQuery.Type("normal"),
): PokemonOriginalMovesQuery.Move =
    PokemonOriginalMovesQuery.Move(
        id = id,
        name = "move-$id",
        accuracy = 100,
        category = category,
        description = descriptions,
        pp = pp,
        power = 40,
        type = type,
    )

private fun databaseMove(id: Int) =
    Move(
        id = id,
        name = "Move $id",
        description = "A move description.",
        category = "Physical",
        type = "Normal",
        pp = 10,
        power = 40,
        accuracy = 100,
    )

private class FakeMovesDao(initialMoves: List<Move> = emptyList()) : MovesDao {
    private val moves = MutableStateFlow(initialMoves)

    val currentMoves: List<Move>
        get() = moves.value

    var replaceAllCalls = 0
        private set

    var insertedMoves = emptyList<Move>()
        private set

    override fun getAll(): Flow<List<Move>> = moves

    override suspend fun count(): Int = moves.value.size

    override fun findById(id: Int): Flow<Move?> =
        MutableStateFlow(moves.value.firstOrNull { it.id == id })

    override suspend fun findByIds(ids: List<Int>): List<Move> = moves.value.filter { it.id in ids }

    override fun findByName(name: String): Flow<List<Move>> =
        MutableStateFlow(moves.value.filter { name in it.name })

    override suspend fun insert(move: Move) {
        moves.value += move
    }

    override suspend fun insertAll(vararg move: Move) {
        insertedMoves = move.toList()
        moves.value = move.toList()
    }

    override suspend fun replaceAll(moves: List<Move>) {
        replaceAllCalls++
        insertedMoves = moves
        this.moves.value = moves
    }

    override suspend fun delete(move: Move) {
        moves.value -= move
    }

    override suspend fun deleteAll() {
        moves.value = emptyList()
    }
}
