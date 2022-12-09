package des.c5inco.pokedexer.data.pokemon

import des.c5inco.pokedexer.model.Evolution
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.ui.pokedex.PokemonDetailsEvolutions

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
)

fun mapSampleEvolutionsToList(
    sample: List<Evolution>
): List<PokemonDetailsEvolutions> {
    return sample.map {
        PokemonDetailsEvolutions(
            SamplePokemonData[it.id - 1],
            it.targetLevel
        )
    }
}