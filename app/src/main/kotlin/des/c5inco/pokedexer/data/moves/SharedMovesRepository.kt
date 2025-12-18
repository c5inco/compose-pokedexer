package des.c5inco.pokedexer.data.moves

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import des.c5inco.pokedexer.data.Result
import des.c5inco.pokedexer.data.toAndroid
import des.c5inco.pokedexer.data.toAndroidMoveList
import des.c5inco.pokedexer.model.Move
import des.c5inco.pokedexer.shared.PokemonOriginalMovesQuery
import des.c5inco.pokedexer.shared.data.toDomainModel
import des.c5inco.pokedexer.shared.data.db.MovesDao as SharedMovesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Moves repository implementation that uses the shared KMP database.
 * Returns Android models for compatibility with existing UI code.
 */
class SharedMovesRepository @Inject constructor(
    private val sharedMovesDao: SharedMovesDao,
    private val apolloClient: ApolloClient
) : MovesRepository {

    override fun moves(): Flow<List<Move>> {
        return sharedMovesDao.getAll().map { it.toAndroidMoveList() }
    }

    override suspend fun updateMoves() {
        val moves = sharedMovesDao.getAll().first()

        if (moves.isEmpty()) {
            withContext(Dispatchers.IO) {
                println("Loading moves from network using shared module...")
                val response = apolloClient.query(PokemonOriginalMovesQuery()).execute()

                if (!response.hasErrors()) {
                    val movesFromServer = response.data!!.moves.map { model ->
                        model.toDomainModel()
                    }

                    sharedMovesDao.deleteAll()
                    sharedMovesDao.insertAll(*movesFromServer.toTypedArray())
                    println("Populated shared moves database: ${movesFromServer.size}")
                } else {
                    throw ApolloException("The response has errors: ${response.errors}")
                }
            }
        } else {
            println("Moves loaded from shared database: ${moves.size}")
        }
    }

    override fun getMoveById(id: Int): Flow<Move?> {
        return sharedMovesDao.findById(id).map { it?.toAndroid() }
    }

    override suspend fun getMovesByIds(ids: List<Int>): Result<List<Move>> {
        return Result.Success(sharedMovesDao.findByIds(ids).toAndroidMoveList())
    }

    override fun getMovesByName(name: String): Flow<List<Move>> {
        return sharedMovesDao.findByName(name).map { it.toAndroidMoveList() }
    }
}

