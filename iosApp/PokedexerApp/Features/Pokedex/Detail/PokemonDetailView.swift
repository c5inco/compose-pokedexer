import Shared
import SwiftUI

struct PokemonDetailView: View {
    let pokemonId: Int
    @StateObject private var viewModel: PokemonDetailViewModel
    @Environment(\.dismiss) var dismiss
    @State private var pokeballRotation: Double = 0

    init(pokemonId: Int) {
        self.pokemonId = pokemonId
        _viewModel = StateObject(
            wrappedValue: PokemonDetailViewModel(pokemonId: pokemonId)
        )
    }

    var body: some View {
        ZStack {
            if let pokemon = viewModel.pokemon {
                ZStack(alignment: .topLeading) {
                    PokemonDetailMeshGradient(type: pokemon.primaryType)

                    RoundedRectangleDecoration()
                        .frame(
                            width: 150,
                            height: 150,
                        )
                        .ignoresSafeArea()
                        .rotationEffect(.degrees(-20), anchor: .center)
                        .offset(x: -60, y: -75)

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
                                .font(AppTypography.title3(weight: .bold))
                                .foregroundColor(.white)

                            Spacer()

                            Button(action: viewModel.toggleFavorite) {
                                Image(
                                    systemName: viewModel.isFavorite
                                        ? "heart.fill" : "heart"
                                )
                                .font(.title2)
                                .foregroundColor(.white)
                            }
                        }
                        .padding()

                        // Pokemon Image (with horizontal pager in full version)
                        PokemonImage(pokemon: pokemon, size: 220)
                            .offset(x: 0, y: 24)
                            .zIndex(2)

                        CardContent(
                            pokemon: pokemon,
                            abilities: viewModel.abilities,
                            evolutions: viewModel.evolutions,
                            moves: viewModel.moves
                        )
                        .zIndex(1)
                    }
                    .background(
                        PokeballBackground(opacity: 0.15, tint: .white)
                            .frame(width: 240, height: 240)
                            .rotationEffect(.degrees(pokeballRotation))
                            .offset(y: -150)
                            .onAppear {
                                withAnimation(.linear(duration: 4.0).repeatForever(autoreverses: false)) {
                                    pokeballRotation = 360
                                }
                            }
                    )
                }
                .pokemonTheme(pokemon)
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

struct RoundedRectangleDecoration: View {
    @Environment(\.pokemonTheme) var theme

    var body: some View {
        RoundedRectangle(cornerRadius: 32)
            .fill(Color.white.opacity(0.13))
    }
}

struct CardContent: View {
    let pokemon: Pokemon
    let abilities: [PokemonDetailsAbility]
    let evolutions: [PokemonDetailsEvolution]
    let moves: [PokemonDetailsMove]

    @State private var selectedTab: DetailTab = .stats
    @State private var dragOffset: CGFloat = 0
    @Environment(\.dismiss) var dismiss
    @Environment(\.pokemonTheme) private var theme

    enum DetailTab: String, CaseIterable, Identifiable {
        case about, stats, evolution, moves

        var id: String { rawValue }
        var title: String { rawValue.capitalized }
    }

    var body: some View {
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
            .onAppear {
                let appearance = UISegmentedControl.appearance()
                
                // Background of the picker track
                appearance.backgroundColor = UIColor(theme.surfaceContainer)
                
                appearance.selectedSegmentTintColor = UIColor(theme.surfaceContainerLow)
                
                // Text attributes for unselected state
                appearance.setTitleTextAttributes([
                    .foregroundColor: UIColor(theme.tertiary),
                    .font: UIFont(name: "CircularStd-Book", size: 13)!
                ], for: .normal)
                
                // Text attributes for selected state
                appearance.setTitleTextAttributes([
                    .foregroundColor: UIColor(theme.primary),
                    .font: UIFont(name: "CircularStd-Bold", size: 13)!
                ], for: .selected)
            }

            // Tab Content
            ScrollView {
                switch selectedTab {
                case .about:
                    AboutSection(
                        pokemon: pokemon,
                        abilities: abilities
                    )
                case .stats:
                    StatsSection(pokemon: pokemon)
                case .evolution:
                    EvolutionSection(
                        evolutions: evolutions
                    )
                case .moves:
                    MovesSection(moves: moves)
                }
            }
            .safeAreaPadding(.bottom)
        }
        .background(theme.surface)
        .clipShape(RoundedRectangle(cornerRadius: 20))
        .ignoresSafeArea()
    }
}

// MARK: - Tab Sections
struct AboutSection: View {
    let pokemon: Pokemon
    let abilities: [PokemonDetailsAbility]
    @Environment(\.pokemonTheme) var theme

    var body: some View {
        VStack(alignment: .leading, spacing: 28) {
            // Description
            Text(pokemon.desc)
                .font(AppTypography.body)
                .lineSpacing(4)

            // Height/Weight Card
            HStack(spacing: 0) {
                VStack(alignment: .leading, spacing: 12) {
                    Text("Height")
                        .font(AppTypography.callout)
                        .foregroundColor(theme.secondary)
                    Text(pokemon.heightInMeters)
                        .font(AppTypography.body)
                }
                .frame(maxWidth: .infinity, alignment: .leading)

                VStack(alignment: .leading, spacing: 12) {
                    Text("Weight")
                        .font(AppTypography.callout)
                        .foregroundColor(theme.secondary)
                    Text(pokemon.weightInKilograms)
                        .font(AppTypography.body)
                }
                .frame(maxWidth: .infinity, alignment: .leading)
            }
            .padding()
            .background(theme.surfaceContainerHigh)
            .clipShape(RoundedRectangle(cornerRadius: 12))

            // Abilities Section
            if !abilities.isEmpty {
                VStack(alignment: .leading, spacing: 16) {
                    Text("Abilities")
                        .font(AppTypography.headline)

                    VStack(spacing: 12) {
                        ForEach(abilities) { ability in
                            VStack(alignment: .leading, spacing: 8) {
                                if ability.isHidden {
                                    Text("Hidden")
                                        .font(AppTypography.caption1)
                                        .textCase(.uppercase)
                                        .foregroundColor(theme.primary)
                                }
                                Text(ability.ability.name.capitalized)
                                    .font(AppTypography.body)
                                Text(ability.ability.desc)
                                    .font(AppTypography.footnote)
                                    .foregroundColor(.secondary)
                            }
                            .frame(maxWidth: .infinity, alignment: .leading)
                            .padding()
                            .background(theme.surfaceContainerHigh)
                            .clipShape(RoundedRectangle(cornerRadius: 12))
                        }
                    }
                }
            }

            // Breeding Section
            VStack(alignment: .leading, spacing: 16) {
                Text("Breeding")
                    .font(AppTypography.headline)

                Grid(
                    alignment: .leading,
                    horizontalSpacing: 24,
                    verticalSpacing: 18
                ) {
                    GridRow {
                        Text("Gender")
                            .font(AppTypography.subheadline)
                            .foregroundColor(.secondary)

                        if pokemon.genderRate != -1 {
                            HStack(spacing: 12) {
                                HStack(spacing: 2) {
                                    Image("ic_male_gender")
                                        .foregroundStyle(
                                            Color(hex: 0xFF6C_79DB)
                                        )
                                    Text(
                                        "\(100.0 - Double(pokemon.genderRate) * 12.5, specifier: "%.1f")%"
                                    )
                                    .font(AppTypography.subheadline)
                                }
                                HStack(spacing: 2) {
                                    Image("ic_female_gender")
                                        .foregroundStyle(
                                            Color(hex: 0xFFF0_729F)
                                        )
                                    Text(
                                        "\(Double(pokemon.genderRate) * 12.5, specifier: "%.1f")%"
                                    )
                                    .font(AppTypography.subheadline)
                                }
                            }
                        } else {
                            Text("Genderless")
                                .font(AppTypography.subheadline)
                        }
                    }

                    GridRow {
                        Text("Egg Groups")
                            .font(AppTypography.subheadline)
                            .foregroundColor(.secondary)
                        Text("-")
                            .font(AppTypography.subheadline)
                    }

                    GridRow {
                        Text("Egg Cycles")
                            .font(AppTypography.subheadline)
                            .foregroundColor(.secondary)
                        Text("-")
                            .font(AppTypography.subheadline)
                    }
                }
            }
        }
        .padding(.horizontal)
    }
}

struct StatsSection: View {
    let pokemon: Pokemon
    @State private var progressValues: [Double] = [0, 0, 0, 0, 0, 0]

    var body: some View {
        VStack(spacing: 12) {
            StatBar(
                label: "HP",
                value: Int(pokemon.hp),
                progress: progressValues[0]
            )
            StatBar(
                label: "Attack",
                value: Int(pokemon.attack),
                progress: progressValues[1]
            )
            StatBar(
                label: "Defense",
                value: Int(pokemon.defense),
                progress: progressValues[2]
            )
            StatBar(
                label: "Sp. Atk",
                value: Int(pokemon.specialAttack),
                progress: progressValues[3],
            )
            StatBar(
                label: "Sp. Def",
                value: Int(pokemon.specialDefense),
                progress: progressValues[4],
            )
            StatBar(
                label: "Speed",
                value: Int(pokemon.speed),
                progress: progressValues[5],
            )
        }
        .pokemonTheme(pokemon)
        .padding(.horizontal)
        .onAppear {
            startSequencedBuild()
        }
    }

    func startSequencedBuild() {
        for index in 0...5 {
            let delay = Double(index) * 0.07

            withAnimation(.spring(duration: 0.3, bounce: 0.5).delay(delay)) {
                progressValues[index] = 1.0
            }
        }
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
                                .font(AppTypography.headline)

                            // Evolution trigger info
                            HStack(spacing: 4) {
                                if evolution.targetLevel > 0 {
                                    Text("Level \(evolution.targetLevel)")
                                        .font(AppTypography.footnote)
                                        .foregroundColor(.secondary)
                                }
                                if let item = evolution.item {
                                    Text("(\(item.name.capitalized))")
                                        .font(AppTypography.footnote)
                                        .foregroundColor(.secondary)
                                }
                            }
                        }

                        Spacer()
                    }
                    .padding(.horizontal)
                }
            }
            .padding(.horizontal)
        }
    }
}

struct MovesSection: View {
    let moves: [PokemonDetailsMove]
    @Environment(\.pokemonTheme) private var theme

    var body: some View {
        if moves.isEmpty {
            EmptyStateView(message: "No moves found")
                .frame(height: 200)
        } else {
            LazyVStack(
                alignment: .leading,
                spacing: 0,
                pinnedViews: [.sectionHeaders]
            ) {
                Section {
                    ForEach(moves) { move in
                        VStack(spacing: 0) {
                            PokemonMoveRow(move: move)
                                .padding(.vertical, 12)
                                .padding(.horizontal)

                            if move.id != moves.last?.id {
                                Divider()
                                    .padding(.horizontal)
                            }
                        }
                    }
                } header: {
                    PokemonMoveTableHeader()
                        .padding(.horizontal)
                        .padding(.bottom, 8)
                        .background(theme.surface)
                }
            }
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
                .font(AppTypography.subheadline)
                .foregroundColor(.secondary)
            Spacer()
            Text(value)
                .font(AppTypography.subheadline(weight: .semibold))
        }
    }
}

struct StatBar: View {
    let label: String
    let value: Int
    let progress: Double
    private let max: Int = 255
    @Environment(\.pokemonTheme) var theme

    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            HStack {
                Text(label)
                    .font(AppTypography.subheadline)
                    .foregroundColor(.secondary)
                Spacer()
                Text("\(value)")
                    .font(AppTypography.subheadline(weight: .bold))
            }

            GeometryReader { geometry in
                ZStack(alignment: .leading) {
                    Rectangle()
                        .fill(theme.surfaceContainerHigh)
                        .frame(height: 8)
                        .cornerRadius(4)

                    Rectangle()
                        .fill(theme.primary)
                        .frame(
                            width: geometry.size.width * progress
                                * (CGFloat(value)
                                    / CGFloat(max)),
                            height: 8
                        )
                        .cornerRadius(4)
                }
            }
        }
    }
}

struct PokemonMoveTableHeader: View {
    var body: some View {
        HStack(spacing: 4) {
            Text("Lvl")
                .frame(width: 35, alignment: .leading)

            Text("Name")
                .frame(maxWidth: .infinity, alignment: .leading)

            Text("Type")
                .frame(width: 80)

            Text("Cat")
                .frame(width: 40)

            Text("Pwr")
                .frame(width: 40, alignment: .trailing)

            Text("Acc")
                .frame(width: 40, alignment: .trailing)
        }
        .font(AppTypography.caption1)
        .foregroundColor(.secondary)
        .padding(.vertical, 4)
        .overlay(
            Rectangle()
                .frame(height: 1)
                .foregroundColor(.secondary),
            alignment: .bottom
        )
    }
}

struct PokemonMoveRow: View {
    let move: PokemonDetailsMove

    var body: some View {
        PokemonMoveRowContent(move: move)
            .pokemonTheme(types: [move.move.type])
    }
}

private struct PokemonMoveRowContent: View {
    let move: PokemonDetailsMove
    @Environment(\.pokemonTheme) var theme

    var body: some View {
        HStack(spacing: 4) {
            Text("\(move.targetLevel)")
                .font(AppTypography.subheadline)
                .foregroundStyle(theme.secondary)
                .frame(width: 35, alignment: .leading)

            Text(move.move.name.capitalized)
                .font(AppTypography.subheadline)
                .frame(maxWidth: .infinity, alignment: .leading)

            TypeLabel(typeName: move.move.type, colored: true)
                .frame(width: 80)

            CategoryIcon(category: move.move.category)
                .frame(width: 20, height: 20)
                .frame(width: 40)

            Text(move.move.powerDisplay)
                .font(AppTypography.subheadline)
                .frame(width: 40, alignment: .trailing)

            Text(move.move.accuracyDisplay)
                .font(AppTypography.subheadline)
                .frame(width: 40, alignment: .trailing)
        }
    }
}
