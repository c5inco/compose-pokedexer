package des.c5inco.pokedexer.ui.common

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import des.c5inco.pokedexer.LocalGifImageLoader
import des.c5inco.pokedexer.R

private const val LOADING_INDICATOR_DURATION_MILLIS = 2_000

@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val imageLoader = LocalGifImageLoader.current
    val imageSize = 56.dp
    val imageSizePx = with(density) { imageSize.toPx() }

    val containerWidthPx = remember { mutableIntStateOf(0) }

    val infiniteTransition = rememberInfiniteTransition(label = "pika_loader_transition")
    val progress =
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec =
                infiniteRepeatable(
                    animation =
                        tween(
                            durationMillis = LOADING_INDICATOR_DURATION_MILLIS,
                            easing = LinearEasing,
                        ),
                    repeatMode = RepeatMode.Restart,
                ),
            label = "pika_loader_progress",
        )

    Box(
        modifier =
            modifier.fillMaxWidth().height(imageSize).clipToBounds().onSizeChanged {
                containerWidthPx.intValue = it.width
            }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context).data(R.drawable.pika_loader).build(),
            contentDescription = "Loading",
            imageLoader = imageLoader,
            placeholder = painterResource(R.drawable.pika_loader_placeholder),
            modifier =
                Modifier.size(imageSize).graphicsLayer {
                    translationX =
                        loadingIndicatorTranslationX(
                            progress = progress.value,
                            imageWidth = imageSizePx,
                            containerWidth = containerWidthPx.intValue.toFloat(),
                        )
                },
        )
    }
}

internal fun loadingIndicatorTranslationX(
    progress: Float,
    imageWidth: Float,
    containerWidth: Float,
): Float = -imageWidth + (containerWidth + imageWidth) * progress
