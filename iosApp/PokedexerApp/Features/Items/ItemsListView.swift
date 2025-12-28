import SwiftUI
import Shared

struct ItemsListView: View {
    @StateObject private var viewModel = ItemsListViewModel()

    var body: some View {
        Group {
            if viewModel.isLoading {
                LoadingView()
            } else {
                ScrollView {
                    LazyVStack(spacing: 8) {
                        ForEach(Array(viewModel.items.enumerated()), id: \.element.id) { index, item in
                            ItemCard(item: item, index: index)
                        }
                    }
                    .padding()
                }
            }
        }
        .navigationTitle("Items")
        .navigationBarTitleDisplayMode(.large)
        .task {
            await viewModel.loadItems()
        }
    }
}

struct ItemCard: View {
    let item: Item
    let index: Int

    var body: some View {
        HStack(spacing: 16) {
            ItemImage(item: item, size: 45)

            VStack(alignment: .leading, spacing: 4) {
                Text(item.name.capitalized)
                    .font(.headline)

                Text(item.desc)
                    .font(.caption)
                    .foregroundColor(.secondary)
            }

            Spacer()
        }
        .padding()
        .background(
            index % 2 == 0
                ? Color(.systemBackground)
                : Color(.secondarySystemBackground)
        )
        .cornerRadius(12)
    }
}
