package des.c5inco.pokedexer.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.ui.theme.AppTheme
import des.c5inco.pokedexer.ui.theme.Grey100

@Composable
fun PokeBallBackground(
    modifier: Modifier = Modifier,
    tint: Color = Grey100
) {
    Box(
        modifier.size(240.dp),
    ) {
        PokeBallLarge(tint = tint)
    }
}

@Preview
@Composable
fun PokeBallBackgroundPreview() {
    AppTheme {
        Surface(Modifier.fillMaxSize()) {
            PokeBallBackground()
        }
    }
}