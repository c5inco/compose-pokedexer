package des.c5inco.pokedexer.data.abilities

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.exception.DefaultApolloException
import des.c5inco.pokedexer.AbilitiesQuery
import des.c5inco.pokedexer.data.Result
import des.c5inco.pokedexer.data.cleanupDescriptionText
import des.c5inco.pokedexer.model.Ability
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface AbilitiesRepository {
    suspend fun getAllAbilities(): Result<List<Ability>>
    suspend fun getAbilityById(id: Int): Result<Ability>
    suspend fun getAbilitiesByIds(ids: List<Int>): Result<List<Ability>>
    suspend fun getAbilitiesByName(name: String): Result<List<Ability>>
}

class AbilitiesRepositoryImpl @Inject constructor(
    private val abilitiesDao: AbilitiesDao,
    private val apolloClient: ApolloClient
): AbilitiesRepository {
    override suspend fun getAllAbilities(): Result<List<Ability>> {
        val localItems = abilitiesDao.getAll()

        if (localItems.isNotEmpty()) {
            delay(300)
            println("abilities from cache")
            return Result.Success(localItems)
        } else {
            return withContext(Dispatchers.IO) {
                println("abilities from network")
                val response = apolloClient.query(AbilitiesQuery()).execute()

                if (!response.hasErrors()) {
                    val abilitiesFromServer = response.data!!.abilities.map { model ->
                        Ability(
                            id = model.id,
                            name = model.name.split("-").joinToString(" ") { part ->
                                part.replaceFirstChar { it.uppercase() }
                            },
                            description = cleanupDescriptionText(model.flavorText.first().description),
                        )
                    }

                    abilitiesDao.deleteAll()
                    abilitiesDao.insertAll(*abilitiesFromServer.toTypedArray())
                    Result.Success(abilitiesFromServer)
                } else {
                    Result.Error(
                        DefaultApolloException("The response has errors: ${response.errors}", null)
                    )
                }
            }
        }
    }

    override suspend fun getAbilityById(id: Int): Result<Ability> {
        abilitiesDao.findById(id)?.let {
            return Result.Success(it)
        }
        return Result.Error(
            Exception("Ability with ID: $id not found in local DB!")
        )
    }

    override suspend fun getAbilitiesByIds(ids: List<Int>): Result<List<Ability>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAbilitiesByName(name: String): Result<List<Ability>> {
        return Result.Success(abilitiesDao.findByName(name))
    }
}