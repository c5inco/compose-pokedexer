package des.c5inco.pokedexer.shared.data.preferences

import kotlinx.coroutines.flow.Flow

/**
 * Interface for storing and retrieving favorite Pokemon IDs.
 * Platform-specific implementations will use native storage mechanisms:
 * - Android: DataStore Preferences
 * - iOS: UserDefaults
 */
interface FavoritesStore {
    /**
     * A flow that emits the current list of favorite Pokemon IDs.
     */
    val favoritesFlow: Flow<List<Int>>

    /**
     * Toggles a Pokemon's favorite status.
     * If the Pokemon is already a favorite, it will be removed.
     * If it's not a favorite, it will be added.
     */
    suspend fun toggleFavorite(pokemonId: Int)

    /**
     * Gets the current list of favorites synchronously.
     */
    suspend fun getFavorites(): List<Int>
}

