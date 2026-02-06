import Shared
import SwiftUI

struct HomeView: View {
    @StateObject private var viewModel = HomeViewModel()
    @EnvironmentObject var coordinator: NavigationCoordinator
    @EnvironmentObject var appViewModel: AppViewModel

    var body: some View {
        ZStack {
            VStack(alignment: .leading, spacing: 32) {
                // Title
                Text("What Pokémon are you looking for?")
                    .font(AppTypography.largeTitle)
                    .padding(.top, 64)

                // Syncing Indicator
                if appViewModel.isSyncingData {
                    HStack(spacing: 8) {
                        ProgressView()
                            .scaleEffect(0.8)
                        Text(appViewModel.syncProgress)
                            .font(AppTypography.footnote)
                            .foregroundColor(.secondary)
                    }
                    .frame(maxWidth: .infinity, alignment: .leading)
                }

                // Search Bar
                SearchBar(
                    text: $viewModel.searchText,
                    isLoading: viewModel.isLoading
                )

                // Search Results or Menu Grid
                if viewModel.searchText.isEmpty {
                    MenuGridView(onMenuItemTap: { screen in
                        coordinator.push(screen)
                    })
                } else {
                    SearchResultsView(
                        results: viewModel.searchResults,
                        onPokemonTap: { pokemon in
                            coordinator.push(
                                .pokemonDetail(id: Int(pokemon.id))
                            )
                        }
                    )
                }

                Spacer()
            }
            .padding(.horizontal)
            .padding(.top, 32)
        }
        .background(alignment: .topTrailing) {
            PokeballBackground(opacity: 0.05, tint: .black)
                .frame(width: 240, height: 240)
                .offset(x: 90, y: -72)
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
                .font(AppTypography.body)

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
        MenuItem(
            title: "Pokédex",
            icon: "ic_pokeball",
            color: Color(hex: 0x5AC178),
            type: PokemonType.grass,
            screen: .pokedex
        ),
        MenuItem(
            title: "Moves",
            icon: "dumbbell.fill",
            color: Color(hex: 0xE74347),
            type: PokemonType.fighting,
            screen: .moves
        ),
        MenuItem(
            title: "Type charts",
            icon: "chart.bar.xaxis",
            color: Color(hex: 0x429BED),
            type: PokemonType.water,
            screen: .typeChart
        ),
        MenuItem(
            title: "Items",
            icon: "storefront.fill",
            color: Color(hex: 0xFBAE46),
            type: PokemonType.fire,
            screen: .items
        ),
    ]

    var body: some View {
        LazyVGrid(
            columns: [
                GridItem(.flexible(), spacing: 16), GridItem(.flexible()),
            ],
            spacing: 16
        ) {
            ForEach(menuItems) { item in
                MenuCard(item: item)
                    .onTapGesture {
                        onMenuItemTap(item.screen)
                    }
                    .pokemonTheme(type: item.type)
            }
        }
    }
}

struct MenuItem: Identifiable {
    let id = UUID()
    let title: String
    let icon: String
    let color: Color
    let type: PokemonType
    let screen: Screen
}

struct MenuCard: View {
    let item: MenuItem
    @Environment(\.pokemonTheme) var theme

    var body: some View {
        VStack(alignment: .leading) {
            Spacer()
            Text(item.title)
                .font(AppTypography.headline)
                .foregroundColor(.white)
        }
        .padding(16)
        .frame(height: 128)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(alignment: .topTrailing) {
            if item.screen == .pokedex {
                PokeballBackground(opacity: 0.3)
                    .frame(width: 64, height: 64)
                    .offset(x: -8, y: 8)
            } else {
                Image(systemName: item.icon)
                    .font(.system(size: 48))
                    .foregroundColor(Color.white.opacity(0.3))
                    .offset(x: -10, y: 16)
            }
        }
        .background(theme.background)
        .cornerRadius(16)
        .shadow(color: theme.background, radius: 8, x: 0, y: 4)
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
                .font(AppTypography.headline)
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
                    .font(AppTypography.body.weight(.bold))

                HStack(spacing: 4) {
                    ForEach(pokemon.typeOfPokemon.prefix(2), id: \.self) {
                        type in
                        TypeLabel(typeName: type, size: .small)
                    }
                }
            }

            Spacer()

            Text(pokemon.formattedId)
                .font(AppTypography.footnote)
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
                    .font(AppTypography.body.weight(.bold))

                TypeLabel(typeName: move.type, size: .small)
            }

            Spacer()

            VStack(alignment: .trailing, spacing: 2) {
                Text("Pwr: \(move.powerDisplay)")
                    .font(AppTypography.footnote)
                    .foregroundColor(.secondary)
                Text("Acc: \(move.accuracyDisplay)")
                    .font(AppTypography.footnote)
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
                    .font(AppTypography.body.weight(.bold))

                Text(item.desc)
                    .font(AppTypography.footnote)
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
