package des.c5inco.pokedexer.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import des.c5inco.pokedexer.R
import des.c5inco.pokedexer.ui.theme.Theme.Companion.PokedexerTheme

val appFontFamily = FontFamily(
        Font(R.font.circularstd_book),
        Font(R.font.circularstd_medium, FontWeight.W600),
        Font(R.font.circularstd_black, FontWeight.Bold),
        Font(R.font.circularstd_bold, FontWeight.W900)
    )

// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = appFontFamily,
    h4 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
    ),
    h6 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
    ),
)

@Preview(showBackground = true)
@Composable
fun TypePreview() {
    PokedexerTheme {
        Column {
            Text(
                "H1",
                style = MaterialTheme.typography.h1
            )
            Text("H2",
                style = MaterialTheme.typography.h2
            )
            Text("H3",
                style = MaterialTheme.typography.h3
            )
            Text("Body1",
                style = MaterialTheme.typography.body1
            )
            Text("Button",
                style = MaterialTheme.typography.button
            )
            Text("Caption",
                style = MaterialTheme.typography.caption
            )
        }
    }
}