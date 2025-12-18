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
        sprite = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/${this.name}.png"
    )
}

private fun formatItemName(name: String): String {
    return name.split("-").joinToString(" ") { it.replaceFirstChar { c -> c.uppercase() } }
}

