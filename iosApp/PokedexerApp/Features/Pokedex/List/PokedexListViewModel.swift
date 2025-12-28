import Foundation
import Shared

@MainActor
class PokedexListViewModel: ObservableObject {
    @Published var pokemon: [Pokemon] = []
    @Published var isLoading = false
    @Published var selectedGeneration: Int = 1
    @Published var selectedType: String?
    @Published var showFavorites = false
    @Published var showFilters = false
    @Published var favoriteIds: Set<Int> = []

    private let sdk = PokedexerSDKWrapper.shared
    private var loadTask: Task<Void, Never>?

    var filteredPokemon: [Pokemon] {
        var filtered = pokemon

        // Filter by type if selected
        if let type = selectedType {
            filtered = filtered.filter { $0.typeOfPokemon.contains(type) }
        }

        // Filter by favorites if enabled
        if showFavorites {
            filtered = filtered.filter { favoriteIds.contains(Int($0.id)) }
        }

        return filtered
    }

    func loadPokemon() async {
        print("🔵 PokedexListViewModel: Loading Pokemon for generation \(selectedGeneration)")
        isLoading = true

        loadTask?.cancel()
        loadTask = Task {
            do {
                print("🔵 PokedexListViewModel: Calling getPokemonByGeneration(\(selectedGeneration))")
                guard let flow = sdk.getPokemonByGeneration(generationId: Int32(selectedGeneration)) else {
                    print("🔴 SDK not initialized yet")
                    self.isLoading = false
                    return
                }

                // SKIE's SkieSwiftFlow conforms to AsyncSequence, iterate directly
                for await pokemonList in flow {
                    print("🔵 PokedexListViewModel: Received \(pokemonList.count) pokemon for gen \(selectedGeneration)")
                    self.pokemon = pokemonList
                    self.isLoading = false
                    break
                }
                print("🔵 PokedexListViewModel: Finished loading generation \(selectedGeneration)")
            } catch {
                print("🔴 Error loading Pokemon: \(error)")
                self.isLoading = false
            }
        }
    }

    func isFavorite(_ pokemonId: Int) -> Bool {
        favoriteIds.contains(pokemonId)
    }

    func toggleFavorite(_ pokemonId: Int) {
        if favoriteIds.contains(pokemonId) {
            favoriteIds.remove(pokemonId)
        } else {
            favoriteIds.insert(pokemonId)
        }
    }

    func toggleFavoritesFilter() {
        showFavorites.toggle()
    }

    func selectGeneration(_ generation: Int?) {
        guard let gen = generation, gen != selectedGeneration else { return }
        selectedGeneration = gen
        Task {
            await loadPokemon()
        }
    }

    func selectType(_ type: String?) {
        selectedType = type
    }

    deinit {
        loadTask?.cancel()
    }
}
