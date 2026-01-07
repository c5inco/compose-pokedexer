package des.c5inco.pokedexer.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import des.c5inco.pokedexer.shared.model.Move
import des.c5inco.pokedexer.shared.model.MoveCategory
import des.c5inco.pokedexer.model.categoryIcon
import des.c5inco.pokedexer.ui.theme.MoveCategoryTheme
import des.c5inco.pokedexer.ui.theme.PokemonColors

@Composable
fun CategoryIcon(
    modifier: Modifier = Modifier,
    move: Move
) {
    MoveCategoryTheme(
        category = MoveCategory.valueOf(move.category)
    ) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = move.categoryIcon()),
                contentDescription = move.category,
                tint = MoveCategoryTheme.colorScheme.primary,
                modifier = Modifier
                    .matchParentSize()
                    .graphicsLayer {
                        rotationX = 40f
                        rotationY = -15f
                    })
        }
    }
}