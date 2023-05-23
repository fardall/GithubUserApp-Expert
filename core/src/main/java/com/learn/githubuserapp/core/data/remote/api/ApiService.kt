package com.learn.githubuserapp.core.data.remote.api

import com.learn.githubuserapp.core.data.remote.response.FollowResponseItem
import com.learn.githubuserapp.core.data.remote.response.SearchResponse
import com.learn.githubuserapp.core.data.remote.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Headers("Authorization: token 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")
    @GET("search/users")
    suspend fun findUser(
        @Query("q") q: String
    ): SearchResponse

    @Headers("Authorization: token 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")
    @GET("users/{username}")
    suspend fun getUserDetail(
        @Path("username") username: String
    ): UserResponse

    @Headers("Authorization: token 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")
    @GET("users/{username}/followers")
    suspend fun getUserFollowers(
        @Path("username") username: String
    ): List<FollowResponseItem>

    @Headers("Authorization: token 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")
    @GET("users/{username}/following")
    suspend fun getUserFollowing(
        @Path("username") username: String
    ): List<FollowResponseItem>
}