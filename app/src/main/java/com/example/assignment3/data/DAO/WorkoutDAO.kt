package com.example.assignment3.data.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.assignment3.data.Entity.Workout

@Dao
interface WorkoutDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE) //If there's a workout with the exact same details, ignore it
    fun addWorkout(workout: Workout) //suspend for coroutines

    @Query("SELECT * FROM workouts ORDER BY id ASC")
    fun readAllData() : LiveData<List<Workout>>

    @Update
    suspend fun updateWorkout(workout: Workout)

    @Delete
    suspend fun deleteWorkout(workout: Workout): Void

    // Optional: Add a query to delete all workouts
    @Query("DELETE FROM workouts")
    suspend fun deleteAllWorkouts() : Unit

    @Transaction
    @Query("SELECT * FROM workouts ORDER BY date DESC")
    fun getAllWorkoutsWithExercises(): LiveData<List<Workout>>

    // Get a single workout
    @Transaction
    @Query("SELECT * FROM workouts WHERE id = :workoutId")
    suspend fun getWorkoutWithExercises(workoutId: Int): Workout
}