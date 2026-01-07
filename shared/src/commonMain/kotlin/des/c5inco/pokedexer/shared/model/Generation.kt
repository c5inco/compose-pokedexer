package des.c5inco.pokedexer.shared.model

enum class Generation(val id: Int, val romanNumeral: String) {
    I(1, "I"),
    II(2, "II"),
    III(3, "III"),
    IV(4, "IV"),
    V(5, "V"),
    VI(6, "VI"),
    VII(7, "VII"),
    VIII(8, "VIII"),
    IX(9, "IX");

    override fun toString(): String {
        return romanNumeral
    }

    companion object {
        fun fromId(id: Int): Generation? {
            return entries.find { it.id == id }
        }
    }
}
