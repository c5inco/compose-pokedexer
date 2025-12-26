import SwiftUI
import shared

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
                            PokedexCardView(
                                pokemon: pokemon,
                                isFavorite: viewModel.isFavorite(Int(pokemon.id)),
                                onFavoriteTap: {
                                    viewModel.toggleFavorite(Int(pokemon.id))
                                }
                            )
                            .onTapGesture {
                                coordinator.push(.pokemonDetail(id: Int(pokemon.id)))
                            }
                            .transition(.asymmetric(
                                insertion: .offset(y: 50).combined(with: .opacity),
                                removal: .opacity
                            ))
                            .animation(
                                .easeOut(duration: 0.5).delay(Double(index) * 0.06),
                                value: viewModel.filteredPokemon.count
                            )
                        }
                    }
                    .padding()
                }

                // Filter FAB
                Button(action: {
                    viewModel.showFilters.toggle()
                }) {
                    Image(systemName: "line.3.horizontal.decrease.circle.fill")
                        .font(.system(size: 56))
                        .foregroundColor(.white)
                        .background(
                            Circle()
                                .fill(Color.blue)
                                .frame(width: 56, height: 56)
                        )
                }
                .padding(24)
                .shadow(color: .black.opacity(0.3), radius: 8, y: 4)
            }
        }
        .navigationTitle("Pokemon")
        .navigationBarTitleDisplayMode(.large)
        .task {
            await viewModel.loadPokemon()
        }
        .sheet(isPresented: $viewModel.showFilters) {
            FilterSheet(viewModel: viewModel)
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
                                TypeLabel(typeName: type, size: .medium)
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
