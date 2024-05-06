package des.c5inco.pokedexer.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import des.c5inco.pokedexer.model.Move
import des.c5inco.pokedexer.model.categoryIcon
import des.c5inco.pokedexer.ui.theme.PokemonColors

@Composable
fun CategoryIcon(
    modifier: Modifier = Modifier,
    move: Move
) {
    Box(
        modifier,
        contentAlignment = Alignment.Center
    ) {
        Icon(painter = painterResource(id = move.categoryIcon()),
            contentDescription = move.category,
            tint = when (move.category.lowercase()) {
                "physical" -> PokemonColors.Fire
                "special" -> PokemonColors.Dragon
                else -> PokemonColors.Dark
            },
            modifier = Modifier.graphicsLayer {
                rotationX = 40f
                rotationY = -15f
            })
    }
}