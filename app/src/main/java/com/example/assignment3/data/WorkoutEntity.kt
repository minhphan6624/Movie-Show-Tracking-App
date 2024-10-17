package com.example.assignment3.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,  // Unique ID for each workout
    val name: String,  // Name of the workout (e.g., "Leg Day")
    val date: String,  // Date of the workout (e.g., "2024-10-17")
    val duration: String? = null
)
