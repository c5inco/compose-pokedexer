package des.c5inco.pokedexer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import des.c5inco.pokedexer.ui.theme.Theme.Companion.PokedexerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexerTheme {
                PokedexerApp()
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