package des.c5inco.pokedexer.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import coil.ImageLoader
import coil.decode.ImageDecoderDecoder
import com.apollographql.apollo3.ApolloClient
import des.c5inco.pokedexer.RootViewModel
import des.c5inco.pokedexer.shared.data.PokemonDatabase
import des.c5inco.pokedexer.shared.data.abilities.AbilitiesDao
import des.c5inco.pokedexer.shared.data.abilities.AbilitiesRepository
import des.c5inco.pokedexer.shared.data.abilities.AbilitiesRepositoryImpl
import des.c5inco.pokedexer.shared.data.getDatabaseBuilder
import des.c5inco.pokedexer.shared.data.items.ItemsDao
import des.c5inco.pokedexer.shared.data.items.ItemsRepository
import des.c5inco.pokedexer.shared.data.items.ItemsRepositoryImpl
import des.c5inco.pokedexer.shared.data.moves.MovesDao
import des.c5inco.pokedexer.shared.data.moves.MovesRepository
import des.c5inco.pokedexer.shared.data.moves.RemoteMovesRepository
import des.c5inco.pokedexer.shared.data.pokemon.PokemonDao
import des.c5inco.pokedexer.shared.data.pokemon.PokemonRepository
import des.c5inco.pokedexer.shared.data.pokemon.RemotePokemonRepository
import des.c5inco.pokedexer.ui.home.HomeViewModel
import des.c5inco.pokedexer.ui.items.ItemsViewModel
import des.c5inco.pokedexer.ui.moves.MovesListViewModel
import des.c5inco.pokedexer.ui.pokedex.PokedexViewModel
import des.c5inco.pokedexer.ui.pokedex.PokemonDetailsViewModel
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
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
 * Interface containing all provider methods for third-party dependencies.
 */
@ContributesTo(AppScope::class)
interface ApplicationModule {
    // Database (from shared module)
    @Provides
    @SingleIn(AppScope::class)
    fun provideDatabase(context: Context): PokemonDatabase {
        return getDatabaseBuilder(context)
            .fallbackToDestructiveMigration(dropAllTables = true)
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

    // Repositories (from shared module - manually wired since we don't use @ContributesBinding in shared)
    @Provides
    @SingleIn(AppScope::class)
    fun providePokemonRepository(
        pokemonDao: PokemonDao,
        apolloClient: ApolloClient
    ): PokemonRepository {
        return RemotePokemonRepository(pokemonDao, apolloClient)
    }

    @Provides
    @SingleIn(AppScope::class)
    fun provideMovesRepository(
        movesDao: MovesDao,
        apolloClient: ApolloClient
    ): MovesRepository {
        return RemoteMovesRepository(movesDao, apolloClient)
    }

    @Provides
    @SingleIn(AppScope::class)
    fun provideItemsRepository(
        itemsDao: ItemsDao,
        apolloClient: ApolloClient
    ): ItemsRepository {
        return ItemsRepositoryImpl(itemsDao, apolloClient)
    }

    @Provides
    @SingleIn(AppScope::class)
    fun provideAbilitiesRepository(
        abilitiesDao: AbilitiesDao,
        apolloClient: ApolloClient
    ): AbilitiesRepository {
        return AbilitiesRepositoryImpl(abilitiesDao, apolloClient)
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

    // Image Loading
    @Provides
    @SingleIn(AppScope::class)
    fun provideGifImageLoader(context: Context): ImageLoader {
        return ImageLoader.Builder(context)
            .components {
                add(ImageDecoderDecoder.Factory())
            }
            .build()
    }
}

/**
 * Main dependency graph for the application.
 * This replaces the Hilt SingletonComponent.
 */
@SingleIn(AppScope::class)
@DependencyGraph(AppScope::class)
interface ApplicationGraph {
    // ViewModels - exposed as accessors (for ViewModels without assisted params)
    val rootViewModel: RootViewModel
    val homeViewModel: HomeViewModel
    val movesListViewModel: MovesListViewModel
    val itemsViewModel: ItemsViewModel

    // Factories for assisted injection ViewModels
    val pokedexViewModelFactory: PokedexViewModel.Factory
    val pokemonDetailsViewModelFactory: PokemonDetailsViewModel.PokemonDetailsViewModelFactory

    // Image loading
    val gifImageLoader: ImageLoader

    /**
     * Factory to create the ApplicationGraph with external dependencies.
     */
    @DependencyGraph.Factory
    interface Factory {
        fun create(@Provides context: Context): ApplicationGraph
    }
}
