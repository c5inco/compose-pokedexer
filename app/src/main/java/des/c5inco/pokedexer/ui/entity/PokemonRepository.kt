package des.c5inco.pokedexer.ui.entity

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo3.ApolloClient
import des.c5inco.pokedexer.PokemonOriginalQuery
import des.c5inco.pokedexer.ui.entity.Pokemon as PokemonViewModel

class PokemonRepository {
    private val apolloClient = ApolloClient.Builder()
        .serverUrl("https://beta.pokeapi.co/graphql/v1beta")
        .build()

    suspend fun getPokemon(): List<PokemonViewModel> {
        val response = apolloClient.query(PokemonOriginalQuery()).execute()
        return response.dataAssertNoErrors.pokemon.map { model ->
            val detail = model.detail.first()
            val stats = detail.stats.map { it.base_stat }

            PokemonViewModel(
                id = model.id.toString(),
                name = model.name,
                description = model.description.first().flavor_text,
                typeOfPokemon = detail.types.map { it.type!!.name },
                hp = stats[0],
                attack = stats[1],
                defense = stats[2],
                specialAttack = stats[3],
                specialDefense = stats[4],
                speed = stats[5],
                total = stats.sum()
            )
        }
    }
}

class PokemonDataSource(private val repository: PokemonRepository) : PagingSource<Int, PokemonViewModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonViewModel> {
        val pokemons = repository.getPokemon()
        return LoadResult.Page(data = pokemons, prevKey = null, nextKey = null)
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonViewModel>): Int? {
        return null
    }
}