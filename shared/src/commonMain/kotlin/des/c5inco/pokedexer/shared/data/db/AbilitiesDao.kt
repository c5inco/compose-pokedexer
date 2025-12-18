package des.c5inco.pokedexer.shared.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import des.c5inco.pokedexer.shared.model.Ability
import kotlinx.coroutines.flow.Flow

@Dao
interface AbilitiesDao {
    @Query("SELECT * FROM ability")
    fun getAll(): Flow<List<Ability>>

    @Query("SELECT * FROM ability WHERE id = :id LIMIT 1")
    fun findById(id: Int): Flow<Ability?>

    @Query("SELECT * FROM ability WHERE id IN (:ids)")
    suspend fun findByIds(ids: List<Int>): List<Ability>

    @Query("SELECT * FROM ability WHERE name LIKE '%' || :name || '%' COLLATE NOCASE")
    suspend fun findByName(name: String): List<Ability>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Ability)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg item: Ability)

    @Query("DELETE FROM ability")
    suspend fun deleteAll()
}

