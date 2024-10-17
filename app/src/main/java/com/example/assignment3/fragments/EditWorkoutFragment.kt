package com.example.assignment3.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.assignment3.R
import com.example.assignment3.data.Workout
import com.example.assignment3.data.WorkoutViewModel
import com.example.assignment3.databinding.FragmentEditBinding


class EditWorkoutFragment : Fragment() {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private lateinit var workoutViewModel: WorkoutViewModel

    private val args: EditWorkoutFragmentArgs by navArgs()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,  savedInstanceState: Bundle?): View? {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        workoutViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)

        /// Populate fields with existing workout data
        binding.editWorkoutName.setText(args.workout.name)
        binding.editWorkoutDate.setText(args.workout.date)
        binding.editWorkoutDuration.setText(args.workout.duration)

        binding.updateButton.setOnClickListener {
            updateWorkout()
        }

        binding.deleteButton.setOnClickListener {
            deleteWorkout()
        }
    }

    private fun updateWorkout() {
        val updatedWorkout = Workout(
            id = args.workout.id,
            name = binding.editWorkoutName.text.toString(),
            date = binding.editWorkoutDate.text.toString(),
            duration = binding.editWorkoutDuration.text.toString()
        )
        workoutViewModel.updateWorkout(updatedWorkout)
        findNavController().navigate(R.id.action_editWorkoutFragment_to_workoutListFragment)
    }

    private fun deleteWorkout() {
        workoutViewModel.deleteWorkout(args.workout)
        findNavController().navigate(R.id.action_editWorkoutFragment_to_workoutListFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}