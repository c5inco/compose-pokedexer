package des.c5inco.pokedexer.shared.data

import com.apollographql.apollo3.ApolloClient

/**
 * Provides a shared Apollo client for accessing the PokeAPI GraphQL endpoint.
 */
object ApolloClientProvider {
    private const val POKEAPI_GRAPHQL_URL = "https://beta.pokeapi.co/graphql/v1beta"

    val apolloClient: ApolloClient by lazy {
        ApolloClient.Builder()
            .serverUrl(POKEAPI_GRAPHQL_URL)
            .build()
    }
}

