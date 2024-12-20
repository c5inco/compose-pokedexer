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

    @get:Rule
    val rule = BaselineProfileRule()

    @Test
    fun generate() = rule.collect(
        packageName = "des.c5inco.pokedexer",
    ) {
        startActivityAndWait()
        device.wait(Until.hasObject(By.text("Pokedex")), 10_0000)

        val button = device.findObject(By.text("Pokedex"))
        button.click()

        device.wait(Until.hasObject(By.res("PokedexLazyGrid")), 5_000)
        val list = device.findObject(By.res("PokedexLazyGrid"))
        if (list != null) {
            list.setGestureMargin(device.displayWidth / 5)
            list.fling(Direction.DOWN)
        }
    }
}