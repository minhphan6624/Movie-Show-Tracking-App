package com.example.assignment3.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment3.databinding.FragmentDetailBinding
import com.example.assignment3.data.MovieShowViewModel

class MovieShowDetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!


    private lateinit var movieShowViewModel: MovieShowViewModel
    private val args: MovieShowDetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieShowViewModel = ViewModelProvider(this).get(MovieShowViewModel::class.java)

        observeMovieShow()
        setupEditButton()
    }

    private fun observeMovieShow() {
        movieShowViewModel.getMovieShowById(args.movieShowId).observe(viewLifecycleOwner) { movieShow ->
            movieShow?.let {
                binding.textViewTitle.text = it.title
                binding.textViewType.text = it.type
                binding.textViewGenre.text = it.genre
                binding.textViewReleaseDate.text = it.releaseDate
                binding.textViewStatus.text = it.status
                binding.ratingBar.rating = it.rating
                binding.textViewNotes.text = it.notes
            }
        }
    }

    private fun setupEditButton() {
        binding.buttonEdit.setOnClickListener {
            val action = MovieShowDetailFragmentDirections.actionMovieShowDetailFragmentToEditMovieShowFragment(args.movieShowId)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}