package des.c5inco.pokedexer.data.pokemon

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import des.c5inco.pokedexer.PokemonOriginalQuery
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

    // used to drive "random" failure in a predictable pattern, making the first request always
    // succeed
    private var requestCount = 0

    /**
     * Randomly fail some loads to simulate a real network.
     *
     * This will fail deterministically every 5 requests
     */
    private fun shouldRandomlyFail(): Boolean = ++requestCount % 5 == 0
}

class LocalPokemonDataSource {
    fun fetchPokemon() : List<Pokemon> {
        return SamplePokemonData
    }
}

class RemotePokemonRepository : PokemonRepository {
    private val apolloClient = ApolloClient.Builder()
        .serverUrl("https://beta.pokeapi.co/graphql/v1beta")
        .build()

    override suspend fun getAllPokemon(): Result<List<Pokemon>> {
        return withContext(Dispatchers.IO) {
            val response = apolloClient.query(PokemonOriginalQuery()).execute()

            if (!response.hasErrors()) {
                Result.Success(
                    response.dataAssertNoErrors.pokemon.map { model ->
                        val detail = model.detail.first()
                        val stats = detail.stats.map { it.base_stat }

                        Pokemon(
                            id = model.id,
                            name = model.name,
                            description = model.description.first().flavor_text,
                            typeOfPokemon = detail.types.map { it.type!!.name },
                            category = model.species[0].genus,
                            image = model.id,
                            hp = stats[0],
                            attack = stats[1],
                            defense = stats[2],
                            specialAttack = stats[3],
                            specialDefense = stats[4],
                            speed = stats[5],
                        )
                    }
                )
            } else {
                Result.Error(
                    ApolloException("The response has errors: ${response.errors}")
                )
            }
        }
    }
}