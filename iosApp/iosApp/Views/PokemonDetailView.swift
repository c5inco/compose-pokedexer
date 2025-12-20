import SwiftUI
import Shared

enum DetailSection: String, CaseIterable {
    case about = "About"
    case baseStats = "Base stats"
    case evolution = "Evolution"
    case moves = "Moves"
}

struct PokemonDetailView: View {
    let pokemon: Shared.Pokemon
    @State private var selectedSection: DetailSection = .baseStats
    @Environment(\.dismiss) private var dismiss
    
    private var primaryTypeColor: Color {
        typeColor(for: pokemon.typeOfPokemon.first ?? "Normal")
    }
    
    var body: some View {
        ZStack(alignment: .top) {
            MeshGradient(
                width: 3, height: 3,
                points: [
                    [0.0, 0.0], [0.5, 0.0], [1.0, 0.0],
                    [0.0, 0.5], [0.5, 0.4], [1.0, 0.5],
                    [0.0, 1.0], [0.5, 1.0], [1.0, 1.0]
                ],
                colors: [
                    primaryTypeColor.opacity(0.7), primaryTypeColor.opacity(0.7), primaryTypeColor.opacity(0.7),
                    primaryTypeColor, primaryTypeColor, primaryTypeColor,
                    primaryTypeColor.opacity(0.9), primaryTypeColor.opacity(0.9), primaryTypeColor.opacity(0.9)
                ]
            ).ignoresSafeArea()
            
            VStack {
                Spacer()
                HStack {
                    Spacer()
                    PokeballView(tint: primaryTypeColor.opacity(0.3))
                        .frame(width: 200, height: 200)
                        .offset(x: 60, y: 50)
                }
            }.offset(y: 200)
            
            VStack(spacing: 0) {
                VStack(alignment: .leading, spacing: 8) {
                    HStack(alignment: .top) {
                        Text(pokemon.name).font(.largeTitle).fontWeight(.bold).foregroundColor(.white)
                        Spacer()
                        Text(String(format: "#%03d", pokemon.id)).font(.largeTitle).fontWeight(.bold).foregroundColor(.white.opacity(0.6))
                    }
                    HStack(spacing: 12) {
                        ForEach(pokemon.typeOfPokemon, id: \.self) { type in
                            Text(type).font(.subheadline).fontWeight(.medium).foregroundColor(.white)
                                .padding(.horizontal, 16).padding(.vertical, 6)
                                .background(Color.white.opacity(0.25)).cornerRadius(20)
                        }
                    }
                }.padding(.horizontal, 24).padding(.top, 60)
                
                AsyncImage(url: URL(string: "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/\(pokemon.id).png")) { phase in
                    if case .success(let image) = phase { image.resizable().scaledToFit() }
                    else { ProgressView().tint(.white) }
                }.frame(width: 220, height: 220).padding(.top, 20)
                
                VStack(spacing: 0) {
                    HStack(spacing: 0) {
                        ForEach(DetailSection.allCases, id: \.self) { section in
                            Button {
                                withAnimation(.easeInOut(duration: 0.2)) { selectedSection = section }
                            } label: {
                                VStack(spacing: 8) {
                                    Text(section.rawValue).font(.subheadline)
                                        .fontWeight(selectedSection == section ? .semibold : .regular)
                                        .foregroundColor(selectedSection == section ? primaryTypeColor : .secondary)
                                    Rectangle().fill(selectedSection == section ? primaryTypeColor : Color.clear)
                                        .frame(height: 3).cornerRadius(1.5)
                                }
                            }.frame(maxWidth: .infinity)
                        }
                    }.padding(.top, 16).padding(.horizontal)
                    Divider()
                    ScrollView {
                        switch selectedSection {
                        case .about: AboutSection(pokemon: pokemon, color: primaryTypeColor)
                        case .baseStats: BaseStatsSection(pokemon: pokemon, color: primaryTypeColor)
                        case .evolution: EvolutionSection(pokemon: pokemon, color: primaryTypeColor)
                        case .moves: MovesSection(pokemon: pokemon)
                        }
                    }
                    .contentMargins(.bottom, 34, for: .scrollContent)
                }
                .background(Color(.systemBackground))
                .clipShape(UnevenRoundedRectangle(topLeadingRadius: 32, topTrailingRadius: 32))
            }
        }
        .navigationBarBackButtonHidden(true)
        .toolbar {
            ToolbarItem(placement: .navigationBarLeading) {
                Button { dismiss() } label: { Image(systemName: "chevron.left").foregroundColor(.white).fontWeight(.semibold) }
            }
            ToolbarItem(placement: .navigationBarTrailing) {
                Button { } label: { Image(systemName: "heart").foregroundColor(.white) }
            }
        }
    }
    
    private func typeColor(for type: String) -> Color {
        switch type.lowercased() {
        case "grass": return PokemonColors.grass
        case "fire": return PokemonColors.fire
        case "water": return PokemonColors.water
        case "electric": return PokemonColors.electric
        case "psychic": return PokemonColors.psychic
        case "poison": return PokemonColors.poison
        case "ground": return PokemonColors.ground
        case "flying": return PokemonColors.flying
        case "bug": return PokemonColors.bug
        case "normal": return PokemonColors.normal
        case "fighting": return PokemonColors.fighting
        case "rock": return PokemonColors.rock
        case "ghost": return PokemonColors.ghost
        case "ice": return PokemonColors.ice
        case "dragon": return PokemonColors.dragon
        case "dark": return PokemonColors.dark
        case "steel": return PokemonColors.steel
        case "fairy": return PokemonColors.fairy
        default: return PokemonColors.normal
        }
    }
}

struct AboutSection: View {
    let pokemon: Shared.Pokemon
    let color: Color
    var body: some View {
        VStack(alignment: .leading, spacing: 24) {
            Text(pokemon.description_).font(.body).padding(.horizontal)
            HStack(spacing: 0) {
                VStack(alignment: .leading, spacing: 8) {
                    Text("Height").font(.subheadline).foregroundColor(.secondary)
                    Text(String(format: "%.1fm", pokemon.height)).font(.title3).fontWeight(.semibold)
                }.frame(maxWidth: .infinity, alignment: .leading)
                VStack(alignment: .leading, spacing: 8) {
                    Text("Weight").font(.subheadline).foregroundColor(.secondary)
                    Text(String(format: "%.1fkg", pokemon.weight)).font(.title3).fontWeight(.semibold)
                }.frame(maxWidth: .infinity, alignment: .leading)
            }.padding().background(color.opacity(0.1)).cornerRadius(16).padding(.horizontal)
            VStack(alignment: .leading, spacing: 12) {
                Text("Abilities").font(.headline).padding(.horizontal)
                if pokemon.abilitiesList.isEmpty {
                    Text("No abilities data available.").font(.subheadline).foregroundColor(.secondary).padding(.horizontal)
                } else {
                    ForEach(pokemon.abilitiesList, id: \.id) { ability in
                        HStack {
                            Text("Ability #\(ability.id)").font(.subheadline).fontWeight(.semibold)
                            Spacer()
                            if ability.isHidden { Text("Hidden").font(.caption).foregroundColor(.secondary) }
                        }.frame(maxWidth: .infinity, alignment: .leading)
                            .padding().background(color.opacity(0.1)).cornerRadius(12).padding(.horizontal)
                    }
                }
            }
            Spacer(minLength: 32)
        }.padding(.top, 24)
    }
}

struct BaseStatsSection: View {
    let pokemon: Shared.Pokemon
    let color: Color
    var body: some View {
        VStack(spacing: 16) {
            StatRowView(name: "HP", value: Int(pokemon.hp), color: color)
            StatRowView(name: "Attack", value: Int(pokemon.attack), color: color)
            StatRowView(name: "Defense", value: Int(pokemon.defense), color: color)
            StatRowView(name: "Sp. Atk", value: Int(pokemon.specialAttack), color: color)
            StatRowView(name: "Sp. Def", value: Int(pokemon.specialDefense), color: color)
            StatRowView(name: "Speed", value: Int(pokemon.speed), color: color)
            Spacer(minLength: 32)
        }.padding(.top, 24).padding(.horizontal)
    }
}

struct StatRowView: View {
    let name: String
    let value: Int
    let color: Color
    var body: some View {
        HStack(spacing: 16) {
            Text(name).font(.subheadline).foregroundColor(.secondary).frame(width: 70, alignment: .leading)
            Text("\(value)").font(.subheadline).fontWeight(.semibold).frame(width: 36, alignment: .trailing).monospacedDigit()
            GeometryReader { geo in
                ZStack(alignment: .leading) {
                    Capsule().fill(color.opacity(0.2))
                    Capsule().fill(color).frame(width: geo.size.width * CGFloat(value) / 255.0)
                }
            }.frame(height: 6)
        }
    }
}

struct EvolutionSection: View {
    let pokemon: Shared.Pokemon
    let color: Color
    
    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            Text("Evolution chain").font(.headline).padding(.horizontal)
            if pokemon.evolutionChain.isEmpty || pokemon.evolutionChain.count < 2 {
                Text("This Pokémon does not evolve.").font(.subheadline).foregroundColor(.secondary).padding(.horizontal)
            } else {
                let chain = Array(pokemon.evolutionChain)
                VStack(spacing: 12) {
                    ForEach(0..<chain.count - 1, id: \.self) { idx in
                        let evo = chain[idx]
                        let next = chain[idx + 1]
                        EvolutionRow(fromId: Int(evo.id), toId: Int(next.id), targetLevel: Int(next.targetLevel), color: color)
                    }
                }
            }
            Spacer(minLength: 32)
        }.padding(.top, 24)
    }
}

struct EvolutionRow: View {
    let fromId: Int
    let toId: Int
    let targetLevel: Int
    let color: Color
    
    var body: some View {
        HStack {
            EvoPokemon(id: fromId)
            Spacer()
            VStack(spacing: 4) {
                Image(systemName: "arrow.right").foregroundColor(color).fontWeight(.semibold)
                Text(targetLevel > 0 ? "Lvl \(targetLevel)" : "Evolve").font(.caption).foregroundColor(.secondary)
            }
            Spacer()
            EvoPokemon(id: toId)
        }.padding().background(Color(.systemGray6)).cornerRadius(16).padding(.horizontal)
    }
}

struct EvoPokemon: View {
    let id: Int
    var body: some View {
        VStack {
            AsyncImage(url: URL(string: "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/\(id).png")) { phase in
                if case .success(let img) = phase { img.resizable().scaledToFit() }
                else { Circle().fill(Color.gray.opacity(0.2)) }
            }.frame(width: 80, height: 80).background(Color.gray.opacity(0.1)).clipShape(Circle())
            Text("#\(id)").font(.caption).fontWeight(.medium)
        }
    }
}

struct MovesSection: View {
    let pokemon: Shared.Pokemon
    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            if pokemon.movesList.isEmpty {
                Text("No moves data available.").font(.subheadline).foregroundColor(.secondary).padding(.horizontal)
            } else {
                ForEach(pokemon.movesList.prefix(20), id: \.id) { move in
                    HStack {
                        Text("Move #\(move.id)").font(.subheadline)
                        Spacer()
                        if move.targetLevel > 0 {
                            Text("Lvl \(move.targetLevel)").font(.caption).foregroundColor(.secondary)
                        }
                    }.padding().background(Color(.systemGray6)).cornerRadius(12).padding(.horizontal)
                }
            }
            Spacer(minLength: 32)
        }.padding(.top, 24)
    }
}
