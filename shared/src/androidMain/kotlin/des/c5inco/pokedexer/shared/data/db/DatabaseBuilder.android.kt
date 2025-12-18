package des.c5inco.pokedexer.shared.data.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Creates a Room database builder for Android.
 * Note: This requires the Context to be set before use.
 */
private var applicationContext: Context? = null

/**
 * Initialize the database builder with the application context.
 * This should be called from the Application class or during app initialization.
 */
fun initializeDatabase(context: Context) {
    applicationContext = context.applicationContext
}

actual fun getDatabaseBuilder(): RoomDatabase.Builder<PokedexerDatabase> {
    val context = applicationContext
        ?: throw IllegalStateException("Database not initialized. Call initializeDatabase(context) first.")
    
    return Room.databaseBuilder<PokedexerDatabase>(
        context = context,
        name = context.getDatabasePath(DATABASE_NAME).absolutePath
    )
}

