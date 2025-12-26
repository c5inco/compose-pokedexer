import Foundation
import shared

@MainActor
class PokemonDetailViewModel: ObservableObject {
    @Published var pokemon: Pokemon?
    @Published var pokemonSet: [Pokemon] = []
    @Published var currentPokemonId: Int
    @Published var isFavorite = false
    @Published var isLoading = false

    private let sdk = PokedexerSDKWrapper.shared
    private var loadTask: Task<Void, Never>?

    init(pokemonId: Int) {
        self.currentPokemonId = pokemonId
    }

    func loadPokemon() async {
        isLoading = true

        loadTask = Task {
            do {
                // Load current Pokemon
                for try await pokemonData in sdk.getPokemonById(id: Int32(currentPokemonId)).asAsyncSequence() as AsyncThrowingStream<Pokemon?, Error> {
                    if let pokemonData = pokemonData {
                        self.pokemon = pokemonData
                    }
                    break
                }

                // Load all Pokemon for paging
                for try await allPokemon in sdk.getAllPokemon().asAsyncSequence() as AsyncThrowingStream<[Pokemon], Error> {
                    self.pokemonSet = allPokemon
                    break
                }

                self.isLoading = false
            } catch {
                print("Error loading Pokemon details: \(error)")
                self.isLoading = false
            }
        }
    }

    func toggleFavorite() {
        isFavorite.toggle()
    }

    deinit {
        loadTask?.cancel()
    }
}
