import Foundation
import Shared
import KMPNativeCoroutinesAsync
import Network

/**
 * ViewModel for the Pokemon list screen.
 * Uses the PokedexerSDK from the shared Kotlin module.
 */
@MainActor
class PokemonListViewModel: ObservableObject {
    @Published var pokemon: [Shared.Pokemon] = []
    @Published var isLoading: Bool = true
    @Published var isOffline: Bool = false
    @Published var error: String?
    @Published var selectedGeneration: Int32 = 1
    
    private let sdk: PokedexerSDK
    private var observationTask: Task<Void, Never>? = nil
    private let networkMonitor = NWPathMonitor()
    private let monitorQueue = DispatchQueue(label: "NetworkMonitor")
    
    init() {
        // Initialize SDK with iOS-native UserDefaults storage
        let favoritesStore = UserDefaultsFavoritesStore()
        self.sdk = PokedexerSDK(favoritesStore: favoritesStore)
        
        // Start network monitoring
        networkMonitor.pathUpdateHandler = { [weak self] path in
            Task { @MainActor in
                self?.isOffline = path.status != .satisfied
            }
        }
        networkMonitor.start(queue: monitorQueue)
    }
    
    func loadPokemon() async {
        isLoading = true
        error = nil
        
        // Cancel existing observation if switching generations
        observationTask?.cancel()
        
        // Start observing the local database for changes to this generation
        observationTask = Task { @MainActor in
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
            // Only show error if we don't have cached data
            if pokemon.isEmpty {
                if isOffline {
                    self.error = "You're offline. Connect to the internet to load Pokémon."
                } else {
                    self.error = "Failed to load Pokémon. Please try again."
                }
            }
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
        networkMonitor.cancel()
    }
}
