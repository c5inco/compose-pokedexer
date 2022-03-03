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
    return when (type.lowercase()) {
        "grass", "bug" -> PokemonColors.LightTeal
        "fire" -> PokemonColors.LightRed
        "water", "fighting", "normal" -> PokemonColors.LightBlue
        "electric", "psychic" -> PokemonColors.LightYellow
        "poison", "ghost" -> PokemonColors.LightPurple
        "ground", "rock" -> PokemonColors.LightBrown
        "dark" -> PokemonColors.Black
        else -> return PokemonColors.LightBlue
    }
}