package com.example.assignment3.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Workout::class], version = 1, exportSchema = false)
abstract class WorkoutDatabase: RoomDatabase() {

    abstract fun workoutDAO(): WorkoutDAO

    //Everything in this object will be visible to other classes
    companion object {

        @Volatile
        private var INSTANCE: WorkoutDatabase? = null

        fun getDatabase(context: Context) : WorkoutDatabase {

            // Check if the db already exists
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            // Otherwise, create an instance of the Db
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WorkoutDatabase::class.java,
                    "workout_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}