package des.c5inco.pokedexer

import androidx.navigation3.runtime.NavKey
import des.c5inco.pokedexer.ui.home.appbar.elements.MenuItem
import des.c5inco.pokedexer.ui.navigation.Screen
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

private const val POKEMON_ID = 25
private const val APP_JVM_FACADE = "des.c5inco.pokedexer.PokedexerAppKt"

class PokedexerNavigationTest {
    @Test
    fun menuDestinationsPreserveSupportedNavigation() {
        assertEquals(Screen.Pokedex, destinationFor(MenuItem.Pokedex))
        assertEquals(Screen.Moves, destinationFor(MenuItem.Moves))
        assertEquals(Screen.Items, destinationFor(MenuItem.Items))
        assertEquals(Screen.TypeCharts, destinationFor(MenuItem.TypeCharts))
        assertNull(destinationFor(MenuItem.Abilities))
        assertNull(destinationFor(MenuItem.Locations))
    }

    @Test
    fun backNavigationKeepsRootAndRemovesNestedDestination() {
        val rootStack = mutableListOf<NavKey>(Screen.Home)
        assertFalse(rootStack.popNestedDestination())
        assertEquals(listOf(Screen.Home), rootStack)

        val nestedStack = mutableListOf<NavKey>(Screen.Home, Screen.Pokedex)
        assertTrue(nestedStack.popNestedDestination())
        assertEquals(listOf(Screen.Home), nestedStack)
    }

    @Test
    fun previousPokemonIdOnlyComesFromDetailsBehindPokedex() {
        assertEquals(
            POKEMON_ID,
            previousPokemonId(
                listOf(Screen.Home, Screen.PokemonDetails(POKEMON_ID), Screen.Pokedex)
            ),
        )
        assertNull(previousPokemonId(listOf(Screen.Home, Screen.Pokedex)))
    }

    @Test
    fun jvmFacadeMatchesBaselineProfile() {
        val facade =
            Class.forName(APP_JVM_FACADE, false, PokedexerNavigationTest::class.java.classLoader)

        assertTrue(facade.declaredMethods.any { it.name == "PokedexerApp" })
    }
}
