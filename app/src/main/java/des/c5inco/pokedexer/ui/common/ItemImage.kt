package des.c5inco.pokedexer.ui.common

import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.core.graphics.drawable.toBitmap
import com.skydoves.landscapist.coil.CoilImage
import des.c5inco.pokedexer.R
import des.c5inco.pokedexer.model.Item

@Composable
fun ItemImage(
    modifier: Modifier = Modifier,
    item: Item
) {
    CoilImage(
        imageModel = { itemAssetsUri(item.sprite) },
        previewPlaceholder = R.drawable.item_flame_orb,
        success = { _, painter ->
            Image(
                painter = painter,
                contentScale = ContentScale.Inside,
                contentDescription = null,
                modifier = Modifier.matchParentSize()
            )
        },
        failure = {
            PokeBall(
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                alpha = Emphasis.Disabled.alpha
            )
        },
        modifier = modifier
    )
}