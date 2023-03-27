package des.c5inco.pokedexer.ui.pokedex

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.EntryPointAccessors
import des.c5inco.pokedexer.data.Result
import des.c5inco.pokedexer.data.moves.MovesRepository
import des.c5inco.pokedexer.data.pokemon.PokemonRepository
import des.c5inco.pokedexer.data.preferences.UserPreferencesRepository
import des.c5inco.pokedexer.di.ViewModelFactoryProvider
import des.c5inco.pokedexer.model.Move
import des.c5inco.pokedexer.model.Pokemon
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

/**
 * UI state for the Pokemon details screen
 */
data class PokemonDetailsUiState(
    val pokemon: Pokemon?,
    val isFavorite: Boolean = false,
    val loading: Boolean = false,
)

data class PokemonDetailsAboutUiState(
    val description: String
)

data class PokemonDetailsBaseStatsUiState(
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int,
)

data class PokemonDetailsEvolutions(
    val pokemon: Pokemon,
    val targetLevel: Int
)

data class PokemonDetailsMoves(
    val move: Move,
    val targetLevel: Int
)

class PokemonDetailsViewModel @AssistedInject constructor(
    private val pokemonRepository: PokemonRepository,
    private val movesRepository: MovesRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    @Assisted private val pokemon: Pokemon
): ViewModel() {
    private val userPreferencesFlow = userPreferencesRepository.userPreferencesFlow
    var details by mutableStateOf(pokemon)
        private set

    var evolutions by mutableStateOf(listOf<PokemonDetailsEvolutions>())
        private set

    var moves by mutableStateOf(listOf<PokemonDetailsMoves>())
        private set

    var isFavorite by mutableStateOf(false)
        private set

    @AssistedFactory
    interface PokemonDetailsViewModelFactory {
        fun create(pokemon: Pokemon): PokemonDetailsViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: PokemonDetailsViewModelFactory,
            pokemon: Pokemon
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(pokemon) as T
            }
        }
    }

    init {
        refresh(pokemon)
    }

    fun refresh(incomingPokemon: Pokemon) {
        viewModelScope.launch {
            details = incomingPokemon

            val ev = mutableListOf<PokemonDetailsEvolutions>()
            incomingPokemon.evolutionChain.map {
                launch {
                    when (val result = pokemonRepository.getPokemonById(it.id)) {
                        is Result.Success -> {
                            ev.add(PokemonDetailsEvolutions(result.data, it.targetLevel))
                        }
                        is Result.Error -> {
                            // TODO: Pokemon only queried from local database which currently limited to original 151
                            println(result.exception)
                        }
                    }
                }
            }.joinAll()
            evolutions = ev
                .sortedBy { it.pokemon.id }
                .toList()

            val mv = mutableListOf<PokemonDetailsMoves>()
            incomingPokemon.movesList.map {
                launch {
                    when (val result = movesRepository.getMoveById(it.id)) {
                        is Result.Success -> {
                            mv.add(PokemonDetailsMoves(result.data, it.targetLevel))
                        }
                        is Result.Error -> {
                            // TODO: Moves only queried from local database which currently limited to gen 1 moves
                            println(result.exception)
                        }
                    }
                }
            }.joinAll()
            moves = mv
                .sortedBy { it.targetLevel }
                .toList()

            userPreferencesFlow.collect {
                isFavorite = it.favorites.contains(incomingPokemon.id)
            }
        }
    }

    fun toggleFavorite(pokemonId: Int) {
        viewModelScope.launch {
            userPreferencesRepository.updateFavorites(pokemonId)
        }
    }
}

@Composable
fun pokemonDetailsViewModel(pokemon: Pokemon): PokemonDetailsViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    ).pokemonDetailsViewModelFactory()

    return viewModel(factory = PokemonDetailsViewModel.provideFactory(factory, pokemon))
}