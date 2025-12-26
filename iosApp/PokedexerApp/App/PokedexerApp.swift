import SwiftUI

@main
struct PokedexerApp: App {
    @StateObject private var coordinator = NavigationCoordinator()
    @StateObject private var appViewModel = AppViewModel()

    var body: some Scene {
        WindowGroup {
            ContentView()
                .environmentObject(coordinator)
                .environmentObject(appViewModel)
                .task {
                    await appViewModel.syncData()
                }
        }
    }
}
