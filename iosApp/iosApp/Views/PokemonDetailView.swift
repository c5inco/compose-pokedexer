import SwiftUI
import Shared

struct PokemonDetailView: View {
    let pokemon: Shared.Pokemon
    
    var body: some View {
        ScrollView {
            VStack(spacing: 24) {
                // Pokemon Image
                AsyncImage(url: pokemonImageURL(id: Int(pokemon.id))) { image in
                    image
                        .resizable()
                        .scaledToFit()
                } placeholder: {
                    ProgressView()
                }
                .frame(height: 200)
                
                // Pokemon Info
                VStack(spacing: 16) {
                    Text("#\(String(format: "%03d", pokemon.id))")
                        .font(.caption)
                        .foregroundColor(.secondary)
                    
                    Text(pokemon.name)
                        .font(.largeTitle)
                        .fontWeight(.bold)
                    
                    Text(pokemon.category)
                        .font(.subheadline)
                        .foregroundColor(.secondary)
                    
                    // Types
                    HStack(spacing: 8) {
                        ForEach(pokemon.typeOfPokemon, id: \.self) { type in
                            TypeBadge(type: type)
                        }
                    }
                    
                    // Description
                    // Note: Kotlin 'description' property is renamed to 'description_' in Swift
                    Text(pokemon.description_)
                        .font(.body)
                        .multilineTextAlignment(.center)
                        .padding(.horizontal)
                }
                
                // Stats
                StatsSection(pokemon: pokemon)
                
                Spacer()
            }
            .padding()
        }
        .navigationTitle(pokemon.name)
        .navigationBarTitleDisplayMode(.inline)
    }
    
    private func pokemonImageURL(id: Int) -> URL? {
        URL(string: "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/\(id).png")
    }
}

struct TypeBadge: View {
    let type: String
    
    var body: some View {
        Text(type)
            .font(.subheadline)
            .fontWeight(.semibold)
            .padding(.horizontal, 16)
            .padding(.vertical, 8)
            .background(typeColor.gradient)
            .foregroundColor(.white)
            .cornerRadius(20)
    }
    
    private var typeColor: Color {
        switch type.lowercased() {
        case "grass": return .green
        case "fire": return .red
        case "water": return .blue
        case "electric": return .yellow
        case "psychic": return .pink
        case "poison": return .purple
        case "ground": return .brown
        case "flying": return .cyan
        case "bug": return Color(red: 0.5, green: 0.7, blue: 0.2)
        case "normal": return .gray
        case "fighting": return .orange
        case "rock": return Color(red: 0.6, green: 0.5, blue: 0.3)
        case "ghost": return .indigo
        case "ice": return Color(red: 0.6, green: 0.85, blue: 0.9)
        case "dragon": return Color(red: 0.4, green: 0.3, blue: 0.8)
        case "dark": return Color(red: 0.3, green: 0.25, blue: 0.2)
        case "steel": return Color(red: 0.6, green: 0.6, blue: 0.7)
        case "fairy": return Color(red: 0.9, green: 0.6, blue: 0.7)
        default: return .gray
        }
    }
}

struct StatsSection: View {
    let pokemon: Shared.Pokemon
    
    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            Text("Base Stats")
                .font(.headline)
            
            StatRow(name: "HP", value: Int(pokemon.hp), maxValue: 255, color: .red)
            StatRow(name: "Attack", value: Int(pokemon.attack), maxValue: 255, color: .orange)
            StatRow(name: "Defense", value: Int(pokemon.defense), maxValue: 255, color: .yellow)
            StatRow(name: "Sp. Atk", value: Int(pokemon.specialAttack), maxValue: 255, color: .blue)
            StatRow(name: "Sp. Def", value: Int(pokemon.specialDefense), maxValue: 255, color: .green)
            StatRow(name: "Speed", value: Int(pokemon.speed), maxValue: 255, color: .pink)
        }
        .padding()
        .background(Color(.systemGray6))
        .cornerRadius(16)
    }
}

struct StatRow: View {
    let name: String
    let value: Int
    let maxValue: Int
    let color: Color
    
    var body: some View {
        HStack {
            Text(name)
                .font(.subheadline)
                .frame(width: 70, alignment: .leading)
            
            Text("\(value)")
                .font(.subheadline)
                .fontWeight(.semibold)
                .frame(width: 40, alignment: .trailing)
            
            GeometryReader { geometry in
                ZStack(alignment: .leading) {
                    Capsule()
                        .fill(Color(.systemGray4))
                    
                    Capsule()
                        .fill(color.gradient)
                        .frame(width: geometry.size.width * CGFloat(value) / CGFloat(maxValue))
                }
            }
            .frame(height: 8)
        }
    }
}
