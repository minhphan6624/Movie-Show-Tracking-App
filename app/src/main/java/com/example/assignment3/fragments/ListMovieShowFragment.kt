package com.example.assignment3.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment3.adapter.MovieShowAdapter
import com.example.assignment3.R
import com.example.assignment3.data.MovieShowViewModel

import com.example.assignment3.databinding.FragmentListBinding

class ListMovieShowFragment : Fragment() {

    private var _binding : FragmentListBinding? = null
    private val binding get()  = _binding!!

    private lateinit var movieShowViewModel: MovieShowViewModel
    private lateinit var movieShowAdapter: MovieShowAdapter

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupViewModel()
        setupFab()
    }

    private fun setupRecyclerView() {
        movieShowAdapter = MovieShowAdapter()
        binding.movieShowList.adapter = movieShowAdapter
        binding.movieShowList.layoutManager = LinearLayoutManager(requireContext())

        movieShowAdapter.setOnItemClickListener { movieShow ->
            val action = ListMovieShowFragmentDirections.actionListMovieShowsFragmentToMovieShowDetailFragment(movieShow.id)
            findNavController().navigate(action)
        }
    }

    private fun setupViewModel() {
        movieShowViewModel = ViewModelProvider(this).get(MovieShowViewModel::class.java)
        movieShowViewModel.allMoviesShows.observe(viewLifecycleOwner) { movieShows ->
            movieShowAdapter.submitList(movieShows)
        }
    }

    private fun setupFab() {
        binding.fabAddMovieShow.setOnClickListener {
            findNavController().navigate(R.id.action_listMovieShowsFragment_to_addMovieShowFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}