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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface PokedexUiState {
    data object Loading : PokedexUiState
    data class Ready(
        val pokemon: List<Pokemon>,
        val favorites: List<Pokemon>,
        val typeFilter: Type?
    ) : PokedexUiState
}

@HiltViewModel
class PokedexViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    userPreferencesRepository: UserPreferencesRepository,
): ViewModel() {
    private val userPreferencesFlow = userPreferencesRepository.userPreferencesFlow
    var favorites = mutableStateListOf<Pokemon>()
    var showFavorites by mutableStateOf(false)
    private var typeFilters = MutableStateFlow<Type?>(null)

    val state: StateFlow<PokedexUiState> =
        combine(
            pokemonRepository.pokemon(),
            userPreferencesFlow,
            typeFilters
        ) { pokemon, userPreferences, typeFilters ->
            if (favorites.isNotEmpty()) {
                favorites.clear()
            }

            when (val result = pokemonRepository.getPokemonByIds(userPreferences.favorites)) {
                is Result.Success -> {
                    favorites.addAll(result.data.toList())
                }
                else ->
                    favorites
            }
            PokedexUiState.Ready(
                pokemon = pokemon.filter {
                    if (typeFilters != null) {
                        it.typeOfPokemon.contains(typeFilters.toString())
                    } else {
                        true
                    }
                },
                favorites = favorites.toList(),
                typeFilter = typeFilters
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PokedexUiState.Loading
        )

    fun toggleFavorites() {
        showFavorites = !showFavorites
    }

    fun filterByType(typeToFilter: Type?) {
        viewModelScope.launch {
            typeFilters.value = if (typeFilters.value != typeToFilter) typeToFilter else null
        }
    }
}