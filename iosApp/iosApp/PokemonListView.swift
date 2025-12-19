import SwiftUI
import shared

struct PokemonListView: View {
    let pokemon: [Pokemon]
    
    private let columns = [
        GridItem(.flexible()),
        GridItem(.flexible())
    ]
    
    var body: some View {
        ScrollView {
            LazyVGrid(columns: columns, spacing: 16) {
                ForEach(pokemon, id: \.id) { pokemon in
                    NavigationLink(destination: PokemonDetailView(pokemon: pokemon)) {
                        PokemonCard(pokemon: pokemon)
                    }
                    .buttonStyle(PlainButtonStyle())
                }
            }
            .padding()
        }
    }
}

struct PokemonCard: View {
    let pokemon: Pokemon
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            // Pokemon Image placeholder
            ZStack {
                RoundedRectangle(cornerRadius: 12)
                    .fill(pokemonTypeColor.opacity(0.2))
                
                AsyncImage(url: pokemonImageURL) { phase in
                    switch phase {
                    case .empty:
                        ProgressView()
                    case .success(let image):
                        image
                            .resizable()
                            .aspectRatio(contentMode: .fit)
                    case .failure:
                        Image(systemName: "photo")
                            .font(.largeTitle)
                            .foregroundColor(.gray)
                    @unknown default:
                        EmptyView()
                    }
                }
                .frame(width: 80, height: 80)
            }
            .frame(height: 100)
            
            VStack(alignment: .leading, spacing: 4) {
                Text("#\(String(format: "%03d", pokemon.id))")
                    .font(.caption)
                    .foregroundColor(.secondary)
                
                Text(pokemon.name)
                    .font(.headline)
                    .foregroundColor(.primary)
                
                HStack(spacing: 4) {
                    ForEach(pokemon.typeOfPokemon, id: \.self) { type in
                        TypeBadge(type: type)
                    }
                }
            }
            .padding(.horizontal, 8)
            .padding(.bottom, 8)
        }
        .background(
            RoundedRectangle(cornerRadius: 16)
                .fill(Color(.systemBackground))
                .shadow(color: .black.opacity(0.1), radius: 4, x: 0, y: 2)
        )
    }
    
    private var pokemonImageURL: URL? {
        URL(string: "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/\(pokemon.id).png")
    }
    
    private var pokemonTypeColor: Color {
        guard let firstType = pokemon.typeOfPokemon.first else { return .gray }
        return typeColor(for: firstType)
    }
}

struct TypeBadge: View {
    let type: String
    
    var body: some View {
        Text(type)
            .font(.caption2)
            .fontWeight(.semibold)
            .foregroundColor(.white)
            .padding(.horizontal, 8)
            .padding(.vertical, 4)
            .background(
                Capsule()
                    .fill(typeColor(for: type))
            )
    }
}

func typeColor(for type: String) -> Color {
    switch type.lowercased() {
    case "normal": return Color(red: 0.66, green: 0.65, blue: 0.48)
    case "fire": return Color(red: 0.93, green: 0.51, blue: 0.19)
    case "water": return Color(red: 0.39, green: 0.56, blue: 0.94)
    case "electric": return Color(red: 0.97, green: 0.82, blue: 0.17)
    case "grass": return Color(red: 0.48, green: 0.78, blue: 0.30)
    case "ice": return Color(red: 0.59, green: 0.85, blue: 0.84)
    case "fighting": return Color(red: 0.76, green: 0.18, blue: 0.16)
    case "poison": return Color(red: 0.64, green: 0.24, blue: 0.63)
    case "ground": return Color(red: 0.89, green: 0.75, blue: 0.40)
    case "flying": return Color(red: 0.66, green: 0.56, blue: 0.95)
    case "psychic": return Color(red: 0.98, green: 0.33, blue: 0.53)
    case "bug": return Color(red: 0.65, green: 0.73, blue: 0.10)
    case "rock": return Color(red: 0.71, green: 0.63, blue: 0.21)
    case "ghost": return Color(red: 0.45, green: 0.34, blue: 0.59)
    case "dragon": return Color(red: 0.44, green: 0.21, blue: 0.99)
    case "dark": return Color(red: 0.44, green: 0.34, blue: 0.27)
    case "steel": return Color(red: 0.72, green: 0.72, blue: 0.81)
    case "fairy": return Color(red: 0.84, green: 0.52, blue: 0.68)
    default: return .gray
    }
}
