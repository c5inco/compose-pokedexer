package des.c5inco.pokedexer.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val defaultTypography = Typography()

val AppTypography = Typography(
    displaySmall = TextStyle(
        fontWeight = FontWeight.Black,
        fontSize = 36.sp,
        fontFamily = appFontFamily
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Black,
        fontSize = 30.sp,
        fontFamily = appFontFamily
    ),
    titleLarge = TextStyle(
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
)