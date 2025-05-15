package des.c5inco.pokedexer.data.pokemon

import des.c5inco.pokedexer.data.Result
import des.c5inco.pokedexer.model.Pokemon
import kotlinx.coroutines.flow.Flow

/**
 * Interface to the Pokemon data layer.
 */
interface PokemonRepository {
    fun pokemon(): Flow<List<Pokemon>>

    suspend fun updatePokemon()

    fun getPokemonById(id: Int): Flow<Pokemon?>

    fun getPokemonByIds(ids: List<Int>): Flow<List<Pokemon>>

    fun getPokemonByName(name: String): Flow<List<Pokemon>>

    suspend fun addPokemon(pokemon: Pokemon): Result<Pokemon>

    suspend fun deleteAllPokemon(): Result<Int>
}