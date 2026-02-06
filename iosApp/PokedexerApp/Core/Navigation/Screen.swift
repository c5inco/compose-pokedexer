import Foundation

enum Screen: Hashable {
    case home
    case pokedex
    case pokemonDetail(id: Int)
    case moves
    case items
    case typeChart
}
