package des.c5inco.pokedexer.shared.data

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import des.c5inco.pokedexer.shared.data.abilities.AbilitiesDao
import des.c5inco.pokedexer.shared.data.items.ItemsDao
import des.c5inco.pokedexer.shared.data.moves.MovesDao
import des.c5inco.pokedexer.shared.data.pokemon.PokemonDao
import des.c5inco.pokedexer.shared.model.AbilityEntity
import des.c5inco.pokedexer.shared.model.ItemEntity
import des.c5inco.pokedexer.shared.model.MoveEntity
import des.c5inco.pokedexer.shared.model.Pokemon

@Database(
    version = 1,
    entities = [Pokemon::class, MoveEntity::class, ItemEntity::class, AbilityEntity::class],
    exportSchema = true
)
@TypeConverters(Converters::class)
@ConstructedBy(PokemonDatabaseConstructor::class)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun movesDao(): MovesDao
    abstract fun itemsDao(): ItemsDao
    abstract fun abilitiesDao(): AbilitiesDao
}

// The Room compiler generates the `actual` implementation.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object PokemonDatabaseConstructor : RoomDatabaseConstructor<PokemonDatabase> {
    override fun initialize(): PokemonDatabase
}
