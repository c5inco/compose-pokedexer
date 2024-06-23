package des.c5inco.pokedexer.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.imageLoader
import des.c5inco.pokedexer.R
import des.c5inco.pokedexer.model.Item

@Composable
fun ItemImage(
    modifier: Modifier = Modifier,
    item: Item
) {
    AsyncImage(
        model = itemAssetsUri(item.sprite),
        placeholder = painterResource(id = R.drawable.item_flame_orb),
        imageLoader = LocalContext.current.imageLoader,
        contentDescription = null,
        contentScale = ContentScale.Inside,
        modifier = modifier
    )
}