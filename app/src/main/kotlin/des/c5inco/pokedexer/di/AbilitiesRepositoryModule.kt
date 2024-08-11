package des.c5inco.pokedexer.di

import com.apollographql.apollo3.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import des.c5inco.pokedexer.data.abilities.AbilitiesDao
import des.c5inco.pokedexer.data.abilities.AbilitiesRepository
import des.c5inco.pokedexer.data.abilities.AbilitiesRepositoryImpl

@InstallIn(SingletonComponent::class)
@Module
object RemoteAbilitiesRepositoryModule {

    @Provides
    fun provideAbilitiesRepository(
        abilitiesDao: AbilitiesDao,
        apolloClient: ApolloClient
    ): AbilitiesRepository {
        return AbilitiesRepositoryImpl(abilitiesDao, apolloClient)
    }
}