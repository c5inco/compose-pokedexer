package des.c5inco.pokedexer.shared.data.pokemon

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import des.c5inco.pokedexer.shared.PokemonOriginalQuery
import des.c5inco.pokedexer.shared.data.cleanupDescriptionText
import des.c5inco.pokedexer.shared.data.moves.MovesRepository
import des.c5inco.pokedexer.shared.model.Evolution
import des.c5inco.pokedexer.shared.model.EvolutionTrigger
import des.c5inco.pokedexer.shared.model.Generation
import des.c5inco.pokedexer.shared.model.Pokemon
import des.c5inco.pokedexer.shared.model.PokemonAbility
import des.c5inco.pokedexer.shared.model.PokemonMove
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

private const val MOVES_RETRY_DELAY_MILLIS = 30_000L

class GenerationLoader
internal constructor(
    private val applicationScope: CoroutineScope,
    private val loadedGenerationIds: suspend () -> Set<Int>,
    private val refreshMoves: suspend () -> Unit,
    private val fetchGeneration: suspend (Generation) -> Unit,
    private val waitBeforeMovesRetry: suspend () -> Unit = { delay(MOVES_RETRY_DELAY_MILLIS) },
) {
    constructor(
        pokemonDao: PokemonDao,
        apolloClient: ApolloClient,
        movesRepository: MovesRepository,
        applicationScope: CoroutineScope,
        ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) : this(
        pokemonDao = pokemonDao,
        apolloClient = apolloClient,
        applicationScope = applicationScope,
        refreshMoves = movesRepository::updateMoves,
        ioDispatcher = ioDispatcher,
    )

    internal constructor(
        pokemonDao: PokemonDao,
        apolloClient: ApolloClient,
        applicationScope: CoroutineScope,
        refreshMoves: suspend () -> Unit,
        ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) : this(
        applicationScope = applicationScope,
        loadedGenerationIds = { pokemonDao.getLoadedGenerationIds().toSet() },
        refreshMoves = refreshMoves,
        fetchGeneration = { generation ->
            fetchPokemonByGeneration(pokemonDao, apolloClient, generation, ioDispatcher)
        },
    )

    private val mutex = Mutex()
    private val queue = mutableListOf<Generation>()
    private val inFlight = mutableSetOf<Generation>()
    private val attempts = mutableMapOf<Generation, CompletableDeferred<Unit>>()
    private var initialMovesRefresh = CompletableDeferred<Unit>()
    private var worker: Job? = null
    private var movesAreReady = false

    suspend fun start() {
        mutex.withLock {
            Generation.entries.forEach { enqueueLocked(it, prioritised = false) }
            ensureWorkerLocked()
        }
    }

    suspend fun prioritise(generation: Generation) {
        if (generation.id in loadedGenerationIds()) return

        mutex.withLock {
            enqueueLocked(generation, prioritised = true)
            ensureWorkerLocked()
        }
    }

    suspend fun load(generation: Generation) {
        if (generation.id in loadedGenerationIds()) {
            val movesReadyForGeneration =
                generation == Generation.I || mutex.withLock { movesAreReady }
            if (movesReadyForGeneration) return
        }

        val attempt = mutex.withLock {
            val pendingAttempt = enqueueLocked(generation, prioritised = true)
            ensureWorkerLocked()
            pendingAttempt
        }
        attempt.await()
    }

    internal suspend fun awaitInitialMovesRefresh() {
        val completion = mutex.withLock {
            if (movesAreReady) return
            ensureWorkerLocked()
            initialMovesRefresh
        }
        completion.await()
    }

    private fun enqueueLocked(
        generation: Generation,
        prioritised: Boolean,
    ): CompletableDeferred<Unit> {
        if (generation in inFlight) {
            val existingAttempt = attempts.getValue(generation)
            if (prioritised && queue.remove(generation)) queue.add(0, generation)
            return existingAttempt
        }

        val attempt = CompletableDeferred<Unit>()
        attempts[generation] = attempt
        inFlight += generation
        if (prioritised) queue.add(0, generation) else queue += generation
        return attempt
    }

    private fun ensureWorkerLocked() {
        if (worker != null) return

        val movesRefreshCompletion = CompletableDeferred<Unit>()
        initialMovesRefresh = movesRefreshCompletion
        val newWorker =
            applicationScope.launch(start = CoroutineStart.LAZY) {
                runWorker(movesRefreshCompletion)
            }
        worker = newWorker
        newWorker.invokeOnCompletion { error ->
            if (error != null) movesRefreshCompletion.completeExceptionally(error)
        }
        newWorker.start()
    }

    private suspend fun runWorker(movesRefreshCompletion: CompletableDeferred<Unit>) {
        try {
            coroutineScope {
                val shouldRefreshMoves = mutex.withLock { !movesAreReady }
                val movesRefresh =
                    if (shouldRefreshMoves) {
                        async { refreshMovesSafely(refreshMoves, movesRefreshCompletion) }
                    } else {
                        null
                    }
                val generationOneQueued = mutex.withLock { queue.remove(Generation.I) }
                if (generationOneQueued) processGeneration(Generation.I)

                var movesRefreshed = movesRefresh?.await() != false
                if (!movesRefreshed) {
                    waitBeforeMovesRetry()
                    movesRefreshed = refreshMovesSafely(refreshMoves)
                }
                if (movesRefreshed) {
                    mutex.withLock { movesAreReady = true }
                    movesRefreshCompletion.complete(Unit)
                }

                while (true) {
                    val generation = takeNextGeneration() ?: return@coroutineScope
                    processGeneration(generation)
                }
            }
        } finally {
            releaseCancelledWorker()
        }
    }

    private suspend fun processGeneration(generation: Generation) {
        try {
            if (generation.id !in loadedGenerationIds()) {
                fetchGeneration(generation)
            }
        } catch (error: CancellationException) {
            throw error
        } catch (error: Exception) {
            println("Failed to fetch Pokemon for generation ${generation.id}: ${error.message}")
        } finally {
            finishAttempt(generation)
        }
    }

    private suspend fun takeNextGeneration(): Generation? = mutex.withLock {
        if (queue.isEmpty()) {
            worker = null
            null
        } else {
            queue.removeAt(0)
        }
    }

    private suspend fun finishAttempt(generation: Generation) {
        val attempt = mutex.withLock {
            inFlight -= generation
            attempts.remove(generation)
        }
        attempt?.complete(Unit)
    }

    private suspend fun releaseCancelledWorker() {
        val currentWorker = currentCoroutineContext().job
        val pendingAttempts = mutex.withLock {
            if (worker !== currentWorker) return@withLock emptyList()

            worker = null
            queue.clear()
            inFlight.clear()
            attempts.values.toList().also { attempts.clear() }
        }
        pendingAttempts.forEach { it.complete(Unit) }
    }
}

private suspend fun refreshMovesSafely(
    refreshMoves: suspend () -> Unit,
    completion: CompletableDeferred<Unit>? = null,
): Boolean =
    try {
        refreshMoves()
        true
    } catch (error: CancellationException) {
        completion?.completeExceptionally(error)
        throw error
    } catch (error: Exception) {
        completion?.completeExceptionally(error)
        println("Failed to refresh moves before loading generations: ${error.message}")
        false
    }

private suspend fun fetchPokemonByGeneration(
    pokemonDao: PokemonDao,
    apolloClient: ApolloClient,
    generation: Generation,
    ioDispatcher: CoroutineDispatcher,
) =
    withContext(ioDispatcher) {
        println("Loading Pokemon (gen ${generation.id}) from network...")
        val response =
            apolloClient
                .query(
                    PokemonOriginalQuery(
                        generation.id,
                        CANONICAL_VERSION_GROUP_IDS.getValue(generation.id),
                    )
                )
                .execute()

        if (response.hasErrors()) {
            throw ApolloException("The response has errors: ${response.errors}")
        }

        val pokemonFromServer =
            response.data!!.pokemon.map { model ->
                val detail = model.detail.first()
                val stats = detail.stats.map { it.baseStat }

                Pokemon(
                    id = model.id,
                    name = formatName(model.name),
                    description =
                        cleanupDescriptionText(model.description.first().flavorText)
                            .replace(model.name.uppercase(), formatName(model.name)),
                    typeOfPokemon = detail.types.map { formatName(it.type!!.name) },
                    category = model.species[0].genus,
                    image = model.id,
                    height = (detail.height ?: 0) / 10.0,
                    weight = (detail.weight ?: 0) / 10.0,
                    genderRate = model.genderRate ?: -1,
                    generationId = generation.id,
                    hp = stats[0],
                    attack = stats[1],
                    defense = stats[2],
                    specialAttack = stats[3],
                    specialDefense = stats[4],
                    speed = stats[5],
                    evolutionChain =
                        transformEvolutionChain(model.evolutionChain?.evolutions ?: emptyList()),
                    movesList = transformMoves(detail.moves),
                    abilitiesList = transformAbilities(detail.abilities),
                )
            }

        pokemonDao.insertAll(*pokemonFromServer.toTypedArray())
        println("Populated pokemon database: ${pokemonFromServer.size}")
    }

private fun transformEvolutionChain(list: List<PokemonOriginalQuery.Evolution>): List<Evolution> {
    return list.map {
        if (it.targetLevels.isNotEmpty()) {
            val target = it.targetLevels.first()

            Evolution(
                id = it.id,
                targetLevel = target.level ?: -1,
                trigger =
                    when (target.triggerType) {
                        3 -> EvolutionTrigger.UseItem
                        2 -> EvolutionTrigger.Trade
                        else -> EvolutionTrigger.LevelUp
                    },
                itemId = target.itemId ?: -1,
            )
        } else {
            Evolution(id = it.id)
        }
    }
}

private fun transformMoves(list: List<PokemonOriginalQuery.Move>): List<PokemonMove> {
    return list.map { PokemonMove(it.id!!, it.level) }
}

private fun transformAbilities(list: List<PokemonOriginalQuery.Ability>): List<PokemonAbility> {
    return list.map { PokemonAbility(it.id!!, it.isHidden) }
}

private fun formatName(name: String): String {
    return name.replaceFirstChar { it.uppercase() }
}

private val CANONICAL_VERSION_GROUP_IDS =
    mapOf(1 to 7, 2 to 10, 3 to 6, 4 to 9, 5 to 14, 6 to 16, 7 to 18, 8 to 20, 9 to 25)
