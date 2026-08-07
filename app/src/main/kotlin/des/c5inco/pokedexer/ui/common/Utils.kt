package des.c5inco.pokedexer.ui.common

import androidx.compose.foundation.border
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val POKEDEX_ID_DIGITS = 3
private const val DEGREES_IN_HALF_ROTATION = 180
private const val DEFAULT_ANALOGOUS_ANGLE_DEGREES = 15f
private const val HSL_COMPONENT_COUNT = 3
private const val MAX_RGB_CHANNEL_VALUE = 255
private const val FULL_HUE_ROTATION_DEGREES = 360f
private const val OPPOSITE_ANALOGOUS_HUE_MULTIPLIER = 2f
private const val LOOP_DELAY_MILLIS = 1000L
private const val LOOP_LAST_INDEX = 8

fun formatId(id: Int): String = "#" + "$id".padStart(POKEDEX_ID_DIGITS, '0')

fun artworkUrl(id: Int): String =
    "https://assets.pokemon.com/assets/cms2/img/pokedex/full/" +
        "${id.toString().padStart(POKEDEX_ID_DIGITS, '0')}.png"

fun itemAssetsUri(name: String): String = assetsUri("items", "$name.webp")

private fun assetsUri(subDirectory: String? = null, name: String): String {
    val baseUri = "file:///android_asset"
    return subDirectory?.let { "$baseUri/$subDirectory/$name" } ?: run { "$baseUri/$name" }
}

fun Double.toRadian(): Double = this / DEGREES_IN_HALF_ROTATION * Math.PI

fun Modifier.debugBounds(width: Dp = 1.dp) = border(width, Color.Magenta)

fun Canvas.drawPathWithPaint(path: Path, paint: Paint = Paint()) = drawPath(path, paint)

fun calculateAnalogousColors(
    baseColor: Color,
    angle: Float = DEFAULT_ANALOGOUS_ANGLE_DEGREES,
): List<Color> {
    // Convert the base color to HSL
    val hsl = FloatArray(HSL_COMPONENT_COUNT)
    ColorUtils.RGBToHSL(
        (baseColor.red * MAX_RGB_CHANNEL_VALUE).toInt(),
        (baseColor.green * MAX_RGB_CHANNEL_VALUE).toInt(),
        (baseColor.blue * MAX_RGB_CHANNEL_VALUE).toInt(),
        hsl,
    )

    // Calculate the four analogous hues
    val hue1 = (hsl[0] + angle) % FULL_HUE_ROTATION_DEGREES
    val hue2 = (hsl[0] - angle) % FULL_HUE_ROTATION_DEGREES
    val hue3 = (hsl[0] + angle * OPPOSITE_ANALOGOUS_HUE_MULTIPLIER) % FULL_HUE_ROTATION_DEGREES
    val hue4 = (hsl[0] - angle * OPPOSITE_ANALOGOUS_HUE_MULTIPLIER) % FULL_HUE_ROTATION_DEGREES
    val analogousHues = listOf(hue1, hue2, hue3, hue4)

    // Create analogous colors
    val analogousColors = analogousHues.map { hue ->
        val newHsl = hsl.copyOf()
        newHsl[0] = hue
        Color(ColorUtils.HSLToColor(newHsl))
    }

    return analogousColors
}

val infiniteLoopFlow: Flow<Int> = flow {
    while (true) {
        delay(LOOP_DELAY_MILLIS)
        emit((0..LOOP_LAST_INDEX).random())
    }
}

@Preview(name = "Phone", device = "spec:width=411dp,height=891dp")
@Preview(name = "Phone-Landscape", device = "spec:width=411dp,height=891dp,orientation=landscape")
@Preview(name = "Foldable", device = "spec:width=673dp,height=841dp")
@Preview(name = "Tablet", device = "spec:width=1280dp,height=800dp,dpi=240")
@Preview(name = "Desktop", device = "spec:width=1920dp,height=1080dp,dpi=160")
annotation class ReferenceDevicePreviews

@Preview(name = "Default", fontScale = 1.0f)
@Preview(name = "Small", fontScale = 0.85f)
@Preview(name = "Large", fontScale = 1.15f)
@Preview(name = "Largest", fontScale = 1.3f)
annotation class FontScalePreviews
