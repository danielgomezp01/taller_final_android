package com.example.moviesapp.ui.movie_detail

import androidx.lifecycle.SavedStateHandle
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
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _movie = MutableStateFlow<MovieEntity?>(null)
    val movie: StateFlow<MovieEntity?> = _movie

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val movieId: Int = savedStateHandle["movieId"] ?: -1

    init {
        if (movieId != -1) {
            getMovieById(movieId)
        }
    }

    fun getMovieById(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _movie.value = repository.getLocalMovieById(id)
            } catch (e: Exception) {
                _error.value = "Error fetching movie details: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun saveMovie(movie: MovieEntity) {
        viewModelScope.launch {
            if (movie.id == 0) { // New movie
                repository.insertLocalMovie(movie)
            } else { // Existing movie
                repository.updateLocalMovie(movie)
            }
        }
    }
}