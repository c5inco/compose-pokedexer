import Shared
import SwiftUI

struct TypeChartView: View {
    let types = TypeEffectiveness.shared.displayOrder
    let cellSize: CGFloat = 44
    
    @State private var scrollOffset: CGPoint = .zero
    
    var body: some View {
        ZStack(alignment: .topLeading) {
            // 1. Main Content (Scrollable)
            ScrollView([.horizontal, .vertical]) {
                VStack(spacing: 0) {
                    ForEach(types, id: \.self) { attackerType in
                        HStack(spacing: 0) {
                            ForEach(types, id: \.self) { defenderType in
                                let effectiveness = TypeEffectiveness.shared.getEffectiveness(attacker: attackerType, defender: defenderType)
                                EffectivenessCell(effectiveness: effectiveness)
                                    .frame(width: cellSize, height: cellSize)
                            }
                        }
                    }
                }
                .background(GeometryReader { proxy in
                    Color.clear.preference(
                        key: ViewOffsetKey.self,
                        value: proxy.frame(in: .named("scroll")).origin
                    )
                })
                .padding(.top, cellSize) // Space for top header
                .padding(.leading, cellSize) // Space for left header
                .padding(.trailing, 16)
                .padding(.bottom, 16)
            }
            .coordinateSpace(name: "scroll")
            .onPreferenceChange(ViewOffsetKey.self) { value in
                scrollOffset = value
            }
            
            // 2. Top Header (Horizontal sticky)
            ScrollView(.horizontal) { // Dummy scrollview to clip content? No, simple HStack.
                HStack(spacing: 0) {
                    ForEach(types, id: \.self) { type in
                        TypeHeaderCell(type: type)
                            .frame(width: cellSize, height: cellSize)
                    }
                }
                .offset(x: scrollOffset.x)
                .padding(.leading, cellSize) // Align with grid start
            }
            .disabled(true) // Disable interaction, let main scroll drive it
            .frame(height: cellSize)
            .background(.regularMaterial)
            .zIndex(1)
            
            // 3. Left Header (Vertical sticky)
            ScrollView(.vertical) {
                VStack(spacing: 0) {
                    ForEach(types, id: \.self) { type in
                        TypeHeaderCell(type: type)
                            .frame(width: cellSize, height: cellSize)
                    }
                }
                .offset(y: scrollOffset.y)
                .padding(.top, cellSize) // Align with grid start
            }
            .disabled(true)
            .frame(width: cellSize)
            .background(.regularMaterial)
            .zIndex(1)
            
            // 4. Corner (Fixed)
            Color.clear
                .background(.regularMaterial)
                .frame(width: cellSize, height: cellSize)
                .zIndex(2)
                .overlay(alignment: .bottomTrailing) {
                     Divider()
                     Divider().rotationEffect(.degrees(90))
                }
        }
        .safeAreaInset(edge: .bottom) {
            LegendRow()
                .padding(.vertical, 8)
                .background(.regularMaterial)
        }
        .navigationTitle("Type Chart")
        .navigationBarTitleDisplayMode(.inline)
    }
}

// MARK: - Preference Key
struct ViewOffsetKey: PreferenceKey {
    static var defaultValue: CGPoint = .zero
    static func reduce(value: inout CGPoint, nextValue: () -> CGPoint) {
        value = nextValue()
    }
}

// MARK: - Components

private struct TypeHeaderCell: View {
    let type: PokemonType
    
    var body: some View {
        TypeHeaderCellContent(type: type)
            .pokemonTheme(types: [type.name])
    }
}

private struct TypeHeaderCellContent: View {
    let type: PokemonType
    @Environment(\.pokemonTheme) var theme
    
    var body: some View {
        ZStack {
            TypeIcon(typeName: type.name)
                .foregroundColor(theme.onBackground
                )
                .padding(6)
        }
        .background {
            Circle()
                .fill(theme.background.gradient)
        }
        .padding(4)
    }
}

private struct EffectivenessCell: View {
    let effectiveness: Float
    
    var body: some View {
        let (backgroundColor, text, textColor) = styleFor(effectiveness)
        
        ZStack {
            if !text.isEmpty {
                Text(text)
                    .font(AppTypography.caption1)
                    .fontWeight(.medium)
                    .foregroundColor(textColor)
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .padding(4)
        .background(
            RoundedRectangle(cornerRadius: 4)
                .fill(backgroundColor)
        )
    }
    
    func styleFor(_ value: Float) -> (Color, String, Color) {
        switch value {
        case 2.0:
            return (Color(hex: 0x4CAF50).opacity(0.2), "2×", Color(hex: 0x2E7D32))
        case 0.5:
            return (Color(hex: 0xF44336).opacity(0.2), "½×", Color(hex: 0xC62828))
        case 0.0:
            return (Color(hex: 0x424242).opacity(0.2), "0", Color(hex: 0x9E9E9E))
        default:
            return (.clear, "", .clear)
        }
    }
}

private struct LegendRow: View {
    var body: some View {
        HStack(spacing: 16) {
            LegendItem(color: Color(hex: 0x4CAF50), text: "2× Super Effective")
            LegendItem(color: Color(hex: 0xF44336), text: "½× Not Very Effective")
            LegendItem(color: Color(hex: 0x424242), text: "0 No Effect")
        }
        .frame(maxWidth: .infinity)
    }
}

private struct LegendItem: View {
    let color: Color
    let text: String
    
    var body: some View {
        HStack(spacing: 4) {
            RoundedRectangle(cornerRadius: 2)
                .fill(color.opacity(0.3))
                .frame(width: 12, height: 12)
            
            Text(text)
                .font(.system(size: 10))
                .foregroundColor(.secondary)
        }
    }
}
