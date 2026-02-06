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

    var categoryIcon: String {
        switch move.category.lowercased() {
        case "physical": return "ic_move_physical"
        case "special": return "ic_move_special"
        case "status": return "ic_move_status"
        default: return "dumbbell.fill"
        }
    }

    var body: some View {
        HStack(spacing: 8) {
            Text(move.name.capitalized)
                .font(AppTypography.body)
                .frame(maxWidth: .infinity, alignment: .leading)

            TypeLabel(typeName: move.type, colored: true)
                .frame(width: 80)

            Image(categoryIcon)
                .foregroundStyle(MoveCategoryColors.color(for: move.category))
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
