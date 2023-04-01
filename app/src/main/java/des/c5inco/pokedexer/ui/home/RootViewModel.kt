package des.c5inco.pokedexer.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import des.c5inco.pokedexer.data.Result
import des.c5inco.pokedexer.data.items.ItemsRepository
import des.c5inco.pokedexer.data.moves.MovesRepository
import des.c5inco.pokedexer.data.pokemon.PokemonRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    val imageLoader: ImageLoader,
    private val pokemonRepository: PokemonRepository,
    private val movesRepository: MovesRepository,
    private val itemsRepository: ItemsRepository,
): ViewModel() {
    init {
        viewModelScope.launch {
            println("Populating databases...")

            when(val pokemonResults = pokemonRepository.getAllPokemon()) {
                is Result.Success -> {
                    println("Pokemon database: ${pokemonResults.data.size}")
                }
                is Result.Error -> {
                    throw pokemonResults.exception
                }
            }

            when(val movesResults = movesRepository.getAllMoves()) {
                is Result.Success -> {
                    println("Moves database: ${movesResults.data.size}")
                }
                is Result.Error -> {
                    throw movesResults.exception
                }
            }

            when(val itemsResults = itemsRepository.getAllItems()) {
                is Result.Success -> {
                    println("Items database: ${itemsResults.data.size}")
                }
                is Result.Error -> {
                    throw itemsResults.exception
                }
            }
        }
    }
}