package des.c5inco.pokedexer.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import des.c5inco.pokedexer.R

private val defaultTypography = Typography()
private val appFontFamily = FontFamily(
    Font(R.font.circularstd_book),
    Font(R.font.circularstd_medium, FontWeight.Medium),
    Font(R.font.circularstd_black, FontWeight.Black),
    Font(R.font.circularstd_bold, FontWeight.Bold)
)

val AppTypography = Typography(
    displaySmall = TextStyle(
        fontWeight = FontWeight.Black,
        fontSize = 36.sp,
        fontFamily = appFontFamily
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Black,
        fontFamily = appFontFamily
    ),
    headlineSmall = TextStyle(
        fontWeight = FontWeight.Black,
        fontSize = 30.sp,
        fontFamily = appFontFamily
    ),
    titleLarge = defaultTypography.titleLarge.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        fontFamily = appFontFamily
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        fontFamily = appFontFamily
    ),
    bodyLarge = defaultTypography.bodyLarge.copy(
        fontFamily = appFontFamily
    ),
    bodyMedium = defaultTypography.bodyMedium.copy(
        fontFamily = appFontFamily
    ),
    bodySmall = defaultTypography.bodySmall.copy(
        fontFamily = appFontFamily
    ),
    labelLarge = defaultTypography.labelLarge.copy(
        fontFamily = appFontFamily
    ),
    labelMedium = defaultTypography.labelMedium.copy(
        fontFamily = appFontFamily
    ),
    labelSmall = defaultTypography.labelSmall.copy(
        fontWeight = FontWeight.Black,
        fontFamily = appFontFamily,
    )
)

@Preview(showBackground = true)
@Composable
fun AppTypographyPreview() {
    AppTheme {
        Column {
            Text(
                "displaySmall",
                style = MaterialTheme.typography.displaySmall
            )
            Text("headlineMedium",
                style = MaterialTheme.typography.headlineMedium
            )
            Text("headlineSmall",
                style = MaterialTheme.typography.headlineSmall
            )
            Text("titleLarge",
                style = MaterialTheme.typography.titleLarge
            )
            Text("titleMedium",
                style = MaterialTheme.typography.titleMedium
            )
            Text("bodyLarge",
                style = MaterialTheme.typography.bodyLarge
            )
            Text("bodyMedium",
                style = MaterialTheme.typography.bodyMedium
            )
            Text("bodySmall",
                style = MaterialTheme.typography.bodySmall
            )
            Text("labelLarge",
                style = MaterialTheme.typography.labelLarge
            )
            Text("labelSmall",
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}