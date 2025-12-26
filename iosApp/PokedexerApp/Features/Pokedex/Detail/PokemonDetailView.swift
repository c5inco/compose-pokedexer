import SwiftUI
import shared

struct PokemonDetailView: View {
    let pokemonId: Int
    @StateObject private var viewModel: PokemonDetailViewModel
    @State private var selectedTab: DetailTab = .stats
    @State private var dragOffset: CGFloat = 0
    @Environment(\.dismiss) var dismiss

    enum DetailTab: String, CaseIterable, Identifiable {
        case about, stats, evolution, moves

        var id: String { rawValue }
        var title: String { rawValue.capitalized }
    }

    init(pokemonId: Int) {
        self.pokemonId = pokemonId
        _viewModel = StateObject(wrappedValue: PokemonDetailViewModel(pokemonId: pokemonId))
    }

    var body: some View {
        ZStack {
            if let pokemon = viewModel.pokemon {
                // Background mesh gradient
                if #available(iOS 18.0, *) {
                    PokemonDetailMeshGradient(pokemon: pokemon)
                } else {
                    // Fallback linear gradient
                    let typeColor = PokemonColors.color(for: pokemon.primaryType)
                    LinearGradient(
                        colors: [typeColor.opacity(0.6), typeColor],
                        startPoint: .top,
                        endPoint: .bottom
                    )
                    .ignoresSafeArea()
                }

                VStack(spacing: 0) {
                    // Header
                    HStack {
                        Button(action: { dismiss() }) {
                            Image(systemName: "chevron.left")
                                .font(.title2)
                                .foregroundColor(.white)
                        }

                        Spacer()

                        Text(pokemon.name.capitalized)
                            .font(.title2.bold())
                            .foregroundColor(.white)

                        Spacer()

                        Button(action: viewModel.toggleFavorite) {
                            Image(systemName: viewModel.isFavorite ? "heart.fill" : "heart")
                                .font(.title2)
                                .foregroundColor(viewModel.isFavorite ? .red : .white)
                        }
                    }
                    .padding()

                    // Pokemon Image (with horizontal pager in full version)
                    PokemonImage(pokemon: pokemon, size: 200)
                        .padding(.vertical, 32)

                    // Draggable card with tabs
                    VStack(spacing: 0) {
                        // Tab Picker
                        Picker("Section", selection: $selectedTab) {
                            ForEach(DetailTab.allCases) { tab in
                                Text(tab.title).tag(tab)
                            }
                        }
                        .pickerStyle(.segmented)
                        .padding()

                        // Tab Content
                        ScrollView {
                            switch selectedTab {
                            case .about:
                                AboutSection(pokemon: pokemon, abilities: viewModel.abilities)
                            case .stats:
                                StatsSection(pokemon: pokemon)
                            case .evolution:
                                EvolutionSection(evolutions: viewModel.evolutions)
                            case .moves:
                                MovesSection(moves: viewModel.moves)
                            }
                        }
                    }
                    .background(Color(.systemBackground))
                    .clipShape(RoundedRectangle(cornerRadius: 32))
                    .offset(y: max(dragOffset, 0))
                    .gesture(
                        DragGesture()
                            .onChanged { value in
                                dragOffset = value.translation.height
                            }
                            .onEnded { value in
                                if value.translation.height > 100 {
                                    dismiss()
                                } else {
                                    withAnimation(.spring()) {
                                        dragOffset = 0
                                    }
                                }
                            }
                    )
                }
            } else {
                LoadingView()
            }
        }
        .navigationBarHidden(true)
        .task {
            await viewModel.loadPokemon()
        }
    }
}

// MARK: - Tab Sections
struct AboutSection: View {
    let pokemon: Pokemon
    let abilities: [PokemonDetailsAbility]

    var body: some View {
        VStack(alignment: .leading, spacing: 28) {
            // Description
            Text(pokemon.description)
                .font(.body)
                .lineSpacing(4)
            
            // Height/Weight Card
            HStack(spacing: 0) {
                VStack(alignment: .leading, spacing: 12) {
                    Text("Height")
                        .font(.caption)
                        .foregroundColor(.secondary)
                    Text(pokemon.heightInMeters)
                        .font(.body)
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                
                VStack(alignment: .leading, spacing: 12) {
                    Text("Weight")
                        .font(.caption)
                        .foregroundColor(.secondary)
                    Text(pokemon.weightInKilograms)
                        .font(.body)
                }
                .frame(maxWidth: .infinity, alignment: .leading)
            }
            .padding(16)
            .background(Color(.secondarySystemBackground))
            .clipShape(RoundedRectangle(cornerRadius: 12))
            
            // Abilities Section
            if !abilities.isEmpty {
                VStack(alignment: .leading, spacing: 16) {
                    Text("Abilities")
                        .font(.headline)
                    
                    VStack(spacing: 12) {
                        ForEach(abilities) { ability in
                            VStack(alignment: .leading, spacing: 8) {
                                if ability.isHidden {
                                    Text("HIDDEN")
                                        .font(.caption2)
                                        .fontWeight(.semibold)
                                        .foregroundColor(.blue)
                                }
                                Text(ability.ability.name.capitalized)
                                    .font(.body)
                                Text(ability.ability.description_)
                                    .font(.caption)
                                    .foregroundColor(.secondary)
                            }
                            .frame(maxWidth: .infinity, alignment: .leading)
                            .padding(16)
                            .background(Color(.secondarySystemBackground))
                            .clipShape(RoundedRectangle(cornerRadius: 12))
                        }
                    }
                }
            }
            
            // Breeding Section
            VStack(alignment: .leading, spacing: 24) {
                Text("Breeding")
                    .font(.headline)
                
                HStack {
                    Text("Gender")
                        .font(.caption)
                        .foregroundColor(.secondary)
                        .frame(width: 80, alignment: .leading)
                    
                    if pokemon.genderRate != -1 {
                        HStack(spacing: 12) {
                            HStack(spacing: 2) {
                                Image(systemName: "circle.fill")
                                    .font(.system(size: 8))
                                    .foregroundColor(.blue)
                                Text("\(100.0 - Double(pokemon.genderRate) * 12.5, specifier: "%.1f")%")
                                    .font(.body)
                            }
                            HStack(spacing: 2) {
                                Image(systemName: "circle.fill")
                                    .font(.system(size: 8))
                                    .foregroundColor(.pink)
                                Text("\(Double(pokemon.genderRate) * 12.5, specifier: "%.1f")%")
                                    .font(.body)
                            }
                        }
                    } else {
                        Text("Genderless")
                            .font(.body)
                    }
                }
                
                HStack {
                    Text("Egg Groups")
                        .font(.caption)
                        .foregroundColor(.secondary)
                        .frame(width: 80, alignment: .leading)
                    Text("-")
                        .font(.body)
                }
                
                HStack {
                    Text("Egg Cycles")
                        .font(.caption)
                        .foregroundColor(.secondary)
                        .frame(width: 80, alignment: .leading)
                    Text("-")
                        .font(.body)
                }
            }
        }
        .padding(24)
    }
}

struct StatsSection: View {
    let pokemon: Pokemon

    var body: some View {
        VStack(spacing: 12) {
            StatBar(label: "HP", value: Int(pokemon.hp), max: 255)
            StatBar(label: "Attack", value: Int(pokemon.attack), max: 255)
            StatBar(label: "Defense", value: Int(pokemon.defense), max: 255)
            StatBar(label: "Sp. Atk", value: Int(pokemon.specialAttack), max: 255)
            StatBar(label: "Sp. Def", value: Int(pokemon.specialDefense), max: 255)
            StatBar(label: "Speed", value: Int(pokemon.speed), max: 255)
        }
        .padding()
    }
}

struct EvolutionSection: View {
    let evolutions: [PokemonDetailsEvolution]

    var body: some View {
        if evolutions.isEmpty {
            EmptyStateView(message: "No evolutions")
                .frame(height: 200)
        } else {
            VStack(spacing: 24) {
                ForEach(evolutions) { evolution in
                    HStack(spacing: 16) {
                        // Pokemon image
                        PokemonImage(pokemon: evolution.pokemon, size: 80)
                        
                        VStack(alignment: .leading, spacing: 4) {
                            Text(evolution.pokemon.name.capitalized)
                                .font(.headline)
                            
                            // Evolution trigger info
                            HStack(spacing: 4) {
                                if evolution.targetLevel > 0 {
                                    Text("Level \(evolution.targetLevel)")
                                        .font(.caption)
                                        .foregroundColor(.secondary)
                                }
                                if let item = evolution.item {
                                    Text("(\(item.name.capitalized))")
                                        .font(.caption)
                                        .foregroundColor(.secondary)
                                }
                            }
                        }
                        
                        Spacer()
                    }
                    .padding(.horizontal)
                }
            }
            .padding(.vertical, 24)
        }
    }
}

struct MovesSection: View {
    let moves: [PokemonDetailsMove]

    var body: some View {
        if moves.isEmpty {
            EmptyStateView(message: "No moves found")
                .frame(height: 200)
        } else {
            VStack(alignment: .leading, spacing: 8) {
                ForEach(moves.prefix(20)) { move in
                    HStack {
                        VStack(alignment: .leading, spacing: 2) {
                            Text(move.move.name.capitalized)
                                .font(.body)
                            Text(move.move.type.capitalized)
                                .font(.caption2)
                                .foregroundColor(.secondary)
                        }
                        Spacer()
                        Text("Lv. \(move.targetLevel)")
                            .font(.caption)
                            .foregroundColor(.secondary)
                    }
                    .padding(.vertical, 8)
                    .padding(.horizontal)
                    
                    if move.id != moves.prefix(20).last?.id {
                        Divider()
                            .padding(.horizontal)
                    }
                }
            }
            .padding(.vertical, 16)
        }
    }
}

// MARK: - Helper Views
struct InfoRow: View {
    let label: String
    let value: String

    var body: some View {
        HStack {
            Text(label)
                .foregroundColor(.secondary)
            Spacer()
            Text(value)
                .fontWeight(.semibold)
        }
    }
}

struct StatBar: View {
    let label: String
    let value: Int
    let max: Int

    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            HStack {
                Text(label)
                    .font(.caption)
                    .foregroundColor(.secondary)
                Spacer()
                Text("\(value)")
                    .font(.caption.bold())
            }

            GeometryReader { geometry in
                ZStack(alignment: .leading) {
                    Rectangle()
                        .fill(Color(.systemGray5))
                        .frame(height: 8)
                        .cornerRadius(4)

                    Rectangle()
                        .fill(Color.blue)
                        .frame(width: geometry.size.width * CGFloat(value) / CGFloat(max), height: 8)
                        .cornerRadius(4)
                }
            }
            .frame(height: 8)
        }
    }
}
