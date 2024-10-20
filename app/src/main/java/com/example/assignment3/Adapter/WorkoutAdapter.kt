package com.example.assignment3.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment3.databinding.ItemWorkoutBinding
import com.example.assignment3.data.Entity.Workout

class WorkoutAdapter(private val listener : OnWorkoutClickListener) : RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    private var workoutList = emptyList<Workout>()

    // ViewHolder class
    class WorkoutViewHolder(private val binding: ItemWorkoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(workout: Workout, listener: OnWorkoutClickListener) {
            binding.workoutName.text = workout.name
            binding.workoutDate.text = workout.date
            binding.workoutDuration.text = workout.duration
//            binding.exerciseSummary.text = "${workout.exercises.size} exercises"

            // Set a click listener on the item
            binding.root.setOnClickListener {
                listener.onWorkoutClick(workout)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val binding = ItemWorkoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workout = workoutList[position]
        holder.bind(workout, listener)
    }

    override fun getItemCount(): Int = workoutList.size

    fun setWorkouts(workouts: List<Workout>) {
        this.workoutList = workouts
        notifyDataSetChanged()
    }

    interface OnWorkoutClickListener {
        fun onWorkoutClick(workout: Workout)
    }
}
