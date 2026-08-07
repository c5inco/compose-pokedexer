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
}
