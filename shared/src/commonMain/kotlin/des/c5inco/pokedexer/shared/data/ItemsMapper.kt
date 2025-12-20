package des.c5inco.pokedexer.shared.data

import des.c5inco.pokedexer.shared.ItemsQuery
import des.c5inco.pokedexer.shared.model.Item

/**
 * Maps Apollo GraphQL response to domain Item model.
 */
fun ItemsQuery.Item.toDomainModel(): Item {
    return Item(
        id = this.id,
        name = formatItemName(this.name),
        description = this.flavorText.firstOrNull()?.text?.let { cleanupDescriptionText(it) } ?: "",
        sprite = this.name
    )
}

private fun formatItemName(name: String): String {
    return name.split("-").joinToString(" ") { it.replaceFirstChar { c -> c.uppercase() } }
}

