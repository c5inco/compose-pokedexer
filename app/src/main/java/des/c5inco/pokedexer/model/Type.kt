package des.c5inco.pokedexer.model

import androidx.compose.ui.graphics.Color
import des.c5inco.pokedexer.ui.theme.PokemonColors

enum class Type {
    Normal,
    Fire,
    Fighting,
    Water,
    Flying,
    Grass,
    Poison,
    Electric,
    Ground,
    Psychic,
    Rock,
    Ice,
    Bug,
    Dragon,
    Ghost,
    Dark,
    Steel,
    Fairy,
}

fun mapTypeToColor(type: String): Color {
    return when (Type.valueOf(type)) {
        Type.Grass -> PokemonColors.LightTeal
        Type.Bug -> PokemonColors.Bug
        Type.Fire -> PokemonColors.Fire
        Type.Flying -> PokemonColors.Flying
        Type.Water -> PokemonColors.Blue
        Type.Ice -> PokemonColors.Ice
        Type.Dragon -> PokemonColors.Dragon
        Type.Normal -> PokemonColors.Normal
        Type.Fighting -> PokemonColors.Fighting
        Type.Electric -> PokemonColors.Electric
        Type.Psychic -> PokemonColors.Psychic
        Type.Poison -> PokemonColors.Poison
        Type.Ghost -> PokemonColors.LightPurple
        Type.Ground, Type.Rock -> PokemonColors.LightBrown
        Type.Dark -> PokemonColors.Dark
        else -> return PokemonColors.LightBlue
    }
}