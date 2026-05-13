package cst.unitbvfmi2026.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cst.unitbvfmi2026.data.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    //in Java: singura posib de "a instantia" o interfata este printr-o instanta anonima
    @Insert(onConflict = OnConflictStrategy.REPLACE) //implica si un update fortat
    suspend fun insert(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE) //implica si un update fortat
    suspend fun insert(user: List<UserEntity>)

    @Query("SELECT * FROM users")
    fun getAll(): Flow<List<UserEntity>>

    @Query("SELECT * FROM users WHERE id = :id")//:id = parametru din functie
    fun getById(id: Long): UserEntity?
}