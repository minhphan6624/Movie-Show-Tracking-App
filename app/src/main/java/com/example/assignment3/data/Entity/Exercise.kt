package com.example.assignment3.data.Entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "exercises",
    foreignKeys = [ForeignKey(
        entity = Workout::class,
        parentColumns = ["id"],
        childColumns = ["workoutId"],
        onDelete = ForeignKey.CASCADE
    )])
@Parcelize
data class Exercise(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val workoutId: Int,
    val name: String,
    val weight: Float, // Weight in kg
    val reps: Int,
    var completed: Boolean = false
) : Parcelable {

}