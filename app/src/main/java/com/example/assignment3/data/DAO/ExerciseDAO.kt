package com.example.assignment3.data.DAO

import androidx.room.*
import com.example.assignment3.data.Entity.Exercise

interface ExerciseDAO {
    @Insert
    suspend fun addExercise(exercise: Exercise)

    @Update
    suspend fun updateExercise(exercise: Exercise)

    @Delete
    suspend fun deleteExercise(exercise: Exercise)

    // Get all the exercises for a workout
    @Query("SELECT * FROM exercises WHERE workoutId = :workoutId")
    suspend fun getExerciseForWorkout(workoutId : Int): List<Exercise>

    // Get all the completed exercise for a workout
    @Query("SELECT * FROM exercises WHERE workoutId = :workoutId AND completed = 1")
    suspend fun getCompletedExercisesForWorkout(workoutId: Int): List<Exercise>
}