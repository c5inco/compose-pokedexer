package des.c5inco.pokedexer.shared.data.preferences

import kotlinx.coroutines.flow.Flow

data class UserPreferences(
    val favorites: List<Int>
)

interface UserPreferencesRepository {
    val userPreferencesFlow: Flow<UserPreferences>
    suspend fun updateFavorites(pokemonId: Int)
    suspend fun fetchInitialPreferences(): UserPreferences
}
