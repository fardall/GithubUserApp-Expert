package com.learn.githubuserapp.core.utils

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.learn.githubuserapp.core.data.remote.response.FollowResponseItem
import com.learn.githubuserapp.core.data.remote.response.ItemsUser
import com.learn.githubuserapp.core.data.remote.response.UserResponse
import com.learn.githubuserapp.core.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun <T> LifecycleOwner.collectLifecycleFlow(flow: Flow<T>, collect: suspend (T) ->Unit)  {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect(collect)
        }
    }

}

fun ItemsUser.toDomain() =
    User(
        username = login ?: "",
        photoUrl = avatarUrl ?: "",
    )

fun UserResponse.toDomain() =
    User(
        name = name ?: "",
        username = login ?: "",
        photoUrl = avatarUrl ?: "",
        followers = followers.toString(),
        following = following.toString(),
    )

fun FollowResponseItem.toDomain() =
    User(
        username = login ?: "",
        photoUrl = avatarUrl ?: "",
    )

fun mapEntitiesToDomain(entities: List<com.learn.githubuserapp.core.data.local.entity.UserEntity>) =
    entities.map {
        User(
            id = it.id,
            username = it.username ?: "",
            photoUrl = it.avatarUrl ?: "",
        )
    }

fun User.toEntity() = com.learn.githubuserapp.core.data.local.entity.UserEntity(
    id = id,
    username = username,
    avatarUrl = photoUrl,
)

const val TAG = "asd"

const val BASE_API_URL = "https://api.github.com/"

const val BASE_SITE_URL = "https://www.github.com/"