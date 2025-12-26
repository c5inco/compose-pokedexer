import SwiftUI

/// Decorative Pokéball background similar to Android implementation
struct PokeballBackground: View {
    var opacity: Double = 0.05
    var tint: Color = .white

    var body: some View {
        GeometryReader { geometry in
            Canvas { context, size in
                let center = CGPoint(x: size.width * 0.8, y: size.height * 0.1)
                let radius = min(size.width, size.height) * 0.3

                // Top half circle
                var topPath = Path()
                topPath.addArc(center: center,
                              radius: radius,
                              startAngle: .degrees(180),
                              endAngle: .degrees(0),
                              clockwise: false)

                context.fill(topPath, with: .color(tint.opacity(opacity)))

                // Bottom half circle
                var bottomPath = Path()
                bottomPath.addArc(center: center,
                                 radius: radius,
                                 startAngle: .degrees(0),
                                 endAngle: .degrees(180),
                                 clockwise: false)

                context.fill(bottomPath, with: .color(tint.opacity(opacity * 0.7)))

                // Center band
                let bandHeight = radius * 0.15
                var bandPath = Path()
                bandPath.addRect(CGRect(x: center.x - radius,
                                       y: center.y - bandHeight,
                                       width: radius * 2,
                                       height: bandHeight * 2))

                context.fill(bandPath, with: .color(tint.opacity(opacity)))

                // Center circle
                var centerCircle = Path()
                centerCircle.addEllipse(in: CGRect(x: center.x - radius * 0.25,
                                                   y: center.y - radius * 0.25,
                                                   width: radius * 0.5,
                                                   height: radius * 0.5))

                context.fill(centerCircle, with: .color(tint.opacity(opacity * 1.5)))
            }
        }
    }
}
