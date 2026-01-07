import SwiftUI
import Kingfisher

@main
struct PokedexerApp: App {
    @StateObject private var coordinator = NavigationCoordinator()
    @StateObject private var appViewModel = AppViewModel()
    @StateObject private var sdkWrapper = PokedexerSDKWrapper.shared

    init() {
        // Configure Kingfisher image cache
        let cache = ImageCache.default

        // Set memory cache limit to ~50MB
        cache.memoryStorage.config.totalCostLimit = 50 * 1024 * 1024

        // Set disk cache limit to ~200MB
        cache.diskStorage.config.sizeLimit = 200 * 1024 * 1024

        // Set cache expiration to 7 days
        cache.diskStorage.config.expiration = .days(7)
    }

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
