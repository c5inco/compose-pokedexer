import SwiftUI

struct TypeChartView: View {
    let allTypes = ["Normal", "Fire", "Water", "Electric", "Grass", "Ice", "Fighting", "Poison", "Ground", "Flying", "Psychic", "Bug", "Rock", "Ghost", "Dragon", "Dark", "Steel", "Fairy"]

    var body: some View {
        ScrollView([.horizontal, .vertical]) {
            VStack(spacing: 0) {
                // Header row
                HStack(spacing: 0) {
                    Color.clear
                        .frame(width: 80, height: 40)

                    ForEach(allTypes, id: \.self) { type in
                        TypeIcon(typeName: type)
                            .frame(width: 40, height: 40)
                    }
                }

                // Data rows
                ForEach(allTypes, id: \.self) { attackingType in
                    HStack(spacing: 0) {
                        TypeIcon(typeName: attackingType)
                            .frame(width: 80, height: 40)

                        ForEach(allTypes, id: \.self) { defendingType in
                            let effectiveness = getEffectiveness(attacking: attackingType, defending: defendingType)

                            Text(effectiveness.symbol)
                                .font(AppTypography.caption1)
                                .frame(width: 40, height: 40)
                                .background(effectiveness.color)
                                .border(Color.gray.opacity(0.2), width: 0.5)
                        }
                    }
                }
            }
        }
        .navigationTitle("Type Chart")
        .navigationBarTitleDisplayMode(.inline)
    }

    func getEffectiveness(attacking: String, defending: String) -> (symbol: String, color: Color) {
        // Simplified type effectiveness (would need full chart for accuracy)
        // This is a placeholder - full implementation would reference the TypeEffectiveness.kt
        return ("1×", Color(.systemBackground))
    }
}
