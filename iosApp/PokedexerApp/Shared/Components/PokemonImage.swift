import SwiftUI
import Shared
import Kingfisher

struct PokemonImage: View {
    let pokemon: Pokemon
    var size: CGFloat = 80

    var body: some View {
        KFImage.url(pokemon.imageURL)
            .placeholder {
                ProgressView()
                    .frame(width: size, height: size)
            }
            .fade(duration: 0.25)
            .resizable()
            .aspectRatio(contentMode: .fit)
            .frame(width: size, height: size)
    }
}

struct ItemImage: View {
    let item: Item
    var size: CGFloat = 45

    var body: some View {
        Group {
            if let uiImage = item.bundleImage() {
                Image(uiImage: uiImage)
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: size, height: size)
            } else {
                // Fallback to default placeholder if bundle image not found
                Image(systemName: "cube.box")
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: size, height: size)
                    .foregroundColor(.gray)
            }
        }
    }
}
