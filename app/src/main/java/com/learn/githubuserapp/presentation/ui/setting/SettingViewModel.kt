package com.learn.githubuserapp.presentation.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.learn.githubuserapp.core.domain.usecase.UserUseCase
import kotlinx.coroutines.launch

class SettingViewModel(private val useCase: UserUseCase) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> = useCase.getTheme().asLiveData()

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            useCase.saveTheme(isDarkModeActive)
        }
    }
}