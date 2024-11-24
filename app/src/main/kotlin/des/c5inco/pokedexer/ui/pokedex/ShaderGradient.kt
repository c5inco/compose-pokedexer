package des.c5inco.pokedexer.ui.pokedex

import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import des.c5inco.pokedexer.ui.theme.AppTheme
import des.c5inco.pokedexer.ui.theme.PokemonTypesTheme
import org.intellij.lang.annotations.Language

@Language("AGSL")
val AngledShader = """
    uniform float2 resolution;
    layout(color) uniform half4 startColor;
    layout(color) uniform half4 endColor;

    vec4 main(vec2 fragCoord) {
        vec2 uv = fragCoord / resolution.xy;
        
        float angle = radians(15.0);
        vec2 direction = vec2(cos(angle), sin(angle));
        
        // Calculate the gradient
        float gradient = dot(uv, direction);
        
        // Interpolate between the colors
        return mix(startColor, endColor, gradient);
    }
""".trimIndent()

@Language("AGSL")
val MovingShader = """
    uniform float time;
    uniform float2 resolution;
    layout(color) uniform half4 startColor;
    layout(color) uniform half4 endColor;

    vec4 main(vec2 fragCoord) {
        vec2 uv = fragCoord / resolution.xy;
        
        float angle = radians(15.0);
        vec2 direction = vec2(cos(angle), sin(angle));
        
        // Calculate the gradient
        float gradient = dot(uv, direction);
        
        // Interpolate between the colors
        float mixValue = dot(uv, vec2(0.2, 0.2)) + abs(sin(time * 0.7));
        return mix(startColor, endColor, gradient);
    }
""".trimIndent()

@Language("AGSL")
val LinearSRGBShader = """
    // https://bottosson.github.io/posts/colorwrong/#what-can-we-do%3F
    vec3 linearSrgbToSrgb(vec3 x) {
        vec3 xlo = 12.92*x;
        vec3 xhi = 1.055 * pow(x, vec3(1.0/2.4)) - 0.055;
        return mix(xlo, xhi, step(vec3(0.0031308), x));
    
    }
    
    vec3 srgbToLinearSrgb(vec3 x) {
        vec3 xlo = x / 12.92;
        vec3 xhi = pow((x + 0.055)/(1.055), vec3(2.4));
        return mix(xlo, xhi, step(vec3(0.04045), x));
    }
    
    uniform float2 resolution;
    layout(color) uniform half4 startColor;
    layout(color) uniform half4 endColor;

    vec4 main(vec2 fragCoord) {
        // Implicit assumption in here that colors are full opacity
        float fraction = fragCoord.x / resolution.x;
        
        // Convert start and end colors to linear SRGB
        vec3 linearStart = srgbToLinearSrgb(startColor.xyz);
        vec3 linearEnd = srgbToLinearSrgb(endColor.xyz);
        
        // Interpolate in linear SRGB space
        vec3 linearInterpolated = mix(linearStart, linearEnd, fraction);
        
        // And convert back to SRGB
        return vec4(linearSrgbToSrgb(linearInterpolated), 1.0);
    }
""".trimIndent()


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun Modifier.shaderGradientBackground(
    startColor: Color,
    endColor: Color,
) = this.drawWithCache {
    val shader = RuntimeShader(AngledShader)

    shader.setFloatUniform("resolution", size.width, size.height)
    shader.setColorUniform("startColor", startColor.toArgb())
    shader.setColorUniform("endColor", endColor.toArgb())

    val gradientBrush = ShaderBrush(shader)
    onDrawBehind {
        drawRect(
            brush = gradientBrush,
            size = size
        )
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@PreviewLightDark
@Composable
private fun ShaderGradientPreview() {
    val time by produceState(0f) {
        while (true) {
            withInfiniteAnimationFrameMillis {
                value = it / 2000f
            }
        }
    }

    AppTheme {
        PokemonTypesTheme(
            types = SamplePokemonData[9].typeOfPokemon,
        ) {
            Surface {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    val startColor = PokemonTypesTheme.colorScheme.surfaceVariant
                    val endColor = PokemonTypesTheme.colorScheme.surface

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .shaderGradientBackground(startColor, endColor)
                    ) { }

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .drawWithCache {
                                val linearSrgbShader = RuntimeShader(LinearSRGBShader)

                                linearSrgbShader.setFloatUniform("resolution", size.width, size.height)
                                linearSrgbShader.setColorUniform("startColor", startColor.toArgb())
                                linearSrgbShader.setColorUniform("endColor", endColor.toArgb())

                                val gradientBrush = ShaderBrush(linearSrgbShader)
                                onDrawBehind {
                                    drawRect(
                                        brush = gradientBrush,
                                        size = size
                                    )
                                }
                            }
                    ) { }

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(startColor, endColor),
                                )
                            )
                    ) { }
                }
            }
        }
    }
}