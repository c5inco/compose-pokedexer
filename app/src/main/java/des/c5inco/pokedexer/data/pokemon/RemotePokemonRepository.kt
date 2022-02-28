package des.c5inco.pokedexer.data.pokemon

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import des.c5inco.pokedexer.PokemonOriginalQuery
import des.c5inco.pokedexer.data.Result
import des.c5inco.pokedexer.model.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemotePokemonRepository @Inject constructor(
    private val pokemonDao: PokemonDao,
    private val apolloClient: ApolloClient
) : PokemonRepository {

    override suspend fun getAllPokemon(): Result<List<Pokemon>> {
        val localPokemon = pokemonDao.getAll()

        if (localPokemon.isNotEmpty()) {
            // TODO: Ask why this is needed to avoid blinking
            delay(300)
            println("from cache")
            return Result.Success(localPokemon)
        } else {
            return withContext(Dispatchers.IO) {
                println("from network")
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

                    pokemonDao.deleteAll()
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