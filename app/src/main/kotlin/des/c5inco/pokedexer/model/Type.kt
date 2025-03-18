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
        Type.Grass -> PokemonColors.Grass
        Type.Bug -> PokemonColors.Bug
        Type.Fairy -> PokemonColors.Fairy
        Type.Fire -> PokemonColors.Fire
        Type.Flying -> PokemonColors.Flying
        Type.Water -> PokemonColors.Water
        Type.Ice -> PokemonColors.Ice
        Type.Dragon -> PokemonColors.Dragon
        Type.Normal -> PokemonColors.Normal
        Type.Fighting -> PokemonColors.Fighting
        Type.Electric -> PokemonColors.Electric
        Type.Psychic -> PokemonColors.Psychic
        Type.Poison -> PokemonColors.Poison
        Type.Ghost -> PokemonColors.Ghost
        Type.Ground -> PokemonColors.Ground
        Type.Rock -> PokemonColors.Rock
        Type.Dark -> PokemonColors.Dark
        Type.Steel -> PokemonColors.Steel
        else -> return Color.Magenta
    }
}

fun mapTypeToCuratedAnalogousHue(type: Type?): Int {
    return when (type) {
        Type.Electric,
        Type.Fairy,
        Type.Grass,
        Type.Poison,
        Type.Ghost,
        Type.Psychic,
        Type.Dragon -> 1
        else -> 0
    }
}