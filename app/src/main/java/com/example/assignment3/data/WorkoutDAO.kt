package com.example.assignment3.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface WorkoutDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE) //If there's a workout with the exact same details, ignore it
    fun addWorkout(workout: Workout) //suspend for coroutines

    @Query("SELECT * FROM workouts ORDER BY id ASC")
    fun readAllData() : LiveData<List<Workout>>

    @Update
    suspend fun updateWorkout(workout: Workout)

    @Delete
    suspend fun deleteWorkout(workout: Workout)

    // Optional: Add a query to delete all workouts
    @Query("DELETE FROM workouts")
    suspend fun deleteAllWorkouts()
}