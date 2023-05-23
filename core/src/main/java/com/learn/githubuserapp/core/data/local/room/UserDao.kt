package com.learn.githubuserapp.core.data.local.room

import androidx.room.*
import com.learn.githubuserapp.core.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userEntity: UserEntity)

    @Query("DELETE FROM user WHERE username = :username")
    suspend fun delete(username: String)

    @Query("SELECT * FROM user ORDER BY id ASC")
    fun getAllUsers() : Flow<List<UserEntity>>

    @Query("SELECT COUNT() FROM user WHERE username = :username")
    fun isFavorite(username: String): Flow<Int>

}