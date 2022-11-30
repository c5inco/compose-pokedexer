package des.c5inco.pokedexer.data.moves

import androidx.room.*
import des.c5inco.pokedexer.model.Move

@Dao
interface MovesDao {
    @Query("SELECT * FROM move")
    suspend fun getAll(): List<Move>

    @Query("SELECT * FROM move WHERE id = :id LIMIT 1")
    suspend fun findById(id: Int): Move?

    @Query("SELECT * FROM move WHERE name LIKE :name LIMIT 1")
    suspend fun findByName(name: String): Move?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(move: Move)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg move: Move)

    @Delete
    suspend fun delete(move: Move)

    @Query("DELETE FROM move")
    suspend fun deleteAll()
}