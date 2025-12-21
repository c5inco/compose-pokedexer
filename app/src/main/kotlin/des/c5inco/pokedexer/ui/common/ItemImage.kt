package des.c5inco.pokedexer.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import des.c5inco.pokedexer.R
import des.c5inco.pokedexer.model.Item

@Composable
fun ItemImage(
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Inside,
    item: Item
) {
    AsyncImage(
        model = itemAssetsUri(item.sprite),
        placeholder = painterResource(id = R.drawable.item_flame_orb),
        contentDescription = null,
        contentScale = contentScale,
        modifier = modifier
    )
}
