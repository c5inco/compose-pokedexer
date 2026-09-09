package des.c5inco.pokedexer.ui.common

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.test.platform.app.InstrumentationRegistry
import java.io.File
import java.io.FileOutputStream
import kotlin.math.abs
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class MeshGradientScreenshotTest {
    @get:Rule val composeTestRule = createComposeRule()

    @Test
    fun meshGradientMatchesReferenceScreenshot() {
        composeTestRule.setContent {
            CompositionLocalProvider(LocalDensity provides Density(ReferenceDensity)) {
                Box(
                    Modifier.size(width = 200.dp, height = 124.dp)
                        .meshGradient(points = GradientPoints)
                        .testTag(GradientTag)
                )
            }
        }
        composeTestRule.waitForIdle()

        val actual =
            composeTestRule.onNodeWithTag(GradientTag).captureToImage().asAndroidBitmap()
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val actualFile =
            File(
                instrumentation.targetContext.getExternalFilesDir(null),
                "mesh_gradient_actual.png",
            )
        FileOutputStream(actualFile).use {
            actual.compress(Bitmap.CompressFormat.PNG, 100, it)
        }

        val expected =
            instrumentation.context.assets
                .open("mesh_gradient_reference.png")
                .use(BitmapFactory::decodeStream)

        assertEquals(expected.width, actual.width)
        assertEquals(expected.height, actual.height)

        var totalChannelDifference = 0L
        var changedPixels = 0
        for (y in 0 until actual.height) {
            for (x in 0 until actual.width) {
                val expectedPixel = expected.getPixel(x, y)
                val actualPixel = actual.getPixel(x, y)
                val difference =
                    abs(android.graphics.Color.red(expectedPixel) - android.graphics.Color.red(actualPixel)) +
                        abs(
                            android.graphics.Color.green(expectedPixel) -
                                android.graphics.Color.green(actualPixel)
                        ) +
                        abs(
                            android.graphics.Color.blue(expectedPixel) -
                                android.graphics.Color.blue(actualPixel)
                        )
                totalChannelDifference += difference
                if (difference > PixelDifferenceThreshold) changedPixels++
            }
        }

        val pixelCount = actual.width * actual.height
        val meanChannelDifference = totalChannelDifference.toDouble() / (pixelCount * 3)
        val changedPixelRatio = changedPixels.toDouble() / pixelCount
        assertTrue(
            "Mesh gradient changed visually: mean channel difference $meanChannelDifference, " +
                "changed pixels ${changedPixelRatio * 100}% (actual: ${actualFile.absolutePath})",
            meanChannelDifference <= MeanChannelDifferenceThreshold &&
                changedPixelRatio <= ChangedPixelRatioThreshold,
        )
    }

    private companion object {
        const val GradientTag = "mesh-gradient"
        const val ReferenceDensity = 2.625f
        const val PixelDifferenceThreshold = 36
        const val MeanChannelDifferenceThreshold = 2.0
        const val ChangedPixelRatioThreshold = 0.02

        val GradientPoints =
            listOf(
                listOf(
                    Offset(0f, 0f) to Color(0xFFE91E63),
                    Offset(0.24f, 0f) to Color(0xFFE91E63),
                    Offset(0.54f, 0f) to Color(0xFFE91E63),
                    Offset(0.79f, 0f) to Color(0xFFE91E63),
                    Offset(1f, 0f) to Color(0xFF9C27B0),
                ),
                listOf(
                    Offset(0f, 0.5f) to Color(0xFFE91E63),
                    Offset(0.24f, 0.63f) to Color(0xFF9C27B0),
                    Offset(0.53f, 0.42f) to Color(0xFF9C27B0),
                    Offset(0.8f, 0.67f) to Color(0xFFE91E63),
                    Offset(1f, 0.36f) to Color(0xFF9C27B0),
                ),
                listOf(
                    Offset(0f, 1f) to Color(0xFF9C27B0),
                    Offset(0.24f, 1f) to Color(0xFF9C27B0),
                    Offset(0.52f, 1f) to Color(0xFF9C27B0),
                    Offset(0.79f, 1f) to Color(0xFF9C27B0),
                    Offset(1f, 1f) to Color(0xFF9C27B0),
                ),
            )
    }
}
