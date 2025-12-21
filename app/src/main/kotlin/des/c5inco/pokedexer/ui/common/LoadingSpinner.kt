package des.c5inco.pokedexer.ui.common

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import des.c5inco.pokedexer.R

/**
 * A loading spinner component that displays an animated Pikachu GIF.
 * This component is used consistently across the app when loading data from the network.
 */
@Composable
fun LoadingSpinner(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(context)
                    .data(R.drawable.pika_loader)
                    .build(),
                imageLoader = imageLoader
            ),
            contentDescription = "Loading",
            modifier = Modifier.size(120.dp)
        )
    }
}
