package des.c5inco.pokedexer.baseline

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class BaselineProfileGenerator {

    @get:Rule val rule = BaselineProfileRule()

    @Test
    fun generate() =
        rule.collect(packageName = "des.c5inco.pokedexer.meshbenchmark") {
            startActivityAndWait()
            device.wait(Until.hasObject(By.text("Pokédex")), APP_START_TIMEOUT_MILLIS)

            val button = device.findObject(By.text("Pokédex"))
            button.click()

            device.wait(Until.hasObject(By.scrollable(true)), GRID_LOAD_TIMEOUT_MILLIS)
            val list = device.findObject(By.scrollable(true))
            if (list != null) {
                list.setGestureMargin(device.displayWidth / GESTURE_MARGIN_DIVISOR)
                list.fling(Direction.DOWN)
            }
        }

    private companion object {
        const val APP_START_TIMEOUT_MILLIS = 100_000L
        const val GRID_LOAD_TIMEOUT_MILLIS = 5_000L
        const val GESTURE_MARGIN_DIVISOR = 5
    }
}
