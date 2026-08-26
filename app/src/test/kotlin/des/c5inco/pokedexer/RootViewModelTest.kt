package des.c5inco.pokedexer

import com.apollographql.apollo3.exception.ApolloException
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertSame
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class RootViewModelTest {
    @Test
    fun apolloFailureDoesNotCancelSiblingWork() = runBlocking {
        var siblingCompleted = false

        coroutineScope {
            launch { runDataUpdateSafely("moves") { throw ApolloException("offline") } }
            launch { siblingCompleted = true }
        }

        assertTrue(siblingCompleted)
    }

    @Test
    fun dataUpdateCancellationIsRethrown() {
        val cancellation = CancellationException("cancelled")

        val thrown =
            assertThrows(CancellationException::class.java) {
                runBlocking { runDataUpdateSafely("items") { throw cancellation } }
            }

        assertSame(cancellation, thrown)
    }
}
