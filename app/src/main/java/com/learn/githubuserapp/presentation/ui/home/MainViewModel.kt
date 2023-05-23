package com.learn.githubuserapp.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.learn.githubuserapp.core.domain.model.User
import com.learn.githubuserapp.core.domain.usecase.UserUseCase
import com.learn.githubuserapp.core.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val useCase: UserUseCase) : ViewModel() {

    private val _foundUsers = MutableStateFlow<Result<List<User>>>(Result.Success(emptyList()))
    val foundUsers: StateFlow<Result<List<User>>> = _foundUsers

    fun findUser(username: String) {
        viewModelScope.launch {
            useCase.findUser(username).collect {
                _foundUsers.value = it
            }
        }
    }

    fun getThemeSettings(): LiveData<Boolean> = useCase.getTheme().asLiveData()

}