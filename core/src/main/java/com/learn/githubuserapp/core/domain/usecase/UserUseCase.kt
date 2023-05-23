package com.learn.githubuserapp.core.domain.usecase

import com.learn.githubuserapp.core.domain.model.User
import com.learn.githubuserapp.core.utils.Result
import kotlinx.coroutines.flow.Flow

interface UserUseCase {

    // MainActivity
    fun findUser(username: String): Flow<Result<List<User>>>

    fun getTheme(): Flow<Boolean>

    // SettingActivity
    suspend fun saveTheme(isDarkModeActive: Boolean)

    // FavoriteActivity
    fun getAllUsers(): Flow<List<User>>

    // DetailActivity
    fun getUserDetail(username: String): Flow<Result<User>>

    fun getUserFollowers(username: String): Flow<Result<List<User>>>

    fun getUserFollowing(username: String): Flow<Result<List<User>>>

    suspend fun insertUser(user: User)

    suspend fun deleteUser(username: String)

    fun isFavorite(username: String): Flow<Int>
}