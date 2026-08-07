package des.c5inco.pokedexer

import des.c5inco.pokedexer.di.ApplicationGraph
import des.c5inco.pokedexer.di.DataStoreModule
import des.c5inco.pokedexer.di.DatabaseModule
import des.c5inco.pokedexer.di.ImageLoaderModule
import des.c5inco.pokedexer.di.NetworkModule
import des.c5inco.pokedexer.di.RepositoryModule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

private const val EXPECTED_SUM = 4

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(EXPECTED_SUM, 2 + 2)
    }

    @Test
    fun applicationGraph_aggregatesProviderModules() {
        val graphType = ApplicationGraph::class.java

        assertTrue(DatabaseModule::class.java.isAssignableFrom(graphType))
        assertTrue(NetworkModule::class.java.isAssignableFrom(graphType))
        assertTrue(RepositoryModule::class.java.isAssignableFrom(graphType))
        assertTrue(DataStoreModule::class.java.isAssignableFrom(graphType))
        assertTrue(ImageLoaderModule::class.java.isAssignableFrom(graphType))
    }
}
