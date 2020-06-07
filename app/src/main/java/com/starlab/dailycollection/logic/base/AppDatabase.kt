package com.starlab.dailycollection.logic.base

import android.content.Context
import android.widget.Toast
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.starlab.dailycollection.logic.base.dao.DCollectionDao
import com.starlab.dailycollection.logic.base.dao.UserDao
import com.starlab.dailycollection.logic.model.DCollection
import com.starlab.dailycollection.logic.model.User

@Database(version = 1, entities = arrayOf(User::class, DCollection::class))
abstract class AppDatabase : RoomDatabase() {

    abstract  fun userDao() : UserDao

    abstract  fun dCollectionDao(): DCollectionDao

    companion object {

        private var instance: AppDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): AppDatabase {

            instance?.let {
                return it
            }
            return instance ?: Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "dcs_database.db")
                .build().apply {
                    instance = this
                    //dcs_star
                }
        }

    }
}