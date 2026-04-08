package des.c5inco.pokedexer.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.R

private val BottomNavIconSize = 24.dp

@Composable
fun pokedexerBottomBar(currentScreen: Screen, onDestinationSelected: (Screen) -> Unit) {
    NavigationBar {
        bottomNavDestinations.forEach { destination ->
            NavigationBarItem(
                selected = destination.screen == currentScreen,
                onClick = { onDestinationSelected(destination.screen) },
                icon = {
                    Icon(
                        painter = painterResource(destination.iconRes),
                        contentDescription = stringResource(destination.labelRes),
                        modifier = Modifier.size(BottomNavIconSize),
                    )
                },
                label = { Text(text = stringResource(destination.labelRes)) },
            )
        }
    }
}

private data class PokedexerBottomBarDestination(
    val screen: Screen,
    @param:StringRes val labelRes: Int,
    @param:DrawableRes val iconRes: Int,
)

private val bottomNavDestinations =
    listOf(
        PokedexerBottomBarDestination(
            screen = Screen.Pokedex,
            labelRes = R.string.pokedexLabel,
            iconRes = R.drawable.ic_catching_pokemon,
        ),
        PokedexerBottomBarDestination(
            screen = Screen.Moves,
            labelRes = R.string.movesLabel,
            iconRes = R.drawable.ic_fitness_center,
        ),
        PokedexerBottomBarDestination(
            screen = Screen.Items,
            labelRes = R.string.itemsLabel,
            iconRes = R.drawable.ic_store_mall_directory,
        ),
        PokedexerBottomBarDestination(
            screen = Screen.TypeCharts,
            labelRes = R.string.typeChartsNavLabel,
            iconRes = R.drawable.ic_genetics,
        ),
    )
