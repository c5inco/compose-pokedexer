package des.c5inco.pokedexer.data.pokemon

import androidx.room.*
import des.c5inco.pokedexer.data.moves.MovesDao
import des.c5inco.pokedexer.model.Evolution
import des.c5inco.pokedexer.model.Move
import des.c5inco.pokedexer.model.Pokemon

@Database(
    version = 3,
    entities = [Pokemon::class, Move::class],
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
    ]
)
@TypeConverters(Converters::class)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun movesDao(): MovesDao
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
                list.add(Evolution(evo[0].toInt(), evo[1].toInt()))
            }
        }

        return list.toList()
    }

    @TypeConverter
    fun evolutionListToString(list: List<Evolution>): String {
        return list.joinToString(separator = "|") {
            val data = listOf(it.id, it.targetLevel)
            data.joinToString(",")
        }
    }
}