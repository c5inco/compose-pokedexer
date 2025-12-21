package des.c5inco.pokedexer.ui.common

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import des.c5inco.pokedexer.LocalGifImageLoader
import des.c5inco.pokedexer.R

@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val imageLoader = LocalGifImageLoader.current
    val imageSize = 56.dp

    var containerWidth by remember { mutableIntStateOf(0) }

    val infiniteTransition = rememberInfiniteTransition(label = "pika_loader_transition")
    val xOffset by infiniteTransition.animateFloat(
        initialValue = -containerWidth.toFloat(),
        targetValue = containerWidth.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pika_loader_offset"
    )

    Layout(
        content = {
            AsyncImage(
                model = ImageRequest.Builder(context).data(R.drawable.pika_loader).build(),
                contentDescription = "Loading",
                imageLoader = imageLoader,
                modifier = Modifier.size(imageSize),
            )
        },
        modifier = modifier.fillMaxWidth().padding(vertical = 24.dp)
    ) { measurables, constraints ->
        val placeable = measurables.first().measure(constraints)
        containerWidth = constraints.maxWidth

        layout(constraints.maxWidth, placeable.height) {
            placeable.placeRelative(x = xOffset.toInt(), y = 0)
        }
    }
}
