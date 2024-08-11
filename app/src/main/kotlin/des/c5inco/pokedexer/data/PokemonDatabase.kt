package des.c5inco.pokedexer.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import des.c5inco.pokedexer.data.abilities.AbilitiesDao
import des.c5inco.pokedexer.data.items.ItemsDao
import des.c5inco.pokedexer.data.moves.MovesDao
import des.c5inco.pokedexer.data.pokemon.PokemonDao
import des.c5inco.pokedexer.model.Ability
import des.c5inco.pokedexer.model.Evolution
import des.c5inco.pokedexer.model.EvolutionTrigger
import des.c5inco.pokedexer.model.Item
import des.c5inco.pokedexer.model.Move
import des.c5inco.pokedexer.model.Pokemon
import des.c5inco.pokedexer.model.PokemonAbility
import des.c5inco.pokedexer.model.PokemonMove

@Database(
    version = 6,
    entities = [Pokemon::class, Move::class, Item::class, Ability::class],
    autoMigrations = [
        AutoMigration(from = 1, to = 6),
    ]
)
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
        val list = mutableListOf<Evolution>()

        if (str.isNotBlank()) {
            str.split("|").map {
                val evo = it.split(",")
                list.add(
                    Evolution(
                        id = evo[0].toInt(),
                        targetLevel = evo[1].toInt(),
                        trigger = EvolutionTrigger.fromInt(evo[2].toInt()),
                        itemId = evo[3].toInt()
                    ))
            }
        }

        return list.toList()
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
        val list = mutableListOf<PokemonMove>()

        if (str.isNotBlank()) {
            str.split("|").map {
                val move = it.split(",")
                list.add(PokemonMove(move[0].toInt(), move[1].toInt()))
            }
        }

        return list.toList()
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
        val list = mutableListOf<PokemonAbility>()

        if (str.isNotBlank()) {
            str.split("|").map {
                val ability = it.split(",")
                list.add(PokemonAbility(ability[0].toInt(), ability[1].toBoolean()))
            }
        }

        return list.toList()
    }

    @TypeConverter
    fun pokemonAbilityListToString(list: List<PokemonAbility>): String {
        return list.joinToString(separator = "|") {
            val data = listOf(it.id, it.isHidden)
            data.joinToString(",")
        }
    }
}