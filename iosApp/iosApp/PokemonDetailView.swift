import SwiftUI
import shared

struct PokemonDetailView: View {
    let pokemon: Pokemon
    @State private var selectedTab = 0
    
    var body: some View {
        ScrollView {
            VStack(spacing: 0) {
                // Header with Pokemon image
                headerSection
                
                // Info section
                VStack(spacing: 24) {
                    // Basic info
                    basicInfoSection
                    
                    // Tab selector
                    Picker("", selection: $selectedTab) {
                        Text("About").tag(0)
                        Text("Stats").tag(1)
                        Text("Moves").tag(2)
                    }
                    .pickerStyle(SegmentedPickerStyle())
                    .padding(.horizontal)
                    
                    // Tab content
                    switch selectedTab {
                    case 0:
                        aboutSection
                    case 1:
                        statsSection
                    case 2:
                        movesSection
                    default:
                        EmptyView()
                    }
                }
                .padding()
                .background(Color(.systemBackground))
                .clipShape(RoundedRectangle(cornerRadius: 32, style: .continuous))
                .offset(y: -32)
            }
        }
        .navigationBarTitleDisplayMode(.inline)
        .background(pokemonTypeColor.ignoresSafeArea(edges: .top))
    }
    
    private var headerSection: some View {
        ZStack {
            pokemonTypeColor
            
            VStack {
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
                            .font(.system(size: 80))
                            .foregroundColor(.white.opacity(0.5))
                    @unknown default:
                        EmptyView()
                    }
                }
                .frame(width: 200, height: 200)
            }
            .padding(.top, 20)
            .padding(.bottom, 60)
        }
    }
    
    private var basicInfoSection: some View {
        VStack(spacing: 8) {
            Text("#\(String(format: "%03d", pokemon.id))")
                .font(.subheadline)
                .foregroundColor(.secondary)
            
            Text(pokemon.name)
                .font(.largeTitle)
                .fontWeight(.bold)
            
            HStack(spacing: 8) {
                ForEach(pokemon.typeOfPokemon, id: \.self) { type in
                    TypeBadge(type: type)
                }
            }
            
            Text(pokemon.category)
                .font(.subheadline)
                .foregroundColor(.secondary)
        }
    }
    
    private var aboutSection: some View {
        VStack(alignment: .leading, spacing: 16) {
            Text(pokemon.description_)
                .font(.body)
                .foregroundColor(.secondary)
            
            Divider()
            
            HStack {
                VStack {
                    Text(String(format: "%.1f m", pokemon.height))
                        .font(.headline)
                    Text("Height")
                        .font(.caption)
                        .foregroundColor(.secondary)
                }
                .frame(maxWidth: .infinity)
                
                Divider()
                    .frame(height: 40)
                
                VStack {
                    Text(String(format: "%.1f kg", pokemon.weight))
                        .font(.headline)
                    Text("Weight")
                        .font(.caption)
                        .foregroundColor(.secondary)
                }
                .frame(maxWidth: .infinity)
            }
        }
        .padding()
        .background(Color(.secondarySystemBackground))
        .cornerRadius(16)
    }
    
    private var statsSection: some View {
        VStack(spacing: 12) {
            StatBar(label: "HP", value: Int(pokemon.hp), maxValue: 255, color: .red)
            StatBar(label: "Attack", value: Int(pokemon.attack), maxValue: 255, color: .orange)
            StatBar(label: "Defense", value: Int(pokemon.defense), maxValue: 255, color: .yellow)
            StatBar(label: "Sp. Atk", value: Int(pokemon.specialAttack), maxValue: 255, color: .blue)
            StatBar(label: "Sp. Def", value: Int(pokemon.specialDefense), maxValue: 255, color: .green)
            StatBar(label: "Speed", value: Int(pokemon.speed), maxValue: 255, color: .pink)
        }
        .padding()
        .background(Color(.secondarySystemBackground))
        .cornerRadius(16)
    }
    
    private var movesSection: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Moves")
                .font(.headline)
            
            if pokemon.movesList.isEmpty {
                Text("No moves available")
                    .foregroundColor(.secondary)
            } else {
                LazyVGrid(columns: [GridItem(.flexible()), GridItem(.flexible())], spacing: 8) {
                    ForEach(pokemon.movesList.prefix(10), id: \.id) { move in
                        Text("Move #\(move.id)")
                            .font(.caption)
                            .padding(8)
                            .frame(maxWidth: .infinity)
                            .background(Color(.tertiarySystemBackground))
                            .cornerRadius(8)
                    }
                }
            }
        }
        .padding()
        .background(Color(.secondarySystemBackground))
        .cornerRadius(16)
    }
    
    private var pokemonImageURL: URL? {
        URL(string: "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/\(pokemon.id).png")
    }
    
    private var pokemonTypeColor: Color {
        guard let firstType = pokemon.typeOfPokemon.first else { return .gray }
        return typeColor(for: firstType)
    }
}

struct StatBar: View {
    let label: String
    let value: Int
    let maxValue: Int
    let color: Color
    
    var body: some View {
        HStack {
            Text(label)
                .font(.caption)
                .frame(width: 60, alignment: .leading)
            
            Text("\(value)")
                .font(.caption)
                .fontWeight(.semibold)
                .frame(width: 30)
            
            GeometryReader { geometry in
                ZStack(alignment: .leading) {
                    RoundedRectangle(cornerRadius: 4)
                        .fill(Color(.systemGray5))
                    
                    RoundedRectangle(cornerRadius: 4)
                        .fill(color)
                        .frame(width: geometry.size.width * CGFloat(value) / CGFloat(maxValue))
                }
            }
            .frame(height: 8)
        }
    }
}
