package des.c5inco.pokedexer.ui.home.appbar.search

import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.ArcMode
import androidx.compose.animation.core.ExperimentalAnimationSpecApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring.DampingRatioMediumBouncy
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring

@OptIn(ExperimentalSharedTransitionApi::class)
internal val containerBoundsTransform = BoundsTransform { _, _ ->
    spring(
        dampingRatio = DampingRatioMediumBouncy,
        stiffness = 1200f
    )
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalAnimationSpecApi::class)
internal val textBoundsTransform = BoundsTransform { initialBounds, targetBounds ->
    keyframes {
        durationMillis = 300
        initialBounds at 0 using ArcMode.ArcLinear using FastOutSlowInEasing
        targetBounds at 300
    }
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalAnimationSpecApi::class)
internal val imageBoundsTransform = BoundsTransform { initialBounds, targetBounds ->
    keyframes {
        durationMillis = 200
        initialBounds at 0 using ArcMode.ArcBelow using FastOutSlowInEasing
        targetBounds at 200
    }
}