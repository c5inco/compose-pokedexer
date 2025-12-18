package des.c5inco.pokedexer.shared.data

import des.c5inco.pokedexer.shared.PokemonOriginalQuery
import des.c5inco.pokedexer.shared.model.Evolution
import des.c5inco.pokedexer.shared.model.EvolutionTrigger
import des.c5inco.pokedexer.shared.model.Pokemon
import des.c5inco.pokedexer.shared.model.PokemonAbility
import des.c5inco.pokedexer.shared.model.PokemonMove

/**
 * Maps Apollo GraphQL response to domain Pokemon model.
 */
fun PokemonOriginalQuery.Pokemon.toDomainModel(generationId: Int): Pokemon {
    val detail = this.detail.firstOrNull()
    val stats = detail?.stats?.map { it.baseStat } ?: emptyList()

    return Pokemon(
        id = this.id,
        name = formatName(this.name),
        description = cleanupDescriptionText(this.description.firstOrNull()?.flavorText ?: "")
            .replace(this.name.uppercase(), formatName(this.name)),
        typeOfPokemon = detail?.types?.map { formatName(it.type!!.name) } ?: emptyList(),
        category = this.species.firstOrNull()?.genus ?: "",
        image = this.id,
        height = (detail?.height ?: 0) / 10.0, // in decimeters
        weight = (detail?.weight ?: 0) / 10.0, // in 10 gram chunks
        genderRate = this.genderRate ?: -1,
        generationId = generationId,
        hp = if (stats.isNotEmpty()) stats[0] else 0,
        attack = if (stats.size > 1) stats[1] else 0,
        defense = if (stats.size > 2) stats[2] else 0,
        specialAttack = if (stats.size > 3) stats[3] else 0,
        specialDefense = if (stats.size > 4) stats[4] else 0,
        speed = if (stats.size > 5) stats[5] else 0,
        evolutionChain = transformEvolutionChain(this.evolutionChain?.evolutions ?: emptyList()),
        movesList = transformMoves(detail?.moves ?: emptyList()),
        abilitiesList = transformAbilities(detail?.abilities ?: emptyList())
    )
}

private fun transformEvolutionChain(
    list: List<PokemonOriginalQuery.Evolution>
): List<Evolution> {
    return list.map {
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
    return list.map {
        PokemonMove(it.id!!, it.level)
    }
}

private fun transformAbilities(
    list: List<PokemonOriginalQuery.Ability>
): List<PokemonAbility> {
    return list.map {
        PokemonAbility(it.id!!, it.isHidden)
    }
}

