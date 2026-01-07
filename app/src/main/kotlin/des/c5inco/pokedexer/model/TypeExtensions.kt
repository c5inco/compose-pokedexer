package des.c5inco.pokedexer.model

import androidx.compose.ui.graphics.Color
import des.c5inco.pokedexer.shared.model.Type
import des.c5inco.pokedexer.ui.theme.PokemonColors

fun mapTypeToColor(type: String): Color {
    val pokemonType = Type.entries.find { it.name.equals(type, ignoreCase = true) }
        ?: return PokemonColors.Normal

    return when (pokemonType) {
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
