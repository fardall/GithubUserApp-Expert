package com.learn.githubuserapp

import android.app.Application
import com.learn.githubuserapp.core.di.dataSources
import com.learn.githubuserapp.core.di.locals
import com.learn.githubuserapp.core.di.networks
import com.learn.githubuserapp.core.di.repository
import com.learn.githubuserapp.di.useCase
import com.learn.githubuserapp.di.viewModels
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                useCase,
                viewModels,
                locals,
                networks,
                dataSources,
                repository,
            )
        }
    }
}