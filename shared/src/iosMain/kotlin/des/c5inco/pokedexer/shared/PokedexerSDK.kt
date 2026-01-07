package des.c5inco.pokedexer.shared

import com.apollographql.apollo3.ApolloClient
import des.c5inco.pokedexer.shared.data.PokemonDatabase
import des.c5inco.pokedexer.shared.data.abilities.AbilitiesRepositoryImpl
import des.c5inco.pokedexer.shared.data.getDatabaseBuilder
import des.c5inco.pokedexer.shared.data.items.ItemsRepositoryImpl
import des.c5inco.pokedexer.shared.data.moves.RemoteMovesRepository
import des.c5inco.pokedexer.shared.data.pokemon.RemotePokemonRepository
import des.c5inco.pokedexer.shared.model.Ability
import des.c5inco.pokedexer.shared.model.Generation
import des.c5inco.pokedexer.shared.model.Item
import des.c5inco.pokedexer.shared.model.Move
import des.c5inco.pokedexer.shared.model.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Main entry point for iOS to access the shared Pokedex functionality.
 * This SDK provides access to Pokemon, Moves, Items, and Abilities data.
 *
 * Use [PokedexerSDK.create] to instantiate asynchronously off the main thread.
 */
class PokedexerSDK private constructor(
    private val database: PokemonDatabase,
    private val apolloClient: ApolloClient,
    private val pokemonRepository: RemotePokemonRepository,
    private val movesRepository: RemoteMovesRepository,
    private val itemsRepository: ItemsRepositoryImpl,
    private val abilitiesRepository: AbilitiesRepositoryImpl
) {
    companion object {
        /**
         * Creates a PokedexerSDK instance asynchronously.
         * Database and client initialization happens on Dispatchers.IO
         * to avoid blocking the main thread.
         */
        suspend fun create(): PokedexerSDK = withContext(Dispatchers.IO) {
            val database = getDatabaseBuilder()
                .fallbackToDestructiveMigration(dropAllTables = true)
                .build()

            val apolloClient = ApolloClient.Builder()
                .serverUrl("https://beta.pokeapi.co/graphql/v1beta")
                .build()

            val pokemonRepository = RemotePokemonRepository(
                database.pokemonDao(),
                apolloClient
            )

            val movesRepository = RemoteMovesRepository(
                database.movesDao(),
                apolloClient
            )

            val itemsRepository = ItemsRepositoryImpl(
                database.itemsDao(),
                apolloClient
            )

            val abilitiesRepository = AbilitiesRepositoryImpl(
                database.abilitiesDao(),
                apolloClient
            )

            PokedexerSDK(
                database,
                apolloClient,
                pokemonRepository,
                movesRepository,
                itemsRepository,
                abilitiesRepository
            )
        }
    }

    // Pokemon methods
    fun getAllPokemon(): Flow<List<Pokemon>> = pokemonRepository.pokemon()

    suspend fun updatePokemon() = pokemonRepository.updatePokemon()

    fun getPokemonById(id: Int): Flow<Pokemon?> = pokemonRepository.getPokemonById(id)

    fun getPokemonByName(name: String): Flow<List<Pokemon>> = pokemonRepository.getPokemonByName(name)
    
    fun getPokemonByGeneration(generationId: Int): Flow<List<Pokemon>> {
        val generation = Generation.entries.firstOrNull { it.id == generationId } ?: Generation.I
        return pokemonRepository.getPokemonByGeneration(generation)
    }

    // Moves methods
    fun getAllMoves(): Flow<List<Move>> = movesRepository.moves()

    suspend fun updateMoves() = movesRepository.updateMoves()

    fun getMoveById(id: Int): Flow<Move?> = movesRepository.getMoveById(id)

    fun getMovesByName(name: String): Flow<List<Move>> = movesRepository.getMovesByName(name)

    // Items methods
    fun getAllItems(): Flow<List<Item>> = itemsRepository.items()

    suspend fun updateItems() = itemsRepository.updateItems()

    fun getItemById(id: Int): Flow<Item?> = itemsRepository.getItemById(id)

    fun getItemsByName(name: String): Flow<List<Item>> = itemsRepository.getItemsByName(name)

    // Abilities methods
    suspend fun updateAbilities() = abilitiesRepository.updateAbilities()

    fun getAbilityById(id: Int): Flow<Ability?> = abilitiesRepository.getAbilityById(id)
}
