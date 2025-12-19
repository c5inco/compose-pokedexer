package des.c5inco.pokedexer.shared

import des.c5inco.pokedexer.shared.data.pokemon.PokemonRepository
import des.c5inco.pokedexer.shared.model.Pokemon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CommonPokemonListViewModel(
    private val repository: PokemonRepository,
    private val scope: CoroutineScope
) {
    private val _pokemon = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokemon: StateFlow<List<Pokemon>> = _pokemon.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        scope.launch {
            repository.pokemon().collect {
                _pokemon.value = it
            }
        }
    }

    fun refresh() {
        scope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                repository.updatePokemon()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}
