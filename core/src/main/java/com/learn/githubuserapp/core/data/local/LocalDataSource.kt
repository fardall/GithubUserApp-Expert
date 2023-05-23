package com.learn.githubuserapp.core.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.learn.githubuserapp.core.data.local.datastore.UserPreferencesFactory
import com.learn.githubuserapp.core.data.local.entity.UserEntity
import com.learn.githubuserapp.core.data.local.room.UserDao
import kotlinx.coroutines.flow.map

class LocalDataSource(
    private val userDao: com.learn.githubuserapp.core.data.local.room.UserDao,
    private val dataStore: DataStore<Preferences>
) {
    fun getTheme() = dataStore.data.map { preferences ->
        preferences[com.learn.githubuserapp.core.data.local.datastore.UserPreferencesFactory.UserPreferencesKey.THEME_KEY] ?: false
    }

    suspend fun saveTheme(isDarkMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[com.learn.githubuserapp.core.data.local.datastore.UserPreferencesFactory.UserPreferencesKey.THEME_KEY] = isDarkMode
        }
    }

    suspend fun insertUser(userEntity: com.learn.githubuserapp.core.data.local.entity.UserEntity) {
        userDao.insert(userEntity)
    }

    suspend fun deleteUser(username: String) {
        userDao.delete(username)
    }

    fun isFavorite(username: String) = userDao.isFavorite(username)

    fun getAllUsers() = userDao.getAllUsers()
}