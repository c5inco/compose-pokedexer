package des.c5inco.pokedexer.benchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailsBenchmark {
    @get:Rule val benchmarkRule = MacrobenchmarkRule()

    @Test fun pagePokemonCompilationNone() = pagePokemon(CompilationMode.None())

    @Test fun pagePokemonCompilationPartial() = pagePokemon(CompilationMode.Partial())

    fun pagePokemon(compilationMode: CompilationMode) =
        benchmarkRule.measureRepeated(
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

                val firstPokemon =
                    device.wait(Until.findObject(By.text("Bulbasaur")), POKEMON_LOAD_TIMEOUT_MILLIS)
                firstPokemon.click()

                device.waitForIdle()
            },
        ) {
            val pager =
                device.wait(Until.findObject(By.res("PokemonPager")), POKEMON_LOAD_TIMEOUT_MILLIS)

            pager?.let {
                pager.setGestureMargin(device.displayWidth / GESTURE_MARGIN_DIVISOR)

                device.wait(Until.findObject(By.text("Bulbasaur")), POKEMON_LOAD_TIMEOUT_MILLIS)
                pager.fling(Direction.RIGHT, PAGER_FLING_SPEED_PIXELS_PER_SECOND)

                device.wait(Until.findObject(By.text("Ivysaur")), POKEMON_LOAD_TIMEOUT_MILLIS)
                pager.fling(Direction.RIGHT, PAGER_FLING_SPEED_PIXELS_PER_SECOND)

                device.wait(Until.findObject(By.text("Venusaur")), POKEMON_LOAD_TIMEOUT_MILLIS)
                pager.fling(Direction.RIGHT, PAGER_FLING_SPEED_PIXELS_PER_SECOND)

                device.wait(
                    Until.findObject(By.text("Charmander")),
                    FINAL_POKEMON_LOAD_TIMEOUT_MILLIS,
                )
                pager.fling(Direction.RIGHT, PAGER_FLING_SPEED_PIXELS_PER_SECOND)
            }

            device.waitForIdle()
        }

    private companion object {
        const val POKEMON_LOAD_TIMEOUT_MILLIS = 5_000L
        const val FINAL_POKEMON_LOAD_TIMEOUT_MILLIS = 1_000L
        const val GESTURE_MARGIN_DIVISOR = 5
        const val PAGER_FLING_SPEED_PIXELS_PER_SECOND = 1_500
    }
}
