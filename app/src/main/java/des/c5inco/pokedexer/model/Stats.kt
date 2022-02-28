package des.c5inco.pokedexer.model

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector3D
import androidx.compose.animation.core.TwoWayConverter

data class Stat(
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int,
)

data class StatHolder(
    val s1: Int,
    val s2: Int,
    val s3: Int,
)

val StatHolderToVector: () -> TwoWayConverter<StatHolder, AnimationVector3D> = {
    TwoWayConverter(convertToVector = {
        AnimationVector3D(
            it.s1.toFloat(),
            it.s2.toFloat(),
            it.s3.toFloat()
        )
    }, convertFromVector = {
        StatHolder(it.v1.toInt(), it.v2.toInt(), it.v3.toInt())
    })
}

fun Animatable(initialValue: StatHolder): Animatable<StatHolder, AnimationVector3D> =
    Animatable(initialValue, StatHolderToVector())

