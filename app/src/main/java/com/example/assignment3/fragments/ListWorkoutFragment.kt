package com.example.assignment3.fragments

import WorkoutAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment3.R
import com.example.assignment3.data.WorkoutViewModel
import com.example.assignment3.databinding.FragmentListBinding

class ListWorkoutFragment : Fragment() {

    private var _binding : FragmentListBinding? = null
    private val binding get()  = _binding!!

    private lateinit var workoutAdapter: WorkoutAdapter
    private lateinit var workoutViewModel: WorkoutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using ViewBinding
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the RecyclerView
        workoutAdapter = WorkoutAdapter()
        binding.workoutList.apply {
            adapter = workoutAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        // Set up ViewModel and observe the data
        workoutViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)
        workoutViewModel.getAllWorkouts().observe(viewLifecycleOwner, { workouts ->
            workoutAdapter.setWorkouts(workouts)
        })

        //Handle navigation on FAB click
        binding.floatingActionButton.setOnClickListener() {
            findNavController().navigate(R.id.action_workoutListFragment_to_addWorkoutFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Prevent memory leaks
        _binding = null
    }
}