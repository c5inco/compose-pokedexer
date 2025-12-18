package des.c5inco.pokedexer.shared.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

/**
 * Android implementation of FavoritesStore using DataStore Preferences.
 */
class DataStoreFavoritesStore(
    private val context: Context
) : FavoritesStore {

    private object PreferencesKeys {
        val FAVORITES = stringPreferencesKey("favorites")
    }

    override val favoritesFlow: Flow<List<Int>> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            parseFavorites(preferences[PreferencesKeys.FAVORITES])
        }

    override suspend fun toggleFavorite(pokemonId: Int) {
        context.dataStore.edit { preferences ->
            val currentFavorites = parseFavorites(preferences[PreferencesKeys.FAVORITES]).toMutableList()

            if (currentFavorites.contains(pokemonId)) {
                currentFavorites.remove(pokemonId)
            } else {
                currentFavorites.add(pokemonId)
            }

            preferences[PreferencesKeys.FAVORITES] = currentFavorites.joinToString("|")
        }
    }

    override suspend fun getFavorites(): List<Int> {
        return parseFavorites(context.dataStore.data.first()[PreferencesKeys.FAVORITES])
    }

    private fun parseFavorites(favoritesString: String?): List<Int> {
        return favoritesString
            ?.split("|")
            ?.filter { it.isNotBlank() }
            ?.map { it.toInt() }
            ?: emptyList()
    }
}

/**
 * Factory function to create the Android FavoritesStore.
 */
fun createFavoritesStore(context: Context): FavoritesStore {
    return DataStoreFavoritesStore(context)
}

