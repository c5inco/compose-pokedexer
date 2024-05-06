package des.c5inco.pokedexer.di

import com.apollographql.apollo3.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import des.c5inco.pokedexer.data.moves.MovesDao
import des.c5inco.pokedexer.data.moves.MovesRepository
import des.c5inco.pokedexer.data.moves.RemoteMovesRepository

@InstallIn(SingletonComponent::class)
@Module
object RemoteMovesRepositoryModule {

    @Provides
    fun provideMovesRepository(
        movesDao: MovesDao, apolloClient: ApolloClient
    ): MovesRepository {
        return RemoteMovesRepository(movesDao, apolloClient)
    }
}
