package des.c5inco.pokedexer.model

import androidx.compose.ui.graphics.Color
import des.c5inco.pokedexer.shared.model.Type
import des.c5inco.pokedexer.ui.theme.PokemonColors

private val TypeColors =
    mapOf(
        Type.Bug to PokemonColors.Bug,
        Type.Dark to PokemonColors.Dark,
        Type.Dragon to PokemonColors.Dragon,
        Type.Electric to PokemonColors.Electric,
        Type.Fairy to PokemonColors.Fairy,
        Type.Fighting to PokemonColors.Fighting,
        Type.Fire to PokemonColors.Fire,
        Type.Flying to PokemonColors.Flying,
        Type.Ghost to PokemonColors.Ghost,
        Type.Grass to PokemonColors.Grass,
        Type.Ground to PokemonColors.Ground,
        Type.Ice to PokemonColors.Ice,
        Type.Normal to PokemonColors.Normal,
        Type.Poison to PokemonColors.Poison,
        Type.Psychic to PokemonColors.Psychic,
        Type.Rock to PokemonColors.Rock,
        Type.Steel to PokemonColors.Steel,
        Type.Water to PokemonColors.Water,
    )

fun mapTypeToColor(type: String): Color {
    val pokemonType =
        Type.entries.find { it.name.equals(type, ignoreCase = true) } ?: return PokemonColors.Normal

    return TypeColors.getValue(pokemonType)
}
