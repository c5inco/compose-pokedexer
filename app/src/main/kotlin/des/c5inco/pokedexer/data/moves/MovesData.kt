package des.c5inco.pokedexer.data.moves

import des.c5inco.pokedexer.shared.model.Move

val SampleMoves = listOf(
    Move(
        id = 1,
        name = "Pound",
        description = "A physical attack delivered with a long tail or a foreleg, etc.",
        category = "Physical",
        type = "Normal",
        pp = 35,
        power = 40,
        accuracy = 100
    ),
    Move(
        id = 2,
        name = "Karate Chop",
        description = "The foe is attacked with a sharp chop. It has a high critical-hit ratio.",
        category = "Physical",
        type = "Fighting",
        pp = 25,
        power = 50,
        accuracy = 100
    ),
    Move(
        id = 13,
        name = "Razor Wind",
        description = "Blades of wind hit the foe on the 2nd turn. It has a high critical-hit ratio.",
        category = "Special",
        type = "Flying",
        pp = 10,
        power = 80,
        accuracy = 100
    ),
    Move(
        id = 14,
        name = "Swords Dance",
        description = "A frenetic dance of fighting. It sharply raises the ATTACK stat.",
        category = "Status",
        type = "Normal",
        pp = 20,
        power = null,
        accuracy = null
    ),
    Move(
        id = 16,
        name = "Hyper Beam",
        description = "A severely damaging attack that makes the user rest on the next turn.",
        category = "Physical",
        type = "Normal",
        pp = 20,
        power = null,
        accuracy = null
    ),
)