package des.c5inco.pokedexer.data

import des.c5inco.pokedexer.model.Ability as AndroidAbility
import des.c5inco.pokedexer.model.Evolution as AndroidEvolution
import des.c5inco.pokedexer.model.EvolutionTrigger as AndroidEvolutionTrigger
import des.c5inco.pokedexer.model.Item as AndroidItem
import des.c5inco.pokedexer.model.Move as AndroidMove
import des.c5inco.pokedexer.model.Pokemon as AndroidPokemon
import des.c5inco.pokedexer.model.PokemonAbility as AndroidPokemonAbility
import des.c5inco.pokedexer.model.PokemonMove as AndroidPokemonMove
import des.c5inco.pokedexer.shared.model.Ability as SharedAbility
import des.c5inco.pokedexer.shared.model.Evolution as SharedEvolution
import des.c5inco.pokedexer.shared.model.EvolutionTrigger as SharedEvolutionTrigger
import des.c5inco.pokedexer.shared.model.Item as SharedItem
import des.c5inco.pokedexer.shared.model.Move as SharedMove
import des.c5inco.pokedexer.shared.model.Pokemon as SharedPokemon
import des.c5inco.pokedexer.shared.model.PokemonAbility as SharedPokemonAbility
import des.c5inco.pokedexer.shared.model.PokemonMove as SharedPokemonMove

/**
 * Adapters to convert between shared KMP models and Android-specific models.
 * This allows gradual migration while maintaining compatibility with existing UI code.
 */

// Pokemon adapters
fun SharedPokemon.toAndroid(): AndroidPokemon = AndroidPokemon(
    id = id,
    name = name,
    description = description,
    typeOfPokemon = typeOfPokemon,
    category = category,
    image = image,
    height = height,
    weight = weight,
    genderRate = genderRate,
    generationId = generationId,
    hp = hp,
    attack = attack,
    defense = defense,
    specialAttack = specialAttack,
    specialDefense = specialDefense,
    speed = speed,
    evolutionChain = evolutionChain.map { it.toAndroid() },
    movesList = movesList.map { it.toAndroid() },
    abilitiesList = abilitiesList.map { it.toAndroid() }
)

fun AndroidPokemon.toShared(): SharedPokemon = SharedPokemon(
    id = id,
    name = name,
    description = description,
    typeOfPokemon = typeOfPokemon,
    category = category,
    image = image,
    height = height,
    weight = weight,
    genderRate = genderRate,
    generationId = generationId,
    hp = hp,
    attack = attack,
    defense = defense,
    specialAttack = specialAttack,
    specialDefense = specialDefense,
    speed = speed,
    evolutionChain = evolutionChain.map { it.toShared() },
    movesList = movesList.map { it.toShared() },
    abilitiesList = abilitiesList.map { it.toShared() }
)

// Evolution adapters
fun SharedEvolution.toAndroid(): AndroidEvolution = AndroidEvolution(
    id = id,
    targetLevel = targetLevel,
    trigger = trigger.toAndroid(),
    itemId = itemId
)

fun AndroidEvolution.toShared(): SharedEvolution = SharedEvolution(
    id = id,
    targetLevel = targetLevel,
    trigger = trigger.toShared(),
    itemId = itemId
)

fun SharedEvolutionTrigger.toAndroid(): AndroidEvolutionTrigger = when (this) {
    SharedEvolutionTrigger.LevelUp -> AndroidEvolutionTrigger.LevelUp
    SharedEvolutionTrigger.UseItem -> AndroidEvolutionTrigger.UseItem
    SharedEvolutionTrigger.Trade -> AndroidEvolutionTrigger.Trade
}

fun AndroidEvolutionTrigger.toShared(): SharedEvolutionTrigger = when (this) {
    AndroidEvolutionTrigger.LevelUp -> SharedEvolutionTrigger.LevelUp
    AndroidEvolutionTrigger.UseItem -> SharedEvolutionTrigger.UseItem
    AndroidEvolutionTrigger.Trade -> SharedEvolutionTrigger.Trade
}

// PokemonMove adapters
fun SharedPokemonMove.toAndroid(): AndroidPokemonMove = AndroidPokemonMove(
    id = id,
    targetLevel = targetLevel
)

fun AndroidPokemonMove.toShared(): SharedPokemonMove = SharedPokemonMove(
    id = id,
    targetLevel = targetLevel
)

// PokemonAbility adapters
fun SharedPokemonAbility.toAndroid(): AndroidPokemonAbility = AndroidPokemonAbility(
    id = id,
    isHidden = isHidden
)

fun AndroidPokemonAbility.toShared(): SharedPokemonAbility = SharedPokemonAbility(
    id = id,
    isHidden = isHidden
)

// Move adapters
fun SharedMove.toAndroid(): AndroidMove = AndroidMove(
    id = id,
    name = name,
    description = description,
    category = category,
    type = type,
    pp = pp,
    power = power,
    accuracy = accuracy
)

fun AndroidMove.toShared(): SharedMove = SharedMove(
    id = id,
    name = name,
    description = description,
    category = category,
    type = type,
    pp = pp,
    power = power,
    accuracy = accuracy
)

// Item adapters
fun SharedItem.toAndroid(): AndroidItem = AndroidItem(
    id = id,
    name = name,
    description = description,
    sprite = sprite
)

fun AndroidItem.toShared(): SharedItem = SharedItem(
    id = id,
    name = name,
    description = description,
    sprite = sprite
)

// Ability adapters
fun SharedAbility.toAndroid(): AndroidAbility = AndroidAbility(
    id = id,
    name = name,
    description = description
)

fun AndroidAbility.toShared(): SharedAbility = SharedAbility(
    id = id,
    name = name,
    description = description
)

// List extension functions
fun List<SharedPokemon>.toAndroidList(): List<AndroidPokemon> = map { it.toAndroid() }
fun List<AndroidPokemon>.toSharedList(): List<SharedPokemon> = map { it.toShared() }
fun List<SharedMove>.toAndroidMoveList(): List<AndroidMove> = map { it.toAndroid() }
fun List<SharedItem>.toAndroidItemList(): List<AndroidItem> = map { it.toAndroid() }
fun List<SharedAbility>.toAndroidAbilityList(): List<AndroidAbility> = map { it.toAndroid() }

