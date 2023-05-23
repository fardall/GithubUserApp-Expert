package com.learn.githubuserapp.core.di

import com.learn.githubuserapp.core.data.UserRepository
import com.learn.githubuserapp.core.data.local.LocalDataSource
import com.learn.githubuserapp.core.data.local.datastore.UserPreferencesFactory
import com.learn.githubuserapp.core.data.local.room.UserRoomDatabase
import com.learn.githubuserapp.core.data.remote.NetworkDataSource
import com.learn.githubuserapp.core.data.remote.api.ApiConfig
import com.learn.githubuserapp.core.domain.repository.IUserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val locals = module {
    factory { get<UserRoomDatabase>().userDao() }
    single { UserRoomDatabase.getDatabase(androidContext()) }
    single { UserPreferencesFactory(
        androidContext()
    ).create() }
}

val networks = module {
    single { ApiConfig.getApiService() }
}

val dataSources = module {
    single { LocalDataSource(get(), get()) }
    single { NetworkDataSource(get()) }
}

val repository = module {
    single<IUserRepository> { UserRepository(get(), get()) }
}