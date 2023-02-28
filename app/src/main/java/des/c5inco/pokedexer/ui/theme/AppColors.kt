package des.c5inco.pokedexer.ui.theme

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
        val Electric = Color(0xffFFCE4B)
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
    }
}

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
    onSurfaceDark = Color(0xffBFD039),
    onSurfaceLight = Color(0xff5A6400),
    surfaceVariantDark = Color(0x66002019),
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
    primaryDark = Color(0xffB48B00),
    primaryLight = PokemonColors.Electric,
    surfaceDark = Color(0xff765A00),
    onSurfaceDark = Color(0xffFFF8F1),
    surfaceVariantDark = Color(0xA8251A00),
    surfaceVariantLight = Color(0x26251A00),
)

val FightingTypeColors = TypeColors(
    primaryDark = Color(0xffFFB4A7),
    primaryLight = PokemonColors.Fighting,
    surfaceDark = Color(0xff80291C),
    onSurfaceDark = Color(0xffFFDAD4),
    surfaceVariantDark = Color(0x66400200),
    surfaceVariantLight = Color(0x26400200),
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
    onSurfaceDark = Color(0xffFFE089),
    surfaceVariantDark = Color(0x66241A00),
    surfaceVariantLight = Color(0x26241A00),
)

val IceTypeColors = TypeColors(
    primaryDark = Color(0xff79D1FF),
    primaryLight = PokemonColors.Ice,
    surfaceDark = Color(0xff004C68),
    onSurfaceDark = Color(0xffC3E8FF),
    onSurfaceLight = Color(0xff003549),
    surfaceVariantDark = Color(0x66001E2C),
    surfaceVariantLight = Color(0x26001E2C),
)

val NormalTypeColors = TypeColors(
    primaryDark = Color(0xffC7C9A7),
    primaryLight = Color(0xff2F321A),
    surfaceDark = Color(0xff47483B),
    surfaceLight = PokemonColors.Normal,
    onSurfaceDark = Color(0xffC8C7B7),
    onSurfaceLight = Color(0xff5E6145),
    surfaceVariantDark = Color(0x661A1E00),
    surfaceVariantLight = Color(0x261A1E00),
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
    surfaceVariantLight = Color(0x263E001D),
)

val RockTypeColors = TypeColors(
    primaryDark = Color(0xffE1C64B),
    primaryLight = PokemonColors.Rock,
    surfaceDark = Color(0xff534600),
    onSurfaceDark = Color(0xffFFE264),
    onSurfaceLight = Color(0xff3A3000),
    surfaceVariantDark = Color(0x66221B00),
    surfaceVariantLight = Color(0x26221B00),
)

val WaterTypeColors = TypeColors(
    primaryDark = Color(0xff037BCB),
    primaryLight = PokemonColors.Water,
    surfaceDark = Color(0xff00497C),
    onSurfaceDark = Color(0xffD1E4FF),
    surfaceVariantDark = Color(0x66001D36),
    surfaceVariantLight = Color(0x26001D36),
)