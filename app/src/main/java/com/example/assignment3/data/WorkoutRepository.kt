package com.example.assignment3.data

import androidx.lifecycle.LiveData

class WorkoutRepository(private val workoutDao: WorkoutDAO) {
    val readAllWorkout: LiveData<List<Workout>> = workoutDao.readAllData()

    suspend fun addWorkout(workout: Workout) {
        workoutDao.addWorkout(workout)
    }

    suspend fun updateWorkout(workout: Workout) {
        workoutDao.updateWorkout(workout)
    }

    suspend fun deleteWorkout(workout: Workout) {
        workoutDao.deleteWorkout(workout)
    }

    // Optional: Add a function to delete all workouts
    suspend fun deleteAllWorkouts() {
        workoutDao.deleteAllWorkouts()
    }
}