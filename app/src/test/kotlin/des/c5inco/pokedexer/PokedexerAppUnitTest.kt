package des.c5inco.pokedexer

import androidx.compose.ui.unit.Density
import des.c5inco.pokedexer.ui.navigation.Screen
import org.junit.Assert.assertSame
import org.junit.Test

class PokedexerAppUnitTest {
    @Test
    fun pokemonDetailsTransitionMetadataProvider_reusesMetadataForSameInputs() {
        val provider = pokemonDetailsTransitionMetadataProvider(Density(density = 3f))

        val first = provider(Screen.PokemonDetails(25))
        val second = provider(Screen.PokemonDetails(25))

        assertSame(first, second)
    }
}
