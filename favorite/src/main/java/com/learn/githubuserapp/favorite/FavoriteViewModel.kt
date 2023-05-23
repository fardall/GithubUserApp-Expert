package com.learn.githubuserapp.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.githubuserapp.core.domain.model.User
import com.learn.githubuserapp.core.domain.usecase.UserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(private val useCase: UserUseCase) : ViewModel() {

    private var _favUsers = MutableStateFlow<List<User>>(listOf())
    val favUsers: StateFlow<List<User>> = _favUsers

    fun getAllUsers() {
        viewModelScope.launch {
            useCase.getAllUsers().collect {
                _favUsers.value = it
            }
        }
    }
}