package des.c5inco.pokedexer.data.pokemon

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import des.c5inco.pokedexer.model.Pokemon
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemon")
    suspend fun getAll(): List<Pokemon>

    @Query("SELECT * FROM pokemon")
    fun getAllFlow(): Flow<List<Pokemon>>

    @Query("SELECT * FROM pokemon WHERE id = :id LIMIT 1")
    suspend fun findById(id: Int): Pokemon?

    @Query("SELECT * FROM pokemon WHERE id IN (:ids)")
    suspend fun findByIds(ids: List<Int>): List<Pokemon>

    @Query("SELECT * FROM pokemon WHERE name LIKE '%' || :name || '%' COLLATE NOCASE")
    fun findByName(name: String): Flow<List<Pokemon>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemon: Pokemon)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg pokemon: Pokemon)

    @Delete
    suspend fun delete(pokemon: Pokemon)

    @Query("DELETE FROM pokemon")
    suspend fun deleteAll()
}