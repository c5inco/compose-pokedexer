package des.c5inco.pokedexer.ui.common

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.VertexMode
import androidx.compose.ui.graphics.Vertices
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.tooling.preview.Preview
import des.c5inco.pokedexer.ui.theme.AppTheme

private const val POINT_STROKE_WIDTH = 4f * 0.001f
private const val CUBIC_CONTROL_FRACTION = 0.5f

data class MeshGradientOptions(
    val blendMode: BlendMode = BlendMode.DstIn,
    val showPoints: Boolean = false,
    val modifyIndices: (List<Int>) -> List<Int> = { it },
)

@Preview
@Composable
private fun MeshGradientPreview() {
    val indigo700 = Color(0xFF3F51B5)
    val magenta = Color(0xFFFF00FF)
    val midpoint = 0.5f
    val lowerRowY = 0.4f
    val infiniteTransition = rememberInfiniteTransition(label = "meshGradientTransition")
    val animatedPoint by
        infiniteTransition.animateFloat(
            initialValue = .8f,
            targetValue = .1f,
            animationSpec =
                InfiniteRepeatableSpec(
                    animation = tween(durationMillis = 10_000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse,
                ),
            label = "animatedPoint",
        )

    val colors =
        listOf(
            listOf(
                Offset(0f, 0f) to magenta,
                Offset(midpoint, 0f) to magenta,
                Offset(1f, 0f) to magenta,
            ),
            listOf(
                Offset(0f, lowerRowY) to indigo700,
                Offset(midpoint, animatedPoint) to indigo700,
                Offset(1f, lowerRowY) to indigo700,
            ),
            listOf(
                Offset(0f, 1f) to Color.DarkGray,
                Offset(midpoint, 1f) to Color.DarkGray,
                Offset(1f, 1f) to Color.DarkGray,
            ),
        )

    AppTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.DarkGray) {
            Row(Modifier.fillMaxSize()) {
                Column(
                    Modifier.fillMaxSize()
                        .meshGradient(points = colors, resolutionX = 32, resolutionY = 32)
                ) {}
            }
        }
    }
}

// Code from: https://gist.github.com/sinasamaki/05725557c945c5329fdba4a3494aaecb
@Composable
fun Modifier.meshGradient(
    points: List<List<Pair<Offset, Color>>>,
    resolutionX: Int = 1,
    resolutionY: Int = 1,
    options: MeshGradientOptions = MeshGradientOptions(),
): Modifier {
    val pointData by
        remember(points, resolutionX, resolutionY) {
            derivedStateOf { PointData(points, resolutionX, resolutionY) }
        }

    return drawWithCache {
        onDrawBehind {
            drawIntoCanvas { canvas ->
                scale(scaleX = size.width, scaleY = size.height, pivot = Offset.Zero) {
                    canvas.drawVertices(
                        vertices =
                            Vertices(
                                vertexMode = VertexMode.Triangles,
                                positions = pointData.offsets,
                                textureCoordinates = pointData.offsets,
                                colors = pointData.colors,
                                indices = options.modifyIndices(pointData.indices),
                            ),
                        blendMode = options.blendMode,
                        paint = paint,
                    )
                }

                if (options.showPoints) {
                    val flattenedPaint = Paint()
                    flattenedPaint.color = Color.White.copy(alpha = .9f)
                    flattenedPaint.strokeWidth = POINT_STROKE_WIDTH
                    flattenedPaint.strokeCap = StrokeCap.Round
                    flattenedPaint.blendMode = BlendMode.SrcOver

                    scale(scaleX = size.width, scaleY = size.height, pivot = Offset.Zero) {
                        canvas.drawPoints(
                            pointMode = PointMode.Points,
                            points = pointData.offsets,
                            paint = flattenedPaint,
                        )
                    }
                }
            }
        }
    }
}

private class PointData(
    private val points: List<List<Pair<Offset, Color>>>,
    private val stepsX: Int,
    private val stepsY: Int,
) {
    val offsets: MutableList<Offset>
    val colors: MutableList<Color>
    val indices: List<Int>
    private val xLength: Int = (points[0].size * stepsX) - (stepsX - 1)
    private val yLength: Int = (points.size * stepsY) - (stepsY - 1)
    private val measure = PathMeasure()

    init {
        offsets =
            buildList { repeat((xLength - 0) * (yLength - 0)) { add(Offset(0f, 0f)) } }
                .toMutableList()

        colors =
            buildList { repeat((xLength - 0) * (yLength - 0)) { add(Color.Transparent) } }
                .toMutableList()

        indices = buildTriangleIndices(columns = xLength, rows = yLength)
        generateInterpolatedOffsets()
    }

    private fun generateInterpolatedOffsets() {
        generateHorizontalOffsets()
        generateVerticalOffsets()
    }

    private fun generateHorizontalOffsets() {
        for (y in 0..points.lastIndex) {
            for (x in 0..points[y].lastIndex) {
                this[x * stepsX, y * stepsY] = points[y][x].first
                this[x * stepsX, y * stepsY] = points[y][x].second

                if (x == points[y].lastIndex) continue

                val path =
                    cubicPathX(
                        point1 = points[y][x].first,
                        point2 = points[y][x + 1].first,
                        position =
                            when (x) {
                                0 -> 0
                                points[y].lastIndex - 1 -> 2
                                else -> 1
                            },
                    )
                measure.setPath(path, false)

                for (i in 1..<stepsX) {
                    val position = measure.getPosition(i / stepsX.toFloat() * measure.length)
                    this[(x * stepsX) + i, y * stepsY] = Offset(position.x, position.y)
                    this[(x * stepsX) + i, y * stepsY] =
                        lerp(points[y][x].second, points[y][x + 1].second, i / stepsX.toFloat())
                }
            }
        }
    }

    private fun generateVerticalOffsets() {
        for (y in 0..<points.lastIndex) {
            for (x in 0..<this.xLength) {
                val path =
                    cubicPathY(
                        point1 = this[x, y * stepsY].let { Offset(it.x, it.y) },
                        point2 = this[x, (y + 1) * stepsY].let { Offset(it.x, it.y) },
                        position =
                            when (y) {
                                0 -> 0
                                points[y].lastIndex - 1 -> 2
                                else -> 1
                            },
                    )
                measure.setPath(path, false)
                for (i in (1..<stepsY)) {
                    val position = measure.getPosition(i / stepsY.toFloat() * measure.length)
                    val point3 = Offset(position.x, position.y)

                    this[x, ((y * stepsY) + i)] = point3

                    this[x, ((y * stepsY) + i)] =
                        lerp(
                            this.getColor(x, y * stepsY),
                            this.getColor(x, (y + 1) * stepsY),
                            i / stepsY.toFloat(),
                        )
                }
            }
        }
    }

    operator fun get(x: Int, y: Int): Offset {
        val index = (y * xLength) + x
        return offsets[index]
    }

    private fun getColor(x: Int, y: Int): Color {
        val index = (y * xLength) + x
        return colors[index]
    }

    private operator fun set(x: Int, y: Int, offset: Offset) {
        val index = (y * xLength) + x
        offsets[index] = Offset(offset.x, offset.y)
    }

    private operator fun set(x: Int, y: Int, color: Color) {
        val index = (y * xLength) + x
        colors[index] = color
    }
}

internal fun buildTriangleIndices(columns: Int, rows: Int): List<Int> = buildList {
    for (y in 0..<rows - 1) {
        for (x in 0..<columns - 1) {
            val topLeft = (y * columns) + x
            val topRight = topLeft + 1
            val bottomLeft = ((y + 1) * columns) + x
            val bottomRight = bottomLeft + 1

            add(topLeft)
            add(bottomLeft)
            add(bottomRight)
            add(topLeft)
            add(topRight)
            add(bottomRight)
        }
    }
}

private fun cubicPathX(point1: Offset, point2: Offset, position: Int): Path {
    val path =
        Path().apply {
            moveTo(point1.x, point1.y)
            val delta = (point2.x - point1.x) * CUBIC_CONTROL_FRACTION
            when (position) {
                0 -> cubicTo(point1.x, point1.y, point2.x - delta, point2.y, point2.x, point2.y)

                2 -> cubicTo(point1.x + delta, point1.y, point2.x, point2.y, point2.x, point2.y)

                else ->
                    cubicTo(
                        point1.x + delta,
                        point1.y,
                        point2.x - delta,
                        point2.y,
                        point2.x,
                        point2.y,
                    )
            }

            lineTo(point2.x, point2.y)
        }
    return path
}

private fun cubicPathY(point1: Offset, point2: Offset, position: Int): Path {
    val path =
        Path().apply {
            moveTo(point1.x, point1.y)
            val delta = (point2.y - point1.y) * CUBIC_CONTROL_FRACTION
            when (position) {
                0 -> cubicTo(point1.x, point1.y, point2.x, point2.y - delta, point2.x, point2.y)

                2 -> cubicTo(point1.x, point1.y + delta, point2.x, point2.y, point2.x, point2.y)

                else ->
                    cubicTo(
                        point1.x,
                        point1.y + delta,
                        point2.x,
                        point2.y - delta,
                        point2.x,
                        point2.y,
                    )
            }

            lineTo(point2.x, point2.y)
        }
    return path
}

private val paint = Paint()
