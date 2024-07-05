package des.c5inco.pokedexer.data

fun cleanupDescriptionText(
    text: String,
): String {
    return text
        .replace("\n", " ")
        .replace("\u000c", " ")
        .replace("POKéMON", "pokemon")
}
