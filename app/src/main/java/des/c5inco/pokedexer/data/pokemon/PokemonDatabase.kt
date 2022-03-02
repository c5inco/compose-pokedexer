package des.c5inco.pokedexer.data.pokemon

import androidx.room.*
import des.c5inco.pokedexer.data.moves.MovesDao
import des.c5inco.pokedexer.model.Move
import des.c5inco.pokedexer.model.Pokemon

@Database(
    version = 2,
    entities = [Pokemon::class, Move::class],
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
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
        return str?.let { str.split(",") }
    }

    @TypeConverter
    fun listToString(list: List<String>?): String? {
        return list?.joinToString(",")
    }
}