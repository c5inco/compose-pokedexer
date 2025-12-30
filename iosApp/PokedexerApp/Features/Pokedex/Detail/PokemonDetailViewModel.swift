import Foundation
import Shared
import Combine

// MARK: - Detail Models (mirroring Android)
struct PokemonDetailsEvolution: Identifiable {
    let id: Int
    let pokemon: Pokemon
    let trigger: EvolutionTrigger
    let targetLevel: Int32
    let item: Item?
}

struct PokemonDetailsMove: Identifiable {
    let uuid = UUID()
    var id: UUID { uuid }
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
    @Published var isLoading = false
    
    // Detailed data
    @Published var evolutions: [PokemonDetailsEvolution] = []
    @Published var moves: [PokemonDetailsMove] = []
    @Published var abilities: [PokemonDetailsAbility] = []

    private let favoriteManager = FavoriteManager.shared
    private let sdk = PokedexerSDKWrapper.shared
    private var loadTask: Task<Void, Never>?
    private var cancellables = Set<AnyCancellable>()
    
    var isFavorite: Bool {
        favoriteManager.isFavorite(currentPokemonId)
    }

    init(pokemonId: Int) {
        self.currentPokemonId = pokemonId
        
        // Observe changes to favoriteIds and trigger view updates
        favoriteManager.$favoriteIds
            .sink { [weak self] _ in
                self?.objectWillChange.send()
            }
            .store(in: &cancellables)
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
        var seenMoveIds = Set<String>()

        for pokemonMove in pokemon.movesList {
            // Create a unique key for deduplication
            let key = "\(pokemonMove.id)-\(pokemonMove.targetLevel)"
            if seenMoveIds.contains(key) { continue }
            seenMoveIds.insert(key)

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

        // Sort by level, then by name for consistency
        self.moves = loadedMoves.sorted {
            if $0.targetLevel != $1.targetLevel {
                return $0.targetLevel < $1.targetLevel
            }
            return $0.move.name < $1.move.name
        }
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
        favoriteManager.toggleFavorite(currentPokemonId)
    }

    deinit {
        loadTask?.cancel()
        cancellables.removeAll()
    }
}
