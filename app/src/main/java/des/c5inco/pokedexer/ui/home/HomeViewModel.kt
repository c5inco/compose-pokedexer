package des.c5inco.pokedexer.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import des.c5inco.pokedexer.data.Result
import des.c5inco.pokedexer.data.pokemon.PokemonRepository
import des.c5inco.pokedexer.model.Pokemon
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class HomeUiState(
    val searchResults: List<Pokemon> = listOf(),
    val loading: Boolean = false,
)

@OptIn(ExperimentalFoundationApi::class, ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
): ViewModel() {
    var uiState by mutableStateOf(HomeUiState(loading = true))
        private set

    val searchText = TextFieldState()

    val searchResults: StateFlow<List<Pokemon>> =
        searchText.textAsFlow()
            .debounce(1000)
            .mapLatest {
                val results = pokemonRepository.getPokemonByName(it.toString())
                if (results is Result.Success) {
                    results.data
                } else {
                    emptyList()
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(0),
                initialValue = emptyList()
            )
}