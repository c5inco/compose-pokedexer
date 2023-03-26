package des.c5inco.pokedexer.benchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
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
    ) = measure(
        compilationMode = compilationMode,
        setupBlock = {
            pressHome()
            startActivityAndWait()

            val button = device.findObject(By.text("Pokedex"))
            button.click()

            device.waitForIdle()

            val firstPokemon = device.findObject(By.text("Bulbasaur"))
            firstPokemon.click()
        }
    ) {
        device.waitForIdle()

        val pager = device.findObject(By.res("PokemonPager"))
        if (pager != null) {
            pager.setGestureMargin(device.displayWidth / 5)
            repeat(4) {
                pager.swipe(Direction.LEFT, 0.2f)
            }
        }

        device.waitForIdle()
    }

    private fun measure(
        compilationMode: CompilationMode,
        setupBlock: MacrobenchmarkScope.() -> Unit,
        measureBlock: MacrobenchmarkScope.() -> Unit
    ) {
        return benchmarkRule.measureRepeated(
            packageName = "des.c5inco.pokedexer",
            metrics = listOf(FrameTimingMetric()),
            iterations = 5,
            compilationMode = compilationMode,
            startupMode = StartupMode.COLD,
            setupBlock = setupBlock,
            measureBlock = measureBlock
        )
    }
}