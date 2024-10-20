package com.example.assignment3.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.assignment3.data.Entity.Exercise
import com.example.assignment3.data.Entity.Workout
import com.example.assignment3.data.Entity.WorkoutWithExercises
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WorkoutViewModel(application: Application) : AndroidViewModel(application)
{
    private val repository: WorkoutRepository
    private val allWorkouts: LiveData<List<Workout>>

    init {
        val workoutDAO = WorkoutDatabase.getDatabase(application).workoutDAO()
        val exerciseDAO = WorkoutDatabase.getDatabase(application).exerciseDao()
        repository = WorkoutRepository(workoutDAO, exerciseDAO)
        allWorkouts = repository.allWorkouts
    }

    // Public getter for readAllData
    fun getAllWorkouts(): LiveData<List<Workout>> {
        return allWorkouts
    }

    fun getWorkoutById(workoutId: Int): LiveData<WorkoutWithExercises> =
        repository.getWorkoutById(workoutId)

    suspend fun insertWorkout(workout: Workout) : Long {
        return repository.insertWorkout(workout)
    }

    fun updateWorkout(workout: Workout) = viewModelScope.launch(Dispatchers.IO) {
            repository.updateWorkout(workout)
        }

    fun deleteWorkout(workout: Workout) = viewModelScope.launch(Dispatchers.IO) {
            repository.deleteWorkout(workout)
    }

    // ------- Exercise management -------
    fun insertExercise(exercise: Exercise) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertExercise(exercise)
    }

    fun updateExercise(exercise: Exercise) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateExercise(exercise)
    }

    fun deleteExercise(exercise: Exercise) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteExercise(exercise)
    }

    fun getExercisesForWorkout(workoutId: Int): LiveData<List<Exercise>> {
        return repository.getExercisesForWorkout(workoutId)
    }

}