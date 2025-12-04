package des.c5inco.pokedexer

import kotlinx.serialization.Serializable

/**
 * Navigation destinations for the Pokedexer app using Nav3.
 * Each destination is a serializable sealed class for type-safe navigation.
 */
@Serializable
sealed interface AppDestination {
    /**
     * Home screen - the main landing page with menu options
     */
    @Serializable
    data object Home : AppDestination

    /**
     * Pokedex list screen showing all Pokemon
     */
    @Serializable
    data object PokedexList : AppDestination

    /**
     * Pokemon details screen
     * @param pokemonId The ID of the Pokemon to display
     */
    @Serializable
    data class PokemonDetails(val pokemonId: Int) : AppDestination

    /**
     * Moves list screen showing all Pokemon moves
     */
    @Serializable
    data object Moves : AppDestination

    /**
     * Items screen showing all Pokemon items
     */
    @Serializable
    data object Items : AppDestination
}
