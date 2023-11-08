package des.c5inco.pokedexer.ui.common

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp

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
    fadeIn(animationSpec = tween(durationMillis = 210, delayMillis = 90, easing = LinearOutSlowInEasing)) +
    scaleIn(initialScale = 0.8f, transformOrigin = TransformOrigin(0.5f, 1f), animationSpec = tween(durationMillis = 300))

val SharedZAxisExitTransition =
    fadeOut(animationSpec = tween(durationMillis = 90, easing = FastOutLinearInEasing)) +
    scaleOut(targetScale = 0.8f, transformOrigin = TransformOrigin(0.5f, 1f), animationSpec = tween(durationMillis = 300))