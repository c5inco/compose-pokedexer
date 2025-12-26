import SwiftUI
import shared

// MARK: - Pokemon Mesh Gradient
/// Port of MeshGradient from Android Compose to SwiftUI iOS 18+ native API
/// Reference: app/src/main/kotlin/des/c5inco/pokedexer/ui/pokedex/PokedexCard.kt lines 63-87

@available(iOS 18.0, *)
struct PokemonMeshGradient {
    /// Create a mesh gradient for Pokemon cards
    /// Ported from PokedexCard.kt gradient structure
    static func makeGradient(
        surfaceColor: Color,
        analogousColor: Color
    ) -> (points: [SIMD2<Float>], colors: [Color]) {
        // Exact point structure from Android PokedexCard.kt (lines 64-86)
        // 3 rows × 5 columns = 15 control points
        let points: [SIMD2<Float>] = [
            // Row 0 (top)
            SIMD2<Float>(0.0, 0.0),
            SIMD2<Float>(0.24099097, 0.0),
            SIMD2<Float>(0.5358101, 0.0),
            SIMD2<Float>(0.7894143, 0.0),
            SIMD2<Float>(1.0, 0.0),

            // Row 1 (middle)
            SIMD2<Float>(0.0, 0.5),
            SIMD2<Float>(0.24236615, 0.6261937),
            SIMD2<Float>(0.5254497, 0.4176749),
            SIMD2<Float>(0.802476, 0.6690188),
            SIMD2<Float>(1.0, 0.35517487),

            // Row 2 (bottom)
            SIMD2<Float>(0.0, 1.0),
            SIMD2<Float>(0.23941448, 1.0),
            SIMD2<Float>(0.5159903, 1.0),
            SIMD2<Float>(0.7876128, 1.0),
            SIMD2<Float>(1.0, 1.0)
        ]

        // Color mapping from Android (lines 64-86)
        let colors: [Color] = [
            // Row 0
            analogousColor, analogousColor, analogousColor, analogousColor, surfaceColor,
            // Row 1
            analogousColor, surfaceColor, surfaceColor, analogousColor, surfaceColor,
            // Row 2
            surfaceColor, surfaceColor, surfaceColor, surfaceColor, surfaceColor
        ]

        return (points: points, colors: colors)
    }
}

// MARK: - MeshGradient View
@available(iOS 18.0, *)
struct PokemonCardMeshGradient: View {
    let pokemon: Pokemon
    @Environment(\.colorScheme) var colorScheme

    var body: some View {
        let typeColor = PokemonColors.color(for: pokemon.primaryType)
        let analogousColors = typeColor.analogousColors()
        let hueIndex = mapTypeToCuratedAnalogousHue(pokemon.primaryType)
        let analogousColor = analogousColors[hueIndex]

        let gradient = PokemonMeshGradient.makeGradient(
            surfaceColor: typeColor,
            analogousColor: analogousColor
        )

        MeshGradient(
            width: 5,  // 5 columns
            height: 3,  // 3 rows
            points: gradient.points,
            colors: gradient.colors
        )
    }
}

// MARK: - Full Screen Mesh Gradient
@available(iOS 18.0, *)
struct PokemonDetailMeshGradient: View {
    let pokemon: Pokemon

    var body: some View {
        let typeColor = PokemonColors.color(for: pokemon.primaryType)
        let analogousColors = typeColor.analogousColors()
        let hueIndex = mapTypeToCuratedAnalogousHue(pokemon.primaryType)
        let analogousColor = analogousColors[hueIndex]

        let gradient = PokemonMeshGradient.makeGradient(
            surfaceColor: typeColor,
            analogousColor: analogousColor
        )

        MeshGradient(
            width: 5,
            height: 3,
            points: gradient.points,
            colors: gradient.colors
        )
        .ignoresSafeArea()
    }
}
