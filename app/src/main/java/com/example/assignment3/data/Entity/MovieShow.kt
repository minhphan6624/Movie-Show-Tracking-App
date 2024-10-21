package com.example.assignment3.data.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "movies_shows")
data class MovieShow(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var title: String,
    var type: String, // "Movie" or "TV Show"
    var genre: String,
    var releaseDate: String,
    var status: String, // "To Watch", "Watching", "Completed"
    var rating: Float = 0f,
    var notes: String? = null
)