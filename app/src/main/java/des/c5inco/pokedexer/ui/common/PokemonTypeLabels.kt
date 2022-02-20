package des.c5inco.pokedexer.ui.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class TypeLabelMetrics(
    val cornerRadius: Dp,
    val fontSize: TextUnit,
    val verticalPadding: Dp,
    val horizontalPadding: Dp,
    val elementSpacing: Dp
) {
    companion object {
        val SMALL = TypeLabelMetrics(24.dp, 9.sp, 3.dp, 8.dp, 8.dp)
        val MEDIUM = TypeLabelMetrics(24.dp, 12.sp, 4.dp, 12.dp, 8.dp)
    }
}

@Composable
fun PokemonTypeLabels(
    types: List<String>?,
    metrics: TypeLabelMetrics
) {
    types?.forEach {
        Surface(
            color = Color(0x38FFFFFF),
            shape = RoundedCornerShape(metrics.cornerRadius)
        ) {
            PokemonTypeLabel(it, metrics)
        }
        Spacer(modifier = Modifier.size(metrics.elementSpacing))
    }
}

@Composable
fun PokemonTypeLabel(
    text: String,
    metrics: TypeLabelMetrics
) {
    Text(
        text = text,
        fontSize = metrics.fontSize,
        color = Color.White,
        modifier = Modifier.padding(
            start = metrics.horizontalPadding,
            end = metrics.horizontalPadding,
            top = metrics.verticalPadding,
            bottom = metrics.verticalPadding
        ),
    )
}