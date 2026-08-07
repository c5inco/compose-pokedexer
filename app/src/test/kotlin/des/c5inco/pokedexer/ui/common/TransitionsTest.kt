package des.c5inco.pokedexer.ui.common

import org.junit.Assert.assertEquals
import org.junit.Test

class TransitionsTest {
    @Test
    fun material3DurationsRemainUnchanged() {
        assertEquals(ExpectedDurations.MEDIUM_1_MILLIS, Material3Durations.MEDIUM_1_MILLIS)
        assertEquals(ExpectedDurations.MEDIUM_2_MILLIS, Material3Durations.MEDIUM_2_MILLIS)
        assertEquals(ExpectedDurations.LONG_1_MILLIS, Material3Durations.LONG_1_MILLIS)
        assertEquals(ExpectedDurations.LONG_2_MILLIS, Material3Durations.LONG_2_MILLIS)
    }

    @Test
    fun emphasizedPathCurveRemainsUnchanged() {
        assertEquals(
            listOf(
                ExpectedPathCurve.FIRST_CONTROL_X,
                ExpectedPathCurve.FIRST_CONTROL_Y,
                ExpectedPathCurve.SECOND_CONTROL_X,
                ExpectedPathCurve.SECOND_CONTROL_Y,
                ExpectedPathCurve.FIRST_END_X,
                ExpectedPathCurve.FIRST_END_Y,
            ),
            listOf(
                EmphasizedPathCurve.FIRST_CONTROL_X,
                EmphasizedPathCurve.FIRST_CONTROL_Y,
                EmphasizedPathCurve.SECOND_CONTROL_X,
                EmphasizedPathCurve.SECOND_CONTROL_Y,
                EmphasizedPathCurve.FIRST_END_X,
                EmphasizedPathCurve.FIRST_END_Y,
            ),
        )
        assertEquals(
            listOf(
                ExpectedPathCurve.THIRD_CONTROL_X,
                ExpectedPathCurve.THIRD_CONTROL_Y,
                ExpectedPathCurve.FOURTH_CONTROL_X,
                ExpectedPathCurve.FOURTH_CONTROL_Y,
            ),
            listOf(
                EmphasizedPathCurve.THIRD_CONTROL_X,
                EmphasizedPathCurve.THIRD_CONTROL_Y,
                EmphasizedPathCurve.FOURTH_CONTROL_X,
                EmphasizedPathCurve.FOURTH_CONTROL_Y,
            ),
        )
    }

    @Test
    fun emphasizedCubicBezierCurvesRemainUnchanged() {
        assertEquals(
            listOf(
                ExpectedAccelerateCurve.FIRST_CONTROL_X,
                ExpectedAccelerateCurve.FIRST_CONTROL_Y,
                ExpectedAccelerateCurve.SECOND_CONTROL_X,
                ExpectedAccelerateCurve.SECOND_CONTROL_Y,
            ),
            listOf(
                EmphasizedAccelerateCurve.FIRST_CONTROL_X,
                EmphasizedAccelerateCurve.FIRST_CONTROL_Y,
                EmphasizedAccelerateCurve.SECOND_CONTROL_X,
                EmphasizedAccelerateCurve.SECOND_CONTROL_Y,
            ),
        )
        assertEquals(
            listOf(
                ExpectedDecelerateCurve.FIRST_CONTROL_X,
                ExpectedDecelerateCurve.FIRST_CONTROL_Y,
                ExpectedDecelerateCurve.SECOND_CONTROL_X,
                ExpectedDecelerateCurve.SECOND_CONTROL_Y,
            ),
            listOf(
                EmphasizedDecelerateCurve.FIRST_CONTROL_X,
                EmphasizedDecelerateCurve.FIRST_CONTROL_Y,
                EmphasizedDecelerateCurve.SECOND_CONTROL_X,
                EmphasizedDecelerateCurve.SECOND_CONTROL_Y,
            ),
        )
    }
}

private object ExpectedDurations {
    const val MEDIUM_1_MILLIS = 250
    const val MEDIUM_2_MILLIS = 300
    const val LONG_1_MILLIS = 450
    const val LONG_2_MILLIS = 500
}

private object ExpectedPathCurve {
    const val FIRST_CONTROL_X = 0.05f
    const val FIRST_CONTROL_Y = 0f
    const val SECOND_CONTROL_X = 0.133333f
    const val SECOND_CONTROL_Y = 0.06f
    const val FIRST_END_X = 0.166666f
    const val FIRST_END_Y = 0.4f
    const val THIRD_CONTROL_X = 0.208333f
    const val THIRD_CONTROL_Y = 0.82f
    const val FOURTH_CONTROL_X = 0.25f
    const val FOURTH_CONTROL_Y = 1f
}

private object ExpectedAccelerateCurve {
    const val FIRST_CONTROL_X = 0.3f
    const val FIRST_CONTROL_Y = 0f
    const val SECOND_CONTROL_X = 0.8f
    const val SECOND_CONTROL_Y = 0.15f
}

private object ExpectedDecelerateCurve {
    const val FIRST_CONTROL_X = 0.05f
    const val FIRST_CONTROL_Y = 0.7f
    const val SECOND_CONTROL_X = 0.1f
    const val SECOND_CONTROL_Y = 1f
}
