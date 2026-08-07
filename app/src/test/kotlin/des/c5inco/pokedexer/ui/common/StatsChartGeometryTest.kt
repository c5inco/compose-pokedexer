package des.c5inco.pokedexer.ui.common

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import kotlin.math.tan
import org.junit.Assert.assertEquals
import org.junit.Test

class StatsChartGeometryTest {
    @Test
    fun fullRatiosPreserveExistingRingGeometry() {
        val size = Size(width = CHART_WIDTH, height = CHART_HEIGHT)
        val angle = ANGLE_DEGREES.toRadian()
        val verticalOffset = CENTER_X * tan(angle).toFloat()

        val points = calculateStatRingPoints(size, StatRatios(), angle)

        assertOffsetsEqual(
            expected =
                listOf(
                    Offset(CENTER_X, 0f),
                    Offset(CHART_WIDTH, CENTER_Y - verticalOffset),
                    Offset(CHART_WIDTH, CENTER_Y + verticalOffset),
                    Offset(CENTER_X, CHART_HEIGHT),
                    Offset(0f, CENTER_Y + verticalOffset),
                    Offset(0f, CENTER_Y - verticalOffset),
                ),
            actual = points,
        )
    }

    @Test
    fun asymmetricRatiosPreserveStatPointMapping() {
        val size = Size(width = CHART_WIDTH, height = CHART_HEIGHT)
        val angle = ANGLE_DEGREES.toRadian()
        val tangent = tan(angle).toFloat()

        val points =
            calculateStatRingPoints(
                size = size,
                ratios =
                    StatRatios(
                        hp = HP_RATIO,
                        attack = ATTACK_RATIO,
                        defense = DEFENSE_RATIO,
                        speed = SPEED_RATIO,
                        specialAttack = SPECIAL_ATTACK_RATIO,
                        specialDefense = SPECIAL_DEFENSE_RATIO,
                    ),
                angle = angle,
            )

        assertOffsetsEqual(
            expected =
                listOf(
                    Offset(CENTER_X, CENTER_Y - CENTER_Y * HP_RATIO),
                    Offset(
                        CENTER_X + CENTER_X * ATTACK_RATIO,
                        CENTER_Y - CENTER_X * ATTACK_RATIO * tangent,
                    ),
                    Offset(
                        CENTER_X + CENTER_X * DEFENSE_RATIO,
                        CENTER_Y + CENTER_X * DEFENSE_RATIO * tangent,
                    ),
                    Offset(CENTER_X, CENTER_Y + CENTER_Y * SPEED_RATIO),
                    Offset(
                        CENTER_X - CENTER_X * SPECIAL_DEFENSE_RATIO,
                        CENTER_Y + CENTER_X * SPECIAL_DEFENSE_RATIO * tangent,
                    ),
                    Offset(
                        CENTER_X - CENTER_X * SPECIAL_ATTACK_RATIO,
                        CENTER_Y - CENTER_X * SPECIAL_ATTACK_RATIO * tangent,
                    ),
                ),
            actual = points,
        )
    }

    @Test
    fun originLineEndsPreservePaddingAndOrder() {
        val size = Size(width = CHART_WIDTH, height = CHART_HEIGHT)
        val angle = ANGLE_DEGREES.toRadian()
        val verticalOffset = (CENTER_X - ORIGIN_PADDING) * tan(angle).toFloat()

        val points = calculateOriginLineEnds(size, angle, ORIGIN_PADDING)

        assertOffsetsEqual(
            expected =
                listOf(
                    Offset(CENTER_X, ORIGIN_PADDING),
                    Offset(CHART_WIDTH - ORIGIN_PADDING, CENTER_Y + verticalOffset),
                    Offset(CHART_WIDTH - ORIGIN_PADDING, CENTER_Y - verticalOffset),
                    Offset(ORIGIN_PADDING, CENTER_Y + verticalOffset),
                    Offset(ORIGIN_PADDING, CENTER_Y - verticalOffset),
                    Offset(CENTER_X, CHART_HEIGHT - ORIGIN_PADDING),
                ),
            actual = points,
        )
    }

    private fun assertOffsetsEqual(expected: List<Offset>, actual: List<Offset>) {
        assertEquals(expected.size, actual.size)
        expected.zip(actual).forEach { (expectedOffset, actualOffset) ->
            assertEquals(expectedOffset.x, actualOffset.x, TOLERANCE)
            assertEquals(expectedOffset.y, actualOffset.y, TOLERANCE)
        }
    }

    private companion object {
        const val TOLERANCE = 0.0001f
        const val CHART_WIDTH = 200f
        const val CHART_HEIGHT = 230f
        const val CENTER_X = 100f
        const val CENTER_Y = 115f
        const val ANGLE_DEGREES = 32.0
        const val ORIGIN_PADDING = 10f
        const val HP_RATIO = 0.1f
        const val ATTACK_RATIO = 0.2f
        const val DEFENSE_RATIO = 0.3f
        const val SPEED_RATIO = 0.4f
        const val SPECIAL_ATTACK_RATIO = 0.5f
        const val SPECIAL_DEFENSE_RATIO = 0.6f
    }
}
