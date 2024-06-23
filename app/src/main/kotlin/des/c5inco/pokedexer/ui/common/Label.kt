package des.c5inco.pokedexer.ui.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun Label(
    modifier: Modifier = Modifier,
    text: String,
    emphasis: Emphasis = Emphasis.Medium
) {
    Text(
        text = text,
        modifier = modifier.graphicsLayer {
            alpha = emphasis.alpha
        },
    )
}

enum class Emphasis(val alpha: Float) {
    Disabled(0.5f),
    Medium(0.7f),
    High(1f)
}