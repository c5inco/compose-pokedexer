package des.c5inco.pokedexer.shared.data.moves

import des.c5inco.pokedexer.shared.data.Result
import des.c5inco.pokedexer.shared.model.Move
import kotlinx.coroutines.flow.Flow

interface MovesRepository {
    fun moves(): Flow<List<Move>>
    suspend fun updateMoves()
    fun getMoveById(id: Int): Flow<Move?>
    suspend fun getMovesByIds(ids: List<Int>): Result<List<Move>>
    fun getMovesByName(name: String): Flow<List<Move>>
}
