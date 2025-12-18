package des.c5inco.pokedexer.shared

import com.rickclephas.kmp.nativecoroutines.NativeFlow
import com.rickclephas.kmp.nativecoroutines.NativeSuspend
import com.rickclephas.kmp.nativecoroutines.asNativeFlow
import com.rickclephas.kmp.nativecoroutines.nativeSuspend
import des.c5inco.pokedexer.shared.model.Ability
import des.c5inco.pokedexer.shared.model.Item
import des.c5inco.pokedexer.shared.model.Move
import des.c5inco.pokedexer.shared.model.Pokemon
import kotlin.Int
import kotlin.String
import kotlin.Unit
import kotlin.collections.List
import kotlin.native.ObjCName

/**
 * Get all Pokemon as a Flow.
 */
@ObjCName(name = "getAllPokemon")
public fun PokedexerSDK.getAllPokemonNative(): NativeFlow<List<Pokemon>> =
    getAllPokemon().asNativeFlow(null)

/**
 * Get Pokemon by generation as a Flow.
 */
@ObjCName(name = "getPokemonByGeneration")
public fun PokedexerSDK.getPokemonByGenerationNative(generationId: Int): NativeFlow<List<Pokemon>> =
    getPokemonByGeneration(generationId).asNativeFlow(null)

/**
 * Get a single Pokemon by ID.
 */
@ObjCName(name = "getPokemonById")
public fun PokedexerSDK.getPokemonByIdNative(id: Int): NativeFlow<Pokemon?> =
    getPokemonById(id).asNativeFlow(null)

/**
 * Search Pokemon by name.
 */
@ObjCName(name = "searchPokemon")
public fun PokedexerSDK.searchPokemonNative(name: String): NativeFlow<List<Pokemon>> =
    searchPokemon(name).asNativeFlow(null)

/**
 * Load Pokemon for a generation from the network if not cached.
 *  Returns the list of Pokemon for that generation.
 */
@ObjCName(name = "loadPokemonForGeneration")
public fun PokedexerSDK.loadPokemonForGenerationNative(generationId: Int):
    NativeSuspend<List<Pokemon>> = nativeSuspend(null) { loadPokemonForGeneration(generationId) }

/**
 * Get all moves as a Flow.
 */
@ObjCName(name = "getAllMoves")
public fun PokedexerSDK.getAllMovesNative(): NativeFlow<List<Move>> =
    getAllMoves().asNativeFlow(null)

/**
 * Get a single move by ID.
 */
@ObjCName(name = "getMoveById")
public fun PokedexerSDK.getMoveByIdNative(id: Int): NativeFlow<Move?> =
    getMoveById(id).asNativeFlow(null)

/**
 * Get moves by IDs.
 */
@ObjCName(name = "getMovesByIds")
public fun PokedexerSDK.getMovesByIdsNative(ids: List<Int>): NativeSuspend<List<Move>> =
    nativeSuspend(null) { getMovesByIds(ids) }

/**
 * Load moves from the network if not cached.
 */
@ObjCName(name = "loadMoves")
public fun PokedexerSDK.loadMovesNative(): NativeSuspend<List<Move>> = nativeSuspend(null) {
    loadMoves() }

/**
 * Get all items as a Flow.
 */
@ObjCName(name = "getAllItems")
public fun PokedexerSDK.getAllItemsNative(): NativeFlow<List<Item>> =
    getAllItems().asNativeFlow(null)

/**
 * Get a single item by ID.
 */
@ObjCName(name = "getItemById")
public fun PokedexerSDK.getItemByIdNative(id: Int): NativeFlow<Item?> =
    getItemById(id).asNativeFlow(null)

/**
 * Load items from the network if not cached.
 */
@ObjCName(name = "loadItems")
public fun PokedexerSDK.loadItemsNative(): NativeSuspend<List<Item>> = nativeSuspend(null) {
    loadItems() }

/**
 * Get all abilities as a Flow.
 */
@ObjCName(name = "getAllAbilities")
public fun PokedexerSDK.getAllAbilitiesNative(): NativeFlow<List<Ability>> =
    getAllAbilities().asNativeFlow(null)

/**
 * Get a single ability by ID.
 */
@ObjCName(name = "getAbilityById")
public fun PokedexerSDK.getAbilityByIdNative(id: Int): NativeFlow<Ability?> =
    getAbilityById(id).asNativeFlow(null)

/**
 * Get abilities by IDs.
 */
@ObjCName(name = "getAbilitiesByIds")
public fun PokedexerSDK.getAbilitiesByIdsNative(ids: List<Int>): NativeSuspend<List<Ability>> =
    nativeSuspend(null) { getAbilitiesByIds(ids) }

/**
 * Load abilities from the network if not cached.
 */
@ObjCName(name = "loadAbilities")
public fun PokedexerSDK.loadAbilitiesNative(): NativeSuspend<List<Ability>> = nativeSuspend(null) {
    loadAbilities() }

/**
 * Get favorites as a Flow.
 */
@ObjCName(name = "favoritesFlow")
public val PokedexerSDK.favoritesFlowNative: NativeFlow<List<Int>>
  get() = favoritesFlow.asNativeFlow(null)

/**
 * Toggle a Pokemon's favorite status.
 */
@ObjCName(name = "toggleFavorite")
public fun PokedexerSDK.toggleFavoriteNative(pokemonId: Int): NativeSuspend<Unit> =
    nativeSuspend(null) { toggleFavorite(pokemonId) }

/**
 * Get the current list of favorites.
 */
@ObjCName(name = "getFavorites")
public fun PokedexerSDK.getFavoritesNative(): NativeSuspend<List<Int>> = nativeSuspend(null) {
    getFavorites() }

/**
 * Get favorite Pokemon.
 */
@ObjCName(name = "getFavoritePokemon")
public fun PokedexerSDK.getFavoritePokemonNative(): NativeFlow<List<Pokemon>> =
    getFavoritePokemon().asNativeFlow(null)
