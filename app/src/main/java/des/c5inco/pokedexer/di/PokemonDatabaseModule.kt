package des.c5inco.pokedexer.di

import android.content.Context
import androidx.room.Room
import com.apollographql.apollo3.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import des.c5inco.pokedexer.data.pokemon.PokemonDao
import des.c5inco.pokedexer.data.pokemon.PokemonDatabase
import des.c5inco.pokedexer.data.pokemon.PokemonRepository
import des.c5inco.pokedexer.data.pokemon.RemotePokemonRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PokemonDatabaseModule {

    @Provides
    fun providePokemonDao(database: PokemonDatabase): PokemonDao {
        return database.pokemonDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): PokemonDatabase {
        return Room.databaseBuilder(
            appContext,
            PokemonDatabase::class.java,
            "pokemon.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideApolloClient() : ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("https://beta.pokeapi.co/graphql/v1beta")
            .build()
    }

    @Provides
    fun providePokemonRepository(
        pokemonDao: PokemonDao,
        apolloClient: ApolloClient
    ): PokemonRepository {
        return RemotePokemonRepository(pokemonDao, apolloClient)
    }
}