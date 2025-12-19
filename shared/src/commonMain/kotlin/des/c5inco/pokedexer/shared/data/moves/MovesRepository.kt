package des.c5inco.pokedexer.shared.data.moves

import des.c5inco.pokedexer.shared.data.Result
import des.c5inco.pokedexer.shared.model.MoveEntity
import kotlinx.coroutines.flow.Flow

interface MovesRepository {
    fun moves(): Flow<List<MoveEntity>>
    suspend fun updateMoves()
    fun getMoveById(id: Int): Flow<MoveEntity?>
    suspend fun getMovesByIds(ids: List<Int>): Result<List<MoveEntity>>
    fun getMovesByName(name: String): Flow<List<MoveEntity>>
}
