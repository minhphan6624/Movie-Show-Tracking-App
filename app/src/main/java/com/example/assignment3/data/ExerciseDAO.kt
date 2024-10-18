package com.example.assignment3.data

import androidx.room.*

interface ExerciseDAO {
    @Insert
    suspend fun addExercise(exercise: Exercise)

    @Update
    suspend fun updateExercise(exercise: Exercise)

    @Delete
    suspend fun deleteExercise(exercise: Exercise)

    @Query("SELECT * FROM exercises WHERE workoutId = :workoutId")
    suspend fun getExerciseForWorkout(workoutId : Int): List<Exercise>
}