package com.example.assignment3.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.assignment3.R
import com.example.assignment3.data.Workout
import com.example.assignment3.data.WorkoutViewModel
import com.example.assignment3.databinding.FragmentEditBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.Deprecated as Deprecated


class EditWorkoutFragment : Fragment() {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private lateinit var workoutViewModel: WorkoutViewModel

    private val args: EditWorkoutFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit_workout, menu)
    }

    @Suppress("DEPRECATION")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> {
                showDeleteConfirmationDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateWorkout() {
        val updatedWorkout = Workout(
            id = args.workout.id,
            name = binding.editWorkoutName.text.toString(),
            date = binding.editWorkoutDate.text.toString(),
            duration = binding.editWorkoutDuration.text.toString()
        )

        val originalWorkout = args.workout.copy()
        workoutViewModel.updateWorkout(args.workout)

        Snackbar.make(requireView(), "Workout updated", Snackbar.LENGTH_LONG)
            .setAction("UNDO") {
                workoutViewModel.updateWorkout(originalWorkout)
            }
            .show()

        findNavController().navigate(R.id.action_editWorkoutFragment_to_workoutListFragment)
    }

    private fun deleteWorkout() {

        val workoutToDelete = args.workout.copy()

        workoutViewModel.deleteWorkout(args.workout)
        Snackbar.make(requireView(), "Workout deleted", Snackbar.LENGTH_LONG)
            .setAction("UNDO") {
                workoutViewModel.addWorkout(workoutToDelete)
            }.show()
        findNavController().navigate(R.id.action_editWorkoutFragment_to_workoutListFragment)
    }

    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Workout")
            .setMessage("Are you sure you want to delete this workout?")
            .setPositiveButton("Delete") { _, _ ->
                // Handle the delete action
                deleteWorkout()
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}