package des.c5inco.pokedexer.ui.pokedex

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import des.c5inco.pokedexer.data.Result
import des.c5inco.pokedexer.data.pokemon.PokemonRepository
import des.c5inco.pokedexer.data.preferences.UserPreferencesRepository
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.model.Type
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
    private val pokemonRepository: PokemonRepository,
    userPreferencesRepository: UserPreferencesRepository,
): ViewModel() {
    var uiState by mutableStateOf(PokedexUiState(loading = true))
        private set
    private val pokemonList = mutableStateListOf<Pokemon>()

    private val userPreferencesFlow = userPreferencesRepository.userPreferencesFlow
    var favorites = mutableStateListOf<Pokemon>()
    var showFavorites by mutableStateOf(false)
    var typeFilter by mutableStateOf<Type?>(null)

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
                    pokemonList.addAll(result.data)
                    uiState = uiState.copy(
                        loading = false,
                        pokemon = pokemonList.toList()
                    )
                }
                is Result.Error -> {
                    throw result.exception
                }
            }

            userPreferencesFlow.collect {
                if (favorites.isNotEmpty()) {
                    favorites.clear()
                }

                when (val result = pokemonRepository.getPokemonByIds(it.favorites)) {
                    is Result.Success -> {
                        favorites.addAll(result.data.toList())
                    }
                    else ->
                        favorites
                }
            }
        }
    }

    fun toggleFavorites() {
        showFavorites = !showFavorites
    }

    fun filterByType(
        typeToFilter: Type?
    ) {
        typeFilter = if (typeFilter != typeToFilter) typeToFilter else null

        uiState = PokedexUiState(
            loading = false,
            pokemon = if (typeFilter != null) {
                pokemonList.filter { it.typeOfPokemon.contains(typeFilter.toString()) }
            } else {
                pokemonList.toList()
            }
        )
    }
}