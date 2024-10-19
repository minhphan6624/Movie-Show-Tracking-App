package com.example.assignment3.data.Entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

@Entity(tableName = "workouts")
@Parcelize
data class Workout(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,  // Unique ID for each workout
    val name: String,  // Name of the workout (e.g., "Leg Day")
    val date: String,  // Date of the workout (e.g., "2024-10-17")
    val duration: String? = null

) : Parcelable {
    @Relation (
        parentColumn = "id",
        entityColumn = "workoutId"
    )
    var exercises: List<Exercise> = listOf()
}
