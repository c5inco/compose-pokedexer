package des.c5inco.pokedexer.shared.data.pokemon

import des.c5inco.pokedexer.shared.model.Generation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.job
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.yield

class GenerationLoaderTest {
    @Test
    fun skipsGenerationsAlreadyInTheDatabase() = runBlocking {
        val scope = testApplicationScope()
        val loadedGenerationIds = mutableSetOf(Generation.II.id, Generation.VIII.id)
        val fetchedGenerations = Channel<Generation>(Channel.UNLIMITED)
        val expectedGenerations =
            listOf(
                Generation.I,
                Generation.III,
                Generation.IV,
                Generation.V,
                Generation.VI,
                Generation.VII,
                Generation.IX,
            )
        val loader =
            GenerationLoader(
                applicationScope = scope,
                loadedGenerationIds = { loadedGenerationIds },
                refreshMoves = {},
                fetchGeneration = { generation ->
                    fetchedGenerations.send(generation)
                    loadedGenerationIds += generation.id
                },
            )

        try {
            loader.start()

            val fetched =
                List(expectedGenerations.size) {
                    withTimeout(TIMEOUT_MILLIS) { fetchedGenerations.receive() }
                }
            assertEquals(expectedGenerations, fetched)
            assertNull(fetchedGenerations.tryReceive().getOrNull())
        } finally {
            scope.cancel()
        }
    }

    @Test
    fun prioritiseMovesAQueuedGenerationToTheFront() = runBlocking {
        val scope = testApplicationScope()
        val loadedGenerationIds = mutableSetOf<Int>()
        val fetchedGenerations = Channel<Generation>(Channel.UNLIMITED)
        val allowGenerationOneToFinish = CompletableDeferred<Unit>()
        val loader =
            GenerationLoader(
                applicationScope = scope,
                loadedGenerationIds = { loadedGenerationIds },
                refreshMoves = {},
                fetchGeneration = { generation ->
                    fetchedGenerations.send(generation)
                    if (generation == Generation.I) allowGenerationOneToFinish.await()
                    loadedGenerationIds += generation.id
                },
            )

        try {
            loader.start()
            assertEquals(Generation.I, withTimeout(TIMEOUT_MILLIS) { fetchedGenerations.receive() })

            loader.prioritise(Generation.VIII)
            allowGenerationOneToFinish.complete(Unit)

            assertEquals(
                Generation.VIII,
                withTimeout(TIMEOUT_MILLIS) { fetchedGenerations.receive() },
            )
        } finally {
            scope.cancel()
        }
    }

    @Test
    fun duplicateRequestsDuringAnActiveLoadFetchOnce() = runBlocking {
        val scope = testApplicationScope()
        val loadedGenerationIds = mutableSetOf<Int>()
        val fetchStarted = CompletableDeferred<Unit>()
        val allowFetchToFinish = CompletableDeferred<Unit>()
        var fetchCount = 0
        val loader =
            GenerationLoader(
                applicationScope = scope,
                loadedGenerationIds = { loadedGenerationIds },
                refreshMoves = {},
                fetchGeneration = { generation ->
                    fetchCount++
                    fetchStarted.complete(Unit)
                    allowFetchToFinish.await()
                    loadedGenerationIds += generation.id
                },
            )

        try {
            val firstRequest = async { loader.load(Generation.II) }
            fetchStarted.await()
            val secondRequest = async { loader.load(Generation.II) }
            loader.prioritise(Generation.II)
            yield()
            allowFetchToFinish.complete(Unit)

            firstRequest.await()
            secondRequest.await()
            assertEquals(1, fetchCount)
        } finally {
            scope.cancel()
        }
    }

    @Test
    fun generationTwoWaitsForMovesRefresh() = runBlocking {
        val scope = testApplicationScope()
        val loadedGenerationIds = mutableSetOf<Int>()
        val movesRefreshStarted = CompletableDeferred<Unit>()
        val allowMovesRefreshToFinish = CompletableDeferred<Unit>()
        val fetchedGenerations = Channel<Generation>(Channel.UNLIMITED)
        val loader =
            GenerationLoader(
                applicationScope = scope,
                loadedGenerationIds = { loadedGenerationIds },
                refreshMoves = {
                    movesRefreshStarted.complete(Unit)
                    allowMovesRefreshToFinish.await()
                },
                fetchGeneration = { generation ->
                    fetchedGenerations.send(generation)
                    loadedGenerationIds += generation.id
                },
            )

        try {
            loader.start()
            movesRefreshStarted.await()
            assertEquals(Generation.I, withTimeout(TIMEOUT_MILLIS) { fetchedGenerations.receive() })
            repeat(3) { yield() }
            assertNull(fetchedGenerations.tryReceive().getOrNull())

            allowMovesRefreshToFinish.complete(Unit)

            assertEquals(
                Generation.II,
                withTimeout(TIMEOUT_MILLIS) { fetchedGenerations.receive() },
            )
        } finally {
            scope.cancel()
        }
    }

    @Test
    fun cachedLaterGenerationWaitsForMovesRefresh() = runBlocking {
        val scope = testApplicationScope()
        val movesRefreshStarted = CompletableDeferred<Unit>()
        val allowMovesRefreshToFinish = CompletableDeferred<Unit>()
        var fetchCount = 0
        val loader =
            GenerationLoader(
                applicationScope = scope,
                loadedGenerationIds = { setOf(Generation.II.id) },
                refreshMoves = {
                    movesRefreshStarted.complete(Unit)
                    allowMovesRefreshToFinish.await()
                },
                fetchGeneration = { fetchCount++ },
            )

        try {
            val request = async { loader.load(Generation.II) }
            withTimeout(TIMEOUT_MILLIS) { movesRefreshStarted.await() }
            yield()
            assertFalse(request.isCompleted)

            allowMovesRefreshToFinish.complete(Unit)
            request.await()

            assertEquals(0, fetchCount)
        } finally {
            scope.cancel()
        }
    }

    @Test
    fun failedGenerationCompletesTheRequestAndCanRetry() = runBlocking {
        val scope = testApplicationScope()
        val loadedGenerationIds = mutableSetOf<Int>()
        var fetchAttempts = 0
        val loader =
            GenerationLoader(
                applicationScope = scope,
                loadedGenerationIds = { loadedGenerationIds },
                refreshMoves = {},
                fetchGeneration = { generation ->
                    fetchAttempts++
                    if (fetchAttempts == 1) error("Network unavailable")
                    loadedGenerationIds += generation.id
                },
            )

        try {
            loader.load(Generation.II)
            assertEquals(1, fetchAttempts)

            loader.load(Generation.II)

            assertEquals(2, fetchAttempts)
            assertEquals(setOf(Generation.II.id), loadedGenerationIds)
        } finally {
            scope.cancel()
        }
    }

    private fun CoroutineScope.testApplicationScope(): CoroutineScope =
        CoroutineScope(coroutineContext + SupervisorJob(coroutineContext.job))

    private companion object {
        const val TIMEOUT_MILLIS = 5_000L
    }
}
