package com.example.assignment3.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.assignment3.data.Entity.MovieShow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieShowViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MovieShowRepository
    val allMoviesShows: LiveData<List<MovieShow>>

    init {
        val movieShowDAO = AppDatabase.getDatabase(application).movieShowDAO()
        repository = MovieShowRepository(movieShowDAO)
        allMoviesShows = repository.allMoviesShows
    }

    fun insert(movieShow: MovieShow) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(movieShow)
    }

    fun update(movieShow: MovieShow) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(movieShow)
    }

    fun delete(movieShow: MovieShow) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(movieShow)
    }

    fun getMovieShowById(id: Int): LiveData<MovieShow> {
        return repository.getMovieShowById(id)
    }

}