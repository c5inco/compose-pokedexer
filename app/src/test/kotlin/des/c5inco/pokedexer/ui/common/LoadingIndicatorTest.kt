package des.c5inco.pokedexer.ui.common

import org.junit.Assert.assertEquals
import org.junit.Test

class LoadingIndicatorTest {
    @Test
    fun translationMovesImageFromOutsideLeftToOutsideRight() {
        assertEquals(
            -IMAGE_WIDTH,
            loadingIndicatorTranslationX(
                progress = 0f,
                imageWidth = IMAGE_WIDTH,
                containerWidth = CONTAINER_WIDTH,
            ),
        )
        assertEquals(
            MIDPOINT,
            loadingIndicatorTranslationX(
                progress = 0.5f,
                imageWidth = IMAGE_WIDTH,
                containerWidth = CONTAINER_WIDTH,
            ),
        )
        assertEquals(
            CONTAINER_WIDTH,
            loadingIndicatorTranslationX(
                progress = 1f,
                imageWidth = IMAGE_WIDTH,
                containerWidth = CONTAINER_WIDTH,
            ),
        )
    }

    private companion object {
        const val IMAGE_WIDTH = 147f
        const val CONTAINER_WIDTH = 1080f
        const val MIDPOINT = 466.5f
    }
}
