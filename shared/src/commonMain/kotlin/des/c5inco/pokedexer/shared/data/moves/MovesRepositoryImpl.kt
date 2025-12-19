package des.c5inco.pokedexer.shared.data.moves

import com.apollographql.apollo3.ApolloClient
import des.c5inco.pokedexer.shared.PokemonOriginalMovesQuery
import des.c5inco.pokedexer.shared.data.Result
import des.c5inco.pokedexer.shared.data.cleanupDescriptionText
import des.c5inco.pokedexer.shared.model.MoveEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class MovesRepositoryImpl(
    private val movesDao: MovesDao,
    private val apolloClient: ApolloClient
) : MovesRepository {
    override fun moves(): Flow<List<MoveEntity>> {
        return movesDao.getAll()
    }

    override suspend fun updateMoves() {
        val moves = movesDao.getAll().first()

        if (moves.isEmpty()) {
            println("Loading moves from network...")
            val response = apolloClient.query(PokemonOriginalMovesQuery()).execute()

            if (!response.hasErrors()) {
                val movesFromServer = response.data!!.moves.map { model ->
                    MoveEntity(
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
                throw Exception("The response has errors: ${response.errors}")
            }
        } else {
            println("Moves loaded from database: ${moves.size}")
        }
    }

    override fun getMoveById(id: Int): Flow<MoveEntity?> {
        return movesDao.findById(id)
    }

    override suspend fun getMovesByIds(ids: List<Int>): Result<List<MoveEntity>> {
        return Result.Success(movesDao.findByIds(ids))
    }

    override fun getMovesByName(name: String): Flow<List<MoveEntity>> {
        return movesDao.findByName(name)
    }
}
