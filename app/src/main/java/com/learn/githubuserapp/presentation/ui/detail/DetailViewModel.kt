package com.learn.githubuserapp.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.githubuserapp.core.domain.model.User
import com.learn.githubuserapp.core.utils.Result
import com.learn.githubuserapp.core.domain.usecase.UserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val useCase: UserUseCase) : ViewModel() {

    private var _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _user = MutableStateFlow<Result<User>>(Result.Loading)
    val user: StateFlow<Result<User>> = _user

    private val _userFollowers = MutableStateFlow<Result<List<User>>>(Result.Loading)
    val userFollowers: StateFlow<Result<List<User>>> = _userFollowers

    private val _userFollowing = MutableStateFlow<Result<List<User>>>(Result.Loading)
    val userFollowing: StateFlow<Result<List<User>>> = _userFollowing

    private val _isFavorite = MutableStateFlow(0)
    val isFavorite: StateFlow<Int> = _isFavorite

    fun setUsername(username: String) {
        _username.value = username
    }

    fun getUserDetail(username: String) {
        viewModelScope.launch {
            useCase.getUserDetail(username).collect {
                _user.value = it
            }
        }
    }

    fun getUserFollowers(username: String) {
        viewModelScope.launch {
            useCase.getUserFollowers(username).collect {
                _userFollowers.value = it
            }
        }
    }

    fun getUserFollowing(username: String) {
        viewModelScope.launch {
            useCase.getUserFollowing(username).collect {
                _userFollowing.value = it
            }
        }
    }

    fun insertUser(user: User) {
        viewModelScope.launch {
            useCase.insertUser(user)
        }
    }

    fun deleteUser(username: String) {
        viewModelScope.launch {
            useCase.deleteUser(username)
        }
    }

    fun isFavorite(username: String) {
        viewModelScope.launch {
            useCase.isFavorite(username).collect {
                _isFavorite.value = it
            }
        }
    }
}