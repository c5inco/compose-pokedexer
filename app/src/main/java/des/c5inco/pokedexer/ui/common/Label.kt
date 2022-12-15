package des.c5inco.pokedexer.ui.common

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import des.c5inco.pokedexer.ui.theme.NeutralBlue

@Composable
fun Label(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        text = text,
        modifier = modifier,
        color = NeutralBlue.copy(alpha = 0.6f)
    )
}