package des.c5inco.pokedexer.di

import com.apollographql.apollo3.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import des.c5inco.pokedexer.data.items.ItemsRepository
import des.c5inco.pokedexer.data.items.SharedItemsRepository
import des.c5inco.pokedexer.shared.data.db.ItemsDao as SharedItemsDao

@InstallIn(SingletonComponent::class)
@Module
object RemoteItemsRepositoryModule {

    /**
     * Provides the Items repository using the shared KMP database.
     */
    @Provides
    fun provideItemsRepository(
        sharedItemsDao: SharedItemsDao,
        apolloClient: ApolloClient
    ): ItemsRepository {
        return SharedItemsRepository(sharedItemsDao, apolloClient)
    }
}