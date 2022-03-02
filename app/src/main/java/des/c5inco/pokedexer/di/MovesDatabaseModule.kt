package des.c5inco.pokedexer.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import des.c5inco.pokedexer.data.moves.MovesDao
import des.c5inco.pokedexer.data.pokemon.PokemonDatabase

@InstallIn(SingletonComponent::class)
@Module
object MovesDatabaseModule {

    @Provides
    fun provideMovesDao(database: PokemonDatabase): MovesDao {
        return database.movesDao()
    }
}