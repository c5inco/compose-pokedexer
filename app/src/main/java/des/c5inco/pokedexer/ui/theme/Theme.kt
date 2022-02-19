package des.c5inco.pokedexer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// private val DarkColorPalette = darkColors(
//     primary = Purple200, primaryVariant = Purple700, secondary = Teal200
// )

private val LightColorPalette = lightColors(
    primary = Color.White,
    primaryVariant = Color(0xFFC20029),
    onPrimary = Color.Black,
    secondary = Color.White,
    onSecondary = Color.Black,
    background = Color(0xFFEEEEEE),
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    error = Color(0xFFD00036),
    onError = Color.White
)

class Theme {
    companion object {
        @Composable
        fun PokedexerTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
            val colors = if (darkTheme) {
                LightColorPalette
            } else {
                LightColorPalette
            }

            MaterialTheme(
                colors = colors, typography = Typography, shapes = Shapes, content = content
            )
        }
    }
}
