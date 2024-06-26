package des.c5inco.pokedexer.data.pokemon

import des.c5inco.pokedexer.data.Result
import des.c5inco.pokedexer.model.Pokemon

/**
 * Interface to the Pokemon data layer.
 */
interface PokemonRepository {
    /**
     * Get all pokemon
     */
    suspend fun getAllPokemon(): Result<List<Pokemon>>

    suspend fun getPokemonById(id: Int): Result<Pokemon>

    suspend fun getPokemonByIds(ids: List<Int>): Result<List<Pokemon>>

    suspend fun getPokemonByName(name: String): Result<List<Pokemon>>

    suspend fun addPokemon(pokemon: Pokemon): Result<Pokemon>

    suspend fun deleteAllPokemon(): Result<Int>
}