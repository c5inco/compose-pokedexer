package des.c5inco.pokedexer.shared.data.moves

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import des.c5inco.pokedexer.shared.PokemonOriginalMovesQuery
import des.c5inco.pokedexer.shared.data.Result
import des.c5inco.pokedexer.shared.data.cleanupDescriptionText
import des.c5inco.pokedexer.shared.model.Move
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class RemoteMovesRepository(
    private val movesDao: MovesDao,
    private val apolloClient: ApolloClient,
) : MovesRepository {
    override fun moves(): Flow<List<Move>> {
        return movesDao.getAll()
    }

    override suspend fun updateMoves() =
        withContext(Dispatchers.IO) {
            println("Loading moves from network...")
            val response = apolloClient.query(PokemonOriginalMovesQuery()).execute()

            if (response.hasErrors()) {
                throw ApolloException("The response has errors: ${response.errors}")
            }

            val data = response.data ?: throw ApolloException("The response contains no move data")
            val remoteCount = data.info.total?.count ?: data.moves.size
            val movesFromServer =
                data.moves.mapNotNull { model ->
                    val category = model.category ?: return@mapNotNull null
                    val type = model.type ?: return@mapNotNull null
                    val pp = model.pp ?: return@mapNotNull null
                    Move(
                        id = model.id,
                        name =
                            model.name.split("-").joinToString(" ") { part ->
                                part.replaceFirstChar { it.uppercase() }
                            },
                        description =
                            cleanupDescriptionText(
                                model.description.firstOrNull()?.flavorText.orEmpty()
                            ),
                        category = category.name.replaceFirstChar { it.uppercase() },
                        type = type.name.replaceFirstChar { it.uppercase() },
                        pp = pp,
                        power = model.power,
                        accuracy = model.accuracy,
                    )
                }
            if (movesFromServer.size != remoteCount) {
                throw ApolloException(
                    "Expected $remoteCount complete moves but mapped ${movesFromServer.size}"
                )
            }

            val localCount = movesDao.count()
            if (localCount == remoteCount) {
                println("Moves loaded from database: $localCount")
                return@withContext
            }

            movesDao.replaceAll(movesFromServer)
            println("Populated moves database: ${movesFromServer.size}")
        }

    override fun getMoveById(id: Int): Flow<Move?> {
        return movesDao.findById(id)
    }

    override suspend fun getMovesByIds(ids: List<Int>): Result<List<Move>> {
        return Result.Success(movesDao.findByIds(ids))
    }

    override fun getMovesByName(name: String): Flow<List<Move>> {
        return movesDao.findByName(name)
    }
}
