package com.example.assignment3.data

import androidx.lifecycle.LiveData
import com.example.assignment3.data.DAO.ExerciseDAO
import com.example.assignment3.data.DAO.WorkoutDAO
import com.example.assignment3.data.Entity.Exercise
import com.example.assignment3.data.Entity.Workout

class WorkoutRepository(private val workoutDao: WorkoutDAO, private val exerciseDao: ExerciseDAO) {

    val allWorkouts: LiveData<List<Workout>> = workoutDao.getAllWorkouts()

    suspend fun insertWorkout(workout: Workout) = workoutDao.insertWorkout(workout)
    suspend fun updateWorkout(workout: Workout) = workoutDao.updateWorkout(workout)
    suspend fun deleteWorkout(workout: Workout) = workoutDao.deleteWorkout(workout)
    suspend fun getWorkoutById(workoutId: Int): Workout = workoutDao.getWorkoutById(workoutId)


    // ---- Exercise ----

    suspend fun insertExercise(exercise: Exercise) = exerciseDao.insertExercise(exercise)
    suspend fun updateExercise(exercise: Exercise) = exerciseDao.updateExercise(exercise)
    suspend fun deleteExercise(exercise: Exercise) = exerciseDao.deleteExercise(exercise)

    fun getExercisesForWorkout(workoutId: Int): LiveData<List<Exercise>> = exerciseDao.getExercisesForWorkout(workoutId)


}