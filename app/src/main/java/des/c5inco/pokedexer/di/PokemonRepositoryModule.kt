package des.c5inco.pokedexer.di

import com.apollographql.apollo3.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import des.c5inco.pokedexer.data.pokemon.PokemonDao
import des.c5inco.pokedexer.data.pokemon.PokemonRepository
import des.c5inco.pokedexer.data.pokemon.RemotePokemonRepository

@InstallIn(SingletonComponent::class)
@Module
object RemotePokemonRepositoryModule {

    @Provides
    fun providePokemonRepository(
        pokemonDao: PokemonDao, apolloClient: ApolloClient
    ): PokemonRepository {
        return RemotePokemonRepository(pokemonDao, apolloClient)
    }
}
