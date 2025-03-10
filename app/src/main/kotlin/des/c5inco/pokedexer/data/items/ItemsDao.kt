package des.c5inco.pokedexer.data.items

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import des.c5inco.pokedexer.model.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemsDao {
    @Query("SELECT * FROM item")
    fun getAll(): Flow<List<Item>>

    @Query("SELECT * FROM item WHERE id = :id LIMIT 1")
    suspend fun findById(id: Int): Item?

    @Query("SELECT * FROM item WHERE id IN (:ids)")
    suspend fun findByIds(ids: List<Int>): List<Item>

    @Query("SELECT * FROM item WHERE name LIKE '%' || :name || '%' COLLATE NOCASE")
    fun findByName(name: String): Flow<List<Item>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Item)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg item: Item)

    @Query("DELETE FROM item")
    suspend fun deleteAll()
}
