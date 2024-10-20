package com.example.assignment3.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.assignment3.data.DAO.ExerciseDAO
import com.example.assignment3.data.DAO.WorkoutDAO
import com.example.assignment3.data.Entity.Exercise
import com.example.assignment3.data.Entity.Workout

@Database(entities = [Workout::class, Exercise::class], version = 2, exportSchema = false)
abstract class WorkoutDatabase: RoomDatabase() {

    abstract fun workoutDAO(): WorkoutDAO
    abstract fun exerciseDao(): ExerciseDAO

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
                )
                .fallbackToDestructiveMigration()  // This line allows destructive migration
                .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}