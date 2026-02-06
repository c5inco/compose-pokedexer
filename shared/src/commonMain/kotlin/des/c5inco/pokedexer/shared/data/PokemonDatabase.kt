package des.c5inco.pokedexer.shared.data

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import des.c5inco.pokedexer.shared.data.abilities.AbilitiesDao
import des.c5inco.pokedexer.shared.data.items.ItemsDao
import des.c5inco.pokedexer.shared.data.moves.MovesDao
import des.c5inco.pokedexer.shared.data.pokemon.PokemonDao
import des.c5inco.pokedexer.shared.model.Ability
import des.c5inco.pokedexer.shared.model.Evolution
import des.c5inco.pokedexer.shared.model.EvolutionTrigger
import des.c5inco.pokedexer.shared.model.Item
import des.c5inco.pokedexer.shared.model.Move
import des.c5inco.pokedexer.shared.model.Pokemon
import des.c5inco.pokedexer.shared.model.PokemonAbility
import des.c5inco.pokedexer.shared.model.PokemonMove

// Room KMP requires this expect/actual pattern for database construction
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object PokemonDatabaseConstructor : RoomDatabaseConstructor<PokemonDatabase>

@Database(
    version = 7,
    entities = [Pokemon::class, Move::class, Item::class, Ability::class],
    exportSchema = true
)
@ConstructedBy(PokemonDatabaseConstructor::class)
@TypeConverters(Converters::class)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun movesDao(): MovesDao
    abstract fun itemsDao(): ItemsDao
    abstract fun abilitiesDao(): AbilitiesDao
}

class Converters {
    @TypeConverter
    fun stringToList(str: String?): List<String>? {
        return str?.split(",")
    }

    @TypeConverter
    fun listToString(list: List<String>?): String? {
        return list?.joinToString(",")
    }

    @TypeConverter
    fun stringToEvolutionList(str: String): List<Evolution> {
        if (str.isBlank()) return emptyList()

        return str.split("|").mapNotNull { entry ->
            try {
                val evo = entry.split(",")
                if (evo.size >= 4) {
                    Evolution(
                        id = evo[0].toInt(),
                        targetLevel = evo[1].toInt(),
                        trigger = EvolutionTrigger.fromInt(evo[2].toInt()),
                        itemId = evo[3].toInt()
                    )
                } else null
            } catch (e: NumberFormatException) {
                null
            }
        }
    }

    @TypeConverter
    fun evolutionListToString(list: List<Evolution>): String {
        return list.joinToString(separator = "|") {
            val data = listOf(it.id, it.targetLevel, it.trigger.value, it.itemId)
            data.joinToString(",")
        }
    }

    @TypeConverter
    fun stringToPokemonMoveList(str: String): List<PokemonMove> {
        if (str.isBlank()) return emptyList()

        return str.split("|").mapNotNull { entry ->
            try {
                val move = entry.split(",")
                if (move.size >= 2) {
                    PokemonMove(move[0].toInt(), move[1].toInt())
                } else null
            } catch (e: NumberFormatException) {
                null
            }
        }
    }

    @TypeConverter
    fun pokemonMoveListToString(list: List<PokemonMove>): String {
        return list.joinToString(separator = "|") {
            val data = listOf(it.id, it.targetLevel)
            data.joinToString(",")
        }
    }

    @TypeConverter
    fun stringToPokemonAbilityList(str: String): List<PokemonAbility> {
        if (str.isBlank()) return emptyList()

        return str.split("|").mapNotNull { entry ->
            try {
                val ability = entry.split(",")
                if (ability.size >= 2) {
                    PokemonAbility(ability[0].toInt(), ability[1].toBoolean())
                } else null
            } catch (e: NumberFormatException) {
                null
            }
        }
    }

    @TypeConverter
    fun pokemonAbilityListToString(list: List<PokemonAbility>): String {
        return list.joinToString(separator = "|") {
            val data = listOf(it.id, it.isHidden)
            data.joinToString(",")
        }
    }
}
