package des.c5inco.pokedexer.benchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import junit.framework.TestCase.fail
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PokedexListScrollBenchmark{
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun scrollCompilationNone() = scroll(CompilationMode.None())

    @Test
    fun scrollCompilationPartial() = scroll(CompilationMode.Partial())

    private fun scroll(
        compilationMode: CompilationMode
    ) = benchmarkRule.measureRepeated(
        packageName = "des.c5inco.pokedexer",
        metrics = listOf(FrameTimingMetric()),
        iterations = 5,
        compilationMode = compilationMode,
        startupMode = null,
        setupBlock = {
            killProcess()
            pressHome()
            startActivityAndWait()

            val textSelector = By.text("Pokedex")
            if (!device.wait(Until.hasObject(textSelector), 5_000)) {
                fail("Pokedex menu item not found in time")
            }

            device.findObject(textSelector).click()
            device.waitForIdle()
        }
    ) {
        repeat(3) {
            val listSelector = By.res("PokedexLazyGrid")
            if (!device.wait(Until.hasObject(listSelector), 5_000)) {
                fail("List not found in time")
            }
            val list = device.findObject(listSelector)
            list.setGestureMarginPercentage(0.2f)
            list.fling(Direction.DOWN)
            device.waitForIdle()
            list.fling(Direction.UP)
        }
    }
}