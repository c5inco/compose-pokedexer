package des.c5inco.pokedexer.shared.data.items

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import des.c5inco.pokedexer.shared.model.ItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemsDao {
    @Query("SELECT * FROM itementity")
    fun getAll(): Flow<List<ItemEntity>>

    @Query("SELECT * FROM itementity WHERE id = :id LIMIT 1")
    fun findById(id: Int): Flow<ItemEntity?>

    @Query("SELECT * FROM itementity WHERE id IN (:ids)")
    suspend fun findByIds(ids: List<Int>): List<ItemEntity>

    @Query("SELECT * FROM itementity WHERE name LIKE '%' || :name || '%' COLLATE NOCASE")
    fun findByName(name: String): Flow<List<ItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg item: ItemEntity)

    @Query("DELETE FROM itementity")
    suspend fun deleteAll()
}
