package des.c5inco.pokedexer.journeys

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeDown
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeRight
import androidx.compose.ui.test.swipeUp
import des.c5inco.pokedexer.MainActivity
import org.junit.Rule
import org.junit.Test

class ScreenVerificationTest {
    @get:Rule val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testItemsScreenNavigationAndScrolling() {
        // 1. Open Items screen from bottom navigation
        composeTestRule.onNodeWithText("Items").performClick()

        // 2. Verify we are on Items screen
        composeTestRule.waitForIdle()
        composeTestRule.waitUntil(8000) {
            composeTestRule.onAllNodesWithText("Items").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Items").assertExists()

        // 3. Wait for content to load
        composeTestRule.waitUntil(10000) {
            composeTestRule
                .onAllNodesWithText("Master Ball", substring = true)
                .fetchSemanticsNodes()
                .isNotEmpty() ||
                composeTestRule
                    .onAllNodesWithText("Potion", substring = true)
                    .fetchSemanticsNodes()
                    .isNotEmpty()
        }

        // 4. Verify scrolling works
        repeat(5) {
            composeTestRule.onNodeWithTag("ItemsLazyColumn").performTouchInput {
                swipeUp(startY = this.centerY, endY = this.centerY - 1000f)
            }
            composeTestRule.waitForIdle()
        }

        // 5. Scroll back up to the top
        repeat(5) {
            composeTestRule.onNodeWithTag("ItemsLazyColumn").performTouchInput {
                swipeDown(startY = this.centerY, endY = this.centerY + 1000f)
            }
            composeTestRule.waitForIdle()
        }

        composeTestRule.onNodeWithText("Pokédex").assertExists()
        composeTestRule.onNodeWithText("Moves").assertExists()
        composeTestRule.onNodeWithText("Items").assertExists()
        composeTestRule.onNodeWithText("Type Charts").assertExists()
    }

    @Test
    fun testMovesScreenNavigationAndScrolling() {
        // 1. Open Moves screen from bottom navigation
        composeTestRule.onNodeWithText("Moves").performClick()

        // 2. Verify we are on Moves screen
        composeTestRule.waitForIdle()
        composeTestRule.waitUntil(8000) {
            composeTestRule.onAllNodesWithText("Moves").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Moves").assertExists()

        // 3. Wait for table headers to appear
        composeTestRule.waitUntil(10000) {
            composeTestRule.onAllNodesWithText("Name").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Name").assertExists()
        composeTestRule.onNodeWithText("Type").assertExists()
        composeTestRule.onNodeWithText("Category").assertExists()
        composeTestRule.onNodeWithText("Power").assertExists()
        composeTestRule.onNodeWithText("Accuracy").assertExists()

        // 4. Wait for move data to load
        composeTestRule.waitUntil(10000) {
            composeTestRule
                .onAllNodesWithText("Pound", substring = true)
                .fetchSemanticsNodes()
                .isNotEmpty() ||
                composeTestRule
                    .onAllNodesWithText("Scratch", substring = true)
                    .fetchSemanticsNodes()
                    .isNotEmpty()
        }

        // 5. Verify scrolling works
        repeat(10) {
            composeTestRule.onNodeWithTag("MovesLazyColumn").performTouchInput {
                swipeUp(startY = this.centerY, endY = this.centerY - 1200f)
            }
            composeTestRule.waitForIdle()
        }

        // 6. Scroll back up to the top
        repeat(10) {
            composeTestRule.onNodeWithTag("MovesLazyColumn").performTouchInput {
                swipeDown(startY = this.centerY, endY = this.centerY + 1200f)
            }
            composeTestRule.waitForIdle()
        }

        composeTestRule.onNodeWithText("Pokédex").assertExists()
        composeTestRule.onNodeWithText("Moves").assertExists()
        composeTestRule.onNodeWithText("Items").assertExists()
        composeTestRule.onNodeWithText("Type Charts").assertExists()
    }

    @Test
    fun testTypeChartScreenNavigationAndScrolling() {
        // 1. Open Type charts screen from bottom navigation
        composeTestRule.onNodeWithText("Type Charts").performClick()

        // 2. Verify we are on Type Chart screen
        composeTestRule.waitForIdle()
        composeTestRule.waitUntil(8000) {
            composeTestRule.onAllNodesWithText("Type Chart").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Type Chart").assertExists()

        // 3. Verify legend items are present
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("2× Super Effective", substring = true).assertExists()
        composeTestRule.onNodeWithText("½× Not Very Effective", substring = true).assertExists()
        composeTestRule.onNodeWithText("0 No Effect", substring = true).assertExists()

        // 4. Verify scrolling works in both directions
        repeat(3) {
            composeTestRule.onNodeWithTag("TypeChartScrollableBox").performTouchInput {
                swipeUp(startY = this.centerY, endY = this.centerY - 800f)
            }
            composeTestRule.waitForIdle()
        }

        repeat(3) {
            composeTestRule.onNodeWithTag("TypeChartScrollableBox").performTouchInput {
                swipeLeft(startX = this.centerX, endX = this.centerX - 800f)
            }
            composeTestRule.waitForIdle()
        }

        repeat(3) {
            composeTestRule.onNodeWithTag("TypeChartScrollableBox").performTouchInput {
                swipeRight(startX = this.centerX, endX = this.centerX + 800f)
            }
            composeTestRule.waitForIdle()
        }

        repeat(3) {
            composeTestRule.onNodeWithTag("TypeChartScrollableBox").performTouchInput {
                swipeDown(startY = this.centerY, endY = this.centerY + 800f)
            }
            composeTestRule.waitForIdle()
        }

        composeTestRule.onNodeWithText("Pokédex").assertExists()
        composeTestRule.onNodeWithText("Moves").assertExists()
        composeTestRule.onNodeWithText("Items").assertExists()
        composeTestRule.onNodeWithText("Type Charts").assertExists()
    }

    @Test
    fun testItemsScreenDataPersistenceAfterNavigation() {
        // 1. Navigate to Items
        composeTestRule.onNodeWithText("Items").performClick()

        // 2. Wait for items to load
        composeTestRule.waitForIdle()
        composeTestRule.waitUntil(10000) {
            composeTestRule
                .onAllNodesWithText("Master Ball", substring = true)
                .fetchSemanticsNodes()
                .isNotEmpty() ||
                composeTestRule
                    .onAllNodesWithText("Potion", substring = true)
                    .fetchSemanticsNodes()
                    .isNotEmpty()
        }

        // 3. Switch away and back
        composeTestRule.onNodeWithText("Moves").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Items").performClick()
        composeTestRule.waitForIdle()

        // 4. Verify items are still loaded
        composeTestRule.waitUntil(8000) {
            composeTestRule
                .onAllNodesWithText("Master Ball", substring = true)
                .fetchSemanticsNodes()
                .isNotEmpty() ||
                composeTestRule
                    .onAllNodesWithText("Potion", substring = true)
                    .fetchSemanticsNodes()
                    .isNotEmpty()
        }

        composeTestRule.onNodeWithText("Items").assertExists()
    }

    @Test
    fun testMovesScreenDataPersistenceAfterNavigation() {
        // 1. Navigate to Moves
        composeTestRule.onNodeWithText("Moves").performClick()

        // 2. Wait for moves to load
        composeTestRule.waitForIdle()
        composeTestRule.waitUntil(10000) {
            composeTestRule.onAllNodesWithText("Name").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Name").assertExists()

        // 3. Switch away and back
        composeTestRule.onNodeWithText("Items").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Moves").performClick()
        composeTestRule.waitForIdle()

        // 4. Verify moves are still loaded
        composeTestRule.waitUntil(8000) {
            composeTestRule.onAllNodesWithText("Name").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Moves").assertExists()
        composeTestRule.onNodeWithText("Name").assertExists()
    }
}
