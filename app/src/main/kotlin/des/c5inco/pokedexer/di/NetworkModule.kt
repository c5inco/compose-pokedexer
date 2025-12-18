package des.c5inco.pokedexer.di

import com.apollographql.apollo3.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import des.c5inco.pokedexer.shared.data.ApolloClientProvider
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    /**
     * Provides the Apollo client from the shared KMP module.
     * This ensures both Android and iOS use the same GraphQL configuration.
     */
    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        return ApolloClientProvider.apolloClient
    }
}