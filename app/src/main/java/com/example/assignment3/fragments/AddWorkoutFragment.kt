package com.example.assignment3.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment3.Adapter.ExerciseAdapter
import com.example.assignment3.R
import com.example.assignment3.data.Entity.Exercise
import com.example.assignment3.data.Entity.Workout
import com.example.assignment3.data.WorkoutViewModel
import com.example.assignment3.databinding.FragmentAddBinding
import com.google.android.material.snackbar.Snackbar

class AddWorkoutFragment : Fragment() {

    private lateinit var workoutViewModel: WorkoutViewModel  // Declare the ViewModel
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var exerciseAdapter: ExerciseAdapter
    private val exercises = mutableListOf<Exercise>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        workoutViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)

        setupExerciseRecyclerView()
        setupListeners()


    }

    private fun setupExerciseRecyclerView() {
        exerciseAdapter = ExerciseAdapter()
        binding.exerciseList.apply {
            adapter = exerciseAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupListeners() {
        binding.addExerciseButton.setOnClickListener {
            exercises.add(Exercise(name = "", weight = 0f, reps = 0))
            exerciseAdapter.submitList(exercises.toList())
        }

        binding.saveBtn.setOnClickListener {
            saveWorkout()
        }
    }

    private fun saveWorkout() {
        val name = binding.addWorkoutName.text.toString()
        val date = binding.addWorkoutDate.text.toString()
        val duration = binding.addWorkoutDuration.text.toString()

        if (validateInput(name, date, duration)) {
            viewLifecycleOwner.lifecycleScope.launch {
               try {
                   val workout = Workout(name = name, date = date, duration = duration)
                   val workoutId = workoutViewModel.insertWorkout(workout)


                   // Create new Exercise objects with the correct workoutId
                   val exercisesWithId = exercises.map { exercise ->
                       exercise.copy(workoutId = workoutId.toInt())
                   }

                   exercisesWithId.forEach { exercise ->
                       workoutViewModel.insertExercise(exercise)
                   }

                   Snackbar.make(requireView(), "Workout added successfully", Snackbar.LENGTH_LONG)
                       .setAction("UNDO") {
                           viewLifecycleOwner.lifecycleScope.launch {
                               workoutViewModel.deleteWorkout(workout)
                           }
                       }
                       .show()
                   findNavController().navigate(R.id.action_addWorkoutFragment_to_workoutListFragment)
               }
               catch (e: Exception) {
                   Toast.makeText(context, "Error saving workout: ${e.message}", Toast.LENGTH_SHORT).show()
               }

            }
        } else {
            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

//    private fun addWorkout() {
//        val name = binding.addWorkoutName.text.toString()
//        val date = binding.addWorkoutDate.text.toString()
//        val duration = binding.addWorkoutDuration.text.toString()
//
//        if (validateInput(name, date, duration)) {
//            val workout = Workout(name = name, date = date, duration = duration)
//            workoutViewModel.insertWorkout(workout)
//            Snackbar.make(requireView(), "Workout added successfully", Snackbar.LENGTH_LONG).show()
//
//        } else {
//            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
//        }
//    }

    private fun validateInput(name: String, date: String, duration: String): Boolean {
        return name.isNotBlank() && date.isNotBlank() && duration.isNotBlank()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}