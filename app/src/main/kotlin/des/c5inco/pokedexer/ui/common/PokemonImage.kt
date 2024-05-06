package des.c5inco.pokedexer.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.imageLoader
import des.c5inco.pokedexer.data.pokemon.placeholderPokemonImage

@Composable
fun PokemonImage(
    modifier: Modifier = Modifier,
    image: Int,
    description: String? = null,
    tint: Color? = null
) {
    AsyncImage(
        model = artworkUrl(image),
        placeholder = painterResource(id = placeholderPokemonImage(image)),
        imageLoader = LocalContext.current.imageLoader,
        contentDescription = description,
        colorFilter = tint?.let { ColorFilter.tint(it) },
        modifier = modifier,
    )
}