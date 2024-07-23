package des.c5inco.pokedexer.benchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
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

    fun scroll(
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

            val button = device.findObject(By.text("Pokedex"))
            button.click()

            device.waitForIdle()
        }
    ) {
        val list = device.wait(Until.findObject(By.res("PokedexLazyGrid")), 5000)

        if (list != null) {
            list.setGestureMargin(device.displayWidth / 5)
            list.fling(Direction.DOWN)
        }

        device.waitForIdle()
    }
}
