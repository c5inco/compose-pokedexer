package des.c5inco.pokedexer.data.items

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import des.c5inco.pokedexer.model.Item

@Dao
interface ItemsDao {
    @Query("SELECT * FROM item")
    suspend fun getAll(): List<Item>

    @Query("SELECT * FROM item WHERE id = :id LIMIT 1")
    suspend fun findById(id: Int): Item?

    @Query("SELECT * FROM item WHERE id IN (:ids)")
    suspend fun findByIds(ids: List<Int>): List<Item>

    @Query("SELECT * FROM item WHERE name LIKE :name LIMIT 1")
    suspend fun findByName(name: String): Item?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Item)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg item: Item)

    @Query("DELETE FROM item")
    suspend fun deleteAll()
}
