package des.c5inco.pokedexer.ui.pokedex

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

private const val MAX_RADIUS_DP = 56f
private const val MAX_FADE_DP = 160f
private const val MAX_RAMP_DP = 240f
private const val MAX_CORNER_RADIUS_DP = 480f
private const val LABEL_WEIGHT = 0.4f
private const val SLIDER_WEIGHT = 0.6f

private val RadiusRange = 0f..MAX_RADIUS_DP
private val FadeRange = 0f..MAX_FADE_DP
private val RampRange = 0f..MAX_RAMP_DP
private val CornerRadiusRange = 0f..MAX_CORNER_RADIUS_DP

/** Debug-only controls for tuning the Pokedex edge blur live. */
@Composable
internal fun EdgeBlurTuningPanel(
    tuning: EdgeBlurTuning,
    onTuningChange: (EdgeBlurTuning) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge,
        color = MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = 0.94f),
        shadowElevation = 6.dp,
    ) {
        Column(
            modifier =
                Modifier.verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Edge blur",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f),
                )
                TextButton(onClick = { onTuningChange(EdgeBlurTuning()) }) { Text("Reset") }
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Filled.Close, contentDescription = "Close blur tuning")
                }
            }
            ShapeControls(tuning, onTuningChange)
            TopControls(tuning, onTuningChange)
            BottomControls(tuning, onTuningChange)
        }
    }
}

@Composable
private fun TopControls(tuning: EdgeBlurTuning, onTuningChange: (EdgeBlurTuning) -> Unit) {
    SectionLabel("Top app bar")
    DpSlider("Radius", tuning.topRadius, RadiusRange) {
        onTuningChange(tuning.copy(topRadius = it))
    }
    DpSlider("Fade", tuning.topFade, FadeRange) { onTuningChange(tuning.copy(topFade = it)) }
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(40.dp)) {
        Text(
            "Scroll-linked",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f),
        )
        Switch(
            checked = tuning.scrollLinked,
            onCheckedChange = { onTuningChange(tuning.copy(scrollLinked = it)) },
        )
    }
    if (tuning.scrollLinked) {
        DpSlider("Scroll ramp", tuning.scrollRamp, RampRange) {
            onTuningChange(tuning.copy(scrollRamp = it))
        }
    }
}

@Composable
private fun ShapeControls(tuning: EdgeBlurTuning, onTuningChange: (EdgeBlurTuning) -> Unit) {
    SectionLabel("Shape")
    SingleChoiceSegmentedButtonRow(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        EdgeBlurShape.entries.forEachIndexed { index, shape ->
            SegmentedButton(
                selected = tuning.shape == shape,
                onClick = { onTuningChange(tuning.copy(shape = shape)) },
                shape = SegmentedButtonDefaults.itemShape(index, EdgeBlurShape.entries.size),
            ) {
                Text(shape.name)
            }
        }
    }
    if (tuning.shape == EdgeBlurShape.Pill) {
        DpSlider("Corner radius", tuning.cornerRadius, CornerRadiusRange) {
            onTuningChange(tuning.copy(cornerRadius = it))
        }
    }
}

@Composable
private fun BottomControls(tuning: EdgeBlurTuning, onTuningChange: (EdgeBlurTuning) -> Unit) {
    SectionLabel("Filter button")
    DpSlider("Radius", tuning.bottomRadius, RadiusRange) {
        onTuningChange(tuning.copy(bottomRadius = it))
    }
    DpSlider("Fade", tuning.bottomFade, FadeRange) { onTuningChange(tuning.copy(bottomFade = it)) }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(top = 8.dp),
    )
}

@Composable
private fun DpSlider(
    label: String,
    value: Dp,
    range: ClosedFloatingPointRange<Float>,
    onValueChange: (Dp) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            "$label ${value.value.roundToInt()}dp",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(LABEL_WEIGHT),
        )
        Slider(
            value = value.value,
            onValueChange = { onValueChange(it.dp) },
            valueRange = range,
            modifier = Modifier.weight(SLIDER_WEIGHT).height(36.dp),
        )
    }
}
