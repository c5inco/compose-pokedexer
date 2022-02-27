package des.c5inco.pokedexer.ui.common

import androidx.compose.foundation.border
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun formatId(id: Int): String = "#" + "$id".padStart(3, '0')

fun Double.toRadian(): Double = this / 180 * Math.PI

fun Modifier.debugBounds(width: Dp = 1.dp) =
    border(width, Color.Magenta)