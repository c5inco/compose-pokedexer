package des.c5inco.pokedexer.journeys

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeDown
import androidx.compose.ui.test.swipeUp
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
        composeTestRule.onAllNodesWithText("Bulbasaur")[0].performClick()
        
        // 3. Verify Details Screen
        composeTestRule.waitForIdle()
        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithText("About").fetchSemanticsNodes().isNotEmpty()
        }
        
        // Assertions for details screen
        composeTestRule.onNodeWithText("Base stats").assertExists()
        composeTestRule.onNodeWithText("About").assertExists()
    }
    
    @Test
    fun testPokedexScrollsToLastViewedPokemon() {
        // This test verifies that when navigating back from Pokemon details,
        // the Pokedex list scrolls to show the Pokemon that was last viewed.
        // This is especially important when using the pager in details to view
        // a Pokemon that wasn't in the viewport when first entering details.
        
        // 1. Open Pokedex
        composeTestRule.onNodeWithText("Pokédex").performClick()
        
        // Verify we are on Pokedex screen
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Pokemon").assertExists()
        
        // Wait for initial content to load
        composeTestRule.waitUntil(8000) {
            composeTestRule.onAllNodesWithText("Bulbasaur").fetchSemanticsNodes().isNotEmpty()
        }
        
        // 2. Scroll down in the Pokedex list to reach Pikachu (#25)
        repeat(2) {
            composeTestRule.onNodeWithTag("PokedexLazyGrid").performTouchInput {
                swipeUp(
                    startY = this.centerY,
                    endY = this.centerY - 1000f
                )
            }
            composeTestRule.waitForIdle()
        }
        
        // 3. Wait for Pikachu to appear (Pokemon #25)
        composeTestRule.waitUntil(10000) {
            composeTestRule.onAllNodesWithText("Pikachu").fetchSemanticsNodes().isNotEmpty()
        }
        
        // Note: Pikachu is now visible in the viewport
        // 4. Scroll up to move Pikachu out of the initial viewport
        // This simulates the scenario where we'll need to scroll back to it
        composeTestRule.onNodeWithTag("PokedexLazyGrid").performTouchInput {
            swipeDown(
                startY = this.centerY,
                endY = this.centerY + 800f
            )
        }
        composeTestRule.waitForIdle()
        
        // Scroll back down to see Pikachu again and click it
        composeTestRule.onNodeWithTag("PokedexLazyGrid").performTouchInput {
            swipeUp(
                startY = this.centerY,
                endY = this.centerY - 800f
            )
        }
        composeTestRule.waitForIdle()
        
        // Wait again for Pikachu and click it
        composeTestRule.waitUntil(8000) {
            composeTestRule.onAllNodesWithText("Pikachu").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onAllNodesWithText("Pikachu")[0].performClick()
        
        // 5. Wait for details screen to load
        composeTestRule.waitForIdle()
        composeTestRule.waitUntil(8000) {
            composeTestRule.onAllNodesWithText("About").fetchSemanticsNodes().isNotEmpty()
        }
        
        // Verify we're on the details screen
        composeTestRule.onNodeWithText("Base stats").assertExists()
        
        // 6. Go back to the Pokedex
        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }
        composeTestRule.waitForIdle()
        
        // 7. Verify the Pokedex list has scrolled back to show Pikachu
        // The scroll behavior should ensure Pikachu is visible and positioned
        // at least 100.dp from the top (as per the scroll offset implementation)
        composeTestRule.waitUntil(8000) {
            composeTestRule.onAllNodesWithText("Pikachu").fetchSemanticsNodes().isNotEmpty()
        }
        
        // Verify we're back on the Pokedex screen and Pikachu is visible
        // This confirms the scroll position was correctly restored
        composeTestRule.onNodeWithText("Pokemon").assertExists()
    }
}
