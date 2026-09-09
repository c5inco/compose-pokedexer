package des.c5inco.pokedexer.ui.common

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
import androidx.compose.ui.graphics.VertexMode
import androidx.compose.ui.graphics.Vertices
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.lerp

@Composable
fun Modifier.legacyMeshGradient(
    points: List<List<Pair<Offset, Color>>>,
    resolutionX: Int,
    resolutionY: Int,
): Modifier {
    val pointData by
        remember(points, resolutionX, resolutionY) {
            derivedStateOf { LegacyPointData(points, resolutionX, resolutionY) }
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
                                indices = pointData.indices,
                            ),
                        blendMode = BlendMode.DstIn,
                        paint = LegacyPaint,
                    )
                }
            }
        }
    }
}

private class LegacyPointData(
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
    private val indicesBlocks: List<IndicesBlock>

    init {
        offsets =
            buildList { repeat((xLength - 0) * (yLength - 0)) { add(Offset(0f, 0f)) } }
                .toMutableList()
        colors =
            buildList { repeat((xLength - 0) * (yLength - 0)) { add(Color.Transparent) } }
                .toMutableList()
        indicesBlocks = buildList {
            for (y in 0..yLength - 2) {
                for (x in 0..xLength - 2) {
                    val a = (y * xLength) + x
                    val b = a + 1
                    val c = ((y + 1) * xLength) + x
                    val d = c + 1
                    add(
                        IndicesBlock(
                            indices = listOf(a, c, d, a, b, d),
                            x = x,
                            y = y,
                        )
                    )
                }
            }
        }
        indices = indicesBlocks.flatMap { it.indices }
        generateInterpolatedOffsets()
    }

    private fun generateInterpolatedOffsets() {
        for (y in points.indices) {
            for (x in points[y].indices) {
                this[x * stepsX, y * stepsY] = points[y][x].first
                this[x * stepsX, y * stepsY] = points[y][x].second

                if (x != points[y].lastIndex) {
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
                        measure.getPosition(i / stepsX.toFloat() * measure.length).let {
                            this[(x * stepsX) + i, y * stepsY] = Offset(it.x, it.y)
                        }
                        this[(x * stepsX) + i, y * stepsY] =
                            lerp(
                                points[y][x].second,
                                points[y][x + 1].second,
                                i / stepsX.toFloat(),
                            )
                    }
                }
            }
        }

        for (y in 0..<points.lastIndex) {
            for (x in 0..<xLength) {
                val path =
                    cubicPathY(
                        point1 = this[x, y * stepsY],
                        point2 = this[x, (y + 1) * stepsY],
                        position =
                            when (y) {
                                0 -> 0
                                points[y].lastIndex - 1 -> 2
                                else -> 1
                            },
                    )
                measure.setPath(path, false)

                for (i in (1..<stepsY)) {
                    val point =
                        measure.getPosition(i / stepsY.toFloat() * measure.length).let {
                            Offset(it.x, it.y)
                        }
                    this[x, (y * stepsY) + i] = point
                    this[x, (y * stepsY) + i] =
                        lerp(
                            getColor(x, y * stepsY),
                            getColor(x, (y + 1) * stepsY),
                            i / stepsY.toFloat(),
                        )
                }
            }
        }
    }

    private operator fun get(x: Int, y: Int): Offset = offsets[(y * xLength) + x]

    private fun getColor(x: Int, y: Int): Color = colors[(y * xLength) + x]

    private operator fun set(x: Int, y: Int, offset: Offset) {
        offsets[(y * xLength) + x] = offset
    }

    private operator fun set(x: Int, y: Int, color: Color) {
        colors[(y * xLength) + x] = color
    }

    data class IndicesBlock(val indices: List<Int>, val x: Int, val y: Int)
}

private fun cubicPathX(point1: Offset, point2: Offset, position: Int): Path =
    Path().apply {
        moveTo(point1.x, point1.y)
        val delta = (point2.x - point1.x) * 0.5f
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

private fun cubicPathY(point1: Offset, point2: Offset, position: Int): Path =
    Path().apply {
        moveTo(point1.x, point1.y)
        val delta = (point2.y - point1.y) * 0.5f
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

private val LegacyPaint = Paint()
