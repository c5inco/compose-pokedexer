package des.c5inco.pokedexer.shared.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserPreferencesRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : UserPreferencesRepository {
    private object PreferencesKeys {
        val FAVORITES = stringPreferencesKey("favorites")
    }

    override val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            println("Error reading preferences: ${exception.message}")
            emit(emptyPreferences())
        }.map { preferences ->
            mapUserPreferences(preferences)
        }

    override suspend fun updateFavorites(pokemonId: Int) {
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

    override suspend fun fetchInitialPreferences() = mapUserPreferences(dataStore.data.first())

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
