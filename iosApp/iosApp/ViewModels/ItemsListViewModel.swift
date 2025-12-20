import Foundation
import Shared
import KMPNativeCoroutinesAsync

@MainActor
class ItemsListViewModel: ObservableObject {
    @Published var items: [Shared.Item] = []
    @Published var isLoading = false
    @Published var error: String?
    
    private let sdk: PokedexerSDK
    
    init() {
        self.sdk = PokedexerSDK(favoritesStore: UserDefaultsFavoritesStore())
    }
    
    func loadItems() async {
        isLoading = true
        error = nil
        
        do {
            let loadedItems = try await asyncFunction(for: sdk.loadItems())
            items = loadedItems
        } catch {
            self.error = "Failed to load items: \(error.localizedDescription)"
            print("Error loading items: \(error)")
        }
        
        isLoading = false
    }
}

