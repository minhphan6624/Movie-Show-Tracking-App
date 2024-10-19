package com.example.assignment3.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.assignment3.R
import com.example.assignment3.data.Entity.Workout
import com.example.assignment3.data.WorkoutViewModel
import com.example.assignment3.databinding.FragmentAddBinding
import com.google.android.material.snackbar.Snackbar

class AddWorkoutFragment : Fragment() {

    private lateinit var workoutViewModel: WorkoutViewModel  // Declare the ViewModel
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

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

        binding.saveBtn.setOnClickListener {
            addWorkout()
        }

    }

    private fun addWorkout() {
        val name = binding.addWorkoutName.text.toString()
        val date = binding.addWorkoutDate.text.toString()
        val duration = binding.addWorkoutDuration.text.toString()

        if (validateInput(name, date, duration)) {
            val workout = Workout(name = name, date = date, duration = duration)
            workoutViewModel.addWorkout(workout)
            Snackbar.make(requireView(), "Workout added successfully", Snackbar.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addWorkoutFragment_to_workoutListFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateInput(name: String, date: String, duration: String): Boolean {
        return name.isNotBlank() && date.isNotBlank() && duration.isNotBlank()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}