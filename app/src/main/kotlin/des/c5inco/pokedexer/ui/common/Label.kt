package des.c5inco.pokedexer.ui.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

private const val DISABLED_EMPHASIS_ALPHA = 0.5f
private const val MEDIUM_EMPHASIS_ALPHA = 0.7f

@Composable
fun Label(modifier: Modifier = Modifier, text: String, emphasis: Emphasis = Emphasis.Medium) {
    Text(text = text, modifier = modifier.graphicsLayer { alpha = emphasis.alpha })
}

enum class Emphasis(val alpha: Float) {
    Disabled(DISABLED_EMPHASIS_ALPHA),
    Medium(MEDIUM_EMPHASIS_ALPHA),
    High(1f),
}
