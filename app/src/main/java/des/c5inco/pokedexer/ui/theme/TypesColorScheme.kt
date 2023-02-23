package des.c5inco.pokedexer.ui.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.structuralEqualityPolicy
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
    var primaryGrass by mutableStateOf(primaryGrass, structuralEqualityPolicy())
        internal set
    var primaryFire by mutableStateOf(primaryFire, structuralEqualityPolicy())
        internal set
    var primaryElectric by mutableStateOf(primaryElectric, structuralEqualityPolicy())
        internal set
    var primaryPsychic by mutableStateOf(primaryPsychic, structuralEqualityPolicy())
        internal set
    var primaryWater by mutableStateOf(primaryWater, structuralEqualityPolicy())
        internal set

    var surfaceGrass by mutableStateOf(surfaceGrass, structuralEqualityPolicy())
        internal set
    var surfaceFire by mutableStateOf(surfaceFire, structuralEqualityPolicy())
        internal set
    var surfaceElectric by mutableStateOf(surfaceElectric, structuralEqualityPolicy())
        internal set
    var surfacePsychic by mutableStateOf(surfacePsychic, structuralEqualityPolicy())
        internal set
    var surfaceWater by mutableStateOf(surfaceWater, structuralEqualityPolicy())
        internal set

    var onSurfaceGrass by mutableStateOf(onSurfaceGrass, structuralEqualityPolicy())
        internal set
    var onSurfaceFire by mutableStateOf(onSurfaceFire, structuralEqualityPolicy())
        internal set
    var onSurfaceElectric by mutableStateOf(onSurfaceElectric, structuralEqualityPolicy())
        internal set
    var onSurfacePsychic by mutableStateOf(onSurfacePsychic, structuralEqualityPolicy())
        internal set
    var onSurfaceWater by mutableStateOf(onSurfaceWater, structuralEqualityPolicy())
        internal set

    var surfaceVariantGrass by mutableStateOf(surfaceVariantGrass, structuralEqualityPolicy())
        internal set
    var surfaceVariantFire by mutableStateOf(surfaceVariantFire, structuralEqualityPolicy())
        internal set
    var surfaceVariantElectric by mutableStateOf(surfaceVariantElectric, structuralEqualityPolicy())
        internal set
    var surfaceVariantPsychic by mutableStateOf(surfaceVariantPsychic, structuralEqualityPolicy())
        internal set
    var surfaceVariantWater by mutableStateOf(surfaceVariantWater, structuralEqualityPolicy())
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