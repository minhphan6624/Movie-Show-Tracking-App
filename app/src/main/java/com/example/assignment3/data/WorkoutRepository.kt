package com.example.assignment3.data

import androidx.lifecycle.LiveData
import com.example.assignment3.data.DAO.ExerciseDAO
import com.example.assignment3.data.DAO.WorkoutDAO
import com.example.assignment3.data.Entity.Workout

class WorkoutRepository(private val workoutDao: WorkoutDAO, private val exerciseDao: ExerciseDAO) {

    val allWorkout: LiveData<List<Workout>> = workoutDao.readAllData()

    suspend fun addWorkout(workout: Workout) {
        workoutDao.addWorkout(workout)
    }

    suspend fun updateWorkout(workout: Workout) {
        workoutDao.updateWorkout(workout)
    }

    suspend fun deleteWorkout(workout: Workout) {
        workoutDao.deleteWorkout(workout)
    }

    suspend fun deleteAllWorkouts() {
        workoutDao.deleteAllWorkouts()
    }
}