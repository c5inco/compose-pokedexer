package des.c5inco.pokedexer.ui.common

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.ui.theme.Theme.Companion.PokedexerTheme
import kotlin.math.tan

@Preview
@Composable
fun StatsChartPreview() {
    PokedexerTheme {
        Surface {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                StatsChart(
                    size = 240,
                    hp = 45,
                    attack = 49,
                    defense = 49,
                    specialAttack = 65,
                    specialDefense = 65,
                    speed = 45
                )
                StatsChart()
            }
        }
    }
}

@Composable
fun StatsChart(
    size: Int = 240,
    hp: Int = 200,
    attack: Int = 200,
    defense: Int = 200,
    specialAttack: Int = 200,
    specialDefense: Int = 200,
    speed: Int = 200,
) {
    val maxStat = 500f

    Box {
        StatRing(
            ringSize = size,
            cornerRadius = 64f,
            drawOriginLines = true,
            color = Color(0xfff5f5f5),
        )

        AnimatedStatRing(
            ringSize = size,
            strokeWidth = 4f,
            color = Color.Magenta,
            hpRatio = hp / maxStat,
            attackRatio = attack / maxStat,
            defenseRatio = defense / maxStat,
            specialAttackRatio = specialAttack / maxStat ,
            specialDefenseRatio = specialDefense / maxStat,
            speedRatio = speed / maxStat
        )
    }
}

@Composable
private fun AnimatedStatRing(
    ringSize: Int,
    color: Color = Color.Black,
    strokeWidth: Float = 16f,
    drawOriginLines: Boolean = false,
    hpRatio: Float = 1f,
    attackRatio: Float = 1f,
    defenseRatio: Float = 1f,
    specialAttackRatio: Float = 1f,
    specialDefenseRatio: Float = 1f,
    speedRatio: Float = 1f,
) {
    val localDensity = LocalDensity.current
    var expanded by remember { mutableStateOf(true) }

    val wr = (localDensity.density * ringSize) / 2
    val hr = (localDensity.density * (ringSize * 1.15f)) / 2

    val originOffset = Offset(x = wr, y = hr)
    val angle = 32.0.toRadian()

    val statAnimationSpec: AnimationSpec<Offset> =
        tween(durationMillis = 300)

    val animatedHp by animateOffsetAsState(
        targetValue = if (!expanded) {
            originOffset
        } else {
            Offset(
                x = wr,
                y = hr - hr * hpRatio
            )
        },
        animationSpec = statAnimationSpec
    )
    val animatedAttack by animateOffsetAsState(
        targetValue = if (!expanded) {
            originOffset
        } else {
            Offset(
                x = wr + wr * attackRatio,
                y = hr - (wr * attackRatio) * tan(angle).toFloat()
            )
        },
        animationSpec = statAnimationSpec
    )
    val animatedDefense by animateOffsetAsState(
        targetValue = if (!expanded) {
            originOffset
        } else {
            Offset(
                x = wr + wr * defenseRatio,
                y = hr - (wr * defenseRatio) * tan(-angle).toFloat()
            )
        },
        animationSpec = statAnimationSpec
    )
    val animatedSpeed by animateOffsetAsState(
        targetValue = if (!expanded) {
            originOffset
        } else {
            Offset(
                x = wr,
                y = hr + hr * speedRatio
            )
        },
        animationSpec = statAnimationSpec
    )
    val animatedSpecialAttack by animateOffsetAsState(
        targetValue = if (!expanded) {
            originOffset
        } else {
            Offset(
                x = wr - wr * specialAttackRatio,
                y = hr - (wr * specialAttackRatio) * tan(angle).toFloat()
            )
        },
        animationSpec = statAnimationSpec
    )
    val animatedSpecialDefense by animateOffsetAsState(
        targetValue = if (!expanded) {
            originOffset
        } else {
            Offset(
                x = wr - wr * specialDefenseRatio,
                y = hr - (wr * specialDefenseRatio) * tan(-angle).toFloat()
            )
        },
        animationSpec = statAnimationSpec
    )
    Canvas(
        Modifier
            .padding(16.dp)
            .size(width = ringSize.dp, height = ringSize.dp * 1.15f)
            .clickable(
                onClick = { expanded = !expanded },
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
    ) {
        val (width, height) = size

        val path = Path()
        path.moveTo(animatedHp.x, animatedHp.y)
        path.lineTo(animatedAttack.x, animatedAttack.y)
        path.lineTo(animatedDefense.x, animatedDefense.y)
        path.lineTo(animatedSpeed.x, animatedSpeed.y)
        path.lineTo(animatedSpecialDefense.x, animatedSpecialDefense.y)
        path.lineTo(animatedSpecialAttack.x, animatedSpecialAttack.y)
        path.close()

        if (drawOriginLines) {
            drawOriginLines(
                width = width,
                height = height,
                angle = angle,
                color = Color.White
            )
        }

        drawPath(
            path = path,
            color = color.copy(0.1f),
            style = Fill
        )

        drawPath(
            path = path,
            color = color,
            style = Stroke(
                width = strokeWidth,
            ),
        )
    }
}

@Composable
private fun StatRing(
    ringSize: Int,
    color: Color = Color.Black,
    cornerRadius: Float = 8f,
    drawOriginLines: Boolean = false,
    hpRatio: Float = 1f,
    attackRatio: Float = 1f,
    defenseRatio: Float = 1f,
    specialAttackRatio: Float = 1f,
    specialDefenseRatio: Float = 1f,
    speedRatio: Float = 1f,
) {
    Canvas(
        Modifier
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

        this.drawContext.canvas.drawPathWithPaint(
            path = path,
            paint = Paint().apply {
                this.color = color
                this.pathEffect = PathEffect.cornerPathEffect(cornerRadius)

                style = PaintingStyle.Fill
            }
        )

        if (drawOriginLines) {
            drawOriginLines(
                width = width,
                height = height,
                angle = angle,
                color = Color.White
            )
        }
    }
}

fun Canvas.drawPathWithPaint(
    path: Path,
    paint: Paint = Paint()
) {
    drawPath(
        path,
        paint
    )
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

