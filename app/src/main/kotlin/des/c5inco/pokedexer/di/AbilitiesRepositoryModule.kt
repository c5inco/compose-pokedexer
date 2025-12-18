package des.c5inco.pokedexer.di

import com.apollographql.apollo3.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import des.c5inco.pokedexer.data.abilities.AbilitiesRepository
import des.c5inco.pokedexer.data.abilities.SharedAbilitiesRepository
import des.c5inco.pokedexer.shared.data.db.AbilitiesDao as SharedAbilitiesDao

@InstallIn(SingletonComponent::class)
@Module
object RemoteAbilitiesRepositoryModule {

    /**
     * Provides the Abilities repository using the shared KMP database.
     */
    @Provides
    fun provideAbilitiesRepository(
        sharedAbilitiesDao: SharedAbilitiesDao,
        apolloClient: ApolloClient
    ): AbilitiesRepository {
        return SharedAbilitiesRepository(sharedAbilitiesDao, apolloClient)
    }
}