import SwiftUI
import Shared

struct PokemonListView: View {
    @StateObject private var viewModel = PokemonListViewModel()
    @State private var showGenerationPicker = false
    
    var body: some View {
        Group {
            if viewModel.isLoading && viewModel.pokemon.isEmpty {
                ProgressView("Loading Pokémon...")
            } else {
                VStack {
                    if let error = viewModel.error {
                        Text(error)
                            .foregroundColor(.red)
                            .padding()
                    }
                    
                    ScrollView {
                        LazyVGrid(columns: [
                            GridItem(.flexible()),
                            GridItem(.flexible())
                        ], spacing: 16) {
                            ForEach(viewModel.pokemon, id: \.id) { pokemon in
                                NavigationLink(destination: PokemonDetailView(pokemon: pokemon)) {
                                    PokemonCard(pokemon: pokemon)
                                }
                            }
                        }
                        .padding()
                    }
                }
            }
        }
        .navigationTitle("Pokédex")
        .toolbar {
            ToolbarItem(placement: .navigationBarTrailing) {
                Button(action: { showGenerationPicker = true }) {
                    Image(systemName: "line.3.horizontal.decrease.circle")
                }
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
                        }
                    }
                }
            }
            .navigationTitle("Select Generation")
            .navigationBarTitleDisplayMode(.inline)
        }
    }
}

struct PokemonCard: View {
    let pokemon: Shared.Pokemon
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            // Pokemon Image (placeholder)
            AsyncImage(url: pokemonImageURL(id: Int(pokemon.id))) { image in
                image
                    .resizable()
                    .scaledToFit()
            } placeholder: {
                ProgressView()
            }
            .frame(height: 100)
            .frame(maxWidth: .infinity)
            
            Text("#\(String(format: "%03d", pokemon.id))")
                .font(.caption)
                .foregroundColor(.secondary)
            
            Text(pokemon.name)
                .font(.headline)
                .lineLimit(1)
            
            HStack(spacing: 4) {
                ForEach(pokemon.typeOfPokemon, id: \.self) { type in
                    Text(type)
                        .font(.caption2)
                        .padding(.horizontal, 8)
                        .padding(.vertical, 4)
                        .background(typeColor(for: type).opacity(0.2))
                        .foregroundColor(typeColor(for: type))
                        .cornerRadius(8)
                }
            }
        }
        .padding()
        .background(Color(.systemBackground))
        .cornerRadius(16)
        .shadow(color: .black.opacity(0.1), radius: 8, y: 4)
    }
    
    private func pokemonImageURL(id: Int) -> URL? {
        URL(string: "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/\(id).png")
    }
    
    private func typeColor(for type: String) -> Color {
        switch type.lowercased() {
        case "grass": return .green
        case "fire": return .red
        case "water": return .blue
        case "electric": return .yellow
        case "psychic": return .pink
        case "poison": return .purple
        case "ground": return .brown
        case "flying": return .cyan
        case "bug": return .green.opacity(0.7)
        case "normal": return .gray
        case "fighting": return .orange
        case "rock": return .brown.opacity(0.7)
        case "ghost": return .indigo
        case "ice": return .cyan.opacity(0.7)
        case "dragon": return .purple.opacity(0.8)
        case "dark": return .black.opacity(0.7)
        case "steel": return .gray.opacity(0.7)
        case "fairy": return .pink.opacity(0.7)
        default: return .gray
        }
    }
}

#Preview {
    NavigationStack {
        PokemonListView()
    }
}
