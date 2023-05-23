package com.learn.githubuserapp.core.data.remote

import com.learn.githubuserapp.core.data.remote.api.ApiService
import com.learn.githubuserapp.core.utils.Result
import com.learn.githubuserapp.core.utils.toDomain
import kotlinx.coroutines.flow.flow


class NetworkDataSource(
    private val apiService: ApiService,
) {
    fun findUser(username: String) = flow {
        emit(Result.Loading)
        try {
            val response = apiService.findUser(username)
            val responseItems = response.items?.map { it!!.toDomain() } ?: emptyList()
            emit(Result.Success(responseItems))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getUserDetail(username: String) = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getUserDetail(username).toDomain()
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getUserFollowers(username: String) = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getUserFollowers(username).map { it.toDomain() }
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getUserFollowing(username: String) = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getUserFollowing(username).map { it.toDomain() }
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }
}