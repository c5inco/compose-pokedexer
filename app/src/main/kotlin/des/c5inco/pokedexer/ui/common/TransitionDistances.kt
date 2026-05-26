package des.c5inco.pokedexer.ui.common

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp

private val SharedAxisSlideDistance = 30.dp

internal fun sharedAxisSlideDistancePx(density: Density): Int =
    with(density) { SharedAxisSlideDistance.roundToPx() }

internal fun sharedXAxisEnterOffset(density: Density): Int = sharedAxisSlideDistancePx(density)

internal fun sharedXAxisPopEnterOffset(density: Density): Int = -sharedAxisSlideDistancePx(density)
