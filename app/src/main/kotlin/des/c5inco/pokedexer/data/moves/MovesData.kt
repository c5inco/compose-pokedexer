package des.c5inco.pokedexer.data.moves

import des.c5inco.pokedexer.model.Move

val SampleMoves = listOf(
    Move(
        id = 1,
        name = "Pound",
        description = "",
        category = "Physical",
        type = "Normal",
        pp = 35,
        power = 40,
        accuracy = 100
    ),
    Move(
        id = 2,
        name = "Karate Chop",
        description = "",
        category = "Physical",
        type = "Fighting",
        pp = 25,
        power = 50,
        accuracy = 100
    ),
    Move(
        id = 13,
        name = "Razor Wind",
        description = "",
        category = "Special",
        type = "Flying",
        pp = 10,
        power = 80,
        accuracy = 100
    ),
    Move(
        id = 14,
        name = "Swords Dance",
        description = "",
        category = "Status",
        type = "Normal",
        pp = 20,
        power = null,
        accuracy = null
    ),
)