package com.example.assignment3.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.assignment3.R
import com.example.assignment3.data.Entity.MovieShow
import com.example.assignment3.data.MovieShowViewModel
import com.example.assignment3.databinding.FragmentAddBinding

class AddMovieShowFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieShowViewModel: MovieShowViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieShowViewModel = ViewModelProvider(this).get(MovieShowViewModel::class.java)

        setupSpinners()
        setupSaveButton()
    }

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

    private fun setupSaveButton() {
        binding.buttonSave.setOnClickListener {
            if (validateInput()) {
                val newMovieShow = MovieShow(
                    title = binding.editTextTitle.text.toString(),
                    type = binding.spinnerType.selectedItem.toString(),
                    genre = binding.editTextGenre.text.toString(),
                    releaseDate = binding.editTextReleaseDate.text.toString(),
                    status = binding.spinnerStatus.selectedItem.toString(),
                    rating = binding.ratingBar.rating,
                    notes = binding.editTextNotes.text.toString()
                )
                movieShowViewModel.insert(newMovieShow)
                Toast.makeText(requireContext(), "Movie/Show added successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
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