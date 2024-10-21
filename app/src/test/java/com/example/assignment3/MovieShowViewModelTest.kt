package com.example.assignment3

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.assignment3.data.Entity.MovieShow
import com.example.assignment3.data.MovieShowRepository
import com.example.assignment3.data.MovieShowViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MovieShowViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Mock
    private lateinit var repository: MovieShowRepository

    private lateinit var viewModel: MovieShowViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = MovieShowViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun insertMovieShow_callsRepositoryInsert(): Unit = testScope.runBlockingTest {
        val movieShow = MovieShow(title = "Test Movie", type = "Movie", genre = "Action", releaseDate = "2023", status = "Watching")
        viewModel.insert(movieShow)
        verify(repository).insert(movieShow)
    }

    @Test
    fun getAllMoviesShows_returnsLiveDataFromRepository() {
        val mockLiveData: LiveData<List<MovieShow>> = MutableLiveData()
        `when`(repository.allMoviesShows).thenReturn(mockLiveData)

        val result = viewModel.allMoviesShows
        assert(result == mockLiveData)
    }

    // Add more tests for update, delete, and getMovieShowById operations
}