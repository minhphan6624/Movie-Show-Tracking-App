package com.example.assignment3.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.assignment3.data.Entity.MovieShow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieShowViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MovieShowRepository
    val allMoviesShows: LiveData<List<MovieShow>>

    private val _currentFilter = MutableLiveData<String?>(null)

    init {
        val movieShowDAO = AppDatabase.getDatabase(application).movieShowDAO()
        repository = MovieShowRepository(movieShowDAO)
        allMoviesShows = repository.allMoviesShows
    }

    val filteredMovieShows: LiveData<List<MovieShow>> = _currentFilter.switchMap { filter ->
        filter?.let {
            repository.getMovieShowsByStatus(it)
        } ?: allMoviesShows
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

    fun setFilter(status: String?) {
        _currentFilter.value = status
    }

}