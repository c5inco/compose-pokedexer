package des.c5inco.pokedexer.shared.data

import androidx.sqlite.SQLiteConnection
import androidx.sqlite.SQLiteStatement
import kotlin.test.Test
import kotlin.test.assertEquals

class PokemonDatabaseMigrationTest {
    @Test
    fun migrationClearsOnlyPokemonCache() {
        val connection = RecordingSQLiteConnection()

        InvalidatePokemonCacheMigration().onPostMigrate(connection)

        assertEquals(listOf("DELETE FROM pokemon"), connection.statements)
    }
}

private class RecordingSQLiteConnection : SQLiteConnection {
    val statements = mutableListOf<String>()

    override fun prepare(sql: String): SQLiteStatement {
        statements += sql
        return NoOpSQLiteStatement
    }

    override fun close() = Unit
}

private object NoOpSQLiteStatement : SQLiteStatement {
    override fun bindBlob(index: Int, value: ByteArray) = Unit

    override fun bindDouble(index: Int, value: Double) = Unit

    override fun bindLong(index: Int, value: Long) = Unit

    override fun bindText(index: Int, value: String) = Unit

    override fun bindNull(index: Int) = Unit

    override fun getBlob(index: Int): ByteArray = error("Not used")

    override fun getDouble(index: Int): Double = error("Not used")

    override fun getLong(index: Int): Long = error("Not used")

    override fun getText(index: Int): String = error("Not used")

    override fun isNull(index: Int): Boolean = error("Not used")

    override fun getColumnCount(): Int = 0

    override fun getColumnName(index: Int): String = error("Not used")

    override fun getColumnType(index: Int): Int = error("Not used")

    override fun step(): Boolean = false

    override fun reset() = Unit

    override fun clearBindings() = Unit

    override fun close() = Unit
}
