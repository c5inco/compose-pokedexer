import Foundation
import Combine
import Shared

@MainActor
class HomeViewModel: ObservableObject {
    @Published var searchText = ""
    @Published var searchResults: SearchResponse = SearchResponse()
    @Published var isLoading = false

    private let sdk = PokedexerSDKWrapper.shared
    private var searchTask: Task<Void, Never>?
    private var debounceTask: Task<Void, Never>?

    struct SearchResponse {
        var foundPokemon: [Pokemon] = []
        var foundMoves: [Move] = []
        var foundItems: [Item] = []

        var isEmpty: Bool {
            foundPokemon.isEmpty && foundMoves.isEmpty && foundItems.isEmpty
        }
    }

    init() {
        // Observe search text changes
        searchTask = Task {
            for await searchText in $searchText.values {
                await performSearch(query: searchText)
            }
        }
    }

    private func performSearch(query: String) async {
        // Cancel previous debounce
        debounceTask?.cancel()

        guard !query.isEmpty else {
            searchResults = SearchResponse()
            return
        }

        // Debounce 200ms
        debounceTask = Task {
            try? await Task.sleep(nanoseconds: 200_000_000)

            guard !Task.isCancelled else { return }

            isLoading = true

            do {
                async let pokemon = searchPokemon(query: query)
                async let moves = searchMoves(query: query)
                async let items = searchItems(query: query)

                let results = try await (pokemon, moves, items)

                searchResults = SearchResponse(
                    foundPokemon: results.0,
                    foundMoves: results.1,
                    foundItems: results.2
                )
                isLoading = false
            } catch {
                print("Search error: \(error)")
                isLoading = false
            }
        }
    }

    private func searchPokemon(query: String) async throws -> [Pokemon] {
        guard let flow = sdk.getPokemonByName(name: query) else { return [] }
        // SKIE's SkieSwiftFlow conforms to AsyncSequence, iterate directly
        for await pokemonList in flow {
            return Array(pokemonList.prefix(5)) // Limit to 5 results
        }
        return []
    }

    private func searchMoves(query: String) async throws -> [Move] {
        guard let flow = sdk.getMovesByName(name: query) else { return [] }
        for await movesList in flow {
            return Array(movesList.prefix(5))
        }
        return []
    }

    private func searchItems(query: String) async throws -> [Item] {
        guard let flow = sdk.getItemsByName(name: query) else { return [] }
        for await itemsList in flow {
            return Array(itemsList.prefix(5))
        }
        return []
    }

    deinit {
        searchTask?.cancel()
        debounceTask?.cancel()
    }
}
