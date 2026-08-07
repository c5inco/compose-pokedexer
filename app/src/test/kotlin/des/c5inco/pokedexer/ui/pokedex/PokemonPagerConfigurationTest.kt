package des.c5inco.pokedexer.ui.pokedex

import androidx.compose.ui.graphics.Color
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PokemonPagerConfigurationTest {
    @Test
    fun defaultsForegroundColorToBlack() {
        val backgroundColor = Color.Red

        val configuration = PokemonPagerConfiguration(backgroundColor = backgroundColor)

        assertEquals(backgroundColor, configuration.backgroundColor)
        assertEquals(Color.Black, configuration.foregroundColor)
    }

    @Test
    fun preservesCustomForegroundAndBackgroundColors() {
        val backgroundColor = Color.Blue
        val foregroundColor = Color.White

        val configuration =
            PokemonPagerConfiguration(
                backgroundColor = backgroundColor,
                foregroundColor = foregroundColor,
            )

        assertEquals(backgroundColor, configuration.backgroundColor)
        assertEquals(foregroundColor, configuration.foregroundColor)
    }

    @Test
    fun defaultsToEnabled() {
        val configuration = PokemonPagerConfiguration(backgroundColor = Color.Black)

        assertTrue(configuration.enabled)
    }
}
