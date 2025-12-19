package des.c5inco.pokedexer.shared

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import des.c5inco.pokedexer.shared.data.PokemonDatabase
import des.c5inco.pokedexer.shared.data.PokemonDatabaseConstructor
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSHomeDirectory
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import okio.Path.Companion.toPath

class IosDatabaseFactory : DatabaseFactory {
    override fun createDatabase(): PokemonDatabase {
        val dbFile = NSHomeDirectory() + "/pokemon.db"
        return Room.databaseBuilder<PokemonDatabase>(
            name = dbFile,
            factory = { PokemonDatabaseConstructor.initialize() }
        ).setDriver(androidx.sqlite.driver.bundled.BundledSQLiteDriver()) // Important for KMP
            .build()
    }
}

@OptIn(ExperimentalForeignApi::class)
fun createIosDataStore(): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath(
        produceFile = {
            val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null,
            )
            (requireNotNull(documentDirectory).path + "/user_preferences.preferences_pb").toPath()
        }
    )
}

object IosSharedModule {
    val shared: SharedModule by lazy {
        SharedModule(
            databaseFactory = IosDatabaseFactory(),
            dataStore = createIosDataStore()
        )
    }

    val scope = kotlinx.coroutines.MainScope()
}
