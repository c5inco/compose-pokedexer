package des.c5inco.pokedexer.ui.pokedex

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import des.c5inco.pokedexer.data.Result
import des.c5inco.pokedexer.data.pokemon.PokemonRepository
import des.c5inco.pokedexer.data.preferences.UserPreferencesRepository
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.model.Type
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed interface PokedexUiState {
    data class Loading(
        val listLoadedState: MutableTransitionState<Boolean>
    ) : PokedexUiState

    data class Ready(
        val listLoadedState: MutableTransitionState<Boolean>,
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
    private val listLoadedState = MutableTransitionState(false)

    var favorites = mutableStateListOf<Pokemon>()
    val showFavorites = MutableStateFlow(false)
    private var typeFilters = MutableStateFlow<Type?>(null)

    val state: StateFlow<PokedexUiState> =
        combine(
            pokemonRepository.pokemon(),
            userPreferencesFlow,
            typeFilters
        ) { pokemon, userPreferences, typeFilters ->
            delay(500)
            listLoadedState.targetState = true

            val favorites = pokemonRepository.getPokemonByIds(userPreferences.favorites).first()

            when (val result = pokemonRepository.getPokemonByIds(userPreferences.favorites)) {
                is Result.Success -> {
                    favorites.addAll(result.data.toList())
                }
                else ->
                    favorites
            }
            PokedexUiState.Ready(
                listLoadedState = listLoadedState,
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
            initialValue = PokedexUiState.Loading(
                listLoadedState = listLoadedState
            )
        )

    fun toggleFavorites() {
        showFavorites.value = !showFavorites.value
    }

    fun filterByType(typeToFilter: Type?) {
        typeFilters.value = if (typeFilters.value != typeToFilter) typeToFilter else null
    }
}