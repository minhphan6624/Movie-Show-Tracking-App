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
    suspend fun insertWorkout(workout: Workout) //suspend for coroutines

    @Update
    suspend fun updateWorkout(workout: Workout)

    @Delete
    suspend fun deleteWorkout(workout: Workout)

    @Query("SELECT * FROM workouts ORDER BY id ASC")
    fun getAllWorkouts() : LiveData<List<Workout>>

    // Get a single workout with exercises by Id
    @Transaction
    @Query("SELECT * FROM workouts WHERE id = :workoutId")
    suspend fun getWorkoutById(workoutId: Int): Workout


}