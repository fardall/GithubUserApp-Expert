package com.learn.githubuserapp.favorite

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val favorite = module {
    viewModelOf(::FavoriteViewModel)
}