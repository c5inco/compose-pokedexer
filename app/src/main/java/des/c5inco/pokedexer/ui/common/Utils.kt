package des.c5inco.pokedexer.ui.common

import androidx.compose.foundation.border
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.intellij.lang.annotations.Language
import java.lang.Integer.min

fun formatId(id: Int): String = "#" + "$id".padStart(3, '0')

fun artworkUrl(id: Int): String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png"
fun artworkUrlSvg(id: Int): String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/${id}.svg"

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

fun placeholderPokemonImage(id: Int): Int {
    val sampleImages = listOf(
        R.drawable.poke001,
        R.drawable.poke002,
        R.drawable.poke003,
        R.drawable.poke004,
        R.drawable.poke005,
        R.drawable.poke006,
        R.drawable.poke007,
        R.drawable.poke008,
        R.drawable.poke009,
        R.drawable.poke010,
    )
    return sampleImages[min(id, 10) - 1]
}

@Language("AGSL")
val PROGRESSIVE_TINT_SHADER = """
    uniform float progress;
    uniform shader contents; 
    
    vec4 main(in vec2 fragCoord) {
        const vec4 finalColor = vec4(0,0,0,0.4);
        vec4 currentValue = contents.eval(fragCoord);
        
        if (currentValue.w > 0) {
            return mix(currentValue, finalColor, progress);
        } else {
            return currentValue;
        }
    }
""".trimIndent()