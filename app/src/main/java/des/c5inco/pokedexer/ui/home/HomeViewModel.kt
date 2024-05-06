package des.c5inco.pokedexer.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import des.c5inco.pokedexer.data.Result
import des.c5inco.pokedexer.data.pokemon.PokemonRepository
import des.c5inco.pokedexer.model.Item
import des.c5inco.pokedexer.model.Move
import des.c5inco.pokedexer.model.Pokemon
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class SearchResponse(
    val currentText: String,
    val foundPokemon: List<Pokemon> = emptyList(),
    val foundMoves: List<Move> = emptyList(),
    val foundItems: List<Item> = emptyList(),
)

@OptIn(ExperimentalFoundationApi::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
): ViewModel() {
    val searchText = TextFieldState()

    val loading by mutableStateOf(false)

    val foundPokemon: StateFlow<SearchResponse> =
        searchText.textAsFlow()
            .debounce(200)
            .mapLatest {
                val textContent = it.toString()
                if (textContent.isEmpty()) {
                    SearchResponse(
                        currentText = textContent,
                        foundPokemon = emptyList()
                    )
                } else {
                    when (val result = pokemonRepository.getPokemonByName(textContent)) {
                        is Result.Success -> {
                            SearchResponse(
                                currentText = textContent,
                                foundPokemon = result.data
                            )
                        }

                        is Result.Error -> {
                            throw result.exception
                        }
                    }
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SearchResponse(
                    currentText = ""
                )
            )
}