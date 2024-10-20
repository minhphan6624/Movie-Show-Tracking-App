package com.example.assignment3.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment3.Adapter.ExerciseAdapter
import com.example.assignment3.databinding.FragmentDetailWorkoutBinding
import com.example.assignment3.data.WorkoutViewModel
import com.example.assignment3.data.Entity.Exercise

class WorkoutDetailFragment : Fragment() {

    private var _binding: FragmentDetailWorkoutBinding? = null
    private val binding get() = _binding!!
    private val workoutViewModel: WorkoutViewModel by viewModels()
    private val args: WorkoutDetailFragmentArgs by navArgs()
    private lateinit var exerciseAdapter: ExerciseAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWorkoutDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeWorkout()
    }

    private fun setupRecyclerView() {
        exerciseAdapter = ExerciseAdapter()
        binding.exerciseList.apply {
            adapter = exerciseAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun observeWorkout() {
        workoutViewModel.getWorkoutById(args.workoutId).observe(viewLifecycleOwner) { workout ->
            workout?.let {
                binding.workoutName.text = it.name
                binding.workoutDate.text = it.date
                binding.workoutDuration.text = it.duration ?: "N/A"
            }
        }

        workoutViewModel.getExercisesForWorkout(args.workoutId).observe(viewLifecycleOwner) { exercises ->
            exerciseAdapter.submitList(exercises)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}