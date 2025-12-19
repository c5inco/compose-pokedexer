package des.c5inco.pokedexer.shared.data.moves

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import des.c5inco.pokedexer.shared.model.MoveEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovesDao {
    @Query("SELECT * FROM moveentity")
    fun getAll(): Flow<List<MoveEntity>>

    @Query("SELECT * FROM moveentity WHERE id = :id LIMIT 1")
    fun findById(id: Int): Flow<MoveEntity?>

    @Query("SELECT * FROM moveentity WHERE id IN (:ids)")
    suspend fun findByIds(ids: List<Int>): List<MoveEntity>

    @Query("SELECT * FROM moveentity WHERE name LIKE '%' || :name || '%' COLLATE NOCASE")
    fun findByName(name: String): Flow<List<MoveEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(move: MoveEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg move: MoveEntity)

    @Delete
    suspend fun delete(move: MoveEntity)

    @Query("DELETE FROM moveentity")
    suspend fun deleteAll()
}
