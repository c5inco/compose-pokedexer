import Foundation
import shared

@MainActor
class MovesListViewModel: ObservableObject {
    @Published var moves: [Move] = []
    @Published var isLoading = false

    private let sdk = PokedexerSDKWrapper.shared
    private var loadTask: Task<Void, Never>?

    func loadMoves() async {
        isLoading = true

        loadTask = Task {
            do {
                // Check if SDK is initialized
                guard sdk.isInitialized else {
                    print("SDK not initialized yet")
                    self.isLoading = false
                    return
                }

                // Fetch moves from API first
                try await sdk.updateMoves()

                if let flow = sdk.getAllMoves() {
                    for try await movesList in flow.asAsyncSequence() as AsyncThrowingStream<[Move], Error> {
                        self.moves = movesList
                        self.isLoading = false
                        break
                    }
                }
            } catch {
                print("Error loading moves: \(error)")
                self.isLoading = false
            }
        }
    }

    deinit {
        loadTask?.cancel()
    }
}
