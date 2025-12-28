import Foundation
import Shared

// MARK: - Detail Models (mirroring Android)
struct PokemonDetailsEvolution: Identifiable {
    let id: Int
    let pokemon: Pokemon
    let trigger: EvolutionTrigger
    let targetLevel: Int32
    let item: Item?
}

struct PokemonDetailsMove: Identifiable {
    var id: Int32 { move.id }
    let move: Move
    let targetLevel: Int32
}

struct PokemonDetailsAbility: Identifiable {
    var id: Int32 { ability.id }
    let ability: Ability
    let isHidden: Bool
}

@MainActor
class PokemonDetailViewModel: ObservableObject {
    @Published var pokemon: Pokemon?
    @Published var pokemonSet: [Pokemon] = []
    @Published var currentPokemonId: Int
    @Published var isFavorite = false
    @Published var isLoading = false
    
    // Detailed data
    @Published var evolutions: [PokemonDetailsEvolution] = []
    @Published var moves: [PokemonDetailsMove] = []
    @Published var abilities: [PokemonDetailsAbility] = []

    private let sdk = PokedexerSDKWrapper.shared
    private var loadTask: Task<Void, Never>?

    init(pokemonId: Int) {
        self.currentPokemonId = pokemonId
    }

    func loadPokemon() async {
        isLoading = true

        loadTask = Task {
            do {
                // Check if SDK is initialized
                guard sdk.isInitialized else {
                    print("SDK not initialized yet")
                    self.isLoading = false
                    return
                }

                // Load current Pokemon
                var loadedPokemon: Pokemon? = nil
                if let flow = sdk.getPokemonById(id: Int32(currentPokemonId)) {
                    // SKIE's SkieSwiftOptionalFlow conforms to AsyncSequence
                    for await pokemonData in flow {
                        if let pokemonData = pokemonData {
                            loadedPokemon = pokemonData
                            self.pokemon = pokemonData
                        }
                        break
                    }
                }

                // Load all Pokemon for paging
                if let flow = sdk.getAllPokemon() {
                    for await allPokemon in flow {
                        self.pokemonSet = allPokemon
                        break
                    }
                }

                // Load detailed evolution data
                if let pokemon = loadedPokemon {
                    await loadEvolutions(for: pokemon)
                    await loadMoves(for: pokemon)
                    await loadAbilities(for: pokemon)
                }

                self.isLoading = false
            } catch {
                print("Error loading Pokemon details: \(error)")
                self.isLoading = false
            }
        }
    }
    
    private func loadEvolutions(for pokemon: Pokemon) async {
        var loadedEvolutions: [PokemonDetailsEvolution] = []

        for evolution in pokemon.evolutionChain {
            do {
                // Fetch Pokemon details for this evolution
                var evoPokemon: Pokemon? = nil
                if let flow = sdk.getPokemonById(id: evolution.id) {
                    for await p in flow {
                        evoPokemon = p
                        break
                    }
                }

                // Fetch Item if needed
                var evoItem: Item? = nil
                if evolution.itemId > 0, let flow = sdk.getItemById(id: evolution.itemId) {
                    for await item in flow {
                        evoItem = item
                        break
                    }
                }

                if let evoPokemon = evoPokemon {
                    loadedEvolutions.append(PokemonDetailsEvolution(
                        id: Int(evolution.id),
                        pokemon: evoPokemon,
                        trigger: evolution.trigger,
                        targetLevel: evolution.targetLevel,
                        item: evoItem
                    ))
                }
            } catch {
                print("Error loading evolution \(evolution.id): \(error)")
            }
        }

        self.evolutions = loadedEvolutions.sorted { $0.pokemon.id < $1.pokemon.id }
    }

    private func loadMoves(for pokemon: Pokemon) async {
        var loadedMoves: [PokemonDetailsMove] = []

        for pokemonMove in pokemon.movesList {
            do {
                if let flow = sdk.getMoveById(id: pokemonMove.id) {
                    for await move in flow {
                        if let move = move {
                            loadedMoves.append(PokemonDetailsMove(
                                move: move,
                                targetLevel: pokemonMove.targetLevel
                            ))
                        }
                        break
                    }
                }
            } catch {
                print("Error loading move \(pokemonMove.id): \(error)")
            }
        }

        self.moves = loadedMoves.sorted { $0.targetLevel < $1.targetLevel }
    }

    private func loadAbilities(for pokemon: Pokemon) async {
        var loadedAbilities: [PokemonDetailsAbility] = []

        for pokemonAbility in pokemon.abilitiesList {
            do {
                if let flow = sdk.getAbilityById(id: pokemonAbility.id) {
                    for await ability in flow {
                        if let ability = ability {
                            loadedAbilities.append(PokemonDetailsAbility(
                                ability: ability,
                                isHidden: pokemonAbility.isHidden
                            ))
                        }
                        break
                    }
                }
            } catch {
                print("Error loading ability \(pokemonAbility.id): \(error)")
            }
        }

        self.abilities = loadedAbilities
    }

    func toggleFavorite() {
        isFavorite.toggle()
    }

    deinit {
        loadTask?.cancel()
    }
}
