package des.c5inco.pokedexer.ui.home.appbar

import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.ui.home.appbar.elements.MenuItem
import org.junit.Assert.assertSame
import org.junit.Test

class MainAppBarCallbacksTest {
    @Test
    fun menuItemSelectedForwardsSelectedItem() {
        val selectedItem = MenuItem.Moves
        var receivedItem: MenuItem? = null
        val callbacks =
            MainAppBarCallbacks(onMenuItemSelected = { menuItem -> receivedItem = menuItem })

        callbacks.menuItemSelected(selectedItem)

        assertSame(selectedItem, receivedItem)
    }

    @Test
    fun searchResultSelectedForwardsSelectedResult() {
        val selectedResult = SearchResult.PokemonEvent(SamplePokemonData.first())
        var receivedResult: SearchResult? = null
        val callbacks =
            MainAppBarCallbacks(
                onSearchResultSelected = { searchResult -> receivedResult = searchResult }
            )

        callbacks.searchResultSelected(selectedResult)

        assertSame(selectedResult, receivedResult)
    }
}
