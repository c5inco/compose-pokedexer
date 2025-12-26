import SwiftUI

struct TypeLabel: View {
    let typeName: String
    let size: TypeLabelSize

    enum TypeLabelSize {
        case small
        case medium
        case large

        var fontSize: CGFloat {
            switch self {
            case .small: return 10
            case .medium: return 12
            case .large: return 14
            }
        }

        var padding: EdgeInsets {
            switch self {
            case .small: return EdgeInsets(top: 2, leading: 6, bottom: 2, trailing: 6)
            case .medium: return EdgeInsets(top: 4, leading: 8, bottom: 4, trailing: 8)
            case .large: return EdgeInsets(top: 6, leading: 12, bottom: 6, trailing: 12)
            }
        }
    }

    var body: some View {
        Text(typeName.capitalized)
            .font(.system(size: size.fontSize, weight: .semibold))
            .foregroundColor(.white)
            .padding(size.padding)
            .background(
                PokemonColors.color(for: typeName)
                    .opacity(0.8)
            )
            .cornerRadius(size.fontSize)
    }
}

struct TypeIcon: View {
    let typeName: String

    var iconName: String {
        // Map Pokemon types to SF Symbols
        switch typeName.lowercased() {
        case "fire": return "flame.fill"
        case "water": return "drop.fill"
        case "grass": return "leaf.fill"
        case "electric": return "bolt.fill"
        case "ice": return "snowflake"
        case "fighting": return "figure.boxing"
        case "poison": return "cross.case.fill"
        case "ground": return "mountain.2.fill"
        case "flying": return "wind"
        case "psychic": return "brain.head.profile"
        case "bug": return "ant.fill"
        case "rock": return "diamond.fill"
        case "ghost": return "moonphase.waning.gibbous"
        case "dragon": return "lizard.fill"
        case "dark": return "moon.fill"
        case "steel": return "shield.fill"
        case "fairy": return "sparkles"
        case "normal": return "circle.fill"
        default: return "questionmark.circle.fill"
        }
    }

    var body: some View {
        Image(systemName: iconName)
            .foregroundColor(PokemonColors.color(for: typeName))
    }
}
