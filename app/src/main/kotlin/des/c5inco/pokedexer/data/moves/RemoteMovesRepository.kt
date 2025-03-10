package des.c5inco.pokedexer.data.moves

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import des.c5inco.pokedexer.PokemonOriginalMovesQuery
import des.c5inco.pokedexer.data.Result
import des.c5inco.pokedexer.data.cleanupDescriptionText
import des.c5inco.pokedexer.model.Move
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteMovesRepository @Inject constructor(
    private val movesDao: MovesDao,
    private val apolloClient: ApolloClient
): MovesRepository {
    override fun moves(): Flow<List<Move>> {
        return movesDao.getAll()
    }

    override suspend fun updateMoves() {
        val moves = movesDao.getAll().first()

        if (moves.isEmpty()) {
            withContext(Dispatchers.IO) {
                println("Loading moves from network...")
                val response = apolloClient.query(PokemonOriginalMovesQuery()).execute()

                if (!response.hasErrors()) {
                    val movesFromServer = response.data!!.moves.map { model ->
                        Move(
                            id = model.id,
                            name = model.name.split("-").joinToString(" ") { part ->
                                part.replaceFirstChar { it.uppercase() }
                            },
                            description = cleanupDescriptionText(model.description.first().flavorText),
                            category = model.category!!.name.replaceFirstChar { it.uppercase() },
                            type = model.type!!.name.replaceFirstChar { it.uppercase() },
                            pp = model.pp!!,
                            power = model.power,
                            accuracy = model.accuracy
                        )
                    }

                    movesDao.deleteAll()
                    movesDao.insertAll(*movesFromServer.toTypedArray())
                    println("Populated moves database: ${movesFromServer.size}")
                } else {
                    throw ApolloException("The response has errors: ${response.errors}")
                }
            }
        } else {
            delay(3000)
            println("Moves loaded from database: ${moves.size}")
        }
    }

    override suspend fun getMoveById(id: Int): Result<Move> {
        movesDao.findById(id)?.let {
            return Result.Success(it)
        }
        return Result.Error(
            Exception("Move with ID: $id not found in local DB!")
        )
    }
    override suspend fun getMovesByIds(ids: List<Int>): Result<List<Move>> {
        return Result.Success(movesDao.findByIds(ids))
    }

    override suspend fun getMovesByName(name: String): Result<List<Move>> {
        return Result.Success(movesDao.findByName(name))
    }
}