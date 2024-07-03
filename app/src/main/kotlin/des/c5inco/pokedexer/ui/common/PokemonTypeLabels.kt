package des.c5inco.pokedexer.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import des.c5inco.pokedexer.R
import des.c5inco.pokedexer.model.Type
import des.c5inco.pokedexer.ui.theme.AppTheme
import des.c5inco.pokedexer.ui.theme.PokemonTypesTheme
import des.c5inco.pokedexer.ui.theme.SuperEllipse

data class TypeLabelMetrics(
    val cornerRadius: Dp,
    val fontSize: TextUnit,
    val fontWeight: FontWeight = FontWeight.Normal,
    val verticalPadding: Dp,
    val horizontalPadding: Dp,
    val elementSpacing: Dp
) {
    companion object {
        val SMALL = TypeLabelMetrics(24.dp,
            fontSize = 10.sp,
            verticalPadding = 0.dp,
            horizontalPadding = 10.dp,
            elementSpacing = 8.dp
        )
        val MEDIUM = TypeLabelMetrics(24.dp,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            verticalPadding = 0.dp,
            horizontalPadding = 12.dp,
            elementSpacing = 8.dp
        )
    }
}

@Composable
fun PokemonTypeLabels(
    modifier: Modifier = Modifier,
    types: List<String>,
    metrics: TypeLabelMetrics = TypeLabelMetrics.MEDIUM
) {
    PokemonTypesTheme(types = listOf(types[0])) {
        types.forEach {
            TypeLabel(modifier = modifier, text = it, metrics = metrics)
            Spacer(modifier = Modifier.size(metrics.elementSpacing))
        }
    }
}

@Composable
fun TypeLabel(
    modifier: Modifier = Modifier,
    text: String,
    colored: Boolean = false,
    metrics: TypeLabelMetrics = TypeLabelMetrics.MEDIUM
) {
    Surface(
        modifier = modifier,
        color = if (colored) PokemonTypesTheme.colorScheme.surface else Color(0x38FFFFFF),
        shape = RoundedCornerShape(metrics.cornerRadius)
    ) {
        Text(
            text = text,
            fontSize = metrics.fontSize,
            fontWeight = metrics.fontWeight,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(
                start = metrics.horizontalPadding,
                end = metrics.horizontalPadding,
                top = metrics.verticalPadding,
                bottom = metrics.verticalPadding
            ),
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@PreviewLightDark
@Composable
private fun PokemonTypeLabelsPreview() {
    AppTheme {
        Surface {
            FlowRow(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                Type.entries.forEach {
                    PokemonTypesTheme(
                        types = listOf(it.toString())
                    ) {
                        TypeLabel(
                            text = it.toString(),
                            colored = true
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TypeIconLabel(
    modifier: Modifier = Modifier,
    type: Type
) {
    val shape = remember { SuperEllipse() }

    PokemonTypesTheme(types = listOf(type.toString())) {
        Surface(
            modifier = modifier.clip(shape),
            color = PokemonTypesTheme.colorScheme.surface,
        ) {
            Icon(
                painter = painterResource(id = mapTypeToIcon(type)),
                contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .size(24.dp)
            )
        }
    }
}

private fun mapTypeToIcon(type: Type): Int {
    return when (type) {
        Type.Normal -> return R.drawable.ic_type_normal
        Type.Fire -> R.drawable.ic_type_fire
        Type.Water -> R.drawable.ic_type_water
        Type.Electric -> R.drawable.ic_type_electric
        Type.Grass -> R.drawable.ic_type_grass
        Type.Ice -> R.drawable.ic_type_ice
        Type.Fighting -> R.drawable.ic_type_fighting
        Type.Poison -> R.drawable.ic_type_poison
        Type.Ground -> R.drawable.ic_type_ground
        Type.Flying -> R.drawable.ic_type_flying
        Type.Psychic -> R.drawable.ic_type_psychic
        Type.Bug -> R.drawable.ic_type_bug
        Type.Rock -> R.drawable.ic_type_rock
        Type.Ghost -> R.drawable.ic_type_ghost
        Type.Dragon -> R.drawable.ic_type_dragon
        Type.Dark -> R.drawable.ic_type_dark
        Type.Steel -> R.drawable.ic_type_steel
        Type.Fairy -> R.drawable.ic_type_fairy
    }
}

@OptIn(ExperimentalLayoutApi::class)
@PreviewLightDark
@Composable
private fun TypeIconLabelPreview() {
    AppTheme {
        Surface {
            FlowRow(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                Type.entries.forEach {
                    TypeIconLabel(type = it)
                }
            }
        }
    }
}