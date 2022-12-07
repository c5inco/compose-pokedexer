package des.c5inco.pokedexer.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun PokemonImage(
    modifier: Modifier = Modifier,
    image: Int,
) {
    CoilImage(
        imageModel = { artworkUrl(image) },
        previewPlaceholder = placeholderPokemonImage(image),
        modifier = modifier
    )
}