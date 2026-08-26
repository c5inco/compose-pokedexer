package des.c5inco.pokedexer.shared.data.pokemon

import des.c5inco.pokedexer.shared.data.Result
import des.c5inco.pokedexer.shared.model.Generation
import des.c5inco.pokedexer.shared.model.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart

class RemotePokemonRepository(
    private val pokemonDao: PokemonDao,
    private val generationLoader: GenerationLoader,
) : PokemonRepository {
    override fun pokemon(): Flow<List<Pokemon>> {
        return pokemonDao.getAllFlow()
    }

    override suspend fun updatePokemon() {
        generationLoader.load(Generation.I)
    }

    override fun getPokemonById(id: Int): Flow<Pokemon?> {
        return pokemonDao.findById(id)
    }

    override fun getPokemonByIds(ids: List<Int>): Flow<List<Pokemon>> {
        return pokemonDao.findByIds(ids)
    }

    override fun getPokemonByName(name: String): Flow<List<Pokemon>> {
        return pokemonDao.findByName(name)
    }

    override fun getPokemonByGeneration(generation: Generation): Flow<List<Pokemon>> {
        return pokemonDao
            .getAllByGeneration(generation.id)
            .onStart { generationLoader.load(generation) }
            .catch { error ->
                println("Failed to fetch Pokemon for generation ${generation.id}: ${error.message}")
                emit(emptyList())
            }
    }

    override suspend fun addPokemon(pokemon: Pokemon): Result<Pokemon> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllPokemon(): Result<Int> {
        TODO("Not yet implemented")
    }
}
