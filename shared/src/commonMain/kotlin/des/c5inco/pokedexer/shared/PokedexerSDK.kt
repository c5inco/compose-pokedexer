package des.c5inco.pokedexer.shared

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import des.c5inco.pokedexer.shared.data.ApolloClientProvider
import des.c5inco.pokedexer.shared.data.db.PokedexerDatabase
import des.c5inco.pokedexer.shared.data.db.getDatabaseBuilder
import des.c5inco.pokedexer.shared.data.preferences.FavoritesStore
import des.c5inco.pokedexer.shared.data.toDomainModel
import des.c5inco.pokedexer.shared.model.Ability
import des.c5inco.pokedexer.shared.model.Generation
import des.c5inco.pokedexer.shared.model.Item
import des.c5inco.pokedexer.shared.model.Move
import des.c5inco.pokedexer.shared.model.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

/**
 * Main entry point for the Pokedexer SDK.
 * This class provides a clean interface for iOS (and other platforms) to interact with
 * the shared data layer.
 *
 * iOS usage example:
 * ```swift
 * let sdk = PokedexerSDK(favoritesStore: UserDefaultsFavoritesStore())
 *
 * // Using async/await (via KMP-NativeCoroutines)
 * let pokemon = try await asyncFunction(for: sdk.loadPokemonForGeneration(generationId: 1))
 *
 * // Observing flows
 * for try await favorites in asyncSequence(for: sdk.favoritesFlow) {
 *     // Handle favorites updates
 * }
 * ```
 */
class PokedexerSDK(
    private val favoritesStore: FavoritesStore
) {
    private val database: PokedexerDatabase by lazy {
        getDatabaseBuilder()
            .fallbackToDestructiveMigration(dropAllTables = true)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.Default)
            .build()
    }

    private val apolloClient = ApolloClientProvider.apolloClient

    // Loading states
    private val _isLoadingPokemon = MutableStateFlow(false)
    val isLoadingPokemon: StateFlow<Boolean> = _isLoadingPokemon.asStateFlow()

    // DAOs
    private val pokemonDao get() = database.pokemonDao()
    private val movesDao get() = database.movesDao()
    private val itemsDao get() = database.itemsDao()
    private val abilitiesDao get() = database.abilitiesDao()

    // ===== Pokemon =====

    /**
     * Get all Pokemon as a Flow.
     */
    @NativeCoroutines
    fun getAllPokemon(): Flow<List<Pokemon>> = pokemonDao.getAllFlow()

    /**
     * Get Pokemon by generation as a Flow.
     */
    @NativeCoroutines
    fun getPokemonByGeneration(generationId: Int): Flow<List<Pokemon>> =
        pokemonDao.getAllByGeneration(generationId)

    /**
     * Get a single Pokemon by ID.
     */
    @NativeCoroutines
    fun getPokemonById(id: Int): Flow<Pokemon?> = pokemonDao.findById(id)

    /**
     * Search Pokemon by name.
     */
    @NativeCoroutines
    fun searchPokemon(name: String): Flow<List<Pokemon>> = pokemonDao.findByName(name)

    /**
     * Load Pokemon for a generation from the network if not cached.
     * Returns the list of Pokemon for that generation.
     */
    @NativeCoroutines
    suspend fun loadPokemonForGeneration(generationId: Int): List<Pokemon> {
        _isLoadingPokemon.value = true
        try {
            val localPokemon = pokemonDao.getAllByGeneration(generationId).first()

            if (localPokemon.isEmpty()) {
                return withContext(Dispatchers.Default) {
                    println("Loading Pokemon (gen $generationId) from network...")
                    val response = apolloClient.query(PokemonOriginalQuery(generationId)).execute()

                    if (!response.hasErrors()) {
                        val pokemonFromServer = response.data!!.pokemon.map { model ->
                            model.toDomainModel(generationId)
                        }

                        pokemonDao.insertAll(*pokemonFromServer.toTypedArray())
                        println("Populated database: ${pokemonFromServer.size} pokemon")
                        pokemonFromServer
                    } else {
                        println("Error loading Pokemon: ${response.errors}")
                        emptyList()
                    }
                }
            } else {
                return localPokemon
            }
        } finally {
            _isLoadingPokemon.value = false
        }
    }

    // ===== Moves =====

    /**
     * Get all moves as a Flow.
     */
    @NativeCoroutines
    fun getAllMoves(): Flow<List<Move>> = movesDao.getAll()

    /**
     * Get a single move by ID.
     */
    @NativeCoroutines
    fun getMoveById(id: Int): Flow<Move?> = movesDao.findById(id)

    /**
     * Get moves by IDs.
     */
    @NativeCoroutines
    suspend fun getMovesByIds(ids: List<Int>): List<Move> = movesDao.findByIds(ids)

    /**
     * Load moves from the network if not cached.
     */
    @NativeCoroutines
    suspend fun loadMoves(): List<Move> {
        val moves = movesDao.getAll().first()

        if (moves.isEmpty()) {
            return withContext(Dispatchers.Default) {
                val response = apolloClient.query(PokemonOriginalMovesQuery()).execute()

                if (!response.hasErrors()) {
                    val movesFromServer = response.data!!.moves.map { it.toDomainModel() }
                    movesDao.deleteAll()
                    movesDao.insertAll(*movesFromServer.toTypedArray())
                    movesFromServer
                } else {
                    emptyList()
                }
            }
        }
        return moves
    }

    // ===== Items =====

    /**
     * Get all items as a Flow.
     */
    @NativeCoroutines
    fun getAllItems(): Flow<List<Item>> = itemsDao.getAll()

    /**
     * Get a single item by ID.
     */
    @NativeCoroutines
    fun getItemById(id: Int): Flow<Item?> = itemsDao.findById(id)

    /**
     * Load items from the network if not cached.
     */
    @NativeCoroutines
    suspend fun loadItems(): List<Item> {
        val items = itemsDao.getAll().first()

        if (items.isEmpty()) {
            return withContext(Dispatchers.Default) {
                val response = apolloClient.query(ItemsQuery()).execute()

                if (!response.hasErrors()) {
                    val itemsFromServer = response.data!!.info.items.map { it.toDomainModel() }
                    itemsDao.deleteAll()
                    itemsDao.insertAll(*itemsFromServer.toTypedArray())
                    itemsFromServer
                } else {
                    emptyList()
                }
            }
        }
        return items
    }

    // ===== Abilities =====

    /**
     * Get all abilities as a Flow.
     */
    @NativeCoroutines
    fun getAllAbilities(): Flow<List<Ability>> = abilitiesDao.getAll()

    /**
     * Get a single ability by ID.
     */
    @NativeCoroutines
    fun getAbilityById(id: Int): Flow<Ability?> = abilitiesDao.findById(id)

    /**
     * Get abilities by IDs.
     */
    @NativeCoroutines
    suspend fun getAbilitiesByIds(ids: List<Int>): List<Ability> = abilitiesDao.findByIds(ids)

    /**
     * Load abilities from the network if not cached.
     */
    @NativeCoroutines
    suspend fun loadAbilities(): List<Ability> {
        val abilities = abilitiesDao.getAll().first()

        if (abilities.isEmpty()) {
            return withContext(Dispatchers.Default) {
                val response = apolloClient.query(AbilitiesQuery()).execute()

                if (!response.hasErrors()) {
                    val abilitiesFromServer = response.data!!.abilities.map { it.toDomainModel() }
                    abilitiesDao.deleteAll()
                    abilitiesDao.insertAll(*abilitiesFromServer.toTypedArray())
                    abilitiesFromServer
                } else {
                    emptyList()
                }
            }
        }
        return abilities
    }

    // ===== Favorites =====

    /**
     * Get favorites as a Flow.
     */
    @NativeCoroutines
    val favoritesFlow: Flow<List<Int>>
        get() = favoritesStore.favoritesFlow

    /**
     * Toggle a Pokemon's favorite status.
     */
    @NativeCoroutines
    suspend fun toggleFavorite(pokemonId: Int) {
        favoritesStore.toggleFavorite(pokemonId)
    }

    /**
     * Get the current list of favorites.
     */
    @NativeCoroutines
    suspend fun getFavorites(): List<Int> {
        return favoritesStore.getFavorites()
    }

    /**
     * Get favorite Pokemon.
     */
    @NativeCoroutines
    fun getFavoritePokemon(): Flow<List<Pokemon>> {
        return pokemonDao.findByIds(emptyList()) // This will be updated to use favorites
    }

    // ===== Utility =====

    /**
     * Get all available generations.
     */
    fun getGenerations(): List<GenerationInfo> {
        return Generation.entries.map { gen ->
            GenerationInfo(
                id = gen.id,
                name = "Generation ${gen.romanNumeral}",
                romanNumeral = gen.romanNumeral
            )
        }
    }
}

/**
 * Information about a Pokemon generation.
 */
data class GenerationInfo(
    val id: Int,
    val name: String,
    val romanNumeral: String
)

