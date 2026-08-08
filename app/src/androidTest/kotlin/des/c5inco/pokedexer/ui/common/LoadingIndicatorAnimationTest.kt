package des.c5inco.pokedexer.ui.common

import android.app.Application
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.toPixelMap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.test.ext.junit.runners.AndroidJUnit4
import des.c5inco.pokedexer.LocalGifImageLoader
import des.c5inco.pokedexer.appGraph
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoadingIndicatorAnimationTest {
    @get:Rule val composeTestRule = createComposeRule()

    @Test
    fun loadingIndicatorTraversesAndRestarts() {
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.setContent {
            val application = LocalContext.current.applicationContext as Application
            CompositionLocalProvider(
                LocalGifImageLoader provides application.appGraph.gifImageLoader
            ) {
                LoadingIndicator()
            }
        }

        composeTestRule.mainClock.advanceTimeBy(400)
        val (leftX, viewportWidth) = renderedIndicatorPosition()

        composeTestRule.mainClock.advanceTimeBy(600)
        val middleX = renderedIndicatorPosition().first

        composeTestRule.mainClock.advanceTimeBy(800)
        val rightX = renderedIndicatorPosition().first

        composeTestRule.mainClock.advanceTimeBy(600)
        val restartedX = renderedIndicatorPosition().first

        assertTrue("Expected the indicator in the left third", leftX < viewportWidth / 3)
        assertTrue(
            "Expected the indicator in the middle third",
            middleX in viewportWidth / 3 until viewportWidth * 2 / 3,
        )
        assertTrue("Expected the indicator in the right third", rightX > viewportWidth * 2 / 3)
        assertTrue("Expected the repeating indicator to restart", restartedX < viewportWidth / 3)
    }

    private fun renderedIndicatorPosition(): Pair<Int, Int> {
        val pixels = composeTestRule.onRoot().captureToImage().toPixelMap()
        for (x in 0 until pixels.width) {
            for (y in 0 until pixels.height) {
                val color = pixels[x, y]
                if (color.red > 0.6f && color.green > 0.4f && color.blue < 0.4f) {
                    return x to pixels.width
                }
            }
        }
        error("Rendered loading indicator not found")
    }
}
