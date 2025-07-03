package com.example.moviesapp.ui.movie_detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moviesapp.data.local.MovieEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    navController: NavController,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val movie by viewModel.movie.collectAsState()

    var title by remember { mutableStateOf("") }
    var overview by remember { mutableStateOf("") }
    var releaseDate by remember { mutableStateOf("") }
    var posterPath by remember { mutableStateOf("") }

    LaunchedEffect(movie) {
        movie?.let {
            title = it.title
            overview = it.overview
            releaseDate = it.releaseDate
            posterPath = it.posterPath ?: ""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (movie == null) "Agregar Película" else "Editar Película") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val movieToSave = movie?.copy(
                    title = title,
                    overview = overview,
                    releaseDate = releaseDate,
                    posterPath = posterPath.ifEmpty { null }
                ) ?: MovieEntity(
                    title = title,
                    overview = overview,
                    releaseDate = releaseDate,
                    posterPath = posterPath.ifEmpty { null }
                )
                viewModel.saveMovie(movieToSave)
                navController.popBackStack()
            }) {
                Icon(Icons.Default.Save, contentDescription = "Guardar Película")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = overview,
                onValueChange = { overview = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = releaseDate,
                onValueChange = { releaseDate = it },
                label = { Text("Fecha de Lanzamiento (YYYY-MM-DD)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = posterPath,
                onValueChange = { posterPath = it },
                label = { Text("Ruta del Poster (ej. /abc.jpg)") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}