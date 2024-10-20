package com.example.assignment3.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment3.Adapter.ExerciseAdapter
import com.example.assignment3.R
import com.example.assignment3.data.Entity.Exercise
import com.example.assignment3.data.Entity.Workout
import com.example.assignment3.data.WorkoutViewModel
import com.example.assignment3.databinding.FragmentEditBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class EditWorkoutFragment : Fragment() {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private lateinit var workoutViewModel: WorkoutViewModel

    private val args: EditWorkoutFragmentArgs by navArgs()

    private lateinit var exerciseAdapter: ExerciseAdapter
    private var currentWorkout: Workout? = null
    private val exercises = mutableListOf<Exercise>()

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

        setupRecyclerView()
        observeWorkout()
        setupListeners()
    }

    private fun setupRecyclerView() {

        exerciseAdapter = ExerciseAdapter().apply {
            setOnExerciseChangedListener { updatedExercise ->
                val index = exercises.indexOfFirst { it.id == updatedExercise.id }
                if (index != -1) {
                    exercises[index] = updatedExercise
                }
            }
            setOnExerciseDeletedListener { exerciseToDelete ->
                exercises.removeAll { it.id == exerciseToDelete.id }
                exerciseAdapter.submitList(exercises.toList())
            }
        }

        binding.exerciseList.apply {
            adapter = exerciseAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun observeWorkout() {
        workoutViewModel.getWorkoutById(args.workoutId).observe(viewLifecycleOwner) { workoutWithExercises ->
            workoutWithExercises?.let {
                currentWorkout = it.workout
                binding.editWorkoutName.setText(it.workout.name)
                binding.editWorkoutDate.setText(it.workout.date)
                binding.editWorkoutDuration.setText(it.workout.duration)
                exercises.clear()
                exercises.addAll(it.exercises)
                exerciseAdapter.submitList(exercises.toList())
            }
        }
    }

    private fun setupListeners() {
        binding.addExerciseButton.setOnClickListener {
            exercises.add(Exercise(name = "", weight = 0f, reps = 0, workoutId = args.workoutId))
            exerciseAdapter.submitList(exercises.toList())
        }

        binding.updateButton.setOnClickListener {
            updateWorkout()
        }
    }

    // Action bar
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
        currentWorkout?.let { workout ->
            val updatedWorkout = workout.copy(
                name = binding.editWorkoutName.text.toString(),
                date = binding.editWorkoutDate.text.toString(),
                duration = binding.editWorkoutDuration.text.toString()
            )

            viewLifecycleOwner.lifecycleScope.launch {
                workoutViewModel.updateWorkout(updatedWorkout)
                exercises.forEach { exercise ->
                    if (exercise.id == 0) {
                        workoutViewModel.insertExercise(exercise.copy(workoutId = workout.id))
                    } else {
                        workoutViewModel.updateExercise(exercise)
                    }
                }
                // Navigate back to detail fragment
                findNavController().navigate(
                    EditWorkoutFragmentDirections.actionEditWorkoutFragmentToWorkoutDetailFragment(workout.id)
                )
            }
        }
    }

    private fun deleteWorkout() {
        currentWorkout?.let { workout ->
            viewLifecycleOwner.lifecycleScope.launch {
                workoutViewModel.deleteWorkout(workout)
                // Navigate back to the workout list
                findNavController().navigate(
                    EditWorkoutFragmentDirections.actionEditWorkoutFragmentToWorkoutDetailFragment(workout.id)
                )
            }
        }
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

//    // Function to update workout
//    private fun updateWorkout() {
//        val updatedWorkout = Workout(
//            id = args.workout.id,
//            name = binding.editWorkoutName.text.toString(),
//            date = binding.editWorkoutDate.text.toString(),
//            duration = binding.editWorkoutDuration.text.toString()
//        )
//
//        val originalWorkout = args.workout.copy()
//        workoutViewModel.updateWorkout(updatedWorkout)
//
//        Snackbar.make(requireView(), "Workout updated", Snackbar.LENGTH_LONG)
//            .setAction("UNDO") {
//                workoutViewModel.updateWorkout(originalWorkout)
//            }
//            .show()
//
//        findNavController().navigate(R.id.action_editWorkoutFragment_to_workoutDetailFragment)
//    }

//    private fun deleteWorkout() {
//
//        val workoutToDelete = args.workout.copy()
//
//        workoutViewModel.deleteWorkout(args.workout)
//        findNavController().navigate(R.id.action_editWorkoutFragment_to_workoutDetailFragment)
//    }