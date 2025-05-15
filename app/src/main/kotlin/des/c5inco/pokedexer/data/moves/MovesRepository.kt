package des.c5inco.pokedexer.data.moves

import des.c5inco.pokedexer.data.Result
import des.c5inco.pokedexer.model.Move
import kotlinx.coroutines.flow.Flow

interface MovesRepository {
    fun moves(): Flow<List<Move>>
    suspend fun updateMoves()
    fun getMoveById(id: Int): Flow<Move?>
    suspend fun getMovesByIds(ids: List<Int>): Result<List<Move>>
    fun getMovesByName(name: String): Flow<List<Move>>
}