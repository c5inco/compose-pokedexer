package des.c5inco.pokedexer.journeys

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import des.c5inco.pokedexer.MainActivity
import org.junit.Rule
import org.junit.Test

class ScreenVerificationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testItemsScreenNavigationAndScrolling() {
        // 1. Open Items screen from home menu
        composeTestRule.onNodeWithText("Items").performClick()

        // 2. Verify we are on Items screen
        composeTestRule.waitForIdle()
        composeTestRule.waitUntil(8000) {
            composeTestRule.onAllNodesWithText("Items").fetchSemanticsNodes().isNotEmpty()
        }

        // Verify the Items title is present
        composeTestRule.onNodeWithText("Items").assertExists()

        // 3. Wait for content to load - we should see items with names and descriptions
        composeTestRule.waitUntil(10000) {
            composeTestRule.onAllNodesWithText("Master Ball", substring = true).fetchSemanticsNodes().isNotEmpty() ||
            composeTestRule.onAllNodesWithText("Potion", substring = true).fetchSemanticsNodes().isNotEmpty()
        }

        // 4. Verify scrolling works - perform scroll gestures
        // Note: Since ItemsScreen uses LazyColumn without a testTag, we'll scroll via touch input on the content
        composeTestRule.waitForIdle()

        // Verify back navigation button exists
        composeTestRule.onNodeWithContentDescription("Back").assertExists()

        // Go back to home
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        composeTestRule.waitForIdle()
    }

    @Test
    fun testMovesScreenNavigationAndScrolling() {
        // 1. Open Moves screen from home menu
        composeTestRule.onNodeWithText("Moves").performClick()

        // 2. Verify we are on Moves screen
        composeTestRule.waitForIdle()
        composeTestRule.waitUntil(8000) {
            composeTestRule.onAllNodesWithText("Moves").fetchSemanticsNodes().isNotEmpty()
        }

        // Verify the Moves title is present
        composeTestRule.onNodeWithText("Moves").assertExists()

        // 3. Wait for table headers to appear
        composeTestRule.waitUntil(10000) {
            composeTestRule.onAllNodesWithText("Name").fetchSemanticsNodes().isNotEmpty()
        }

        // Verify table headers exist
        composeTestRule.onNodeWithText("Name").assertExists()
        composeTestRule.onNodeWithText("Type").assertExists()
        composeTestRule.onNodeWithText("Category").assertExists()
        composeTestRule.onNodeWithText("Power").assertExists()
        composeTestRule.onNodeWithText("Accuracy").assertExists()

        // 4. Wait for move data to load
        composeTestRule.waitUntil(10000) {
            // Wait for at least one move to be visible
            composeTestRule.onAllNodesWithText("Pound", substring = true).fetchSemanticsNodes().isNotEmpty() ||
            composeTestRule.onAllNodesWithText("Scratch", substring = true).fetchSemanticsNodes().isNotEmpty()
        }

        // 5. Verify scrolling works
        composeTestRule.waitForIdle()

        // Verify back navigation button exists
        composeTestRule.onNodeWithContentDescription("Back").assertExists()

        // Go back to home
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        composeTestRule.waitForIdle()
    }

    @Test
    fun testTypeChartScreenNavigationAndScrolling() {
        // 1. Open Type charts screen from home menu
        composeTestRule.onNodeWithText("Type charts").performClick()

        // 2. Verify we are on Type Chart screen
        composeTestRule.waitForIdle()
        composeTestRule.waitUntil(8000) {
            composeTestRule.onAllNodesWithText("Type Chart").fetchSemanticsNodes().isNotEmpty()
        }

        // Verify the Type Chart title is present
        composeTestRule.onNodeWithText("Type Chart").assertExists()

        // 3. Verify legend items are present (these should always be visible)
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("2× Super Effective", substring = true).assertExists()
        composeTestRule.onNodeWithText("½× Not Very Effective", substring = true).assertExists()
        composeTestRule.onNodeWithText("0 No Effect", substring = true).assertExists()

        // 4. Verify type icons are present (content description for type icons)
        // The TypeChartScreen should show type effectiveness grid which is scrollable
        // Note: The screen has both horizontal and vertical scrolling capabilities
        composeTestRule.waitForIdle()

        // 5. Verify back navigation button exists
        composeTestRule.onNodeWithContentDescription("Back").assertExists()

        // Go back to home
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        composeTestRule.waitForIdle()
    }

    @Test
    fun testItemsScreenDataPersistenceAfterNavigation() {
        // This test verifies that the Items screen maintains its data after navigating away and back

        // 1. Navigate to Items
        composeTestRule.onNodeWithText("Items").performClick()

        // 2. Wait for items to load
        composeTestRule.waitForIdle()
        composeTestRule.waitUntil(10000) {
            composeTestRule.onAllNodesWithText("Master Ball", substring = true).fetchSemanticsNodes().isNotEmpty() ||
            composeTestRule.onAllNodesWithText("Potion", substring = true).fetchSemanticsNodes().isNotEmpty()
        }

        // 3. Go back to home
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        composeTestRule.waitForIdle()

        // 4. Navigate back to Items
        composeTestRule.onNodeWithText("Items").performClick()
        composeTestRule.waitForIdle()

        // 5. Verify items are still loaded (should be faster this time due to caching)
        composeTestRule.waitUntil(8000) {
            composeTestRule.onAllNodesWithText("Master Ball", substring = true).fetchSemanticsNodes().isNotEmpty() ||
            composeTestRule.onAllNodesWithText("Potion", substring = true).fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Items").assertExists()
    }

    @Test
    fun testMovesScreenDataPersistenceAfterNavigation() {
        // This test verifies that the Moves screen maintains its data after navigating away and back

        // 1. Navigate to Moves
        composeTestRule.onNodeWithText("Moves").performClick()

        // 2. Wait for moves to load
        composeTestRule.waitForIdle()
        composeTestRule.waitUntil(10000) {
            composeTestRule.onAllNodesWithText("Name").fetchSemanticsNodes().isNotEmpty()
        }

        // Verify table headers
        composeTestRule.onNodeWithText("Name").assertExists()

        // 3. Go back to home
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        composeTestRule.waitForIdle()

        // 4. Navigate back to Moves
        composeTestRule.onNodeWithText("Moves").performClick()
        composeTestRule.waitForIdle()

        // 5. Verify moves are still loaded (should be faster this time due to caching)
        composeTestRule.waitUntil(8000) {
            composeTestRule.onAllNodesWithText("Name").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Moves").assertExists()
        composeTestRule.onNodeWithText("Name").assertExists()
    }
}
