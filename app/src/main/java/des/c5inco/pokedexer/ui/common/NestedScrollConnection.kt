package des.c5inco.pokedexer.ui.common

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun nestedScrollConnection(
    swipeableState: SwipeableState<Int>,
    minAnchor: Float,
) = remember {
    object : NestedScrollConnection {
        override fun onPreScroll(
            available: Offset,
            source: NestedScrollSource
        ): Offset {
            val delta = available.y
            return if (delta < 0 && source == NestedScrollSource.Drag) {
                swipeableState.performDrag(delta).toOffset()
            } else {
                Offset.Zero
            }
        }

        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource
        ): Offset {
            return if (source == NestedScrollSource.Drag) {
                swipeableState.performDrag(available.y).toOffset()
            } else {
                Offset.Zero
            }
        }

        override suspend fun onPreFling(available: Velocity): Velocity {
            val toFling = available.y
            val currentOffset = swipeableState.offset
            return if (toFling < 0 && currentOffset.value > minAnchor) {
                swipeableState.performFling(velocity = toFling)
                // since we go to the anchor with tween settling, consume all for the best UX
                available
            } else {
                Velocity.Zero
            }
        }

        override suspend fun onPostFling(
            consumed: Velocity,
            available: Velocity
        ): Velocity {
            swipeableState.performFling(velocity = available.y)
            return super.onPostFling(consumed, available)
        }

        private fun Float.toOffset() = Offset(0f, this)
    }
}