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
                .presentationDetents([.medium])
        }
    }
}

// MARK: - Filter Sheet
struct FilterSheet: View {
    @ObservedObject var viewModel: PokedexListViewModel
    @Environment(\.dismiss) var dismiss

    let allTypes = ["Bug", "Dark", "Dragon", "Electric", "Fairy", "Fighting", "Fire", "Flying", "Ghost", "Grass", "Ground", "Ice", "Normal", "Poison", "Psychic", "Rock", "Steel", "Water"]
    let generations = Array(1...9)

    var body: some View {
        NavigationView {
            List {
                Section("Options") {
                    Toggle("Show Favorites Only", isOn: $viewModel.showFavorites)
                }

                Section("Filter by Type") {
                    ForEach(allTypes, id: \.self) { type in
                        Button(action: {
                            viewModel.selectType(viewModel.selectedType == type ? nil : type)
                        }) {
                            HStack {
                                TypeLabel(typeName: type, size: .medium, colored: true)
                                Spacer()
                                if viewModel.selectedType == type {
                                    Image(systemName: "checkmark")
                                        .foregroundColor(.blue)
                                }
                            }
                        }
                    }
                }

                Section("Filter by Generation") {
                    ForEach(generations, id: \.self) { gen in
                        Button(action: {
                            viewModel.selectGeneration(gen)
                        }) {
                            HStack {
                                Text("Generation \(gen)")
                                Spacer()
                                if viewModel.selectedGeneration == gen {
                                    Image(systemName: "checkmark")
                                        .foregroundColor(.blue)
                                }
                            }
                        }
                    }
                }
            }
            .navigationTitle("Filters")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button("Done") {
                        dismiss()
                    }
                }
            }
        }
    }
}
