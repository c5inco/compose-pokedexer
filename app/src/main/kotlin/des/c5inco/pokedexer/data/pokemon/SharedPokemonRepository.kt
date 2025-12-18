package des.c5inco.pokedexer.data.pokemon

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import des.c5inco.pokedexer.data.Result
import des.c5inco.pokedexer.data.toAndroid
import des.c5inco.pokedexer.data.toAndroidList
import des.c5inco.pokedexer.data.toShared
import des.c5inco.pokedexer.model.Generation
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.shared.PokemonOriginalQuery
import des.c5inco.pokedexer.shared.data.toDomainModel
import des.c5inco.pokedexer.shared.data.db.PokemonDao as SharedPokemonDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Pokemon repository implementation that uses the shared KMP database.
 * Returns Android models for compatibility with existing UI code.
 */
class SharedPokemonRepository @Inject constructor(
    private val sharedPokemonDao: SharedPokemonDao,
    private val apolloClient: ApolloClient
) : PokemonRepository {

    override fun pokemon(): Flow<List<Pokemon>> {
        return sharedPokemonDao.getAllFlow().map { it.toAndroidList() }
    }

    override suspend fun updatePokemon() {
        updatePokemonByGeneration(1)
    }

    private suspend fun updatePokemonByGeneration(generationId: Int) {
        val localPokemon = sharedPokemonDao.getAllByGeneration(generationId).first()

        if (localPokemon.isEmpty()) {
            withContext(Dispatchers.IO) {
                println("Loading Pokemon (gen $generationId) from network using shared module...")
                val response = apolloClient.query(PokemonOriginalQuery(generationId)).execute()

                if (!response.hasErrors()) {
                    val pokemonFromServer = response.data!!.pokemon.map { model ->
                        model.toDomainModel(generationId)
                    }

                    sharedPokemonDao.insertAll(*pokemonFromServer.toTypedArray())
                    println("Populated shared pokemon database: ${pokemonFromServer.size}")
                } else {
                    throw ApolloException("The response has errors: ${response.errors}")
                }
            }
        } else {
            println("Pokemon (Gen $generationId) loaded from shared database: ${localPokemon.size}")
        }
    }

    override fun getPokemonById(id: Int): Flow<Pokemon?> {
        return sharedPokemonDao.findById(id).map { it?.toAndroid() }
    }

    override fun getPokemonByIds(ids: List<Int>): Flow<List<Pokemon>> {
        return sharedPokemonDao.findByIds(ids).map { it.toAndroidList() }
    }

    override fun getPokemonByName(name: String): Flow<List<Pokemon>> {
        return sharedPokemonDao.findByName(name).map { it.toAndroidList() }
    }

    override fun getPokemonByGeneration(generation: Generation): Flow<List<Pokemon>> {
        return flow {
            val localPokemon = sharedPokemonDao.getAllByGeneration(generation.id).first()

            if (localPokemon.isEmpty()) {
                // Generation not in database, fetch from remote
                try {
                    updatePokemonByGeneration(generation.id)
                    // After fetching, emit the data from the database
                    val remotePokemon = sharedPokemonDao.getAllByGeneration(generation.id).first()
                    emit(remotePokemon.toAndroidList())
                } catch (e: Exception) {
                    println("Failed to fetch Pokemon for generation ${generation.id}: ${e.message}")
                    emit(emptyList())
                }
            } else {
                emit(localPokemon.toAndroidList())
            }
        }
    }

    override suspend fun addPokemon(pokemon: Pokemon): Result<Pokemon> {
        return try {
            sharedPokemonDao.insert(pokemon.toShared())
            Result.Success(pokemon)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun deleteAllPokemon(): Result<Int> {
        return try {
            val count = sharedPokemonDao.getAll().size
            sharedPokemonDao.deleteAll()
            Result.Success(count)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}

