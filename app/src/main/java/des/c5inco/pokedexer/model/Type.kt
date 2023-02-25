package des.c5inco.pokedexer.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import des.c5inco.pokedexer.ui.theme.PokemonColors
import des.c5inco.pokedexer.ui.theme.TypesTheme

enum class Type {
    Normal,
    Fire,
    Fighting,
    Water,
    Flying,
    Grass,
    Poison,
    Electric,
    Ground,
    Psychic,
    Rock,
    Ice,
    Bug,
    Dragon,
    Ghost,
    Dark,
    Steel,
    Fairy,
}

fun mapTypeToColor(type: String): Color {
    return when (Type.valueOf(type)) {
        Type.Grass -> PokemonColors.LightTeal
        Type.Bug -> PokemonColors.Bug
        Type.Fire -> PokemonColors.Fire
        Type.Flying -> PokemonColors.Flying
        Type.Water -> PokemonColors.Blue
        Type.Ice -> PokemonColors.Ice
        Type.Dragon -> PokemonColors.Dragon
        Type.Normal -> PokemonColors.Normal
        Type.Fighting -> PokemonColors.Fighting
        Type.Electric -> PokemonColors.Electric
        Type.Psychic -> PokemonColors.Psychic
        Type.Poison -> PokemonColors.Poison
        Type.Ghost -> PokemonColors.LightPurple
        Type.Ground, Type.Rock -> PokemonColors.LightBrown
        Type.Dark -> PokemonColors.Dark
        else -> return PokemonColors.LightBlue
    }
}

@Composable
fun mapTypesToPrimaryColor(
    types: List<String>
): Color {
    val type = types.elementAtOrNull(0)

    return type?.let {
        when (Type.valueOf(type)) {
            Type.Grass -> TypesTheme.colorScheme.primaryGrass
            Type.Bug -> PokemonColors.Bug
            Type.Fire -> TypesTheme.colorScheme.primaryFire
            Type.Flying -> PokemonColors.Flying
            Type.Water -> TypesTheme.colorScheme.primaryWater
            Type.Ice -> PokemonColors.Ice
            Type.Dragon -> TypesTheme.colorScheme.primaryDragon
            Type.Normal -> PokemonColors.Normal
            Type.Fighting -> PokemonColors.Fighting
            Type.Electric -> TypesTheme.colorScheme.primaryElectric
            Type.Psychic -> TypesTheme.colorScheme.primaryPsychic
            Type.Poison -> PokemonColors.Poison
            Type.Ghost -> PokemonColors.LightPurple
            Type.Ground, Type.Rock -> PokemonColors.LightBrown
            Type.Dark -> PokemonColors.Dark
            else -> PokemonColors.LightBlue
        }
    } ?: Color.Magenta
}

@Composable
fun mapTypesToSurfaceColor(
    types: List<String>
): Color {
    val type = types.elementAtOrNull(0)

    return type?.let {
        when (Type.valueOf(type)) {
            Type.Grass -> TypesTheme.colorScheme.surfaceGrass
            Type.Bug -> PokemonColors.Bug
            Type.Fire -> TypesTheme.colorScheme.surfaceFire
            Type.Flying -> PokemonColors.Flying
            Type.Water -> TypesTheme.colorScheme.surfaceWater
            Type.Ice -> PokemonColors.Ice
            Type.Dragon -> TypesTheme.colorScheme.surfaceDragon
            Type.Normal -> PokemonColors.Normal
            Type.Fighting -> PokemonColors.Fighting
            Type.Electric -> TypesTheme.colorScheme.surfaceElectric
            Type.Psychic -> TypesTheme.colorScheme.surfacePsychic
            Type.Poison -> PokemonColors.Poison
            Type.Ghost -> PokemonColors.LightPurple
            Type.Ground, Type.Rock -> PokemonColors.LightBrown
            Type.Dark -> PokemonColors.Dark
            else -> PokemonColors.LightBlue
        }
    } ?: Color.Magenta
}

@Composable
fun mapTypesToOnSurfaceColor(
    types: List<String>
): Color {
    val type = types.elementAtOrNull(0)

    return type?.let {
        when (Type.valueOf(type)) {
            Type.Grass -> TypesTheme.colorScheme.onSurfaceGrass
            Type.Bug -> PokemonColors.Bug
            Type.Fire -> TypesTheme.colorScheme.onSurfaceFire
            Type.Flying -> PokemonColors.Flying
            Type.Water -> TypesTheme.colorScheme.onSurfaceWater
            Type.Ice -> PokemonColors.Ice
            Type.Dragon -> TypesTheme.colorScheme.onSurfaceDragon
            Type.Normal -> PokemonColors.Normal
            Type.Fighting -> PokemonColors.Fighting
            Type.Electric -> TypesTheme.colorScheme.onSurfaceElectric
            Type.Psychic -> TypesTheme.colorScheme.onSurfacePsychic
            Type.Poison -> PokemonColors.Poison
            Type.Ghost -> PokemonColors.LightPurple
            Type.Ground, Type.Rock -> PokemonColors.LightBrown
            Type.Dark -> PokemonColors.Dark
            else -> PokemonColors.LightBlue
        }
    } ?: Color.Magenta
}

@Composable
fun mapTypesToSurfaceVariantColor(
    types: List<String>
): Color {
    val type = types.elementAtOrNull(0)

    return type?.let {
        when (Type.valueOf(type)) {
            Type.Grass -> TypesTheme.colorScheme.surfaceVariantGrass
            Type.Bug -> PokemonColors.Bug
            Type.Fire -> TypesTheme.colorScheme.surfaceVariantFire
            Type.Flying -> PokemonColors.Flying
            Type.Water -> TypesTheme.colorScheme.surfaceVariantWater
            Type.Ice -> PokemonColors.Ice
            Type.Dragon -> TypesTheme.colorScheme.surfaceVariantDragon
            Type.Normal -> PokemonColors.Normal
            Type.Fighting -> PokemonColors.Fighting
            Type.Electric -> TypesTheme.colorScheme.surfaceVariantElectric
            Type.Psychic -> TypesTheme.colorScheme.surfaceVariantPsychic
            Type.Poison -> PokemonColors.Poison
            Type.Ghost -> PokemonColors.LightPurple
            Type.Ground, Type.Rock -> PokemonColors.LightBrown
            Type.Dark -> PokemonColors.Dark
            else -> PokemonColors.LightBlue
        }
    } ?: Color.Magenta
}