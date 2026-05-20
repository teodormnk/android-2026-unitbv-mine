package cst.unitbvfmi2026.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cst.unitbvfmi2026.data.entities.AddressEntity
import cst.unitbvfmi2026.data.entities.AddressWithUsers
import cst.unitbvfmi2026.data.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressDao {
    //in Java: singura posib de "a instantia" o interfata este printr-o instanta anonima
    @Insert(onConflict = OnConflictStrategy.REPLACE) //implica si un update fortat
    suspend fun insert(address: AddressEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE) //implica si un update fortat
    suspend fun insert(address: List<AddressEntity>)

    @Query("SELECT * FROM addresses")
    fun getAll(): Flow<List<AddressEntity>>

    @Query("SELECT * FROM addresses WHERE id = :id")//:id = parametru din functie
    fun getById(id: Long): AddressEntity?

    @Query("SELECT * FROM addresses WHERE id = :id")
    fun getAddressWithUsers(id: Long): Flow<AddressWithUsers>
}