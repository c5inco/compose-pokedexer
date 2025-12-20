# Compose Pokedexer
![License-MIT](https://img.shields.io/badge/License-MIT-red.svg)

Adapted fork of original [compose-pokedex](https://github.com/zsoltk/compose-pokedex) project

Notable changes:
- Upgraded dependencies: Compose alpha -> stable+, Material2 -> Material3
- Swapped out [composer-router](https://github.com/zsoltk/compose-router) for [Compose Navigation](https://developer.android.com/jetpack/compose/navigation)
- Added [Accompanist](https://github.com/google/accompanist) for edge-to-edge UI treatment
- Querying pokemon data via [GraphQL from PokeApi](https://pokeapi.co/docs/graphql), storing in local Room database
- [Metro](https://zacsweers.github.io/metro/latest/) for dependency injection, as well as ViewModels
- Coil for image loading
- Simple use of RuntimeShader of pager color transition (for devices API 33+)
- Dynamic theming for app and Pokemon types, powered by [Material Kolor](https://github.com/jordond/MaterialKolor)
- Many animations (loading, infinite, shared element and navigation transitions)

## Screenshots

![Screenshots of app](assets/jul2024-screenshots.png "Screenshots")

## Original design

Adapted from [Pokedex App design](https://dribbble.com/shots/6545819-Pokedex-App) by [Saepul Nahwan](https://dribbble.com/saepulnahwan23).

Notable additions:
- Dark theme
- Designs for all tabs on details screen
- Designs for Move, Abilities, and Items screens
- Designs for search

## License

All the code available under the MIT license. See [LICENSE](LICENSE).
