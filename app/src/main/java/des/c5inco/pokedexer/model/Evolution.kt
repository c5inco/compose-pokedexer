package des.c5inco.pokedexer.model

data class Evolution(
    val id: Int,
    val targetLevel: Int
)

data class EvolutionTrigger(
    val targetLevel: Int?,
    val type: EvolutionTriggerType,
    val itemId: Int?
)

enum class EvolutionTriggerType {
    LevelUp,
    UseItem,
    Trade
}