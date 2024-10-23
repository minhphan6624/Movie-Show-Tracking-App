package com.example.assignment3.data.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.assignment3.data.Entity.MovieShow

@Dao
interface MovieShowDAO {
    @Query("SELECT * FROM movies_shows ORDER BY title ASC")
    fun getAllMoviesShows(): LiveData<List<MovieShow>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieShow(movieShow: MovieShow)

    @Update
    suspend fun updateMovieShow(movieShow: MovieShow)

    @Delete
    suspend fun deleteMovieShow(movieShow: MovieShow)

    @Query("SELECT * FROM movies_shows WHERE id = :id")
    fun getMovieShowById(id: Int): LiveData<MovieShow>

    // Return movies/shows of a specific status
    @Query("SELECT * FROM movies_shows WHERE status = :status ORDER BY title ASC")
    fun getMovieShowsByStatus(status: String): LiveData<List<MovieShow>>
}