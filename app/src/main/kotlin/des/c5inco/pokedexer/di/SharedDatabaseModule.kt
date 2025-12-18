package des.c5inco.pokedexer.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import des.c5inco.pokedexer.shared.data.db.AbilitiesDao
import des.c5inco.pokedexer.shared.data.db.ItemsDao
import des.c5inco.pokedexer.shared.data.db.MovesDao
import des.c5inco.pokedexer.shared.data.db.PokedexerDatabase
import des.c5inco.pokedexer.shared.data.db.PokemonDao
import des.c5inco.pokedexer.shared.data.db.initializeDatabase
import des.c5inco.pokedexer.shared.data.db.getDatabaseBuilder
import javax.inject.Singleton

/**
 * Hilt module that provides the shared KMP database and DAOs.
 * This allows Android to use the same database layer as iOS.
 */
@InstallIn(SingletonComponent::class)
@Module
object SharedDatabaseModule {

    @Provides
    @Singleton
    fun provideSharedDatabase(@ApplicationContext appContext: Context): PokedexerDatabase {
        // Initialize the database builder with the Android context
        initializeDatabase(appContext)
        
        return getDatabaseBuilder()
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    @Provides
    fun provideSharedPokemonDao(database: PokedexerDatabase): PokemonDao {
        return database.pokemonDao()
    }

    @Provides
    fun provideSharedMovesDao(database: PokedexerDatabase): MovesDao {
        return database.movesDao()
    }

    @Provides
    fun provideSharedItemsDao(database: PokedexerDatabase): ItemsDao {
        return database.itemsDao()
    }

    @Provides
    fun provideSharedAbilitiesDao(database: PokedexerDatabase): AbilitiesDao {
        return database.abilitiesDao()
    }
}

