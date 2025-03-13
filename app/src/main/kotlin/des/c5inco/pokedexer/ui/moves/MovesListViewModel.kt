package des.c5inco.pokedexer.ui.moves

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import des.c5inco.pokedexer.data.moves.MovesRepository
import des.c5inco.pokedexer.model.Move
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed interface MovesListUiState {
    data object Loading : MovesListUiState
    data class Ready(
        val moves: List<Move>,
    ) : MovesListUiState
}

@HiltViewModel
class MovesListViewModel @Inject constructor(
    movesRepository: MovesRepository
): ViewModel() {
    val state: StateFlow<MovesListUiState> =
        movesRepository.moves().map {
            delay(500)
            MovesListUiState.Ready(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MovesListUiState.Loading
        )
}
