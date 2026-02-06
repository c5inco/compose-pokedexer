import SwiftUI

@main
struct PokedexerApp: App {
    @StateObject private var coordinator = NavigationCoordinator()
    @StateObject private var appViewModel = AppViewModel()
    @StateObject private var sdkWrapper = PokedexerSDKWrapper.shared

    var body: some Scene {
        WindowGroup {
            ContentView()
                .environmentObject(coordinator)
                .environmentObject(appViewModel)
                .environmentObject(sdkWrapper)
                .task {
                    // Skip SDK initialization in Xcode Previews
                    guard ProcessInfo.processInfo.environment["XCODE_RUNNING_FOR_PLAYGROUNDS"] == nil else {
                        return
                    }

                    // Initialize SDK asynchronously (runs on background thread)
                    await sdkWrapper.initialize()

                    // Once SDK is ready, sync data
                    await appViewModel.syncData()
                }
        }
    }
}
