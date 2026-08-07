package des.c5inco.pokedexer.ui.moves

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import des.c5inco.pokedexer.shared.data.moves.MovesRepository
import des.c5inco.pokedexer.shared.model.Move
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

private const val LOADING_DELAY_MILLIS = 500L
private const val SUBSCRIPTION_STOP_TIMEOUT_MILLIS = 5_000L

sealed interface MovesListUiState {
    data object Loading : MovesListUiState

    data class Ready(val moves: List<Move>) : MovesListUiState
}

@Inject
class MovesListViewModel(movesRepository: MovesRepository) : ViewModel() {
    val state: StateFlow<MovesListUiState> =
        movesRepository
            .moves()
            .mapLatest {
                delay(LOADING_DELAY_MILLIS)
                MovesListUiState.Ready(it)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(SUBSCRIPTION_STOP_TIMEOUT_MILLIS),
                initialValue = MovesListUiState.Loading,
            )
}
