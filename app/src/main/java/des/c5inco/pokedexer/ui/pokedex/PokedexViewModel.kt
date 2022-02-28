package des.c5inco.pokedexer.ui.pokedex

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import des.c5inco.pokedexer.data.Result
import des.c5inco.pokedexer.data.pokemon.PokemonRepository
import des.c5inco.pokedexer.model.Pokemon
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UI state for the Pokedex screen
 */
data class PokedexUiState(
    val pokemon: List<Pokemon> = listOf(),
    val loading: Boolean = false,
)

@HiltViewModel
class PokedexViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
): ViewModel() {
    var uiState by mutableStateOf(PokedexUiState(loading = true))
        private set

    init {
        refresh()
    }

    /**
     * Refresh Pokemon list
     */
    fun refresh() {
        viewModelScope.launch {
            // TODO: Handle error/exception better
            when (val result = pokemonRepository.getAllPokemon()) {
                is Result.Success -> {
                    uiState = uiState.copy(
                        loading = false,
                        pokemon = result.data
                    )
                }
                is Result.Error -> {
                    throw result.exception
                }
            }
        }
    }
}