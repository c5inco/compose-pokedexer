package des.c5inco.pokedexer.ui.typechart

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberScrollable2DState
import androidx.compose.foundation.gestures.scrollable2D
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberOverscrollEffect
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import des.c5inco.pokedexer.data.typechart.TypeEffectiveness
import des.c5inco.pokedexer.model.Type
import des.c5inco.pokedexer.ui.common.mapTypeToIcon
import des.c5inco.pokedexer.ui.theme.AppTheme
import des.c5inco.pokedexer.ui.theme.PokemonTypesTheme
import des.c5inco.pokedexer.ui.theme.SuperEllipse
import kotlin.math.abs

private val CellSize = 48.dp
private val HeaderCellSize = 48.dp

@Composable
fun TypeChartScreenRoute(onBackClick: () -> Unit = {}) {
    TypeChartScreen(onBackClick = onBackClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypeChartScreen(onBackClick: () -> Unit = {}) {
    val types = TypeEffectiveness.displayOrder
    val density = LocalDensity.current

    // Calculate content dimensions
    val cellSizePx = with(density) { CellSize.toPx() }
    val contentWidth = cellSizePx * types.size
    val contentHeight = cellSizePx * types.size

    // Track scroll offsets
    var offset by remember { mutableStateOf(Offset.Zero) }

    // Track viewport size for scroll bounds
    var viewportWidth by remember { mutableFloatStateOf(0f) }
    var viewportHeight by remember { mutableFloatStateOf(0f) }

    val scrollable2DState = rememberScrollable2DState { delta ->
        // Calculate max scroll (content size - viewport size)
        val maxScrollX = (contentWidth - viewportWidth).coerceAtLeast(0f)
        val maxScrollY = (contentHeight - viewportHeight).coerceAtLeast(0f)

        // Option 1: Filter out deltas if detecting a specific direction
        if (abs(delta.x) > abs(delta.y)) {
            // Horizontal scroll
            val newOffsetX = (offset.x + delta.x).coerceIn(-maxScrollX, 0f)
            offset = Offset(newOffsetX, offset.y)
        } else {
            // Vertical scroll
            val newOffsetY = (offset.y + delta.y).coerceIn(-maxScrollY, 0f)
            offset = Offset(offset.x, newOffsetY)
        }

        // Option 2: Don't filter in any direction
        // val newOffsetX = (offset.x + delta.x).coerceIn(-maxScrollX, 0f)
        // val newOffsetY = (offset.y + delta.y).coerceIn(-maxScrollY, 0f)
        // offset = Offset(newOffsetX, newOffsetY)

        delta
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Type Chart") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                )
            )
        }
    ) { innerPadding ->
        val topPadding = innerPadding.calculateTopPadding()

        // Data cells - scrolls both directions with scrollable2D
        Box(Modifier.padding(top = topPadding)) {
            Box(
                modifier =
                    Modifier
                        .padding(start = CellSize)
                        .onSizeChanged { size ->
                            viewportWidth = size.width.toFloat()
                            viewportHeight = with (density) { size.height - topPadding.toPx() }
                        }
                        .scrollable2D(
                            state = scrollable2DState,
                            overscrollEffect = rememberOverscrollEffect()
                        )
            ) {
                Column(
                    modifier =
                        Modifier.wrapContentSize(align = Alignment.TopStart, unbounded = true)
                            .graphicsLayer {
                                translationX = offset.x
                                translationY = offset.y
                            }
                ) {
                    types.forEach { attackerType ->
                        Row(Modifier.wrapContentWidth(unbounded = true)) {
                            types.forEach { defenderType ->
                                val effectiveness =
                                    TypeEffectiveness.getEffectiveness(
                                        attacker = attackerType,
                                        defender = defenderType,
                                    )
                                EffectivenessCell(effectiveness = effectiveness)
                            }
                        }
                    }
                }
            }
            // Top header row (frozen Y, scrolls with X)
            Row(
                modifier = Modifier.fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceContainer)
            ) {
                Box(Modifier.size(CellSize)) {}
                Row(
                    modifier =
                        Modifier.wrapContentWidth(align = Alignment.Start, unbounded = true)
                            .graphicsLayer { translationX = offset.x }
                ) {
                    types.forEach { defenderType -> TypeHeaderCell(type = defenderType) }
                }
            }

            // Content area with frozen Y-axis header
            Column(
                Modifier.fillMaxHeight().background(MaterialTheme.colorScheme.surfaceContainer)
            ) {
                Column(
                    modifier =
                        Modifier
                            .wrapContentHeight(align = Alignment.Top, unbounded = true)
                            .graphicsLayer { translationY = offset.y }
                ) {
                    Box(Modifier.size(CellSize)) {}
                    types.forEach { attackerType -> TypeHeaderCell(type = attackerType) }
                    Spacer(Modifier.height(20.dp))
                }
            }
            Box(
                Modifier.background(MaterialTheme.colorScheme.surfaceContainer).size(CellSize)
            ) {}
        }
    }
}

@Composable
private fun TypeHeaderCell(type: Type, modifier: Modifier = Modifier) {
    val shape = remember { SuperEllipse() }

    PokemonTypesTheme(types = listOf(type.toString())) {
        Box(modifier = modifier.size(HeaderCellSize), contentAlignment = Alignment.Center) {
            Surface(
                modifier = Modifier.size(36.dp).clip(shape),
                color = PokemonTypesTheme.colorScheme.surface,
                contentColor = PokemonTypesTheme.colorScheme.onSurface,
            ) {
                Icon(
                    painter = painterResource(id = mapTypeToIcon(type)),
                    contentDescription = type.name,
                    modifier = Modifier.padding(4.dp).size(28.dp),
                )
            }
        }
    }
}

@Composable
private fun EffectivenessCell(effectiveness: Float, modifier: Modifier = Modifier) {
    val (backgroundColor, text) =
        when (effectiveness) {
            2f -> Color(0xFF4CAF50) to "2×"
            0.5f -> Color(0xFFF44336) to "½×"
            0f -> Color(0xFF424242) to "0"
            else -> Color.Transparent to ""
        }

    Box(
        modifier =
            modifier
                .size(CellSize)
                .padding(2.dp)
                .background(
                    color = backgroundColor.copy(alpha = if (effectiveness == 1f) 0f else 0.2f),
                    shape = MaterialTheme.shapes.small,
                ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            color =
                when (effectiveness) {
                    2f -> Color(0xFF2E7D32)
                    0.5f -> Color(0xFFC62828)
                    0f -> Color(0xFF9E9E9E)
                    else -> Color.Transparent
                },
        )
    }
}

@Composable
private fun LegendRow(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LegendItem(color = Color(0xFF4CAF50), text = "2× Super Effective")
        LegendItem(color = Color(0xFFF44336), text = "½× Not Very Effective")
        LegendItem(color = Color(0xFF424242), text = "0 No Effect")
    }
}

@Composable
private fun LegendItem(
    color: Color,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color.copy(alpha = 0.3f), MaterialTheme.shapes.extraSmall)
        )
        Text(
            text = text,
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TypeChartScreenPreview() {
    AppTheme {
        TypeChartScreen()
    }
}
