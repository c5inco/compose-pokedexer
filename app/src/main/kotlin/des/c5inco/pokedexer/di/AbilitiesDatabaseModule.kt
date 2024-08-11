package des.c5inco.pokedexer.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import des.c5inco.pokedexer.data.PokemonDatabase
import des.c5inco.pokedexer.data.abilities.AbilitiesDao

@InstallIn(SingletonComponent::class)
@Module
object AbilitiesDatabaseModule {

    @Provides
    fun provideAbilitiesDao(database: PokemonDatabase): AbilitiesDao {
        return database.abilitiesDao()
    }
}