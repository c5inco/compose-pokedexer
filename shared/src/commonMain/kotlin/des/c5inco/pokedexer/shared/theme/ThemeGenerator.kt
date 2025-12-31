package des.c5inco.pokedexer.shared.theme

import des.c5inco.pokedexer.shared.model.Type

/**
 * Represents a Pokemon theme color scheme that can be used cross-platform.
 * Colors are represented as ARGB integers for easy conversion to platform-specific color types.
 */
data class PokemonThemeColors(
    val primary: Int,
    val secondary: Int,
    val tertiary: Int,
    val background: Int,
    val onBackground: Int,
    val surface: Int,
    val onSurface: Int,
    val surfaceVariant: Int,
    val onSurfaceVariant: Int,
    val surfaceContainer: Int,
    val surfaceContainerLow: Int,
    val surfaceContainerHigh: Int
)

/**
 * Pokemon type seed colors as ARGB integers.
 * This is the cross-platform color definition used by both Android and iOS.
 */
object PokemonTypeSeeds {
    const val Bug = 0xffAFC836.toInt()
    const val Dark = 0xff9298A4.toInt()
    const val Dragon = 0xff0180C7.toInt()
    const val Electric = 0xffF0C03E.toInt()
    const val Fairy = 0xffee99ee.toInt()
    const val Fighting = 0xffE74347.toInt()
    const val Fire = 0xffFBAE46.toInt()
    const val Flying = 0xffA6C2F2.toInt()
    const val Ghost = 0xff7773D4.toInt()
    const val Grass = 0xff5AC178.toInt()
    const val Ground = 0xffD29463.toInt()
    const val Ice = 0xff8CDDD4.toInt()
    const val Normal = 0xffA3A49E.toInt()
    const val Poison = 0xffC261D4.toInt()
    const val Psychic = 0xffFE9F92.toInt()
    const val Rock = 0xffD7CD90.toInt()
    const val Water = 0xff429BED.toInt()
    const val Steel = 0xff58A6AA.toInt()
}

/**
 * Maps Pokemon types to their seed color as ARGB integer.
 * This is the shared implementation used by both Android and iOS.
 */
fun getSeedColorForType(types: List<String>): Int {
    val firstType = types.firstOrNull() ?: return 0xFFFF00FF.toInt() // Magenta fallback

    return try {
        when (Type.valueOf(firstType.replaceFirstChar { it.uppercase() })) {
            Type.Bug -> PokemonTypeSeeds.Bug
            Type.Dark -> PokemonTypeSeeds.Dark
            Type.Dragon -> PokemonTypeSeeds.Dragon
            Type.Electric -> PokemonTypeSeeds.Electric
            Type.Fairy -> PokemonTypeSeeds.Fairy
            Type.Fighting -> PokemonTypeSeeds.Fighting
            Type.Fire -> PokemonTypeSeeds.Fire
            Type.Flying -> PokemonTypeSeeds.Flying
            Type.Ghost -> PokemonTypeSeeds.Ghost
            Type.Grass -> PokemonTypeSeeds.Grass
            Type.Ground -> PokemonTypeSeeds.Ground
            Type.Ice -> PokemonTypeSeeds.Ice
            Type.Normal -> PokemonTypeSeeds.Normal
            Type.Poison -> PokemonTypeSeeds.Poison
            Type.Psychic -> PokemonTypeSeeds.Psychic
            Type.Rock -> PokemonTypeSeeds.Rock
            Type.Steel -> PokemonTypeSeeds.Steel
            Type.Water -> PokemonTypeSeeds.Water
        }
    } catch (e: IllegalArgumentException) {
        0xFFFF00FF.toInt() // Magenta fallback
    }
}
