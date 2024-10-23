package com.example.assignment3.data

import androidx.lifecycle.LiveData
import com.example.assignment3.data.DAO.MovieShowDAO
import com.example.assignment3.data.Entity.MovieShow

class MovieShowRepository(private val movieShowDAO: MovieShowDAO) {

    val allMoviesShows: LiveData<List<MovieShow>> = movieShowDAO.getAllMoviesShows()

    suspend fun insert(movieShow: MovieShow) {
        movieShowDAO.insertMovieShow(movieShow)
    }

    suspend fun update(movieShow: MovieShow) {
        movieShowDAO.updateMovieShow(movieShow)
    }

    suspend fun delete(movieShow: MovieShow) {
        movieShowDAO.deleteMovieShow(movieShow)
    }

    fun getMovieShowById(id: Int): LiveData<MovieShow> {
        return movieShowDAO.getMovieShowById(id)
    }

    fun getMovieShowsByStatus(status: String): LiveData<List<MovieShow>> {
        return movieShowDAO.getMovieShowsByStatus(status)
    }

    fun searchMovieShows(query: String): LiveData<List<MovieShow>> {
        return movieShowDAO.searchMovieShows(query)
    }
}