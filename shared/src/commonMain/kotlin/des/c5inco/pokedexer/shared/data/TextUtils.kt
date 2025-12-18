package des.c5inco.pokedexer.shared.data

/**
 * Cleans up description text from the PokeAPI.
 * Removes form feed characters and normalizes whitespace.
 */
fun cleanupDescriptionText(text: String): String {
    return text
        .replace("\n", " ")
        .replace("\u000c", " ")
        .replace("POKéMON", "pokemon")
}

/**
 * Formats a Pokemon name to proper case.
 */
fun formatName(name: String): String {
    return name.replaceFirstChar { it.uppercase() }
}

