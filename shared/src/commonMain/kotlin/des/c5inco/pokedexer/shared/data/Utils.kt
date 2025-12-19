package des.c5inco.pokedexer.shared.data

fun cleanupDescriptionText(text: String): String {
    return text.replace("\n", " ")
}
