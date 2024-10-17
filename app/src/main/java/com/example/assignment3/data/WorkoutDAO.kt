package com.example.assignment3.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WorkoutDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE) //If there's a workout with the exact same details, ignore it
    fun addWorkout(workout: Workout) //suspend for coroutines

    @Query("SELECT * FROM workouts ORDER BY id ASC")
    fun readAllData() : LiveData<List<Workout>>
}