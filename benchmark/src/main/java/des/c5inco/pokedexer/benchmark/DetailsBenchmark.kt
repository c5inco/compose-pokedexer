package des.c5inco.pokedexer.benchmark

import android.content.Intent
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
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
            packageName = "des.c5inco.pokedexer.meshbenchmark",
            metrics = listOf(FrameTimingMetric()),
            iterations = 3,
            compilationMode = compilationMode,
            startupMode = null,
            setupBlock = {
                killProcess()
                pressHome()
                startActivityAndWait { it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) }

                val button = device.findObject(By.text("Pokédex"))
                button.click()

                device.waitForIdle()

                val firstPokemon =
                    checkNotNull(
                        device.wait(
                            Until.findObject(By.text("Bulbasaur")),
                            POKEMON_LOAD_TIMEOUT_MILLIS,
                        )
                    ) { "Bulbasaur card not found in time" }
                firstPokemon.click()

                device.waitForIdle()
            },
        ) {
            val pager =
                checkNotNull(
                    device.wait(
                        Until.findObject(By.scrollable(true)),
                        POKEMON_LOAD_TIMEOUT_MILLIS,
                    )
                ) { "Pokemon pager not found in time" }
            val bounds = pager.visibleBounds
            val startX = bounds.right - (bounds.width() / GESTURE_MARGIN_DIVISOR)
            val endX = bounds.left + (bounds.width() / GESTURE_MARGIN_DIVISOR)
            val y = bounds.centerY()

            listOf("Ivysaur", "Venusaur", "Charmander", "Charmeleon").forEach { pokemon ->
                device.executeShellCommand("input swipe $startX $y $endX $y $SWIPE_DURATION_MILLIS")
                check(device.wait(Until.hasObject(By.text(pokemon)), POKEMON_LOAD_TIMEOUT_MILLIS)) {
                    "$pokemon page not found in time"
                }
            }

            Thread.sleep(POST_SWIPE_DELAY_MILLIS)
        }

    private companion object {
        const val POKEMON_LOAD_TIMEOUT_MILLIS = 5_000L
        const val GESTURE_MARGIN_DIVISOR = 5
        const val SWIPE_DURATION_MILLIS = 1_000
        const val POST_SWIPE_DELAY_MILLIS = 500L
    }
}
