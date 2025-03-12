package des.c5inco.pokedexer

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import des.c5inco.pokedexer.data.PokemonDatabase
import des.c5inco.pokedexer.data.pokemon.PokemonDao
import des.c5inco.pokedexer.data.pokemon.SamplePokemonData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PokemonDatabaseTest {
    private lateinit var pokemonDao: PokemonDao
    private lateinit var db: PokemonDatabase

    @Before
    fun createDb() {
        // TODO: Use Hilt in the future
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, PokemonDatabase::class.java)
            .build()

        pokemonDao = db.pokemonDao()
    }

    @After
    @Throws(IOException::class)
    fun deleteDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertOnePokemon() = runBlocking {
        val pokemon = SamplePokemonData[0].copy()
        pokemonDao.insert(pokemon)
        val onePokemon = pokemonDao.findById(1).first()
        assertEquals(onePokemon?.id, 1)
        assertEquals(onePokemon?.typeOfPokemon, listOf("Grass", "Poison"))
    }

    @Test
    @Throws(Exception::class)
    fun insertMultiplePokemon() = runBlocking {
        val pokemon = SamplePokemonData
        pokemonDao.insertAll(*(pokemon.toTypedArray()))
        val allPokemon = pokemonDao.getAll()
        assertEquals(allPokemon.size, 9)
    }

    @Test
    @Throws(Exception::class)
    fun insertDuplicatePokemon() = runBlocking {
        val pokemon = SamplePokemonData[0].copy()
        pokemonDao.insert(pokemon)
        pokemonDao.insert(pokemon)
        val allPokemon = pokemonDao.getAll()
        assertEquals(allPokemon.size, 1)
    }

    @Test
    @Throws(Exception::class)
    fun insertDuplicateMultiplePokemon() = runBlocking {
        val pokemon = SamplePokemonData
        pokemonDao.insertAll(*(pokemon.toTypedArray()))
        pokemonDao.insertAll(*(pokemon.toTypedArray()))
        val allPokemon = pokemonDao.getAll()
        assertEquals(allPokemon.size, 9)
    }
}