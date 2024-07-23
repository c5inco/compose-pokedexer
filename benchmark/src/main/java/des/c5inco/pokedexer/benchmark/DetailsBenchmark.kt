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
class DetailsBenchmark{
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun pagePokemonCompilationNone() = pagePokemon(CompilationMode.None())

    @Test
    fun pagePokemonCompilationPartial() = pagePokemon(CompilationMode.Partial())

    fun pagePokemon(
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

            val firstPokemon = device.wait(Until.findObject(By.text("Bulbasaur")), 5000)
            firstPokemon.click()

            device.waitForIdle()
        }
    ) {
        val pager = device.wait(Until.findObject(By.res("PokemonPager")), 5000)

        pager?.let {
            pager.setGestureMargin(device.displayWidth / 5)

            device.wait(Until.findObject(By.text("Bulbasaur")), 5000)
            pager.fling(Direction.RIGHT, 1500)

            device.wait(Until.findObject(By.text("Ivysaur")), 5000)
            pager.fling(Direction.RIGHT, 1500)

            device.wait(Until.findObject(By.text("Venusaur")), 5000)
            pager.fling(Direction.RIGHT, 1500)

            device.wait(Until.findObject(By.text("Charmander")), 1000)
            pager.fling(Direction.RIGHT, 1500)
        }

        device.waitForIdle()
    }
}
