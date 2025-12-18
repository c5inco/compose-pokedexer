package des.c5inco.pokedexer.shared.data.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import des.c5inco.pokedexer.shared.model.Ability
import des.c5inco.pokedexer.shared.model.Item
import des.c5inco.pokedexer.shared.model.Move
import des.c5inco.pokedexer.shared.model.Pokemon

/**
 * Room database for the Pokedexer app.
 * Shared between Android and iOS.
 */
@Database(
    version = 1,
    entities = [Pokemon::class, Move::class, Item::class, Ability::class],
    exportSchema = true
)
@TypeConverters(Converters::class)
@ConstructedBy(PokedexerDatabaseConstructor::class)
abstract class PokedexerDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun movesDao(): MovesDao
    abstract fun itemsDao(): ItemsDao
    abstract fun abilitiesDao(): AbilitiesDao
}

/**
 * Database constructor for Room KMP.
 * Room will generate the implementation.
 */
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object PokedexerDatabaseConstructor : RoomDatabaseConstructor<PokedexerDatabase> {
    override fun initialize(): PokedexerDatabase
}

/**
 * Database name constant.
 */
const val DATABASE_NAME = "pokedexer.db"

