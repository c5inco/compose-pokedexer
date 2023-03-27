package des.c5inco.pokedexer.data.moves

import des.c5inco.pokedexer.data.Result
import des.c5inco.pokedexer.model.Move

interface MovesRepository {
    suspend fun getAllMoves(): Result<List<Move>>
    suspend fun getMoveById(id: Int): Result<Move>
    suspend fun getMovesByIds(ids: List<Int>): Result<List<Move>>
}