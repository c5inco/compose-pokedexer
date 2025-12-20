import Foundation
import Shared
import KMPNativeCoroutinesAsync

/**
 * ViewModel for the Moves list screen.
 * Uses the PokedexerSDK from the shared Kotlin module.
 */
@MainActor
class MovesListViewModel: ObservableObject {
    @Published var moves: [Shared.Move] = []
    @Published var isLoading: Bool = true
    @Published var error: String?
    
    private let sdk: PokedexerSDK
    private var observationTask: Task<Void, Never>? = nil
    
    init() {
        // Initialize SDK with iOS-native UserDefaults storage
        let favoritesStore = UserDefaultsFavoritesStore()
        self.sdk = PokedexerSDK(favoritesStore: favoritesStore)
    }
    
    func loadMoves() async {
        isLoading = true
        error = nil
        
        // Cancel existing observation
        observationTask?.cancel()
        
        // Start observing the local database for changes
        observationTask = Task { @MainActor in
            do {
                let movesFlow = sdk.getAllMoves()
                for try await movesList in asyncSequence(for: movesFlow) {
                    self.moves = movesList
                    // If we have data, we're no longer "loading" from the cache perspective
                    if !movesList.isEmpty {
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
            _ = try await asyncFunction(for: sdk.loadMoves())
        } catch {
            self.error = "Network load failed: \(error.localizedDescription)"
        }
        
        isLoading = false
    }
    
    deinit {
        observationTask?.cancel()
    }
}

