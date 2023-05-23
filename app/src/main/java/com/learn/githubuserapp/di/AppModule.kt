package com.learn.githubuserapp.di

import com.learn.githubuserapp.core.domain.usecase.UserInteractor
import com.learn.githubuserapp.core.domain.usecase.UserUseCase
import com.learn.githubuserapp.presentation.ui.detail.DetailViewModel
import com.learn.githubuserapp.presentation.ui.home.MainViewModel
import com.learn.githubuserapp.presentation.ui.setting.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val useCase = module {
    factory<UserUseCase> { UserInteractor(get()) }
}

val viewModels = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::DetailViewModel)
    viewModelOf(::SettingViewModel)
}