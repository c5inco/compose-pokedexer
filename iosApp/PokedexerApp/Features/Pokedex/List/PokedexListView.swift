import SwiftUI
import Shared
import Kingfisher

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
                            .onAppear {
                                prefetchImagesIfNeeded(for: index)
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

    private func prefetchImagesIfNeeded(for index: Int) {
        // Prefetch images for the next 10 items
        let prefetchCount = 10
        let startIndex = index + 1
        let endIndex = min(startIndex + prefetchCount, viewModel.filteredPokemon.count)

        guard startIndex < viewModel.filteredPokemon.count else { return }

        let urls = (startIndex..<endIndex).compactMap { idx in
            viewModel.filteredPokemon[idx].imageURL
        }

        if !urls.isEmpty {
            ImagePrefetcher(urls: urls).start()
        }
    }
}


