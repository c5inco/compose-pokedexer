package des.c5inco.pokedexer.di

import com.apollographql.apollo3.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import des.c5inco.pokedexer.data.items.ItemsDao
import des.c5inco.pokedexer.data.items.ItemsRepository
import des.c5inco.pokedexer.data.items.ItemsRepositoryImpl

@InstallIn(SingletonComponent::class)
@Module
object RemoteItemsRepositoryModule {

    @Provides
    fun provideItemsRepository(
        itemsDao: ItemsDao,
        apolloClient: ApolloClient
    ): ItemsRepository {
        return ItemsRepositoryImpl(itemsDao, apolloClient)
    }
}