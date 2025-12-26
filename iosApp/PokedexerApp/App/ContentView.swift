import SwiftUI

struct ContentView: View {
    @EnvironmentObject var coordinator: NavigationCoordinator

    var body: some View {
        NavigationStack(path: $coordinator.path) {
            HomeView()
                .navigationDestination(for: Screen.self) { screen in
                    switch screen {
                    case .home:
                        HomeView()
                    case .pokedex:
                        PokedexListView()
                    case .pokemonDetail(let id):
                        PokemonDetailView(pokemonId: id)
                    case .moves:
                        MovesListView()
                    case .items:
                        ItemsListView()
                    case .typeChart:
                        TypeChartView()
                    }
                }
        }
    }
}
