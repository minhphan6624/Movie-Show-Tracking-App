package com.example.assignment3.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.assignment3.data.Entity.MovieShow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieShowViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MovieShowRepository
    private val _searchQuery = MutableLiveData<String>("")
    private val _currentFilter = MutableLiveData<String?>(null)
    val allMoviesShows: LiveData<List<MovieShow>>

    init {
        val movieShowDAO = AppDatabase.getDatabase(application).movieShowDAO()
        repository = MovieShowRepository(movieShowDAO)
        allMoviesShows = repository.allMoviesShows
    }

    val movieShows = MediatorLiveData<List<MovieShow>>().apply {
        // Set initial value
        addSource(repository.allMoviesShows) { result ->
            value = result
        }
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

    // Search functionality
    fun searchMovieShows(query: String): LiveData<List<MovieShow>> {
        return repository.searchMovieShows(query)
    }

    // Filter functionality
    fun getMovieShowsByStatus(status: String): LiveData<List<MovieShow>> {
        return repository.getMovieShowsByStatus(status)
    }

}