package com.example.assignment3.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // Enable options menu
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupViewModel()
        setupFilterChips()
        setupFab()
    }

    // Set up the list
    private fun setupRecyclerView() {
        movieShowAdapter = MovieShowAdapter()
        binding.movieShowList.adapter = movieShowAdapter
        binding.movieShowList.layoutManager = LinearLayoutManager(requireContext())

        movieShowAdapter.setOnItemClickListener { movieShow ->
            val action = ListMovieShowFragmentDirections.actionListMovieShowsFragmentToMovieShowDetailFragment(movieShow.id)
            findNavController().navigate(action)
        }
    }

    private fun setupFilterChips() {
        binding.filterChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            when (checkedIds.firstOrNull()) {
                R.id.chipToWatch -> observeFilteredMovies("To Watch")
                R.id.chipWatching -> observeFilteredMovies("Watching")
                R.id.chipCompleted -> observeFilteredMovies("Completed")
                else -> {
                    // Show all movies when no chip is selected or "All" is selected
                    movieShowViewModel.allMoviesShows.observe(viewLifecycleOwner) { movieShowAdapter.submitList(it) }
                }
            }
        }
    }

    private fun observeFilteredMovies(status: String) {
        movieShowViewModel.getMovieShowsByStatus(status).observe(viewLifecycleOwner) {
            movieShowAdapter.submitList(it)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView

        searchView.apply {
            queryHint = "Search by title..."

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText.isNullOrEmpty()) {
                        // If search is empty, show all movies/shows
                        movieShowViewModel.allMoviesShows.observe(viewLifecycleOwner) {
                            movieShowAdapter.submitList(it)
                        }
                    } else {
                        // If there's a search query, show search results
                        movieShowViewModel.searchMovieShows(newText).observe(viewLifecycleOwner) {
                            movieShowAdapter.submitList(it)
                        }
                    }
                    return true
                }
            })
        }

        super.onCreateOptionsMenu(menu, inflater)
    }


    private fun setupViewModel() {
        movieShowViewModel = ViewModelProvider(this)[MovieShowViewModel::class.java]

        // Observe the filtered movies/shows
        movieShowViewModel.movieShows.observe(viewLifecycleOwner) { movieShows ->
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