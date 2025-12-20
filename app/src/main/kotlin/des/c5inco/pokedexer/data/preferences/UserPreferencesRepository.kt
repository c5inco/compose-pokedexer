package des.c5inco.pokedexer.data.preferences

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

data class UserPreferences(
    val favorites: List<Int>
)

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    private val TAG: String = "UserPreferencesRepo"
    private object PreferencesKeys {
        val FAVORITES = stringPreferencesKey("favorites")
    }

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences.", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            mapUserPreferences(preferences)
        }

    suspend fun updateFavorites(pokemonId: Int) {
        dataStore.edit { preferences ->
            val userPreferences = mapUserPreferences(preferences)
            val newFavorites = userPreferences.favorites.toMutableList()

            if (newFavorites.contains(pokemonId)) {
                newFavorites.remove(pokemonId)
            } else {
                newFavorites.add(pokemonId)
            }

            println("FAVORITES: $newFavorites")
            preferences[PreferencesKeys.FAVORITES] = newFavorites.joinToString("|")
        }
    }

    suspend fun fetchInitialPreferences() = mapUserPreferences(dataStore.data.first().toPreferences())

    private fun mapUserPreferences(preferences: Preferences): UserPreferences {
        val favorites =
            preferences[PreferencesKeys.FAVORITES]
                ?.split("|")
                ?.filter { it.isNotBlank() }
                ?.map {
                    it.toInt()
                }

        return UserPreferences(favorites ?: emptyList())
    }
}