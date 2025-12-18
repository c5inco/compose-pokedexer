package des.c5inco.pokedexer.shared.data.db

import androidx.room.RoomDatabase

/**
 * Expect function for platform-specific database builder creation.
 * Each platform provides its own implementation.
 */
expect fun getDatabaseBuilder(): RoomDatabase.Builder<PokedexerDatabase>

