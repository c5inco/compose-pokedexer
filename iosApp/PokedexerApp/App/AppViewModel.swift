import Foundation
import Shared

@MainActor
class AppViewModel: ObservableObject {
    @Published var isSyncingData = false
    @Published var syncProgress: String = ""

    private let sdk = PokedexerSDKWrapper.shared

    func syncData() async {
        isSyncingData = true
        syncProgress = "Syncing Pokémon..."

        let startTime = Date()
        let minimumDisplayTime: TimeInterval = 1.5

        do {
            try await sdk.updatePokemon()
            syncProgress = "Syncing Moves..."
            try await sdk.updateMoves()
            syncProgress = "Syncing Items..."
            try await sdk.updateItems()
            syncProgress = "Syncing Abilities..."
            try await sdk.updateAbilities()
        } catch {
            print("Error syncing data: \(error)")
        }

        // Ensure loading indicator is visible for at least minimumDisplayTime
        let elapsed = Date().timeIntervalSince(startTime)
        if elapsed < minimumDisplayTime {
            try? await Task.sleep(nanoseconds: UInt64((minimumDisplayTime - elapsed) * 1_000_000_000))
        }

        isSyncingData = false
        syncProgress = ""
    }
}
