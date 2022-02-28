package des.c5inco.pokedexer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.skydoves.landscapist.coil.LocalCoilImageLoader
import dagger.hilt.android.AndroidEntryPoint
import des.c5inco.pokedexer.ui.home.RootViewModel
import des.c5inco.pokedexer.ui.theme.Theme.Companion.PokedexerTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: RootViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Turn off the decor fitting system windows, which means we need to through handling
        // insets
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            // Update the system bars to be translucent
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = MaterialTheme.colors.isLight
            SideEffect {
                systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = useDarkIcons)
            }

            CompositionLocalProvider(LocalCoilImageLoader provides viewModel.imageLoader) {
                PokedexerTheme {
                    ProvideWindowInsets {
                        PokedexerApp()
                    }
                }
            }
        }
    }
}

@Preview(device = Devices.PIXEL_4)
@Composable
fun DefaultPreview() {
    PokedexerTheme {
        PokedexerApp()
    }
}