package com.example.assignment3.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WorkoutViewModel(application: Application) : AndroidViewModel(application)
{
    private val readAllData: LiveData<List<Workout>>
    private val repository: WorkoutRepository

    init {
        val workoutDao = WorkoutDatabase.getDatabase(application).workoutDAO()
        repository = WorkoutRepository(workoutDao)
        readAllData = repository.readAllWorkout
    }

    // Public getter for readAllData
    fun getAllWorkouts(): LiveData<List<Workout>> {
        return readAllData
    }

    fun addWorkout(workout: Workout){
        // use dispatcher to let the code to run in the background/worker thread
        viewModelScope.launch(Dispatchers.IO){
            repository.addWorkout(workout)
        }
    }

    fun updateWorkout(workout: Workout) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateWorkout(workout)
        }
    }

    fun deleteWorkout(workout: Workout) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteWorkout(workout)
        }
    }
}