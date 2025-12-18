import Foundation
import Shared

@MainActor
class HomeViewModel: ObservableObject {
    @Published var greeting: String = ""
    
    private let sdk: PokedexerSDK
    
    init() {
        // Initialize SDK with iOS-native UserDefaults storage
        let favoritesStore = UserDefaultsFavoritesStore()
        self.sdk = PokedexerSDK(favoritesStore: favoritesStore)
        
        // Simple greeting for now
        self.greeting = "Your Pokémon companion app"
    }
}
