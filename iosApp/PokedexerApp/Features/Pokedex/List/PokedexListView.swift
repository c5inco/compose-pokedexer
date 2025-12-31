import SwiftUI
import Shared

struct PokedexListView: View {
    @StateObject private var viewModel = PokedexListViewModel()
    @EnvironmentObject var coordinator: NavigationCoordinator

    var body: some View {
        ZStack(alignment: .bottomTrailing) {
            if viewModel.isLoading && viewModel.pokemon.isEmpty {
                LoadingView()
            } else {
                ScrollView {
                    LazyVGrid(columns: [GridItem(.flexible()), GridItem(.flexible())], spacing: 8) {
                        ForEach(Array(viewModel.filteredPokemon.enumerated()), id: \.element.id) { index, pokemon in
                            PokedexCard(
                                pokemon: pokemon,
                                isFavorite: viewModel.isFavorite(Int(pokemon.id))
                            )
                            .onTapGesture {
                                coordinator.push(.pokemonDetail(id: Int(pokemon.id)))
                            }
                        }
                    }
                    .padding()
                }
            }
        }
        .navigationTitle("Pokemon")
        .navigationBarTitleDisplayMode(.large)
        .toolbar {
            ToolbarItem(placement: .navigationBarTrailing) {
                Button(action: {
                    viewModel.showFilters.toggle()
                }) {
                    Label("Filter", systemImage: "line.3.horizontal.decrease")
                }
            }
        }
        .task {
            await viewModel.loadPokemon()
        }
        .sheet(isPresented: $viewModel.showFilters) {
            FilterSheet(viewModel: viewModel)
                .presentationDetents([.fraction(0.65)])
        }
    }
}


