package des.c5inco.pokedexer.shared

import com.apollographql.apollo3.exception.ApolloException
import kotlin.coroutines.cancellation.CancellationException
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue
import kotlinx.coroutines.runBlocking

class PokedexerSDKTest {
    @Test
    fun apolloFailureDoesNotPreventNextUpdate() = runBlocking {
        var nextUpdateCompleted = false

        runIosDataUpdateSafely("moves") { throw ApolloException("offline") }
        runIosDataUpdateSafely("items") { nextUpdateCompleted = true }

        assertTrue(nextUpdateCompleted)
    }

    @Test
    fun cancellationIsRethrown() {
        assertFailsWith<CancellationException> {
            runBlocking {
                runIosDataUpdateSafely("moves") { throw CancellationException("cancelled") }
            }
        }
    }
}
