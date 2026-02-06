import Foundation
import Combine

/// Singleton manager for favorite Pokemon IDs, persisted using UserDefaults
@MainActor
class FavoriteManager: ObservableObject {
    static let shared = FavoriteManager()
    
    @Published var favoriteIds: Set<Int> = []
    
    private let userDefaultsKey = "favoritePokemonIds"
    private let userDefaults = UserDefaults.standard
    
    private init() {
        loadFavorites()
    }
    
    /// Load favorites from UserDefaults
    private func loadFavorites() {
        if let array = userDefaults.array(forKey: userDefaultsKey) as? [Int] {
            favoriteIds = Set(array)
        } else {
            favoriteIds = []
        }
    }
    
    /// Save favorites to UserDefaults
    private func saveFavorites() {
        let array = Array(favoriteIds).sorted()
        userDefaults.set(array, forKey: userDefaultsKey)
    }
    
    /// Check if a Pokemon is favorited
    func isFavorite(_ pokemonId: Int) -> Bool {
        favoriteIds.contains(pokemonId)
    }
    
    /// Toggle favorite status for a Pokemon
    func toggleFavorite(_ pokemonId: Int) {
        if favoriteIds.contains(pokemonId) {
            favoriteIds.remove(pokemonId)
        } else {
            favoriteIds.insert(pokemonId)
        }
        saveFavorites()
    }
}
