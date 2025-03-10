package des.c5inco.pokedexer

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import des.c5inco.pokedexer.data.PokemonDatabase
import des.c5inco.pokedexer.data.moves.MovesDao
import des.c5inco.pokedexer.data.moves.SampleMoves
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MovesDatabaseTest {
    private lateinit var movesDao: MovesDao
    private lateinit var db: PokemonDatabase

    @Before
    fun createDb() {
        // TODO: Use Hilt in the future
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, PokemonDatabase::class.java)
            .build()

        movesDao = db.movesDao()
    }

    @After
    @Throws(IOException::class)
    fun deleteDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertOneMove() = runBlocking {
        val pokemon = SampleMoves[0].copy()
        movesDao.insert(pokemon)
        val oneMove = movesDao.findById(1)
        assertEquals(oneMove?.id, 1)
        assertEquals(oneMove?.name, "Pound")
    }

    @Test
    @Throws(Exception::class)
    fun insertMultipleMoves() = runBlocking {
        val pokemon = SampleMoves
        movesDao.insertAll(*(pokemon.toTypedArray()))
        val allMoves = movesDao.getAll().first()
        assertEquals(allMoves.size, 5)
    }
}