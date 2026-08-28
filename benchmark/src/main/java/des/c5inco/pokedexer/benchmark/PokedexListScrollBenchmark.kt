package des.c5inco.pokedexer.benchmark

import android.content.Intent
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import junit.framework.TestCase.fail
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PokedexListScrollBenchmark {
    @get:Rule val benchmarkRule = MacrobenchmarkRule()

    @Test fun scrollCompilationNone() = scroll(CompilationMode.None())

    @Test fun scrollCompilationPartial() = scroll(CompilationMode.Partial())

    private fun scroll(compilationMode: CompilationMode) =
        benchmarkRule.measureRepeated(
            packageName = "des.c5inco.pokedexer.meshbenchmark",
            metrics = listOf(FrameTimingMetric()),
            iterations = 3,
            compilationMode = compilationMode,
            startupMode = null,
            setupBlock = {
                killProcess()
                pressHome()
                startActivityAndWait { it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) }

                val textSelector = By.text("Pokédex")
                if (!device.wait(Until.hasObject(textSelector), CONTENT_LOAD_TIMEOUT_MILLIS)) {
                    fail("Pokédex menu item not found in time")
                }

                device.findObject(textSelector).click()
                device.waitForIdle()
            },
        ) {
            repeat(SCROLL_CYCLES) {
                val listSelector = By.scrollable(true)
                if (!device.wait(Until.hasObject(listSelector), CONTENT_LOAD_TIMEOUT_MILLIS)) {
                    fail("List not found in time")
                }
                val list = device.findObject(listSelector)
                val bounds = list.visibleBounds
                val x = bounds.centerX()
                val top = bounds.top + (bounds.height() / GESTURE_MARGIN_DIVISOR)
                val bottom = bounds.bottom - (bounds.height() / GESTURE_MARGIN_DIVISOR)
                device.executeShellCommand("input swipe $x $bottom $x $top $SWIPE_DURATION_MILLIS")
                Thread.sleep(POST_SWIPE_DELAY_MILLIS)
                device.executeShellCommand("input swipe $x $top $x $bottom $SWIPE_DURATION_MILLIS")
                Thread.sleep(POST_SWIPE_DELAY_MILLIS)
            }
        }

    private companion object {
        const val CONTENT_LOAD_TIMEOUT_MILLIS = 5_000L
        const val SCROLL_CYCLES = 3
        const val GESTURE_MARGIN_DIVISOR = 5
        const val SWIPE_DURATION_MILLIS = 300
        const val POST_SWIPE_DELAY_MILLIS = 500L
    }
}
