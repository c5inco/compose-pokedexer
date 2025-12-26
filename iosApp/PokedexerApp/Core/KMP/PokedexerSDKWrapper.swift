import Foundation
import shared

@MainActor
class PokedexerSDKWrapper {
    static let shared = PokedexerSDKWrapper()
    private let sdk: PokedexerSDK

    private init() {
        self.sdk = PokedexerSDK()
    }

    // Pokemon methods
    func getAllPokemon() -> Kotlinx_coroutines_coreFlow {
        return sdk.getAllPokemon()
    }

    func getPokemonById(id: Int32) -> Kotlinx_coroutines_coreFlow {
        return sdk.getPokemonById(id: id)
    }

    func getPokemonByName(name: String) -> Kotlinx_coroutines_coreFlow {
        return sdk.getPokemonByName(name: name)
    }
    
    func getPokemonByGeneration(generationId: Int32) -> Kotlinx_coroutines_coreFlow {
        return sdk.getPokemonByGeneration(generationId: generationId)
    }

    func updatePokemon() async throws {
        try await sdk.updatePokemon()
    }

    // Moves methods
    func getAllMoves() -> Kotlinx_coroutines_coreFlow {
        return sdk.getAllMoves()
    }

    func getMoveById(id: Int32) -> Kotlinx_coroutines_coreFlow {
        return sdk.getMoveById(id: id)
    }

    func getMovesByName(name: String) -> Kotlinx_coroutines_coreFlow {
        return sdk.getMovesByName(name: name)
    }

    func updateMoves() async throws {
        try await sdk.updateMoves()
    }

    // Items methods
    func getAllItems() -> Kotlinx_coroutines_coreFlow {
        return sdk.getAllItems()
    }

    func getItemById(id: Int32) -> Kotlinx_coroutines_coreFlow {
        return sdk.getItemById(id: id)
    }

    func getItemsByName(name: String) -> Kotlinx_coroutines_coreFlow {
        return sdk.getItemsByName(name: name)
    }

    func updateItems() async throws {
        try await sdk.updateItems()
    }

    // Abilities methods
    func getAbilityById(id: Int32) -> Kotlinx_coroutines_coreFlow {
        return sdk.getAbilityById(id: id)
    }

    func updateAbilities() async throws {
        try await sdk.updateAbilities()
    }
}
