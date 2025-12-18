import SwiftUI

struct HomeView: View {
    @ObservedObject var viewModel: HomeViewModel
    
    var body: some View {
        VStack(spacing: 24) {
            Text("Pokedexer")
                .font(.largeTitle)
                .fontWeight(.bold)
            
            Text(viewModel.greeting)
                .font(.subheadline)
                .foregroundColor(.secondary)
            
            LazyVGrid(columns: [
                GridItem(.flexible()),
                GridItem(.flexible())
            ], spacing: 16) {
                NavigationLink {
                    PokemonListView()
                } label: {
                    MenuCardContent(
                        title: "Pokédex",
                        icon: "list.bullet",
                        color: .green
                    )
                }
                
                MenuCard(
                    title: "Moves",
                    icon: "bolt.fill",
                    color: .red
                ) {
                    // Navigate to Moves
                }
                
                MenuCard(
                    title: "Items",
                    icon: "bag.fill",
                    color: .orange
                ) {
                    // Navigate to Items
                }
                
                MenuCard(
                    title: "Abilities",
                    icon: "sparkles",
                    color: .purple
                ) {
                    // Navigate to Abilities
                }
            }
            .padding(.horizontal)
            
            Spacer()
        }
        .padding(.top, 40)
        .navigationTitle("")
        .navigationBarHidden(true)
    }
}

struct MenuCardContent: View {
    let title: String
    let icon: String
    let color: Color
    
    var body: some View {
        VStack(spacing: 12) {
            Image(systemName: icon)
                .font(.system(size: 32))
                .foregroundColor(.white)
            
            Text(title)
                .font(.headline)
                .foregroundColor(.white)
        }
        .frame(maxWidth: .infinity)
        .frame(height: 120)
        .background(color.gradient)
        .cornerRadius(16)
    }
}

struct MenuCard: View {
    let title: String
    let icon: String
    let color: Color
    let action: () -> Void
    
    var body: some View {
        Button(action: action) {
            MenuCardContent(title: title, icon: icon, color: color)
        }
    }
}

#Preview {
    NavigationStack {
        HomeView(viewModel: HomeViewModel())
    }
}
