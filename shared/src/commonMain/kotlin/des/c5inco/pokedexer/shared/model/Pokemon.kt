package des.c5inco.pokedexer.shared.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Domain model and Room entity representing a Pokemon.
 * This is the shared representation used across platforms.
 */
@Entity
data class Pokemon(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    @ColumnInfo(name = "types")
    val typeOfPokemon: List<String> = listOf(),
    val category: String,
    val image: Int,
    @ColumnInfo(defaultValue = "0.0")
    val height: Double,
    @ColumnInfo(defaultValue = "0.0")
    val weight: Double,
    @ColumnInfo(defaultValue = "-1")
    val genderRate: Int = -1,
    @ColumnInfo(defaultValue = "1")
    val generationId: Int = 1,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int,
    @ColumnInfo(name = "evolutions", defaultValue = "")
    val evolutionChain: List<Evolution> = listOf(),
    @ColumnInfo(name = "moves", defaultValue = "")
    val movesList: List<PokemonMove> = listOf(),
    @ColumnInfo(name = "abilities", defaultValue = "")
    val abilitiesList: List<PokemonAbility> = listOf(),
)

