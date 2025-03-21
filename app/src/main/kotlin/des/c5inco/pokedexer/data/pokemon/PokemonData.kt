package des.c5inco.pokedexer.data.pokemon

import des.c5inco.pokedexer.R
import des.c5inco.pokedexer.data.abilities.SampleAbilities
import des.c5inco.pokedexer.data.items.SampleItems
import des.c5inco.pokedexer.data.moves.SampleMoves
import des.c5inco.pokedexer.model.Evolution
import des.c5inco.pokedexer.model.EvolutionTrigger
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.ui.pokedex.PokemonDetailsAbilities
import des.c5inco.pokedexer.ui.pokedex.PokemonDetailsEvolutions
import des.c5inco.pokedexer.ui.pokedex.PokemonDetailsMoves

val SamplePokemonData = listOf(
    Pokemon(
        id = 1,
        name = "Bulbasaur",
        description = "Bulbasaur can be seen napping in bright sunlight. There is a seed on its back. By soaking up the sun's rays, the seed grows progressively larger.",
        typeOfPokemon = listOf("Grass", "Poison"),
        category = "Seed",
        image = 1,
        height = 0.7,
        weight = 6.9,
        genderRate = 1,
        hp = 45,
        attack = 49,
        defense = 49,
        specialAttack = 65,
        specialDefense = 65,
        speed = 45,
        evolutionChain = listOf(
            Evolution(1, -1),
            Evolution(2, 16),
            Evolution(3, 32),
        )
    ),
    Pokemon(
        id = 2,
        name = "Ivysaur",
        description = "There is a bud on this Pokémon's back. To support its weight, Ivysaur's legs and trunk grow thick and strong. If it starts spending more time lying in the sunlight, it's a sign that the bud will bloom into a large flower soon.",
        typeOfPokemon = listOf("Grass", "Poison"),
        category = "Seed",
        image = 2,
        height = 1.0,
        weight = 13.0,
        genderRate = 1,
        hp = 60,
        attack = 62,
        defense = 63,
        specialAttack = 80,
        specialDefense = 80,
        speed = 60,
        evolutionChain = listOf(
            Evolution(1, -1),
            Evolution(2, 16),
            Evolution(3, 32),
        )
    ),
    Pokemon(
        id = 3,
        name = "Venusaur",
        description = "There is a large flower on Venusaur's back. The flower is said to take on vivid colors if it gets plenty of nutrition and sunlight. The flower's aroma soothes the emotions of people.",
        typeOfPokemon = listOf("Grass", "Poison"),
        category = "Seed",
        image = 3,
        height = 2.0,
        weight = 100.0,
        genderRate = 1,
        hp = 80,
        attack = 82,
        defense = 83,
        specialAttack = 100,
        specialDefense = 100,
        speed = 80,
        evolutionChain = listOf(
            Evolution(1, -1),
            Evolution(2, 16),
            Evolution(3, 32),
        )
    ),
    Pokemon(
        id = 4,
        name = "Charmander",
        description = "The flame that burns at the tip of its tail is an indication of its emotions. The flame wavers when Charmander is enjoying itself. If the Pokémon becomes enraged, the flame burns fiercely.",
        typeOfPokemon = listOf("Fire"),
        category = "Lizard",
        image = 4,
        height = 0.6,
        weight = 8.5,
        hp = 39,
        attack = 52,
        defense = 43,
        specialAttack = 60,
        specialDefense = 50,
        speed = 65,
        evolutionChain = listOf(
            Evolution(4, -1),
            Evolution(5, 16),
            Evolution(6, 32),
        )
    ),
    Pokemon(
        id = 5,
        name = "Charmeleon",
        description = "Charmeleon mercilessly destroys its foes using its sharp claws. If it encounters a strong foe, it turns aggressive. In this excited state, the flame at the tip of its tail flares with a bluish white color.",
        typeOfPokemon = listOf("Fire"),
        category = "Flame",
        image = 5,
        height = 1.1,
        weight = 19.0,
        hp = 58,
        attack = 64,
        defense = 58,
        specialAttack = 80,
        specialDefense = 65,
        speed = 80,
        evolutionChain = listOf(
            Evolution(4, -1),
            Evolution(5, 16),
            Evolution(6, 32),
        )
    ),
    Pokemon(
        id = 6,
        name = "Charizard",
        description = "Charizard flies around the sky in search of powerful opponents. It breathes fire of such great heat that it melts anything. However, it never turns its fiery breath on any opponent weaker than itself.",
        typeOfPokemon = listOf("Fire"),
        category = "Flame",
        image = 6,
        height = 1.7,
        weight = 90.5,
        hp = 78,
        attack = 84,
        defense = 78,
        specialAttack = 109,
        specialDefense = 85,
        speed = 100,
        evolutionChain = listOf(
            Evolution(4, -1),
            Evolution(5, 16),
            Evolution(6, 32),
        )
    ),
    Pokemon(
        id = 7,
        name = "Squirtle",
        description = "Squirtle's shell is not merely used for protection. The shell's rounded shape and the grooves on its surface help minimize resistance in water, enabling this Pokémon to swim at high speeds.",
        typeOfPokemon = listOf("Water"),
        category = "Tiny Turtle",
        image = 7,
        height = 0.5,
        weight = 9.0,
        hp = 44,
        attack = 48,
        defense = 65,
        specialAttack = 50,
        specialDefense = 64,
        speed = 43,
        evolutionChain = listOf(
            Evolution(7, -1),
            Evolution(8, 16),
            Evolution(9, 32),
        )
    ),
    Pokemon(
        id = 8,
        name = "Wartortle",
        description = "Its tail is large and covered with a rich, thick fur. The tail becomes increasingly deeper in color as Wartortle ages. The scratches on its shell are evidence of this Pokémon's toughness as a battler.",
        typeOfPokemon = listOf("Water"),
        category = "Turtle",
        image = 8,
        height = 1.0,
        weight = 22.5,
        hp = 59,
        attack = 63,
        defense = 80,
        specialAttack = 65,
        specialDefense = 80,
        speed = 58,
        evolutionChain = listOf(
            Evolution(7, -1),
            Evolution(8, 16),
            Evolution(9, 32),
        )
    ),
    Pokemon(
        id = 9,
        name = "Blastoise",
        description = "Blastoise has water spouts that protrude from its shell. The water spouts are very accurate. They can shoot bullets of water with enough accuracy to strike empty cans from a distance of over 160 feet.",
        typeOfPokemon = listOf("Water"),
        category = "Shellfish",
        image = 9,
        height = 1.6,
        weight = 85.5,
        hp = 79,
        attack = 83,
        defense = 100,
        specialAttack = 85,
        specialDefense = 105,
        speed = 78,
        evolutionChain = listOf(
            Evolution(7, -1),
            Evolution(8, 16),
            Evolution(9, 32),
        )
    ),
    Pokemon(
        id = 10,
        name = "Blastoise",
        description = "Blastoise has water spouts that protrude from its shell. The water spouts are very accurate. They can shoot bullets of water with enough accuracy to strike empty cans from a distance of over 160 feet.",
        typeOfPokemon = listOf("Psychic"),
        category = "Shellfish",
        image = 10,
        height = 1.6,
        weight = 85.5,
        hp = 79,
        attack = 83,
        defense = 100,
        specialAttack = 85,
        specialDefense = 105,
        speed = 78,
        evolutionChain = listOf(
            Evolution(7, -1),
            Evolution(8, 16),
            Evolution(9, 32),
        )
    ),
    Pokemon(
        id = 11,
        name = "Blastoise",
        description = "Blastoise has water spouts that protrude from its shell. The water spouts are very accurate. They can shoot bullets of water with enough accuracy to strike empty cans from a distance of over 160 feet.",
        typeOfPokemon = listOf("Bug"),
        category = "Shellfish",
        image = 11,
        height = 1.6,
        weight = 85.5,
        hp = 79,
        attack = 83,
        defense = 100,
        specialAttack = 85,
        specialDefense = 105,
        speed = 78,
        evolutionChain = listOf(
            Evolution(7, -1),
            Evolution(8, 16),
            Evolution(9, 32),
        )
    ),
    Pokemon(
        id = 12,
        name = "Blastoise",
        description = "Blastoise has water spouts that protrude from its shell. The water spouts are very accurate. They can shoot bullets of water with enough accuracy to strike empty cans from a distance of over 160 feet.",
        typeOfPokemon = listOf("Dragon"),
        category = "Shellfish",
        image = 12,
        height = 1.6,
        weight = 85.5,
        hp = 79,
        attack = 83,
        defense = 100,
        specialAttack = 85,
        specialDefense = 105,
        speed = 78,
        evolutionChain = listOf(
            Evolution(7, -1),
            Evolution(8, 16),
            Evolution(9, 32),
        )
    ),
    Pokemon(
        id = 13,
        name = "Blastoise",
        description = "Blastoise has water spouts that protrude from its shell. The water spouts are very accurate. They can shoot bullets of water with enough accuracy to strike empty cans from a distance of over 160 feet.",
        typeOfPokemon = listOf("Dark"),
        category = "Shellfish",
        image = 13,
        height = 1.6,
        weight = 85.5,
        hp = 79,
        attack = 83,
        defense = 100,
        specialAttack = 85,
        specialDefense = 105,
        speed = 78,
        evolutionChain = listOf(
            Evolution(7, -1),
            Evolution(8, 16),
            Evolution(9, 32),
        )
    ),
    Pokemon(
        id = 14,
        name = "Blastoise",
        description = "Blastoise has water spouts that protrude from its shell. The water spouts are very accurate. They can shoot bullets of water with enough accuracy to strike empty cans from a distance of over 160 feet.",
        typeOfPokemon = listOf("Ground"),
        category = "Shellfish",
        image = 14,
        height = 1.6,
        weight = 85.5,
        hp = 79,
        attack = 83,
        defense = 100,
        specialAttack = 85,
        specialDefense = 105,
        speed = 78,
        evolutionChain = listOf(
            Evolution(7, -1),
            Evolution(8, 16),
            Evolution(9, 32),
        )
    ),
    Pokemon(
        id = 15,
        name = "Blastoise",
        description = "Blastoise has water spouts that protrude from its shell. The water spouts are very accurate. They can shoot bullets of water with enough accuracy to strike empty cans from a distance of over 160 feet.",
        typeOfPokemon = listOf("Ice"),
        category = "Shellfish",
        image = 15,
        height = 1.6,
        weight = 85.5,
        hp = 79,
        attack = 83,
        defense = 100,
        specialAttack = 85,
        specialDefense = 105,
        speed = 78,
        evolutionChain = listOf(
            Evolution(7, -1),
            Evolution(8, 16),
            Evolution(9, 32),
        )
    ),Pokemon(
        id = 16,
        name = "Blastoise",
        description = "Blastoise has water spouts that protrude from its shell. The water spouts are very accurate. They can shoot bullets of water with enough accuracy to strike empty cans from a distance of over 160 feet.",
        typeOfPokemon = listOf("Normal"),
        category = "Shellfish",
        image = 16,
        height = 1.6,
        weight = 85.5,
        hp = 79,
        attack = 83,
        defense = 100,
        specialAttack = 85,
        specialDefense = 105,
        speed = 78,
        evolutionChain = listOf(
            Evolution(7, -1),
            Evolution(8, 16),
            Evolution(9, 32),
        )
    ),
    Pokemon(
        id = 17,
        name = "Blastoise",
        description = "Blastoise has water spouts that protrude from its shell. The water spouts are very accurate. They can shoot bullets of water with enough accuracy to strike empty cans from a distance of over 160 feet.",
        typeOfPokemon = listOf("Steel"),
        category = "Shellfish",
        image = 17,
        height = 1.6,
        weight = 85.5,
        hp = 79,
        attack = 83,
        defense = 100,
        specialAttack = 85,
        specialDefense = 105,
        speed = 78,
        evolutionChain = listOf(
            Evolution(7, -1),
            Evolution(8, 16),
            Evolution(9, 32),
        )
    ),
    Pokemon(
        id = 18,
        name = "Blastoise",
        description = "Blastoise has water spouts that protrude from its shell. The water spouts are very accurate. They can shoot bullets of water with enough accuracy to strike empty cans from a distance of over 160 feet.",
        typeOfPokemon = listOf("Poison"),
        category = "Shellfish",
        image = 18,
        height = 1.6,
        weight = 85.5,
        hp = 79,
        attack = 83,
        defense = 100,
        specialAttack = 85,
        specialDefense = 105,
        speed = 78,
        evolutionChain = listOf(
            Evolution(7, -1),
            Evolution(8, 16),
            Evolution(9, 32),
        )
    ),Pokemon(
        id = 19,
        name = "Blastoise",
        description = "Blastoise has water spouts that protrude from its shell. The water spouts are very accurate. They can shoot bullets of water with enough accuracy to strike empty cans from a distance of over 160 feet.",
        typeOfPokemon = listOf("Fairy"),
        category = "Shellfish",
        image = 19,
        height = 1.6,
        weight = 85.5,
        hp = 79,
        attack = 83,
        defense = 100,
        specialAttack = 85,
        specialDefense = 105,
        speed = 78,
        evolutionChain = listOf(
            Evolution(7, -1),
            Evolution(8, 16),
            Evolution(9, 32),
        )
    ),
    Pokemon(
        id = 20,
        name = "Blastoise",
        description = "Blastoise has water spouts that protrude from its shell. The water spouts are very accurate. They can shoot bullets of water with enough accuracy to strike empty cans from a distance of over 160 feet.",
        typeOfPokemon = listOf("Flying"),
        category = "Shellfish",
        image = 20,
        height = 1.6,
        weight = 85.5,
        hp = 79,
        attack = 83,
        defense = 100,
        specialAttack = 85,
        specialDefense = 105,
        speed = 78,
        evolutionChain = listOf(
            Evolution(7, -1),
            Evolution(8, 16),
            Evolution(9, 32),
        )
    ),
    Pokemon(
        id = 21,
        name = "Blastoise",
        description = "Blastoise has water spouts that protrude from its shell. The water spouts are very accurate. They can shoot bullets of water with enough accuracy to strike empty cans from a distance of over 160 feet.",
        typeOfPokemon = listOf("Fighting"),
        category = "Shellfish",
        image = 21,
        height = 1.6,
        weight = 85.5,
        hp = 79,
        attack = 83,
        defense = 100,
        specialAttack = 85,
        specialDefense = 105,
        speed = 78,
        evolutionChain = listOf(
            Evolution(7, -1),
            Evolution(8, 16),
            Evolution(9, 32),
        )
    ),
    Pokemon(
        id = 22,
        name = "Blastoise",
        description = "Blastoise has water spouts that protrude from its shell. The water spouts are very accurate. They can shoot bullets of water with enough accuracy to strike empty cans from a distance of over 160 feet.",
        typeOfPokemon = listOf("Ghost"),
        category = "Shellfish",
        image = 22,
        height = 1.6,
        weight = 85.5,
        hp = 79,
        attack = 83,
        defense = 100,
        specialAttack = 85,
        specialDefense = 105,
        speed = 78,
        evolutionChain = listOf(
            Evolution(7, -1),
            Evolution(8, 16),
            Evolution(9, 32),
        )
    ),
    Pokemon(
        id = 23,
        name = "Blastoise",
        description = "Blastoise has water spouts that protrude from its shell. The water spouts are very accurate. They can shoot bullets of water with enough accuracy to strike empty cans from a distance of over 160 feet.",
        typeOfPokemon = listOf("Electric"),
        category = "Shellfish",
        image = 23,
        height = 1.6,
        weight = 85.5,
        hp = 79,
        attack = 83,
        defense = 100,
        specialAttack = 85,
        specialDefense = 105,
        speed = 78,
        evolutionChain = listOf(
            Evolution(7, -1),
            Evolution(8, 16),
            Evolution(9, 32),
        )
    ),
    Pokemon(
        id = 24,
        name = "Blastoise",
        description = "Blastoise has water spouts that protrude from its shell. The water spouts are very accurate. They can shoot bullets of water with enough accuracy to strike empty cans from a distance of over 160 feet.",
        typeOfPokemon = listOf("Water"),
        category = "Shellfish",
        image = 24,
        height = 1.6,
        weight = 85.5,
        hp = 79,
        attack = 83,
        defense = 100,
        specialAttack = 85,
        specialDefense = 105,
        speed = 78,
        evolutionChain = listOf(
            Evolution(7, -1),
            Evolution(8, 16),
            Evolution(9, 32),
        )
    ),
    Pokemon(
        id = 25,
        name = "Pikachu",
        description = "Whenever Pikachu comes across something new, it blasts it with a jolt of electricity. If you come across a blackened berry, it’s evidence that this pokemon mistook the intensity of its charge",
        typeOfPokemon = listOf("Electric"),
        category = "Mouse Pokémon",
        image = 25,
        height = 0.4,
        weight = 6.0,
        genderRate = 4,
        hp = 35,
        attack = 55,
        defense = 40,
        specialAttack = 50,
        specialDefense = 50,
        speed = 90,
        evolutionChain = listOf(
            Evolution(25, -1),
            Evolution(26, -1, EvolutionTrigger.UseItem, 83),
        )
    ),
    Pokemon(
        id = 26,
        name = "Raichu",
        description = "If the electrical sacks become excessively charged, Raichu plants its tail in the ground and discharges. Scorched patches of ground will be found near this pokemon’s nest.",
        typeOfPokemon = listOf("Electric"),
        category = "Mouse Pokémon",
        image = 26,
        height = 0.8,
        weight = 30.0,
        genderRate = 4,
        hp = 60,
        attack = 90,
        defense = 55,
        specialAttack = 90,
        specialDefense = 80,
        speed = 110,
        evolutionChain = listOf(
            Evolution(25, -1),
            Evolution(26, -1, EvolutionTrigger.UseItem, 83),
        )
    ),
)

fun mapSampleEvolutionsToList(
    sample: List<Evolution>
): List<PokemonDetailsEvolutions> {
    return sample.map {
        PokemonDetailsEvolutions(
            pokemon = SamplePokemonData.first { p -> it.id == p.id },
            targetLevel = it.targetLevel,
            trigger = it.trigger,
            item = SampleItems.firstOrNull { i -> it.itemId == i.id}
        )
    }
}

fun mapSampleMovesToDetailsList(): List<PokemonDetailsMoves> {
    return SampleMoves.map {
        PokemonDetailsMoves(
            it,
            0
        )
    }
}

fun mapSampleAbilitiesToDetailsList(): List<PokemonDetailsAbilities> {
    return SampleAbilities.map {
        PokemonDetailsAbilities(
            it,
            it.id % 2 == 0
        )
    }
}

fun placeholderPokemonImage(id: Int): Int {
    val sampleImages = listOf(
        R.drawable.poke001,
        R.drawable.poke002,
        R.drawable.poke003,
        R.drawable.poke004,
        R.drawable.poke005,
        R.drawable.poke006,
        R.drawable.poke007,
        R.drawable.poke008,
        R.drawable.poke009,
        R.drawable.poke010,
        R.drawable.poke011,
        R.drawable.poke012,
        R.drawable.poke013,
        R.drawable.poke014,
        R.drawable.poke015,
        R.drawable.poke016,
        R.drawable.poke017,
        R.drawable.poke018,
        R.drawable.poke019,
        R.drawable.poke020,
        R.drawable.poke021,
        R.drawable.poke022,
        R.drawable.poke023,
        R.drawable.poke024,
        R.drawable.poke025,
        R.drawable.poke026,
    )
    return sampleImages[Integer.min(id, sampleImages.size) - 1]
}