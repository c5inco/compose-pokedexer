import SwiftUI
import Shared

struct PokemonListView: View {
    @StateObject private var viewModel = PokemonListViewModel()
    @State private var showGenerationPicker = false
    
    var body: some View {
        ZStack {
            // Background Pokeball decoration
            VStack {
                HStack {
                    Spacer()
                    PokeballView(tint: Color.primary.opacity(0.05))
                        .frame(width: 200, height: 200)
                        .offset(x: 80, y: -60)
                }
                Spacer()
            }
            
            Group {
                if viewModel.isLoading && viewModel.pokemon.isEmpty {
                    VStack(spacing: 16) {
                        ProgressView()
                            .scaleEffect(1.5)
                        Text("Loading Pokémon...")
                            .font(.subheadline)
                            .foregroundColor(.secondary)
                    }
                    .accessibilityElement(children: .combine)
                    .accessibilityLabel("Loading Pokémon, please wait")
                } else if viewModel.isOffline && viewModel.pokemon.isEmpty {
                    // Offline state with no cached data
                    VStack(spacing: 16) {
                        Image(systemName: "wifi.slash")
                            .font(.system(size: 48))
                            .foregroundColor(.secondary)
                            .accessibilityHidden(true)
                        
                        Text("No Internet Connection")
                            .font(.headline)
                        
                        Text("Please check your connection and try again.")
                            .font(.subheadline)
                            .foregroundColor(.secondary)
                            .multilineTextAlignment(.center)
                        
                        Button("Retry") {
                            Task {
                                await viewModel.loadPokemon()
                            }
                        }
                        .buttonStyle(.borderedProminent)
                        .accessibilityHint("Attempts to reload the Pokémon list")
                    }
                    .padding()
                    .accessibilityElement(children: .contain)
                } else {
                    VStack {
                        if let error = viewModel.error {
                            HStack {
                                Image(systemName: "exclamationmark.triangle.fill")
                                    .foregroundColor(.orange)
                                    .accessibilityHidden(true)
                                Text(error)
                                    .font(.caption)
                            }
                            .padding(.horizontal)
                            .padding(.vertical, 8)
                            .background(Color.orange.opacity(0.1))
                            .cornerRadius(8)
                            .padding()
                            .accessibilityElement(children: .combine)
                            .accessibilityLabel("Warning: \(error)")
                        }
                        
                        ScrollView {
                            LazyVGrid(columns: [
                                GridItem(.flexible(), spacing: 16),
                                GridItem(.flexible(), spacing: 16)
                            ], spacing: 16) {
                                ForEach(viewModel.pokemon, id: \.id) { pokemon in
                                    NavigationLink(destination: PokemonDetailView(pokemon: pokemon)) {
                                        PokemonCard(pokemon: pokemon)
                                    }
                                    .accessibilityLabel("\(pokemon.name), number \(pokemon.id)")
                                    .accessibilityHint("Double tap to view details")
                                }
                            }
                            .padding(.horizontal)
                            .padding(.top, 8)
                        }
                    }
                }
            }
        }
        .navigationTitle("Pokemon")
        .toolbar {
            ToolbarItem(placement: .navigationBarTrailing) {
                Button(action: { showGenerationPicker = true }) {
                    Image(systemName: "line.3.horizontal.decrease.circle")
                }
                .accessibilityLabel("Filter by generation")
                .accessibilityHint("Opens generation picker")
            }
        }
        .sheet(isPresented: $showGenerationPicker) {
            GenerationPickerView(
                generations: viewModel.getGenerations(),
                selectedId: $viewModel.selectedGeneration,
                onSelect: { genId in
                    Task {
                        await viewModel.selectGeneration(genId)
                    }
                    showGenerationPicker = false
                }
            )
        }
        .task {
            await viewModel.loadPokemon()
        }
    }
}

struct GenerationPickerView: View {
    let generations: [Shared.GenerationInfo]
    @Binding var selectedId: Int32
    let onSelect: (Int32) -> Void
    
    var body: some View {
        NavigationStack {
            List(generations, id: \.id) { gen in
                Button(action: { onSelect(gen.id) }) {
                    HStack {
                        Text(gen.name)
                        Spacer()
                        if gen.id == selectedId {
                            Image(systemName: "checkmark")
                                .foregroundColor(.accentColor)
                                .accessibilityHidden(true)
                        }
                    }
                }
                .accessibilityLabel(gen.name)
                .accessibilityValue(gen.id == selectedId ? "Selected" : "")
                .accessibilityHint("Double tap to select this generation")
            }
            .navigationTitle("Select Generation")
            .navigationBarTitleDisplayMode(.inline)
        }
    }
}

// MARK: - Pokemon Card
struct PokemonCard: View {
    let pokemon: Shared.Pokemon
    
    private var primaryTypeColor: Color {
        typeColor(for: pokemon.typeOfPokemon.first ?? "Normal")
    }
    
    private var secondaryTypeColor: Color {
        if pokemon.typeOfPokemon.count > 1 {
            return typeColor(for: pokemon.typeOfPokemon[1])
        }
        return primaryTypeColor.opacity(0.8)
    }
    
    var body: some View {
        ZStack {
            // Gradient background
            RoundedRectangle(cornerRadius: 20)
                .fill(
                    LinearGradient(
                        colors: [primaryTypeColor, secondaryTypeColor],
                        startPoint: .topLeading,
                        endPoint: .bottomTrailing
                    )
                )
            
            // Content
            VStack(alignment: .leading, spacing: 4) {
                // Top row: Name and ID
                HStack(alignment: .top) {
                    Text(pokemon.name)
                        .font(.headline)
                        .fontWeight(.bold)
                        .foregroundColor(.white)
                        .lineLimit(1)
                    
                    Spacer()
                    
                    Text(formatId(Int(pokemon.id)))
                        .font(.subheadline)
                        .fontWeight(.semibold)
                        .foregroundColor(.white.opacity(0.6))
                }
                
                // Type badges
                VStack(alignment: .leading, spacing: 6) {
                    ForEach(pokemon.typeOfPokemon, id: \.self) { type in
                        TypeBadge(type: type)
                    }
                }
                
                Spacer()
            }
            .padding(12)
            
            // Pokemon image with Pokeball background
            VStack {
                Spacer()
                HStack {
                    Spacer()
                    ZStack(alignment: .bottomTrailing) {
                        // Pokeball decoration
                        PokeballView(tint: .white.opacity(0.2))
                            .frame(width: 100, height: 100)
                            .offset(x: 15, y: 15)
                        
                        // Pokemon image
                        AsyncImage(url: pokemonImageURL(id: Int(pokemon.id))) { phase in
                            switch phase {
                            case .empty:
                                ProgressView()
                                    .tint(.white)
                            case .success(let image):
                                image
                                    .resizable()
                                    .scaledToFit()
                            case .failure:
                                Image(systemName: "questionmark")
                                    .font(.largeTitle)
                                    .foregroundColor(.white.opacity(0.5))
                            @unknown default:
                                EmptyView()
                            }
                        }
                        .frame(width: 90, height: 90)
                    }
                }
            }
        }
        .frame(height: 140)
        .accessibilityElement(children: .ignore)
        .accessibilityLabel(pokemonAccessibilityLabel)
    }
    
    private var pokemonAccessibilityLabel: String {
        let types = pokemon.typeOfPokemon.joined(separator: " and ")
        return "\(pokemon.name), number \(pokemon.id), \(types) type"
    }
    
    private func formatId(_ id: Int) -> String {
        String(format: "#%03d", id)
    }
    
    private func pokemonImageURL(id: Int) -> URL? {
        URL(string: "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/\(id).png")
    }
    
    private func typeColor(for type: String) -> Color {
        switch type.lowercased() {
        case "grass": return PokemonColors.grass
        case "fire": return PokemonColors.fire
        case "water": return PokemonColors.water
        case "electric": return PokemonColors.electric
        case "psychic": return PokemonColors.psychic
        case "poison": return PokemonColors.poison
        case "ground": return PokemonColors.ground
        case "flying": return PokemonColors.flying
        case "bug": return PokemonColors.bug
        case "normal": return PokemonColors.normal
        case "fighting": return PokemonColors.fighting
        case "rock": return PokemonColors.rock
        case "ghost": return PokemonColors.ghost
        case "ice": return PokemonColors.ice
        case "dragon": return PokemonColors.dragon
        case "dark": return PokemonColors.dark
        case "steel": return PokemonColors.steel
        case "fairy": return PokemonColors.fairy
        default: return PokemonColors.normal
        }
    }
}

// MARK: - Type Badge
struct TypeBadge: View {
    let type: String
    
    var body: some View {
        Text(type)
            .font(.caption2)
            .fontWeight(.medium)
            .foregroundColor(.white)
            .padding(.horizontal, 12)
            .padding(.vertical, 4)
            .background(Color.white.opacity(0.25))
            .cornerRadius(20)
    }
}

#Preview {
    NavigationStack {
        PokemonListView()
    }
}
