import SwiftUI
import Shared

@available(iOS 18.0, *)
struct PokedexCardView: View {
    let pokemon: Pokemon
    let isFavorite: Bool
    var onFavoriteTap: () -> Void = {}

    var body: some View {
        ZStack {
            // Mesh Gradient Background
            PokemonCardMeshGradient(pokemon: pokemon)
                .clipShape(RoundedRectangle(cornerRadius: 16))

            // Card Content
            VStack(alignment: .leading) {
                HStack(alignment: .top) {
                    VStack(alignment: .leading, spacing: 8) {
                        Text(pokemon.name.capitalized)
                            .font(.system(size: 14, weight: .bold))
                            .foregroundColor(.white)

                        HStack(spacing: 4) {
                            ForEach(pokemon.typeOfPokemon.prefix(2), id: \.self) { type in
                                TypeLabel(typeName: type, size: .small)
                            }
                        }
                    }

                    Spacer()

                    Text(pokemon.formattedId)
                        .font(.system(size: 14, weight: .bold))
                        .foregroundColor(.white.opacity(0.7))
                }
                .padding([.top, .leading, .trailing], 12)

                Spacer()

                ZStack(alignment: .bottomTrailing) {
                    // Pokeball background
                    PokeballBackground(opacity: 0.25, tint: .white)
                        .frame(height: 88)
                        .offset(x: 0, y: 0)

                    // Pokemon Image
                    PokemonImage(pokemon: pokemon, size: 80)
                        .padding([.bottom, .trailing], 6)

                    // Favorite icon
                    if isFavorite {
                        Image(systemName: "heart.fill")
                            .foregroundColor(.red)
                            .padding(8)
                    }
                }
            }
        }
        .frame(height: 124)
    }
}

// Fallback for iOS 17 and below with linear gradient
struct PokedexCardViewLegacy: View {
    let pokemon: Pokemon
    let isFavorite: Bool
    var onFavoriteTap: () -> Void = {}

    var body: some View {
        ZStack {
            // Linear Gradient Fallback
            let typeColor = PokemonColors.color(for: pokemon.primaryType)
            let analogousColors = typeColor.analogousColors()
            let hueIndex = mapTypeToCuratedAnalogousHue(pokemon.primaryType)

            LinearGradient(
                colors: [analogousColors[hueIndex], typeColor],
                startPoint: .topLeading,
                endPoint: .bottomTrailing
            )
            .clipShape(RoundedRectangle(cornerRadius: 16))

            // Card Content (same as above)
            VStack(alignment: .leading) {
                HStack(alignment: .top) {
                    VStack(alignment: .leading, spacing: 8) {
                        Text(pokemon.name.capitalized)
                            .font(.system(size: 14, weight: .bold))
                            .foregroundColor(.white)

                        HStack(spacing: 4) {
                            ForEach(pokemon.typeOfPokemon.prefix(2), id: \.self) { type in
                                TypeLabel(typeName: type, size: .small)
                            }
                        }
                    }

                    Spacer()

                    Text(pokemon.formattedId)
                        .font(.system(size: 14, weight: .bold))
                        .foregroundColor(.white.opacity(0.7))
                }
                .padding([.top, .leading, .trailing], 12)

                Spacer()

                ZStack(alignment: .bottomTrailing) {
                    PokeballBackground(opacity: 0.25, tint: .white)
                        .frame(height: 88)

                    PokemonImage(pokemon: pokemon, size: 80)
                        .padding([.bottom, .trailing], 6)

                    if isFavorite {
                        Image(systemName: "heart.fill")
                            .foregroundColor(.red)
                            .padding(8)
                    }
                }
            }
        }
        .frame(height: 124)
    }
}

// Wrapper to automatically use correct version
struct PokedexCard: View {
    let pokemon: Pokemon
    let isFavorite: Bool
    var onFavoriteTap: () -> Void = {}

    var body: some View {
        if #available(iOS 18.0, *) {
            PokedexCardView(pokemon: pokemon, isFavorite: isFavorite, onFavoriteTap: onFavoriteTap)
        } else {
            PokedexCardViewLegacy(pokemon: pokemon, isFavorite: isFavorite, onFavoriteTap: onFavoriteTap)
        }
    }
}
