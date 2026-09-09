package des.c5inco.pokedexer.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.MeshGradientPainter

private const val BEZIER_CONTROL_POINT_SCALE = 0.5f

@Composable
fun Modifier.meshGradient(points: List<List<Pair<Offset, Color>>>): Modifier {
    val rows = points.lastIndex
    val columns = points.first().lastIndex
    val painter =
        remember(points) {
            MeshGradientPainter(rows = rows, columns = columns) {
                points.forEachIndexed { row, rowPoints ->
                    rowPoints.forEachIndexed { column, (position, color) ->
                        setVertex(
                            row = row,
                            column = column,
                            position = position,
                            color = color,
                            leftControlPoint =
                                horizontalControlPoint(points, row, column, direction = -1),
                            topControlPoint =
                                verticalControlPoint(points, row, column, direction = -1),
                            rightControlPoint =
                                horizontalControlPoint(points, row, column, direction = 1),
                            bottomControlPoint =
                                verticalControlPoint(points, row, column, direction = 1),
                        )
                    }
                }
            }
        }
    return paint(painter)
}

// Preserve the legacy interpolator's endpoint tangents while the native painter owns rendering.
private fun horizontalControlPoint(
    points: List<List<Pair<Offset, Color>>>,
    row: Int,
    column: Int,
    direction: Int,
): Offset {
    val adjacentColumn = column + direction
    val isFlatControlPoint =
        adjacentColumn !in points[row].indices ||
            (direction > 0 && column == 0) ||
            (direction < 0 && column == points[row].lastIndex && column > 1)
    return if (isFlatControlPoint) {
        Offset.Zero
    } else {
        val adjacentPosition = points[row][adjacentColumn].first
        val position = points[row][column].first
        Offset((adjacentPosition.x - position.x) * BEZIER_CONTROL_POINT_SCALE, 0f)
    }
}

private fun verticalControlPoint(
    points: List<List<Pair<Offset, Color>>>,
    row: Int,
    column: Int,
    direction: Int,
): Offset {
    val adjacentRow = row + direction
    val isFlatControlPoint =
        adjacentRow !in points.indices ||
            (direction > 0 && row == 0) ||
            (direction < 0 && row == points[row].lastIndex && row > 1)
    return if (isFlatControlPoint) {
        Offset.Zero
    } else {
        val adjacentPosition = points[adjacentRow][column].first
        val position = points[row][column].first
        Offset(0f, (adjacentPosition.y - position.y) * BEZIER_CONTROL_POINT_SCALE)
    }
}
