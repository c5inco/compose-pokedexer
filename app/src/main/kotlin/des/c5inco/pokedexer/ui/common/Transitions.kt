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

object Material2Transitions {
    val SharedXAxisEnterTransition: (Density) -> EnterTransition = { density ->
        fadeIn(
            animationSpec = tween(durationMillis = 210, delayMillis = 90, easing = LinearOutSlowInEasing)
        ) +
        slideInHorizontally(
            animationSpec = tween(durationMillis = 300)
        ) {
            with(density) { 30.dp.roundToPx() }
        }
    }

    val SharedXAxisPopEnterTransition: (Density) -> EnterTransition = { density ->
        fadeIn(
            animationSpec = tween(durationMillis = 210, delayMillis = 90, easing = LinearOutSlowInEasing)
        ) +
        slideInHorizontally(
            animationSpec = tween(durationMillis = 300)
        ) {
            with(density) { -30.dp.roundToPx() }
        }
    }

    val SharedXAxisExitTransition: (Density) -> ExitTransition = { density ->
        fadeOut(
            animationSpec = tween(durationMillis = 90, easing = FastOutLinearInEasing)
        ) +
        slideOutHorizontally(
            animationSpec = tween(durationMillis = 300)
        ) {
            with(density) { -30.dp.roundToPx() }
        }
    }

    val SharedXAxisPopExitTransition: (Density) -> ExitTransition = { density ->
        fadeOut(
            animationSpec = tween(durationMillis = 90, easing = FastOutLinearInEasing)
        ) +
        slideOutHorizontally(
            animationSpec = tween(durationMillis = 300)
        ) {
            with(density) { 30.dp.roundToPx() }
        }
    }

    val SharedZAxisEnterTransition =
        fadeIn(
            animationSpec = tween(durationMillis = 210, delayMillis = 90, easing = LinearOutSlowInEasing)
        ) +
        scaleIn(
            initialScale = 0.8f,
            transformOrigin = TransformOrigin(0.5f, 1f),
            animationSpec = tween(durationMillis = 300)
        )

    val SharedZAxisExitTransition =
        fadeOut(animationSpec = tween(durationMillis = 90, easing = FastOutLinearInEasing)) +
        scaleOut(
            targetScale = 0.8f,
            transformOrigin = TransformOrigin(0.5f, 1f),
            animationSpec = tween(durationMillis = 300)
        )
}

// Material3 motion

private val pathForAnimation =
    Path().apply {
        moveTo(0f, 0f)
        cubicTo(0.05f, 0f, 0.133333f, 0.06f, 0.166666f, 0.4f)
        cubicTo(0.208333f, 0.82f, 0.25f, 1f, 1f, 1f)
    }

val DurationMedium1 = 250
val DurationMedium2 = 300
val DurationLong1 = 450
val DurationLong2 = 500
val EmphasizedEasing = PathEasing(pathForAnimation)
val EmphasizedAccelerateEasing = CubicBezierEasing(0.3f, 0f, 0.8f, 0.15f)
val EmphasizedDecelerateEasing = CubicBezierEasing(0.05f, 0.7f, 0.1f, 1f)

object Material3Transitions {
    val SharedXAxisEnterTransition: (Density) -> EnterTransition = { density ->
        fadeIn(
            animationSpec = tween(durationMillis = DurationLong1, easing = EmphasizedEasing)
        ) +
        slideInHorizontally(
            animationSpec = tween(durationMillis = DurationLong2, easing = EmphasizedEasing)
        ) {
            it / 2
        }
    }

    val SharedXAxisPopEnterTransition: (Density) -> EnterTransition = { density ->
        fadeIn(
            animationSpec = tween(durationMillis = DurationLong1, easing = EmphasizedEasing)
        ) +
        slideInHorizontally(
            animationSpec = tween(durationMillis = DurationLong2, easing = EmphasizedEasing)
        ) {
            -it / 2
        }
    }

    val SharedXAxisExitTransition: (Density) -> ExitTransition = { density ->
        fadeOut(
            animationSpec = tween(durationMillis = DurationMedium1, easing = EmphasizedAccelerateEasing)
        ) +
        slideOutHorizontally(
            animationSpec = tween(durationMillis = DurationMedium2, easing = EmphasizedAccelerateEasing)
        ) {
            with(density) { -30.dp.roundToPx() }
        }
    }

    val SharedXAxisPopExitTransition: (Density) -> ExitTransition = { density ->
        fadeOut(
            animationSpec = tween(durationMillis = DurationMedium1, easing = EmphasizedAccelerateEasing)
        ) +
        slideOutHorizontally(
            animationSpec = tween(durationMillis = DurationMedium2, easing = EmphasizedAccelerateEasing)
        ) {
            with(density) { 30.dp.roundToPx() }
        }
    }

    val SharedYAxisEnterTransition: (Density) -> EnterTransition = { density ->
        fadeIn(
            animationSpec = tween(durationMillis = DurationLong1, easing = EmphasizedDecelerateEasing)
        ) +
        slideInVertically(animationSpec = tween(durationMillis = DurationLong2, easing = EmphasizedDecelerateEasing)) {
            it / 2
        }
    }

    val SharedZAxisEnterTransition =
        fadeIn(animationSpec = tween(durationMillis = DurationLong1, easing = EmphasizedEasing)) +
        scaleIn(
            initialScale = 0.8f,
            transformOrigin = TransformOrigin(0.5f, 1f),
            animationSpec = tween(durationMillis = DurationLong2, easing = EmphasizedEasing)
        )

    val SharedZAxisExitTransition =
        fadeOut(animationSpec = tween(durationMillis = DurationMedium1, easing = EmphasizedAccelerateEasing)) +
        scaleOut(
            targetScale = 0.8f,
            transformOrigin = TransformOrigin(0.5f, 1f),
            animationSpec = tween(durationMillis = DurationMedium2, easing = EmphasizedAccelerateEasing)
        )
}
