package des.c5inco.pokedexer.data.pokemon

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import des.c5inco.pokedexer.PokemonOriginalQuery
import des.c5inco.pokedexer.data.Result
import des.c5inco.pokedexer.model.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemotePokemonRepository(
    private val pokemonDao: PokemonDao
) : PokemonRepository {
    private val apolloClient = ApolloClient.Builder()
        .serverUrl("https://beta.pokeapi.co/graphql/v1beta")
        .build()

    override suspend fun getAllPokemon(): Result<List<Pokemon>> {
        val localPokemon = pokemonDao.getAll()

        if (localPokemon.isNotEmpty()) {
            return Result.Success(localPokemon)
        } else {
            return withContext(Dispatchers.IO) {
                val response = apolloClient.query(PokemonOriginalQuery()).execute()

                if (!response.hasErrors()) {
                    val pokemonFromServer = response.data!!.pokemon.map { model ->
                        val detail = model.detail.first()
                        val stats = detail.stats.map { it.base_stat }

                        Pokemon(
                            id = model.id,
                            name = formatName(model.name),
                            description = model.description.first().flavor_text,
                            typeOfPokemon = detail.types.map { formatName(it.type!!.name) },
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

                    pokemonDao.insertAll(*pokemonFromServer.toTypedArray())
                    Result.Success(pokemonFromServer)
                } else {
                    Result.Error(
                        ApolloException("The response has errors: ${response.errors}")
                    )
                }
            }
        }
    }

    override suspend fun addPokemon(pokemon: Pokemon): Result<Pokemon> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllPokemon(): Result<Int> {
        TODO("Not yet implemented")
    }
}

private fun formatName(
    name: String
): String {
    return name.replaceFirstChar { it.uppercase() }
}