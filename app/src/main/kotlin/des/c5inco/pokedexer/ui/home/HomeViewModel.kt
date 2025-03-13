package des.c5inco.pokedexer.ui.home

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import des.c5inco.pokedexer.data.items.ItemsRepository
import des.c5inco.pokedexer.data.moves.MovesRepository
import des.c5inco.pokedexer.data.pokemon.PokemonRepository
import des.c5inco.pokedexer.model.Item
import des.c5inco.pokedexer.model.Move
import des.c5inco.pokedexer.model.Pokemon
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class SearchResponse(
    val currentText: String = "",
    val foundPokemon: List<Pokemon> = emptyList(),
    val foundMoves: List<Move> = emptyList(),
    val foundItems: List<Item> = emptyList(),
)

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    private val movesRepository: MovesRepository,
    private val itemsRepository: ItemsRepository,
): ViewModel() {
    val searchText = TextFieldState()

    val searchResponses: StateFlow<SearchResponse> =
        snapshotFlow { searchText.text }
            .debounce(200)
            .mapLatest {
                val textContent = it.toString()
                if (textContent.isEmpty()) {
                    SearchResponse(
                        currentText = textContent,
                    )
                } else {
                    combine(
                        pokemonRepository.getPokemonByName(textContent),
                        movesRepository.getMovesByName(textContent),
                        itemsRepository.getItemsByName(textContent)
                    ) { pokemonResults, movesResults, itemsResults ->
                        SearchResponse(
                            currentText = textContent,
                            foundPokemon = pokemonResults,
                            foundMoves = movesResults,
                            foundItems = itemsResults
                        )
                    }.first()
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