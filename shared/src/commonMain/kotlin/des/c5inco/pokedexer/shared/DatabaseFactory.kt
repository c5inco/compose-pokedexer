package des.c5inco.pokedexer.shared

import des.c5inco.pokedexer.shared.data.PokemonDatabase

interface DatabaseFactory {
    fun createDatabase(): PokemonDatabase
}
