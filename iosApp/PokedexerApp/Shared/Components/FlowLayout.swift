import SwiftUI

struct FlowLayout: Layout {
    var spacing: CGFloat = 8

    func sizeThatFits(proposal: ProposedViewSize, subviews: Subviews, cache: inout ()) -> CGSize {
        let sizes = subviews.map { $0.sizeThatFits(.unspecified) }
        var totalHeight: CGFloat = 0
        var totalWidth: CGFloat = 0
        var currentLineWidth: CGFloat = 0
        var currentLineHeight: CGFloat = 0

        let maxWidth = proposal.width ?? .infinity

        for size in sizes {
            if currentLineWidth + size.width + spacing > maxWidth {
                totalHeight += currentLineHeight + spacing
                totalWidth = max(totalWidth, currentLineWidth)
                currentLineWidth = size.width
                currentLineHeight = size.height
            } else {
                currentLineWidth += (currentLineWidth == 0 ? 0 : spacing) + size.width
                currentLineHeight = max(currentLineHeight, size.height)
            }
        }

        totalHeight += currentLineHeight
        totalWidth = max(totalWidth, currentLineWidth)

        return CGSize(width: totalWidth, height: totalHeight)
    }

    func placeSubviews(in bounds: CGRect, proposal: ProposedViewSize, subviews: Subviews, cache: inout ()) {
        var x = bounds.minX
        var y = bounds.minY
        var currentLineHeight: CGFloat = 0

        for subview in subviews {
            let size = subview.sizeThatFits(.unspecified)
            if x + size.width > bounds.maxX {
                x = bounds.minX
                y += currentLineHeight + spacing
                currentLineHeight = 0
            }

            subview.place(at: CGPoint(x: x, y: y), proposal: .unspecified)
            x += size.width + spacing
            currentLineHeight = max(currentLineHeight, size.height)
        }
    }
}
