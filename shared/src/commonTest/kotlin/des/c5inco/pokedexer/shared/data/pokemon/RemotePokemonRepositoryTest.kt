@file:OptIn(com.apollographql.apollo3.annotations.ApolloExperimental::class)

package des.c5inco.pokedexer.shared.data.pokemon

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.testing.MapTestNetworkTransport
import com.apollographql.apollo3.testing.registerTestResponse
import des.c5inco.pokedexer.shared.PokemonOriginalQuery
import des.c5inco.pokedexer.shared.model.Generation
import des.c5inco.pokedexer.shared.model.Pokemon
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.job
import kotlinx.coroutines.runBlocking

class RemotePokemonRepositoryTest {
    @Test
    fun passesCanonicalVersionGroupForEveryGeneration() = runBlocking {
        val versionGroups =
            mapOf(
                Generation.I to 7,
                Generation.II to 10,
                Generation.III to 6,
                Generation.IV to 9,
                Generation.V to 14,
                Generation.VI to 16,
                Generation.VII to 18,
                Generation.VIII to 20,
                Generation.IX to 25,
            )

        versionGroups.forEach { (generation, versionGroupId) ->
            val pokemonDao = FakePokemonDao()
            val client = ApolloClient.Builder().networkTransport(MapTestNetworkTransport()).build()
            val applicationScope =
                CoroutineScope(coroutineContext + SupervisorJob(coroutineContext.job))
            val query = PokemonOriginalQuery(generation.id, versionGroupId)
            client.registerTestResponse(
                query,
                PokemonOriginalQuery.Data(
                    info = PokemonOriginalQuery.Info(PokemonOriginalQuery.Total(0)),
                    pokemon = emptyList(),
                ),
            )

            try {
                val loader =
                    GenerationLoader(
                        pokemonDao = pokemonDao,
                        apolloClient = client,
                        applicationScope = applicationScope,
                        refreshMoves = {},
                    )
                RemotePokemonRepository(pokemonDao, loader)
                    .getPokemonByGeneration(generation)
                    .first()

                assertEquals(1, pokemonDao.insertAllCalls, "Generation ${generation.id}")
            } finally {
                applicationScope.cancel()
                client.close()
            }
        }
    }
}

private class FakePokemonDao : PokemonDao {
    private val pokemon = MutableStateFlow(emptyList<Pokemon>())

    var insertAllCalls = 0
        private set

    override suspend fun getAll(): List<Pokemon> = pokemon.value

    override fun getAllFlow(): Flow<List<Pokemon>> = pokemon

    override fun getAllByGeneration(generationId: Int): Flow<List<Pokemon>> =
        MutableStateFlow(pokemon.value.filter { it.generationId == generationId })

    override suspend fun getLoadedGenerationIds(): List<Int> =
        pokemon.value.map(Pokemon::generationId).distinct()

    override fun findById(id: Int): Flow<Pokemon?> =
        MutableStateFlow(pokemon.value.firstOrNull { it.id == id })

    override fun findByIds(ids: List<Int>): Flow<List<Pokemon>> =
        MutableStateFlow(pokemon.value.filter { it.id in ids })

    override fun findByName(name: String): Flow<List<Pokemon>> =
        MutableStateFlow(pokemon.value.filter { name in it.name })

    override suspend fun insert(pokemon: Pokemon) {
        this.pokemon.value += pokemon
    }

    override suspend fun insertAll(vararg pokemon: Pokemon) {
        insertAllCalls++
        this.pokemon.value += pokemon
    }

    override suspend fun delete(pokemon: Pokemon) {
        this.pokemon.value -= pokemon
    }

    override suspend fun deleteAll() {
        pokemon.value = emptyList()
    }
}
