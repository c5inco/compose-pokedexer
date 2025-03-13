package des.c5inco.pokedexer.ui.pokedex

import androidx.compose.animation.core.MutableTransitionState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import des.c5inco.pokedexer.data.pokemon.PokemonRepository
import des.c5inco.pokedexer.data.preferences.UserPreferencesRepository
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.model.Type
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

sealed interface PokedexUiState {
    data class Loading(
        val listLoadedState: MutableTransitionState<Boolean>
    ) : PokedexUiState

    data class Ready(
        val listLoadedState: MutableTransitionState<Boolean>,
        val pokemon: List<Pokemon>,
        val favorites: List<Pokemon>,
    ) : PokedexUiState
}

@HiltViewModel
class PokedexViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    userPreferencesRepository: UserPreferencesRepository,
): ViewModel() {
    private val userPreferencesFlow = userPreferencesRepository.userPreferencesFlow
    private val listLoadedState = MutableTransitionState(false)

    val showFavorites = MutableStateFlow(false)
    val typeFilters = MutableStateFlow<Type?>(null)

    val state: StateFlow<PokedexUiState> =
        combine(
            pokemonRepository.pokemon(),
            userPreferencesFlow,
        ) { pokemon, userPreferences ->
            delay(500)
            listLoadedState.targetState = true

            val favorites = pokemonRepository.getPokemonByIds(userPreferences.favorites).first()

            PokedexUiState.Ready(
                listLoadedState = listLoadedState,
                pokemon = pokemon,
                favorites = favorites.toList(),
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PokedexUiState.Loading(
                listLoadedState = listLoadedState
            )
        )

    fun toggleFavorites() {
        showFavorites.update { !showFavorites.value }
    }

    fun filterByType(typeToFilter: Type?) {
        typeFilters.update { if (typeFilters.value != typeToFilter) typeToFilter else null }
    }
}