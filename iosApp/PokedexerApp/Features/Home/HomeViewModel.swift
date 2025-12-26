import Foundation
import Combine
import shared

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
        Task {
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
        var results: [Pokemon] = []
        for try await pokemonList in sdk.getPokemonByName(name: query).asAsyncSequence() as AsyncThrowingStream<[Pokemon], Error> {
            results = pokemonList
            break // Take first emission
        }
        return Array(results.prefix(5)) // Limit to 5 results
    }

    private func searchMoves(query: String) async throws -> [Move] {
        var results: [Move] = []
        for try await movesList in sdk.getMovesByName(name: query).asAsyncSequence() as AsyncThrowingStream<[Move], Error> {
            results = movesList
            break
        }
        return Array(results.prefix(5))
    }

    private func searchItems(query: String) async throws -> [Item] {
        var results: [Item] = []
        for try await itemsList in sdk.getItemsByName(name: query).asAsyncSequence() as AsyncThrowingStream<[Item], Error> {
            results = itemsList
            break
        }
        return Array(results.prefix(5))
    }

    deinit {
        searchTask?.cancel()
        debounceTask?.cancel()
    }
}
