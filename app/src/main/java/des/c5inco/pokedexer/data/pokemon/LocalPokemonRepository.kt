package des.c5inco.pokedexer.data.pokemon

import des.c5inco.pokedexer.data.Result
import des.c5inco.pokedexer.model.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class LocalPokemonRepository(
    private val localPokemonDataSource: LocalPokemonDataSource = LocalPokemonDataSource()
) : PokemonRepository {
    override suspend fun getAllPokemon(): Result<List<Pokemon>> {
        return withContext(Dispatchers.IO) {
            delay(800) // pretend we're on a slow network
            if (shouldRandomlyFail()) {
                Result.Error(IllegalStateException())
            } else {
                Result.Success(localPokemonDataSource.fetchPokemon())
            }
        }
    }

    override suspend fun getPokemonById(id: Int): Result<Pokemon> {
        return Result.Success(localPokemonDataSource.getPokemonById(id))
    }

    // used to drive "random" failure in a predictable pattern, making the first request always
    // succeed
    private var requestCount = 0

    /**
     * Randomly fail some loads to simulate a real network.
     *
     * This will fail deterministically every 5 requests
     */
    private fun shouldRandomlyFail(): Boolean = ++requestCount % 5 == 0

    override suspend fun addPokemon(pokemon: Pokemon): Result<Pokemon> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllPokemon(): Result<Int> {
        TODO("Not yet implemented")
    }
}

class LocalPokemonDataSource {
    fun fetchPokemon() : List<Pokemon> {
        return SamplePokemonData
    }

    fun getPokemonById(id: Int): Pokemon {
        return SamplePokemonData[id - 1]
    }
}