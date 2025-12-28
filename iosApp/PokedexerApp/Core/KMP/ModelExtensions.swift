import Foundation
import Shared

// MARK: - Pokemon Extensions
extension Pokemon {
    var formattedId: String {
        String(format: "#%03d", id)
    }

    var primaryType: String? {
        typeOfPokemon.first
    }

    var imageURL: URL? {
        // Construct Pokemon image URL from the image ID
        URL(string: "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/\(image).png")
    }

    var heightInMeters: String {
        String(format: "%.1f m", height / 10.0)
    }

    var weightInKilograms: String {
        String(format: "%.1f kg", weight / 10.0)
    }

    var genderRatio: String {
        switch genderRate {
        case -1:
            return "Genderless"
        case 0:
            return "100% ♂"
        case 8:
            return "100% ♀"
        default:
            let femalePercent = Int((Double(genderRate) / 8.0) * 100)
            let malePercent = 100 - femalePercent
            return "\(malePercent)% ♂ / \(femalePercent)% ♀"
        }
    }
}

// MARK: - Move Extensions
extension Move {
    var powerDisplay: String {
        if let power = power?.intValue, power > 0 {
            return "\(power)"
        }
        return "—"
    }

    var accuracyDisplay: String {
        if let accuracy = accuracy?.intValue, accuracy > 0 {
            return "\(accuracy)"
        }
        return "—"
    }

    var ppDisplay: String {
        return "\(pp)"
    }
}

// MARK: - Item Extensions
extension Item {
    var spriteURL: URL? {
        URL(string: sprite)
    }
}

// MARK: - Evolution Extensions
extension Evolution {
    var triggerDisplayName: String {
        // SKIE generates native Swift enums with camelCase names
        switch trigger {
        case .levelUp:
            return targetLevel > 0 ? "Level \(targetLevel)" : "Level Up"
        case .useItem:
            return "Use Item"
        case .trade:
            return "Trade"
        }
    }
}

// MARK: - PokemonMove Extensions  
extension PokemonMove {
    var moveId: Int32 {
        return id
    }
    
    var level: Int32 {
        return targetLevel
    }
    
    var name: String {
        return "Move #\(id)"
    }
}
