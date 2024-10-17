package com.example.assignment3.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.assignment3.R
import com.example.assignment3.data.Workout
import com.example.assignment3.data.WorkoutViewModel
import com.example.assignment3.databinding.FragmentAddBinding

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

        // Save workout on button click
        binding.saveBtn.setOnClickListener {
            val workoutName = binding.workoutNameEt.text.toString()
            val workoutDate = binding.workoutDateEt.text.toString()
            val workoutDuration = binding.workoutDurationEt.text.toString()

            if (workoutName.isNotEmpty() && workoutDate.isNotEmpty()) {
                val workout =
                    Workout(name = workoutName, date = workoutDate, duration = workoutDuration)

                // Save workout using ViewModel
                workoutViewModel.addWorkout(workout)

                findNavController().navigate(R.id.action_addWorkoutFragment_to_workoutListFragment)
            } else {
                // Handle input validation
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}