package des.c5inco.pokedexer.ui.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import des.c5inco.pokedexer.shared.data.items.ItemsRepository
import des.c5inco.pokedexer.shared.model.Item
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

private const val LOADING_DELAY_MILLIS = 500L
private const val SUBSCRIPTION_STOP_TIMEOUT_MILLIS = 5_000L

sealed interface ItemsListUiState {
    data object Loading : ItemsListUiState

    data class Ready(val items: List<Item>) : ItemsListUiState
}

@Inject
class ItemsViewModel(itemsRepository: ItemsRepository) : ViewModel() {
    val state: StateFlow<ItemsListUiState> =
        itemsRepository
            .items()
            .mapLatest {
                delay(LOADING_DELAY_MILLIS)
                ItemsListUiState.Ready(it)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(SUBSCRIPTION_STOP_TIMEOUT_MILLIS),
                initialValue = ItemsListUiState.Loading,
            )
}
