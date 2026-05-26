package des.c5inco.pokedexer.ui.navigation

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class PokedexerBottomBarTest {
    @get:Rule val composeTestRule = createComposeRule()

    @Test
    fun bottomBar_appliesModifierToNavigationBar() {
        composeTestRule.setContent {
            pokedexerBottomBar(
                currentScreen = Screen.Pokedex,
                onDestinationSelected = {},
                modifier = Modifier.testTag("pokedexerBottomBar"),
            )
        }

        composeTestRule.onNodeWithTag("pokedexerBottomBar").assertExists()
    }
}
