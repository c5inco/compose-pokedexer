package des.c5inco.pokedexer.shared.data.preferences

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import platform.Foundation.NSUserDefaults

private const val FAVORITES_KEY = "pokemon_favorites"

/**
 * iOS implementation of FavoritesStore using NSUserDefaults.
 */
class UserDefaultsFavoritesStore : FavoritesStore {

    private val userDefaults = NSUserDefaults.standardUserDefaults

    private val _favoritesFlow = MutableStateFlow(loadFavorites())
    override val favoritesFlow: Flow<List<Int>> = _favoritesFlow.asStateFlow()

    override suspend fun toggleFavorite(pokemonId: Int) {
        val currentFavorites = loadFavorites().toMutableList()

        if (currentFavorites.contains(pokemonId)) {
            currentFavorites.remove(pokemonId)
        } else {
            currentFavorites.add(pokemonId)
        }

        saveFavorites(currentFavorites)
        _favoritesFlow.value = currentFavorites
    }

    override suspend fun getFavorites(): List<Int> {
        return loadFavorites()
    }

    private fun loadFavorites(): List<Int> {
        val favoritesString = userDefaults.stringForKey(FAVORITES_KEY) ?: return emptyList()
        return favoritesString
            .split("|")
            .filter { it.isNotBlank() }
            .mapNotNull { it.toIntOrNull() }
    }

    private fun saveFavorites(favorites: List<Int>) {
        val favoritesString = favorites.joinToString("|")
        userDefaults.setObject(favoritesString, FAVORITES_KEY)
        userDefaults.synchronize()
    }
}

/**
 * Factory function to create the iOS FavoritesStore.
 */
fun createFavoritesStore(): FavoritesStore {
    return UserDefaultsFavoritesStore()
}

