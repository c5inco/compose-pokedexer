package des.c5inco.pokedexer.ui.pokedex

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.blur.BlurStop
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PokedexEdgeBlurTest {
    private val stops =
        pokedexEdgeBlurStops(
            height = HEIGHT.dp,
            top = EdgeBlurBand(extent = TOP_BAND.dp, fade = FADE.dp, radius = TOP_RADIUS.dp),
            bottom =
                EdgeBlurBand(extent = BOTTOM_BAND.dp, fade = FADE.dp, radius = BOTTOM_RADIUS.dp),
        )

    @Test
    fun edgesUseTheirOwnRadius() {
        assertEquals(BlurStop(0f, TOP_RADIUS.dp), stops.first())
        assertEquals(BlurStop(1f, BOTTOM_RADIUS.dp), stops.last())
    }

    @Test
    fun bandEdgesSitHalfwayThroughTheFade() {
        assertEquals(TOP_RADIUS / 2, radiusAt(TOP_BAND / HEIGHT).value, TOLERANCE)
        assertEquals(BOTTOM_RADIUS / 2, radiusAt(1f - BOTTOM_BAND / HEIGHT).value, TOLERANCE)
    }

    @Test
    fun middleIsClearOnceFadeEnds() {
        assertEquals(0f, radiusAt((TOP_BAND + FADE) / HEIGHT).value, TOLERANCE)
        assertEquals(0f, radiusAt(1f - (BOTTOM_BAND + FADE) / HEIGHT).value, TOLERANCE)
    }

    @Test
    fun fadeEasesInsteadOfDroppingLinearly() {
        val quarter = (TOP_BAND - FADE / 2) / HEIGHT
        assertTrue(radiusAt(quarter).value > TOP_RADIUS * EASED_QUARTER_MIN)
        assertTrue(stops.size <= MAX_STOPS)
    }

    @Test
    fun zeroFadeDoesNotDivideByZero() {
        val sharp =
            pokedexEdgeBlurStops(
                height = HEIGHT.dp,
                top = EdgeBlurBand(extent = TOP_BAND.dp, fade = 0.dp, radius = TOP_RADIUS.dp),
                bottom = EdgeBlurBand(extent = BOTTOM_BAND.dp, fade = 0.dp, radius = 0.dp),
            )

        assertTrue(sharp.all { it.fraction in 0f..1f && !it.fraction.isNaN() })
    }

    @Test
    fun overlappingBandsNeverProduceDecreasingFractions() {
        val band = EdgeBlurBand(extent = 150.dp, fade = 40.dp, radius = TOP_RADIUS.dp)
        val tight = pokedexEdgeBlurStops(height = 200.dp, top = band, bottom = band)

        assertEquals(tight.map { it.fraction }.sorted(), tight.map { it.fraction })
        assertEquals(1f, tight.last().fraction)
    }

    @Test
    fun bottomBlurStartsExactlyAtItsStartLine() {
        val bottom = bottomEdgeBlurBand(start = BOTTOM_BAND.dp, fade = FADE.dp, radius = 24.dp)
        val anchored =
            pokedexEdgeBlurStops(
                height = HEIGHT.dp,
                top = EdgeBlurBand(extent = TOP_BAND.dp, fade = FADE.dp, radius = 0.dp),
                bottom = bottom,
            )

        assertEquals(0f, radiusAt(1f - BOTTOM_BAND / HEIGHT, anchored).value, TOLERANCE)
        assertTrue(radiusAt(1f - (BOTTOM_BAND - FADE / 2) / HEIGHT, anchored).value > 0f)
    }

    @Test
    fun bottomBlurFadeLargerThanStartStaysOnScreen() {
        assertEquals(0.dp, bottomEdgeBlurBand(start = FADE.dp, fade = HEIGHT.dp, 24.dp).extent)
    }

    @Test
    fun topBlurIsOffWhileListRestsAtTheTop() {
        assertEquals(0f, topBlurProgress(firstVisibleItemIndex = 0, scrollOffsetPx = 0, RAMP))
    }

    @Test
    fun topBlurRampsInWithScrollOffset() {
        assertEquals(HALF, topBlurProgress(0, scrollOffsetPx = (RAMP * HALF).toInt(), RAMP))
        assertEquals(1f, topBlurProgress(0, scrollOffsetPx = (RAMP * 2).toInt(), RAMP))
    }

    @Test
    fun topBlurIsFullOnceFirstItemScrollsAway() {
        assertEquals(1f, topBlurProgress(firstVisibleItemIndex = 2, scrollOffsetPx = 0, RAMP))
    }

    @Test
    fun zeroRampSnapsOnAsSoonAsContentMoves() {
        assertEquals(0f, topBlurProgress(0, scrollOffsetPx = 0, rampPx = 0f))
        assertEquals(1f, topBlurProgress(0, scrollOffsetPx = 1, rampPx = 0f))
    }

    @Test
    fun invertedPillClearRegionSpansBetweenBands() {
        val geometry = pillGeometry(cornerRadius = 0f)

        assertEquals(Offset(WIDTH / 2, (TOP_BAND + HEIGHT - BOTTOM_BAND) / 2), geometry.center)
        assertEquals((HEIGHT - BOTTOM_BAND - TOP_BAND) / 2, geometry.halfSize.height)
    }

    @Test
    fun invertedPillSidesExtendPastScreenSoOnlyTopAndBottomBlur() {
        assertEquals(WIDTH / 2 + FADE, pillGeometry(cornerRadius = 0f).halfSize.width)
    }

    @Test
    fun invertedPillCornerRadiusClampsToAFullDome() {
        val geometry = pillGeometry(cornerRadius = HEIGHT)

        assertEquals(geometry.halfSize.height, geometry.cornerRadius)
    }

    @Test
    fun invertedPillWithOverlappingBandsHasNoClearRegion() {
        val geometry =
            invertedPillGeometry(
                size = Size(WIDTH, HEIGHT),
                topExtent = HEIGHT,
                bottomExtent = HEIGHT,
                sideOverflow = FADE,
                cornerRadius = FADE,
            )

        assertEquals(0f, geometry.halfSize.height)
        assertEquals(0f, geometry.cornerRadius)
    }

    private fun pillGeometry(cornerRadius: Float) =
        invertedPillGeometry(
            size = Size(WIDTH, HEIGHT),
            topExtent = TOP_BAND,
            bottomExtent = BOTTOM_BAND,
            sideOverflow = FADE,
            cornerRadius = cornerRadius,
        )

    private fun radiusAt(fraction: Float, stops: List<BlurStop> = this.stops): Dp {
        val after = stops.indexOfFirst { it.fraction >= fraction }
        val end = stops[after]
        if (after == 0 || end.fraction == fraction) return end.radius
        val start = stops[after - 1]
        val t = (fraction - start.fraction) / (end.fraction - start.fraction)
        return start.radius + (end.radius - start.radius) * t
    }

    private companion object {
        const val HEIGHT = 800f
        const val WIDTH = 400f
        const val TOP_BAND = 100f
        const val BOTTOM_BAND = 200f
        const val FADE = 64f
        const val TOP_RADIUS = 24f
        const val BOTTOM_RADIUS = 32f
        const val RAMP = 120f
        const val HALF = 0.5f
        const val MAX_STOPS = 16
        const val TOLERANCE = 0.01f
        const val EASED_QUARTER_MIN = 0.75f
    }
}
