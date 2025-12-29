import Shared
import SwiftUI

// MARK: - Pokemon Mesh Gradient
/// Port of MeshGradient.kt from Android app to SwiftUI iOS 18+ native API

@available(iOS 18.0, *)
struct PokemonCardMeshGradient: View {
    let type: String?
    @Environment(\.pokemonTheme) var theme

    static let points: [SIMD2<Float>] = [
        (0.0, 0.0), (0.24099097, 0.0), (0.5358101, 0.0), (0.7894143, 0.0), (1.0, 0.0),
        (0.0, 0.5), (0.24236615, 0.6261937), (0.5254497, 0.4176749), (0.802476, 0.6690188), (1.0, 0.35517487),
        (0.0, 1.0), (0.23941448, 1.0), (0.5159903, 1.0), (0.7876128, 1.0), (1.0, 1.0),
    ].map { .init($0.0, $0.1) }

    var body: some View {
        let surfaceColor = theme.background
        let analogousColors = surfaceColor.analogousColors(offset: 18.0)
        let hueIndex = mapTypeToCuratedAnalogousHue(type)
        let analogousColor = analogousColors[hueIndex]

        // Color mapping from PokedexCard.kt
        let colors = [
            // Row 0
            analogousColor, analogousColor, analogousColor, analogousColor, surfaceColor,
            // Row 1
            analogousColor, surfaceColor, surfaceColor, analogousColor, surfaceColor,
            // Row 2
            surfaceColor, surfaceColor, surfaceColor, surfaceColor, surfaceColor,
        ]

        MeshGradient(
            width: 5,
            height: 3,
            points: PokemonCardMeshGradient.points,
            colors: colors
        )
    }
}

@available(iOS 18.0, *)
struct PokemonDetailMeshGradient: View {
    let type: String?
    @Environment(\.pokemonTheme) var theme
    
    static let points: [SIMD2<Float>] = [
        (0.0, 0.0), (0.33, 0.0), (0.66, 0.0), (1.0, 0.0),
        (0.0, 0.6), (0.25, 0.4), (0.8, 0.6), (1.0, 0.5),
        (0.0, 1.0), (0.33, 1.0), (0.67, 1.0), (1.0, 1.0)
    ].map { .init($0.0, $0.1) }

    var body: some View {
        let surfaceColor = theme.background
        let analogousColors = surfaceColor.analogousColors(offset: 24.0)
        let hueIndex = mapTypeToCuratedAnalogousHue(type)
        let analogousColor = analogousColors[hueIndex]
        let primaryColor = theme.primary
        
        // Color mapping from PokemonDetailScreen.kt
        let colors = [
            // Row 0
            analogousColor, analogousColor, analogousColor, analogousColor,
            // Row 1
            surfaceColor, surfaceColor, surfaceColor, surfaceColor,
            // Row 2
            primaryColor, primaryColor, primaryColor, primaryColor,
        ]

        MeshGradient(
            width: 4,
            height: 3,
            points: PokemonDetailMeshGradient.points,
            colors: colors
        )
        .ignoresSafeArea()
    }
}
