package com.learn.githubuserapp.core.domain.model

data class User(
    val id: Int = 0,
    val name: String = "",
    val username: String = "",
    val photoUrl: String = "",
    val followers: String = "",
    val following: String = "",
)
