package des.c5inco.pokedexer.shared.model

enum class MoveCategory {
    Physical,
    Special,
    Status
}

data class PokemonMove(
    val id: Int,
    val targetLevel: Int
)
