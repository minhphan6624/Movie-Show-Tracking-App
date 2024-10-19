package com.example.assignment3.data.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.assignment3.data.Entity.Exercise

interface ExerciseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: Exercise)

    @Update
    suspend fun updateExercise(exercise: Exercise)

    @Delete
    suspend fun deleteExercise(exercise: Exercise)

    // Get all the exercises for a workout
    @Query("SELECT * FROM exercises WHERE workoutId = :workoutId")
    fun getExercisesForWorkout(workoutId : Int):  LiveData<List<Exercise>>

    // Get all the COMPLETED exercise for a workout
    @Query("SELECT * FROM exercises WHERE workoutId = :workoutId AND completed = 1")
    suspend fun getCompletedExercisesForWorkout(workoutId: Int): LiveData<List<Exercise>>
}