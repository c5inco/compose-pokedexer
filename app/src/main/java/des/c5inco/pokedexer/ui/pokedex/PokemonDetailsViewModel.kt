package des.c5inco.pokedexer.ui.pokedex

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
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
import des.c5inco.pokedexer.data.pokemon.PokemonRepository
import des.c5inco.pokedexer.di.ViewModelFactoryProvider
import des.c5inco.pokedexer.model.Pokemon
import kotlinx.coroutines.launch

/**
 * UI state for the Pokemon details screen
 */
data class PokemonDetailsUiState(
    val pokemon: Pokemon?,
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

class PokemonDetailsViewModel @AssistedInject constructor(
    private val pokemonRepository: PokemonRepository,
    @Assisted private val pokemon: Pokemon
): ViewModel() {
    var evolutions = mutableStateListOf<PokemonDetailsEvolutions>()
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
        pokemon.evolutionChain.forEach {
            viewModelScope.launch {
                when (val result = pokemonRepository.getPokemonById(it.id)) {
                    is Result.Success -> {
                        evolutions.add(PokemonDetailsEvolutions(result.data, it.targetLevel))
                    }
                    is Result.Error -> {
                        throw result.exception
                    }
                }
            }
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