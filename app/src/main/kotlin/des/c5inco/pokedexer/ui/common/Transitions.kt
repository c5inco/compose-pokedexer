package des.c5inco.pokedexer.ui.common

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.PathEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp

private const val SHARED_AXIS_SCALE = 0.8f
private const val TRANSFORM_ORIGIN_CENTER_X = 0.5f

internal object Material3Durations {
    const val MEDIUM_1_MILLIS = 250
    const val MEDIUM_2_MILLIS = 300
    const val LONG_1_MILLIS = 450
    const val LONG_2_MILLIS = 500
}

internal object EmphasizedPathCurve {
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

internal object EmphasizedAccelerateCurve {
    const val FIRST_CONTROL_X = 0.3f
    const val FIRST_CONTROL_Y = 0f
    const val SECOND_CONTROL_X = 0.8f
    const val SECOND_CONTROL_Y = 0.15f
}

internal object EmphasizedDecelerateCurve {
    const val FIRST_CONTROL_X = 0.05f
    const val FIRST_CONTROL_Y = 0.7f
    const val SECOND_CONTROL_X = 0.1f
    const val SECOND_CONTROL_Y = 1f
}

// Material2 motion

object Material2Transitions {
    val SharedXAxisEnterTransition: (Density) -> EnterTransition = { density ->
        fadeIn(
            animationSpec =
                tween(durationMillis = 210, delayMillis = 90, easing = LinearOutSlowInEasing)
        ) +
            slideInHorizontally(animationSpec = tween(durationMillis = 300)) {
                with(density) { 30.dp.roundToPx() }
            }
    }

    val SharedXAxisPopEnterTransition: (Density) -> EnterTransition = { density ->
        fadeIn(
            animationSpec =
                tween(durationMillis = 210, delayMillis = 90, easing = LinearOutSlowInEasing)
        ) +
            slideInHorizontally(animationSpec = tween(durationMillis = 300)) {
                with(density) { -30.dp.roundToPx() }
            }
    }

    val SharedXAxisExitTransition: (Density) -> ExitTransition = { density ->
        fadeOut(animationSpec = tween(durationMillis = 90, easing = FastOutLinearInEasing)) +
            slideOutHorizontally(animationSpec = tween(durationMillis = 300)) {
                with(density) { -30.dp.roundToPx() }
            }
    }

    val SharedXAxisPopExitTransition: (Density) -> ExitTransition = { density ->
        fadeOut(animationSpec = tween(durationMillis = 90, easing = FastOutLinearInEasing)) +
            slideOutHorizontally(animationSpec = tween(durationMillis = 300)) {
                with(density) { 30.dp.roundToPx() }
            }
    }

    val SharedZAxisEnterTransition =
        fadeIn(
            animationSpec =
                tween(durationMillis = 210, delayMillis = 90, easing = LinearOutSlowInEasing)
        ) +
            scaleIn(
                initialScale = SHARED_AXIS_SCALE,
                transformOrigin = TransformOrigin(TRANSFORM_ORIGIN_CENTER_X, 1f),
                animationSpec = tween(durationMillis = 300),
            )

    val SharedZAxisExitTransition =
        fadeOut(animationSpec = tween(durationMillis = 90, easing = FastOutLinearInEasing)) +
            scaleOut(
                targetScale = SHARED_AXIS_SCALE,
                transformOrigin = TransformOrigin(TRANSFORM_ORIGIN_CENTER_X, 1f),
                animationSpec = tween(durationMillis = 300),
            )
}

// Material3 motion

private val pathForAnimation =
    Path().apply {
        moveTo(0f, 0f)
        cubicTo(
            EmphasizedPathCurve.FIRST_CONTROL_X,
            EmphasizedPathCurve.FIRST_CONTROL_Y,
            EmphasizedPathCurve.SECOND_CONTROL_X,
            EmphasizedPathCurve.SECOND_CONTROL_Y,
            EmphasizedPathCurve.FIRST_END_X,
            EmphasizedPathCurve.FIRST_END_Y,
        )
        cubicTo(
            EmphasizedPathCurve.THIRD_CONTROL_X,
            EmphasizedPathCurve.THIRD_CONTROL_Y,
            EmphasizedPathCurve.FOURTH_CONTROL_X,
            EmphasizedPathCurve.FOURTH_CONTROL_Y,
            1f,
            1f,
        )
    }

val DurationMedium1: Int
    get() = Material3Durations.MEDIUM_1_MILLIS
val DurationMedium2: Int
    get() = Material3Durations.MEDIUM_2_MILLIS
val DurationLong1: Int
    get() = Material3Durations.LONG_1_MILLIS
val DurationLong2: Int
    get() = Material3Durations.LONG_2_MILLIS
val EmphasizedEasing = PathEasing(pathForAnimation)
val EmphasizedAccelerateEasing =
    CubicBezierEasing(
        EmphasizedAccelerateCurve.FIRST_CONTROL_X,
        EmphasizedAccelerateCurve.FIRST_CONTROL_Y,
        EmphasizedAccelerateCurve.SECOND_CONTROL_X,
        EmphasizedAccelerateCurve.SECOND_CONTROL_Y,
    )
val EmphasizedDecelerateEasing =
    CubicBezierEasing(
        EmphasizedDecelerateCurve.FIRST_CONTROL_X,
        EmphasizedDecelerateCurve.FIRST_CONTROL_Y,
        EmphasizedDecelerateCurve.SECOND_CONTROL_X,
        EmphasizedDecelerateCurve.SECOND_CONTROL_Y,
    )

object Material3Transitions {
    val SharedXAxisEnterTransition: (Density) -> EnterTransition = { density ->
        fadeIn(animationSpec = tween(durationMillis = DurationLong1, easing = EmphasizedEasing)) +
            slideInHorizontally(
                animationSpec = tween(durationMillis = DurationLong2, easing = EmphasizedEasing)
            ) {
                it / 2
            }
    }

    val SharedXAxisPopEnterTransition: (Density) -> EnterTransition = { density ->
        fadeIn(animationSpec = tween(durationMillis = DurationLong1, easing = EmphasizedEasing)) +
            slideInHorizontally(
                animationSpec = tween(durationMillis = DurationLong2, easing = EmphasizedEasing)
            ) {
                -it / 2
            }
    }

    val SharedXAxisExitTransition: (Density) -> ExitTransition = { density ->
        fadeOut(
            animationSpec =
                tween(durationMillis = DurationMedium1, easing = EmphasizedAccelerateEasing)
        ) +
            slideOutHorizontally(
                animationSpec =
                    tween(durationMillis = DurationMedium2, easing = EmphasizedAccelerateEasing)
            ) {
                with(density) { -30.dp.roundToPx() }
            }
    }

    val SharedXAxisPopExitTransition: (Density) -> ExitTransition = { density ->
        fadeOut(
            animationSpec =
                tween(durationMillis = DurationMedium1, easing = EmphasizedAccelerateEasing)
        ) +
            slideOutHorizontally(
                animationSpec =
                    tween(durationMillis = DurationMedium2, easing = EmphasizedAccelerateEasing)
            ) {
                with(density) { 30.dp.roundToPx() }
            }
    }

    val SharedYAxisEnterTransition: EnterTransition =
        fadeIn(
            animationSpec =
                tween(durationMillis = DurationLong1, easing = EmphasizedDecelerateEasing)
        ) +
            slideInVertically(
                animationSpec =
                    tween(durationMillis = DurationLong2, easing = EmphasizedDecelerateEasing)
            ) {
                it / 2
            }

    val SharedZAxisEnterTransition =
        fadeIn(animationSpec = tween(durationMillis = DurationLong1, easing = EmphasizedEasing)) +
            scaleIn(
                initialScale = SHARED_AXIS_SCALE,
                transformOrigin = TransformOrigin(TRANSFORM_ORIGIN_CENTER_X, 1f),
                animationSpec = tween(durationMillis = DurationLong2, easing = EmphasizedEasing),
            )

    val SharedZAxisExitTransition =
        fadeOut(
            animationSpec =
                tween(durationMillis = DurationMedium1, easing = EmphasizedAccelerateEasing)
        ) +
            scaleOut(
                targetScale = SHARED_AXIS_SCALE,
                transformOrigin = TransformOrigin(TRANSFORM_ORIGIN_CENTER_X, 1f),
                animationSpec =
                    tween(durationMillis = DurationMedium2, easing = EmphasizedAccelerateEasing),
            )
}
