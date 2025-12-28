import SwiftUI

/// Decorative Pokéball background similar to Android implementation
struct PokeballBackground: View {
    var opacity: Double = 0.05
    var tint: Color = .white

    var body: some View {
        Image("ic_pokeball")
            .resizable()
            .scaledToFit()
            .foregroundColor(tint)
            .opacity(opacity)
    }
}
