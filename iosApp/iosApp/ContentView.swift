import SwiftUI
import shared

struct ContentView: View {
    @StateObject private var viewModel = PokemonListViewModel()
    
    var body: some View {
        NavigationStack {
            Group {
                if viewModel.isLoading {
                    ProgressView("Loading Pokemon...")
                        .progressViewStyle(CircularProgressViewStyle())
                } else if let error = viewModel.error {
                    VStack {
                        Image(systemName: "exclamationmark.triangle")
                            .font(.largeTitle)
                            .foregroundColor(.red)
                        Text("Error: \(error)")
                            .multilineTextAlignment(.center)
                            .padding()
                        Button("Retry") {
                            viewModel.loadPokemon()
                        }
                    }
                } else {
                    PokemonListView(pokemon: viewModel.pokemon)
                }
            }
            .navigationTitle("Pokedexer")
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button(action: {
                        viewModel.loadPokemon()
                    }) {
                        Image(systemName: "arrow.clockwise")
                    }
                }
            }
        }
        .onAppear {
            viewModel.loadPokemon()
        }
    }
}

#Preview {
    ContentView()
}
