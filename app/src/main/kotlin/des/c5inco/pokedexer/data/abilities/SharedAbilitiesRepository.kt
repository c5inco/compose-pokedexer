package des.c5inco.pokedexer.data.abilities

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import des.c5inco.pokedexer.data.Result
import des.c5inco.pokedexer.data.toAndroid
import des.c5inco.pokedexer.data.toAndroidAbilityList
import des.c5inco.pokedexer.model.Ability
import des.c5inco.pokedexer.shared.AbilitiesQuery
import des.c5inco.pokedexer.shared.data.toDomainModel
import des.c5inco.pokedexer.shared.data.db.AbilitiesDao as SharedAbilitiesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Abilities repository implementation that uses the shared KMP database.
 * Returns Android models for compatibility with existing UI code.
 */
class SharedAbilitiesRepository @Inject constructor(
    private val sharedAbilitiesDao: SharedAbilitiesDao,
    private val apolloClient: ApolloClient
) : AbilitiesRepository {

    override suspend fun updateAbilities() {
        val localAbilities = sharedAbilitiesDao.getAll().first()

        if (localAbilities.isEmpty()) {
            return withContext(Dispatchers.IO) {
                println("Loading abilities from network using shared module...")
                val response = apolloClient.query(AbilitiesQuery()).execute()

                if (!response.hasErrors()) {
                    val abilitiesFromServer = response.data!!.abilities.map { model ->
                        model.toDomainModel()
                    }

                    sharedAbilitiesDao.deleteAll()
                    sharedAbilitiesDao.insertAll(*abilitiesFromServer.toTypedArray())
                    println("Populated shared abilities database: ${abilitiesFromServer.size}")
                } else {
                    throw ApolloException("The response has errors: ${response.errors}")
                }
            }
        } else {
            println("Abilities loaded from shared database: ${localAbilities.size}")
        }
    }

    override fun getAbilityById(id: Int): Flow<Ability?> {
        return sharedAbilitiesDao.findById(id).map { it?.toAndroid() }
    }

    override suspend fun getAbilitiesByIds(ids: List<Int>): Result<List<Ability>> {
        return Result.Success(sharedAbilitiesDao.findByIds(ids).toAndroidAbilityList())
    }

    override suspend fun getAbilitiesByName(name: String): Result<List<Ability>> {
        return Result.Success(sharedAbilitiesDao.findByName(name).toAndroidAbilityList())
    }
}

