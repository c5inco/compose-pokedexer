package des.c5inco.pokedexer.ui.pokedex

import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Test

class PokemonDetailsPagerBehaviorTest {
    private val pokemonSet = SamplePokemonData.take(POKEMON_SET_SIZE)

    @Test
    fun initialPageMatchesDisplayedPokemonId() {
        val displayedPokemon = pokemonSet[EXPECTED_PAGE]

        assertEquals(
            EXPECTED_PAGE,
            pokemonDetailsInitialPage(
                pokemonSet = pokemonSet,
                displayedPokemonId = displayedPokemon.id,
            ),
        )
    }

    @Test
    fun initialPageFallsBackToFirstPageWhenPokemonIsMissing() {
        assertEquals(
            FIRST_PAGE,
            pokemonDetailsInitialPage(pokemonSet = pokemonSet, displayedPokemonId = Int.MAX_VALUE),
        )
    }

    @Test
    fun initialPageFallsBackToFirstPageWhenPokemonSetIsEmpty() {
        assertEquals(
            FIRST_PAGE,
            pokemonDetailsInitialPage(
                pokemonSet = emptyList(),
                displayedPokemonId = SamplePokemonData.first().id,
            ),
        )
    }

    @Test
    fun pageChangeForwardsPokemonAtCurrentPage() {
        assertSame(
            pokemonSet[EXPECTED_PAGE],
            pokemonDetailsPokemonForPage(pokemonSet = pokemonSet, page = EXPECTED_PAGE),
        )
    }

    @Test
    fun emptyPokemonSetDoesNotResolvePageChange() {
        assertNull(pokemonDetailsPokemonForPage(pokemonSet = emptyList(), page = FIRST_PAGE))
    }

    private companion object {
        const val FIRST_PAGE = 0
        const val EXPECTED_PAGE = 3
        const val POKEMON_SET_SIZE = 7
    }
}
