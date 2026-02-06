import Shared
import SwiftUI

@available(iOS 18.0, *)
struct PokedexCardView: View {
    let pokemon: Pokemon
    let isFavorite: Bool

    @Environment(\.colorScheme) private var colorScheme
    @Environment(\.pokemonTheme) private var theme

    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text(pokemon.name.capitalized)
                .font(.system(size: 14, weight: .bold))
            VStack(alignment: .leading, spacing: 4) {
                ForEach(pokemon.typeOfPokemon.prefix(2), id: \.self) {
                    type in
                    TypeLabel(typeName: type, size: .small)
                }
            }
            Spacer()
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(.top, 24)
        .padding(.leading, 12)
        .frame(height: 124)
        .overlay(alignment: .bottomTrailing) {
            PokemonImage(pokemon: pokemon, size: 80)
                .padding([.bottom, .trailing], 6)
        }
        .overlay(alignment: .bottomTrailing) {
            if isFavorite {
                Image(systemName: "heart.fill")
                    .padding(8)
            }
        }
        .background(alignment: .topTrailing) {
            Text(pokemon.formattedId)
                .font(.system(size: 14, weight: .bold))
                .opacity(colorScheme == .dark ? 0.5 : 0.7)
                .padding(.top, 8)
                .padding(.trailing, 12)
        }
        .background(alignment: .bottomTrailing) {
            PokeballBackground(opacity: 0.25, tint: .white)
                .frame(height: 88)
        }
        .background(PokemonCardMeshGradient(type: pokemon.primaryType))
        .foregroundStyle(theme.onBackground)
        .clipShape(RoundedRectangle(cornerRadius: 16))
    }
}

struct PokedexCard: View {
    let pokemon: Pokemon
    let isFavorite: Bool

    var body: some View {
        PokedexCardView(
            pokemon: pokemon,
            isFavorite: isFavorite
        )
        .pokemonTheme(pokemon)
    }
}
