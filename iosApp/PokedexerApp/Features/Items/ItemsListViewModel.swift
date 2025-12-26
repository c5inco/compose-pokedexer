import Foundation
import shared

@MainActor
class ItemsListViewModel: ObservableObject {
    @Published var items: [Item] = []
    @Published var isLoading = false

    private let sdk = PokedexerSDKWrapper.shared
    private var loadTask: Task<Void, Never>?

    func loadItems() async {
        isLoading = true

        loadTask = Task {
            do {
                // Check if SDK is initialized
                guard sdk.isInitialized else {
                    print("SDK not initialized yet")
                    self.isLoading = false
                    return
                }

                // Fetch items from API first
                try await sdk.updateItems()

                if let flow = sdk.getAllItems() {
                    for try await itemsList in flow.asAsyncSequence() as AsyncThrowingStream<[Item], Error> {
                        self.items = itemsList
                        self.isLoading = false
                        break
                    }
                }
            } catch {
                print("Error loading items: \(error)")
                self.isLoading = false
            }
        }
    }

    deinit {
        loadTask?.cancel()
    }
}
