package des.c5inco.pokedexer.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen : NavKey {
    @Serializable
    data object Home : Screen

    @Serializable
    data object Pokedex : Screen

    @Serializable
    data class PokemonDetails(val id: Int) : Screen

    @Serializable
    data object Moves : Screen

    @Serializable
    data object Items : Screen

    @Serializable
    data object TypeCharts : Screen
}
