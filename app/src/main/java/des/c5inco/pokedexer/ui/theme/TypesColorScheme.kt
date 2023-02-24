package des.c5inco.pokedexer.ui.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@Stable
class TypesColorScheme(
    primaryGrass: Color,
    surfaceGrass: Color,
    onSurfaceGrass: Color,
    surfaceVariantGrass: Color,
    primaryFire: Color,
    surfaceFire: Color,
    onSurfaceFire: Color,
    surfaceVariantFire: Color,
    primaryWater: Color,
    surfaceWater: Color,
    onSurfaceWater: Color,
    surfaceVariantWater: Color,
    primaryElectric: Color,
    surfaceElectric: Color,
    onSurfaceElectric: Color,
    surfaceVariantElectric: Color,
    primaryPsychic: Color,
    surfacePsychic: Color,
    onSurfacePsychic: Color,
    surfaceVariantPsychic: Color,
) {
    var primaryGrass by mutableStateOf(primaryGrass)
        internal set
    var primaryFire by mutableStateOf(primaryFire)
        internal set
    var primaryElectric by mutableStateOf(primaryElectric)
        internal set
    var primaryPsychic by mutableStateOf(primaryPsychic)
        internal set
    var primaryWater by mutableStateOf(primaryWater)
        internal set

    var surfaceGrass by mutableStateOf(surfaceGrass)
        internal set
    var surfaceFire by mutableStateOf(surfaceFire)
        internal set
    var surfaceElectric by mutableStateOf(surfaceElectric)
        internal set
    var surfacePsychic by mutableStateOf(surfacePsychic)
        internal set
    var surfaceWater by mutableStateOf(surfaceWater)
        internal set

    var onSurfaceGrass by mutableStateOf(onSurfaceGrass)
        internal set
    var onSurfaceFire by mutableStateOf(onSurfaceFire)
        internal set
    var onSurfaceElectric by mutableStateOf(onSurfaceElectric)
        internal set
    var onSurfacePsychic by mutableStateOf(onSurfacePsychic)
        internal set
    var onSurfaceWater by mutableStateOf(onSurfaceWater)
        internal set

    var surfaceVariantGrass by mutableStateOf(surfaceVariantGrass)
        internal set
    var surfaceVariantFire by mutableStateOf(surfaceVariantFire)
        internal set
    var surfaceVariantElectric by mutableStateOf(surfaceVariantElectric)
        internal set
    var surfaceVariantPsychic by mutableStateOf(surfaceVariantPsychic)
        internal set
    var surfaceVariantWater by mutableStateOf(surfaceVariantWater)
        internal set

    /** Returns a copy of this ColorScheme, optionally overriding some of the values. */
    fun copy(
        primaryGrass: Color = this.primaryGrass,
        primaryFire: Color = this.primaryFire,
        primaryElectric: Color = this.primaryElectric,
        primaryPsychic: Color = this.primaryPsychic,
        primaryWater: Color = this.primaryWater,
        surfaceGrass: Color = this.surfaceGrass,
        surfaceFire: Color = this.surfaceFire,
        surfaceElectric: Color = this.surfaceElectric,
        surfacePsychic: Color = this.surfacePsychic,
        surfaceWater: Color = this.surfaceWater,
        onSurfaceGrass: Color = this.onSurfaceGrass,
        onSurfaceFire: Color = this.onSurfaceFire,
        onSurfaceElectric: Color = this.onSurfaceElectric,
        onSurfacePsychic: Color = this.onSurfacePsychic,
        onSurfaceWater: Color = this.onSurfaceWater,
        surfaceVariantGrass: Color = this.surfaceVariantGrass,
        surfaceVariantFire: Color = this.surfaceVariantFire,
        surfaceVariantElectric: Color = this.surfaceVariantElectric,
        surfaceVariantPsychic: Color = this.surfaceVariantPsychic,
        surfaceVariantWater: Color = this.surfaceVariantWater,
    ): TypesColorScheme =
        TypesColorScheme(
            primaryGrass = primaryGrass,
            primaryFire = primaryFire,
            primaryElectric = primaryElectric,
            primaryPsychic = primaryPsychic,
            primaryWater = primaryWater,
            surfaceGrass = surfaceGrass,
            surfaceFire = surfaceFire,
            surfaceElectric = surfaceElectric,
            surfacePsychic = surfacePsychic,
            surfaceWater = surfaceWater,
            onSurfaceGrass = onSurfaceGrass,
            onSurfaceFire = onSurfaceFire,
            onSurfaceElectric = onSurfaceElectric,
            onSurfacePsychic = onSurfacePsychic,
            onSurfaceWater = onSurfaceWater,
            surfaceVariantGrass = surfaceVariantGrass,
            surfaceVariantFire = surfaceVariantFire,
            surfaceVariantElectric = surfaceVariantElectric,
            surfaceVariantPsychic = surfaceVariantPsychic,
            surfaceVariantWater = surfaceVariantWater,
        )

    override fun toString(): String {
        return "TypesColorScheme(" +
                "primaryGrass=$primaryGrass" +
                "primaryFire=$primaryFire" +
                "primaryElectric=$primaryElectric" +
                "primaryPsychic=$primaryPsychic" +
                "primaryWater=$primaryWater" +
                "surfaceGrass=$surfaceGrass" +
                "surfaceFire=$surfaceFire" +
                "surfaceElectric=$surfaceElectric" +
                "surfacePsychic=$surfacePsychic" +
                "surfaceWater=$surfaceWater" +
                "onSurfaceGrass=$onSurfaceGrass" +
                "onSurfaceFire=$onSurfaceFire" +
                "onSurfaceElectric=$onSurfaceElectric" +
                "onSurfacePsychic=$onSurfacePsychic" +
                "onSurfaceWater=$onSurfaceWater" +
                "surfaceVariantGrass=$surfaceVariantGrass" +
                "surfaceVariantFire=$surfaceVariantFire" +
                "surfaceVariantElectric=$surfaceVariantElectric" +
                "surfaceVariantPsychic=$surfaceVariantPsychic" +
                "surfaceVariantWater=$surfaceVariantWater" +
                ")"
    }
}

internal fun TypesColorScheme.updateTypesColorSchemeFrom(other: TypesColorScheme) {
    primaryGrass = other.primaryGrass
    primaryFire = other.primaryFire
    primaryElectric = other.primaryElectric
    primaryPsychic = other.primaryPsychic
    primaryWater = other.primaryWater
    surfaceGrass = other.surfaceGrass
    surfaceFire = other.surfaceFire
    surfaceElectric = other.surfaceElectric
    surfacePsychic = other.surfacePsychic
    surfaceWater = other.surfaceWater
    onSurfaceGrass = other.onSurfaceGrass
    onSurfaceFire = other.onSurfaceFire
    onSurfaceElectric = other.onSurfaceElectric
    onSurfacePsychic = other.onSurfacePsychic
    onSurfaceWater = other.onSurfaceWater
    surfaceVariantGrass = other.surfaceVariantGrass
    surfaceVariantFire = other.surfaceVariantFire
    surfaceVariantElectric = other.surfaceVariantElectric
    surfaceVariantPsychic = other.surfaceVariantPsychic
    surfaceVariantWater = other.surfaceVariantWater
}