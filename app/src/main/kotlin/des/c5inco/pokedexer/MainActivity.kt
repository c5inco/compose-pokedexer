package des.c5inco.pokedexer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import des.c5inco.pokedexer.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations, and go edge-to-edge
        // This also sets up the initial system bar style based on the platform theme
        enableEdgeToEdge()

        setContent {
            // Update the system bars to be translucent
            val useDarkIcons = isSystemInDarkTheme()
            DisposableEffect(useDarkIcons) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        android.graphics.Color.TRANSPARENT,
                        android.graphics.Color.TRANSPARENT,
                    ) { useDarkIcons },
                )
                onDispose {}
            }

            AppTheme {
                PokedexerApp()
            }
        }
    }
}

@Preview(device = Devices.PIXEL_4)
@Composable
fun DefaultPreview() {
    AppTheme {
        PokedexerApp()
    }
}