package des.c5inco.pokedexer.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Grey100 = Color(0xfff5f5f5)
val Indigo500 = Color(0xff6C79db)
val NeutralBlue = Color(0xff303943)

val md_theme_light_primary = Color(0xFF4855B5)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFDFE0FF)
val md_theme_light_onPrimaryContainer = Color(0xFF000C62)
val md_theme_light_secondary = Color(0xFF5B5D72)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFFE0E0F9)
val md_theme_light_onSecondaryContainer = Color(0xFF181A2C)
val md_theme_light_tertiary = Color(0xFF77536C)
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFFFD7F0)
val md_theme_light_onTertiaryContainer = Color(0xFF2D1127)
val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_background = Color(0xFFFFFBFF)
val md_theme_light_onBackground = Color(0xFF1B1B1F)
val md_theme_light_surface = Color(0xFFFFFBFF)
val md_theme_light_onSurface = Color(0xFF1B1B1F)
val md_theme_light_surfaceVariant = Color(0xFFE3E1EC)
val md_theme_light_onSurfaceVariant = Color(0xFF46464F)
val md_theme_light_outline = Color(0xFF777680)
val md_theme_light_inverseOnSurface = Color(0xFFF3F0F4)
val md_theme_light_inverseSurface = Color(0xFF303034)
val md_theme_light_inversePrimary = Color(0xFFBCC2FF)
val md_theme_light_shadow = Color(0xFF000000)
val md_theme_light_surfaceTint = Color(0xFF4855B5)
val md_theme_light_outlineVariant = Color(0xFFC7C5D0)
val md_theme_light_scrim = Color(0xFF000000)

val md_theme_dark_primary = Color(0xFFBCC2FF)
val md_theme_dark_onPrimary = Color(0xFF142285)
val md_theme_dark_primaryContainer = Color(0xFF2F3C9C)
val md_theme_dark_onPrimaryContainer = Color(0xFFDFE0FF)
val md_theme_dark_secondary = Color(0xFFC4C5DD)
val md_theme_dark_onSecondary = Color(0xFF2D2F42)
val md_theme_dark_secondaryContainer = Color(0xFF434559)
val md_theme_dark_onSecondaryContainer = Color(0xFFE0E0F9)
val md_theme_dark_tertiary = Color(0xFFE6BAD6)
val md_theme_dark_onTertiary = Color(0xFF45263D)
val md_theme_dark_tertiaryContainer = Color(0xFF5D3C54)
val md_theme_dark_onTertiaryContainer = Color(0xFFFFD7F0)
val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val md_theme_dark_background = Color(0xFF1B1B1F)
val md_theme_dark_onBackground = Color(0xFFE4E1E6)
val md_theme_dark_surface = Color(0xFF1B1B1F)
val md_theme_dark_onSurface = Color(0xFFE4E1E6)
val md_theme_dark_surfaceVariant = Color(0xFF46464F)
val md_theme_dark_onSurfaceVariant = Color(0xFFC7C5D0)
val md_theme_dark_outline = Color(0xFF90909A)
val md_theme_dark_inverseOnSurface = Color(0xFF1B1B1F)
val md_theme_dark_inverseSurface = Color(0xFFE4E1E6)
val md_theme_dark_inversePrimary = Color(0xFF4855B5)
val md_theme_dark_shadow = Color(0xFF000000)
val md_theme_dark_surfaceTint = Color(0xFFBCC2FF)
val md_theme_dark_outlineVariant = Color(0xFF46464F)
val md_theme_dark_scrim = Color(0xFF000000)

class SurfaceTones {
    companion object {
        val light = md_theme_light_surface
        val light1 = Color(0xffF6F3FB)
        val light2 = Color(0xffF0EEF9)
        val light3 = Color(0xffEBE9F7)
        val light4 = Color(0xffE9E7F6)
        val light5 = Color(0xffE5E4F5)

        val dark = md_theme_dark_surface
        val dark1 = Color(0xff23232A)
        val dark2 = Color(0xff282831)
        val dark3 = Color(0xff2D2D38)
        val dark4 = Color(0xff2E2F3A)
        val dark5 = Color(0xff32323E)
    }
}

class PokemonColors {
    companion object {
        val Bug = Color(0xffaabb22)
        val Dark = Color(0xff775544)
        val Dragon = Color(0xff7766EE)
        val Electric = Color(0xffF0C03E)
        val Fairy = Color(0xffee99ee)
        val Fighting = Color(0xffbb5544)
        val Fire = Color(0xffff4422)
        val Flying = Color(0xff8899ff)
        val Ghost = Color(0xff9F5BBA)
        val Grass = Color(0xff4FC1A6)
        val Ground = Color(0xff775544)
        val Ice = Color(0xff66ccff)
        val Normal = Color(0xffaaaa99)
        val Poison = Color(0xffaa5599)
        val Psychic = Color(0xffff5599)
        val Rock = Color(0xffBBAA66)
        val Water = Color(0xff429BED)
        val Steel = Color(0xffaaaabb)
    }
}

@Immutable
data class PokemonTypeColorScheme(
    val primary: Color,
    val surface: Color,
    val onSurface: Color,
    val surfaceVariant: Color
)

val LocalPokemonTypeColorScheme = staticCompositionLocalOf {
    PokemonTypeColorScheme(
        primary = Color.Magenta,
        surface = Color.Magenta,
        onSurface = Color.Magenta,
        surfaceVariant = Color.Magenta
    )
}

@Immutable
data class TypeColors(
    val primaryDark: Color,
    val primaryLight: Color,
    val surfaceDark: Color,
    val surfaceLight: Color = Color.Unspecified,
    val onSurfaceDark: Color,
    val onSurfaceLight: Color = Color.Unspecified,
    val surfaceVariantDark: Color,
    val surfaceVariantLight: Color
)

val BugTypeColors = TypeColors(
    primaryDark = Color(0xffBFD039),
    primaryLight = Color(0xff5A6400),
    surfaceDark = Color(0xff434B00),
    surfaceLight = PokemonColors.Bug,
    onSurfaceDark = Color(0xffF8FFB9),
    onSurfaceLight = Color(0xff5A6400),
    surfaceVariantDark = Color(0x661A1E00),
    surfaceVariantLight = Color(0x26002019),
)

val DarkTypeColors = TypeColors(
    primaryDark = Color(0xffFFB691),
    primaryLight = PokemonColors.Dark,
    surfaceDark = Color(0xff793100),
    onSurfaceDark = Color(0xffFFDBCB),
    surfaceVariantDark = Color(0x66341100),
    surfaceVariantLight = Color(0x26341100),
)

val DragonTypeColors = TypeColors(
    primaryDark = Color(0xffC7BFFF),
    primaryLight = PokemonColors.Dragon,
    surfaceDark = Color(0xff422AB7),
    onSurfaceDark = Color(0xffE4DFFF),
    surfaceVariantDark = Color(0x66180065),
    surfaceVariantLight = Color(0x26180065),
)

val ElectricTypeColors = TypeColors(
    primaryDark = Color(0xffF0C03E),
    primaryLight = PokemonColors.Electric,
    surfaceDark = Color(0xFFB48B00),
    onSurfaceDark = Color(0xffFFF8F1),
    onSurfaceLight = Color(0xff4C3900),
    surfaceVariantDark = Color(0xA8251A00),
    surfaceVariantLight = Color(0x26251A00),
)


val FairyTypeColors = TypeColors(
    primaryDark = Color(0xffC473C5),
    primaryLight = Color(0xffE18EE2),
    surfaceDark = Color(0xff702875),
    onSurfaceDark = Color(0xffFFD6FA),
    onSurfaceLight = Color(0xff631B69),
    surfaceVariantDark = Color(0x6636003C),
    surfaceVariantLight = Color(0x4036003C),
)

val FightingTypeColors = TypeColors(
    primaryDark = Color(0xffFFB4A7),
    primaryLight = PokemonColors.Fighting,
    surfaceDark = Color(0xff80291C),
    onSurfaceDark = Color(0xffFFDAD4),
    surfaceVariantDark = Color(0x662B1B1A),
    surfaceVariantLight = Color(0x402B1B1A),
)

val FireTypeColors = TypeColors(
    primaryDark = Color(0xffE3300E),
    primaryLight = PokemonColors.Fire,
    surfaceDark = Color(0xff8E1400),
    onSurfaceDark = Color(0xffFFDAD3),
    surfaceVariantDark = Color(0x663E0400),
    surfaceVariantLight = Color(0x263E0400),
)

val FlyingTypeColors = TypeColors(
    primaryDark = Color(0xffBAC3FF),
    primaryLight = PokemonColors.Flying,
    surfaceDark = Color(0xff2A3C9E),
    onSurfaceDark = Color(0xffDEE0FF),
    surfaceVariantDark = Color(0x6600105C),
    surfaceVariantLight = Color(0x2600105C),
)

val GhostTypeColors = TypeColors(
    primaryDark = Color(0xffECB2FF),
    primaryLight = PokemonColors.Ghost,
    surfaceDark = Color(0xff6A2885),
    onSurfaceDark = Color(0xffF8D8FF),
    surfaceVariantDark = Color(0x66320046),
    surfaceVariantLight = Color(0x26320046),
)

val GrassTypeColors = TypeColors(
    primaryDark = Color(0xff00876F),
    primaryLight = PokemonColors.Grass,
    surfaceDark = Color(0xff005141),
    onSurfaceDark = Color(0xffE6FFF5),
    surfaceVariantDark = Color(0x66002019),
    surfaceVariantLight = Color(0x26002019),
)

val GroundTypeColors = TypeColors(
    primaryDark = Color(0xffEAC248),
    primaryLight = PokemonColors.Ground,
    surfaceDark = Color(0xff574500),
    onSurfaceDark = Color(0xffFFEFCC),
    surfaceVariantDark = Color(0x66241A00),
    surfaceVariantLight = Color(0x40241A00),
)

val IceTypeColors = TypeColors(
    primaryDark = Color(0xff79D1FF),
    primaryLight = Color(0xff4AB6E8),
    surfaceDark = Color(0xff004C68),
    onSurfaceDark = Color(0xffC3E8FF),
    onSurfaceLight = Color(0xff004C68),
    surfaceVariantDark = Color(0x66001E2C),
    surfaceVariantLight = Color(0x40001E2C),
)

val NormalTypeColors = TypeColors(
    primaryDark = Color(0xffC7C9A7),
    primaryLight = Color(0xff2F321A),
    surfaceDark = Color(0xff47483B),
    surfaceLight = PokemonColors.Normal,
    onSurfaceDark = Color(0xffC8C7B7),
    onSurfaceLight = Color(0xFF39400A),
    surfaceVariantDark = Color(0x66181B03),
    surfaceVariantLight = Color(0x40191C03),
)

val PoisonTypeColors = TypeColors(
    primaryDark = Color(0xffFFACE9),
    primaryLight = PokemonColors.Poison,
    surfaceDark = Color(0xff752769),
    onSurfaceDark = Color(0xffFFD7F1),
    surfaceVariantDark = Color(0x66390033),
    surfaceVariantLight = Color(0x26390033),
)

val PsychicTypeColors = TypeColors(
    primaryDark = Color(0xffF95095),
    primaryLight = PokemonColors.Psychic,
    surfaceDark = Color(0xff8E0049),
    onSurfaceDark = Color(0xffFFD9E2),
    surfaceVariantDark = Color(0x663E001D),
    surfaceVariantLight = Color(0x403E001D),
)

val RockTypeColors = TypeColors(
    primaryDark = Color(0xffE1C64B),
    primaryLight = PokemonColors.Rock,
    surfaceDark = Color(0xff534600),
    onSurfaceDark = Color(0xffFFF0BF),
    onSurfaceLight = Color(0xff463B00),
    surfaceVariantDark = Color(0x66221B00),
    surfaceVariantLight = Color(0x26221B00),
)

val SteelTypeColors = TypeColors(
    primaryDark = Color(0xffBCC3FF),
    primaryLight = PokemonColors.Steel,
    surfaceDark = Color(0xff3B4279),
    onSurfaceDark = Color(0xffDFE0FF),
    onSurfaceLight = Color(0xff463B00),
    surfaceVariantDark = Color(0xff47464A),
    surfaceVariantLight = Color(0xffE4E1E6),
)

val WaterTypeColors = TypeColors(
    primaryDark = Color(0xff037BCB),
    primaryLight = PokemonColors.Water,
    surfaceDark = Color(0xff00497C),
    onSurfaceDark = Color(0xffE9F1FF),
    surfaceVariantDark = Color(0x66001D36),
    surfaceVariantLight = Color(0x33001D36),
)

@Immutable
data class MoveCategoryColorScheme(
    val primary: Color,
    val surface: Color,
    val onSurface: Color,
)

val LocalMoveCategoryColorScheme = staticCompositionLocalOf {
    MoveCategoryColorScheme(
        primary = Color.Magenta,
        surface = Color.Magenta,
        onSurface = Color.Magenta,
    )
}

@Immutable
data class MoveCategoryColors(
    val primaryDark: Color,
    val primaryLight: Color,
    val surfaceDark: Color,
    val surfaceLight: Color,
    val onSurfaceDark: Color = Color.Unspecified,
    val onSurfaceLight: Color = Color.Unspecified,
)

val PhysicalColors = MoveCategoryColors(
    primaryDark = Color(0xffE3300E),
    primaryLight = PokemonColors.Fire,
    surfaceDark = Color(0xff561F14),
    surfaceLight = Color(0xffFFDAD3),
    onSurfaceDark = Color(0xffFFDAD3),
    onSurfaceLight = Color(0xff3A0A03)
)

val SpecialColors = MoveCategoryColors(
    primaryDark = Color(0xffC7BFFF),
    primaryLight = PokemonColors.Dragon,
    surfaceDark = Color(0xff2F295F),
    surfaceLight = Color(0xffE4DFFF),
    onSurfaceDark = Color(0xffE4DFFF),
    onSurfaceLight = Color(0xff1A1249)
)

val StatusColors = MoveCategoryColors(
    primaryDark = Color(0xffFFB691),
    primaryLight = PokemonColors.Dark,
    surfaceDark = Color(0xff542102),
    surfaceLight = Color(0xffFFDBCB),
    onSurfaceDark = Color(0xffFFDBCB),
    onSurfaceLight = Color(0xff341100)
)