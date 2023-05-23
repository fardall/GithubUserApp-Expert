package com.learn.githubuserapp.core.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class UserPreferencesFactory(
    private val appContext: Context
) {
    fun create(): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(com.learn.githubuserapp.core.data.local.datastore.UserPreferencesFactory.Companion.USER_PREFERENCE) }
        )
    }

    companion object {
        const val USER_PREFERENCE = "user_preferences"
    }

    object UserPreferencesKey {
        val THEME_KEY = booleanPreferencesKey(com.learn.githubuserapp.core.data.local.datastore.UserPreferencesFactory.PreferenceKey.THEME_SETTING)
    }

    object PreferenceKey {
        const val THEME_SETTING = "theme_setting"
    }
}