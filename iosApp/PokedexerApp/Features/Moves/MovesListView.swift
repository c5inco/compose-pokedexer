import SwiftUI
import Shared

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
        .font(.caption.bold())
        .foregroundColor(.secondary)
        .padding(.vertical, 4)
    }
}

struct MoveRow: View {
    let move: Move

    var categoryIcon: String {
        switch move.category.lowercased() {
        case "physical": return "flame.fill"
        case "special": return "sparkles"
        case "status": return "star.fill"
        default: return "circle.fill"
        }
    }

    var body: some View {
        HStack(spacing: 8) {
            Text(move.name.capitalized)
                .font(.system(size: 14))
                .frame(maxWidth: .infinity, alignment: .leading)

            TypeLabel(typeName: move.type, size: .small)
                .frame(width: 80)

            Image(systemName: categoryIcon)
                .font(.system(size: 12))
                .foregroundColor(MoveCategoryColors.color(for: move.category))
                .frame(width: 48)

            Text(move.powerDisplay)
                .font(.system(size: 14))
                .frame(width: 45, alignment: .trailing)

            Text(move.accuracyDisplay)
                .font(.system(size: 14))
                .frame(width: 45, alignment: .trailing)
        }
        .padding(.vertical, 4)
    }
}
