package des.c5inco.pokedexer.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

private const val ARTWORK_ASSET_DIRECTORY = "wc"

@Composable
fun rememberArtworkModel(id: Int): String {
    val context = LocalContext.current
    return remember(context, id) { artworkModel(id, context.assets.list(ARTWORK_ASSET_DIRECTORY)) }
}

fun artworkUrl(id: Int): String =
    "https://assets.pokemon.com/assets/cms2/img/pokedex/full/${id.toString().padStart(3, '0')}.png"

fun localArtworkAssetPath(id: Int): String =
    "$ARTWORK_ASSET_DIRECTORY/${id.toString().padStart(3, '0')}.webp"

fun artworkModel(id: Int, assets: Array<String>?): String {
    val localArtworkAssetPath = localArtworkAssetPath(id)
    return if (localArtworkExists(localArtworkAssetPath, assets)) {
        assetsUri(name = localArtworkAssetPath)
    } else {
        artworkUrl(id)
    }
}

fun localArtworkExists(assetPath: String, assets: Array<String>?): Boolean {
    val assetName = assetPath.substringAfterLast('/')
    return assets?.contains(assetName) == true
}
