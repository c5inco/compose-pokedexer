package des.c5inco.pokedexer.ui.common

import androidx.compose.ui.unit.Density
import org.junit.Assert.assertEquals
import org.junit.Test

class TransitionsTest {
    @Test
    fun sharedXAxisEnterOffset_usesFixedMaterialSlideDistance() {
        val density = Density(density = 3f)

        assertEquals(90, sharedXAxisEnterOffset(density))
        assertEquals(-90, sharedXAxisPopEnterOffset(density))
    }
}
