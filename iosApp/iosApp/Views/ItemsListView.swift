import SwiftUI
import Shared

struct ItemsListView: View {
    @StateObject private var viewModel = ItemsListViewModel()
    
    var body: some View {
        Group {
            if viewModel.isLoading && viewModel.items.isEmpty {
                VStack(spacing: 16) {
                    ProgressView()
                        .scaleEffect(1.5)
                    Text("Loading Items...")
                        .font(.subheadline)
                        .foregroundColor(.secondary)
                }
            } else if let error = viewModel.error, viewModel.items.isEmpty {
                VStack(spacing: 16) {
                    Image(systemName: "exclamationmark.triangle")
                        .font(.system(size: 48))
                        .foregroundColor(.orange)
                    Text("Error Loading Items")
                        .font(.headline)
                    Text(error)
                        .font(.subheadline)
                        .foregroundColor(.secondary)
                        .multilineTextAlignment(.center)
                    Button("Retry") {
                        Task { await viewModel.loadItems() }
                    }
                    .buttonStyle(.borderedProminent)
                }
                .padding()
            } else {
                ScrollView {
                    LazyVStack(spacing: 12) {
                        ForEach(viewModel.items, id: \.id) { item in
                            ItemRow(item: item)
                        }
                    }
                    .padding()
                }
            }
        }
        .navigationTitle("Items")
        .task {
            await viewModel.loadItems()
        }
    }
}

struct ItemRow: View {
    let item: Shared.Item
    
    private var spriteURL: URL? {
        // Construct PokeAPI sprite URL from sprite name
        let spriteName = item.sprite.hasPrefix("http") ? 
            extractSpriteName(from: item.sprite) : item.sprite
        return URL(string: "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/\(spriteName).png")
    }
    
    private func extractSpriteName(from url: String) -> String {
        // Extract sprite name from full URL if present
        if url.contains("sprites/items/") {
            return url.components(separatedBy: "sprites/items/").last?
                .replacingOccurrences(of: ".png", with: "") ?? item.sprite
        }
        return item.sprite
    }
    
    var body: some View {
        HStack(spacing: 16) {
            // Item sprite
            AsyncImage(url: spriteURL) { phase in
                switch phase {
                case .success(let image):
                    image
                        .resizable()
                        .interpolation(.none)
                        .scaledToFit()
                case .failure:
                    Image(systemName: "bag.fill")
                        .foregroundColor(.orange)
                default:
                    ProgressView()
                }
            }
            .frame(width: 48, height: 48)
            .background(Color.orange.opacity(0.1))
            .cornerRadius(12)
            
            VStack(alignment: .leading, spacing: 4) {
                Text(formatItemName(item.name))
                    .font(.headline)
                    .lineLimit(1)
                
                Text(item.description_)
                    .font(.caption)
                    .foregroundColor(.secondary)
                    .lineLimit(2)
            }
            
            Spacer()
        }
        .padding()
        .background(Color(.systemGray6))
        .cornerRadius(16)
    }
    
    private func formatItemName(_ name: String) -> String {
        name.split(separator: "-")
            .map { $0.capitalized }
            .joined(separator: " ")
    }
}

#Preview {
    NavigationStack {
        ItemsListView()
    }
}

