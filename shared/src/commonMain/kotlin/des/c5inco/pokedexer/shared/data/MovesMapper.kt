package des.c5inco.pokedexer.shared.data

import des.c5inco.pokedexer.shared.PokemonOriginalMovesQuery
import des.c5inco.pokedexer.shared.model.Move

/**
 * Maps Apollo GraphQL response to domain Move model.
 */
fun PokemonOriginalMovesQuery.Move.toDomainModel(): Move {
    return Move(
        id = this.id,
        name = formatName(this.name),
        description = this.description.firstOrNull()?.flavorText?.let { cleanupDescriptionText(it) } ?: "",
        category = formatName(this.category?.name ?: "Status"),
        type = formatName(this.type?.name ?: "Normal"),
        pp = this.pp ?: 0,
        power = this.power,
        accuracy = this.accuracy
    )
}

