package des.c5inco.pokedexer.data.moves

import des.c5inco.pokedexer.data.Result
import des.c5inco.pokedexer.model.Move
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class LocalMovesRepository(
    private val localMovesDataSource: LocalMovesDataSource = LocalMovesDataSource()
) : MovesRepository {
    override suspend fun getAllMoves(): Result<List<Move>> {
        return withContext(Dispatchers.IO) {
            delay(800) // pretend we're on a slow network
            if (shouldRandomlyFail()) {
                Result.Error(IllegalStateException())
            } else {
                Result.Success(localMovesDataSource.fetchMoves())
            }
        }
    }

    override suspend fun getMoveById(id: Int): Result<Move> {
        return Result.Success(localMovesDataSource.fetchMoves().first())
    }

    override suspend fun getMovesByIds(ids: List<Int>): Result<List<Move>> {
        return Result.Success(localMovesDataSource.fetchMoves().take(3))
    }

    override suspend fun getMovesByName(name: String): Result<List<Move>> {
        TODO("Not yet implemented")
    }

    // used to drive "random" failure in a predictable pattern, making the first request always
    // succeed
    private var requestCount = 0

    /**
     * Randomly fail some loads to simulate a real network.
     *
     * This will fail deterministically every 5 requests
     */
    private fun shouldRandomlyFail(): Boolean = ++requestCount % 5 == 0
}

class LocalMovesDataSource {
    fun fetchMoves() : List<Move> {
        return SampleMoves
    }
}