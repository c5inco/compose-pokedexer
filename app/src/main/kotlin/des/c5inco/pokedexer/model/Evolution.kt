package des.c5inco.pokedexer.model

data class Evolution(
    val id: Int,
    val targetLevel: Int = -1,
    val trigger: EvolutionTrigger = EvolutionTrigger.LevelUp,
    val itemId: Int = -1
)
enum class EvolutionTrigger(val value: Int) {
    LevelUp(1),
    UseItem(2),
    Trade(3);

    companion object {
        fun fromInt(value: Int) = EvolutionTrigger.values().first { it.value == value }
    }
}