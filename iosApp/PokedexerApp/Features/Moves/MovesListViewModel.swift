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
                // Fetch moves from API first
                try await sdk.updateMoves()
                
                for try await movesList in sdk.getAllMoves().asAsyncSequence() as AsyncThrowingStream<[Move], Error> {
                    self.moves = movesList
                    self.isLoading = false
                    break
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
