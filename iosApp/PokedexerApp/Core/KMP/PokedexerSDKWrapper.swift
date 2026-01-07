import Foundation
import Shared

/// Wrapper for the KMP PokedexerSDK that provides async initialization
/// to avoid blocking the main thread during app launch.
@MainActor
class PokedexerSDKWrapper: ObservableObject {
    static let shared = PokedexerSDKWrapper()

    private var sdk: PokedexerSDK?

    /// Indicates whether the SDK has been initialized and is ready for use
    @Published private(set) var isInitialized = false

    /// Error that occurred during initialization, if any
    @Published private(set) var initializationError: Error?

    private init() {}

    /// Initializes the SDK asynchronously on a background thread.
    /// Safe to call multiple times - subsequent calls are no-ops if already initialized.
    func initialize() async {
        // Skip if already initialized
        guard sdk == nil else { return }

        do {
            // Run SDK initialization on a background thread to avoid blocking main thread
            // PokedexerSDK.Companion.shared.create() initializes Room database on Dispatchers.IO in Kotlin
            let newSDK = try await Task.detached(priority: .userInitiated) {
                try await PokedexerSDK.Companion.shared.create()
            }.value

            self.sdk = newSDK
            self.isInitialized = true
            self.initializationError = nil
        } catch {
            self.initializationError = error
            print("Failed to initialize PokedexerSDK: \(error)")
        }
    }

    // MARK: - SDK Access

    /// Returns the initialized SDK, or nil if not yet initialized.
    /// Check `isInitialized` before calling SDK methods.
    private var initializedSDK: PokedexerSDK? {
        guard isInitialized else {
            print("Warning: Attempting to use SDK before initialization")
            return nil
        }
        return sdk
    }

    // MARK: - Pokemon methods (SKIE returns typed Swift Flows)

    func getAllPokemon() -> SkieSwiftFlow<[Pokemon]>? {
        return initializedSDK?.getAllPokemon()
    }

    func getPokemonById(id: Int32) -> SkieSwiftOptionalFlow<Pokemon>? {
        return initializedSDK?.getPokemonById(id: id)
    }

    func getPokemonByName(name: String) -> SkieSwiftFlow<[Pokemon]>? {
        return initializedSDK?.getPokemonByName(name: name)
    }

    func getPokemonByGeneration(generationId: Int32) -> SkieSwiftFlow<[Pokemon]>? {
        return initializedSDK?.getPokemonByGeneration(generationId: generationId)
    }

    func updatePokemon() async throws {
        guard let sdk = initializedSDK else { return }
        try await sdk.updatePokemon()
    }

    // MARK: - Moves methods

    func getAllMoves() -> SkieSwiftFlow<[Move]>? {
        return initializedSDK?.getAllMoves()
    }

    func getMoveById(id: Int32) -> SkieSwiftOptionalFlow<Move>? {
        return initializedSDK?.getMoveById(id: id)
    }

    func getMovesByName(name: String) -> SkieSwiftFlow<[Move]>? {
        return initializedSDK?.getMovesByName(name: name)
    }

    func updateMoves() async throws {
        guard let sdk = initializedSDK else { return }
        try await sdk.updateMoves()
    }

    // MARK: - Items methods

    func getAllItems() -> SkieSwiftFlow<[Item]>? {
        return initializedSDK?.getAllItems()
    }

    func getItemById(id: Int32) -> SkieSwiftOptionalFlow<Item>? {
        return initializedSDK?.getItemById(id: id)
    }

    func getItemsByName(name: String) -> SkieSwiftFlow<[Item]>? {
        return initializedSDK?.getItemsByName(name: name)
    }

    func updateItems() async throws {
        guard let sdk = initializedSDK else { return }
        try await sdk.updateItems()
    }

    // MARK: - Abilities methods

    func getAbilityById(id: Int32) -> SkieSwiftOptionalFlow<Ability>? {
        return initializedSDK?.getAbilityById(id: id)
    }

    func updateAbilities() async throws {
        guard let sdk = initializedSDK else { return }
        try await sdk.updateAbilities()
    }
}
