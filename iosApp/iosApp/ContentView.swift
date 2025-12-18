import SwiftUI

struct ContentView: View {
    @StateObject private var viewModel = HomeViewModel()
    
    var body: some View {
        NavigationStack {
            HomeView(viewModel: viewModel)
        }
    }
}

#Preview {
    ContentView()
}

