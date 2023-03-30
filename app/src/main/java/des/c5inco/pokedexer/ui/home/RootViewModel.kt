package des.c5inco.pokedexer.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import des.c5inco.pokedexer.data.Result
import des.c5inco.pokedexer.data.moves.MovesRepository
import des.c5inco.pokedexer.data.pokemon.PokemonRepository
import des.c5inco.pokedexer.model.Move
import des.c5inco.pokedexer.model.Pokemon
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    val imageLoader: ImageLoader,
    private val pokemonRepository: PokemonRepository,
    private val movesRepository: MovesRepository
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
        }
    }
}