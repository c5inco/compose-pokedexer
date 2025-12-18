import Foundation
import Shared
import KMPNativeCoroutinesAsync

/**
 * ViewModel for the Pokemon list screen.
 * Uses the PokedexerSDK from the shared Kotlin module.
 */
@MainActor
class PokemonListViewModel: ObservableObject {
    @Published var pokemon: [Shared.Pokemon] = []
    @Published var isLoading: Bool = true
    @Published var error: String?
    @Published var selectedGeneration: Int32 = 1
    
    private let sdk: PokedexerSDK
    private var observationTask: Task<Void, Never>? = nil
    
    init() {
        // Initialize SDK with iOS-native UserDefaults storage
        let favoritesStore = UserDefaultsFavoritesStore()
        self.sdk = PokedexerSDK(favoritesStore: favoritesStore)
    }
    
    func loadPokemon() async {
        isLoading = true
        error = nil
        
        // Cancel existing observation if switching generations
        observationTask?.cancel()
        
        // Start observing the local database for changes to this generation
        observationTask = Task {
            do {
                let pokemonFlow = sdk.getPokemonByGeneration(generationId: Int32(selectedGeneration))
                for try await pokemonList in asyncSequence(for: pokemonFlow) {
                    self.pokemon = pokemonList
                    // If we have data, we're no longer "loading" from the cache perspective
                    if !pokemonList.isEmpty {
                        self.isLoading = false
                    }
                }
            } catch {
                if !(error is CancellationError) {
                    self.error = "Database observation failed: \(error.localizedDescription)"
                }
            }
        }
        
        // Trigger a network load if necessary
        do {
            _ = try await asyncFunction(for: sdk.loadPokemonForGeneration(generationId: Int32(selectedGeneration)))
        } catch {
            self.error = "Network load failed: \(error.localizedDescription)"
        }
        
        isLoading = false
    }
    
    func selectGeneration(_ generationId: Int32) async {
        selectedGeneration = generationId
        await loadPokemon()
    }
    
    func getGenerations() -> [Shared.GenerationInfo] {
        return sdk.getGenerations()
    }
    
    deinit {
        observationTask?.cancel()
    }
}
