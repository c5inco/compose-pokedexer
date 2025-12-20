import SwiftUI

// MARK: - Pokemon Type Colors (matching Android)
struct PokemonColors {
    static let bug = Color(red: 0.686, green: 0.784, blue: 0.212)      // #AFC836
    static let dark = Color(red: 0.573, green: 0.596, blue: 0.643)     // #9298A4
    static let dragon = Color(red: 0.004, green: 0.502, blue: 0.780)   // #0180C7
    static let electric = Color(red: 0.941, green: 0.753, blue: 0.243) // #F0C03E
    static let fairy = Color(red: 0.933, green: 0.600, blue: 0.933)    // #EE99EE
    static let fighting = Color(red: 0.906, green: 0.263, blue: 0.278) // #E74347
    static let fire = Color(red: 0.984, green: 0.682, blue: 0.275)     // #FBAE46
    static let flying = Color(red: 0.651, green: 0.761, blue: 0.949)   // #A6C2F2
    static let ghost = Color(red: 0.467, green: 0.451, blue: 0.831)    // #7773D4
    static let grass = Color(red: 0.353, green: 0.757, blue: 0.471)    // #5AC178
    static let ground = Color(red: 0.824, green: 0.580, blue: 0.388)   // #D29463
    static let ice = Color(red: 0.549, green: 0.867, blue: 0.831)      // #8CDDD4
    static let normal = Color(red: 0.639, green: 0.643, blue: 0.620)   // #A3A49E
    static let poison = Color(red: 0.761, green: 0.380, blue: 0.831)   // #C261D4
    static let psychic = Color(red: 0.996, green: 0.624, blue: 0.573)  // #FE9F92
    static let rock = Color(red: 0.843, green: 0.804, blue: 0.565)     // #D7CD90
    static let water = Color(red: 0.259, green: 0.608, blue: 0.929)    // #429BED
    static let steel = Color(red: 0.345, green: 0.651, blue: 0.667)    // #58A6AA
}

// MARK: - Pokeball View
struct PokeballView: View {
    var tint: Color = .gray.opacity(0.1)
    
    var body: some View {
        Image("Pokeball")
            .renderingMode(.template)
            .resizable()
            .scaledToFit()
            .foregroundColor(tint)
    }
}

// MARK: - Home View
struct HomeView: View {
    @ObservedObject var viewModel: HomeViewModel
    
    var body: some View {
        ZStack {
            // Background Pokeball decoration
            VStack {
                HStack {
                    Spacer()
                    PokeballView(tint: Color.primary.opacity(0.05))
                        .frame(width: 200, height: 200)
                        .offset(x: 80, y: -60)
                }
                Spacer()
            }
            
            VStack(alignment: .leading, spacing: 24) {
                // Title
                Text("What Pokémon\nare you looking for?")
                    .font(.title)
                    .fontWeight(.bold)
                    .padding(.top, 60)
                    .accessibilityAddTraits(.isHeader)
                
                // Search bar placeholder
                HStack {
                    Image(systemName: "magnifyingglass")
                        .foregroundColor(.secondary)
                    Text("Search Pokemon, Move, Item, etc")
                        .foregroundColor(.secondary)
                    Spacer()
                }
                .padding()
                .background(Color(.systemGray6))
                .cornerRadius(25)
                
                // Menu Grid
                LazyVGrid(columns: [
                    GridItem(.flexible(), spacing: 16),
                    GridItem(.flexible(), spacing: 16)
                ], spacing: 16) {
                    NavigationLink {
                        PokemonListView()
                    } label: {
                        MenuCard(
                            title: "Pokédex",
                            icon: "circle.circle",
                            color: PokemonColors.grass
                        )
                    }
                    .accessibilityLabel("Pokédex")
                    .accessibilityHint("View all Pokémon")
                    
                    NavigationLink {
                        MovesListView()
                    } label: {
                        MenuCard(
                            title: "Moves",
                            icon: "dumbbell.fill",
                            color: PokemonColors.fighting
                        )
                    }
                    .accessibilityLabel("Moves")
                    .accessibilityHint("View all Pokémon moves")
                    
                    MenuCard(
                        title: "Type charts",
                        icon: "chart.bar.doc.horizontal",
                        color: PokemonColors.water
                    )
                    .accessibilityLabel("Type charts")
                    .accessibilityHint("View type effectiveness charts. Coming soon.")
                    
                    NavigationLink {
                        ItemsListView()
                    } label: {
                        MenuCard(
                            title: "Items",
                            icon: "bag.fill",
                            color: PokemonColors.fire
                        )
                    }
                    .accessibilityLabel("Items")
                    .accessibilityHint("View all Pokémon items")
                }
                
                Spacer()
            }
            .padding(.horizontal, 24)
        }
        .navigationTitle("")
        .navigationBarHidden(true)
    }
}

// MARK: - Menu Card
struct MenuCard: View {
    let title: String
    let icon: String
    let color: Color
    
    var body: some View {
        ZStack(alignment: .topTrailing) {
            // Background
            RoundedRectangle(cornerRadius: 20)
                .fill(color)
            
            // Icon at top-right
            Image(systemName: icon)
                .font(.system(size: 72, weight: .light))
                .foregroundColor(.white.opacity(0.3))
                .offset(x: -12, y: 16)
            
            // Title at bottom-left
            Text(title)
                .font(.headline)
                .fontWeight(.semibold)
                .foregroundColor(.white)
                .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .bottomLeading)
                .padding(16)
        }
        .frame(height: 128)
        .shadow(color: color.opacity(0.4), radius: 8, x: 0, y: 6)
    }
}

#Preview {
    NavigationStack {
        HomeView(viewModel: HomeViewModel())
    }
}
