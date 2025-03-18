package des.c5inco.pokedexer.model

import androidx.compose.ui.graphics.Color
import des.c5inco.pokedexer.ui.theme.PokemonColors

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

fun mapTypeToColor(type: String): Color {
    return when (Type.valueOf(type)) {
        Type.Bug -> PokemonColors.Bug
        Type.Dark -> PokemonColors.Dark
        Type.Dragon -> PokemonColors.Dragon
        Type.Electric -> PokemonColors.Electric
        Type.Fairy -> PokemonColors.Fairy
        Type.Fighting -> PokemonColors.Fighting
        Type.Fire -> PokemonColors.Fire
        Type.Flying -> PokemonColors.Flying
        Type.Ghost -> PokemonColors.Ghost
        Type.Grass -> PokemonColors.Grass
        Type.Ground -> PokemonColors.Ground
        Type.Ice -> PokemonColors.Ice
        Type.Normal -> PokemonColors.Normal
        Type.Poison -> PokemonColors.Poison
        Type.Psychic -> PokemonColors.Psychic
        Type.Rock -> PokemonColors.Rock
        Type.Steel -> PokemonColors.Steel
        Type.Water -> PokemonColors.Water
    }
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