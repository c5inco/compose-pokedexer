package des.c5inco.pokedexer.ui.pokedex

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.EntryPointAccessors
import des.c5inco.pokedexer.data.abilities.AbilitiesRepository
import des.c5inco.pokedexer.data.items.ItemsRepository
import des.c5inco.pokedexer.data.moves.MovesRepository
import des.c5inco.pokedexer.data.pokemon.PokemonRepository
import des.c5inco.pokedexer.data.preferences.UserPreferencesRepository
import des.c5inco.pokedexer.di.ViewModelFactoryProvider
import des.c5inco.pokedexer.model.Ability
import des.c5inco.pokedexer.model.EvolutionTrigger
import des.c5inco.pokedexer.model.Item
import des.c5inco.pokedexer.model.Move
import des.c5inco.pokedexer.model.Pokemon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class PokemonDetailsEvolutions(
    val pokemon: Pokemon,
    val trigger: EvolutionTrigger,
    val targetLevel: Int,
    val item: Item?
)

data class PokemonDetailsMoves(
    val move: Move,
    val targetLevel: Int
)

data class PokemonDetailsAbilities(
    val ability: Ability,
    val isHidden: Boolean,
)

data class PokemonDetailsUiState(
    val details: Pokemon,
    var evolutions: List<PokemonDetailsEvolutions>,
    var moves: List<PokemonDetailsMoves>,
    var abilities: List<PokemonDetailsAbilities>,
    val isFavorite: Boolean,
)

class PokemonDetailsViewModel @AssistedInject constructor(
    private val pokemonRepository: PokemonRepository,
    private val movesRepository: MovesRepository,
    private val itemsRepository: ItemsRepository,
    private val abilitiesRepository: AbilitiesRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    @Assisted private val pokemon: Pokemon
): ViewModel() {
    private val userPreferencesFlow = userPreferencesRepository.userPreferencesFlow
    private val activePokemon = MutableStateFlow(pokemon)
    val pokemonSet = pokemonRepository.pokemon()

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

    val uiState: StateFlow<PokemonDetailsUiState> =
        combine(
            activePokemon,
            userPreferencesFlow,
        ) { incomingPokemon, userPreferences ->
            val evos = incomingPokemon.evolutionChain.mapNotNull {
                val result = pokemonRepository.getPokemonById(it.id).first()

                if (result != null) {
                    PokemonDetailsEvolutions(
                        pokemon = result,
                        targetLevel = it.targetLevel,
                        trigger = it.trigger,
                        item = itemsRepository.getItemById(it.itemId).first()
                    )
                } else {
                    // TODO: Pokemon only queried from local database which currently limited to original 151
                    println("Pokemon not found: $it")
                    null
                }
            }.sortedBy { it.pokemon.id }

            val moves = incomingPokemon.movesList.mapNotNull {
                val result = movesRepository.getMoveById(it.id).first()
                if (result != null) {
                    PokemonDetailsMoves(result, it.targetLevel)
                } else {
                    // TODO: Moves only queried from local database which currently limited to gen 1 moves
                    println("Move not found: $it")
                    null
                }
            }.sortedBy { it.targetLevel }

            val abilities = incomingPokemon.abilitiesList.mapNotNull {
                val result = abilitiesRepository.getAbilityById(it.id).first()
                if (result != null) {
                    PokemonDetailsAbilities(result, it.isHidden)
                } else {
                    // TODO: Moves only queried from local database which currently limited to gen 1 moves
                    println("Ability not found: $it")
                    null
                }
            }

            PokemonDetailsUiState(
                details = incomingPokemon,
                evolutions = evos,
                moves = moves,
                abilities = abilities,
                isFavorite = userPreferences.favorites.contains(incomingPokemon.id),
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PokemonDetailsUiState(
                details = pokemon,
                evolutions = emptyList(),
                moves = emptyList(),
                abilities = emptyList(),
                isFavorite = false,
            )
        )

    fun refresh(incomingPokemon: Pokemon) {
        activePokemon.value = incomingPokemon
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