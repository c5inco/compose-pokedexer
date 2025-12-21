package des.c5inco.pokedexer.ui.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import des.c5inco.pokedexer.data.items.ItemsRepository
import des.c5inco.pokedexer.model.Item
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

sealed interface ItemsListUiState {
    data object Loading : ItemsListUiState
    data class Ready(
        val items: List<Item>,
    ) : ItemsListUiState
}

@Inject
class ItemsViewModel(
    itemsRepository: ItemsRepository
): ViewModel() {
    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<ItemsListUiState> =
        itemsRepository.items().mapLatest {
            delay(500)
            ItemsListUiState.Ready(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ItemsListUiState.Loading
        )
}