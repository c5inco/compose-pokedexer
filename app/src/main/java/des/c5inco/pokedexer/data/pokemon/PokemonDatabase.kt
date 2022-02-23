package des.c5inco.pokedexer.data.pokemon

import android.content.Context
import androidx.room.*
import des.c5inco.pokedexer.model.Pokemon

@Database(entities = [Pokemon::class], version = 1)
@TypeConverters(Converters::class)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao

    companion object {
        @Volatile
        private var _instance: PokemonDatabase? = null

        fun getInstance(context: Context): PokemonDatabase {
            return _instance ?: synchronized(this) {
                val instance = Room
                        .databaseBuilder(
                            context.applicationContext,
                            PokemonDatabase::class.java,
                            "pokemon_database"
                        )
                        .build()
                _instance = instance
                instance
            }
        }
    }
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