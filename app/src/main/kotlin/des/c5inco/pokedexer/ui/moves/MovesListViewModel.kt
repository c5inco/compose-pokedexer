package des.c5inco.pokedexer.ui.moves

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import des.c5inco.pokedexer.data.Result
import des.c5inco.pokedexer.data.moves.MovesRepository
import des.c5inco.pokedexer.model.Move
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UI state for the Moves List screen
 */
data class MovesListUiState(
    val moves: List<Move> = listOf(),
    val loading: Boolean = false,
)

@HiltViewModel
class MovesListViewModel @Inject constructor(
    private val movesRepository: MovesRepository
): ViewModel() {
    var uiState by mutableStateOf(MovesListUiState(loading = true))
        private set

    init {
        refresh()
    }

    /**
     * Refresh Moves list
     */
    fun refresh() {
        viewModelScope.launch {
            // TODO: Handle error/exception better
            when (val result = movesRepository.getAllMoves()) {
                is Result.Success -> {
                    uiState = uiState.copy(
                        loading = false,
                        moves = result.data
                    )
                }
                is Result.Error -> {
                    throw result.exception
                }
            }
        }
    }
}
