package des.c5inco.pokedexer.ui.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import des.c5inco.pokedexer.data.items.ItemsRepository
import des.c5inco.pokedexer.model.Item
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed interface ItemsListUiState {
    data object Loading : ItemsListUiState
    data class Ready(
        val items: List<Item>,
    ) : ItemsListUiState
}

@HiltViewModel
class ItemsListViewModel @Inject constructor(
    itemsRepository: ItemsRepository
): ViewModel() {
    val state: StateFlow<ItemsListUiState> =
        combine(itemsRepository.items()) { items ->
            delay(500)
            ItemsListUiState.Ready(items.first())
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ItemsListUiState.Loading
        )
}