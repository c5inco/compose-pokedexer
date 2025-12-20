package des.c5inco.pokedexer.data.pokemon

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import des.c5inco.pokedexer.PokemonOriginalQuery
import des.c5inco.pokedexer.data.Result
import des.c5inco.pokedexer.data.cleanupDescriptionText
import des.c5inco.pokedexer.model.Evolution
import des.c5inco.pokedexer.model.EvolutionTrigger
import des.c5inco.pokedexer.model.Generation
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.model.PokemonAbility
import des.c5inco.pokedexer.model.PokemonMove
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class RemotePokemonRepository(
    private val pokemonDao: PokemonDao,
    private val apolloClient: ApolloClient
) : PokemonRepository {
    override fun pokemon(): Flow<List<Pokemon>> {
        return pokemonDao.getAllFlow()
    }

    override suspend fun updatePokemon() {
        updatePokemonByGeneration(1)
    }

    private suspend fun updatePokemonByGeneration(generationId: Int) {
        val localPokemon = pokemonDao.getAllByGeneration(generationId).first()

        if (localPokemon.isEmpty()) {
            withContext(Dispatchers.IO) {
                println("Loading Pokemon (gen $generationId) from network...")
                val response = apolloClient.query(PokemonOriginalQuery(generationId)).execute()

                if (!response.hasErrors()) {
                    val pokemonFromServer = response.data!!.pokemon.map { model ->
                        val detail = model.detail.first()
                        val stats = detail.stats.map { it.baseStat }

                        Pokemon(
                            id = model.id,
                            name = formatName(model.name),
                            description = cleanupDescriptionText(model.description.first().flavorText)
                                .replace(model.name.uppercase(), formatName(model.name)),
                            typeOfPokemon = detail.types.map { formatName(it.type!!.name) },
                            category = model.species[0].genus,
                            image = model.id,
                            height = (detail.height ?: 0) / 10.0, // in decimeters
                            weight = (detail.weight ?: 0) / 10.0, // in 10 gram chunks
                            genderRate = model.genderRate ?: -1,
                            generationId = generationId,
                            hp = stats[0],
                            attack = stats[1],
                            defense = stats[2],
                            specialAttack = stats[3],
                            specialDefense = stats[4],
                            speed = stats[5],
                            evolutionChain = transformEvolutionChain(model.evolutionChain?.evolutions ?: emptyList()),
                            movesList = transformMoves(detail.moves),
                            abilitiesList = transformAbilities(detail.abilities)
                        )
                    }

                    pokemonDao.insertAll(*pokemonFromServer.toTypedArray())
                    println("Populated pokemon database: ${pokemonFromServer.size}")
                } else {
                    throw ApolloException("The response has errors: ${response.errors}")
                }
            }
        } else {
            println("Pokemon (Gen $generationId) loaded from database: ${localPokemon.size}")
        }
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
        return flow {
            val localPokemon = pokemonDao.getAllByGeneration(generation.id).first()
            
            if (localPokemon.isEmpty()) {
                // Generation not in database, fetch from remote
                try {
                    updatePokemonByGeneration(generation.id)
                    // After fetching, emit the data from the database
                    val remotePokemon = pokemonDao.getAllByGeneration(generation.id).first()
                    emit(remotePokemon)
                } catch (e: Exception) {
                    println("Failed to fetch Pokemon for generation ${generation.id}: ${e.message}")
                    // Emit empty list on failure
                    emit(emptyList())
                }
            } else {
                // Generation already in database, emit it
                emit(localPokemon)
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

private fun transformEvolutionChain(
    list: List<PokemonOriginalQuery.Evolution>
): List<Evolution> {
    return list
        .map {
            if (it.targetLevels.isNotEmpty()) {
                val target = it.targetLevels.first()

                Evolution(
                    id = it.id,
                    targetLevel = target.level ?: -1,
                    trigger = when (target.triggerType) {
                        3 -> EvolutionTrigger.UseItem
                        2 -> EvolutionTrigger.Trade
                        else -> EvolutionTrigger.LevelUp
                    },
                    itemId = target.itemId ?: -1
                )
            } else {
                Evolution(id = it.id)
            }
        }
}

private fun transformMoves(
    list: List<PokemonOriginalQuery.Move>
): List<PokemonMove> {
    return list
        .map {
            PokemonMove(it.id!!, it.level)
        }
}

private fun transformAbilities(
    list: List<PokemonOriginalQuery.Ability>
): List<PokemonAbility> {
    return list
        .map {
            PokemonAbility(it.id!!, it.isHidden)
        }
}


private fun formatName(
    name: String
): String {
    return name.replaceFirstChar { it.uppercase() }
}
