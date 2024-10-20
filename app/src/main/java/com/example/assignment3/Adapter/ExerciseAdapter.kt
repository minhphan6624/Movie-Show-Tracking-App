package com.example.assignment3.Adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment3.data.Entity.Exercise
import com.example.assignment3.databinding.ItemExerciseBinding

class ExerciseAdapter : ListAdapter<Exercise, ExerciseAdapter.ExerciseViewHolder>(ExerciseDiffCallback()) {

    private var onExerciseChangedListener: ((Exercise) -> Unit)? = null
    private var onExerciseDeletedListener: ((Exercise) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ItemExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = getItem(position)
        holder.bind(exercise)
    }

    inner class ExerciseViewHolder(private val binding: ItemExerciseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: Exercise) {
            binding.exerciseName.setText(exercise.name)
            binding.exerciseWeight.setText(exercise.weight.toString())
            binding.exerciseReps.setText(exercise.reps.toString())

            binding.exerciseName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    onExerciseChangedListener?.invoke(exercise.copy(name = s.toString()))
                }
            })

            binding.exerciseWeight.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    onExerciseChangedListener?.invoke(exercise.copy(weight = s.toString().toFloatOrNull() ?: 0f))
                }
            })

            binding.exerciseReps.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    onExerciseChangedListener?.invoke(exercise.copy(reps = s.toString().toIntOrNull() ?: 0))
                }
            })

            binding.deleteExercise.setOnClickListener {
                onExerciseDeletedListener?.invoke(exercise)
            }
        }
    }

    fun setOnExerciseChangedListener(listener: (Exercise) -> Unit) {
        onExerciseChangedListener = listener
    }

    fun setOnExerciseDeletedListener(listener: (Exercise) -> Unit) {
        onExerciseDeletedListener = listener
    }

    class ExerciseDiffCallback : DiffUtil.ItemCallback<Exercise>() {
        override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
            return oldItem == newItem
        }
    }
}