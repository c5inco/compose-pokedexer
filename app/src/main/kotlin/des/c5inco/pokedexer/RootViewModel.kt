package des.c5inco.pokedexer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.exception.ApolloException
import des.c5inco.pokedexer.shared.data.abilities.AbilitiesRepository
import des.c5inco.pokedexer.shared.data.items.ItemsRepository
import des.c5inco.pokedexer.shared.data.pokemon.GenerationLoader
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.launch

@Inject
class RootViewModel(
    private val generationLoader: GenerationLoader,
    private val itemsRepository: ItemsRepository,
    private val abilitiesRepository: AbilitiesRepository,
) : ViewModel() {
    init {
        viewModelScope.launch {
            println("Populating databases...")

            generationLoader.start()
            launch { runDataUpdateSafely("items") { itemsRepository.updateItems() } }
            launch { abilitiesRepository.updateAbilities() }
        }
    }
}

internal suspend fun runDataUpdateSafely(name: String, update: suspend () -> Unit) {
    try {
        update()
    } catch (exception: ApolloException) {
        println("Failed to update $name: ${exception.message}")
    }
}
