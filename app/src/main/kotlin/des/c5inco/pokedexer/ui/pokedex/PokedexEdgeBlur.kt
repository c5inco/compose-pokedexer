package des.c5inco.pokedexer.ui.pokedex

import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.blur.BlurRadiusSpec
import androidx.compose.ui.graphics.blur.BlurStop
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private const val FADE_SAMPLES = 6

/**
 * Shape of the clear region between the blurred edges: a straight [Band], or an inverted [Pill]
 * whose rounded corners curve the blur boundary at the top and bottom.
 */
internal enum class EdgeBlurShape {
    Band,
    Pill,
}

/** Tunable parameters for the Pokedex list edge blur. Defaults are the shipped look. */
internal data class EdgeBlurTuning(
    val shape: EdgeBlurShape = EdgeBlurShape.Band,
    val cornerRadius: Dp = 160.dp,
    val topRadius: Dp = 24.dp,
    val topFade: Dp = 48.dp,
    val scrollLinked: Boolean = true,
    val scrollRamp: Dp = 48.dp,
    val bottomRadius: Dp = 24.dp,
    val bottomFade: Dp = 48.dp,
)

@Stable
internal class EdgeBlurTuningState {
    var tuning by mutableStateOf(EdgeBlurTuning())
    var panelVisible by mutableStateOf(false)
}

/**
 * Where the edge blur anchors on the list: the top bar's current height via [innerPadding] and the
 * distance above the bottom edge where the filter button blur begins.
 */
internal data class EdgeBlurAnchors(val innerPadding: PaddingValues, val bottomBlurStart: Dp)

/** One blurred edge: [radius] across [extent] from the edge, easing out over [fade] either side. */
internal data class EdgeBlurBand(val extent: Dp, val fade: Dp, val radius: Dp)

/**
 * Bottom band whose blur begins exactly [start] above the bottom edge and eases in over [fade]
 * below it, so the start line holds still while the fade is tuned.
 */
internal fun bottomEdgeBlurBand(start: Dp, fade: Dp, radius: Dp): EdgeBlurBand =
    EdgeBlurBand(extent = (start - fade).coerceAtLeast(0.dp), fade = fade, radius = radius)

/**
 * Blur stops for a surface of [height] that blur at both edges and ease to no blur toward the
 * middle. Each fade follows a smoothstep curve spanning the band's fade on either side of its
 * extent, so the radius is half of the band's radius exactly at its extent. Overlapping bands are
 * clamped so fractions never decrease.
 */
internal fun pokedexEdgeBlurStops(
    height: Dp,
    top: EdgeBlurBand,
    bottom: EdgeBlurBand,
): List<BlurStop> {
    val samples = (0 until FADE_SAMPLES).map { it / (FADE_SAMPLES - 1f) }
    val topPoints = samples.map { t ->
        (top.extent - top.fade + top.fade * 2 * t) / height to top.radius * (1f - smoothstep(t))
    }
    val bottomPoints = samples.map { t ->
        1f - (bottom.extent + bottom.fade - bottom.fade * 2 * t) / height to
            bottom.radius * smoothstep(t)
    }
    val points = listOf(0f to top.radius) + topPoints + bottomPoints + listOf(1f to bottom.radius)
    val fractions =
        points
            .map { it.first }
            .runningReduce { previous, fraction -> fraction.coerceIn(previous, 1f) }
            .map { it.coerceIn(0f, 1f) }
    return fractions.zip(points) { fraction, point -> BlurStop(fraction, point.second) }
}

/**
 * How much of the top blur to apply, from 0 while the list rests at the top to 1 once content has
 * scrolled [rampPx] beneath the top app bar.
 */
internal fun topBlurProgress(
    firstVisibleItemIndex: Int,
    scrollOffsetPx: Int,
    rampPx: Float,
): Float =
    when {
        firstVisibleItemIndex > 0 -> 1f
        rampPx <= 0f -> if (scrollOffsetPx > 0) 1f else 0f
        else -> (scrollOffsetPx / rampPx).coerceIn(0f, 1f)
    }

private fun smoothstep(t: Float): Float = t * t * (1f + 2f * (1f - t))

/** Rounded clear region of an inverted pill mask, in pixels. */
internal data class InvertedPillGeometry(
    val center: Offset,
    val halfSize: Size,
    val cornerRadius: Float,
)

/**
 * Clear region spanning from [topExtent] below the top edge to [bottomExtent] above the bottom
 * edge. The sides extend [sideOverflow] past the surface so only the top and bottom blur, and the
 * corner radius is clamped so the largest value forms a full dome.
 */
internal fun invertedPillGeometry(
    size: Size,
    topExtent: Float,
    bottomExtent: Float,
    sideOverflow: Float,
    cornerRadius: Float,
): InvertedPillGeometry {
    val clearBottom = size.height - bottomExtent
    val halfSize =
        Size(size.width / 2f + sideOverflow, ((clearBottom - topExtent) / 2f).coerceAtLeast(0f))
    return InvertedPillGeometry(
        center = Offset(size.width / 2f, (topExtent + clearBottom) / 2f),
        halfSize = halfSize,
        cornerRadius = cornerRadius.coerceIn(0f, halfSize.minDimension),
    )
}

/**
 * Progressively blurs the list where it scrolls beneath the transparent top app bar and the filter
 * button. Scroll position is read inside the blur layer so scrolling only updates the layer.
 */
internal fun Modifier.pokedexEdgeBlur(
    tuning: EdgeBlurTuning,
    listState: LazyGridState,
    anchors: EdgeBlurAnchors,
    pillMask: InvertedPillBlurMask?,
): Modifier = blur {
    val progress =
        if (tuning.scrollLinked) {
            topBlurProgress(
                listState.firstVisibleItemIndex,
                listState.firstVisibleItemScrollOffset,
                tuning.scrollRamp.toPx(),
            )
        } else {
            1f
        }
    val top =
        EdgeBlurBand(
            extent = anchors.innerPadding.calculateTopPadding(),
            fade = tuning.topFade,
            radius = tuning.topRadius * progress,
        )
    val bottom =
        bottomEdgeBlurBand(
            start = anchors.bottomBlurStart,
            fade = tuning.bottomFade,
            radius = tuning.bottomRadius,
        )
    val maxRadius = maxOf(top.radius, bottom.radius)
    if (size.height > 0.dp && maxRadius > 0.dp) {
        radius =
            if (
                tuning.shape == EdgeBlurShape.Pill &&
                    pillMask != null &&
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
            ) {
                BlurRadiusSpec.shader(maxRadius) { sizePx ->
                    pillMask.update(
                        geometry =
                            invertedPillGeometry(
                                size = sizePx,
                                topExtent = top.extent.toPx(),
                                bottomExtent = bottom.extent.toPx(),
                                sideOverflow = maxOf(top.fade, bottom.fade).toPx(),
                                cornerRadius = tuning.cornerRadius.toPx(),
                            ),
                        fade = Offset(top.fade.toPx(), bottom.fade.toPx()),
                        strength = Offset(top.radius / maxRadius, bottom.radius / maxRadius),
                    )
                }
            } else {
                BlurRadiusSpec.verticalGradient(pokedexEdgeBlurStops(size.height, top, bottom))
            }
    }
}

@Composable
internal fun rememberInvertedPillBlurMask(): InvertedPillBlurMask? = remember {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) InvertedPillBlurMask() else null
}

/**
 * Blur intensity mask that is clear inside a rounded rectangle and eases to full intensity outside
 * it, so equal-blur contours curve around the clear region's corners. The top and bottom halves use
 * their own fade distance and strength.
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
internal class InvertedPillBlurMask {
    private val shader = RuntimeShader(INVERTED_PILL_MASK_AGSL)

    /** [fade] and [strength] hold the top value in `x` and the bottom value in `y`. */
    fun update(geometry: InvertedPillGeometry, fade: Offset, strength: Offset): Shader {
        shader.setFloatUniform("center", geometry.center.x, geometry.center.y)
        shader.setFloatUniform("halfSize", geometry.halfSize.width, geometry.halfSize.height)
        shader.setFloatUniform("cornerRadius", geometry.cornerRadius)
        shader.setFloatUniform("fade", fade.x, fade.y)
        shader.setFloatUniform("strength", strength.x, strength.y)
        return shader
    }
}

// Signed distance to the rounded clear region, eased with smoothstep so the blur reaches half
// strength exactly on the region's edge, matching the band gradient.
private const val INVERTED_PILL_MASK_AGSL =
    """
    uniform float2 center;
    uniform float2 halfSize;
    uniform float cornerRadius;
    uniform float2 fade;
    uniform float2 strength;

    half4 main(float2 position) {
        float2 d = abs(position - center) - halfSize + cornerRadius;
        float distance = length(max(d, 0.0)) + min(max(d.x, d.y), 0.0) - cornerRadius;
        bool isTop = position.y < center.y;
        float edgeFade = max(isTop ? fade.x : fade.y, 0.5);
        float t = clamp((distance + edgeFade) / (2.0 * edgeFade), 0.0, 1.0);
        float intensity = t * t * (3.0 - 2.0 * t) * (isTop ? strength.x : strength.y);
        return half4(0.0, 0.0, 0.0, intensity);
    }
    """
