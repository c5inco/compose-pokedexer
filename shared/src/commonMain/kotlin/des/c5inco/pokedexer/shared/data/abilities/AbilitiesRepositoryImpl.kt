package des.c5inco.pokedexer.shared.data.abilities

import com.apollographql.apollo3.ApolloClient
import des.c5inco.pokedexer.shared.AbilitiesQuery
import des.c5inco.pokedexer.shared.data.Result
import des.c5inco.pokedexer.shared.data.cleanupDescriptionText
import des.c5inco.pokedexer.shared.model.AbilityEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class AbilitiesRepositoryImpl(
    private val abilitiesDao: AbilitiesDao,
    private val apolloClient: ApolloClient
) : AbilitiesRepository {
    override suspend fun updateAbilities() {
        val localAbilities = abilitiesDao.getAll().first()

        if (localAbilities.isEmpty()) {
            println("Loading abilities from network...")
            val response = apolloClient.query(AbilitiesQuery()).execute()

            if (!response.hasErrors()) {
                val abilitiesFromServer = response.data!!.abilities.map { model ->
                    AbilityEntity(
                        id = model.id,
                        name = model.name.split("-").joinToString(" ") { part ->
                            part.replaceFirstChar { it.uppercase() }
                        },
                        description = cleanupDescriptionText(model.flavorText.first().description),
                    )
                }

                abilitiesDao.deleteAll()
                abilitiesDao.insertAll(*abilitiesFromServer.toTypedArray())
                println("Populated abilities database: ${abilitiesFromServer.size}")
            } else {
                throw Exception("The response has errors: ${response.errors}")
            }
        } else {
            println("Abilities loaded from database: ${localAbilities.size}")
        }
    }

    override fun getAbilityById(id: Int): Flow<AbilityEntity?> {
        return abilitiesDao.findById(id)
    }

    override suspend fun getAbilitiesByIds(ids: List<Int>): Result<List<AbilityEntity>> {
        return Result.Success(abilitiesDao.findByIds(ids))
    }

    override suspend fun getAbilitiesByName(name: String): Result<List<AbilityEntity>> {
        return Result.Success(abilitiesDao.findByName(name))
    }
}
