package com.learn.githubuserapp.core.domain.usecase

import com.learn.githubuserapp.core.domain.model.User
import com.learn.githubuserapp.core.domain.repository.IUserRepository
import com.learn.githubuserapp.core.utils.Result
import kotlinx.coroutines.flow.Flow

class UserInteractor(private val repository: IUserRepository) : UserUseCase {
    override fun findUser(username: String): Flow<Result<List<User>>> {
        return repository.findUser(username)
    }

    override fun getTheme(): Flow<Boolean> {
        return repository.getTheme()
    }

    override suspend fun saveTheme(isDarkModeActive: Boolean) {
        return repository.saveTheme(isDarkModeActive)
    }

    override fun getAllUsers(): Flow<List<User>> {
        return repository.getAllUsers()
    }

    override fun getUserDetail(username: String): Flow<Result<User>> {
        return repository.getUserDetail(username)
    }

    override fun getUserFollowers(username: String): Flow<Result<List<User>>> {
        return repository.getUserFollowers(username)
    }

    override fun getUserFollowing(username: String): Flow<Result<List<User>>> {
        return repository.getUserFollowing(username)
    }

    override suspend fun insertUser(user: User) {
        return repository.insertUser(user)
    }

    override suspend fun deleteUser(username: String) {
        return repository.deleteUser(username)
    }

    override fun isFavorite(username: String): Flow<Int> {
        return repository.isFavorite(username)
    }

}