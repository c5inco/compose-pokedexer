package des.c5inco.pokedexer.benchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MeshGradientBenchmark {
    @get:Rule val benchmarkRule = MacrobenchmarkRule()

    @Test fun legacyMeshGradient() = measure(Mode.Legacy)

    @Test fun nativeMeshGradient() = measure(Mode.Native)

    private fun measure(mode: Mode) =
        benchmarkRule.measureRepeated(
            packageName = PackageName,
            metrics = listOf(FrameTimingMetric()),
            iterations = Iterations,
            compilationMode = CompilationMode.None(),
            startupMode = null,
            setupBlock = {
                killProcess()
                device.executeShellCommand(
                    "am start -W -n $PackageName/$ActivityClass " +
                        "--es mesh_mode ${mode.extraValue}"
                )
                Thread.sleep(ReadyDelayMillis)
            },
        ) {
            Thread.sleep(MeasurementMillis)
        }

    private enum class Mode(val extraValue: String) {
        Legacy("legacy"),
        Native("native"),
    }

    private companion object {
        const val PackageName = "des.c5inco.pokedexer.meshbenchmark"
        const val ActivityClass = "des.c5inco.pokedexer.MeshGradientBenchmarkActivity"
        const val Iterations = 5
        const val ReadyDelayMillis = 500L
        const val MeasurementMillis = 1_000L
    }
}
