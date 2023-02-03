package des.c5inco.pokedexer.ui.common

import androidx.compose.foundation.border
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
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