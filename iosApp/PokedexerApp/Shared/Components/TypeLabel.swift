import Shared
import SwiftUI

struct TypeLabel: View {
    let typeName: String
    var size: TypeLabelSize = .medium
    var colored: Bool = false
    var showIcon: Bool = false
    var isSelected: Bool = true

    enum TypeLabelSize {
        case small
        case medium
        case large

        var fontSize: CGFloat {
            switch self {
            case .small: return 11
            case .medium: return 13
            case .large: return 15
            }
        }

        var iconSize: CGFloat {
            switch self {
            case .large: return 24
            default: return 18
            }
        }

        var padding: EdgeInsets {
            switch self {
            case .small:
                return EdgeInsets(top: 2, leading: 6, bottom: 2, trailing: 6)
            case .medium:
                return EdgeInsets(top: 4, leading: 8, bottom: 4, trailing: 8)
            case .large:
                return EdgeInsets(top: 6, leading: 12, bottom: 6, trailing: 12)
            }
        }
    }

    var body: some View {
        TypeLabelContent(
            typeName: typeName,
            size: size,
            colored: colored,
            showIcon: showIcon,
            isSelected: isSelected
        )
    }
}

private struct TypeLabelContent: View {
    let typeName: String
    let size: TypeLabel.TypeLabelSize
    let colored: Bool
    let showIcon: Bool
    let isSelected: Bool

    @Environment(\.pokemonTheme) var theme

    var body: some View {
        HStack(spacing: 4) {
            if size != .small && showIcon {
                TypeIcon(typeName: typeName)
                    .frame(width: size.iconSize, height: size.iconSize)
                    .foregroundColor(
                        isSelected ? theme.onBackground : .secondary
                    )
            }

            Text(typeName.capitalized)
                .font(.circularStd(size: size.fontSize, weight: .semibold))
                .foregroundColor(isSelected ? theme.onBackground : .primary)
        }
        .padding(size.padding)
        .background {
            if isSelected {
                if colored {
                    theme.background
                } else {
                    Color.white.opacity(0.22)
                }
            } else {
                Color(.systemGray6)
            }
        }
        .cornerRadius(size.fontSize * 2)
    }
}

struct TypeIcon: View {
    let typeName: String

    var body: some View {
        let iconName = mapTypeToIcon(typeName: typeName)

        Image(iconName)
            .resizable()
            .aspectRatio(contentMode: .fit)
    }
}

func mapTypeToIcon(typeName: String) -> String {
    switch typeName.lowercased() {
    case "normal": return "ic_type_normal"
    case "fire": return "ic_type_fire"
    case "water": return "ic_type_water"
    case "electric": return "ic_type_electric"
    case "grass": return "ic_type_grass"
    case "ice": return "ic_type_ice"
    case "fighting": return "ic_type_fighting"
    case "poison": return "ic_type_poison"
    case "ground": return "ic_type_ground"
    case "flying": return "ic_type_flying"
    case "psychic": return "ic_type_psychic"
    case "bug": return "ic_type_bug"
    case "rock": return "ic_type_rock"
    case "ghost": return "ic_type_ghost"
    case "dragon": return "ic_type_dragon"
    case "dark": return "ic_type_dark"
    case "steel": return "ic_type_steel"
    case "fairy": return "ic_type_fairy"
    default: return "ic_pokeball"
    }
}
