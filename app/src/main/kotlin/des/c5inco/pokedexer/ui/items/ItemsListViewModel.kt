package des.c5inco.pokedexer.ui.items

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import des.c5inco.pokedexer.data.Result
import des.c5inco.pokedexer.data.items.ItemsRepository
import des.c5inco.pokedexer.model.Item
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ItemsListUiState(
    val items: List<Item> = listOf(),
    val loading: Boolean = false,
)

@HiltViewModel
class ItemsListViewModel @Inject constructor(
    private val itemsRepository: ItemsRepository
): ViewModel() {
    var uiState by mutableStateOf(ItemsListUiState(loading = true))
        private set

    init {
        refresh()
    }

    /**
     * Refresh items list
     */
    fun refresh() {
        viewModelScope.launch {
            // TODO: Handle error/exception better
            when (val result = itemsRepository.getAllItems()) {
                is Result.Success -> {
                    uiState = uiState.copy(
                        loading = false,
                        items = result.data
                    )
                }
                is Result.Error -> {
                    throw result.exception
                }
            }
        }
    }
}