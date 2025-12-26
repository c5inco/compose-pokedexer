import SwiftUI
import shared

struct PokemonImage: View {
    let pokemon: Pokemon
    var size: CGFloat = 80

    var body: some View {
        AsyncImage(url: pokemon.imageURL) { phase in
            switch phase {
            case .empty:
                ProgressView()
                    .frame(width: size, height: size)
            case .success(let image):
                image
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: size, height: size)
            case .failure:
                Image(systemName: "photo")
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: size, height: size)
                    .foregroundColor(.gray)
            @unknown default:
                EmptyView()
            }
        }
    }
}

struct ItemImage: View {
    let item: Item
    var size: CGFloat = 56

    var body: some View {
        AsyncImage(url: item.spriteURL) { phase in
            switch phase {
            case .empty:
                ProgressView()
                    .frame(width: size, height: size)
            case .success(let image):
                image
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: size, height: size)
            case .failure:
                Image(systemName: "cube.box")
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: size, height: size)
                    .foregroundColor(.gray)
            @unknown default:
                EmptyView()
            }
        }
    }
}
