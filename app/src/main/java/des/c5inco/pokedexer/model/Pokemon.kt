package des.c5inco.pokedexer.model

import androidx.compose.ui.graphics.Color
import des.c5inco.pokedexer.ui.theme.PokemonColors

data class Pokemon(
    val id: Int,
    val name: String,
    val description: String,
    val typeOfPokemon: List<String>,
    val category: String,
    val image: Int,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int,
)

fun Pokemon.color(): Color {
    val type = typeOfPokemon.elementAtOrNull(0)

    return when (type?.lowercase()) {
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