package des.c5inco.pokedexer.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import des.c5inco.pokedexer.data.PokemonDatabase
import des.c5inco.pokedexer.data.items.ItemsDao

@InstallIn(SingletonComponent::class)
@Module
object ItemsDatabaseModule {

    @Provides
    fun provideItemsDao(database: PokemonDatabase): ItemsDao {
        return database.itemsDao()
    }
}