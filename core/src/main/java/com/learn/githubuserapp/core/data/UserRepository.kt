package com.learn.githubuserapp.core.data

import com.learn.githubuserapp.core.data.remote.NetworkDataSource
import com.learn.githubuserapp.core.domain.model.User
import com.learn.githubuserapp.core.domain.repository.IUserRepository
import com.learn.githubuserapp.core.utils.Result
import com.learn.githubuserapp.core.utils.mapEntitiesToDomain
import com.learn.githubuserapp.core.utils.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository(
    private val localDataSource: com.learn.githubuserapp.core.data.local.LocalDataSource,
    private val networkDataSource: NetworkDataSource,
) : IUserRepository {
    override fun findUser(username: String): Flow<Result<List<User>>> = networkDataSource.findUser(username)

    override fun getTheme() = localDataSource.getTheme()

    override suspend fun saveTheme(isDarkModeActive: Boolean) {
        localDataSource.saveTheme(isDarkModeActive)
    }

    override suspend fun insertUser(user: User) {
        localDataSource.insertUser(user.toEntity())
    }

    override suspend fun deleteUser(username: String) {
        localDataSource.deleteUser(username)
    }

    override fun isFavorite(username: String) = localDataSource.isFavorite(username)

    override fun getAllUsers() = localDataSource.getAllUsers().map { mapEntitiesToDomain(it) }

    override fun getUserDetail(username: String) = networkDataSource.getUserDetail(username)

    override fun getUserFollowers(username: String) = networkDataSource.getUserFollowers(username)

    override fun getUserFollowing(username: String) = networkDataSource.getUserFollowing(username)
}