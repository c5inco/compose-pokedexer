package des.c5inco.pokedexer

import android.content.Context
import android.content.ContextWrapper
import android.database.sqlite.SQLiteDatabase
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import des.c5inco.pokedexer.shared.data.PokemonDatabase
import des.c5inco.pokedexer.shared.data.getDatabaseBuilder
import java.io.File
import java.util.UUID
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PokemonDatabaseMigrationTest {
    private lateinit var context: Context
    private lateinit var databaseFile: File
    private lateinit var databaseContext: Context
    private var database: PokemonDatabase? = null

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        databaseFile = context.getDatabasePath("pokemon-v7-v8-${UUID.randomUUID()}.db")
        databaseContext =
            object : ContextWrapper(context) {
                override fun getDatabasePath(name: String): File {
                    return if (name == "pokemon.db") databaseFile else super.getDatabasePath(name)
                }
            }
    }

    @After
    fun tearDown() {
        database?.close()
        context.deleteDatabase(databaseFile.name)
        listOf("", "-journal", "-shm", "-wal").forEach { suffix ->
            File(databaseFile.path + suffix).delete()
        }
    }

    @Test
    fun version7To8ClearsOnlyPokemonCache() = runBlocking {
        seedVersion7Database()

        database =
            getDatabaseBuilder(databaseContext)
                .fallbackToDestructiveMigration(dropAllTables = true)
                .build()
        val openedDatabase = checkNotNull(database)

        assertEquals(8, openedDatabase.openHelper.readableDatabase.version)
        assertEquals(0, openedDatabase.pokemonDao().getAll().size)
        assertEquals(1, openedDatabase.movesDao().count())
        assertEquals(1, openedDatabase.itemsDao().count())
        assertEquals(1, openedDatabase.abilitiesDao().getAll().first().size)
    }

    private fun seedVersion7Database() {
        databaseFile.parentFile?.mkdirs()
        SQLiteDatabase.openOrCreateDatabase(databaseFile, null).use { database ->
            createVersion7Tables(database)
            createVersion7RoomMetadata(database)
            insertCachedRows(database)
            database.execSQL("PRAGMA user_version = 7")

            assertEquals(7, database.version)
            assertEquals(1, countRows(database, "Pokemon"))
            assertEquals("10,5|11,9", stringValue(database, "SELECT moves FROM Pokemon"))
            assertEquals(1, countRows(database, "Move"))
            assertEquals(1, countRows(database, "Item"))
            assertEquals(1, countRows(database, "Ability"))
        }
    }

    private fun createVersion7Tables(database: SQLiteDatabase) {
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `Pokemon` (
                `id` INTEGER NOT NULL,
                `name` TEXT NOT NULL,
                `description` TEXT NOT NULL,
                `types` TEXT NOT NULL,
                `category` TEXT NOT NULL,
                `image` INTEGER NOT NULL,
                `height` REAL NOT NULL DEFAULT 0.0,
                `weight` REAL NOT NULL DEFAULT 0.0,
                `genderRate` INTEGER NOT NULL DEFAULT -1,
                `generationId` INTEGER NOT NULL DEFAULT 1,
                `hp` INTEGER NOT NULL,
                `attack` INTEGER NOT NULL,
                `defense` INTEGER NOT NULL,
                `specialAttack` INTEGER NOT NULL,
                `specialDefense` INTEGER NOT NULL,
                `speed` INTEGER NOT NULL,
                `evolutions` TEXT NOT NULL DEFAULT '',
                `moves` TEXT NOT NULL DEFAULT '',
                `abilities` TEXT NOT NULL DEFAULT '',
                PRIMARY KEY(`id`)
            )
            """
                .trimIndent()
        )
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `Move` (
                `id` INTEGER NOT NULL,
                `name` TEXT NOT NULL,
                `description` TEXT NOT NULL,
                `category` TEXT NOT NULL,
                `type` TEXT NOT NULL,
                `pp` INTEGER NOT NULL,
                `power` INTEGER,
                `accuracy` INTEGER,
                PRIMARY KEY(`id`)
            )
            """
                .trimIndent()
        )
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `Item` (
                `id` INTEGER NOT NULL,
                `name` TEXT NOT NULL,
                `description` TEXT NOT NULL,
                `sprite` TEXT NOT NULL,
                PRIMARY KEY(`id`)
            )
            """
                .trimIndent()
        )
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `Ability` (
                `id` INTEGER NOT NULL,
                `name` TEXT NOT NULL,
                `description` TEXT NOT NULL,
                PRIMARY KEY(`id`)
            )
            """
                .trimIndent()
        )
    }

    private fun createVersion7RoomMetadata(database: SQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS room_master_table " +
                "(id INTEGER PRIMARY KEY,identity_hash TEXT)"
        )
        database.execSQL(
            "INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, ?)",
            arrayOf("5950e1cbaabb0b9d159add1ae641c03f"),
        )
    }

    private fun insertCachedRows(database: SQLiteDatabase) {
        database.execSQL(
            """
            INSERT INTO `Pokemon` (
                `id`, `name`, `description`, `types`, `category`, `image`, `height`,
                `weight`, `genderRate`, `generationId`, `hp`, `attack`, `defense`,
                `specialAttack`, `specialDefense`, `speed`, `evolutions`, `moves`, `abilities`
            ) VALUES (1, 'Bulbasaur', 'seed', 'Grass,Poison', 'Seed', 1, 0.7,
                6.9, 1, 1, 45, 49, 49, 65, 65, 45, '', '10,5|11,9', '65,false')
            """
                .trimIndent()
        )
        database.execSQL(
            "INSERT INTO `Move` VALUES (10, 'Tackle', 'seed', 'Physical', 'Normal', 35, 40, 100)"
        )
        database.execSQL("INSERT INTO `Item` VALUES (20, 'Potion', 'seed', 'potion.png')")
        database.execSQL("INSERT INTO `Ability` VALUES (65, 'Overgrow', 'seed')")
    }

    private fun countRows(database: SQLiteDatabase, table: String): Int {
        return database.rawQuery("SELECT COUNT(*) FROM `$table`", null).use { cursor ->
            check(cursor.moveToFirst())
            cursor.getInt(0)
        }
    }

    private fun stringValue(database: SQLiteDatabase, query: String): String {
        return database.rawQuery(query, null).use { cursor ->
            check(cursor.moveToFirst())
            cursor.getString(0)
        }
    }
}
