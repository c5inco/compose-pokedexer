package des.c5inco.pokedexer.shared

import des.c5inco.pokedexer.shared.data.abilities.AbilitiesRepository
import des.c5inco.pokedexer.shared.data.abilities.AbilitiesRepositoryImpl
import des.c5inco.pokedexer.shared.data.items.ItemsRepository
import des.c5inco.pokedexer.shared.data.items.ItemsRepositoryImpl
import des.c5inco.pokedexer.shared.data.moves.MovesRepository
import des.c5inco.pokedexer.shared.data.moves.MovesRepositoryImpl
import des.c5inco.pokedexer.shared.data.pokemon.PokemonRepository
import des.c5inco.pokedexer.shared.data.pokemon.PokemonRepositoryImpl
import des.c5inco.pokedexer.shared.data.preferences.UserPreferencesRepository
import des.c5inco.pokedexer.shared.data.preferences.UserPreferencesRepositoryImpl
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

class SharedModule(
    private val databaseFactory: DatabaseFactory,
    private val dataStore: DataStore<Preferences>
) {
    private val apolloClient by lazy { ApolloClientFactory.create() }
    private val database by lazy { databaseFactory.createDatabase() }

    val pokemonRepository: PokemonRepository by lazy {
        PokemonRepositoryImpl(database.pokemonDao(), apolloClient)
    }

    val movesRepository: MovesRepository by lazy {
        MovesRepositoryImpl(database.movesDao(), apolloClient)
    }

    val itemsRepository: ItemsRepository by lazy {
        ItemsRepositoryImpl(database.itemsDao(), apolloClient)
    }

    val abilitiesRepository: AbilitiesRepository by lazy {
        AbilitiesRepositoryImpl(database.abilitiesDao(), apolloClient)
    }

    val userPreferencesRepository: UserPreferencesRepository by lazy {
        UserPreferencesRepositoryImpl(dataStore)
    }

    fun createPokemonListViewModel(scope: kotlinx.coroutines.CoroutineScope): CommonPokemonListViewModel {
        return CommonPokemonListViewModel(pokemonRepository, scope)
    }
}
