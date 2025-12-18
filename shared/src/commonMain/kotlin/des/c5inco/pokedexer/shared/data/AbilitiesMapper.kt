package des.c5inco.pokedexer.shared.data

import des.c5inco.pokedexer.shared.AbilitiesQuery
import des.c5inco.pokedexer.shared.model.Ability

/**
 * Maps Apollo GraphQL response to domain Ability model.
 */
fun AbilitiesQuery.Ability.toDomainModel(): Ability {
    return Ability(
        id = this.id,
        name = formatAbilityName(this.name),
        description = this.flavorText.firstOrNull()?.description?.let { cleanupDescriptionText(it) } ?: ""
    )
}

private fun formatAbilityName(name: String): String {
    return name.split("-").joinToString(" ") { it.replaceFirstChar { c -> c.uppercase() } }
}

