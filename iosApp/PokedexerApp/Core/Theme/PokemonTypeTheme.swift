import Shared
import SwiftUI

// MARK: - Pokemon Type Colors
struct PokemonColors {
    static let bug = Color(hex: 0xAFC836)
    static let dark = Color(hex: 0x9298A4)
    static let dragon = Color(hex: 0x0180C7)
    static let electric = Color(hex: 0xF0C03E)
    static let fairy = Color(hex: 0xEE99EE)
    static let fighting = Color(hex: 0xE74347)
    static let fire = Color(hex: 0xFBAE46)
    static let flying = Color(hex: 0xA6C2F2)
    static let ghost = Color(hex: 0x7773D4)
    static let grass = Color(hex: 0x5AC178)
    static let ground = Color(hex: 0xD29463)
    static let ice = Color(hex: 0x8CDDD4)
    static let normal = Color(hex: 0xA3A49E)
    static let poison = Color(hex: 0xC261D4)
    static let psychic = Color(hex: 0xFE9F92)
    static let rock = Color(hex: 0xD7CD90)
    static let water = Color(hex: 0x429BED)
    static let steel = Color(hex: 0x58A6AA)

    static func color(for typeName: String?) -> Color {
        guard let typeName = typeName else { return .gray }

        switch typeName.lowercased() {
        case "bug": return bug
        case "dark": return dark
        case "dragon": return dragon
        case "electric": return electric
        case "fairy": return fairy
        case "fighting": return fighting
        case "fire": return fire
        case "flying": return flying
        case "ghost": return ghost
        case "grass": return grass
        case "ground": return ground
        case "ice": return ice
        case "normal": return normal
        case "poison": return poison
        case "psychic": return psychic
        case "rock": return rock
        case "steel": return steel
        case "water": return water
        default: return .gray
        }
    }
}

// MARK: - Move Category Colors
struct MoveCategoryColors {
    static let physicalLight = PokemonColors.fighting
    static let physicalDark = Color(hex: 0xE3300E)

    static let specialLight = PokemonColors.flying
    static let specialDark = Color(hex: 0xC7BFFF)

    static let statusLight = PokemonColors.fire
    static let statusDark = Color(hex: 0xFFB691)

    static func color(for category: String, isDark: Bool = false) -> Color {
        switch category.lowercased() {
        case "physical":
            return isDark ? physicalDark : physicalLight
        case "special":
            return isDark ? specialDark : specialLight
        case "status":
            return isDark ? statusDark : statusLight
        default:
            return .gray
        }
    }
}

// MARK: - Analogous Color Helpers
extension Color {
    /// Calculate analogous colors by shifting hue
    func analogousColors(offset: CGFloat = 18.0) -> [Color] {
        var hue: CGFloat = 0
        var saturation: CGFloat = 0
        var brightness: CGFloat = 0
        var alpha: CGFloat = 0

        #if canImport(UIKit)
            UIColor(self).getHue(
                &hue,
                saturation: &saturation,
                brightness: &brightness,
                alpha: &alpha
            )
        #else
            NSColor(self).getHue(
                &hue,
                saturation: &saturation,
                brightness: &brightness,
                alpha: &alpha
            )
        #endif

        let offsetDegrees = offset / 360.0

        // Return 5 colors: 2 before, original, 2 after
        return [
            Color(
                hue: (hue - offsetDegrees * 2).truncatingRemainder(
                    dividingBy: 1.0
                ),
                saturation: saturation,
                brightness: brightness,
                opacity: alpha
            ),
            Color(
                hue: (hue - offsetDegrees).truncatingRemainder(dividingBy: 1.0),
                saturation: saturation,
                brightness: brightness,
                opacity: alpha
            ),
            self,
            Color(
                hue: (hue + offsetDegrees).truncatingRemainder(dividingBy: 1.0),
                saturation: saturation,
                brightness: brightness,
                opacity: alpha
            ),
            Color(
                hue: (hue + offsetDegrees * 2).truncatingRemainder(
                    dividingBy: 1.0
                ),
                saturation: saturation,
                brightness: brightness,
                opacity: alpha
            ),
        ]
    }
}

// MARK: - Type to Analogous Hue Index
/// Maps Pokemon types to curated analogous hue index
/// Ported from Type.kt mapTypeToCuratedAnalogousHue()
func mapTypeToCuratedAnalogousHue(_ typeName: String?) -> Int {
    guard let typeName = typeName else { return 0 }

    switch typeName.lowercased() {
    case "fairy", "fire":
        return 3
    case "grass":
        return 2
    case "electric", "dragon", "ghost", "poison", "psychic":
        return 1
    default:
        return 0
    }
}

// MARK: - Color Extension for Hex
extension Color {
    init(hex: UInt, alpha: Double = 1.0) {
        self.init(
            .sRGB,
            red: Double((hex >> 16) & 0xFF) / 255,
            green: Double((hex >> 8) & 0xFF) / 255,
            blue: Double(hex & 0xFF) / 255,
            opacity: alpha
        )
    }
}

// MARK: - Material Kolor Integration
/// Pokemon theme color scheme generated by Material Kolor in the KMP module
struct PokemonThemeColorScheme {
    let primary: Color
    let surface: Color
    let onSurface: Color
    let surfaceVariant: Color
    let secondary: Color
    let tertiary: Color
    
    /// Initialize from KMP PokemonThemeColors (ARGB integers)
    init(from kmpColors: PokemonThemeColors) {
        self.primary = Color(argb: kmpColors.primary)
        self.surface = Color(argb: kmpColors.surface)
        self.onSurface = Color(argb: kmpColors.onSurface)
        self.surfaceVariant = Color(argb: kmpColors.surfaceVariant)
        self.secondary = Color(argb: kmpColors.secondary)
        self.tertiary = Color(argb: kmpColors.tertiary)
    }
}

// MARK: - Environment Keys
struct PokemonThemeKey: EnvironmentKey {
    typealias Value = PokemonThemeColorScheme
    
    static let defaultValue = PokemonThemeColorScheme(
        from: ThemeGeneratorKt.generatePokemonThemeForIOS(
            types: ["grass"],
            isDark: false
        )
    )
}

extension EnvironmentValues {
    var pokemonTheme: PokemonThemeColorScheme {
        get { self[PokemonThemeKey.self] }
        set { self[PokemonThemeKey.self] = newValue }
    }
}

// MARK: - Pokemon Theme View Modifier
/// ViewModifier that automatically detects dark mode and applies Material Kolor-generated theme
private struct PokemonThemeModifier: ViewModifier {
    let pokemon: Pokemon
    @Environment(\.colorScheme) var colorScheme
    
    func body(content: Content) -> some View {
        let theme = PokemonThemeColorScheme(
            from: ThemeGeneratorKt.generatePokemonThemeForIOS(
                types: pokemon.typeOfPokemon,
                isDark: colorScheme == .dark
            )
        )
        
        content
            .tint(theme.onSurface)
            .foregroundStyle(theme.onSurface)
            .environment(\.pokemonTheme, theme)
    }
}

extension View {
    /// Apply Material Kolor-generated theme for a Pokemon
    /// Automatically adapts to system dark/light mode
    func pokemonTheme(_ pokemon: Pokemon) -> some View {
        self.modifier(PokemonThemeModifier(pokemon: pokemon))
    }
}

// MARK: - ARGB Color Extension
extension Color {
    /// Initialize Color from ARGB integer value (as used by Compose)
    init(argb: Int32) {
        let a = Double((argb >> 24) & 0xFF) / 255.0
        let r = Double((argb >> 16) & 0xFF) / 255.0
        let g = Double((argb >> 8) & 0xFF) / 255.0
        let b = Double(argb & 0xFF) / 255.0
        
        self.init(.sRGB, red: r, green: g, blue: b, opacity: a)
    }
}
