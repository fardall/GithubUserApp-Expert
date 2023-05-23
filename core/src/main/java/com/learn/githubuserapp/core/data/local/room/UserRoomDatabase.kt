package com.learn.githubuserapp.core.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.learn.githubuserapp.core.data.local.entity.UserEntity
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(entities = [UserEntity::class], version = 5)
abstract class UserRoomDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    companion object {
        @Volatile
        private var INSTANCE: UserRoomDatabase? = null
        private val passphrase = SQLiteDatabase.getBytes("github".toCharArray())
        private val factory = SupportFactory(passphrase)
        @JvmStatic
        fun getDatabase(context: Context): UserRoomDatabase {
            if (INSTANCE == null) {
                synchronized(UserRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        UserRoomDatabase::class.java, "user_database")
                        .fallbackToDestructiveMigration()
                        .openHelperFactory(factory)
                        .build()
                }
            }
            return INSTANCE as UserRoomDatabase
        }
    }
}