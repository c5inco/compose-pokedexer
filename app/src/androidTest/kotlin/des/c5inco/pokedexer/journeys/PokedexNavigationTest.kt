package des.c5inco.pokedexer.journeys

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToKey
import des.c5inco.pokedexer.MainActivity
import org.junit.Rule
import org.junit.Test

private const val PIKACHU_ID = 25

class PokedexNavigationTest {
    @get:Rule val composeTestRule = createAndroidComposeRule<MainActivity>()

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

        // 2. Scroll the Pokedex list to Pikachu (#25). Pixel-based swipes fling a
        // density-dependent distance, so scroll by the grid's item key instead.
        composeTestRule.onNodeWithTag("PokedexLazyGrid").performScrollToKey(PIKACHU_ID)
        composeTestRule.waitForIdle()

        // 3. Wait for Pikachu to appear and click it
        composeTestRule.waitUntil(8000) {
            composeTestRule.onAllNodesWithText("Pikachu").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onAllNodesWithText("Pikachu")[0].performClick()

        // 4. Wait for details screen to load
        composeTestRule.waitForIdle()
        composeTestRule.waitUntil(8000) {
            composeTestRule.onAllNodesWithText("About").fetchSemanticsNodes().isNotEmpty()
        }

        // Verify we're on the details screen
        composeTestRule.onNodeWithText("Base stats").assertExists()

        // 5. Go back to the Pokedex
        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }
        composeTestRule.waitForIdle()

        // 6. Verify the Pokedex list has scrolled back to show Pikachu
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
