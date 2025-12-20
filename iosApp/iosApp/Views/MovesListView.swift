import SwiftUI
import Shared

struct MovesListView: View {
    @StateObject private var viewModel = MovesListViewModel()
    
    var body: some View {
        Group {
            if viewModel.isLoading && viewModel.moves.isEmpty {
                VStack(spacing: 16) {
                    ProgressView()
                        .scaleEffect(1.5)
                    Text("Loading Moves...")
                        .font(.subheadline)
                        .foregroundColor(.secondary)
                }
            } else if let error = viewModel.error, viewModel.moves.isEmpty {
                VStack(spacing: 16) {
                    Image(systemName: "exclamationmark.triangle")
                        .font(.system(size: 48))
                        .foregroundColor(.orange)
                    Text("Error Loading Moves")
                        .font(.headline)
                    Text(error)
                        .font(.subheadline)
                        .foregroundColor(.secondary)
                        .multilineTextAlignment(.center)
                    Button("Retry") {
                        Task { await viewModel.loadMoves() }
                    }
                    .buttonStyle(.borderedProminent)
                }
                .padding()
            } else {
                VStack(spacing: 0) {
                    // Header
                    HStack {
                        Text("Name")
                            .frame(maxWidth: .infinity, alignment: .leading)
                        Text("Type")
                            .frame(width: 75, alignment: .center)
                        Text("Cat.")
                            .frame(width: 40, alignment: .center)
                        Text("Pwr")
                            .frame(width: 36, alignment: .trailing)
                        Text("Acc")
                            .frame(width: 36, alignment: .trailing)
                    }
                    .font(.caption)
                    .fontWeight(.semibold)
                    .foregroundColor(.secondary)
                    .padding(.horizontal)
                    .padding(.vertical, 12)
                    .background(Color(.systemGray6))
                    
                    // List
                    List {
                        ForEach(viewModel.moves, id: \.id) { move in
                            MoveRowView(move: move)
                                .listRowInsets(EdgeInsets(top: 6, leading: 16, bottom: 6, trailing: 16))
                                .listRowSeparator(.hidden)
                        }
                    }
                    .listStyle(.plain)
                }
            }
        }
        .navigationTitle("Moves")
        .task {
            await viewModel.loadMoves()
        }
    }
}

struct MoveRowView: View {
    let move: Shared.Move
    
    var body: some View {
        HStack {
            Text(formatName(move.name))
                .font(.subheadline)
                .lineLimit(1)
                .frame(maxWidth: .infinity, alignment: .leading)
            
            MoveTypeBadge(type: move.type)
                .frame(width: 75)
            
            MoveCategoryBadge(category: move.category)
                .frame(width: 40)
            
            Text(move.power?.description ?? "—")
                .font(.caption)
                .fontWeight(.medium)
                .monospacedDigit()
                .frame(width: 36, alignment: .trailing)
            
            Text(move.accuracy?.description ?? "—")
                .font(.caption)
                .fontWeight(.medium)
                .monospacedDigit()
                .frame(width: 36, alignment: .trailing)
        }
        .padding(.vertical, 8)
        .padding(.horizontal, 12)
        .background(Color(.systemGray6))
        .cornerRadius(12)
    }
    
    private func formatName(_ name: String) -> String {
        name.split(separator: "-")
            .map { $0.capitalized }
            .joined(separator: " ")
    }
}

struct MoveTypeBadge: View {
    let type: String
    
    var body: some View {
        Text(type)
            .font(.caption2)
            .fontWeight(.bold)
            .foregroundColor(.white)
            .padding(.horizontal, 8)
            .padding(.vertical, 4)
            .frame(maxWidth: .infinity)
            .background(typeColor(for: type))
            .cornerRadius(8)
    }
    
    private func typeColor(for type: String) -> Color {
        switch type.lowercased() {
        case "grass": return PokemonColors.grass
        case "fire": return PokemonColors.fire
        case "water": return PokemonColors.water
        case "electric": return PokemonColors.electric
        case "psychic": return PokemonColors.psychic
        case "poison": return PokemonColors.poison
        case "ground": return PokemonColors.ground
        case "flying": return PokemonColors.flying
        case "bug": return PokemonColors.bug
        case "normal": return PokemonColors.normal
        case "fighting": return PokemonColors.fighting
        case "rock": return PokemonColors.rock
        case "ghost": return PokemonColors.ghost
        case "ice": return PokemonColors.ice
        case "dragon": return PokemonColors.dragon
        case "dark": return PokemonColors.dark
        case "steel": return PokemonColors.steel
        case "fairy": return PokemonColors.fairy
        default: return PokemonColors.normal
        }
    }
}

struct MoveCategoryBadge: View {
    let category: String
    
    var body: some View {
        Image(systemName: iconName)
            .font(.system(size: 14, weight: .semibold))
            .foregroundColor(iconColor)
            .frame(width: 28, height: 28)
            .background(iconColor.opacity(0.15))
            .cornerRadius(8)
    }
    
    private var iconName: String {
        switch category.lowercased() {
        case "physical": return "burst.fill"
        case "special": return "sparkles"
        default: return "ellipsis.circle"
        }
    }
    
    private var iconColor: Color {
        switch category.lowercased() {
        case "physical": return PokemonColors.fighting
        case "special": return PokemonColors.flying
        default: return .gray
        }
    }
}

#Preview {
    NavigationStack {
        MovesListView()
    }
}
