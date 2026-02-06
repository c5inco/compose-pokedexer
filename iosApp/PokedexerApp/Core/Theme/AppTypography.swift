import SwiftUI

extension Font {
    static func circularStd(size: CGFloat, weight: Font.Weight = .regular) -> Font {
        let fontName: String
        switch weight {
        case .black:
            fontName = "CircularStd-Black"
        case .bold:
            fontName = "CircularStd-Bold"
        case .medium, .semibold:
            fontName = "CircularStd-Medium"
        case .regular, .thin, .light, .ultraLight:
            fontName = "CircularStd-Book"
        default:
            fontName = "CircularStd-Book"
        }
        return .custom(fontName, size: size)
    }
}

struct AppTypography {
    static func largeTitle(weight: Font.Weight = .black) -> Font { .circularStd(size: 36, weight: weight) }
    static func title1(weight: Font.Weight = .bold) -> Font { .circularStd(size: 30, weight: weight) }
    static func title2(weight: Font.Weight = .bold) -> Font { .circularStd(size: 28, weight: weight) }
    static func title3(weight: Font.Weight = .medium) -> Font { .circularStd(size: 20, weight: weight) }
    static func headline(weight: Font.Weight = .bold) -> Font { .circularStd(size: 18, weight: weight) }
    static func body(weight: Font.Weight = .regular) -> Font { .circularStd(size: 17, weight: weight) }
    static func subheadline(weight: Font.Weight = .regular) -> Font { .circularStd(size: 15, weight: weight) }
    static func callout(weight: Font.Weight = .regular) -> Font { .circularStd(size: 14, weight: weight) }
    static func footnote(weight: Font.Weight = .regular) -> Font { .circularStd(size: 13, weight: weight) }
    static func caption1(weight: Font.Weight = .bold) -> Font { .circularStd(size: 12, weight: weight) }

    // Static versions for convenience
    static let largeTitle = largeTitle()
    static let title1 = title1()
    static let title2 = title2()
    static let title3 = title3()
    static let headline = headline()
    static let body = body()
    static let subheadline = subheadline()
    static let callout = callout()
    static let footnote = footnote()
    static let caption1 = caption1()
}
