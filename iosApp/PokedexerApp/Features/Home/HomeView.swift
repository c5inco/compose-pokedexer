import SwiftUI
import Shared

struct HomeView: View {
    @StateObject private var viewModel = HomeViewModel()
    @EnvironmentObject var coordinator: NavigationCoordinator
    @EnvironmentObject var appViewModel: AppViewModel

    var body: some View {
        ZStack {
            // Background
            PokeballBackground(opacity: 0.05)

            VStack(spacing: 24) {
                // Title
                VStack(alignment: .leading, spacing: 8) {
                    Text("What Pokémon")
                        .font(.system(size: 36, weight: .bold))
                    Text("are you looking for?")
                        .font(.system(size: 36, weight: .bold))
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.horizontal)
                .padding(.top, 32)

                // Syncing Indicator
                if appViewModel.isSyncingData {
                    HStack(spacing: 8) {
                        ProgressView()
                            .scaleEffect(0.8)
                        Text(appViewModel.syncProgress)
                            .font(.subheadline)
                            .foregroundColor(.secondary)
                    }
                    .padding(.horizontal)
                    .frame(maxWidth: .infinity, alignment: .leading)
                }

                // Search Bar
                SearchBar(text: $viewModel.searchText, isLoading: viewModel.isLoading)
                    .padding(.horizontal)

                // Search Results or Menu Grid
                if viewModel.searchText.isEmpty {
                    MenuGridView(onMenuItemTap: { screen in
                        coordinator.push(screen)
                    })
                    .padding(.horizontal)
                } else {
                    SearchResultsView(results: viewModel.searchResults, onPokemonTap: { pokemon in
                        coordinator.push(.pokemonDetail(id: Int(pokemon.id)))
                    })
                }

                Spacer()
            }
        }
        .navigationBarHidden(true)
    }
}


// MARK: - Search Bar
struct SearchBar: View {
    @Binding var text: String
    var isLoading: Bool

    var body: some View {
        HStack {
            Image(systemName: "magnifyingglass")
                .foregroundColor(.secondary)

            TextField("Search Pokemon, Move, Item, etc", text: $text)
                .textFieldStyle(.plain)

            if isLoading {
                ProgressView()
                    .scaleEffect(0.8)
            } else if !text.isEmpty {
                Button(action: { text = "" }) {
                    Image(systemName: "xmark.circle.fill")
                        .foregroundColor(.secondary)
                }
            }
        }
        .padding(12)
        .background(Color(.systemGray6))
        .cornerRadius(12)
    }
}

// MARK: - Menu Grid
struct MenuGridView: View {
    var onMenuItemTap: (Screen) -> Void

    let menuItems: [MenuItem] = [
        MenuItem(title: "Pokédex", icon: "circle.grid.3x3.fill", color: Color(hex: 0x5AC178), screen: .pokedex),
        MenuItem(title: "Moves", icon: "figure.strengthtraining.traditional", color: Color(hex: 0xE74347), screen: .moves),
        MenuItem(title: "Type charts", icon: "chart.bar.xaxis", color: Color(hex: 0x429BED), screen: .typeChart),
        MenuItem(title: "Items", icon: "bag.fill", color: Color(hex: 0xFBAE46), screen: .items)
    ]

    var body: some View {
        LazyVGrid(columns: [GridItem(.flexible()), GridItem(.flexible())], spacing: 16) {
            ForEach(menuItems) { item in
                MenuCard(item: item)
                    .onTapGesture {
                        onMenuItemTap(item.screen)
                    }
            }
        }
    }
}

struct MenuItem: Identifiable {
    let id = UUID()
    let title: String
    let icon: String
    let color: Color
    let screen: Screen
}

struct MenuCard: View {
    let item: MenuItem

    var body: some View {
        VStack {
            Spacer()

            Image(systemName: item.icon)
                .font(.system(size: 64))
                .foregroundColor(item.color.opacity(0.6))

            Spacer()

            Text(item.title)
                .font(.system(size: 18, weight: .semibold))
                .foregroundColor(.white)
                .padding(.bottom, 16)
        }
        .frame(height: 160)
        .frame(maxWidth: .infinity)
        .background(item.color)
        .cornerRadius(16)
        .shadow(color: .black.opacity(0.1), radius: 4, y: 2)
    }
}

// MARK: - Search Results
struct SearchResultsView: View {
    let results: HomeViewModel.SearchResponse
    var onPokemonTap: (Pokemon) -> Void

    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 16) {
                if !results.foundPokemon.isEmpty {
                    SearchSection(title: "Pokémon") {
                        ForEach(results.foundPokemon, id: \.id) { pokemon in
                            PokemonResultCard(pokemon: pokemon)
                                .onTapGesture {
                                    onPokemonTap(pokemon)
                                }
                        }
                    }
                }

                if !results.foundMoves.isEmpty {
                    SearchSection(title: "Moves") {
                        ForEach(results.foundMoves, id: \.id) { move in
                            MoveResultCard(move: move)
                        }
                    }
                }

                if !results.foundItems.isEmpty {
                    SearchSection(title: "Items") {
                        ForEach(results.foundItems, id: \.id) { item in
                            ItemResultCard(item: item)
                        }
                    }
                }
            }
            .padding(.horizontal)
        }
    }
}

struct SearchSection<Content: View>: View {
    let title: String
    @ViewBuilder let content: () -> Content

    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text(title)
                .font(.headline)
                .foregroundColor(.secondary)

            content()
        }
    }
}

struct PokemonResultCard: View {
    let pokemon: Pokemon

    var body: some View {
        HStack(spacing: 12) {
            PokemonImage(pokemon: pokemon, size: 48)

            VStack(alignment: .leading, spacing: 4) {
                Text(pokemon.name.capitalized)
                    .font(.headline)

                HStack(spacing: 4) {
                    ForEach(pokemon.typeOfPokemon.prefix(2), id: \.self) { type in
                        TypeLabel(typeName: type, size: .small)
                    }
                }
            }

            Spacer()

            Text(pokemon.formattedId)
                .font(.caption)
                .foregroundColor(.secondary)
        }
        .padding(12)
        .background(Color(.systemGray6))
        .cornerRadius(12)
    }
}

struct MoveResultCard: View {
    let move: Move

    var body: some View {
        HStack {
            VStack(alignment: .leading, spacing: 4) {
                Text(move.name.capitalized)
                    .font(.headline)

                TypeLabel(typeName: move.type, size: .small)
            }

            Spacer()

            VStack(alignment: .trailing, spacing: 2) {
                Text("Pwr: \(move.powerDisplay)")
                    .font(.caption)
                    .foregroundColor(.secondary)
                Text("Acc: \(move.accuracyDisplay)")
                    .font(.caption)
                    .foregroundColor(.secondary)
            }
        }
        .padding(12)
        .background(Color(.systemGray6))
        .cornerRadius(12)
    }
}

struct ItemResultCard: View {
    let item: Item

    var body: some View {
        HStack(spacing: 12) {
            ItemImage(item: item, size: 40)

            VStack(alignment: .leading, spacing: 2) {
                Text(item.name.capitalized)
                    .font(.headline)

                Text(item.desc)
                    .font(.caption)
                    .foregroundColor(.secondary)
                    .lineLimit(2)
            }

            Spacer()
        }
        .padding(12)
        .background(Color(.systemGray6))
        .cornerRadius(12)
    }
}
