package des.c5inco.pokedexer.ui.common

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.model.color
import des.c5inco.pokedexer.ui.theme.AppTheme
import kotlin.math.tan

@Preview
@Composable
fun StatsChartPreview() {
    AppTheme {
        Surface {
            Column(
                Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var loop by remember { mutableStateOf(0) }
                val p by remember(loop) {
                    derivedStateOf { SamplePokemonData[loop] }
                }

                LaunchedEffect(true) {
                    infiniteLoopFlow.collect {
                        loop = it
                    }
                }

                Box {
                    StatRingBackground(
                        ringSize = 300
                    )
                    StatRingValues(
                        ringSize = 300,
                        pokemon = p
                    )
                }
            }
        }
    }
}

@Composable
fun StatsChart(
    size: Int = 300,
    pokemon: Pokemon
) {
    Box {
        StatRingBackground(
            ringSize = size
        )
        StatRingValues(
            ringSize = size,
            pokemon = pokemon
        )
    }
}

@Composable
private fun StatRingBackground(ringSize: Int) {
    StatRingCanvas(
        ringSize = ringSize,
        color = Color(0xfff5f5f5),
    ) { size, path, color ->
        this.drawContext.canvas.drawPathWithPaint(path = path, paint = Paint().apply {
            this.color = color
            this.pathEffect = PathEffect.cornerPathEffect(64f)

            style = PaintingStyle.Fill
        })

        drawOriginLines(
            width = size.width, height = size.height, angle = 32.0.toRadian(), color = Color.White
        )
    }
}

@Composable
private fun StatRingValues(
    ringSize: Int,
    pokemon: Pokemon
) {
    val maxStat = 180f

    val animateColor by animateColorAsState(
        targetValue = pokemon.color(),
    )
    val animateHp by animateFloatAsState(
        targetValue = pokemon.hp / maxStat,
    )
    val animateAttack by animateFloatAsState(
        targetValue = pokemon.attack / maxStat
    )
    val animateDefense by animateFloatAsState(
        targetValue = pokemon.defense / maxStat
    )
    val animateSpeed by animateFloatAsState(
        targetValue = pokemon.speed / maxStat,
    )
    val animateSpecialAttack by animateFloatAsState(
        targetValue = pokemon.specialAttack / maxStat,
    )
    val animateSpecialDefense by animateFloatAsState(
        targetValue = pokemon.specialDefense / maxStat,
    )

    StatRingCanvas(
        ringSize = ringSize,
        hpRatio = animateHp,
        attackRatio = animateAttack,
        defenseRatio = animateDefense,
        speedRatio = animateSpeed,
        specialDefenseRatio = animateSpecialDefense,
        specialAttackRatio = animateSpecialAttack,
        color = animateColor
    ) { _, path, color ->
        drawPath(
            path = path, color = color.copy(0.3f), style = Fill
        )
        drawPath(
            path = path,
            color = color,
            style = Stroke(width = 8f)
        )
    }
}

@Composable
private fun StatRingCanvas(
    modifier: Modifier = Modifier,
    ringSize: Int,
    color: Color,
    hpRatio: Float = 1f,
    attackRatio: Float = 1f,
    defenseRatio: Float = 1f,
    speedRatio: Float = 1f,
    specialAttackRatio: Float = 1f,
    specialDefenseRatio: Float = 1f,
    drawRing: DrawScope.(size: Size, path: Path, color: Color) -> Unit
) {
    Canvas(
        modifier
            .padding(16.dp)
            .size(width = ringSize.dp, height = ringSize.dp * 1.15f)
    ) {
        val (width, height) = size
        val widthRadius = width / 2
        val heightRadius = height / 2
        val angle = 32.0.toRadian()

        val hp = Offset(
            x = widthRadius,
            y = heightRadius - heightRadius * hpRatio
        )
        val attack = Offset(
            x = widthRadius + widthRadius * attackRatio,
            y = (widthRadius * attackRatio) * tan(angle).toFloat()
        )
        val defense = Offset(
            x = widthRadius + widthRadius * defenseRatio,
            y = (widthRadius * defenseRatio) * tan(-angle).toFloat()
        )
        val speed = Offset(
            x = widthRadius,
            y = heightRadius + heightRadius * speedRatio
        )
        val specialAttack = Offset(
            x = widthRadius - widthRadius * specialAttackRatio,
            y = (widthRadius * specialAttackRatio) * tan(angle).toFloat()
        )
        val specialDefense = Offset(
            x = widthRadius - widthRadius * specialDefenseRatio,
            y = (widthRadius * specialDefenseRatio) * tan(-angle).toFloat()
        )

        val path = Path()
        path.moveTo(hp.x, hp.y)
        path.lineTo(attack.x, heightRadius - attack.y)
        path.lineTo(defense.x, heightRadius - defense.y)
        path.lineTo(speed.x, speed.y)
        path.lineTo(specialDefense.x, heightRadius - specialDefense.y)
        path.lineTo(specialAttack.x, heightRadius - specialAttack.y)
        path.close()

        drawRing(Size(width, height), path, color)
    }
}

private fun DrawScope.drawOriginLines(
    width: Float,
    height: Float,
    angle: Double,
    color: Color,
    strokeWidth: Float = 4f,
    capPadding: Float = 0f
) {
    val widthRadius = width / 2
    val heightRadius = height / 2
    val adjustedWidth = width - capPadding
    val adjustedHeight = height - capPadding
    val adjacent = widthRadius - capPadding
    val oppositeY = heightRadius - adjacent * tan(angle).toFloat()
    val oppositeNegY = heightRadius - adjacent * tan(-angle).toFloat()

    fun drawFromOrigin(end: Offset) {
        drawLine(
            color = color,
            strokeWidth = strokeWidth,
            start = Offset(x = widthRadius, y = heightRadius),
            end = end,
        )
    }

    drawFromOrigin(Offset(
        x = widthRadius,
        y = capPadding
    ))
    drawFromOrigin(Offset(
        x = adjustedWidth,
        y = height - oppositeY
    ))
    drawFromOrigin(Offset(
        x = adjustedWidth,
        y = height - oppositeNegY
    ))
    drawFromOrigin(Offset(
        x = capPadding,
        y = height - oppositeY
    ))
    drawFromOrigin(Offset(
        x = capPadding,
        y = height - oppositeNegY
    ))
    drawFromOrigin(Offset(
        x = widthRadius,
        y = adjustedHeight
    ))
}

