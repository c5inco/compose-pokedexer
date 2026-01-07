package des.c5inco.pokedexer.shared.model

import kotlin.native.ObjCName

@ObjCName("PokemonType")
enum class Type {
    Bug,
    Dark,
    Dragon,
    Electric,
    Fairy,
    Fighting,
    Fire,
    Flying,
    Ghost,
    Grass,
    Ground,
    Ice,
    Normal,
    Poison,
    Psychic,
    Rock,
    Steel,
    Water,
}

fun mapTypeToCuratedAnalogousHue(type: Type?): Int {
    return when (type) {
        Type.Fairy,
        Type.Fire -> 3

        Type.Grass -> 2

        Type.Electric,
        Type.Dragon,
        Type.Ghost,
        Type.Poison,
        Type.Psychic -> 1

        else -> 0
    }
}
