package des.c5inco.pokedexer.journeys

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import des.c5inco.pokedexer.MainActivity
import org.junit.Rule
import org.junit.Test

class VerificationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testNavigationHomeToDetails() {
        // 1. Open Pokedex
        composeTestRule.onNodeWithText("Pokédex").performClick()
        
        // Verify we are on Pokedex screen
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Pokemon").assertExists()
        
        // 2. Click on Bulbasaur (should be first)
        // Wait for it to appear
        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithText("Bulbasaur").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("Bulbasaur").performClick()
        
        // 3. Verify Details Screen
        composeTestRule.waitForIdle()
        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithText("About").fetchSemanticsNodes().isNotEmpty()
        }
        
        // Assertions for details
        composeTestRule.onNodeWithText("Bulbasaur").assertExists()
        composeTestRule.onNodeWithText("Base stats").assertExists()
    }
}
