package des.c5inco.pokedexer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import des.c5inco.pokedexer.data.abilities.AbilitiesRepository
import des.c5inco.pokedexer.data.items.ItemsRepository
import des.c5inco.pokedexer.data.moves.MovesRepository
import des.c5inco.pokedexer.data.pokemon.PokemonRepository
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.launch

@Inject
class RootViewModel(
    private val pokemonRepository: PokemonRepository,
    private val movesRepository: MovesRepository,
    private val itemsRepository: ItemsRepository,
    private val abilitiesRepository: AbilitiesRepository
): ViewModel() {
    init {
        viewModelScope.launch {
            println("Populating databases...")

            launch { pokemonRepository.updatePokemon() }
            launch { movesRepository.updateMoves() }
            launch { itemsRepository.updateItems() }
            launch { abilitiesRepository.updateAbilities() }
        }
    }
}