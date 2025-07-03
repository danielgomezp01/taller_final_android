package com.example.moviesapp.ui.movie_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.data.MovieRepository
import com.example.moviesapp.data.local.MovieEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movies = MutableStateFlow<List<MovieEntity>>(emptyList())
    val movies: StateFlow<List<MovieEntity>> = _movies

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        getMovies()
    }

    fun getMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                // First, try to fetch from local database
                repository.getLocalMovies().collect { localMovies ->
                    _movies.value = localMovies
                }
                // Then, try to fetch from API and update local database
                val remoteMovies = repository.getRemotePopularMovies("YOUR_API_KEY") // Replace with your actual API key
                remoteMovies.forEach { movie ->
                    repository.insertLocalMovie(movie)
                }
            } catch (e: Exception) {
                _error.value = "Error fetching movies: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteMovie(movie: MovieEntity) {
        viewModelScope.launch {
            repository.deleteLocalMovie(movie)
        }
    }
}