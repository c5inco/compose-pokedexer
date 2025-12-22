package des.c5inco.pokedexer.ui.pokedex

import androidx.compose.animation.core.MutableTransitionState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import des.c5inco.pokedexer.data.pokemon.PokemonRepository
import des.c5inco.pokedexer.data.preferences.UserPreferencesRepository
import des.c5inco.pokedexer.model.Generation
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.model.Type
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

sealed interface PokedexUiState {
    data class Loading(
        val listLoadedState: MutableTransitionState<Boolean>
    ) : PokedexUiState

    data class Ready(
        val listLoadedState: MutableTransitionState<Boolean>,
        val pokemon: List<Pokemon>,
        val favoriteIds: Set<Int>,
    ) : PokedexUiState
}

class PokedexViewModel @AssistedInject constructor(
    private val pokemonRepository: PokemonRepository,
    userPreferencesRepository: UserPreferencesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle,
): ViewModel() {
    private val userPreferencesFlow = userPreferencesRepository.userPreferencesFlow

    val showFavorites = savedStateHandle.getStateFlow("showFavorites", false)
    val typeFilters = savedStateHandle.getStateFlow<Type?>("typeFilters", null)
    val generationFilters = savedStateHandle.getStateFlow("generationFilters", Generation.I)

    val state: StateFlow<PokedexUiState> = generationFilters
        .flatMapLatest { generation ->
            val listLoadedState = MutableTransitionState(false)

            val pokemonFlow = pokemonRepository.getPokemonByGeneration(generation)

            flow {
                emit(PokedexUiState.Loading(listLoadedState))
                delay(500)
                emitAll(
                    combine(
                        pokemonFlow,
                        userPreferencesFlow,
                        showFavorites,
                        typeFilters
                    ) { pokemon, userPreferences, favoritesOnly, typeFilter ->
                        listLoadedState.targetState = true

                        val favoriteIds = userPreferences.favorites.toSet()

                        val filteredPokemon = pokemon.filter { p ->
                            val isFavorite = favoriteIds.contains(p.id)
                            val matchesFavorites = if (favoritesOnly) isFavorite else true
                            val matchesType = if (typeFilter != null) {
                                p.typeOfPokemon.contains(typeFilter.toString())
                            } else {
                                true
                            }
                            matchesFavorites && matchesType
                        }

                        PokedexUiState.Ready(
                            listLoadedState = listLoadedState,
                            pokemon = filteredPokemon,
                            favoriteIds = favoriteIds,
                        )
                    }
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = PokedexUiState.Loading(
                listLoadedState = MutableTransitionState(false)
            )
        )

    fun toggleFavorites() {
        savedStateHandle["showFavorites"] = !showFavorites.value
    }

    fun filterByType(typeToFilter: Type?) {
        savedStateHandle["typeFilters"] = if (typeFilters.value != typeToFilter) typeToFilter else null
    }

    fun filterByGeneration(generationToFilter: Generation?) {
        savedStateHandle["generationFilters"] = generationToFilter ?: Generation.I
    }

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): PokedexViewModel
    }
}
