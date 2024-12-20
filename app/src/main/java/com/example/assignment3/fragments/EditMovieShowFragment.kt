package com.example.assignment3.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment3.R
import com.example.assignment3.data.Entity.MovieShow

import com.example.assignment3.data.MovieShowViewModel
import com.example.assignment3.databinding.FragmentEditBinding
import kotlinx.coroutines.launch


class EditMovieShowFragment : Fragment() {
    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieShowViewModel: MovieShowViewModel
    private val args: EditMovieShowFragmentArgs by navArgs()
    private var currentMovieShow: MovieShow? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieShowViewModel = ViewModelProvider(this).get(MovieShowViewModel::class.java)

        setupSpinners()
        observeMovieShow()
        setupUpdateButton()
        setupDeleteButton()
    }

    // Setup the dropboxes
    private fun setupSpinners() {
        val typeAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.movie_show_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerType.adapter = typeAdapter

        val statusAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.movie_show_statuses,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerStatus.adapter = statusAdapter
    }

    private fun observeMovieShow() {
        movieShowViewModel.getMovieShowById(args.movieShowId).observe(viewLifecycleOwner) { movieShow ->
            movieShow?.let {
                currentMovieShow = it
                populateFields(it)
            }
        }
    }

    private fun populateFields(movieShow: MovieShow) {
        binding.editTextTitle.setText(movieShow.title)
        binding.spinnerType.setSelection(resources.getStringArray(R.array.movie_show_types).indexOf(movieShow.type))
        binding.editTextGenre.setText(movieShow.genre)
        binding.editTextReleaseDate.setText(movieShow.releaseDate)
        binding.spinnerStatus.setSelection(resources.getStringArray(R.array.movie_show_statuses).indexOf(movieShow.status))
        binding.ratingBar.rating = movieShow.rating
        binding.editTextNotes.setText(movieShow.notes)
    }

    private fun setupUpdateButton() {
        binding.buttonUpdate.setOnClickListener {
            if (validateInput()) {
                val updatedMovieShow = currentMovieShow?.copy(
                    title = binding.editTextTitle.text.toString(),
                    type = binding.spinnerType.selectedItem.toString(),
                    genre = binding.editTextGenre.text.toString(),
                    releaseDate = binding.editTextReleaseDate.text.toString(),
                    status = binding.spinnerStatus.selectedItem.toString(),
                    rating = binding.ratingBar.rating,
                    notes = binding.editTextNotes.text.toString()
                )
                updatedMovieShow?.let { movieShow ->
                    movieShowViewModel.update(movieShow)
                    Toast.makeText(requireContext(), "Movie/Show updated successfully", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }
            }
        }
    }

    // Delete functionality
    private fun setupDeleteButton() {
        binding.buttonDelete.setOnClickListener {
            showDeleteConfirmationDialog()
        }
    }

    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Movie/Show")
            .setMessage("Are you sure you want to delete this movie/show?")
            .setPositiveButton("Delete") { _, _ ->
                deleteMovieShow()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteMovieShow() {
        currentMovieShow?.let { movieShow ->
            movieShowViewModel.delete(movieShow)
            Toast.makeText(requireContext(), "Movie/Show deleted", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_editMovieShowFragment_to_listMovieShowsFragment)
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true

        // Title validation
        if (binding.editTextTitle.text.isNullOrBlank()) {
            binding.titleInputLayout.error = "Please enter a title"
            isValid = false
        } else {
            binding.titleInputLayout.error = null
        }

        // Genre validation
        if (binding.editTextGenre.text.isNullOrBlank()) {
            binding.genreInputLayout.error = "Please enter a genre"
            isValid = false
        } else {
            binding.genreInputLayout.error = null
        }

        // Release year validation
        val releaseYear = binding.editTextReleaseDate.text.toString()
        if (releaseYear.isBlank()) {
            binding.releaseDateInputLayout.error = "Please enter a release year"
            isValid = false
        } else {
            try {
                val year = releaseYear.toInt()
                val currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
                when {
                    year < 1888 -> { // First known film was in 1888
                        binding.releaseDateInputLayout.error = "Year must be 1888 or later"
                        isValid = false
                    }
                    year > currentYear + 5 -> { // Allow up to 5 years in the future for upcoming releases
                        binding.releaseDateInputLayout.error = "Year cannot be more than 5 years in the future"
                        isValid = false
                    }
                    else -> binding.releaseDateInputLayout.error = null
                }
            } catch (e: NumberFormatException) {
                binding.releaseDateInputLayout.error = "Please enter a valid year"
                isValid = false
            }
        }

        // Type validation
        if (binding.spinnerType.selectedItem == null) {
            Toast.makeText(requireContext(), "Please select a type", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        // Status validation
        if (binding.spinnerStatus.selectedItem == null) {
            Toast.makeText(requireContext(), "Please select a status", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        // Rating validation (if you want to ensure a minimum rating)
        // if (binding.ratingBar.rating == 0f) {
        //     Toast.makeText(requireContext(), "Please rate the movie/show", Toast.LENGTH_SHORT).show()
        //     isValid = false
        // }

        // Notes are optional, so no validation needed unless you want to add specific requirements

        if (!isValid) {
            Toast.makeText(requireContext(), "Please fix the errors above", Toast.LENGTH_SHORT).show()
        }

        return isValid
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}