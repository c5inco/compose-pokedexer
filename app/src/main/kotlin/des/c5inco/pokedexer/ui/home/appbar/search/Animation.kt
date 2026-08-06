package des.c5inco.pokedexer.ui.home.appbar.search

import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.ArcMode
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring.DampingRatioMediumBouncy
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally

private const val RESULT_ENTER_DURATION_MILLIS = 300
private const val RESULT_ENTER_DELAY_STEP_MILLIS = 100
private const val RESULT_STAGGER_GROUP_SIZE = 2
private const val CONTAINER_SPRING_STIFFNESS = 1200f
private const val TEXT_BOUNDS_DURATION_MILLIS = 300
private const val IMAGE_BOUNDS_DURATION_MILLIS = 200

fun slideAndFadeEnterTransition(index: Int): EnterTransition {
    return fadeIn(
        tween(
            durationMillis = RESULT_ENTER_DURATION_MILLIS,
            delayMillis = index / RESULT_STAGGER_GROUP_SIZE * RESULT_ENTER_DELAY_STEP_MILLIS,
        )
    ) +
        slideInHorizontally(
            tween(
                durationMillis = RESULT_ENTER_DURATION_MILLIS,
                delayMillis = index / RESULT_STAGGER_GROUP_SIZE * RESULT_ENTER_DELAY_STEP_MILLIS,
            )
        ) { fullWidth ->
            fullWidth / RESULT_STAGGER_GROUP_SIZE
        }
}

internal val containerBoundsTransform = BoundsTransform { _, _ ->
    spring(dampingRatio = DampingRatioMediumBouncy, stiffness = CONTAINER_SPRING_STIFFNESS)
}

internal val textBoundsTransform = BoundsTransform { initialBounds, targetBounds ->
    keyframes {
        durationMillis = TEXT_BOUNDS_DURATION_MILLIS
        initialBounds at 0 using ArcMode.ArcLinear using FastOutSlowInEasing
        targetBounds at TEXT_BOUNDS_DURATION_MILLIS
    }
}

internal val imageBoundsTransform = BoundsTransform { initialBounds, targetBounds ->
    keyframes {
        durationMillis = IMAGE_BOUNDS_DURATION_MILLIS
        initialBounds at 0 using ArcMode.ArcBelow using FastOutSlowInEasing
        targetBounds at IMAGE_BOUNDS_DURATION_MILLIS
    }
}
