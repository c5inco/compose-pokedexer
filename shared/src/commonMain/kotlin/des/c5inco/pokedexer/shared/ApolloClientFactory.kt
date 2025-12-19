package des.c5inco.pokedexer.shared

import com.apollographql.apollo3.ApolloClient

object ApolloClientFactory {
    private var instance: ApolloClient? = null

    fun create(): ApolloClient {
        return instance ?: ApolloClient.Builder()
            .serverUrl("https://beta.pokeapi.co/graphql/v1beta")
            .build()
            .also { instance = it }
    }
}
