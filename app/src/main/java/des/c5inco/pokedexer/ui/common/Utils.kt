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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun formatId(id: Int): String = "#" + "$id".padStart(3, '0')

fun artworkUrl(id: Int): String = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/${id.toString().padStart(3, '0')}.png"

fun Double.toRadian(): Double = this / 180 * Math.PI

fun Modifier.debugBounds(width: Dp = 1.dp) =
    border(width, Color.Magenta)

fun Canvas.drawPathWithPaint(
    path: Path,
    paint: Paint = Paint()
) = drawPath(path, paint)

val infiniteLoopFlow: Flow<Int> = flow {
    while (true) {
        delay(1000L)
        emit((0..8).random())
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