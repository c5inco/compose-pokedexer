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
                                AboutSection(pokemon: pokemon)
                            case .stats:
                                StatsSection(pokemon: pokemon)
                            case .evolution:
                                EvolutionSection(pokemon: pokemon)
                            case .moves:
                                MovesSection(pokemon: pokemon)
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

    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            Text(pokemon.description)
                .font(.body)

            VStack(alignment: .leading, spacing: 8) {
                InfoRow(label: "Height", value: pokemon.heightInMeters)
                InfoRow(label: "Weight", value: pokemon.weightInKilograms)
                InfoRow(label: "Gender Ratio", value: pokemon.genderRatio)
            }
        }
        .padding()
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
    let pokemon: Pokemon

    var body: some View {
        let evolutions = Array(pokemon.evolutionChain)
        if evolutions.isEmpty {
            EmptyStateView(message: "No evolutions")
                .frame(height: 200)
        } else {
            VStack(spacing: 16) {
                ForEach(evolutions.indices, id: \.self) { index in
                    let evolution = evolutions[index]
                    HStack {
                        Text("→ Evolution #\(evolution.id)")
                            .font(.headline)
                        Spacer()
                        Text(evolution.triggerDisplayName)
                            .font(.caption)
                            .foregroundColor(.secondary)
                    }
                }
            }
            .padding()
        }
    }
}

struct MovesSection: View {
    let pokemon: Pokemon

    var body: some View {
        let moves = Array(pokemon.movesList.prefix(20))
        VStack(alignment: .leading, spacing: 8) {
            ForEach(moves.indices, id: \.self) { index in
                let move = moves[index]
                HStack {
                    Text(move.name.capitalized)
                        .font(.body)
                    Spacer()
                    Text("Lv. \(move.level)")
                        .font(.caption)
                        .foregroundColor(.secondary)
                }
                .padding(.vertical, 4)
            }
        }
        .padding()
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
