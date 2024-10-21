package com.example.assignment3.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.assignment3.data.DAO.MovieShowDAO
import com.example.assignment3.data.Entity.MovieShow

@Database(entities = [MovieShow::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun movieShowDAO(): MovieShowDAO

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "movie_show_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }
}