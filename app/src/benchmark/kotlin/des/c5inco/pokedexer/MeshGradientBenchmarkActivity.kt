package des.c5inco.pokedexer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.ui.common.legacyMeshGradient
import des.c5inco.pokedexer.ui.common.meshGradient
import kotlin.math.roundToInt

class MeshGradientBenchmarkActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val useLegacy = intent.getStringExtra(ModeExtra) == LegacyMode

        setContent {
            val transition = rememberInfiniteTransition(label = "mesh-gradient-benchmark")
            val animatedX =
                transition.animateFloat(
                    initialValue = -48f,
                    targetValue = 48f,
                    animationSpec =
                        InfiniteRepeatableSpec(
                            animation = tween(durationMillis = 1_000),
                            repeatMode = RepeatMode.Reverse,
                        ),
                    label = "animated-x",
                )
            val points = benchmarkPoints()
            val gradientModifier =
                if (useLegacy) {
                    Modifier.legacyMeshGradient(points = points, resolutionX = 10, resolutionY = 10)
                } else {
                    Modifier.meshGradient(points = points)
                }

            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Box(
                    gradientModifier
                        // Match LazyGrid scrolling: move during layout without redrawing the mesh.
                        .offset { IntOffset(animatedX.value.roundToInt(), 0) }
                        .size(width = 200.dp, height = 124.dp)
                )
                Text(if (useLegacy) LegacyReadyText else NativeReadyText)
            }
        }
    }

    companion object {
        const val ModeExtra = "mesh_mode"
        const val LegacyMode = "legacy"
        const val NativeMode = "native"
        const val LegacyReadyText = "mesh-legacy-ready"
        const val NativeReadyText = "mesh-native-ready"
    }
}

private fun benchmarkPoints(): List<List<Pair<Offset, Color>>> =
    listOf(
        listOf(
            Offset(0f, 0f) to Color(0xFFE91E63),
            Offset(0.24f, 0f) to Color(0xFFE91E63),
            Offset(0.54f, 0f) to Color(0xFFE91E63),
            Offset(0.79f, 0f) to Color(0xFFE91E63),
            Offset(1f, 0f) to Color(0xFF9C27B0),
        ),
        listOf(
            Offset(0f, 0.5f) to Color(0xFFE91E63),
            Offset(0.24f, 0.63f) to Color(0xFF9C27B0),
            Offset(0.53f, 0.42f) to Color(0xFF9C27B0),
            Offset(0.8f, 0.67f) to Color(0xFFE91E63),
            Offset(1f, 0.36f) to Color(0xFF9C27B0),
        ),
        listOf(
            Offset(0f, 1f) to Color(0xFF9C27B0),
            Offset(0.24f, 1f) to Color(0xFF9C27B0),
            Offset(0.52f, 1f) to Color(0xFF9C27B0),
            Offset(0.79f, 1f) to Color(0xFF9C27B0),
            Offset(1f, 1f) to Color(0xFF9C27B0),
        ),
    )
