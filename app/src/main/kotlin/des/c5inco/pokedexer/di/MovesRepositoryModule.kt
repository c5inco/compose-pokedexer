package des.c5inco.pokedexer.di

import com.apollographql.apollo3.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import des.c5inco.pokedexer.data.moves.MovesRepository
import des.c5inco.pokedexer.data.moves.SharedMovesRepository
import des.c5inco.pokedexer.shared.data.db.MovesDao as SharedMovesDao

@InstallIn(SingletonComponent::class)
@Module
object RemoteMovesRepositoryModule {

    /**
     * Provides the Moves repository using the shared KMP database.
     */
    @Provides
    fun provideMovesRepository(
        sharedMovesDao: SharedMovesDao,
        apolloClient: ApolloClient
    ): MovesRepository {
        return SharedMovesRepository(sharedMovesDao, apolloClient)
    }
}
