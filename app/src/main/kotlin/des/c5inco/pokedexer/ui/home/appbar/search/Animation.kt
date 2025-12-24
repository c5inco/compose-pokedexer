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

fun slideAndFadeEnterTransition(index: Int): EnterTransition {
    return fadeIn(
        tween(durationMillis = 300, delayMillis = index / 2 * 100)
    ) +
            slideInHorizontally(
                tween(durationMillis = 300, delayMillis = index / 2 * 100)
            ) { it / 2 }
}

internal val containerBoundsTransform = BoundsTransform { _, _ ->
    spring(
        dampingRatio = DampingRatioMediumBouncy,
        stiffness = 1200f
    )
}

internal val textBoundsTransform = BoundsTransform { initialBounds, targetBounds ->
    keyframes {
        durationMillis = 300
        initialBounds at 0 using ArcMode.ArcLinear using FastOutSlowInEasing
        targetBounds at 300
    }
}

internal val imageBoundsTransform = BoundsTransform { initialBounds, targetBounds ->
    keyframes {
        durationMillis = 200
        initialBounds at 0 using ArcMode.ArcBelow using FastOutSlowInEasing
        targetBounds at 200
    }
}