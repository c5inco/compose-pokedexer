package des.c5inco.pokedexer.data.moves

import des.c5inco.pokedexer.data.Result
import des.c5inco.pokedexer.model.Move

interface MovesRepository {
    suspend fun getAllMoves(): Result<List<Move>>
}