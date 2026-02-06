import Shared
import SwiftUI

struct MovesListView: View {
    @StateObject private var viewModel = MovesListViewModel()

    var body: some View {
        Group {
            if viewModel.isLoading {
                LoadingView()
            } else {
                List {
                    Section {
                        ForEach(viewModel.moves, id: \.id) { move in
                            MoveRow(move: move)
                        }
                    } header: {
                        MoveTableHeader()
                    }
                }
                .listStyle(.plain)
            }
        }
        .navigationTitle("Moves")
        .navigationBarTitleDisplayMode(.large)
        .task {
            await viewModel.loadMoves()
        }
    }
}

struct MoveTableHeader: View {
    var body: some View {
        HStack(spacing: 8) {
            Text("Name")
                .frame(maxWidth: .infinity, alignment: .leading)

            Text("Type")
                .frame(width: 80)

            Text("Cat")
                .frame(width: 48)

            Text("Pwr")
                .frame(width: 45, alignment: .trailing)

            Text("Acc")
                .frame(width: 45, alignment: .trailing)
        }
        .font(AppTypography.caption1)
        .foregroundColor(.secondary)
        .padding(.vertical, 4)
    }
}

struct MoveRow: View {
    let move: Move

    var body: some View {
        MoveRowContent(move: move)
            .pokemonTheme(types: [move.type])
    }
}

private struct MoveRowContent: View {
    let move: Move
    @Environment(\.pokemonTheme) var theme

    var body: some View {
        HStack(spacing: 8) {
            Text(move.name.capitalized)
                .font(AppTypography.body)
                .frame(maxWidth: .infinity, alignment: .leading)
                .foregroundColor(theme.onSurface)

            TypeLabel(typeName: move.type, size: .small, colored: true)
                .frame(width: 80)

            CategoryIcon(category: move.category)
                .frame(width: 24, height: 24)
                .frame(width: 48)

            Text(move.powerDisplay)
                .font(AppTypography.subheadline)
                .frame(width: 45, alignment: .trailing)

            Text(move.accuracyDisplay)
                .font(AppTypography.subheadline)
                .frame(width: 45, alignment: .trailing)
        }
    }
}
