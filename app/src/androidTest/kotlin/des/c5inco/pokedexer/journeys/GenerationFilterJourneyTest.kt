package des.c5inco.pokedexer.journeys

import android.graphics.Bitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import des.c5inco.pokedexer.MainActivity
import org.junit.Rule
import org.junit.Test
import java.io.FileOutputStream

class GenerationFilterJourneyTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testGenerationFilterJourney() {
        // 1. Open Pokedex
        composeTestRule.onNodeWithText("Pokédex").performClick()
        
        // Verify we are on Pokedex screen
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Pokemon").assertExists()
        
        takeScreenshot("1_pokedex_screen")

        // 2. Filter to Gen III
        composeTestRule.onNodeWithContentDescription("Show filters").performClick()
        
        // Check if FAB changed to "Hide filters"
        composeTestRule.waitUntil(5000) {
             composeTestRule.onAllNodesWithContentDescription("Hide filters").fetchSemanticsNodes().isNotEmpty()
        }
        
        // Wait for "All generations" to appear
        composeTestRule.waitUntil(10000) {
             composeTestRule.onAllNodesWithText("Gen I").fetchSemanticsNodes().isNotEmpty()
        }
        
        takeScreenshot("2_filter_menu")
        
        composeTestRule.onNodeWithText("Gen I").performClick()
        
        // Wait for Gen III option
        composeTestRule.waitUntil(5000) {
             composeTestRule.onAllNodesWithText("Gen III").fetchSemanticsNodes().isNotEmpty()
        }

        takeScreenshot("3_generations_list")

        composeTestRule.onNodeWithText("Gen III").performClick()
        takeScreenshot("4_filtered_gen_iii")
        
        // 3. Wait for Torchic to be visible
        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithText("Torchic").fetchSemanticsNodes().isNotEmpty()
        }
        
        takeScreenshot("5_torchic_visible")
        
        // 4. Click Torchic
        composeTestRule.onNodeWithText("Torchic").performClick()
        
        // 5. Verify details
        composeTestRule.waitForIdle()
        
        // Wait for details screen
        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithText("About").fetchSemanticsNodes().isNotEmpty()
        }
        
        takeScreenshot("6_torchic_details")
        
        composeTestRule.onNodeWithText("About").assertExists()
        composeTestRule.onNodeWithText("Base stats").assertExists()
        composeTestRule.onNodeWithText("Evolution").assertExists()
        composeTestRule.onNodeWithText("Moves").assertExists()
    }

    private fun takeScreenshot(name: String) {
        try {
            val bitmap = composeTestRule.onRoot().captureToImage().asAndroidBitmap()
            saveBitmap(bitmap, name)
        } catch (e: Exception) {
            e.printStackTrace()
            println("Failed to take screenshot: ${e.message}")
        }
    }

    private fun saveBitmap(bitmap: Bitmap, name: String) {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Save to app's external files directory which doesn't require extra permissions on newer Android
        val file = context.getExternalFilesDir(null)?.resolve("$name.png")
        file?.let {
            FileOutputStream(it).use { out ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            }
            println("Screenshot saved to ${it.absolutePath}")
        }
    }
}




