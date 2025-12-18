package des.c5inco.pokedexer.di

import com.apollographql.apollo3.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import des.c5inco.pokedexer.data.pokemon.PokemonRepository
import des.c5inco.pokedexer.data.pokemon.SharedPokemonRepository
import des.c5inco.pokedexer.shared.data.db.PokemonDao as SharedPokemonDao

@InstallIn(SingletonComponent::class)
@Module
object RemotePokemonRepositoryModule {

    /**
     * Provides the Pokemon repository using the shared KMP database.
     * This enables code sharing between Android and iOS.
     */
    @Provides
    fun providePokemonRepository(
        sharedPokemonDao: SharedPokemonDao,
        apolloClient: ApolloClient
    ): PokemonRepository {
        return SharedPokemonRepository(sharedPokemonDao, apolloClient)
    }
}
