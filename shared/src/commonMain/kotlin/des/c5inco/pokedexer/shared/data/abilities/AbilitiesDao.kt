package des.c5inco.pokedexer.shared.data.abilities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import des.c5inco.pokedexer.shared.model.AbilityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AbilitiesDao {
    @Query("SELECT * FROM abilityentity")
    fun getAll(): Flow<List<AbilityEntity>>

    @Query("SELECT * FROM abilityentity WHERE id = :id LIMIT 1")
    fun findById(id: Int): Flow<AbilityEntity?>

    @Query("SELECT * FROM abilityentity WHERE id IN (:ids)")
    suspend fun findByIds(ids: List<Int>): List<AbilityEntity>

    @Query("SELECT * FROM abilityentity WHERE name LIKE '%' || :name || '%' COLLATE NOCASE")
    suspend fun findByName(name: String): List<AbilityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: AbilityEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg item: AbilityEntity)

    @Query("DELETE FROM abilityentity")
    suspend fun deleteAll()
}
