package com.example.moviesapp.data

import com.example.moviesapp.data.local.MovieDao
import com.example.moviesapp.data.local.MovieEntity
import com.example.moviesapp.data.remote.MovieApiService
import com.example.moviesapp.data.remote.MovieDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieDao: MovieDao,
    private val movieApiService: MovieApiService
) {
    // Local operations (Room)
    fun getLocalMovies(): Flow<List<MovieEntity>> {
        return movieDao.getAllMovies()
    }

    suspend fun getLocalMovieById(movieId: Int): MovieEntity? {
        return movieDao.getMovieById(movieId)
    }

    suspend fun insertLocalMovie(movie: MovieEntity) {
        movieDao.insertMovie(movie)
    }

    suspend fun updateLocalMovie(movie: MovieEntity) {
        movieDao.updateMovie(movie)
    }

    suspend fun deleteLocalMovie(movie: MovieEntity) {
        movieDao.deleteMovie(movie)
    }

    // Remote operations (API)
    suspend fun getRemotePopularMovies(apiKey: String): List<MovieEntity> {
        return try {
            movieApiService.getPopularMovies(apiKey).results.map { it.toMovieEntity() }
        } catch (e: Exception) {
            // Handle API error
            emptyList()
        }
    }

    // Mapper function to convert DTO to Entity
    private fun MovieDto.toMovieEntity(): MovieEntity {
        return MovieEntity(
            id = this.id,
            title = this.title,
            overview = this.overview,
            releaseDate = this.releaseDate,
            posterPath = this.posterPath
        )
    }
}