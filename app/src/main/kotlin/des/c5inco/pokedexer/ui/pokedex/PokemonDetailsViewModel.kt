package des.c5inco.pokedexer.ui.pokedex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import des.c5inco.pokedexer.shared.data.abilities.AbilitiesRepository
import des.c5inco.pokedexer.shared.data.items.ItemsRepository
import des.c5inco.pokedexer.shared.data.moves.MovesRepository
import des.c5inco.pokedexer.shared.data.pokemon.PokemonRepository
import des.c5inco.pokedexer.data.preferences.UserPreferencesRepository
import des.c5inco.pokedexer.shared.model.Ability
import des.c5inco.pokedexer.shared.model.EvolutionTrigger
import des.c5inco.pokedexer.shared.model.Item
import des.c5inco.pokedexer.shared.model.Move
import des.c5inco.pokedexer.shared.model.Pokemon
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
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
    @Assisted private val pokemonId: Int
) : ViewModel() {
    private val userPreferencesFlow = userPreferencesRepository.userPreferencesFlow
    private val activePokemonId = MutableStateFlow(pokemonId)
    val pokemonSet = pokemonRepository.pokemon()

    @AssistedFactory
    interface PokemonDetailsViewModelFactory {
        fun create(pokemonId: Int): PokemonDetailsViewModel
    }

    val uiState: StateFlow<PokemonDetailsUiState?> =
        combine(
            activePokemonId.flatMapLatest { pokemonRepository.getPokemonById(it) }.filterNotNull(),
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
            initialValue = null
        )

    fun refresh(incomingPokemon: Pokemon) {
        activePokemonId.value = incomingPokemon.id
    }

    fun toggleFavorite(pokemonId: Int) {
        viewModelScope.launch {
            userPreferencesRepository.updateFavorites(pokemonId)
        }
    }
}
