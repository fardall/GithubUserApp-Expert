package com.learn.githubuserapp.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class FollowResponseItem(

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

)
