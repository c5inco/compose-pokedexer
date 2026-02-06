import SwiftUI
import Shared

struct CategoryIcon: View {
    let category: String
    
    var body: some View {
        let typeName = mapCategoryToType(category)
        let iconName = mapCategoryToIcon(category)
        
        CategoryIconContent(iconName: iconName)
            .pokemonTheme(types: [typeName])
    }
}

private struct CategoryIconContent: View {
    let iconName: String
    @Environment(\.pokemonTheme) var theme
    
    var body: some View {
        Image(iconName)
            .resizable()
            .aspectRatio(contentMode: .fit)
            .foregroundStyle(theme.primary)
    }
}

private func mapCategoryToType(_ category: String) -> String {
    switch category.lowercased() {
    case "physical": return "fighting"
    case "special": return "flying"
    case "status": return "fire"
    default: return "normal"
    }
}

private func mapCategoryToIcon(_ category: String) -> String {
    switch category.lowercased() {
    case "physical": return "ic_move_physical"
    case "special": return "ic_move_special"
    case "status": return "ic_move_status"
    default: return "dumbbell.fill"
    }
}
