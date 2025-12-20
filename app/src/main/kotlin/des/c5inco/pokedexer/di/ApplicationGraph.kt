package des.c5inco.pokedexer.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.apollographql.apollo3.ApolloClient
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import des.c5inco.pokedexer.data.PokemonDatabase
import des.c5inco.pokedexer.data.abilities.AbilitiesDao
import des.c5inco.pokedexer.data.abilities.AbilitiesRepository
import des.c5inco.pokedexer.data.abilities.AbilitiesRepositoryImpl
import des.c5inco.pokedexer.data.items.ItemsDao
import des.c5inco.pokedexer.data.items.ItemsRepository
import des.c5inco.pokedexer.data.items.ItemsRepositoryImpl
import des.c5inco.pokedexer.data.moves.MovesDao
import des.c5inco.pokedexer.data.moves.MovesRepository
import des.c5inco.pokedexer.data.moves.RemoteMovesRepository
import des.c5inco.pokedexer.data.pokemon.PokemonDao
import des.c5inco.pokedexer.data.pokemon.PokemonRepository
import des.c5inco.pokedexer.data.pokemon.RemotePokemonRepository
import des.c5inco.pokedexer.data.preferences.UserPreferencesRepository
import des.c5inco.pokedexer.RootViewModel
import des.c5inco.pokedexer.ui.home.HomeViewModel
import des.c5inco.pokedexer.ui.items.ItemsViewModel
import des.c5inco.pokedexer.ui.moves.MovesListViewModel
import des.c5inco.pokedexer.ui.pokedex.PokedexViewModel
import des.c5inco.pokedexer.ui.pokedex.PokemonDetailsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

private const val USER_PREFERENCES = "user_preferences"

/**
 * Scope annotation for application-level singletons.
 */
@dev.zacsweers.metro.Scope
annotation class AppScope

/**
 * Interface containing all provider methods for the application.
 */
interface ApplicationModule {
    // Database
    @Provides
    @SingleIn(AppScope::class)
    fun provideDatabase(context: Context): PokemonDatabase {
        return Room
            .databaseBuilder(
                context,
                PokemonDatabase::class.java,
                "pokemon.db"
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providePokemonDao(database: PokemonDatabase): PokemonDao {
        return database.pokemonDao()
    }

    @Provides
    fun provideMovesDao(database: PokemonDatabase): MovesDao {
        return database.movesDao()
    }

    @Provides
    fun provideItemsDao(database: PokemonDatabase): ItemsDao {
        return database.itemsDao()
    }

    @Provides
    fun provideAbilitiesDao(database: PokemonDatabase): AbilitiesDao {
        return database.abilitiesDao()
    }

    // Network
    @Provides
    @SingleIn(AppScope::class)
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("https://beta.pokeapi.co/graphql/v1beta")
            .build()
    }

    // DataStore
    @Provides
    @SingleIn(AppScope::class)
    fun providePreferencesDataStore(context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(context, USER_PREFERENCES)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { context.preferencesDataStoreFile(USER_PREFERENCES) }
        )
    }

    // Repositories
    @Provides
    fun providePokemonRepository(
        pokemonDao: PokemonDao,
        apolloClient: ApolloClient
    ): PokemonRepository {
        return RemotePokemonRepository(pokemonDao, apolloClient)
    }

    @Provides
    fun provideMovesRepository(
        movesDao: MovesDao,
        apolloClient: ApolloClient
    ): MovesRepository {
        return RemoteMovesRepository(movesDao, apolloClient)
    }

    @Provides
    fun provideItemsRepository(
        itemsDao: ItemsDao,
        apolloClient: ApolloClient
    ): ItemsRepository {
        return ItemsRepositoryImpl(itemsDao, apolloClient)
    }

    @Provides
    fun provideAbilitiesRepository(
        abilitiesDao: AbilitiesDao,
        apolloClient: ApolloClient
    ): AbilitiesRepository {
        return AbilitiesRepositoryImpl(abilitiesDao, apolloClient)
    }

    @Provides
    fun provideUserPreferencesRepository(
        dataStore: DataStore<Preferences>
    ): UserPreferencesRepository {
        return UserPreferencesRepository(dataStore)
    }
}

/**
 * Main dependency graph for the application.
 * This replaces the Hilt SingletonComponent.
 */
@SingleIn(AppScope::class)
@DependencyGraph
interface ApplicationGraph : ApplicationModule {
    // ViewModels - exposed as accessors (for ViewModels without assisted params)
    val rootViewModel: RootViewModel
    val homeViewModel: HomeViewModel
    val movesListViewModel: MovesListViewModel
    val itemsViewModel: ItemsViewModel

    // Factories for assisted injection ViewModels
    val pokedexViewModelFactory: PokedexViewModel.Factory
    val pokemonDetailsViewModelFactory: PokemonDetailsViewModel.PokemonDetailsViewModelFactory

    /**
     * Factory to create the ApplicationGraph with external dependencies.
     */
    @DependencyGraph.Factory
    interface Factory {
        fun create(@Provides context: Context): ApplicationGraph
    }
}
