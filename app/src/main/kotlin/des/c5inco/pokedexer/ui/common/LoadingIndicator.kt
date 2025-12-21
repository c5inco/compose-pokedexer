package des.c5inco.pokedexer.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import des.c5inco.pokedexer.LocalGifImageLoader
import des.c5inco.pokedexer.R

@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Box(modifier.padding(vertical = 24.dp)) {
        val context = LocalContext.current
        val imageLoader = LocalGifImageLoader.current

        AsyncImage(
            model = ImageRequest.Builder(context).data(R.drawable.pika_loader).build(),
            contentDescription = "Loading",
            imageLoader = imageLoader,
            modifier = Modifier.size(64.dp),
        )
    }
}
