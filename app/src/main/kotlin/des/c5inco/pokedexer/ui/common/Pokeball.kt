package des.c5inco.pokedexer.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.R
import des.c5inco.pokedexer.ui.theme.AppTheme

@Composable
fun Pokeball(
    modifier: Modifier = Modifier,
    tint: Color = Color.Black,
) {
    Icon(
        modifier = modifier,
        painter = painterResource(id = R.drawable.ic_catching_pokemon),
        contentDescription = "PokeBall",
        tint = tint,
    )
}

@Preview
@Composable
fun PreviewPokeBall() {
    AppTheme {
        Surface {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Pokeball()
                Pokeball(Modifier.size(96.dp))
                Pokeball(Modifier.size(240.dp))
            }
        }
    }
}