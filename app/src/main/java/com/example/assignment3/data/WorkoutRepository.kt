package com.example.assignment3.data

import androidx.lifecycle.LiveData

class WorkoutRepository(private val workoutDao: WorkoutDAO) {
    val readAllWorkout: LiveData<List<Workout>> = workoutDao.readAllData()

    fun addWorkout (workout: Workout) {
        workoutDao.addWorkout(workout)
    }
}